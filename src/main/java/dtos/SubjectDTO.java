package dtos;

import entities.Calculator;
import entities.Subject;

import java.util.Set;

public class SubjectDTO {
    private String name;
    private Set<TopicDTO> topics;

    public SubjectDTO(String name, Set<TopicDTO> topicDTOS) {
        this.name = name;
        this.topics = topicDTOS;
    }

    public SubjectDTO(Subject subject) {
        if(subject.getName() != null)
            this.name = subject.getName();
        this.topics = TopicDTO.getDtos(subject.getTopicList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TopicDTO> getTopicDTOS() {
        return topics;
    }

    public void setTopicDTOS(Set<TopicDTO> topicDTOS) {
        this.topics = topicDTOS;
    }
}

