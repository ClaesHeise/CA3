package facades;

import dtos.*;
import entities.Calculator;
import entities.Subject;
import entities.Topic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class AppFacade {

    private static EntityManagerFactory emf;
    private static AppFacade instance;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private AppFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static AppFacade getAppFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AppFacade();
        }
        return instance;
    }

    public List<AppTopicDTO> getAllTopics() {
        EntityManager em = getEntityManager();
        TypedQuery<Topic> query =  em.createQuery("SELECT t FROM Topic t", Topic.class);
        List<Topic> topics = query.getResultList();
        List<AppTopicDTO> topicDTOS = new ArrayList<>();
        for(Topic t : topics){
            topicDTOS.add(new AppTopicDTO(t));
        }
        em.close();
        return topicDTOS;
    }

    public List<AppSubjectDTO> getSubjects(){
        EntityManager em = getEntityManager();
        TypedQuery<Subject> query =  em.createQuery("SELECT s FROM Subject s", Subject.class);
        List<Subject> subjects = query.getResultList();
        List<AppSubjectDTO> subjectDTOS = new ArrayList<>();
        for(Subject s : subjects){
            subjectDTOS.add(new AppSubjectDTO(s));
        }
        em.close();
        return subjectDTOS;
    }

    public AppSubjectDTO getSubjects(String name){
        EntityManager em = getEntityManager();
        TypedQuery<Subject> query = em.createQuery("SELECT s FROM Subject s WHERE s.name = :nt", Subject.class)
                .setParameter("nt", name);
        Subject subject = query.getSingleResult();
        AppSubjectDTO subjectDTO = new AppSubjectDTO(subject);
        em.close();
        return subjectDTO;
    }

    public AppTopicDTO getTopicByName(String name) {
        EntityManager em = getEntityManager();
        TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t WHERE t.name = :nt", Topic.class)
                .setParameter("nt", name);
        Topic topic = query.getSingleResult();
        AppTopicDTO topicDTO = new AppTopicDTO(topic);
        em.close();
        return topicDTO;
    }
}
