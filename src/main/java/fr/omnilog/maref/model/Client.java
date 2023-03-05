package fr.omnilog.maref.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Client {

    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "client")
    private Set<Project> projects;

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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        //compare les références mémoire
        if (this == o) return true;
        //Compare le nom des classes
        if (o == null || getClass() != o.getClass()) return false;
        //Cast l'objet o dans la type de l'entité
        Client client = (Client) o;
        //Compare les propriétés qui font sens fonctionnellement, dans le cas ci-dessous l'ensemble des propriétés
        return id.equals(client.id) && name.equals(client.name) && Objects.equals(projects, client.projects);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, projects);
        result = 31 * result;
        return result;
    }
}
