package fr.omnilog.maref.service;

import fr.omnilog.maref.model.Project;
import fr.omnilog.maref.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findByProjectSize(String projectSize) {
        //TODO : ?????
        List<Project> projects = projectRepository.findAll();

        if ("small".contentEquals(projectSize)) {
            projects = projects
                    .stream()
                    .filter(p -> p.getStartDate().isAfter(LocalDate.now().minusMonths(3)))
                    .collect(Collectors.toList());
        } else if ("medium".contentEquals(projectSize)) {

        } else if ("large".contentEquals(projectSize)) {

        }
        return projects;
    }
}
