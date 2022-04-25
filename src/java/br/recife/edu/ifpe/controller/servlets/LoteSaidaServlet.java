/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.controller.servlets;

import br.recife.edu.ifpe.model.classes.Estoque;
import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.classes.ItemEntrada;
import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.ItemSaida;
import br.recife.edu.ifpe.model.classes.LoteSaida;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoque;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteSaida;
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
@WebServlet(name = "LoteSaidaServlet", urlPatterns = {"/LoteSaidaServlet"})
public class LoteSaidaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        
        LoteSaida loteSaida = RepositorioLoteSaida.getCurrentInstance().read(codigo);
        
        String responseJSON ="{\"codigo\":"+loteSaida.getCodigo()+","+
                "\"descricao\":\""+loteSaida.getDescricao()+"\",\"itens\":[";
        for(ItemSaida item: loteSaida.getItens()){
            responseJSON += "{\"codigo\":"+item.getCodigo()+",\"nomeProduto\":\""+item.getProduto().getNome()+"\"}";
            if(loteSaida.getItens().indexOf(item) != loteSaida.getItens().size()-1){
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

        LoteSaida lS = (LoteSaida) session.getAttribute("loteSaida");
        
        Estoque estoque = RepositorioEstoque.getCurrentInstance().read();

        for (ItemSaida i : lS.getItens()) {
            for (ItemEstoque is : estoque.getItens()) {
                if (i.getProduto().getCodigo() == is.getProduto().getCodigo() && i.getQuantidade() <= is.getQuantidade()) {
                    is.subtrai(i.getQuantidade());
                    break;
                } else {
                    session.setAttribute("msg", "Erro ao cadastrar lote de saída! A quantidade de itens de saída do produto " + i.getProduto().getNome()
                            + " é maior que a quantidade presente no estoque!");
                    response.sendError(500);
                    return;
                }
            }
        }
        RepositorioLoteSaida.getCurrentInstance().create(lS);
        
        session.removeAttribute("loteSaida");
        session.setAttribute("msg", "Lote de saída criado com sucesso!");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPut(request, response); //To change body of generated methods, choose Tools | Templates.
        
        HttpSession session = request.getSession();
        
        String operacao = request.getParameter("operacao");

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        
        LoteSaida lS = (LoteSaida) session.getAttribute("loteSaida");

        if (lS == null) {
            lS = new LoteSaida();
            session.setAttribute("loteSaida", lS);
        }

        if (operacao.equals("mais")) {

            boolean controle = false;
            for (ItemSaida i : lS.getItens()) {
                if (i.getProduto().getCodigo() == codigo) {
                    i.setQuantidade(i.getQuantidade() + 1);
                    controle = true;
                    Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
                    session.setAttribute("msg", "Incrementado 1 item de " + p.getNome() + " ao lote de saída!");
                    break;
                }
            }

            if (!controle) {
                ItemSaida item = new ItemSaida();
                Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
                item.setProduto(p);
                item.setCodigo((int) (Math.random() * 10000));
                item.setQuantidade(1);
                lS.addItem(item);
                session.setAttribute("msg", "O produto " + p.getNome() + " foi adicionado ao lote de saída!");
            }
        } else if (operacao.equals("menos")) {
            for (ItemSaida i : lS.getItens()) {
                if (i.getProduto().getCodigo() == codigo) {
                    if (i.getQuantidade() == 1) {
                        lS.getItens().remove(i);
                        if(lS.getItens().size() == 0){
                            session.removeAttribute("loteSaida");
                        }
                        session.setAttribute("msg", "O produto " + i.getProduto().getNome() + " foi removido do lote!");
                        break;
                    }
                    session.setAttribute("msg", "Foi removida 1 unidade do produto " + i.getProduto().getNome() + " do lote de saída!");
                    i.setQuantidade(i.getQuantidade() - 1);
                    break;
                }
            }
        }
        
        Funcionario func = (Funcionario) session.getAttribute("funcionarios");
        lS.setResponsavel(func);
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
