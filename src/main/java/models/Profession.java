package models;

import java.util.List;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Profession
{
    Integer BId;
    String Berufname;
    Integer ID_Abteilung;
    Integer ID_BLeitung;
    Integer ID_Schema;

    Department department;
    Teacher depChief;
    Scheme scheme;

    public Profession(Integer bid, String professionName, Integer depID, Integer chiefID, Integer schemeID){
        this.BId = bid;
        this.Berufname = professionName;
        this.ID_Abteilung = depID;
        this.ID_BLeitung = chiefID;
        this.ID_Schema = schemeID;
    }
}
