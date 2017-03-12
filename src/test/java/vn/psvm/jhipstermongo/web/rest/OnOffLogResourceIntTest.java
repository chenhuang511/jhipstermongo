package vn.psvm.jhipstermongo.web.rest;

import vn.psvm.jhipstermongo.JhipstermongoApp;

import vn.psvm.jhipstermongo.domain.OnOffLog;
import vn.psvm.jhipstermongo.repository.OnOffLogRepository;

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
 * Test class for the OnOffLogResource REST controller.
 *
 * @see OnOffLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipstermongoApp.class)
public class OnOffLogResourceIntTest {

    private static final Long DEFAULT_AREA_ID = 1L;
    private static final Long UPDATED_AREA_ID = 2L;

    private static final Long DEFAULT_DEVICE_ID = 1L;
    private static final Long UPDATED_DEVICE_ID = 2L;

    private static final Long DEFAULT_SWITCH_ID = 1L;
    private static final Long UPDATED_SWITCH_ID = 2L;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final String DEFAULT_DATETIME_LOG = "AAAAAAAAAA";
    private static final String UPDATED_DATETIME_LOG = "BBBBBBBBBB";

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_FINISH_TIME = "AAAAAAAAAA";
    private static final String UPDATED_FINISH_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_UID = "AAAAAAAAAA";
    private static final String UPDATED_UID = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_COMMAND = 1;
    private static final Integer UPDATED_COMMAND = 2;

    private static final String DEFAULT_ADD_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ADD_INFO = "BBBBBBBBBB";

    @Autowired
    private OnOffLogRepository onOffLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOnOffLogMockMvc;

