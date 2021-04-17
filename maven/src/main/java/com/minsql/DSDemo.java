package com.minsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DSDemo {
    public static void main(String[] args) {
        DSDemo dsDemo = new DSDemo();
        dsDemo.displayEmployeeById(1);
    }

    void displayEmployeeById(int id) {
        Connection connection = null;
        // String selectSQL = "SELECT * FROM employee WHERE id = ?";
        String selectSQL = "SELECT CONNECTION_ID() as id";
        PreparedStatement prepStmt = null;
        try {
            DataSource ds = DSCreator.getDataSource();

            for (int i = 0; i < 100; i++) {
                Thread.sleep(1000);
                connection = ds.getConnection();
                prepStmt = connection.prepareStatement(selectSQL);
                // prepStmt.setInt(1, id);
                ResultSet rs = prepStmt.executeQuery();
                while (rs.next()) {
                    System.out.println("no: " + i);
                    System.out.println("pid: " + rs.getInt("id"));
                    // System.out.println("id: " + rs.getInt("id"));
                    // System.out.println("First Name: " + rs.getString("first_name"));
                    // System.out.println("Last Name: " + rs.getString("last_name"));
                    // System.out.println("Department: " + rs.getString("department"));
                }
                rs.close();
                prepStmt.close();
                connection.close();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}