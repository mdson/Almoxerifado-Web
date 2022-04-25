<%-- 
    Document   : loteentradaapresentacao
    Created on : 9 de ago de 2021, 18:20:54
    Author     : MaD
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ifpe" uri="/WEB-INF/tlds/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lotes de Saída Cadastrados no Repositório</title>
        <style>
            h2{
                text-align: center;
                background-color: black;
                color: white;
            }
            
            .modal{
                background-color: aquamarine;
                position: absolute;
                top: 200px;
                left: 200px;
                border: 1px black solid;
            }
        </style>
    </head>
    <body>
        <h2>Lotes Cadastrados</h2>
        <ifpe:carregalotesaida/>
        
        <br/>
        <br/>
        
        <table border="2" CELLPADDING=6 rules="all">
            <CAPTION>Lista de lotes de saída cadastrados por data</CAPTION>
            <tr>
                <th>Data de cadastro</th><th>Código</th><th>Quantidade de saída de produtos</th><th>Responsável</th>
            </tr>
            <c:forEach var="item" items="${lotesSaidaInseridos}">
                <tr>
                    <td>
                        <center>${item.data}</center>
                    </td>
                    <td>
                        <center>${item.codigo}</center>
                    </td>
                    <td>
                        <center></center>
                    </td>
                    <td>
                        <center><c:out value="${item.responsavel.nome}"/></center>
                    </td>
                    <td>
                        &emsp;&emsp;<a href="#" onclick="carregaItens(${item.codigo})"><button>Visualizar Lote</button></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <br/>
        <br/>
        
        <script>
            var meuDiv;
            function carregaItens(codigo){ 
                fetch("LoteSaidaServlet?codigo="+codigo,{method:"get"}).then(function(response){
                    response.text().then(function(text){
                        let objeto = JSON.parse(text);
                        
                        meuDiv = document.createElement("div");
                        meuDiv.setAttribute("class","modal");
                        document.body.appendChild(meuDiv);
                        meuDiv.innerHTML = objeto.codigo+"<br/>"+objeto.descricao+"<br/>";
                        
                        let tabela = document.createElement("table");
                        tabela.setAttribute("border","1");
                        
                        meuDiv.appendChild(tabela);
                        
                        for(let i = 0; i < objeto.itens.length; i++){
                            let tr = document.createElement("tr");
                            
                            let td1 = document.createElement("td");
                            td1.innerHTML = objeto.itens[i].codigo;
                            
                            let td2 = document.createElement("td");
                            td2.innerHTML = objeto.itens[i].nomeProduto;
                            
                            tr.appendChild(td1);
                            tr.appendChild(td2);
                            tabela.appendChild(tr);
                        }
                        let botao = document.createElement("button");
                        botao.appendChild(document.createTextNode("Fechar"));
                        
                        meuDiv.appendChild(botao);
                        
                        botao.addEventListener("click", function(){
                            document.body.removeChild(meuDiv);
                            meuDiv = "";
                        });
                    });
                });
            }
        </script>
        
        <center><a href=index.html><button>Página Inicial</button></a></center>
    </body>
</html>
