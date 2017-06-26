package repositories;

import models.Template;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by patrick on 22.06.2017.
 */
public class TemplateRepository implements ITemplateRepository
{

    Connection con = null;

    public TemplateRepository(DataSource dataSource) {
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Template getTemplate()
    {

        String sql = "SELECT * FROM templates";
        Template template = null;
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();


            template = new Template(
                    rs.getBoolean("scenario"),
                    rs.getBoolean("competences"),
                    rs.getBoolean("materials"),
                    rs.getBoolean("technics"),
                    rs.getBoolean("results"),
                    rs.getBoolean("contents"),
                    rs.getBoolean("notes"),
                    rs.getBoolean("achievments")
            );
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return template;
    }

    public Template saveTemplate()
    {
        String sql = "INSERT INTO templates (scenario, competences, materials, " +
                        "technics, results, contents, " +
                        "notes, achievments) VALUES (?,?,?,?,?,?,?,?)";
        Template template = null;
        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(template.isScenario()));
            ps.setString(2, String.valueOf(template.isCompetences()));
            ps.setString(3, String.valueOf(template.isMaterials()));
            ps.setString(4, String.valueOf(template.isTechnics()));
            ps.setString(5, String.valueOf(template.isResults()));
            ps.setString(6, String.valueOf(template.isContents()));
            ps.setString(7, String.valueOf(template.isNotes()));
            ps.setString(8, String.valueOf(template.isAchievments()));


            ps.executeQuery();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return template;
    }

    public Template updateTemplate()
    {
        String sql = "UPDATE templates SET " +
                        "scenario     = ?" +
                        "competences  = ?" +
                        "materials    = ?" +
                        "technics     = ?" +
                        "results      = ?" +
                        "contents     = ?" +
                        "notes        = ?" +
                        "achievments  = ?";

        Template template = null;

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(template.isScenario()));
            ps.setString(2, String.valueOf(template.isCompetences()));
            ps.setString(3, String.valueOf(template.isMaterials()));
            ps.setString(4, String.valueOf(template.isTechnics()));
            ps.setString(5, String.valueOf(template.isResults()));
            ps.setString(6, String.valueOf(template.isContents()));
            ps.setString(7, String.valueOf(template.isNotes()));
            ps.setString(8, String.valueOf(template.isAchievments()));

            ResultSet rs = ps.executeQuery();

            while(rs.next() ){
                System.out.println(rs.getString("name"));
            }

        }catch (Exception e){
            System.out.println(e);
        }

        return template;

    }

    public boolean deleteTemplate()
    {
        String sql = "DELETE FROM templates WHERE pk_id = ?";

        Template template = null;

        try
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(template.getId()));

            ps.executeQuery();

        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }

        return true;
    }


}
