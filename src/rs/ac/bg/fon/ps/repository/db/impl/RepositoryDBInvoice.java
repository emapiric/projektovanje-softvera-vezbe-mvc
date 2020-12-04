/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Invoice;
import rs.ac.bg.fon.ps.domain.InvoiceItem;
import rs.ac.bg.fon.ps.repository.db.DbConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DbRepository;

/**
 *
 * @author Ema
 */
public class RepositoryDBInvoice  implements DbRepository<Invoice>{

    @Override
    public List<Invoice> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Invoice invoice) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String query = "INSERT INTO invoice(number,date,total) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, invoice.getNumber());
            statement.setDate(2, new java.sql.Date(invoice.getDate().getTime()));
            statement.setBigDecimal(3, invoice.getTotal());
            statement.executeUpdate();
            ResultSet rsKey = statement.getGeneratedKeys();
            if (rsKey.next()) {
                Long invoiceID = rsKey.getLong(1);
                invoice.setId(invoiceID);
                query = "INSERT INTO invoice_item(id,orderNumber,product_id,price,quantity,unit,total) VALUES (?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(query);
                for (InvoiceItem item : invoice.getItems()) {
                    statement.setLong(1, invoice.getId());
                    statement.setLong(2, item.getOrderNumber());
                    statement.setLong(3, item.getProduct().getId());
                    statement.setBigDecimal(4, item.getPrice());
                    statement.setBigDecimal(5, item.getQuantity());
                    statement.setString(6, item.getMeasurementUnit().toString());
                    statement.setBigDecimal(7, item.getTotal());
                    statement.executeUpdate();
                }
            }
            else {
                throw new Exception("Invoice id is not generated");
            }
            rsKey.close();
            statement.close();
        } catch  (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void edit(Invoice param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Invoice param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
