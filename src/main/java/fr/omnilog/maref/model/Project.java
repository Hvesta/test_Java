package fr.omnilog.maref.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Project {

    @Id
    private Integer id;
    private String name;
    LocalDate startDate;
    LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany
    private List<Technology> technologies;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Technology> getTechnologies() { return technologies; }

    public void setTechnologies(List<Technology> technologies) { this.technologies = technologies; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id.equals(project.id) && name.equals(project.name) && startDate.equals(project.startDate) && Objects.equals(endDate, project.endDate) && client.equals(project.client) && technologies.equals(project.technologies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, client, technologies);
    }
}
