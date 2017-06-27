package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Template;
import models.User;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     *
     * @param user
     * @return
     */
    public ObservableList<Template> getTemplatesByUser(User user, boolean forManagement) {
        ObservableList<Template> templateList = FXCollections.observableArrayList();
        if(forManagement){
            templateList.add(new Template("Neue Template anlegen"));
        }

        String fields = "t.pk_id, ut.templatename, t.achievements, t.competences, t.contents," +
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
                        rs.getInt("t.pk_id"),
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

    /**
     *
     * @param template
     */
    public void deleteTemplate(Template template) {
        String sql = "DELETE FROM templates WHERE pk_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, template.getId());

            ps.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param template
     * @param user
     * @return
     */
    @Transactional
    public Template createTemplate(Template template, User user) {
        String sqlTemplates = "INSERT INTO templates (scenario, competences, materials, technics," +
                " results, contents, notes, achievements) VALUES (?,?,?,?,?,?,?,?)";

        String sqlUserTemplates = "INSERT INTO user_templates (fk_templateid, fk_userid, templatename)" +
                " VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sqlTemplates);
            ps.setBoolean(1, template.isScenario());
            ps.setBoolean(2, template.isCompetences());
            ps.setBoolean(3, template.isMaterials());
            ps.setBoolean(4, template.isTechnics());
            ps.setBoolean(5, template.isResults());
            ps.setBoolean(6, template.isContents());
            ps.setBoolean(7, template.isNotes());
            ps.setBoolean(8, template.isAchievements());

            ps.execute();

            ResultSet rs = con.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
            rs.next();
            template.setId(rs.getInt(1));

            ps = con.prepareStatement(sqlUserTemplates);
            ps.setInt(1, template.getId());
            ps.setInt(2, user.getId());
            ps.setString(3, template.getTemplateName());

            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return template;
    }

    /**
     *
     * @param template
     * @return template
     */
    public void updateTemplate(Template template) {
        String sql = "UPDATE templates SET " +
                        "scenario     = ?" +
                        "competences  = ?" +
                        "materials    = ?" +
                        "technics     = ?" +
                        "results      = ?" +
                        "contents     = ?" +
                        "notes        = ?" +
                        "achievements  = ? " +
                "WHERE pk_id = ?";

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
            ps.setString(9, String.valueOf(template.getId()));

            ps.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
