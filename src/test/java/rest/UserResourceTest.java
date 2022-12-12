package rest;

import entities.Role;
import entities.Subject;
import entities.Topic;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import utils.EMF_Creator;
import static org.hamcrest.Matchers.equalTo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class UserResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            Role teacherRole = new Role("teacher");
            User user1 = new User("user", "test");
            User user2 = new User("user_name", "test");
            user1.addRole(teacherRole);
            user2.addRole(teacherRole);
            em.persist(teacherRole);
            em.persist(user1);
            em.persist(user2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static String securityToken;

    private static void login(String username, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", username, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    void updatePassword() {
        login("user", "test");

        String password = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\"password\": testing}")
                .when().put("/user")
                .then()
                .statusCode(200)
                .extract().path("password");

        assertTrue(BCrypt.checkpw("testing", password));
//                .body("password", equalTo(BCrypt.hashpw("testing", BCrypt.gensalt())));
    }

    @Test
    void addUser() {
        login("user", "test");

        given()
            .contentType("application/json")
            .header("x-access-token", securityToken)
            .body("{\"name\": testUser,\n\"password\": testing}")
            .when().post("/user")
            .then()
            .statusCode(200)
            .body("username", equalTo("testUser"));
    }

    @Test
    void getFromTeacher() {
        login("user", "test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when().post("/user/verify")
                .then()
                .statusCode(200)
                .body("username", equalTo("user"));
    }
}