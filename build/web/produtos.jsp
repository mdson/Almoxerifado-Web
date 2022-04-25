<%-- 
    Document   : produtos
    Created on : 13 de jul de 2021, 03:07:12
    Author     : MaD
--%>

<%@page import="br.recife.edu.ifpe.model.repositorios.RepositorioProdutos"%>
<%@page import="br.recife.edu.ifpe.model.classes.Produto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Produtos Cadastrados</title>
        <style>
        h2{
            text-align: center;
            background-color: black;
            color: white;
        }
        #criarfunc{
            color: white;
            background-color: black;
            border:5px outset black;
        }
        #modal{
            background-color: aquamarine;
        }
        #modal2{
            background-color: aquamarine;
        }
    </style>
    </head>
    <body>
        <h2>Produtos Cadastrados</h2>
        <%
            String mensagem = (String)session.getAttribute("msgProduto");
            if (mensagem != null) {
                out.println("<strong>Cadastro do produto:</strong> " + mensagem);
                session.removeAttribute("msgProduto");
            }
        %>
        <br/>
        <br/>
        
        <table>
            <tr> 
                <td id="criarfunc"><button onclick="modalopen()">Novo Produto</button></td>
            </tr>
        </table>

        <div id="modal" style="position: absolute; top: 200px; left: 200px; border: 1px black solid">
            <br/>
            <%@include file="cadastroproduto.jsp"%>
            <button onclick="modalclose()">Fechar</button>
        </div>

        <div id="modal2" style="position: absolute; top: 200px; left: 200px; border: 1px black solid">
            <br/>
            <%@include file="visualizarproduto.jsp"%>
            <button onclick="modal2close()">Fechar</button>
        </div>

        <%
            List<Produto> produtos = RepositorioProdutos.getCurrentInstance().readAll();
        %>

        <h3>Busca de produtos:</h3>
        <br/>
        <%
            for (Produto p : produtos) {
        %>
        <strong>Produto encontrado:</strong> Código: <%= p.getCodigo() %><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: <%= p.getNome() %><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Marca: <%= p.getMarca()%><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Categoria: <%= p.getCategoria()%>&emsp;&emsp;
        <a href="CadastroProdutoServletJsp?codigo=<%= p.getCodigo() %>&redirect=visualiza"><button>Visualizar Produto</button></a>
        &emsp;&emsp;<a href="CadastroProdutoServletJsp?codigo=<%= p.getCodigo() %>&redirect=atualiza"><button>Atualizar Cadastro</button></a>
        &emsp;&emsp;<a href="#" onclick="deleteProduto(<%= p.getCodigo() %>)"><button>Excluir Cadastro</button></a>
        <br/>
        <br/>
        <hr>
        <br/>
        <%
            }
        %>

        <script>
            var modal = document.getElementById("modal");
            var modal2 = document.getElementById("modal2");

            <%
                String redirect = request.getParameter("redirect");
                if(redirect == null) {
            %>
                
            document.body.removeChild(modal);
            document.body.removeChild(modal2);

            <% } else if(redirect.equals("visualiza")) { %>
            document.body.removeChild(modal);
            <% } else { %>
            document.body.removeChild(modal2);
            <% }%>
                
            function modalclose() {
                document.body.removeChild(modal);
            }

            function modal2close() {
                document.body.removeChild(modal2);
            }

            function modalopen() {
                document.body.appendChild(modal);
            }
            
            function deleteProduto(codigo) {
                fetch("CadastroProdutoServletJsp?codigo="+codigo,{method:'delete'})
                        .then(function(response){
                            location.reload();
                });
            }
        </script>
        <center><a href=index.html><button>Página Inicial</button></a></center>
    </body>
</html>
