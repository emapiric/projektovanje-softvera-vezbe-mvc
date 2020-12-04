/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Invoice;
import rs.ac.bg.fon.ps.domain.Product;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmInvoice;
import rs.ac.bg.fon.ps.view.form.componenet.table.InvoiceTableModel;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author Ema
 */
public class InvoiceController {
    private final FrmInvoice frmInvoice;

    public InvoiceController(FrmInvoice frmInvoice) {
        this.frmInvoice = frmInvoice;
    }
    
    public  void openForm(FormMode formMode) {
        setupComponents(formMode);
        prepareView(formMode);
        frmInvoice.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        frmInvoice.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        fillCbProducts();
        fillDefaultValues();
        fillTblInvoice();
        addActionListeners();
    }
        
    private void setupComponents(FormMode formMode) {
        switch(formMode){
            case FORM_ADD:
                frmInvoice.getLblInvoiceId().setVisible(false);
                frmInvoice.getTxtInvoiceId().setVisible(false);
                break;
        }
    }

    private void fillCbProducts() {
        try {
            frmInvoice.getCbProduct().setModel(new DefaultComboBoxModel<>(Controller.getInstance().getAllProducts().toArray()));
            frmInvoice.getCbProduct().setSelectedIndex(-1);
            frmInvoice.getCbProduct().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Product product = (Product) e.getItem();
                        frmInvoice.getTxtProductPrice().setText(String.valueOf(product.getPrice()));
                        frmInvoice.getTxtProductMeasurementUnit().setText(product.getMeasurementUnit().toString());
                        frmInvoice.getTxtProductQuantity().setText("1");
                        frmInvoice.getTxtProductQuantity().grabFocus();
                        frmInvoice.getTxtProductQuantity().setSelectionStart(0);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillDefaultValues() {
        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        frmInvoice.getTxtInvoiceDate().setText(currentDate);
        frmInvoice.getTxtInvoiceTotal().setText("0.0");
    }

    private void fillTblInvoice() {
        InvoiceTableModel model = new InvoiceTableModel(new Invoice());
        frmInvoice.getTblInvoice().setModel(model);
    }

    private void addActionListeners() {
        frmInvoice.addBtnAddProductActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    add();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmInvoice, "Invalid product data.","Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void add() {
                Product product = (Product) frmInvoice.getCbProduct().getSelectedItem();
                BigDecimal price = new BigDecimal(frmInvoice.getTxtProductPrice().getText().trim());
                BigDecimal quantity = new BigDecimal(frmInvoice.getTxtProductQuantity().getText().trim());
                InvoiceTableModel model = (InvoiceTableModel) frmInvoice.getTblInvoice().getModel();
                model.addInvoiceItem(product,quantity,price);
                BigDecimal total = model.getInvoice().getTotal();
                frmInvoice.getTxtInvoiceTotal().setText(String.valueOf(total));
            }
        });
        
        frmInvoice.addBtnRemoveInvoiceItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeInvoiceItem();
            }

            private void removeInvoiceItem() {
                int rowIndex = frmInvoice.getTblInvoice().getSelectedRow();
                InvoiceTableModel model = (InvoiceTableModel) frmInvoice.getTblInvoice().getModel();
                if (rowIndex >= 0) {
                    model.removeInvoiceItem(rowIndex);
                    BigDecimal total = model.getInvoice().getTotal();
                    frmInvoice.getTxtInvoiceTotal().setText(String.valueOf(total));
                }
                else {
                    JOptionPane.showMessageDialog(frmInvoice, "Invoice item is not selected.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmInvoice.addBtnSaveInvoiceItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveInvoice();
            }

            private void saveInvoice() {
                try {
                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                    InvoiceTableModel model = (InvoiceTableModel) frmInvoice.getTblInvoice().getModel();
                    Invoice invoice = model.getInvoice();
                    invoice.setNumber(frmInvoice.getTxtInvoiceNumber().getText().trim());//TODO INV-2020-1
                    invoice.setDate(df.parse(frmInvoice.getTxtInvoiceDate().getText().trim()));
                    Controller.getInstance().saveInvoice(invoice);
                    frmInvoice.getTxtInvoiceId().setText(String.valueOf(invoice.getId()));
                    JOptionPane.showMessageDialog(frmInvoice, "Invoice saved.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmInvoice, "Invoice not saved.","Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
