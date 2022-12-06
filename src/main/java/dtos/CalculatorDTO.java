package dtos;

import entities.Calculator;

import java.util.Set;

public class CalculatorDTO {
    private String name;
    private String calculatorURL;
    private Set<CalculatorFieldDTO> calculatorFields;

    public CalculatorDTO(String name, String calculatorURL, Set<CalculatorFieldDTO> calculatorFieldDTOS) {
        this.name = name;
        this.calculatorURL = calculatorURL;
        this.calculatorFields = calculatorFieldDTOS;
    }

    public CalculatorDTO(Calculator calculator) {
        if(calculator.getName() != null)
            this.name = calculator.getName();
        this.calculatorURL = calculator.getCalculatorURL();
        this.calculatorFields = CalculatorFieldDTO.getDtos(calculator.getCalculatorFields());
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

    public Set<CalculatorFieldDTO> getCalculatorFieldDTOs() {
        return calculatorFields;
    }

    public void setCalculatorFieldDTOs(Set<CalculatorFieldDTO> calculatorFieldDTOs) {
        this.calculatorFields = calculatorFieldDTOs;
    }
}
