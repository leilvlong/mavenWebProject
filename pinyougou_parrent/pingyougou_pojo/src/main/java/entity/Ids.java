package entity;

import java.io.Serializable;
import java.util.List;

public class Ids implements Serializable{


    @Override
    public String toString() {
        return "Ids{" +
                "grade=" + grade +
                ", id=" + id +
                '}';
    }

    private Integer grade;
    private List<Long> id;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public List<Long> getId() {
        return this.id;
    }

    public void setId(List<Long> id) {
        this.id = id;
    }
}
