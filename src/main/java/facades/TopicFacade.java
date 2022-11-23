package facades;

import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;

/**
 * @author lam@cphbusiness.dk
 */
public class TopicFacade {

    private static EntityManagerFactory emf;
    private static TopicFacade instance;

    private TopicFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static TopicFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TopicFacade();
        }
        return instance;
    }

    public void saveTopic(TopicDTO topicDTO) {

    }

    public List<TopicDTO> getAllTopics() {
        return null;
    }

    public TopicDTO getTopicByName(String name) {
        return null;
    }

    public void updateTopic(TopicDTO topicDTO) {

    }

    public void deleteTopic(String name) {

    }
}
