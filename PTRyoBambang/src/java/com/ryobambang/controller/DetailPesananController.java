/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ryobambang.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;

/**
 *
 * @author Lenobo
 */
@WebServlet(name = "DetailPesananController", urlPatterns = {"/DetailPesananController"})
public class DetailPesananController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String konten = "<h2>Detail Pesanan</h2>";
        DecimalFormat formatRupiah = new DecimalFormat("#,###");

        try (Connection conn = new com.ryobambang.model.Koneksi().getConnection()) {

            // Jika tombol BAYAR ditekan
            String metodePembayaran = request.getParameter("metode_pembayaran");
            String alamat = request.getParameter("alamat");
            String grandTotalStr = request.getParameter("grand_total");

            if (metodePembayaran != null && alamat != null && grandTotalStr != null) {
                HttpSession session = request.getSession(false);
                com.ryobambang.model.iduser userObj = (com.ryobambang.model.iduser) session.getAttribute("id_user");
                int idUser = userObj.getId_user();

                // Insert ke idbayar per produk (harga per baris)
                String insertBayar = "INSERT INTO idbayar (id_pesanan, id_user, id_produk, tanggal, produk, jumlah_beli, total, metode_pembayaran, alamat) "
                        + "SELECT p.id_pesanan, p.id_user, d.id_produk, p.tanggal, pr.nama_produk, d.jumlah, pr.harga, ?, ? "
                        + "FROM idpesanan p "
                        + "JOIN iddetail_pesanan d ON p.id_pesanan = d.id_pesanan "
                        + "JOIN idproduk pr ON d.id_produk = pr.id_produk "
                        + "WHERE p.id_user = ?";
                PreparedStatement psBayar = conn.prepareStatement(insertBayar);
                psBayar.setString(1, metodePembayaran);
                psBayar.setString(2, alamat);
                psBayar.setInt(3, idUser);
                psBayar.executeUpdate();
                psBayar.close();

                // Hapus pesanan setelah dibayar
                PreparedStatement psDeleteDetail = conn.prepareStatement("DELETE FROM iddetail_pesanan WHERE id_pesanan IN (SELECT id_pesanan FROM idpesanan WHERE id_user = ?)");
                psDeleteDetail.setInt(1, idUser);
                psDeleteDetail.executeUpdate();
                psDeleteDetail.close();

                konten += "<p style='color:green;'>Pembayaran berhasil! Data tersimpan dan pesanan dihapus.</p>";
            }

            // Tampilkan detail pesanan
            String sql = "SELECT p.id_pesanan, p.tanggal, p.total, u.username, "
                    + "d.id_produk, pr.nama_produk, d.jumlah, pr.harga "
                    + "FROM idpesanan p "
                    + "JOIN iddetail_pesanan d ON p.id_pesanan = d.id_pesanan "
                    + "JOIN idproduk pr ON d.id_produk = pr.id_produk "
                    + "JOIN iduser u ON p.id_user = u.id_user "
                    + "ORDER BY p.id_pesanan DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            konten += "<table border='1' cellpadding='8' cellspacing='0' style='width:100%; font-size:16px;'>";
            konten += "<tr><th>ID Pesanan</th><th>User</th><th>Tanggal</th><th>Produk</th><th>Jumlah</th><th>Harga</th></tr>";

            double grandTotal = 0;

            if (!rs.isBeforeFirst()) {
                konten += "<tr><td colspan='6' style='text-align:center; color:blue; font-size:18px;'>Belum Ada Pesanan Nih, Belanja Yuk 😊</td></tr>";
            } else {
                while (rs.next()) {
                    int idPesanan = rs.getInt("id_pesanan");
                    String tanggal = rs.getString("tanggal");
                    double harga = rs.getDouble("harga");
                    String username = rs.getString("username");
                    String namaProduk = rs.getString("nama_produk");
                    int jumlah = rs.getInt("jumlah");

                    String hargaFormatted = "Rp " + formatRupiah.format(harga);
                    grandTotal += harga * jumlah;

                    konten += "<tr>"
                            + "<td>" + idPesanan + "</td>"
                            + "<td>" + username + "</td>"
                            + "<td>" + tanggal + "</td>"
                            + "<td>" + namaProduk + "</td>"
                            + "<td>" + jumlah + "</td>"
                            + "<td>" + hargaFormatted + "</td>"
                            + "</tr>";
                }

                // Baris total semua pesanan
                konten += "<tr><td colspan='6' style='text-align:right; font-weight:bold;'>TOTAL SEMUA PESANAN: Rp "
                        + formatRupiah.format(grandTotal) + "</td></tr>";

                // Form pembayaran & pengiriman
                konten += "<tr><td colspan='6'>"
                        + "<form method='post' action='DetailPesananController'>"
                        + "<input type='hidden' name='grand_total' value='" + grandTotal + "'>"

                        + "<label>Metode Pembayaran:</label><br>"
                        + "<select name='metode_pembayaran' required>"
                        + "<option value='Transfer Bank'>Transfer Bank</option>"
                        + "<option value='E-Wallet'>E-Wallet</option>"
                        + "<option value='COD'>Cash on Delivery</option>"
                        + "</select><br><br>"

                        + "<label>Alamat Lengkap:</label><br>"
                        + "<textarea name='alamat' rows='3' cols='50' required></textarea><br><br>"

                        + "<input type='submit' value='BAYAR' "
                        + "style='background-color:green; color:white; padding:8px 15px; font-weight:bold;'>"
                        + "</form>"
                        + "</td></tr>";
            }

            konten += "</table>";

            rs.close();
            ps.close();

        } catch (Exception e) {
            konten += "<p style='color:red;'>Error: " + e.getMessage() + "</p>";
        }

        request.setAttribute("konten", konten);
        request.getRequestDispatcher("MainForm").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
