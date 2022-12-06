package dtos;

import entities.Topic;

import java.util.HashSet;
import java.util.Set;

public class AppTopicDTO {
    private String name;

    private String description;

    private String example;

    private String formula;

    private String calculatorURL;

    public AppTopicDTO(String name, String description, String example, String formula, String calculatorURL) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.formula = formula;
        this.calculatorURL = calculatorURL;
    }

    //    public static List<TeacherDTO> getDtos(List<Topic> rms){
//        List<TeacherDTO> rmdtos = new ArrayList();
//        rms.forEach(rm->rmdtos.add(new TeacherDTO(rm)));
//        return rmdtos;
//    }


    public AppTopicDTO(Topic topic) {
        if(topic.getName() != null)
            this.name = topic.getName();
        this.description = topic.getDescription();
        this.example = topic.getExample();
        this.formula = topic.getFormula();
        this.calculatorURL = topic.getCalculator().getCalculatorURL();
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

    public String getCalculatorURL() {
        return calculatorURL;
    }

    public void setCalculatorURL(String calculatorURL) {
        this.calculatorURL = calculatorURL;
    }

    public static Set<AppTopicDTO> getDtos(Set<Topic> topics){
        Set<AppTopicDTO> appTopicDTOS = new HashSet<>();
        for(Topic t : topics){
            appTopicDTOS.add(new AppTopicDTO(t));
        }
        return appTopicDTOS;
    }
}
