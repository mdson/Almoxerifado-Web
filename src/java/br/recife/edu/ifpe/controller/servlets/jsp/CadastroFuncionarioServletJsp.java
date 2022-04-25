/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.controller.servlets.jsp;

import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.repositorios.RepositorioFuncionario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MaD
 */
@WebServlet(name = "CadastroFuncionarioServletJsp", urlPatterns = {"/CadastroFuncionarioServletJsp"})
public class CadastroFuncionarioServletJsp extends HttpServlet {

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
        
        int codigo = Integer.parseInt(request.getParameter("codigo"));

        Funcionario f = RepositorioFuncionario.getCurrentInstance().read(codigo);
        
        request.setAttribute("funcionario", f);
        
        getServletContext().getRequestDispatcher("/funcionarios.jsp").forward(request, response);
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
        String att = request.getParameter("atualizar");
        
        Funcionario f = new Funcionario();
        
        f.setCodigo(codfuncionario);
        f.setNome(nomefuncionario);
        f.setDepartamento(depfuncionario);
        
        HttpSession session = request.getSession();

        if(att == null){
            RepositorioFuncionario.getCurrentInstance().create(f);
            session.setAttribute("msg", "" + f.getNome() + ", REALIZADO COM SUCESSO!");
        } else {
            RepositorioFuncionario.getCurrentInstance().update(f);
            session.setAttribute("msg", "" + f.getNome() + ", ATUALIZADO COM SUCESSO!");
        }
        
        response.sendRedirect("funcionarios.jsp");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doDelete(request, response); //To change body of generated methods, choose Tools | Templates.
        
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        Funcionario f = RepositorioFuncionario.getCurrentInstance().read(codigo);
        RepositorioFuncionario.getCurrentInstance().delete(f);
        
        HttpSession session = request.getSession();
        
        session.setAttribute("msg", "" + f.getNome() + ", DELETADO COM SUCESSO!");
    }
    
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
