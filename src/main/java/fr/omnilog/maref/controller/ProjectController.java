package fr.omnilog.maref.controller;



import fr.omnilog.maref.dto.ProjectDTO;
import fr.omnilog.maref.model.Project;
import fr.omnilog.maref.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    private final ModelMapper modelMapper;

    public ProjectController(ProjectService projectService, ModelMapper modelMapper) {
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProjectDTO>> findAll(@RequestParam(name = "size", required = false) String projectSize) {
        List<Project> projects;
        if(StringUtils.hasText(projectSize)){
            projects = projectService.findByProjectSize(projectSize);
        }else{
            projects = projectService.findAll();
        }

        return ResponseEntity.ok(projects.stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> createProjects(@Valid @NotNull @RequestParam ProjectDTO projectDTO) {
        projectService.createProject(projectDTO);
        return ResponseEntity.ok().build();
    }
}
