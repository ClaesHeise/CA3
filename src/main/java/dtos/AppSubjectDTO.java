package dtos;

import entities.Subject;
import entities.Topic;

import java.util.HashSet;
import java.util.Set;

public class AppSubjectDTO {
    private String name;
    private Set<AppTopicDTO> topics = new HashSet<>();

    public AppSubjectDTO(String name, Set<AppTopicDTO> topicDTOS) {
        this.name = name;
        this.topics = topicDTOS;
    }

    public AppSubjectDTO(Subject subject) {
        if(subject.getName() != null)
            this.name = subject.getName();
//        this.topics = AppTopicDTO.getDtos(subject.getTopicList());

        for(Topic t : subject.getTopicList()){
            this.topics.add(new AppTopicDTO(t));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
