package dtos;

import entities.Subject;

import java.util.Set;

public class AppSubjectDTO {
    private String name;
    private Set<AppTopicDTO> topics;

    public AppSubjectDTO(String name, Set<AppTopicDTO> topicDTOS) {
        this.name = name;
        this.topics = topicDTOS;
    }

    public AppSubjectDTO(Subject subject) {
        if(subject.getName() != null)
            this.name = subject.getName();
        this.topics = AppTopicDTO.getDtos(subject.getTopicList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
