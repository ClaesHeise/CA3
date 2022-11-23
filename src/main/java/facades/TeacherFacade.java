package facades;

import dtos.TeacherDTO;
import entities.Role;
import entities.Teacher;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class TeacherFacade {

    private static EntityManagerFactory emf;
    private static TeacherFacade instance;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private TeacherFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static TeacherFacade getTeacherFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeacherFacade();
        }
        return instance;
    }

    public TeacherDTO createTeacher(TeacherDTO teacherDTO){
        EntityManager em = getEntityManager();
        Teacher teacher = new Teacher(teacherDTO.getUsername(), teacherDTO.getPassword());
        Role role = em.find(Role.class, "teacher");
        teacher.addRole(role);
        try {
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new TeacherDTO(teacher);
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
