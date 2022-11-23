package facades;

import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class TeacherFacade {

    private static EntityManagerFactory emf;
    private static TeacherFacade instance;

    private TeacherFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static TeacherFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeacherFacade();
        }
        return instance;
    }

    public void saveTeacher(String username, String password) {

    }

    public TeacherDTO getTeacher() {
        return null;
    }

    public Teacher getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Teacher teacher;
        try {
            teacher = em.find(Teacher.class, username);
            if (teacher == null || !teacher.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return teacher;
    }

    public void updateTeacherPassword(String username, String password) {

    }

    public void deleteTeacher(String username) {

    }
}
