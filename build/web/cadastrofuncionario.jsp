<%-- 
    Document   : cadastrofuncionario
    Created on : 20 de jun de 2021, 01:54:56
    Author     : MaD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro Funcionário</title>
        <style>
            h2{
                text-align: center;
                background-color: black;
                color: white;
            }
        </style>
    </head>
    <body>
        <form method="post" action="CadastroFuncionarioServletJsp">
            <div><h2><em>Formulario de cadastro de funcionario</em></h2></div>
            <table>
                <tr>
                    <td>Informe o codigo do funcionario:</td><td><input type="text" name="codfuncionario" value="${(param.redirect != null && param.redirect eq 'atualiza') ? funcionario.codigo : ''}"/></td>
                </tr>
                <tr>
                    <td>Informe o nome do funcionario:</td><td><input type="text" name="nomefuncionario" value="${(param.redirect != null && param.redirect eq 'atualiza') ? funcionario.nome : ''}"/></td>
                </tr>
                <tr>
                    <td>Informe o departamento do funcionario:</td><td><input type="text" name="depfuncionario" value="${(param.redirect != null && param.redirect eq 'atualiza') ? funcionario.departamento : ''}"/></td>
                </tr>
                
                <input type="hidden" name="${(param.redirect != null && param.redirect eq 'atualiza') ? 'atualizar' : 'cadastrar'}" value="1"/>
                
                <tr>
                    <td><input type="submit" value="${(param.redirect != null && param.redirect eq 'atualiza') ? 'Atualizar' : 'Cadastrar'}"/></td>
                </tr>
            </table>
        </form>
    <center><a href=index.html><button>Página Inicial</button></a></center>
    </body>
</html>
