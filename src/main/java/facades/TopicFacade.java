package facades;

import dtos.TeacherDTO;
import dtos.TopicDTO;
import entities.Teacher;
import entities.Topic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        return topicDTOS;
    }

    public TopicDTO getTopicByName(String name) {
        EntityManager em = getEntityManager();
        TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t WHERE t.name = :nt", Topic.class)
                    .setParameter("nt", name);
        Topic topic = query.getSingleResult();
        TopicDTO topicDTO = new TopicDTO(topic);
        return topicDTO;
    }

    public void updateTopic(TopicDTO topicDTO) {

    }

    public void deleteTopic(String name) {

    }
}
