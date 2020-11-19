/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import observer.Observador;

/**
 *
 * @author camil
 */
public interface Observado {
    
    void criarProduto(String descricao, double preco);
    void addObserver(Observador obs);
}
