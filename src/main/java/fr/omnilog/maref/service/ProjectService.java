package fr.omnilog.maref.service;

import fr.omnilog.maref.dto.ProjectDTO;
import fr.omnilog.maref.helper.DateInterval;
import fr.omnilog.maref.model.Client;
import fr.omnilog.maref.model.Project;
import fr.omnilog.maref.model.Technology;
import fr.omnilog.maref.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TechnologyService technologyService;
    private final ClientService clientService;


    public ProjectService(ProjectRepository projectRepository, TechnologyService technologyService, ClientService clientService) {
        this.projectRepository = projectRepository;
        this.technologyService = technologyService;
        this.clientService = clientService;
    }


    public Project createProject(ProjectDTO projectDTO) {
        // si une date de fin est renseignée
        if (projectDTO.getEndDate() != null) {
            // validation que la date de fin est bien après la date de début
            checkDatesInterval(projectDTO.getStartDate(), projectDTO.getEndDate());
        }
        // validation de l'existence des technologies
        List<Technology> existingTechnologies = technologyService.getMatchingTechnologies(projectDTO.getTechnologies());
        if (projectDTO.getTechnologies().size() != existingTechnologies.size()) {
            throw new RuntimeException("Erreur : une technologie n'existe pas");
        }
        // vérification du client, si client non trouvé une exception sera levée
        var client = clientService.getClientById(projectDTO.getClientId());
        //Mapping du DTO en Entity avant la sauvegarde
        var project = mappingProjectDTOtoEntity(projectDTO, existingTechnologies, client);
        // sauvegarde
        return projectRepository.save(project);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findByProjectSize(String projectSize) {
        // Des requêtes customs via JPA seraient plus performantes car les calculs seraient faits côté base de données
        List<Project> projects = projectRepository.findAll();
        if ("small".contentEquals(projectSize)) {
            projects = projects
                    .stream()
                    .filter(p -> DateInterval.isSmallProject(p.getStartDate(), p.getEndDate()))
                    .collect(Collectors.toList());
        } else if ("medium".contentEquals(projectSize)) {
            projects = projects
                    .stream()
                    .filter(p -> DateInterval.isMediumProject(p.getStartDate(), p.getEndDate()))
                    .collect(Collectors.toList());
        } else if ("large".contentEquals(projectSize)) {
            projects = projects
                    .stream()
                    .filter(p -> DateInterval.isLargeProject(p.getStartDate(), p.getEndDate()))
                    .collect(Collectors.toList());
        }

        return projects;
    }

    private void checkDatesInterval(LocalDate startDate, LocalDate endDate) {
        if(endDate.isBefore(startDate)) {
            throw new RuntimeException("La date de fin du projet doit être supérieure à la date de début");
        }
    }

    private Project mappingProjectDTOtoEntity(ProjectDTO projectDTO, List<Technology> techsList, Client client) {
        var newProject = new Project();
        newProject.setName(projectDTO.getName());
        newProject.setStartDate(projectDTO.getStartDate());
        newProject.setEndDate(projectDTO.getEndDate());
        newProject.setTechnologies(techsList);
        newProject.setClient(client);
        return newProject;
    }


}
