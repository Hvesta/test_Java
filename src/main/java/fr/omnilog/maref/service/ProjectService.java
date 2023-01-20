package fr.omnilog.maref.service;

import fr.omnilog.maref.dto.ProjectDTO;
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


    public void createProject(ProjectDTO projectDTO) {
        // validation date de début et date de fin
        if (projectDTO.getEndDate() != null) {
            checkDatesInterval(projectDTO.getStartDate(), projectDTO.getEndDate());
        }
        // validation de l'existence des technologies
        List<Technology> existingTechnologies = technologyService.getExistingTechnologies(projectDTO.getTechnologies());
        if(projectDTO.getTechnologies().size() != existingTechnologies.size()) {
            throw new RuntimeException("Erreur : une technologie n'existe pas");
        }
        // récupération du client, si client non trouvé une exception sera levée
        var client = clientService.getClientById(projectDTO.getClientId());
        //Mapping du DTO en Entity avant la sauvegarde
        var project = mappingProjectDTOtoEntity(projectDTO, existingTechnologies, client);
        // save
        projectRepository.save(project);

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

    private void checkDatesInterval(LocalDate startDate, LocalDate endDate) {
        if(endDate.isBefore(startDate)) {
            throw new RuntimeException("La date de fin du projet doit être supérieure à la date de début");
        }
    }

    private Project mappingProjectDTOtoEntity(ProjectDTO projectDTO, List<Technology> technosList, Client client) {
        var newProject = new Project();
        newProject.setName(projectDTO.getName());
        newProject.setStartDate(projectDTO.getStartDate());
        newProject.setEndDate(projectDTO.getEndDate());
        newProject.setTechnologies(technosList);
        newProject.setClient(client);
        return newProject;
    }


}
