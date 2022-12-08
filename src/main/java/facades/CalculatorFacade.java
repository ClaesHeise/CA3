package facades;

import dtos.CalculatorDTO;
import dtos.CalculatorFieldDTO;
import dtos.UserDTO;
import entities.*;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalculatorFacade {
    private static EntityManagerFactory emf;
    private static CalculatorFacade instance;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private CalculatorFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static CalculatorFacade getCalculatorFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CalculatorFacade();
        }
        return instance;
    }

    public CalculatorDTO createCalculator(CalculatorDTO calculatorDTO) {
        EntityManager em = getEntityManager();
        Set<CalculatorField> calculatorFields = new HashSet<>();
        for (CalculatorFieldDTO calculatorFieldDTO : calculatorDTO.getCalculatorFieldDTOs()) {
            CalculatorField calculatorField = new CalculatorField(
                    calculatorFieldDTO.getKeyword(),
                    calculatorFieldDTO.getFormula(),
                    calculatorFieldDTO.getTags(),
                    calculatorFieldDTO.isSingleInput()
            );
            calculatorFields.add(calculatorField);
        }
        Calculator calculator = new Calculator(calculatorDTO.getName(), calculatorDTO.getCalculatorURL(), calculatorFields);
        try {
            em.getTransaction().begin();
            em.persist(calculator);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CalculatorDTO(calculator);
    }

    public CalculatorDTO updateCalculator(CalculatorDTO calculatorDTO) {
        EntityManager em = getEntityManager();
        Calculator calculator = em.find(Calculator.class, calculatorDTO.getName());
        calculator.setCalculatorURL(calculatorDTO.getCalculatorURL());
        Set<CalculatorField> calculatorFields = new HashSet<>();
        for (CalculatorFieldDTO calculatorFieldDTO : calculatorDTO.getCalculatorFieldDTOs()) {
            CalculatorField calculatorField = new CalculatorField(
                    calculatorFieldDTO.getId(),
                    calculatorFieldDTO.getKeyword(),
                    calculatorFieldDTO.getFormula(),
                    calculatorFieldDTO.getTags(),
                    calculatorFieldDTO.isSingleInput()
            );
            calculatorFields.add(calculatorField);
        }
        calculator.setCalculatorFields(calculatorFields);
        try {
            em.getTransaction().begin();
            em.merge(calculator);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CalculatorDTO(calculator);
    }

    public CalculatorDTO deleteCalculator(String calculatorName) {
        EntityManager em = getEntityManager();
        Calculator calculator = em.find(Calculator.class, calculatorName);
        try {
            em.getTransaction().begin();
            em.remove(calculator);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CalculatorDTO(calculator);
    }

    public List<String> getAllCalculatorNames() {
        EntityManager em = getEntityManager();
        List<String> names = new ArrayList<>();
        List<Calculator> calculators = em.createNamedQuery("Calculator.findAll").getResultList();
        for (Calculator calculator : calculators) {
            names.add(calculator.getName());
        }
        return names;
    }

    public CalculatorDTO getCalculator(String name) {
        EntityManager em = getEntityManager();
        Calculator calculator = em.find(Calculator.class, name);
        return new CalculatorDTO(calculator);
    }
}
