package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    private String name;

    @OneToMany(mappedBy = "subject")
    private Set<Topic> topicList = new LinkedHashSet<>();


    public Subject(String name) {
        this.name = name;
    }

    public Subject() {}

    public Set<Topic> getTopicList() {
            return this.topicList;
    }

    public void setTopicList(Set<Topic> topicList) {
        this.topicList = topicList;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



        @Override
        public String toString() {
            return "Subject{" +
                    "name='" + name + '\'' +
                    ", subjectList=" + topicList.size() +
                    '}';
    }
}
