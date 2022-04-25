/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.controller.servlets;

import br.recife.edu.ifpe.model.classes.Estoque;
import br.recife.edu.ifpe.model.classes.ItemEntrada;
import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoque;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteEntrada;
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
@WebServlet(name = "LoteEntradaServlet", urlPatterns = {"/LoteEntradaServlet"})
public class LoteEntradaServlet extends HttpServlet {


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
        
        LoteEntrada loteEntrada = RepositorioLoteEntrada.getCurrentInstance().read(codigo);
        
        String responseJSON ="{\"codigo\":"+loteEntrada.getCodigo()+","+
                "\"descricao\":\""+loteEntrada.getDescricao()+"\",\"itens\":[";
        for(ItemEntrada item: loteEntrada.getItens()){
            responseJSON += "{\"codigo\":"+item.getCodigo()+",\"nomeProduto\":\""+item.getProduto().getNome()+"\"}";
            if(loteEntrada.getItens().indexOf(item) != loteEntrada.getItens().size()-1){
                responseJSON += ",";
            }
        }
        responseJSON += "]}";
        response.setContentType("text/plain");
        try(PrintWriter out = response.getWriter()){
            out.println(responseJSON);
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
        HttpSession session = request.getSession();
        
        LoteEntrada lE = (LoteEntrada)session.getAttribute("loteEntrada");
        
        for(ItemEntrada i: lE.getItens()){
            if(i.getQuantidade() > 10){
                session.setAttribute("msg", "Erro ao cadastrar lote no repositório! A quantidade do produto " + i.getProduto().getNome()
                + " ultrapassa o limite aceito!");
                response.sendError(500);
                return;
            }
        }
        
        Estoque estoque = RepositorioEstoque.getCurrentInstance().read();
        
        for(ItemEntrada i: lE.getItens()){
            for(ItemEstoque ie: estoque.getItens()){
                if(i.getProduto().getCodigo() == ie.getProduto().getCodigo()){
                    ie.adiciona(i.getQuantidade());
                    break;
                }
            }
        }
        RepositorioLoteEntrada.getCurrentInstance().create(lE);
        
        session.removeAttribute("loteEntrada");
        session.setAttribute("msg", "Lote inserido no repositório com sucesso!");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPut(request, response); //To change body of generated methods, choose Tools | Templates.

        String operacao = request.getParameter("operacao");

        int codigo = Integer.parseInt(request.getParameter("codigo"));

        HttpSession session = request.getSession();

        LoteEntrada lE = (LoteEntrada) session.getAttribute("loteEntrada");

        if (lE == null) {
            lE = new LoteEntrada();
            session.setAttribute("loteEntrada", lE);
        }

        if (operacao.equals("mais")) {

            boolean controle = false;
            for (ItemEntrada i : lE.getItens()) {
                if (i.getProduto().getCodigo() == codigo) {
                    i.setQuantidade(i.getQuantidade() + 1);
                    controle = true;
                    Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
                    session.setAttribute("msg", "Incrementado 1 item de " + p.getNome() + " ao lote!");
                    break;
                }
            }

            if (!controle) {
                ItemEntrada item = new ItemEntrada();
                Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
                item.setProduto(p);
                item.setCodigo((int) (Math.random() * 10000));
                item.setQuantidade(1);
                lE.addItem(item);
                session.setAttribute("msg", "O produto " + p.getNome() + " foi adicionado ao catálogo de produtos!");
            }
        } else if (operacao.equals("menos")) {
            for (ItemEntrada i : lE.getItens()) {
                if (i.getProduto().getCodigo() == codigo) {
                    if (i.getQuantidade() == 1) {
                        lE.getItens().remove(i);
                        if(lE.getItens().size() == 0){
                            session.removeAttribute("loteEntrada");
                        }
                        session.setAttribute("msg", "O produto " + i.getProduto().getNome() + " foi removido do lote!");
                        break;
                    }
                    session.setAttribute("msg", "Foi removida 1 unidade do produto " + i.getProduto().getNome() + " do lote!");
                    i.setQuantidade(i.getQuantidade() - 1);
                    break;
                }
            }
        }
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
