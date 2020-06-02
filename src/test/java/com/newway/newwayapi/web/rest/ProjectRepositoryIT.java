package com.newway.newwayapi.web.rest;

import com.newway.newwayapi.entity.Developer;
import com.newway.newwayapi.entity.Project;
import com.newway.newwayapi.entity.Tag;
import com.newway.newwayapi.repository.ProjectRepository;
import com.newway.newwayapi.util.RandomUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProjectRepositoryIT {

    private static final String PROJECT_NAME = "newway";
    private static final String PROJECT_DESC = "create-new-people";

    private static final String DEV_USERNAME = "kamil";
    private static final String DEV_EMAIL = "kamil@kamilov.com";
    private static final String DEV_PASSWORD = "12341234";

    private static final String TAG_NAME = "java";
    private ProjectRepository projectRepository;
    private EntityManager em;

    private MockMvc restMockMvc;

    private Project project;

    @BeforeEach
    public void setup() {
        ProjectResource projectResource = new ProjectResource(projectRepository);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(projectResource).build();
    }

    private static Project createProjectEntity() {
        Project project = new Project();
        project.setName(PROJECT_NAME + RandomUtil.generateRandomAlphanumericString());
        project.setDescription(PROJECT_DESC);

        Developer d1 = createDeveloperEntity();
        Developer d2 = createDeveloperEntity();
        Tag t1 = createTagEntity();
        Tag t2 = createTagEntity();
        Tag t3 = createTagEntity();

        project.setTags(Arrays.asList(t1, t2, t3));

        return project;
    }

    private static Developer createDeveloperEntity() {
        Developer developer = new Developer();
        developer.setUsername(DEV_USERNAME + RandomUtil.generateRandomAlphanumericString());
        developer.setPassword(DEV_PASSWORD);
        developer.setEmail(DEV_EMAIL + RandomUtil.generateRandomAlphanumericString());
        return developer;
    }

    private static Tag createTagEntity() {
        Tag tag = new Tag();
        tag.setName(TAG_NAME + RandomUtil.generateRandomAlphanumericString());
        return tag;
    }

    @BeforeEach
    public void initTest() {
        project = createProjectEntity();
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int projectSize = projectRepository.findAll().size();
        int tagSize = project.getTags().size();

        Project project = createProjectEntity();

        restMockMvc.perform(post("/api/v1/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isCreated());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(projectSize + 1);
        Project tp = projects.get(projects.size() - 1);
        assertThat(tp.getName()).isEqualTo(project.getName());
        assertThat(tp.getDescription()).isEqualTo(project.getDescription());
        assertThat(tp.getTags().size()).isEqualTo(tagSize);
    }
}
