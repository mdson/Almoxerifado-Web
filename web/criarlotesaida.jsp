<%-- 
    Document   : criarlotesaida
    Created on : 11 de ago de 2021, 02:53:35
    Author     : MaD
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ifpe" uri="/WEB-INF/tlds/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Criar Lote de Saída</title>
        <style>
            h2{
                text-align: center;
                background-color: black;
                color: white;
            }
        </style>
    </head>
    <body>
        <h2>Criar Lote de Saída</h2>
        <ifpe:carregaestoque/>
        <ifpe:carregafuncionarios/>
        
        <h3><c:out value="${msg}"/></h3>
        <c:remove var="msg" scope="session"/>
        
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
                    <td>
                        <center><a href="#" onclick="adiciona(${item.codigo})"><button style=" background-color: green; color: white;">+</button></a></center>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <br/>
        <br/>
        
        <c:if test="${loteSaida != null}">
        <table border="2" CELLPADDING=6 rules="all">
            <CAPTION>Lote de saída a ser criado</CAPTION>
            <tr>
                <th>Código do produto</th><th>Nome</th><th>Quantidade para remoção</th>
            </tr>
            <c:forEach var="i" items="${loteSaida.itens}">
                <tr>
                    <td>
                        <center>${i.produto.codigo}</center>
                    </td>
                    <td>
                        <center>${i.produto.nome}</center>
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
                
                <button onclick="cadastrarLoteSaida()">Cadastrar Lote no Repositório</button>
        </table>
        
            <table border="2" CELLPADDING=6 rules="all">
                <tr>
                    <th>Responsável</th>
                </tr>
                <tr>
                    <td>
                        <select>
                            <c:forEach var="fAux" items="${funcionarios}">
                                <option onclick="responsavel()"><c:out value="${fAux.nome}"/></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>  
            </table>

        </c:if>
        
        <script>
            function adiciona(codigo){
                fetch("LoteSaidaServlet?operacao=mais&codigo="+codigo,{method:"put"})
                        .then(function(){
                            location.reload();
                        });
            }
            
            function diminui(codigo){
                fetch("LoteSaidaServlet?operacao=menos&codigo="+codigo,{method:"put"})
                        .then(function(){
                            location.reload();
                        });
            }
            
            function responsavel(){
                fetch("LoteSaidaServlet",{method:"put"})
                        .then(function(){
                            location.reload();
                        });
            }
            
            function cadastrarLoteSaida(){
                fetch("LoteSaidaServlet", {method:"post"})
                        .then(function(response){
                            if(response.status === 500){
                                location.reload();
                    }
                            else{
                                location.href = "lotesaidaapresentacao.jsp";
                            }
                        }).catch(function(erro){
                            location.reload();
                        });
            }
        </script>
        
        <br/>
        <br/>
        
        <center><a href=index.html><button>Página Inicial</button></a></center>
    </body>
</html>
