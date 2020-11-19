/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.List;
import model.Produto;
import observer.Observador;

/**
 *
 * @author camil
 */
public class Controle implements Observado{
    
        List <Observador> observadores = new ArrayList<>();
    
       public void criarProduto(String descricao, double preco){
           
           Produto produto = new Produto(descricao, preco);
           for(Observador obs:observadores){
               obs.notificarCriacaoProduto(descricao, preco); 
           }
                     
       }
       
       public void addObserver(Observador obs){
           observadores.add(obs);
       }
       public void removeObserver(Observador obs){
           observadores.remove(obs);
       }
}
