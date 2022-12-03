package facades;

import dtos.CalculatorDTO;
import dtos.CalculatorFieldDTO;
import dtos.TopicDTO;
import entities.Calculator;
import entities.CalculatorField;
import entities.Topic;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lam@cphbusiness.dk
 */
public class TopicFacade {

    private static EntityManagerFactory emf;
    private static TopicFacade instance;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private TopicFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static TopicFacade getTopicFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TopicFacade();
        }
        return instance;
    }

    public TopicDTO createTopic(TopicDTO topicDTO) {
        Set<CalculatorField> calculatorFields = new HashSet<>();
        for (CalculatorFieldDTO calculatorFieldDTO : topicDTO.getCalculatorDTO().getCalculatorFieldDTOs()) {
            calculatorFields.add(new CalculatorField(calculatorFieldDTO.getId(), calculatorFieldDTO.getKeyword(), calculatorFieldDTO.getFormula(), calculatorFieldDTO.getTags(), calculatorFieldDTO.isSingleInput()));
        }
        CalculatorDTO calculatorDTO = topicDTO.getCalculatorDTO();
        Calculator calculator = new Calculator(calculatorDTO.getName(), calculatorFields);
        Topic topic = new Topic(topicDTO.getName(), topicDTO.getDescription(), topicDTO.getExample(), topicDTO.getFormula(), topicDTO.getCalculatorURL(), calculator);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(calculator);
            for (CalculatorField calculatorField : calculatorFields) {
                em.persist(calculatorField);
            }
            em.persist(topic);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new TopicDTO(topic);
    }

    public void addCalculator(CalculatorDTO calculatorDTO) {
        Set<CalculatorField> calculatorFields = new HashSet<>();
        for (CalculatorFieldDTO calculatorFieldDTO : calculatorDTO.getCalculatorFieldDTOs()) {
            calculatorFields.add(new CalculatorField(calculatorFieldDTO.getId(), calculatorFieldDTO.getKeyword(), calculatorFieldDTO.getFormula(), calculatorFieldDTO.getTags(), calculatorFieldDTO.isSingleInput()));
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Calculator(calculatorDTO.getName(), calculatorFields));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<TopicDTO> getAllTopics() {
        EntityManager em = getEntityManager();
        TypedQuery<Topic> query =  em.createQuery("SELECT t FROM Topic t", Topic.class);
        List<Topic> topics = query.getResultList();
        List<TopicDTO> topicDTOS = new ArrayList<>();
        for(Topic t : topics){
            topicDTOS.add(new TopicDTO(t));
        }
        em.close();
        return topicDTOS;
    }

    public List<CalculatorDTO> getAllCalcs() {
        EntityManager em = getEntityManager();
        TypedQuery<Calculator> query =  em.createQuery("SELECT c FROM Calculator c", Calculator.class);
        List<Calculator> calculators = query.getResultList();
        List<CalculatorDTO> calculatorDTOS = new ArrayList<>();
        for(Calculator c : calculators){
            calculatorDTOS.add(new CalculatorDTO(c));
        }
        em.close();
        return calculatorDTOS;
    }

    public TopicDTO getTopicByName(String name) {
        EntityManager em = getEntityManager();
        TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t WHERE t.name = :nt", Topic.class)
                    .setParameter("nt", name);
        Topic topic = query.getSingleResult();
        TopicDTO topicDTO = new TopicDTO(topic);
        em.close();
        return topicDTO;
    }

    public void updateTopic(TopicDTO topicDTO) {
        EntityManager em = getEntityManager();
        Topic topic = em.find(Topic.class, topicDTO.getName());
        try {
            em.getTransaction().begin();
            topic.setDescription(topicDTO.getDescription());
            topic.setExample(topicDTO.getExample());
            topic.setFormula(topicDTO.getFormula());
            topic.setCalculatorURL(topicDTO.getCalculatorURL());
//            Calculator calculator = new Calculator();
//            calculator.setName(topicDTO.getCalculatorDTO().getName());
//            topic.setCalculator(calculator);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
//
//    public void deleteTopic(String name) {
//        EntityManager em = getEntityManager();
//        Query query =  em.createQuery("DELETE FROM Topic t WHERE t.name = :n", Topic.class);
//        int deleteCount = query.setParameter("n", name).executeUpdate();
//        System.out.println(deleteCount);
//        em.close();
//    }

    public void deleteTopic(String name) {
        EntityManager em = emf.createEntityManager();
        Topic topic = em.find(Topic.class, name);
        try {
            em.getTransaction().begin();
            em.remove(topic);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
