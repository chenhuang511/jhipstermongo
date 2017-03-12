package vn.psvm.jhipstermongo.web.rest;

import vn.psvm.jhipstermongo.JhipstermongoApp;

import vn.psvm.jhipstermongo.domain.Switcher;
import vn.psvm.jhipstermongo.repository.SwitcherRepository;

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
 * Test class for the SwitcherResource REST controller.
 *
 * @see SwitcherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipstermongoApp.class)
public class SwitcherResourceIntTest {

    private static final Long DEFAULT_DEVICE_ID = 1L;
    private static final Long UPDATED_DEVICE_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Long DEFAULT_COUNTER = 1L;
    private static final Long UPDATED_COUNTER = 2L;

    private static final Long DEFAULT_COUNT_TIMING = 1L;
    private static final Long UPDATED_COUNT_TIMING = 2L;

    @Autowired
    private SwitcherRepository switcherRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSwitcherMockMvc;

    private Switcher switcher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SwitcherResource switcherResource = new SwitcherResource(switcherRepository);
        this.restSwitcherMockMvc = MockMvcBuilders.standaloneSetup(switcherResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Switcher createEntity() {
        Switcher switcher = new Switcher()
                .deviceId(DEFAULT_DEVICE_ID)
                .name(DEFAULT_NAME)
                .number(DEFAULT_NUMBER)
                .status(DEFAULT_STATUS)
                .counter(DEFAULT_COUNTER)
                .countTiming(DEFAULT_COUNT_TIMING);
        return switcher;
    }

    @Before
    public void initTest() {
        switcherRepository.deleteAll();
        switcher = createEntity();
    }

    @Test
    public void createSwitcher() throws Exception {
        int databaseSizeBeforeCreate = switcherRepository.findAll().size();

        // Create the Switcher

        restSwitcherMockMvc.perform(post("/api/switchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(switcher)))
            .andExpect(status().isCreated());

        // Validate the Switcher in the database
        List<Switcher> switcherList = switcherRepository.findAll();
        assertThat(switcherList).hasSize(databaseSizeBeforeCreate + 1);
        Switcher testSwitcher = switcherList.get(switcherList.size() - 1);
        assertThat(testSwitcher.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testSwitcher.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSwitcher.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testSwitcher.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSwitcher.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testSwitcher.getCountTiming()).isEqualTo(DEFAULT_COUNT_TIMING);
    }

    @Test
    public void createSwitcherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = switcherRepository.findAll().size();

        // Create the Switcher with an existing ID
        Switcher existingSwitcher = new Switcher();
        existingSwitcher.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSwitcherMockMvc.perform(post("/api/switchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSwitcher)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Switcher> switcherList = switcherRepository.findAll();
        assertThat(switcherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = switcherRepository.findAll().size();
        // set the field null
        switcher.setDeviceId(null);

        // Create the Switcher, which fails.

        restSwitcherMockMvc.perform(post("/api/switchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(switcher)))
            .andExpect(status().isBadRequest());

        List<Switcher> switcherList = switcherRepository.findAll();
        assertThat(switcherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = switcherRepository.findAll().size();
        // set the field null
        switcher.setNumber(null);

        // Create the Switcher, which fails.

        restSwitcherMockMvc.perform(post("/api/switchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(switcher)))
            .andExpect(status().isBadRequest());

        List<Switcher> switcherList = switcherRepository.findAll();
        assertThat(switcherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSwitchers() throws Exception {
        // Initialize the database
        switcherRepository.save(switcher);

        // Get all the switcherList
        restSwitcherMockMvc.perform(get("/api/switchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(switcher.getId())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].countTiming").value(hasItem(DEFAULT_COUNT_TIMING.intValue())));
    }

    @Test
    public void getSwitcher() throws Exception {
        // Initialize the database
        switcherRepository.save(switcher);

        // Get the switcher
        restSwitcherMockMvc.perform(get("/api/switchers/{id}", switcher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(switcher.getId()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
            .andExpect(jsonPath("$.countTiming").value(DEFAULT_COUNT_TIMING.intValue()));
    }

    @Test
    public void getNonExistingSwitcher() throws Exception {
        // Get the switcher
        restSwitcherMockMvc.perform(get("/api/switchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSwitcher() throws Exception {
        // Initialize the database
        switcherRepository.save(switcher);
        int databaseSizeBeforeUpdate = switcherRepository.findAll().size();

        // Update the switcher
        Switcher updatedSwitcher = switcherRepository.findOne(switcher.getId());
        updatedSwitcher
                .deviceId(UPDATED_DEVICE_ID)
                .name(UPDATED_NAME)
                .number(UPDATED_NUMBER)
                .status(UPDATED_STATUS)
                .counter(UPDATED_COUNTER)
                .countTiming(UPDATED_COUNT_TIMING);

        restSwitcherMockMvc.perform(put("/api/switchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSwitcher)))
            .andExpect(status().isOk());

        // Validate the Switcher in the database
        List<Switcher> switcherList = switcherRepository.findAll();
        assertThat(switcherList).hasSize(databaseSizeBeforeUpdate);
        Switcher testSwitcher = switcherList.get(switcherList.size() - 1);
        assertThat(testSwitcher.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testSwitcher.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSwitcher.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSwitcher.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSwitcher.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testSwitcher.getCountTiming()).isEqualTo(UPDATED_COUNT_TIMING);
    }

    @Test
    public void updateNonExistingSwitcher() throws Exception {
        int databaseSizeBeforeUpdate = switcherRepository.findAll().size();

        // Create the Switcher

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSwitcherMockMvc.perform(put("/api/switchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(switcher)))
            .andExpect(status().isCreated());

        // Validate the Switcher in the database
        List<Switcher> switcherList = switcherRepository.findAll();
        assertThat(switcherList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSwitcher() throws Exception {
        // Initialize the database
        switcherRepository.save(switcher);
        int databaseSizeBeforeDelete = switcherRepository.findAll().size();

        // Get the switcher
        restSwitcherMockMvc.perform(delete("/api/switchers/{id}", switcher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Switcher> switcherList = switcherRepository.findAll();
        assertThat(switcherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Switcher.class);
    }
}
