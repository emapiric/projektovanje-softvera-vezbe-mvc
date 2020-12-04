/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controller;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Invoice;
import rs.ac.bg.fon.ps.domain.Manufacturer;
import rs.ac.bg.fon.ps.domain.Product;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBInvoice;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBManufacturer;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBProduct;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDbUser;

/**
 *
 * @author laptop-02
 */
public class Controller {

    private final Repository repositoryUser;
    private final Repository repositoryManufacturer;
    private final Repository repositoryProduct;
    private final Repository repositoryInvoice;
    
    private static Controller controller;
    
    private Controller() {
        this.repositoryUser = new RepositoryDbUser();
        this.repositoryManufacturer = new RepositoryDBManufacturer();
        this.repositoryProduct = new RepositoryDBProduct();
        this.repositoryInvoice = new RepositoryDBInvoice();
    }
      
    public static Controller getInstance() {
        if (controller==null) controller = new Controller();
        return controller;
    }

    public User login(String username, String password) throws  Exception{
        List<User> users = repositoryUser.getAll();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        //return null;
        throw new Exception("Unknown user!");
    }
    public List<Manufacturer> getAllManufactures(){
        return repositoryManufacturer.getAll();
    }
    
  public void addProduct(Product product) throws Exception{
        ((DbRepository)repositoryProduct).connect();
        try{
            repositoryProduct.add(product);
            ((DbRepository)repositoryProduct).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)repositoryProduct).rollback();
            throw e;
        }finally{
            ((DbRepository)repositoryProduct).disconnect();
        }
    }
    
    public List<Product> getAllProducts() throws Exception{
        List<Product> products=null;
        ((DbRepository)repositoryProduct).connect();
        try{
            products = repositoryProduct.getAll();
            ((DbRepository)repositoryProduct).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)repositoryProduct).rollback();
            throw e;
        }finally{
            ((DbRepository)repositoryProduct).disconnect();
        }
        return products;
    }
    
    public void deleteProduct(Product product) throws Exception {
        ((DbRepository)repositoryProduct).connect();
        try{
            repositoryProduct.delete(product);
            ((DbRepository)repositoryProduct).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)repositoryProduct).rollback();
            throw e;
        }finally{
            ((DbRepository)repositoryProduct).disconnect();
        }
    }
    
    public void editProduct(Product product) throws Exception {
        ((DbRepository)repositoryProduct).connect();
        try{
            ((DbRepository)repositoryProduct).edit(product);
            ((DbRepository)repositoryProduct).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)repositoryProduct).rollback();
            throw e;
        }finally{
            ((DbRepository)repositoryProduct).disconnect();
        }
    }

    public void saveInvoice(Invoice invoice) throws Exception {
        ((DbRepository)repositoryInvoice).connect();
        try{
            ((DbRepository)repositoryInvoice).edit(invoice);
            ((DbRepository)repositoryInvoice).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)repositoryInvoice).rollback();
            throw e;
        }finally{
            ((DbRepository)repositoryInvoice).disconnect();
        }
    }
}
