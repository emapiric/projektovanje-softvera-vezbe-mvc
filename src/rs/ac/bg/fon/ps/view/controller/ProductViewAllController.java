/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Product;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewProducts;
import rs.ac.bg.fon.ps.view.form.componenet.table.ProductTableModel;

/**
 *
 * @author laptop-02
 */
public class ProductViewAllController {

    private final FrmViewProducts frmViewProducts;

    public ProductViewAllController(FrmViewProducts frmViewProducts) {
        this.frmViewProducts = frmViewProducts;
        addActionListener();
    }

    private void addActionListener() {
        frmViewProducts.getBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewProducts.getTblProducts().getSelectedRow();
                if (row >= 0) {
                    Product product = ((ProductTableModel) frmViewProducts.getTblProducts().getModel()).getProductAt(row);
                    MainCordinator.getInstance().addParam(Constants.PARAM_PRODUCT, product);
                    MainCordinator.getInstance().openProductDetailsProductForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewProducts, "You must select a product", "PRODUCT DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmViewProducts.addWindowListener(new WindowAdapter(){
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblProducts();
            }
        });

    }

    public void openForm() {
        frmViewProducts.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        frmViewProducts.setVisible(true);
    }

    private void prepareView() {
        frmViewProducts.setTitle("View products");
        //fillTblProducts();
    }

    private void fillTblProducts() {
        List<Product> products;
        try {
            products = Controller.getInstance().getAllProducts();
            ProductTableModel ptm = new ProductTableModel(products);
            frmViewProducts.getTblProducts().setModel(ptm);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewProducts, "Error: " + ex.getMessage(), "ERROR DETAILS", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ProductViewAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        fillTblProducts();
    }

}
