package models;

import models.ui_util.Teacher;

import java.util.List;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Department
{
    Integer Aid;
    String Abteilungsname;
    String Abteilungskuerzel;
    String Abteilungserlaeuterung;
    Integer ID_Schulform;
    Integer ID_Leiter;
    Integer ID_Vertreter;

    Teacher DepartmentChief;
    Teacher DepartmentRepChief;
    List<Teacher> teacherList;
}
