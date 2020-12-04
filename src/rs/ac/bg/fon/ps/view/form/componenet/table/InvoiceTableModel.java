/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.componenet.table;

import java.math.BigDecimal;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Invoice;
import rs.ac.bg.fon.ps.domain.InvoiceItem;
import rs.ac.bg.fon.ps.domain.Product;


/**
 *
 * @author Ema
 */
public class InvoiceTableModel extends AbstractTableModel{
    private final Invoice invoice;
    private final String[] columnNames = new String[]{"Order No.","Product","Unit","Price","Quantity","Total"};

    public InvoiceTableModel(Invoice invoice) {
        this.invoice = invoice;
    }
    
    @Override
    public int getRowCount() {
        if (invoice != null) {
            return invoice.getItems().size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItem item = invoice.getItems().get(rowIndex);
        switch(columnIndex) {
            case 0:
                return item.getOrderNumber();
            case 1:
                return item.getProduct().getName();
            case 2: 
                return item.getMeasurementUnit();
            case 3:
                return item.getPrice();
            case 4: 
                return item.getQuantity();
            case 5:
                return item.getTotal();
            default:
                return "n/a";
        }
    }

    public void addInvoiceItem(Product product, BigDecimal quantity, BigDecimal price) {
        InvoiceItem item = new InvoiceItem();
        item.setOrderNumber(invoice.getItems().size()+1);
        item.setProduct(product);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setMeasurementUnit(product.getMeasurementUnit());
        item.setTotal(quantity.multiply(price));
        item.setInvoice(invoice);
        invoice.getItems().add(item);
        invoice.setTotal(invoice.getTotal().add(item.getTotal()));
        fireTableRowsInserted(invoice.getItems().size()-1, invoice.getItems().size()-1);
    }
    
    public void removeInvoiceItem(int rowIndex) {
        InvoiceItem item = invoice.getItems().get(rowIndex);
        invoice.getItems().remove(rowIndex);
        invoice.setTotal(invoice.getTotal().subtract(item.getPrice().multiply(item.getQuantity())));
        setOrderNumbers();
        fireTableRowsDeleted(invoice.getItems().size()-1, invoice.getItems().size()-1);
    }
    
    public Invoice getInvoice() {
        return invoice;
    }

    private void setOrderNumbers() {
        int orderNo = 0;
        for (InvoiceItem item : invoice.getItems()) {
            item.setOrderNumber(++orderNo);
        }
    }
}
