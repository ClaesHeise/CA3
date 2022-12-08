package facades;

import dtos.UserDTO;
import entities.Role;
import entities.Subject;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public UserDTO createUser(UserDTO userDTO){
        EntityManager em = getEntityManager();
        User user = new User(userDTO.getUsername(), userDTO.getPassword());
//        User user = em.find(User.class, userDTO.getUsername());
        Role role = em.find(Role.class, "teacher"); //ToDo change this, if more roles where added
        user.addRole(role);
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public void createRoleAndSubject(Subject subject, Role role){ //Only for backend devs
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(subject);
            em.persist(role);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            System.out.println(user.getUsername()+" "+user.getPassword()+" "+user.getRolesAsStrings());
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException(username+" "+password+" are invalid username or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
    public UserDTO updateUserPassword(String username, String password)  {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        user.setPassword(password);
        try{
            em.getTransaction().begin();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO deleteUser(String username) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO findUserFromUsername(String username) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        em.close();
        return new UserDTO(user);
    }
}
