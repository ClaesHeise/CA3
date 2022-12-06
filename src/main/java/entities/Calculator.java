package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Calculator")
@NamedQuery(name = "Calculator.findAll", query = "SELECT c FROM Calculator c")
public class Calculator {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private String name;
    private String calculatorURL;
    @OneToMany(mappedBy = "calculator", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<CalculatorField> calculatorFields = new LinkedHashSet<>();
    @OneToMany(mappedBy = "calculator")
    private Set<Topic> topics = new LinkedHashSet<>();

    public Calculator(String name, String calculatorURL) {
//                      Set<CalculatorField> calculatorFields) {
        this.name = name;
        this.calculatorURL = calculatorURL;
//        this.calculatorFields = calculatorFields;
//        for (CalculatorField calculatorField : calculatorFields) {
//            calculatorField.setCalculator(this);
//        }
    }

    public Calculator(String name, String calculatorURL, Set<CalculatorField> calculatorFields) {
        this.name = name;
        this.calculatorURL = calculatorURL;
        this.calculatorFields = calculatorFields;
        for (CalculatorField calculatorField : calculatorFields) {
            calculatorField.setCalculator(this);
        }
    }

    public Calculator() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalculatorURL() {
        return calculatorURL;
    }

    public void setCalculatorURL(String calculatorURL) {
        this.calculatorURL = calculatorURL;
    }

    public Set<CalculatorField> getCalculatorFields() {
        return calculatorFields;
    }

    public void setCalculatorFields(Set<CalculatorField> calculatorFields) {
        this.calculatorFields = calculatorFields;
        for (CalculatorField calculatorField : calculatorFields) {
            calculatorField.setCalculator(this);
        }
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
        for (Topic topic : topics) {
            topic.setCalculator(this);
        }
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "name='" + name + '\'' +
                ", calculatorFields=" + calculatorFields +
                '}';
    }
}
