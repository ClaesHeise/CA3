package dtos;

import entities.CalculatorField;
import entities.Topic;

import java.util.HashSet;
import java.util.Set;

public class TopicDTO {
    private String name;

    private String description;

    private String example;

    private String formula;
    private CalculatorDTO calculator;

    public TopicDTO(String name, String description, String example, String formula, CalculatorDTO calculatorDTO) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.formula = formula;
        this.calculator = calculatorDTO;
    }

    public TopicDTO(String name, String description, String example, String formula) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.formula = formula;
    }

    //    public static List<TeacherDTO> getDtos(List<Topic> rms){
//        List<TeacherDTO> rmdtos = new ArrayList();
//        rms.forEach(rm->rmdtos.add(new TeacherDTO(rm)));
//        return rmdtos;
//    }


    public TopicDTO(Topic topic) {
        if(topic.getName() != null)
            this.name = topic.getName();
        this.description = topic.getDescription();
        this.example = topic.getExample();
        this.formula = topic.getFormula();
        if (topic.getCalculator() != null) {
            this.calculator = new CalculatorDTO(topic.getCalculator());
        }
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

    public CalculatorDTO getCalculatorDTO() {
        return calculator;
    }

    public void setCalculatorDTO(CalculatorDTO calculatorDTO) {
        this.calculator = calculatorDTO;
    }

    public static Set<TopicDTO> getDtos(Set<Topic> topics){
        Set<CalculatorFieldDTO> calculatorFieldDTOS = new HashSet<>();
        Set<TopicDTO> topicDTOS = new HashSet<>();
        for(Topic t : topics){
            topicDTOS.add(new TopicDTO(t));
        }
        return topicDTOS;
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", example='" + example + '\'' +
                ", formula='" + formula + '\'' +
                ", calculatorDTO=" + calculator +
                '}';
    }
}
