package fr.omnilog.maref.service;

import fr.omnilog.maref.dto.ProjectDTO;
import fr.omnilog.maref.model.Client;
import fr.omnilog.maref.model.Project;
import fr.omnilog.maref.model.Technology;
import fr.omnilog.maref.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TechnologyService technologyService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ProjectService projectService;

    private Project smallProject;
    private Project mediumProject;
    private Project largeProject;
    private Project mediumProjectTwo;
    private Project largeProjectTwo;

    @BeforeEach
    public void before() {

    }

    private void createAllProjects(){
        smallProject = createProject(1, "smallProject", LocalDate.of(2020, 12, 9), LocalDate.of(2021, 03, 9));
        mediumProject = createProject(2, "mediumProject", LocalDate.of(2020, 01, 9), LocalDate.of(2021, 03, 9));
        largeProject = createProject(3, "largeProject", LocalDate.of(2015, 01, 01), null);
        mediumProjectTwo = createProject(4, "mediumProject2", LocalDate.of(2020, 01, 9), LocalDate.of(2021, 02, 9));
        largeProjectTwo = createProject(5, "largeProject2", LocalDate.of(2014, 01, 01), null);

        List<Project> projects = List.of(smallProject, mediumProject, largeProject, mediumProjectTwo, largeProjectTwo);
        Mockito.when(projectRepository.findAll()).thenReturn(projects);
    }

    private Project createProject(int id, String name, LocalDate startDate, LocalDate endDate) {
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        return project;
    }

    private ProjectDTO createProjectDTO (int id, String name, LocalDate startDate, LocalDate endDate, int clientId, List<String> technos) {
        var projectDTO = new ProjectDTO();
        projectDTO.setId(id);
        projectDTO.setName(name);
        projectDTO.setStartDate(startDate);
        projectDTO.setEndDate(endDate);
        projectDTO.setClientId(clientId);
        projectDTO.setTechnologies(technos);
        return projectDTO;
    }

    private List<String> createTechnologiesListForNewProject() {
        ArrayList<String> listTechnos = new ArrayList<>();
        listTechnos.add("Java");
        listTechnos.add("JS");
        return listTechnos;
    }


    @Test
    public void findAllTest() {
        createAllProjects();
        assertIterableEquals(List.of(smallProject, mediumProject, largeProject, mediumProjectTwo, largeProjectTwo), projectService.findAll());
    }

    @Test
    public void findByProjectSizeWithSmallprojects() {
        createAllProjects();
        List<Project> smallProjects = projectService.findByProjectSize("small");
        assertEquals(1, smallProjects.size());
    }

    @Test
    public void findByProjectSizeWithMediumProjects() {
        createAllProjects();
        List<Project> mediumProjects = projectService.findByProjectSize("medium");
        assertEquals(2, mediumProjects.size());
    }

    @Test
    public void findByProjectSizeWithLargeProjects() {
        createAllProjects();
        List<Project> largeProjects = projectService.findByProjectSize("large");
        assertEquals(2, largeProjects.size());
    }


    @Test
    public void shouldCreateANewProjectOK() {
        //given
        List<String> technologies = createTechnologiesListForNewProject();
        String projectName = "Mon nouveau projet DTO";
        int clientId = 1235698;
        LocalDate startDate = LocalDate.of(2022, 12, 9);
        LocalDate endDate = LocalDate.of(2023, 02, 4);
        var newProjetDTO = createProjectDTO(0, projectName, startDate, endDate, clientId, technologies);

        var totoro = new Client();
        totoro.setName("Totoro");

        List<Technology> existingTechnologies = createExistingTechnologiesProjectOK();

        var expectedProject = createProject(6, projectName, startDate, endDate);
        expectedProject.setClient(totoro);
        expectedProject.setTechnologies(existingTechnologies);

        //when
        Mockito.when(technologyService.getExistingTechnologies(technologies)).thenReturn(existingTechnologies);

        Mockito.when(clientService.getClientById(1235698)).thenReturn(totoro);

        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(expectedProject);

        //then
        var project  = projectService.createProject(newProjetDTO);
        assertNotNull(project);
        assertEquals(project.getId(), 6);
        Mockito.verify(projectRepository, Mockito.times(1)).save(any(Project.class));
        Mockito.verifyNoMoreInteractions(projectRepository);
    }

    private List<Technology> createExistingTechnologiesProjectOK(){
        List<Technology> existingTechnologies = new ArrayList<>();
        Technology java = new Technology();
        java.setName("Java");
        existingTechnologies.add(java);

        Technology javascript = new Technology();
        javascript.setName("JS");
        existingTechnologies.add(javascript);

        return existingTechnologies;
    }

    @Test
    public void shouldThrowRuntimeExceptionBadArgumentForTechnos() {
        //given
        List<String> technologies = createTechnologiesListForNewProject();
        String projectName = "Mon nouveau projet DTO";
        int clientId = 1235698;
        LocalDate startDate = LocalDate.of(2022, 12, 9);
        LocalDate endDate = LocalDate.of(2023, 02, 4);
        var newProjetDTO = createProjectDTO(0, projectName, startDate, endDate, clientId, technologies);

        var totoro = new Client();
        totoro.setName("Totoro");

        List<Technology> existingTechnologies = new ArrayList<>(); // the techs were not found in DB

        //when
        Mockito.when(technologyService.getExistingTechnologies(technologies)).thenReturn(existingTechnologies);

        //then
        assertThrows(RuntimeException.class, () -> {
            projectService.createProject(newProjetDTO);
        });
    }

    @Test
    public void shouldThrowRuntimeExceptionBadArgumentForClient() {
        //given
        List<String> technologies = createTechnologiesListForNewProject();
        String projectName = "Mon nouveau projet DTO";
        int clientId = 1235698;
        LocalDate startDate = LocalDate.of(2022, 12, 9);
        LocalDate endDate = LocalDate.of(2023, 02, 4);
        var newProjetDTO = createProjectDTO(0, projectName, startDate, endDate, clientId, technologies);

        List<Technology> existingTechnologies = createExistingTechnologiesProjectOK();

        //when
        Mockito.when(technologyService.getExistingTechnologies(technologies)).thenReturn(existingTechnologies);

        Mockito.when(clientService.getClientById(123)).thenReturn(null);

        //then
        assertThrows(RuntimeException.class, () -> {
            projectService.createProject(newProjetDTO);
        });
    }

}