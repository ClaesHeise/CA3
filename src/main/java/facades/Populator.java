/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

import dtos.TeacherDTO;
import dtos.TopicDTO;
import entities.Teacher;
import entities.Topic;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        TeacherFacade tf = TeacherFacade.getTeacherFacade(emf);
        TopicFacade tof = TopicFacade.getTopicFacade(emf);
        tof.createTopic(new TopicDTO(new Topic("Fibonacci", "The Fibonacci sequence is a set of integers " +
                "(the Fibonacci numbers) that starts with a zero, followed by a one, then by another one, " +
                "and then by a series of steadily increasing numbers. The sequence follows the rule that each " +
                "number is equal to the sum of the preceding two numbers.", "First 14 numbers:\n0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233 ...\n" +
                "Example on how to get to 7th number: 5th number + 6th number = 7th number => 3 + 5 = 8", "Fn = Fn-1 + Fn-2", "/numbertheory/fibonacci/number")));
        tf.createTeacher(new TeacherDTO(new Teacher("TeacherBanana", "banana123")));
        tf.createTeacher(new TeacherDTO(new Teacher("TeacherStrawberry", "strawberry123")));
        tf.createTeacher(new TeacherDTO(new Teacher("TeacherApple", "apple123")));
    }

    public static void main(String[] args) {
        populate();
    }
}
