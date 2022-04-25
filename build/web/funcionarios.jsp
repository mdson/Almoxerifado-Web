<%-- 
    Document   : funcionarios
    Created on : 21 de jun de 2021, 17:00:43
    Author     : MaD
--%>

<%@page import="br.recife.edu.ifpe.model.repositorios.RepositorioFuncionario"%>
<%@page import="br.recife.edu.ifpe.model.classes.Funcionario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Funcionários Cadastrados</title>
    </head>
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
    <body>

        <h2>Funcionários cadastrados</h2>
        <br/>
        <%
            String mensagem = (String)session.getAttribute("msg");
            if (mensagem != null) {
                out.println("<strong>Cadastro do funcionário:</strong> " + mensagem);
                session.removeAttribute("msg");
            }
        %>
        <br/>
        <br/>

        <table>
            <tr> 
                <td id="criarfunc"><button onclick="modalopen()">Novo Funcionário</button></td>
            </tr>
        </table>

        <div id="modal" style="position: absolute; top: 200px; left: 200px; border: 1px black solid">
            <br/>
            <%@include file="cadastrofuncionario.jsp"%>
            <button onclick="modalclose()">Fechar</button>
        </div>

        <div id="modal2" style="position: absolute; top: 200px; left: 200px; border: 1px black solid">
            <br/>
            <%@include file="visualizarfuncionario.jsp"%>
            <button onclick="modal2close()">Fechar</button>
        </div>

        <%
            List<Funcionario> funcionarios = RepositorioFuncionario.getCurrentInstance().readAll();
        %>

        <h3>Busca de funcionario:</h3>
        <br/>
        <%
            for (Funcionario f : funcionarios) {
        %>
        <strong>Funcionario encontrado:</strong> Código: <%= f.getCodigo() %><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: <%= f.getNome() %><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Departamento: <%= f.getDepartamento() %>&emsp;&emsp;
        <a href="CadastroFuncionarioServletJsp?codigo=<%= f.getCodigo() %>&redirect=visualiza"><button>Visualizar Funcionário</button></a>
        &emsp;&emsp;<a href="CadastroFuncionarioServletJsp?codigo=<%= f.getCodigo() %>&redirect=atualiza"><button>Atualizar Cadastro</button></a>
        &emsp;&emsp;<a href="#" onclick="deleteFunc(<%= f.getCodigo() %>)"><button>Excluir Cadastro</button></a>
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
            
            function deleteFunc(codigo) {
                fetch("CadastroFuncionarioServletJsp?codigo="+codigo,{method:'delete'})
                        .then(function(response){
                            location.reload();
                });
            }
        </script>
        <center><a href=index.html><button>Página Inicial</button></a></center>
    </body>
</html>
