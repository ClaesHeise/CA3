package dtos;

import entities.Calculator;

import java.util.Set;

public class CalculatorDTO {
    private String name;
    private Set<CalculatorFieldDTO> calculatorFieldDTOS;

    public CalculatorDTO(String name, Set<CalculatorFieldDTO> calculatorFieldDTOS) {
        this.name = name;
        this.calculatorFieldDTOS = calculatorFieldDTOS;
    }

    public CalculatorDTO(Calculator calculator) {
        if(calculator.getName() != null)
            this.name = calculator.getName();
        this.calculatorFieldDTOS = CalculatorFieldDTO.getDtos(calculator.getCalculatorFields());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CalculatorFieldDTO> getCalculatorFieldsDTOs() {
        return calculatorFieldDTOS;
    }

    public void setCalculatorFieldsDTOs(Set<CalculatorFieldDTO> calculatorFieldDTOS) {
        this.calculatorFieldDTOS = calculatorFieldDTOS;
    }
}
