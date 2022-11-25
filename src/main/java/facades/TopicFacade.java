package facades;

import dtos.TeacherDTO;
import dtos.TopicDTO;
import entities.Teacher;
import entities.Topic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

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

    public TopicDTO createTopic(TopicDTO topicDTO){
        Topic topic = new Topic(topicDTO.getName(), topicDTO.getDescription(), topicDTO.getExample(), topicDTO.getFormula(), topicDTO.getCalculatorURL());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(topic);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new TopicDTO(topic);
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
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deleteTopic(String name) {
        EntityManager em = getEntityManager();
        Query query =  em.createQuery("DELETE FROM Topic t WHERE t.name = :n", Topic.class);
        int deleteCount = query.setParameter("n", name).executeUpdate();
        System.out.println(deleteCount);
        em.close();
    }
}
