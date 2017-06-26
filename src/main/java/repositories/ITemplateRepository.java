package repositories;

import models.Template;
import models.User;

import java.util.List;

/**
 * Created by patrick on 6/23/17.
 */
public interface ITemplateRepository
{
    public Template getTemplate();
    public Template saveTemplate();
    public Template updateTemplate();
    public boolean deleteTemplate();
    public List<Template> getTemplatesByUser(User user);
}
