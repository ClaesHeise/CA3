package dtos;

import entities.Topic;

public class TopicDTO {
    private String name;

    private String description;

    private String example;

    private String formula;

    private String calculatorURL;

    private CalculatorDTO calculator;

    public TopicDTO(String name, String description, String example, String formula, String calculatorURL, CalculatorDTO calculatorDTO) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.formula = formula;
        this.calculatorURL = calculatorURL;
        this.calculator = calculatorDTO;
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
        this.calculatorURL = topic.getCalculatorURL();
        this.calculator = new CalculatorDTO(topic.getCalculator());
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

    public CalculatorDTO getCalculatorDTO() {
        return calculator;
    }

    public void setCalculatorDTO(CalculatorDTO calculatorDTO) {
        this.calculator = calculatorDTO;
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", example='" + example + '\'' +
                ", formula='" + formula + '\'' +
                ", calculatorURL='" + calculatorURL + '\'' +
                ", calculatorDTO=" + calculator +
                '}';
    }
}
