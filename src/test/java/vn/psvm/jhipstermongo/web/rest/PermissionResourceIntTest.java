package vn.psvm.jhipstermongo.web.rest;

import vn.psvm.jhipstermongo.JhipstermongoApp;

import vn.psvm.jhipstermongo.domain.Permission;
import vn.psvm.jhipstermongo.repository.PermissionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PermissionResource REST controller.
 *
 * @see PermissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipstermongoApp.class)
public class PermissionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPermissionMockMvc;

    private Permission permission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            PermissionResource permissionResource = new PermissionResource(permissionRepository);
        this.restPermissionMockMvc = MockMvcBuilders.standaloneSetup(permissionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permission createEntity() {
        Permission permission = new Permission()
                .name(DEFAULT_NAME);
        return permission;
    }

    @Before
    public void initTest() {
        permissionRepository.deleteAll();
        permission = createEntity();
    }

    @Test
    public void createPermission() throws Exception {
        int databaseSizeBeforeCreate = permissionRepository.findAll().size();

        // Create the Permission

        restPermissionMockMvc.perform(post("/api/permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permission)))
            .andExpect(status().isCreated());

        // Validate the Permission in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeCreate + 1);
        Permission testPermission = permissionList.get(permissionList.size() - 1);
        assertThat(testPermission.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permissionRepository.findAll().size();

        // Create the Permission with an existing ID
        Permission existingPermission = new Permission();
        existingPermission.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermissionMockMvc.perform(post("/api/permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPermission)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = permissionRepository.findAll().size();
        // set the field null
        permission.setName(null);

        // Create the Permission, which fails.

        restPermissionMockMvc.perform(post("/api/permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permission)))
            .andExpect(status().isBadRequest());

        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPermissions() throws Exception {
        // Initialize the database
        permissionRepository.save(permission);

        // Get all the permissionList
        restPermissionMockMvc.perform(get("/api/permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permission.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getPermission() throws Exception {
        // Initialize the database
        permissionRepository.save(permission);

        // Get the permission
        restPermissionMockMvc.perform(get("/api/permissions/{id}", permission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(permission.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingPermission() throws Exception {
        // Get the permission
        restPermissionMockMvc.perform(get("/api/permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePermission() throws Exception {
        // Initialize the database
        permissionRepository.save(permission);
        int databaseSizeBeforeUpdate = permissionRepository.findAll().size();

        // Update the permission
        Permission updatedPermission = permissionRepository.findOne(permission.getId());
        updatedPermission
                .name(UPDATED_NAME);

        restPermissionMockMvc.perform(put("/api/permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPermission)))
            .andExpect(status().isOk());

        // Validate the Permission in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeUpdate);
        Permission testPermission = permissionList.get(permissionList.size() - 1);
        assertThat(testPermission.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingPermission() throws Exception {
        int databaseSizeBeforeUpdate = permissionRepository.findAll().size();

        // Create the Permission

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPermissionMockMvc.perform(put("/api/permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permission)))
            .andExpect(status().isCreated());

        // Validate the Permission in the database
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePermission() throws Exception {
        // Initialize the database
        permissionRepository.save(permission);
        int databaseSizeBeforeDelete = permissionRepository.findAll().size();

        // Get the permission
        restPermissionMockMvc.perform(delete("/api/permissions/{id}", permission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Permission> permissionList = permissionRepository.findAll();
        assertThat(permissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Permission.class);
    }
}
