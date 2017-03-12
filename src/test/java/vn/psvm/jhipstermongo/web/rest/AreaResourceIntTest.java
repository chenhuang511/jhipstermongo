package vn.psvm.jhipstermongo.web.rest;

import vn.psvm.jhipstermongo.JhipstermongoApp;

import vn.psvm.jhipstermongo.domain.Area;
import vn.psvm.jhipstermongo.repository.AreaRepository;

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
 * Test class for the AreaResource REST controller.
 *
 * @see AreaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipstermongoApp.class)
public class AreaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAreaMockMvc;

    private Area area;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            AreaResource areaResource = new AreaResource(areaRepository);
        this.restAreaMockMvc = MockMvcBuilders.standaloneSetup(areaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Area createEntity() {
        Area area = new Area()
                .name(DEFAULT_NAME)
                .address(DEFAULT_ADDRESS)
                .city(DEFAULT_CITY)
                .parentId(DEFAULT_PARENT_ID)
                .customerId(DEFAULT_CUSTOMER_ID);
        return area;
    }

    @Before
    public void initTest() {
        areaRepository.deleteAll();
        area = createEntity();
    }

    @Test
    public void createArea() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // Create the Area

        restAreaMockMvc.perform(post("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate + 1);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArea.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testArea.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testArea.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testArea.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    public void createAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // Create the Area with an existing ID
        Area existingArea = new Area();
        existingArea.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaMockMvc.perform(post("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingArea)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaRepository.findAll().size();
        // set the field null
        area.setName(null);

        // Create the Area, which fails.

        restAreaMockMvc.perform(post("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isBadRequest());

        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaRepository.findAll().size();
        // set the field null
        area.setCustomerId(null);

        // Create the Area, which fails.

        restAreaMockMvc.perform(post("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isBadRequest());

        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAreas() throws Exception {
        // Initialize the database
        areaRepository.save(area);

        // Get all the areaList
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    public void getArea() throws Exception {
        // Initialize the database
        areaRepository.save(area);

        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", area.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(area.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    public void getNonExistingArea() throws Exception {
        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateArea() throws Exception {
        // Initialize the database
        areaRepository.save(area);
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area
        Area updatedArea = areaRepository.findOne(area.getId());
        updatedArea
                .name(UPDATED_NAME)
                .address(UPDATED_ADDRESS)
                .city(UPDATED_CITY)
                .parentId(UPDATED_PARENT_ID)
                .customerId(UPDATED_CUSTOMER_ID);

        restAreaMockMvc.perform(put("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArea)))
            .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArea.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testArea.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testArea.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testArea.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    public void updateNonExistingArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Create the Area

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAreaMockMvc.perform(put("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(area)))
            .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteArea() throws Exception {
        // Initialize the database
        areaRepository.save(area);
        int databaseSizeBeforeDelete = areaRepository.findAll().size();

        // Get the area
        restAreaMockMvc.perform(delete("/api/areas/{id}", area.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Area.class);
    }
}
