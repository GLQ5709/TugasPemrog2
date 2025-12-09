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

            String sql = "SELECT p.id_pesanan, p.tanggal, p.total, u.username, " +
                         "d.id_produk, pr.nama_produk, d.jumlah, pr.harga " +
                         "FROM idpesanan p " +
                         "JOIN iddetail_pesanan d ON p.id_pesanan = d.id_pesanan " +
                         "JOIN idproduk pr ON d.id_produk = pr.id_produk " +
                         "JOIN iduser u ON p.id_user = u.id_user " +
                         "ORDER BY p.id_pesanan DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            konten += "<table border='1' cellpadding='8' cellspacing='0' style='width:100%; font-size:16px;'>";
            konten += "<tr><th>ID Pesanan</th><th>User</th><th>Tanggal</th><th>Produk</th><th>Jumlah</th><th>Harga</th></tr>";

            double grandTotal = 0; // untuk akumulasi semua pesanan

            if (!rs.isBeforeFirst()) {
                konten += "<tr><td colspan='6' style='text-align:center; color:blue; font-size:18px;'>Belum Ada Pesanan Nih, Belanja Yuk 😊</td></tr>";
            } else {
                while (rs.next()) {
                    int idPesanan = rs.getInt("id_pesanan");
                    String tanggal = rs.getString("tanggal");
                    double total = rs.getDouble("total"); // total per pesanan
                    String username = rs.getString("username");
                    String namaProduk = rs.getString("nama_produk");
                    int jumlah = rs.getInt("jumlah");
                    double harga = rs.getDouble("harga");

                    String hargaFormatted = "Rp " + formatRupiah.format(harga);

                    // akumulasi grand total
                    grandTotal += total;

                    konten += "<tr>"
                            + "<td>" + idPesanan + "</td>"
                            + "<td>" + username + "</td>"
                            + "<td>" + tanggal + "</td>"
                            + "<td>" + namaProduk + "</td>"
                            + "<td>" + jumlah + "</td>"
                            + "<td>" + hargaFormatted + "</td>"
                            + "</tr>";
                }

                // baris total semua pesanan
                konten += "<tr><td colspan='6' style='text-align:right; font-weight:bold;'>TOTAL SEMUA PESANAN: Rp " 
                        + formatRupiah.format(grandTotal) + "</td></tr>";

                // tombol BAYAR untuk semua pesanan
                konten += "<tr><td colspan='6' style='text-align:center;'>"
                        + "<form method='post' action='BayarController'>"
                        + "<input type='hidden' name='grand_total' value='" + grandTotal + "'>"
                        + "<input type='submit' value='BAYAR' style='background-color:green; color:white; padding:8px 15px;'>"
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
