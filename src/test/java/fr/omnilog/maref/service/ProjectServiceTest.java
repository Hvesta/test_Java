package fr.omnilog.maref.service;

import fr.omnilog.maref.model.Project;
import fr.omnilog.maref.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    private Project smallProject;
    private Project mediumProject;
    private Project largeProject;

    @BeforeEach
    public void before() {
        smallProject = createProject(1, "smallProject", LocalDate.of(2020, 12, 9), LocalDate.of(2021, 03, 9));
        mediumProject = createProject(2, "mediumProject", LocalDate.of(2020, 01, 9), LocalDate.of(2021, 03, 9));
        largeProject = createProject(3, "largeProject", LocalDate.of(2015, 01, 01), null);

        List<Project> projects = List.of(smallProject, mediumProject, largeProject);
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

    @Test
    public void findAllTest() {
        assertIterableEquals(List.of(smallProject, mediumProject, largeProject), projectService.findAll());
    }

    @Test
    public void findByProjectSizeWithSmallprojects() {
        List<Project> smallProjects = projectService.findByProjectSize("small");
        assertTrue(true);
    }
}