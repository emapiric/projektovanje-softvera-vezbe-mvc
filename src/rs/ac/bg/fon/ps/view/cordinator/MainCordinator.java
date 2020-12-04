/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.cordinator;

import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.ps.domain.Product;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.controller.InvoiceController;
import rs.ac.bg.fon.ps.view.controller.LoginController;
import rs.ac.bg.fon.ps.view.controller.MainContoller;
import rs.ac.bg.fon.ps.view.controller.ProductController;
import rs.ac.bg.fon.ps.view.controller.ProductViewAllController;
import rs.ac.bg.fon.ps.view.form.FrmInvoice;
import rs.ac.bg.fon.ps.view.form.FrmLogin;
import rs.ac.bg.fon.ps.view.form.FrmMain;
import rs.ac.bg.fon.ps.view.form.FrmProduct;
import rs.ac.bg.fon.ps.view.form.FrmViewProducts;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author laptop-02
 */
public class MainCordinator {

    private static MainCordinator instance;

    private final MainContoller mainContoller;
    
    //this can be in some other Singleton class
    private final Map<String, Object> params;

    private MainCordinator() {
        mainContoller = new MainContoller(new FrmMain());
        params = new HashMap<>();
    }

    public static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }
     public void openLoginForm() {
        LoginController loginContoller = new LoginController(new FrmLogin());
        loginContoller.openForm();
    }

    public void openMainForm() {
        mainContoller.openForm();
    }

    public void openAddNewProductForm() {
        ProductController productController = new ProductController(new FrmProduct(mainContoller.getFrmMain(), true));
        productController.openForm(FormMode.FORM_ADD);
    }

    public void openViewAllProductForm() {
        FrmViewProducts form = new FrmViewProducts(mainContoller.getFrmMain(), true);
        
        ProductViewAllController productViewAllController = new ProductViewAllController(form);
        productViewAllController.openForm();
    }

    public void openProductDetailsProductForm() {
        FrmProduct productDetails = new FrmProduct(mainContoller.getFrmMain(), true);
        ProductController productController = new ProductController(productDetails);
        productController.openForm(FormMode.FORM_VIEW);
        params.put(Constants.PARAM_PRODUCT,productDetails);
    }

    public MainContoller getMainContoller() {
        return mainContoller;
    }


    public void addParam(String name, Object key) {
        params.put(name, key);
    }

    public Object getParam(String name) {
        return params.get(name);
    }

    public void openAddNewInvoiceForm() {
        InvoiceController invoiceController = new InvoiceController(new FrmInvoice(mainContoller.getFrmMain(), true));
        invoiceController.openForm(FormMode.FORM_ADD);
    }
   
}
