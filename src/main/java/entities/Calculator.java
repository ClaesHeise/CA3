package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Calculator {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private String name;
    @OneToMany(mappedBy = "calculator")
    private Set<CalculatorField> calculatorFields = new LinkedHashSet<>();
    @OneToMany(mappedBy = "calculator")
    private Set<Topic> topics = new LinkedHashSet<>();

    public Calculator(String name) {
//                      Set<CalculatorField> calculatorFields) {
        this.name = name;
//        this.calculatorFields = calculatorFields;
//        for (CalculatorField calculatorField : calculatorFields) {
//            calculatorField.setCalculator(this);
//        }
    }

    public Calculator() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", topics=" + topics +
                '}';
    }
}
