package util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by iho on 22.06.2017.
 */
public class DatabaseConnector {

    private static final String driverClassName = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost/";
    private static final String dbUsername = "root";
    private static final String dbPassword = "";

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setUrl(url);

        return dataSource;
    }

    public Connection getConnection() {
        Connection con = null;

        try {
            con = this.getDataSource().getConnection();
        } catch (Exception e) {
            System.out.println(e);
        }

        return con;
    }
}
