package repositories;

import javafx.collections.ObservableList;
import models.Template;
import models.User;

/**
 * Created by patrick on 6/23/17.
 */
public interface ITemplateRepository
{
    public Template getTemplate();
    public Template createTemplate(Template template, User user);
    public Template updateTemplate();
    public boolean deleteTemplate();
    public ObservableList<Template> getTemplatesByUser(User user);
}
