package models.reports;

import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class Subject {
    private String name;
    private List<FieldOfLearning> fieldOfLearningList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldOfLearning> getFieldOfLearningList() {
        return fieldOfLearningList;
    }

    public void setFieldOfLearningList(List<FieldOfLearning> fieldOfLearningList) {
        this.fieldOfLearningList = fieldOfLearningList;
    }


}
