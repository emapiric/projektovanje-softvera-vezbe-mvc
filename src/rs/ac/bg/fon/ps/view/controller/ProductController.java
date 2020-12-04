/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Manufacturer;
import rs.ac.bg.fon.ps.domain.MeasurementUnit;
import rs.ac.bg.fon.ps.domain.Product;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmProduct;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author laptop-02
 */
public class ProductController {

    private final FrmProduct frmProduct;

    public ProductController(FrmProduct frmProduct) {
        this.frmProduct = frmProduct;
        addActionListeners();
    }

    private void addActionListeners() {
        frmProduct.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }

            private void save() {
                try {
                    Product product = new Product();
                    product.setId(Long.parseLong(frmProduct.getTxtID().getText().trim()));
                    product.setName(frmProduct.getTxtName().getText().trim());
                    product.setDescription(frmProduct.getTxtDescription().getText().trim());
                    product.setPrice(new BigDecimal(frmProduct.getTxtPrice().getText().trim()));
                    product.setMeasurementUnit((MeasurementUnit) frmProduct.getCbMeasurementUnit().getSelectedItem());
                    product.setManufacturer((Manufacturer) frmProduct.getCbManufacturer().getSelectedItem());

                    Controller.getInstance().addProduct(product);
                    JOptionPane.showMessageDialog(frmProduct, "Product successfully saves");
                    frmProduct.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmProduct.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmProduct, ex.getMessage());
                }
            }
        });

        frmProduct.addEnableChangesBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmProduct.addCancelBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmProduct.dispose();
            }
        });

        frmProduct.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                Product product = makeProductFromForm();
                try {
                    Controller.getInstance().deleteProduct(product);
                    JOptionPane.showMessageDialog(frmProduct, "Product deleted successfully!\n", "Delete product", JOptionPane.INFORMATION_MESSAGE);
                    frmProduct.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct, "Error deleting product!\n" + ex.getMessage(), "Delete product", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frmProduct.addEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit();
            }

            private void edit() {
                Product product = makeProductFromForm();
                try {
                    Controller.getInstance().editProduct(product);
                    JOptionPane.showMessageDialog(frmProduct, "Product changed successfully!\n", "Edit product", JOptionPane.INFORMATION_MESSAGE);
                    frmProduct.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct, "Error editting product!\n" + ex.getMessage(), "Edit product", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void openForm(FormMode formMode) {
        frmProduct.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView(formMode);
        frmProduct.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        fillCbMeasurementUnit();
        fillCbManufacturer();
        setupComponents(formMode);
    }

    private void fillCbMeasurementUnit() {
        frmProduct.getCbMeasurementUnit().removeAllItems();
        for (MeasurementUnit unit : MeasurementUnit.values()) {
            frmProduct.getCbMeasurementUnit().addItem(unit);
        }
    }

    private void fillCbManufacturer() {
        frmProduct.getCbManufacturer().removeAllItems();
        List<Manufacturer> manufacturers = Controller.getInstance().getAllManufactures();
        frmProduct.getCbManufacturer().setModel(new DefaultComboBoxModel<>(manufacturers.toArray()));
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmProduct.getBtnCancel().setEnabled(true);
                frmProduct.getBtnDelete().setEnabled(false);
                frmProduct.getBtnEdit().setEnabled(false);
                frmProduct.getBtnEnableChanges().setEnabled(false);
                frmProduct.getBtnSave().setEnabled(true);

                frmProduct.getTxtID().setEnabled(true);
                frmProduct.getTxtName().setEnabled(true);
                frmProduct.getTxtDescription().setEnabled(true);
                frmProduct.getTxtPrice().setEnabled(true);
                frmProduct.getCbManufacturer().setEnabled(true);
                frmProduct.getCbMeasurementUnit().setEnabled(true);
                break;
            case FORM_VIEW:
                frmProduct.getBtnCancel().setEnabled(true);
                frmProduct.getBtnDelete().setEnabled(true);
                frmProduct.getBtnEdit().setEnabled(false);
                frmProduct.getBtnEnableChanges().setEnabled(true);
                frmProduct.getBtnSave().setEnabled(false);

                //zabrani izmenu vrednosti
                frmProduct.getTxtID().setEnabled(false);
                frmProduct.getTxtName().setEnabled(false);
                frmProduct.getTxtDescription().setEnabled(false);
                frmProduct.getTxtPrice().setEnabled(false);
                frmProduct.getCbManufacturer().setEnabled(false);
                frmProduct.getCbMeasurementUnit().setEnabled(false);

                //get product
                Product product = (Product) MainCordinator.getInstance().getParam(Constants.PARAM_PRODUCT);
                frmProduct.getTxtID().setText(product.getId() + "");
                frmProduct.getTxtName().setText(product.getName());
                frmProduct.getTxtDescription().setText(product.getDescription());
                frmProduct.getTxtPrice().setText(String.valueOf(product.getPrice()));
                frmProduct.getCbMeasurementUnit().setSelectedItem(MeasurementUnit.valueOf(product.getMeasurementUnit().toString()));
                frmProduct.getCbManufacturer().setSelectedItem(product.getManufacturer());
                break;
            case FORM_EDIT:
                frmProduct.getBtnCancel().setEnabled(true);
                frmProduct.getBtnDelete().setEnabled(false);
                frmProduct.getBtnEdit().setEnabled(true);
                frmProduct.getBtnEnableChanges().setEnabled(false);
                frmProduct.getBtnSave().setEnabled(false);

                //zabrani izmenu vrednosti
                frmProduct.getTxtID().setEnabled(false);
                frmProduct.getTxtName().setEnabled(true);
                frmProduct.getTxtDescription().setEnabled(true);
                frmProduct.getTxtPrice().setEnabled(true);
                frmProduct.getCbManufacturer().setEnabled(true);
                frmProduct.getCbMeasurementUnit().setEnabled(true);
                break;
        }
    }

    private Product makeProductFromForm() {
        Product product = new Product();
        product.setId(Long.parseLong(frmProduct.getTxtID().getText().trim()));
        product.setName(frmProduct.getTxtName().getText().trim());
        product.setDescription(frmProduct.getTxtDescription().getText().trim());
        product.setPrice(new BigDecimal(frmProduct.getTxtPrice().getText().trim()));
        product.setManufacturer((Manufacturer) frmProduct.getCbManufacturer().getSelectedItem());
        product.setMeasurementUnit((MeasurementUnit) frmProduct.getCbMeasurementUnit().getSelectedItem());
        return product;
    }
}
