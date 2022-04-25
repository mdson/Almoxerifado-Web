<%-- 
    Document   : visualizarproduto
    Created on : 13 de jul de 2021, 04:21:23
    Author     : MaD
--%>

<%@page import="br.recife.edu.ifpe.model.classes.Produto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Visualizar Produto</title>
    </head>
    <body>
        <h2>Ficha Produto</h2>
        <%
            Produto produto = (Produto)request.getAttribute("produto");
            if (produto != null) {
        %>
        <hr>
        <strong>Produto encontrado:</strong> Código: <%= produto.getCodigo() %><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: <%= produto.getNome() %><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Marca: <%= produto.getMarca()%><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Categoria: <%= produto.getCategoria()%><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Descrição: <%= produto.getDescricao()%><br/>
        <hr>
        <% }%>
    </body>
</html>
