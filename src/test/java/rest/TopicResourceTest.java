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
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class TopicResourceTest {
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
    static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
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

    private void logOut() {
        securityToken = null;
    }

    @Test
    void getAllTopics() {
        given()
                .when()
                .get("/topic/all")
                .then()
                .statusCode(200);
    }


    @Test
    void getByName() {
        given()
                .when()
                .get("/topic/Addition")
                .then()
                .statusCode(200);
    }

    @Test
    void getAllSubject() {
        given()
                .when()
                .get("/topic/allSubjects")
                .then()
                .statusCode(200);
    }

    @Test
    void getSubjectByName() {
        given()
                .when()
                .get("/topic/subject/math")
                .then()
                .statusCode(200)
                .body("name",equalTo("math"),
                        "topics", hasItem(hasEntry("name","addition")));
    }

    @Test
    void addTopic() {
//        login("user","test");
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .body("{\"name\": testTopic,\n\"password\": testing}")
//                .when().post("/topic")
//                .then()
//                .statusCode(200)
//                .body("name",equalTo("testTopic"));
    }

    @Test
    void updateTopic() {
    }

    @Disabled
    @Test
    void delete() {
        login("user","test");

            given()
                    .contentType("application/json")
                    .header("x-access-token",securityToken)
                    .body("{\"name\": \"addition\"}")
                    .when()
                    .delete("/topic");
//                    .statusCode(200)
//                    .body("name",equalTo("addition"));
    }
}