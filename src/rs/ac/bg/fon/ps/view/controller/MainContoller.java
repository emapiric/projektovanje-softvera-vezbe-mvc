/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmMain;

/**
 *
 * @author laptop-02
 */
public class MainContoller {

    private final FrmMain frmMain;

    public MainContoller(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
    }

    public void openForm() {
        frmMain.setVisible(true);
        frmMain.getLblCurrentUser().setText(MainCordinator.getInstance().getParam(Constants.CURRENT_USER).toString());
    }

    private void addActionListener() {
        frmMain.getJmiNewInvoice().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewInvoiceForm();
            }
        });
        
        frmMain.jmiProductNewAddActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductNewActionPerformed(evt);
            }

            private void jmiProductNewActionPerformed(java.awt.event.ActionEvent evt) {
                MainCordinator.getInstance().openAddNewProductForm();
            }
        });
        frmMain.jmiProductShowAllActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductShowAllActionPerformed(evt);
            }

            private void jmiProductShowAllActionPerformed(java.awt.event.ActionEvent evt) {
                MainCordinator.getInstance().openViewAllProductForm();
            }
        });
    }
    
    public FrmMain getFrmMain() {
        return frmMain;
    }
    

}
