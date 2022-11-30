package dtos;

import entities.Calculator;

import java.util.Set;

public class CalculatorDTO {
    private String name;
    private Set<CalculatorFieldDTO> calculatorFields;

    public CalculatorDTO(String name, Set<CalculatorFieldDTO> calculatorFieldDTOS) {
        this.name = name;
        this.calculatorFields = calculatorFieldDTOS;
    }

    public CalculatorDTO(Calculator calculator) {
        if(calculator.getName() != null)
            this.name = calculator.getName();
        this.calculatorFields = CalculatorFieldDTO.getDtos(calculator.getCalculatorFields());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CalculatorFieldDTO> getCalculatorFieldDTOs() {
        return calculatorFields;
    }

    public void setCalculatorFieldDTOs(Set<CalculatorFieldDTO> calculatorFieldDTOs) {
        this.calculatorFields = calculatorFieldDTOs;
    }
}
