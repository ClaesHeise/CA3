package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CalculatorField {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "calculator_name")
    private Calculator calculator;
    private String keyword;
    private String formula;
    @ElementCollection
    private Set<String> tags;

    public CalculatorField() {
    }

    public CalculatorField(Long id, String keyword, String formula, Set<String> tags) {
        this.id = id;
        this.keyword = keyword;
        this.formula = formula;
        this.tags = tags;
    }

    public CalculatorField(String keyword, String formula, Set<String> tags) {
        this.keyword = keyword;
        this.formula = formula;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "CalculatorField{" +
                "id=" + id +
                ", calculator=" + calculator +
                ", keyword='" + keyword + '\'' +
                ", formula='" + formula + '\'' +
                ", tags=" + tags +
                '}';
    }
}
