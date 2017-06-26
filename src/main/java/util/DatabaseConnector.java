package util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by iho on 22.06.2017.
 */
public class DatabaseConnector {

    private final String driverClassName = "com.mysql.jdbc.Driver";

    public DatabaseConnector() {

    }

    public DataSource getDidaktDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:mysql://localhost:3306/didakt?serverTimezone=UTC");

        return dataSource;
    }

    public DataSource getUserDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:mysql://localhost:3306/user_template_db?serverTimezone=UTC");

        return dataSource;
    }
}
