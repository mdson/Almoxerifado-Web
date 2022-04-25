<%-- 
    Document   : visualizarfuncionario
    Created on : 23 de jun de 2021, 21:03:14
    Author     : MaD
--%>

<%@page import="br.recife.edu.ifpe.model.classes.Funcionario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Visualizar Funcionário</title>
    </head>
    <body>
        <h2>Ficha Funcionário</h2>
        <%
            Funcionario funcionario = (Funcionario)request.getAttribute("funcionario");
            if (funcionario != null) {
        %>
        <hr>
        <strong>Funcionario encontrado:</strong> Código: <%=funcionario.getCodigo()%><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: <%=funcionario.getNome()%><br/>
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Departamento: <%=funcionario.getDepartamento()%><br/>
        <hr>
        <% }%>
    </body>
</html>
