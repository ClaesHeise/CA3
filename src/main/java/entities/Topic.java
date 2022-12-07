package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "topic_name")
    private String name;

//    @Lob
    @Column(name = "description", length = 10000)
    private String description;
//    @Size(max = 100000)

    @Column(name = "example")
    private String example;

    @Column(name = "formula")
    private String formula;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "calculator_name")
    private Calculator calculator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_name")
    private Subject subject;

    public Topic(String name, String description, String example, String formula) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.formula = formula;
    }

    //    public Topic(String name, String description, String example, String formula, String calculatorURL, Calculator calculator) {
//        this.name = name;
//        this.description = description;
//        this.example = example;
//        this.formula = formula;
//        this.calculatorURL = calculatorURL;
//        this.calculator = calculator;
//    }

    public Topic() {}

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

    public Calculator getCalculator() {
        return calculator;
    }

    public void setCalculator(Calculator calculator) {
        if (this.calculator != null) {
            this.calculator.getTopics().remove(this);
        }
        this.calculator = calculator;
        if (calculator != null) {
            calculator.getTopics().add(this);
        }
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        if (this.subject != null) {
            this.subject.getTopicList().remove(this);
        }
        this.subject = subject;
        if (subject != null) {
            subject.getTopicList().add(this);
        }
    }

    public void assingSubject(Subject subject){
        if(subject != null){
            this.subject = subject;
            subject.getTopicList().add(this);
        }
    }

    public void assingCalculator(Calculator calculator){
        if(calculator != null){
            this.calculator = calculator;
            calculator.getTopics().add(this);
        }
    }
    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", example='" + example + '\'' +
                ", formula='" + formula + '\'' +
                ", calculator=" + calculator +
                '}';
    }
}
