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
@WebServlet(name = "ProdukController", urlPatterns = {"/ProdukController"})
public class ProdukController extends HttpServlet {

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

        String notif = ""; // pesan notifikasi
        String konten = "<h2>Daftar Produk</h2>";
        DecimalFormat formatRupiah = new DecimalFormat("#,###");

        try (Connection conn = new com.ryobambang.model.Koneksi().getConnection()) {

            // Tangani aksi pemesanan
            String aksi = request.getParameter("aksi");
            if ("Pesan".equals(aksi)) {
                int idProduk = Integer.parseInt(request.getParameter("id_produk"));
                int jumlah = Integer.parseInt(request.getParameter("jumlah"));

                // Ambil user dari session
                HttpSession session = request.getSession(false);
                com.ryobambang.model.iduser userObj = (com.ryobambang.model.iduser) session.getAttribute("id_user");

                if (userObj == null) {
                    notif = "<p style='color:red;'>Error: User tidak ditemukan di session!</p>";
                } else {
                    int idUser = userObj.getId_user(); // gunakan getter sesuai class iduser

                    // Ambil harga produk untuk hitung total
                    String sqlHarga = "SELECT harga FROM idproduk WHERE id_produk = ?";
                    PreparedStatement psHarga = conn.prepareStatement(sqlHarga);
                    psHarga.setInt(1, idProduk);
                    ResultSet rsHarga = psHarga.executeQuery();
                    double harga = 0;
                    if (rsHarga.next()) {
                        harga = rsHarga.getDouble("harga");
                    }
                    rsHarga.close();
                    psHarga.close();

                    double total = harga * jumlah;

                    // Buat pesanan baru dengan id_user dan total
                    String insertPesanan = "INSERT INTO idpesanan (id_user, tanggal, total) VALUES (?, CURRENT_DATE, ?)";
                    PreparedStatement psPesanan = conn.prepareStatement(insertPesanan, Statement.RETURN_GENERATED_KEYS);
                    psPesanan.setInt(1, idUser);
                    psPesanan.setDouble(2, total);
                    psPesanan.executeUpdate();
                    ResultSet rsPesanan = psPesanan.getGeneratedKeys();
                    rsPesanan.next();
                    int idPesanan = rsPesanan.getInt(1);

                    // Tambahkan detail pesanan
                    String insertDetail = "INSERT INTO iddetail_pesanan (id_pesanan, id_produk, jumlah) VALUES (?, ?, ?)";
                    PreparedStatement psDetail = conn.prepareStatement(insertDetail);
                    psDetail.setInt(1, idPesanan);
                    psDetail.setInt(2, idProduk);
                    psDetail.setInt(3, jumlah);
                    psDetail.executeUpdate();

                    // Update stok produk
                    String updateStok = "UPDATE idproduk SET stok = stok - ? WHERE id_produk = ?";
                    PreparedStatement psUpdate = conn.prepareStatement(updateStok);
                    psUpdate.setInt(1, jumlah);
                    psUpdate.setInt(2, idProduk);
                    psUpdate.executeUpdate();

                    notif = "<p style='color:green;'>Pesanan berhasil dibuat!</p>";
                }
            }

            // Tampilkan daftar produk dengan stok
            konten += "<table border='1' cellpadding='8' cellspacing='0' style='width:100%; font-size:16px;'>";
            konten += "<tr><th>ID Produk</th><th>Nama Produk</th><th>Harga</th><th>Stok</th><th>Aksi</th></tr>";

            String sql = "SELECT id_produk, nama_produk, harga, stok FROM idproduk";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idProduk = rs.getInt("id_produk");
                String namaProduk = rs.getString("nama_produk");
                double harga = rs.getDouble("harga");
                int stok = rs.getInt("stok");
                String hargaFormatted = "Rp " + formatRupiah.format(harga);

                konten += "<tr>"
                        + "<td>" + idProduk + "</td>"
                        + "<td>" + namaProduk + "</td>"
                        + "<td>" + hargaFormatted + "</td>"
                        + "<td>" + stok + "</td>"
                        + "<td>"
                        + "<form method='post' action='ProdukController'>"
                        + "<input type='hidden' name='id_produk' value='" + idProduk + "'>"
                        + "<input type='number' name='jumlah' min='1' max='" + stok + "' value='1' required>"
                        + "<input type='submit' name='aksi' value='Pesan'"
                        + (stok == 0 ? " disabled" : "") + ">"
                        + "</form>"
                        + "</td>"
                        + "</tr>";
            }

            rs.close();
            ps.close();
            konten += "</table>";

        } catch (Exception e) {
            notif = "<p style='color:red;'>Error: " + e.getMessage() + "</p>";
        }

        // gabungkan notif + konten
        request.setAttribute("konten", notif + konten);
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
