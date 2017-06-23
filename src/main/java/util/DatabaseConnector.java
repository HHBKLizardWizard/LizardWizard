package util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by iho on 22.06.2017.
 */
public class DatabaseConnector {
    public Connection con = null;
    private static final String driverClassName = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/user_template_db?serverTimezone=UTC";
    private static final String dbUsername = "root";
    private static final String dbPassword = "";

    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setUrl(url);

        return dataSource;
    }

    public Connection getConnection() {

        try {
            this.con = this.getDataSource().getConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
