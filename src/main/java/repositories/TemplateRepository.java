package repositories;

import util.DatabaseConnector;

import java.sql.Connection;

/**
 * Created by iho on 22.06.2017.
 */
public class TemplateRepository implements ITemplateRepository
{
    Connection con = new DatabaseConnector().getConnection();


}
