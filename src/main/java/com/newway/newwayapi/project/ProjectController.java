package com.newway.newwayapi.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @PostMapping
    public Long createProject(@RequestBody Project project) {
        Project p = projectRepository.save(project);
        return p.getId();
    }

    @GetMapping
    public List<Project> readProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("{id}")
    public Project readProject(@PathVariable Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        return optionalProject.orElseGet(Project::new);
    }

    @PutMapping("{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        return projectRepository.save(project);
    }

    @DeleteMapping("{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "deleted";
    }

}
