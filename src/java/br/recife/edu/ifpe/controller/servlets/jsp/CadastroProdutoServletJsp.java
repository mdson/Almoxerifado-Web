/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.controller.servlets.jsp;

import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoque;
import br.recife.edu.ifpe.model.repositorios.RepositorioProdutos;
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
@WebServlet(name = "CadastroProdutoServletJsp", urlPatterns = {"/CadastroProdutoServletJsp"})
public class CadastroProdutoServletJsp extends HttpServlet {


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
        
        Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
        
        request.setAttribute("produto", p);
        
        getServletContext().getRequestDispatcher("/produtos.jsp").forward(request, response);
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
        
        int codproduto = Integer.parseInt(request.getParameter("codproduto"));
        String nomeproduto = request.getParameter("nomeproduto");
        String marcaproduto = request.getParameter("marcaproduto");
        String catproduto = request.getParameter("catproduto");
        String descproduto = request.getParameter("descproduto");
        
        String atualiza = request.getParameter("atualizar");
        
        Produto p = new Produto();
        
        p.setCodigo(codproduto);
        p.setNome(nomeproduto);
        p.setMarca(marcaproduto);
        p.setCategoria(catproduto);
        p.setDescricao(descproduto);
        
        HttpSession session = request.getSession();
        
        if(atualiza == null){
        RepositorioProdutos.getCurrentInstance().create(p);
        
        ItemEstoque item = new ItemEstoque();
        item.setProduto(p);
        item.setQuantidade(0);
        item.setCodigo(p.getCodigo());
        
        RepositorioEstoque.getCurrentInstance().read().addItem(item);
        
        session.setAttribute("msgProduto", "" + p.getNome() + ", REALIZADO COM SUCESSO!");
        
        } else {
            RepositorioProdutos.getCurrentInstance().update(p);
            session.setAttribute("msgProduto", "" + p.getNome() + ", ATUALIZADO COM SUCESSO!");
        }
        response.sendRedirect("produtos.jsp");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doDelete(request, response); //To change body of generated methods, choose Tools | Templates.
        
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        
        Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
        
        RepositorioProdutos.getCurrentInstance().delete(p);
        
        HttpSession session = request.getSession();
        
        session.setAttribute("msgProduto", "" + p.getNome() + ", DELETADO COM SUCESSO!");
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
