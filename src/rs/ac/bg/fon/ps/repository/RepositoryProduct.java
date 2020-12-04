/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Product;

/**
 *
 * @author laptop-02
 */
public class RepositoryProduct {
   private final List<Product> products;

    public RepositoryProduct() {
        products = new ArrayList<>();
    }
   
    public void add(Product product){
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void delete(Product product) throws Exception {
        int index = products.indexOf(product);
        if(index>=0){
             products.remove(index);
        }else{
            throw new Exception("Eroor: Product does not exist!");
        }
       
    }
    
}
