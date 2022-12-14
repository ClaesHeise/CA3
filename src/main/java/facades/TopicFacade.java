package facades;

import dtos.CalculatorDTO;
import dtos.CalculatorFieldDTO;
import dtos.SubjectDTO;
import dtos.TopicDTO;
import entities.*;

import javax.persistence.*;
import java.util.*;

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
        CalculatorDTO calculatorDTO = topicDTO.getCalculatorDTO();
        Calculator calculator = new Calculator(calculatorDTO.getName(), calculatorDTO.getCalculatorURL());
        Set<CalculatorField> calculatorFields = new HashSet<>();
        for (CalculatorFieldDTO calculatorFieldDTO : topicDTO.getCalculatorDTO().getCalculatorFieldDTOs()) {
            CalculatorField cf = new CalculatorField(calculatorFieldDTO.getId(), calculatorFieldDTO.getKeyword(), calculatorFieldDTO.getFormula(), calculatorFieldDTO.getTags(), calculatorFieldDTO.isSingleInput());
            cf.assingCalculator(calculator);
            calculatorFields.add(cf);

        }
        Topic topic = new Topic(topicDTO.getName(), topicDTO.getDescription(), topicDTO.getExample(), topicDTO.getFormula());
        topic.assingCalculator(calculator);
        EntityManager em = getEntityManager();
        Subject subject = em.find(Subject.class, "Math"); // ToDo change this, if more subjects were to be added
        topic.assingSubject(subject);
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

    public TopicDTO createTopic(TopicDTO topicDTO, String subjectName) throws EntityExistsException{
        EntityManager em = getEntityManager();
        Topic topic = new Topic(topicDTO.getName(), topicDTO.getDescription(), topicDTO.getExample(), topicDTO.getFormula());
        String calculatorName = topicDTO.getCalculatorDTO().getName();
        if (calculatorName != null) {
            Calculator calculator = em.find(Calculator.class, calculatorName);
            topic.setCalculator(calculator);
        }
        Subject subject = em.find(Subject.class, subjectName);
        if (subject == null) {
            subject = new Subject(subjectName);
        }
        topic.setSubject(subject);
        try {
            em.getTransaction().begin();
            em.persist(topic);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new TopicDTO(topic);
    }

//    public void addCalculator(CalculatorDTO calculatorDTO) {
//        Set<CalculatorField> calculatorFields = new HashSet<>();
//        for (CalculatorFieldDTO calculatorFieldDTO : calculatorDTO.getCalculatorFieldDTOs()) {
//            calculatorFields.add(new CalculatorField(calculatorFieldDTO.getId(), calculatorFieldDTO.getKeyword(), calculatorFieldDTO.getFormula(), calculatorFieldDTO.getTags(), calculatorFieldDTO.isSingleInput()));
//        }
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(new Calculator(calculatorDTO.getName(), calculatorFields));
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

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

    public List<SubjectDTO> getSubjects(){
        EntityManager em = getEntityManager();
        TypedQuery<Subject> query =  em.createQuery("SELECT s FROM Subject s", Subject.class);
        List<Subject> subjects = query.getResultList();
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for(Subject s : subjects){
            subjectDTOS.add(new SubjectDTO(s));
        }
        em.close();
        return subjectDTOS;
    }

    public SubjectDTO getSubjects(String name){
        EntityManager em = getEntityManager();
        TypedQuery<Subject> query = em.createQuery("SELECT s FROM Subject s WHERE s.name = :nt", Subject.class)
                .setParameter("nt", name);
        Subject subject = query.getSingleResult();
        SubjectDTO subjectDTO = new SubjectDTO(subject);
        em.close();
        return subjectDTO;
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

    public TopicDTO updateTopic(TopicDTO topicDTO, String subjectName) {
        EntityManager em = getEntityManager();
        Topic topic = em.find(Topic.class, topicDTO.getName());
        String oldSubjectName = topic.getSubject().getName();
        topic.setDescription(topicDTO.getDescription());
        topic.setExample(topicDTO.getExample());
        topic.setFormula(topicDTO.getFormula());
        String calculatorName = topicDTO.getCalculatorDTO().getName();
        if (calculatorName != null) {
            Calculator calculator = em.find(Calculator.class, calculatorName);
            topic.setCalculator(calculator);
        }
        Subject subject = em.find(Subject.class, subjectName);
        if (subject == null) {
            subject = new Subject(subjectName);
        }
        topic.setSubject(subject);
        try {
            em.getTransaction().begin();
            em.merge(topic);
            em.flush();
            Subject oldSubject = em.find(Subject.class, oldSubjectName);
            if (oldSubject.getTopicList().isEmpty()) {
                em.remove(oldSubject);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new TopicDTO(topic);
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
