<%-- 
    Document   : loteentrada
    Created on : 12 de jul de 2021, 20:01:43
    Author     : MaD
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ifpe" uri="/WEB-INF/tlds/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Lote de Entrada</title>
        <style>
            h2{
                text-align: center;
                background-color: black;
                color: white;
            }
        </style>
    </head>
    <body>
        <h2>Lista dos Produtos nos Lotes</h2>
        <ifpe:carregaprodutos/>
        <ifpe:carregafuncionarios/>
        
        <h3><c:out value="${msg}"/></h3>
        <c:remove var="msg" scope="session"/>
        
        <br/>
        <br/>
        
        <table border="2" CELLPADDING=6 rules="all">
            <CAPTION>Catálogo de Produtos</CAPTION>
            <tr>
                <th>Código</th><th>Nome</th><th>Marca</th><th>Categoria</th><th>Add item</th>
            </tr>
            <c:forEach var="pAux" items="${produtos}">
                <tr>
                    <td>
                        <center>${pAux.codigo}</center>
                    </td>
                    <td>
                        <center>${pAux.nome}</center>
                    </td>
                    <td>
                        <center>${pAux.marca}</center>
                    </td>
                    <td>
                        <center>${pAux.categoria}</center>
                    </td>
                    <td>
                        <center><a href="#" onclick="adiciona(${pAux.codigo})"><button style=" background-color: green; color: white;">+</button></a></center>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <br/>
        <br/>
        
        <c:if test="${loteEntrada != null}">
        <table border="2" CELLPADDING=6 rules="all">
            <CAPTION>Lote de Produtos</CAPTION>
            <tr>
                <th>Código</th><th>Nome</th><th>Marca</th><th>Categoria</th><th>Quantidade</th><th>Add item</th><th>Remove item</th>
            </tr>
            <c:forEach var="i" items="${loteEntrada.itens}">
                <tr>
                    <td>
                        <center>${i.produto.codigo}</center>
                    </td>
                    <td>
                        <center>${i.produto.nome}</center>
                    </td>
                    <td>
                        <center>${i.produto.marca}</center>
                    </td>
                    <td>
                        <center>${i.produto.categoria}</center>
                    </td>
                    <td>
                        <center>${i.quantidade}</center>
                    </td>
                    <td>
                        <center><a href="#" onclick="adiciona(${i.produto.codigo})"><button style=" background-color: green; color: white;">+</button></a></center>
                    </td>
                    <td>
                        <center><a href="#" onclick="diminui(${i.produto.codigo})"><button style=" background-color: red; color: white;">-</button></a></center>
                    </td>
                </tr>
        </c:forEach> 
        </table>
        <button onclick="cadastrar()">Cadastrar Lote no Repositório</button>
        </c:if>
        
        <br/>
        <br/>
        
        <table border="2" CELLPADDING=6 rules="all">
            <CAPTION>Funcionários Cadastrados</CAPTION>
            <tr>
                <th>Código</th><th>Nome</th><th>Departamento</th>
            </tr>
            <c:forEach var="fAux" items="${funcionarios}">
                <tr>
                    <td>
                        <center>${fAux.codigo}</center>
                    </td>
                    <td>
                        <center>${fAux.nome}</center>
                    </td>
                    <td>
                        <center>${fAux.departamento}</center>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <br/>
        <br/>
        
        <script>
            function adiciona(codigo){
                fetch("LoteEntradaServlet?operacao=mais&codigo="+codigo,{method:"put"})
                        .then(function(){
                            location.reload();
                        });
            }
            
            function diminui(codigo){
                fetch("LoteEntradaServlet?operacao=menos&codigo="+codigo,{method:"put"})
                        .then(function(){
                            location.reload();
                        });
            }
            
            function cadastrar(){
                fetch("LoteEntradaServlet", {method:"post"})
                        .then(function(response){
                            if(response.status === 500){
                                location.reload();
                    }
                            else{
                                location.href = "loteentradaapresentacao.jsp";
                            }
                        }).catch(function(erro){
                            location.reload();
                        });
            }
        </script>
        
        <center><a href=index.html><button>Página Inicial</button></a></center>
    </body>
</html>
