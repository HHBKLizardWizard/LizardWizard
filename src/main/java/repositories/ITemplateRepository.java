package repositories;

import javafx.collections.ObservableList;
import models.Template;
import models.User;

/**
 * Created by patrick on 6/23/17.
 */
public interface ITemplateRepository
{
    public Template createTemplate(Template template, User user);
    public void updateTemplate(Template template);
    public void deleteTemplate(Template template);
    public ObservableList<Template> getTemplatesByUser(User user, boolean forManagement);
}
