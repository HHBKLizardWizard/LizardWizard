package repositories;

import models.Template;

/**
 * Created by patrick on 6/23/17.
 */
public interface ITemplateRepository
{
    public Template getTemplate();
    public Template saveTemplate();
    public Template updateTemplate();
    public boolean deleteTemplate();
}
