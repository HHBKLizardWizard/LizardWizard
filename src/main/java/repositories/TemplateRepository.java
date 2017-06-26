package repositories;

import models.Template;
import models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Template> getTemplatesByUser(User user) {
        List<Template> templateList = new ArrayList<>();

        String fields = "ut.templatename, t.achievements, t.competences, t.contents," +
                " t.materials, t.notes, t.results, t.scenario, t.technics";

        String sql = "SELECT " + fields +
                " FROM templates as t " +
                "LEFT JOIN user_templates as ut " +
                "ON ut.fk_userid = ? " +
                "AND ut.fk_templateid = t.pk_id";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Template template = new Template(
                        rs.getString("templatename"),
                        rs.getBoolean("scenario"),
                        rs.getBoolean("competences"),
                        rs.getBoolean("materials"),
                        rs.getBoolean("technics"),
                        rs.getBoolean("results"),
                        rs.getBoolean("contents"),
                        rs.getBoolean("notes"),
                        rs.getBoolean("achievements"),
                        user
                );

                templateList.add(template);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return templateList;
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
                  /*  rs.getBoolean("scenario"),
                    rs.getBoolean("competences"),
                    rs.getBoolean("materials"),
                    rs.getBoolean("technics"),
                    rs.getBoolean("results"),
                    rs.getBoolean("contents"),
                    rs.getBoolean("notes"),
                    rs.getBoolean("achievements")*/
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
                        "notes, achievements) VALUES (?,?,?,?,?,?,?,?)";
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
            ps.setString(8, String.valueOf(template.isAchievements()));


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
                        "achievements  = ?";

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
            ps.setString(8, String.valueOf(template.isAchievements()));

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
