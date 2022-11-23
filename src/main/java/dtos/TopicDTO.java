package dtos;

import entities.RenameMe;

import java.util.ArrayList;
import java.util.List;

public class TopicDTO {
    String name;

    String description;

    String example;

    String formula;

    public TopicDTO(String name, String description, String example, String formula) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.formula = formula;
    }

    public static List<TeacherDTO> getDtos(List<RenameMe> rms){
        List<TeacherDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new TeacherDTO(rm)));
        return rmdtos;
    }


    public TopicDTO(RenameMe rm) {
        if(rm.getId() != null)
            this.id = rm.getId();
        this.username = rm.getUsername();
        this.password = rm.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "TopicDTO{" + "name=" + name + ", description=" + description + ", example=" + example + ", formula=" + formula +'}';
    }

}
