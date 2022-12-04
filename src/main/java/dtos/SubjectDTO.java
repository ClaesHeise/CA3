package dtos;

import entities.Calculator;
import entities.Subject;

import java.util.Set;

public class SubjectDTO {
    private String name;
    private Set<TopicDTO> topicDTOS;

    public SubjectDTO(String name, Set<TopicDTO> topicDTOS) {
        this.name = name;
        this.topicDTOS = topicDTOS;
    }

    public SubjectDTO(Subject subject) {
        if(subject.getName() != null)
            this.name = subject.getName();
        this.topicDTOS = TopicDTO.getDtos(subject.getTopicList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TopicDTO> getTopicDTOS() {
        return topicDTOS;
    }

    public void setTopicDTOS(Set<TopicDTO> topicDTOS) {
        this.topicDTOS = topicDTOS;
    }
}

