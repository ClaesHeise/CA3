package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "topic")
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

    @Column(name = "calc_url")
    private String calculatorURL;

    public Topic(String name, String description, String example, String formula, String calculatorURL) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.formula = formula;
        this.calculatorURL = calculatorURL;
    }

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

    public String getCalculatorURL() {
        return calculatorURL;
    }

    public void setCalculatorURL(String calculatorURL) {
        this.calculatorURL = calculatorURL;
    }
}