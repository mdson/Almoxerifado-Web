<%-- 
    Document   : cadastroproduto
    Created on : 13 de jul de 2021, 01:57:44
    Author     : MaD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro Produto</title>
        <style>
            h2{
                text-align: center;
                background-color: black;
                color: white;
            }
        </style>
    </head>
    <body>
        <form method="post" action="CadastroProdutoServletJsp">
            <div><h2><em>Formulario de cadastro de produto</em></h2></div>
            <table>
                <tr>
                    <td>Informe o codigo do produto:</td><td><input type="text" name="codproduto" value="${(param.redirect != null && param["redirect"] eq 'atualiza') ? produto.codigo : ''}"/></td>
                </tr>
                <tr>
                    <td>Informe o nome do produto:</td><td><input type="text" name="nomeproduto" value="${(param.redirect != null && param["redirect"] eq 'atualiza') ? produto.nome : ''}"/></td>
                </tr>
                <tr>
                    <td>Informe a marca do produto:</td><td><input type="text" name="marcaproduto" value="${(param.redirect != null && param["redirect"] eq 'atualiza') ? produto.marca : ''}"/></td>
                </tr>
                <tr>
                    <td>Informe a categoria do produto:</td><td><input type="text" name="catproduto" value="${(param.redirect != null && param["redirect"] eq 'atualiza') ? produto.categoria : ''}"/></td>
                </tr>
                <tr>
                    <td>Informe a descrição do produto:</td><td><textarea name="descproduto">${(param.redirect != null && param["redirect"] eq 'atualiza') ? produto.descricao : ''}</textarea>/</td>
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
