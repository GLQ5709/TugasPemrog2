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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id_user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        com.ryobambang.model.iduser u = (com.ryobambang.model.iduser) session.getAttribute("id_user");
        String userName = u.getUsername();

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/style.css'>");
            out.println("<title>PT. RyoBambang</title>");
            out.println("</head>");
            out.println("<body>");

            // header
            out.println("<header>");
            out.println("<div class='logo'>");
            out.println("<h1>PT. RyoBambang</h1>");
            out.println("<p>Toko Baju Online</p>");
            out.println("</div>");
            out.println("<nav>");
            out.println("<ul>");
            out.println("<li><a href='index.jsp'>Home</a></li>");
            out.println("<li><a href='ProdukController'>Produk</a></li>");
            out.println("<li><a href='DetailPesananController'>Detail Pesanan</a></li>");
            out.println("<li><a href='LogoutController'>Logout</a></li>");
            out.println("</ul>");
            out.println("</nav>");
            out.println("</header>");

            // main content
            out.println("<div class='container'>");
            out.println("<main class='content'>");
            out.println("<h2>Halo, " + userName + "</h2>");
            out.println("<br>" + konten);
            out.println("</main>");
            out.println("</div>");

            // footer
            out.println("<footer>");
            out.println("<p>&copy; 2025 PT. RyoBambang | Toko Baju Online</p>");
            out.println("</footer>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String konten = (String) request.getAttribute("konten");

        if (konten == null) {
            konten = "<p>Selamat Datang di PT RyoBambang</p>";
        }

        tampilkan(konten, request, response);
    }
}
