package fr.omnilog.maref.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
public class Technology {
    @Id
    private Integer id;
    private String name;


    @ManyToMany(mappedBy = "technologies")
    private List<Project> projects;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Technology that = (Technology) o;
        return id.equals(that.id) && name.equals(that.name) == projects.equals(that.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, projects);
    }
}
