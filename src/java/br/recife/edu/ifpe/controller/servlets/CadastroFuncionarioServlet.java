/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.controller.servlets;

import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.repositorios.RepositorioFuncionario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(name = "CadastroFuncionarioServlet", urlPatterns = {"/CadastroFuncionarioServlet"})
public class CadastroFuncionarioServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String atualizar = request.getParameter("atualizar");
        String deletar = request.getParameter("deletar");
        String codAux = request.getParameter("codigo");
        
        if(deletar != null){
            int codigo = Integer.parseInt(codAux);
            Funcionario f = RepositorioFuncionario.getCurrentInstance().read(codigo);
            RepositorioFuncionario.getCurrentInstance().delete(f);
            response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Exclusão de cadastro de funcionário</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Confirmação de exclusão de cadastro:</h2>");
            out.println("</br>");
            out.println("<strong>Cadastro do funcionario excluído:</strong>&nbsp;&nbsp;Código: " + f.getCodigo() + "</br>"
                    + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: " + f.getNome() + "</br>"
                    + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Departamento: "
                    + f.getDepartamento() + "</br></br>");
            out.println("</br>");
            out.println("<center><a href=\"index.html\"><button>Pagina Inicial</button></a></center>");
            out.println("</body>");
            out.println("</html>");
        }
        }
        if (atualizar != null) {
            int codigo = Integer.parseInt(codAux);
            Funcionario f = RepositorioFuncionario.getCurrentInstance().read(codigo);
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet CadastroFuncionarioServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Modificar cadastro:</h2>");
                out.println("</br>");
                out.println("<form method=\"post\" action=\"CadastroFuncionarioServlet\"><table>\n"
                        + "                <tr>\n"
                        + "                    <td>Informe o codigo do funcionario:</td><td><input type=\"hidden\" name=\"codfuncionario\"value=\""+f.getCodigo()+"\"></td>\n"
                        + "                </tr>\n"
                        + "                <tr>\n"
                        + "                    <td>Informe o nome do funcionario:</td><td><input type=\"text\" name=\"nomefuncionario\"value=\""+f.getNome()+"\"></td>\n"
                        + "                </tr>\n"
                        + "                <tr>\n"
                        + "                    <td>Informe o departamento do funcionario:</td><td><input type=\"text\" name=\"depfuncionario\"value=\""+f.getDepartamento()+"\"></td>\n"
                        + "                </tr>\n"
                        + "                <input type='hidden' name='atualizar' value='1'>" 
                        + "                <tr>\n"
                        + "                    <td><input type=\"submit\" value=\"Atualizar\"></td>\n"
                        + "                </tr>\n"
                        + "            </table>\n"
                        + "        </form>");
                out.println("</br>");
                out.println("<center><a href=\"CadastroFuncionarioServlet\"><button>Retornar</button></a></center>");
                out.println("</body>");
                out.println("</html>");
            }
        }

        if (codAux == null && atualizar == null && deletar == null) {
            List<Funcionario> funcionarios = RepositorioFuncionario.getCurrentInstance().readAll();
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet CadastroFuncionarioServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Busca de funcionario:</h2>");
                out.println("</br>");
                for (Funcionario f : funcionarios) {
                    out.println("<strong>Funcionario encontrado:</strong> Código: " + f.getCodigo() + "</br>"
                            + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: " + f.getNome() + "</br>"
                            + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Departamento: "
                            + f.getDepartamento() + "&emsp;&emsp;" + "<a href='CadastroFuncionarioServlet?codigo=" + f.getCodigo() + "&atualizar=1'><button>Modificar cadastro</button></a>" + 
                                    "&emsp;&emsp;" + "<a href='CadastroFuncionarioServlet?codigo=" + f.getCodigo() + "&deletar=1'><button>Excluir cadastro</button></a>" +"</br></br>");
                    out.println("<hr>");
                    out.println("</br>");
                }
                out.println("<center><a href=\"index.html\"><button>Pagina Inicial</button></a></center>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            int codigo = Integer.parseInt(codAux);
            Funcionario f = RepositorioFuncionario.getCurrentInstance().read(codigo);

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet CadastroFuncionarioServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Busca de funcionario:</h2>");
                out.println("</br>");
                out.println("<strong>Funcionario encontrado:</strong> Código: " + f.getCodigo() + "</br>"
                        + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: " + f.getNome() + "</br>"
                        + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Departamento: "
                        + f.getDepartamento() + "</br></br>");
                out.println("</br>");
                out.println("<center><a href=\"index.html\"><button>Pagina Inicial</button></a></center>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codfuncionario = Integer.parseInt(request.getParameter("codfuncionario"));
        String nomefuncionario = request.getParameter("nomefuncionario");
        String depfuncionario = request.getParameter("depfuncionario");
        
        String atualizar = request.getParameter("atualizar");

        Funcionario f = new Funcionario();
        f.setCodigo(codfuncionario);
        f.setNome(nomefuncionario);
        f.setDepartamento(depfuncionario);
        
        if(atualizar != null){
            RepositorioFuncionario.getCurrentInstance().update(f);
        }else{
        RepositorioFuncionario.getCurrentInstance().create(f);
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CadastroFuncionarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Confirmação de cadastro:</h2>");
            out.println("</br>");
            out.println("<strong>Cadastro do funcionario:</strong> Código: " + codfuncionario + "</br>"
                    + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Nome: " + nomefuncionario + "</br>"
                    + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Departamento: "
                    + depfuncionario + "</br></br>");
            out.println("</br>");
            out.println("<center><a href=\"index.html\"><button>Pagina Inicial</button></a></center>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
