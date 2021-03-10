package fr.omnilog.maref.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
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

    //TODO : add technologies here

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id.equals(project.id) && name.equals(project.name) && startDate.equals(project.startDate) && Objects.equals(endDate, project.endDate) && client.equals(project.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, client);
    }
}
