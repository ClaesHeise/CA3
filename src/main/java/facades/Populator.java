/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

import dtos.CalculatorDTO;
import dtos.CalculatorFieldDTO;
import dtos.TeacherDTO;
import dtos.TopicDTO;
import entities.Calculator;
import entities.CalculatorField;
import entities.Teacher;
import entities.Topic;
import utils.EMF_Creator;

import java.util.HashSet;
import java.util.Set;

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
        Set<String> tags1 = new HashSet<>();
        tags1.add("+");
        CalculatorField calculatorField1 = new CalculatorField("expression", "", tags1, true);
        Set<CalculatorField> calculatorFields1 = new HashSet<>();
        calculatorFields1.add(calculatorField1);
        Calculator calculator1 = new Calculator("Addition", calculatorFields1);
        Topic topicAddition = new Topic("Addition", "Addition in math is a process of combining two or more numbers. Addends are the numbers added, and the result or the final answer " +
                "we get after the process is called the sum.", "If you have 2 apples and somebody gives you 3 more apples, if you count them together" +
                ", you will now have 5 apples, this can be represented by: 2 + 3 = 5", "a + b = c", "/arithmetic/add", calculator1);
        Set<String> tags2 = new HashSet<>();
        tags2.add("fn");
        CalculatorField calculatorField2 = new CalculatorField("number", "fn", tags2, false);
        Set<CalculatorField> calculatorFields2 = new HashSet<>();
        calculatorFields2.add(calculatorField2);
        Calculator calculator2 = new Calculator("Fibonacci", calculatorFields2);
        Topic topicFibonacci = new Topic("Fibonacci", "The Fibonacci sequence is a set of integers " +
                "(the Fibonacci numbers) that starts with a zero, followed by a one, then by another one, " +
                "and then by a series of steadily increasing numbers. The sequence follows the rule that each " +
                "number is equal to the sum of the preceding two numbers.", "First 14 numbers:\n0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233 ...\n" +
                "Example on how to get to 7th number: 5th number + 6th number = 7th number => 3 + 5 = 8", "Fn = Fn-1 + Fn-2", "/numbertheory/fibonacci/number", calculator2);
        tof.createTopic(new TopicDTO(topicAddition));
        tof.createTopic(new TopicDTO(topicFibonacci));
        tf.createTeacher(new TeacherDTO(new Teacher("TeacherBanana", "banana123")));
        tf.createTeacher(new TeacherDTO(new Teacher("TeacherStrawberry", "strawberry123")));
        tf.createTeacher(new TeacherDTO(new Teacher("TeacherApple", "apple123")));
        tf.createTeacher(new TeacherDTO(new Teacher("Claes", "claes123")));
    }

    public static void main(String[] args) {
        populate();
    }
}
