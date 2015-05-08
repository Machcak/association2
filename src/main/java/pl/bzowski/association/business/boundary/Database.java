package pl.bzowski.association.business.boundary;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

 
public class Database {
 
    public static Connection getConnection() {
        try {
        	InitialContext initialContext = new InitialContext();
        	DataSource dataSource = (DataSource)initialContext.lookup("java:jboss/datasources/MySQLDS");
        	Connection con = dataSource.getConnection();
        	return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }
 
    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
