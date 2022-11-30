package dtos;

import entities.CalculatorField;

import java.util.HashSet;
import java.util.Set;

public class CalculatorFieldDTO {
    private Long id;
    private String keyword;
    private String formula;
    private Set<String> tags;
    private boolean isSingleInput;

    public CalculatorFieldDTO(Long id, String keyword, String formula, Set<String> tags, boolean isSingleInput) {
        this.id = id;
        this.keyword = keyword;
        this.formula = formula;
        this.tags = tags;
        this.isSingleInput = isSingleInput;
    }

    public CalculatorFieldDTO(CalculatorField calculatorField) {
        if(calculatorField.getId() != null)
            this.id = calculatorField.getId();
        this.keyword = calculatorField.getKeyword();
        this.formula = calculatorField.getFormula();
        this.tags = calculatorField.getTags();
        this.isSingleInput = calculatorField.isSingleInput();
    }

    public static Set<CalculatorFieldDTO> getDtos(Set<CalculatorField> calculatorField){
        Set<CalculatorFieldDTO> calculatorFieldDTOS = new HashSet<>();
        calculatorField.forEach(calculatorFields-> calculatorFieldDTOS.add(new CalculatorFieldDTO(calculatorFields)));
        return calculatorFieldDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isSingleInput() {
        return isSingleInput;
    }

    public void setSingleInput(boolean singleInput) {
        isSingleInput = singleInput;
    }
}
