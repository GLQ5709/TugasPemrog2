/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ryobambang.view;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Lenobo
 */
@WebServlet(name = "MainForm", urlPatterns = {"/MainForm"})
public class MainForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void tampilkan(String konten, HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(false); // jangan buat session baru
        if (session == null || session.getAttribute("user") == null) {
            // belum login → balik ke index.jsp
            response.sendRedirect("index.jsp");
            return;
        }

        // kalau sudah login, ambil user dari session
        com.ryobambang.model.iduser u = (com.ryobambang.model.iduser) session.getAttribute("id_user");
        String userName = u.getUsername();

        // menu samping
        String menu = "<br><b>Master Data</b><br>"
                + "<a href=ProdukController>Produk</a><br>"
                + "<b>Transaksi</b><br>"
                + "<a href=PesananController>Pesanan</a><br><br>"
                + "<b>Laporan</b><br>"
                + "<a href=DetailPesananController>Detail Pesanan</a><br><br>"
                + "<a href=LogoutController>Logout</a><br><br>";

        // top menu
        String topMenu = "<nav><ul>"
                + "<li><a href=MainForm>Home</a></li>"
                + "<li><a href=ProdukController>Produk</a></li>"
                + "<li><a href=PesananController>Pesanan</a></li>"
                + "<li><a href=DetailPesananController>Detail Pesanan</a></li>"
                + "<li><a href=LogoutController>Logout</a></li>"
                + "</ul></nav>";

        konten += "<h2>Halo, " + userName + "</h2>";

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href='style.css' rel='stylesheet' type='text/css' />");
            out.println("<title>MainForm - PT RyoBambang</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"#808080\">");
            out.println("<center>");
            out.println("<table width=\"80%\" bgcolor=\"#eeeeee\"> ");
            out.println("<tr><td colspan=\"2\" align=\"center\">");
            out.println("<h2>PT RyoBambang</h2>");
            out.println("<h4>Toko Baju Online</h4>");
            out.println("</td></tr>");
            out.println("<tr height=\"400\">");
            out.println("<td width=\"200\" align=\"center\" valign=\"top\" bgcolor=\"#eeffee\">");
            out.println("<div id='menu'>" + menu + "</div>");
            out.println("</td>");
            out.println("<td align=\"center\" valign=\"top\" bgcolor=\"#ffffff\">");
            out.println(topMenu);
            out.println("<br>" + konten);
            out.println("</td></tr>");
            out.println("<tr><td colspan=\"2\" align=\"center\" bgcolor=\"#eeeef\">");
            out.println("<small>&copy; 2025 PT RyoBambang</small>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tampilkan("", request, response);
    }
}
