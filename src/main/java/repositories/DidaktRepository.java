package repositories;

import models.Profession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by walde on 25.06.2017.
 */
public class DidaktRepository implements IDidaktRepository {
    Connection con = null;

    @Override
    public List<Profession> getProfessions() {
        String sql = "SELECT * from tbl_beruf";
        List<Profession> professionList = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            do{
                professionList.add(new Profession(rs.getInt("Bid"),
                                                rs.getString("Berufname"),
                                                rs.getInt("ID_Abteilung"),
                                                rs.getInt("ID_BLeitung"),
                                                rs.getInt("ID_Schema")));
            }while(rs.next());
        }catch (Exception e){
            System.out.println(e);
        }
        return professionList;
    }
}
