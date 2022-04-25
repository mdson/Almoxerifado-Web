<%-- 
    Document   : estoque
    Created on : 10 de ago de 2021, 22:25:09
    Author     : MaD
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ifpe" uri="/WEB-INF/tlds/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estoque de Produtos</title>
        <style>
            h2{
                text-align: center;
                background-color: black;
                color: white;
            }
        </style>
    </head>
    <body>
        <h2>Produtos em Estoque</h2>
        <ifpe:carregaestoque/>
        
        <table border="2" CELLPADDING=6 rules="all">
            <CAPTION>Lista de produtos no estoque</CAPTION>
            <tr>
                <th>Código do produto</th><th>Nome</th><th>Quantidade em estoque</th>
            </tr>
            <c:forEach var="item" items="${estoque.itens}">
                <tr>
                    <td>
                        <center><c:out value="${item.codigo}"/></center>
                    </td>
                    <td>
                        <center><c:out value="${item.produto.nome}"/></center>
                    </td>
                    <td>
                        <center><c:out value="${item.quantidade}"/></center>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <br/>
        <br/>
        
        <center><a href=index.html><button>Página Inicial</button></a></center>
    </body>
</html>
