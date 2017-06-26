package repositories;

import models.ui_util.Department;
import models.ui_util.Profession;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by walde on 25.06.2017.
 */
public class DidaktRepository implements IDidaktRepository {
    Connection con = null;
    DatabaseConnector databaseConnector = new DatabaseConnector();
    @Override
    public List<Profession> getProfessions() {
        con = databaseConnector.getConnection();
        String sql = "SELECT * from tbl_beruf";
        List<Profession> professionList = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Integer chiefID = null;
            Integer depID = null;
            do{
/*
                professionList.add(new Profession(rs.getInt("Bid"),
                                                rs.getString("Berufname"),
                                                rs.getInt("ID_Abteilung"),
                                                rs.getInt("ID_BLeitung"),
                                                rs.getInt("ID_Schema")));
                                                */
            }while(rs.next());
        }catch (Exception e){
            System.out.println(e);
        }
        return professionList;
    }

    public Department getDepartment(Integer bid)
    {
        con = databaseConnector.getConnection();
        String sql = "SELECT * FROM"
    }
}
