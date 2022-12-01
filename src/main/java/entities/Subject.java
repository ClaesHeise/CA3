package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

    @Entity
    @Table(name = "subject")
    public class Subject implements Serializable {

        private static final long serialVersionUID = 1L;
        @Id
        @Basic(optional = false)
        @NotNull
        @Column(name = "subject_name")
        private String name;

        @ManyToMany(mappedBy = "subjectList")
        private List<Subject> subjectList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Subject> getSubjectList() {
            return subjectList;
        }

        public void setSubjectList(List<Subject> subjectList) {
            this.subjectList = subjectList;
        }

        @Override
        public String toString() {
            return "Subject{" +
                    "name='" + name + '\'' +
                    ", subjectList=" + subjectList +
                    '}';
    }
}
