package facades;

import dtos.SubjectDTO;
import dtos.TopicDTO;
import entities.Role;
import entities.Subject;
import entities.Topic;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

class TopicFacadeTest {
    private static EntityManagerFactory emf;
    private static TopicFacade facade;

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TopicFacade.getTopicFacade(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Topic.deleteAllRows").executeUpdate();
            em.createNamedQuery("Subject.deleteAllRows").executeUpdate();
//            em.createQuery("delete from User ").executeUpdate();
//            em.createQuery("delete from Role").executeUpdate();
            Role teacherRole = new Role("teacher");
            User user1 = new User("user", "test");
            User user2 = new User("user_name", "test");
            Topic topic1 = new Topic("addition"," description","example","formula");
            Topic topic2 = new Topic("fibonacci"," description","example","formula");
            Subject subject1 = new Subject("math");
            Subject subject2 = new Subject("physics");
            topic1.assingSubject(subject1);
            user1.addRole(teacherRole);
            user2.addRole(teacherRole);
            em.persist(teacherRole);
            em.persist(user1);
            em.persist(user2);
            em.persist(topic1);
            em.persist(topic2);
            em.persist(subject1);
            em.persist(subject2);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deleteTopic() {
        TopicDTO oldTopicDTO = facade.getTopicByName("addition");
        assertEquals("addition", oldTopicDTO.getName());

        facade.deleteTopic("addition");

        assertThrows(NoResultException.class, () -> facade.getTopicByName("addition"));
    }
}