<%-- 
    Document   : index
    Created on : Dec 7, 2025, 11:28:13 PM
    Author     : Lenobo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>PT. RyoBambang</title>
    </head>
    <body>
    <header>
        <div class="logo">
            <h1>PT. RyoBambang</h1>
            <p>Toko Baju Online</p>
        </div>
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <%
                    com.ryobambang.model.iduser u = (com.ryobambang.model.iduser)session.getAttribute("id_user");
                    if (u != null) {
                %>
                    <!-- Sudah login -->
                    <li><a href="ProdukController">Produk</a></li>
                    <li><a href="DetailPesananController">Detail Pesanan</a></li>
                    <li><a href="LogoutController">Logout</a></li>
                <%
                    }
                %>
            </ul>
        </nav>
    </header>

    <div class="container">
        <main class="content">
            <%
                if (u == null) {
            %>
                <h2>Selamat Datang</h2>
                <p>Silakan login untuk mulai berbelanja.</p>
                <form action="LoginController" method="post">
                    <label>Username:</label>
                    <input type="text" name="username" required><br>
                    <label>Password:</label>
                    <input type="password" name="password" required><br>
                    <input type="submit" value="Login">
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
                <p>Silakan pilih menu di atas untuk mulai berbelanja atau melihat pesanan.</p>
            <%
                }
            %>
        </main>
    </div>

    <footer>
        <p>&copy; 2025 PT. RyoBambang | Toko Baju Online</p>
    </footer>
    </body>
</html>