    private OnOffLog onOffLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            OnOffLogResource onOffLogResource = new OnOffLogResource(onOffLogRepository);
        this.restOnOffLogMockMvc = MockMvcBuilders.standaloneSetup(onOffLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OnOffLog createEntity() {
        OnOffLog onOffLog = new OnOffLog()
                .areaId(DEFAULT_AREA_ID)
                .deviceId(DEFAULT_DEVICE_ID)
                .switchId(DEFAULT_SWITCH_ID)
                .customerId(DEFAULT_CUSTOMER_ID)
                .datetimeLog(DEFAULT_DATETIME_LOG)
                .startTime(DEFAULT_START_TIME)
                .finishTime(DEFAULT_FINISH_TIME)
                .uid(DEFAULT_UID)
                .status(DEFAULT_STATUS)
                .type(DEFAULT_TYPE)
                .command(DEFAULT_COMMAND)
                .addInfo(DEFAULT_ADD_INFO);
        return onOffLog;
    }

    @Before
    public void initTest() {
        onOffLogRepository.deleteAll();
        onOffLog = createEntity();
    }

    @Test
    public void createOnOffLog() throws Exception {
        int databaseSizeBeforeCreate = onOffLogRepository.findAll().size();

        // Create the OnOffLog

        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isCreated());

        // Validate the OnOffLog in the database
        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeCreate + 1);
        OnOffLog testOnOffLog = onOffLogList.get(onOffLogList.size() - 1);
        assertThat(testOnOffLog.getAreaId()).isEqualTo(DEFAULT_AREA_ID);
        assertThat(testOnOffLog.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testOnOffLog.getSwitchId()).isEqualTo(DEFAULT_SWITCH_ID);
        assertThat(testOnOffLog.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testOnOffLog.getDatetimeLog()).isEqualTo(DEFAULT_DATETIME_LOG);
        assertThat(testOnOffLog.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testOnOffLog.getFinishTime()).isEqualTo(DEFAULT_FINISH_TIME);
        assertThat(testOnOffLog.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testOnOffLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOnOffLog.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOnOffLog.getCommand()).isEqualTo(DEFAULT_COMMAND);
        assertThat(testOnOffLog.getAddInfo()).isEqualTo(DEFAULT_ADD_INFO);
    }

    @Test
    public void createOnOffLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = onOffLogRepository.findAll().size();

        // Create the OnOffLog with an existing ID
        OnOffLog existingOnOffLog = new OnOffLog();
        existingOnOffLog.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOnOffLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = onOffLogRepository.findAll().size();
        // set the field null
        onOffLog.setDeviceId(null);

        // Create the OnOffLog, which fails.

        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isBadRequest());

        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSwitchIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = onOffLogRepository.findAll().size();
        // set the field null
        onOffLog.setSwitchId(null);

        // Create the OnOffLog, which fails.

        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isBadRequest());

        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = onOffLogRepository.findAll().size();
        // set the field null
        onOffLog.setCustomerId(null);

        // Create the OnOffLog, which fails.

        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isBadRequest());

        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDatetimeLogIsRequired() throws Exception {
        int databaseSizeBeforeTest = onOffLogRepository.findAll().size();
        // set the field null
        onOffLog.setDatetimeLog(null);

        // Create the OnOffLog, which fails.

        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isBadRequest());

        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = onOffLogRepository.findAll().size();
        // set the field null
        onOffLog.setUid(null);

        // Create the OnOffLog, which fails.

        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isBadRequest());

        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCommandIsRequired() throws Exception {
        int databaseSizeBeforeTest = onOffLogRepository.findAll().size();
        // set the field null
        onOffLog.setCommand(null);

        // Create the OnOffLog, which fails.

        restOnOffLogMockMvc.perform(post("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isBadRequest());

        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllOnOffLogs() throws Exception {
        // Initialize the database
        onOffLogRepository.save(onOffLog);

        // Get all the onOffLogList
        restOnOffLogMockMvc.perform(get("/api/on-off-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onOffLog.getId())))
            .andExpect(jsonPath("$.[*].areaId").value(hasItem(DEFAULT_AREA_ID.intValue())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].switchId").value(hasItem(DEFAULT_SWITCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].datetimeLog").value(hasItem(DEFAULT_DATETIME_LOG.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].finishTime").value(hasItem(DEFAULT_FINISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].command").value(hasItem(DEFAULT_COMMAND)))
            .andExpect(jsonPath("$.[*].addInfo").value(hasItem(DEFAULT_ADD_INFO.toString())));
    }

    @Test
    public void getOnOffLog() throws Exception {
        // Initialize the database
        onOffLogRepository.save(onOffLog);

        // Get the onOffLog
        restOnOffLogMockMvc.perform(get("/api/on-off-logs/{id}", onOffLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(onOffLog.getId()))
            .andExpect(jsonPath("$.areaId").value(DEFAULT_AREA_ID.intValue()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.intValue()))
            .andExpect(jsonPath("$.switchId").value(DEFAULT_SWITCH_ID.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.datetimeLog").value(DEFAULT_DATETIME_LOG.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.finishTime").value(DEFAULT_FINISH_TIME.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.command").value(DEFAULT_COMMAND))
            .andExpect(jsonPath("$.addInfo").value(DEFAULT_ADD_INFO.toString()));
    }

    @Test
    public void getNonExistingOnOffLog() throws Exception {
        // Get the onOffLog
        restOnOffLogMockMvc.perform(get("/api/on-off-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOnOffLog() throws Exception {
        // Initialize the database
        onOffLogRepository.save(onOffLog);
        int databaseSizeBeforeUpdate = onOffLogRepository.findAll().size();

        // Update the onOffLog
        OnOffLog updatedOnOffLog = onOffLogRepository.findOne(onOffLog.getId());
        updatedOnOffLog
                .areaId(UPDATED_AREA_ID)
                .deviceId(UPDATED_DEVICE_ID)
                .switchId(UPDATED_SWITCH_ID)
                .customerId(UPDATED_CUSTOMER_ID)
                .datetimeLog(UPDATED_DATETIME_LOG)
                .startTime(UPDATED_START_TIME)
                .finishTime(UPDATED_FINISH_TIME)
                .uid(UPDATED_UID)
                .status(UPDATED_STATUS)
                .type(UPDATED_TYPE)
                .command(UPDATED_COMMAND)
                .addInfo(UPDATED_ADD_INFO);

        restOnOffLogMockMvc.perform(put("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOnOffLog)))
            .andExpect(status().isOk());

        // Validate the OnOffLog in the database
        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeUpdate);
        OnOffLog testOnOffLog = onOffLogList.get(onOffLogList.size() - 1);
        assertThat(testOnOffLog.getAreaId()).isEqualTo(UPDATED_AREA_ID);
        assertThat(testOnOffLog.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testOnOffLog.getSwitchId()).isEqualTo(UPDATED_SWITCH_ID);
        assertThat(testOnOffLog.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testOnOffLog.getDatetimeLog()).isEqualTo(UPDATED_DATETIME_LOG);
        assertThat(testOnOffLog.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testOnOffLog.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testOnOffLog.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testOnOffLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOnOffLog.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOnOffLog.getCommand()).isEqualTo(UPDATED_COMMAND);
        assertThat(testOnOffLog.getAddInfo()).isEqualTo(UPDATED_ADD_INFO);
    }

    @Test
    public void updateNonExistingOnOffLog() throws Exception {
        int databaseSizeBeforeUpdate = onOffLogRepository.findAll().size();

        // Create the OnOffLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOnOffLogMockMvc.perform(put("/api/on-off-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(onOffLog)))
            .andExpect(status().isCreated());

        // Validate the OnOffLog in the database
        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteOnOffLog() throws Exception {
        // Initialize the database
        onOffLogRepository.save(onOffLog);
        int databaseSizeBeforeDelete = onOffLogRepository.findAll().size();

        // Get the onOffLog
        restOnOffLogMockMvc.perform(delete("/api/on-off-logs/{id}", onOffLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OnOffLog> onOffLogList = onOffLogRepository.findAll();
        assertThat(onOffLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OnOffLog.class);
    }
}
