package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Project;
import com.newway.newwayapi.repository.ProjectRepository;
import com.newway.newwayapi.web.rest.errors.BadRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.newway.newwayapi.util.PaginationUtil.generatePaginationHttpHeaders;

@RestController
@RequestMapping("v1/projects")
public class ProjectResource {

    private final ProjectRepository projectRepository;

    public ProjectResource(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) throws URISyntaxException {
        if (project.getId() != null) {
            throw new BadRequest("already have an id");
        }
        Project p = projectRepository.save(project);
        return ResponseEntity.created(new URI("v1/projects/" + p.getId())).body(p);
    }

    @GetMapping
    public ResponseEntity<List<Project>> readProjects(Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<Project> page = projectRepository.findAll(pageable);
        uriBuilder.path("v1/projects");
        HttpHeaders headers = generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> readProject(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(p -> ResponseEntity.ok().body(p)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        if (project.getId() == null) {
            throw new BadRequest("invalid request. id must be null.");
        }
        Project p = projectRepository.save(project);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
