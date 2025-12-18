<%-- 
    Document   : index
    Created on : Dec 7, 2025, 11:28:13 PM
    Author     : Lenobo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PT. RyoBambang</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="index.jsp">Home</a></li>
            <li><a href="ProdukController">Produk</a></li>
            <li><a href="DetailPesananController">Detail Pesanan</a></li>
            <li><a href="RiwayatBayarController">Riwayat Bayar</a></li>
        </ul>
    </nav>
</header>

<div class="container">
    <!-- Sidebar -->
    <aside class="sidebar">
        <h3>Menu</h3>
        <ul>
            <li><a href="index.jsp">Home</a></li>
            <%
                com.ryobambang.model.iduser u = (com.ryobambang.model.iduser)session.getAttribute("id_user");
                if (u != null) {
            %>
                <li><a href="ProdukController">Produk</a></li>
                <li><a href="DetailPesananController">Detail Pesanan</a></li>
                <li><a href="RiwayatBayarController">Riwayat Bayar</a></li>
                <li><a href="LogoutController">Logout</a></li>
            <%
                } else {
            %>
                <li><a href="index.jsp">Login</a></li>
            <%
                }
            %>
        </ul>
    </aside>

    <!-- Content -->
    <main class="content">
        <%
            if (u == null) {
        %>
            <h2>Selamat Datang</h2>
            <p>Silakan login untuk mulai berbelanja.</p>
            <form action="LoginController" method="post" class="login-form">
                <label>Username:</label>
                <input type="text" name="username" required>
                <label>Password:</label>
                <input type="password" name="password" required>
                <input type="submit" value="Login" class="btn">
            </form>
            <%
                if ("true".equals(request.getParameter("error"))) {
            %>
                <p style="color:red;">Login gagal, username atau password salah!</p>
            <%
                }
            %>
        <%
            } else {
        %>
            <h2>Selamat Datang</h2>
            <p>Halo, <b><%=u.getUsername()%></b>! Senang melihatmu kembali.</p>
            <p>Silakan pilih menu di sidebar untuk mulai berbelanja atau melihat pesanan.</p>
        <%
            }
        %>
    </main>
</div>
    
</body>
</html>
