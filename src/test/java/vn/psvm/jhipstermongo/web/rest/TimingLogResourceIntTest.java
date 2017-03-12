package vn.psvm.jhipstermongo.web.rest;

import vn.psvm.jhipstermongo.JhipstermongoApp;

import vn.psvm.jhipstermongo.domain.TimingLog;
import vn.psvm.jhipstermongo.repository.TimingLogRepository;

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
 * Test class for the TimingLogResource REST controller.
 *
 * @see TimingLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipstermongoApp.class)
public class TimingLogResourceIntTest {

    private static final String DEFAULT_DATETIME_LOG = "AAAAAAAAAA";
    private static final String UPDATED_DATETIME_LOG = "BBBBBBBBBB";

    private static final Long DEFAULT_SWITCHER_ID = 1L;
    private static final Long UPDATED_SWITCHER_ID = 2L;

    private static final Long DEFAULT_DEVICE_ID = 1L;
    private static final Long UPDATED_DEVICE_ID = 2L;

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_UID = "AAAAAAAAAA";
    private static final String UPDATED_UID = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMMAND = 1;
    private static final Integer UPDATED_COMMAND = 2;

    private static final Integer DEFAULT_VALUE_TIMING = 1;
    private static final Integer UPDATED_VALUE_TIMING = 2;

    private static final String DEFAULT_VALUE_LOOP = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_LOOP = "BBBBBBBBBB";

    private static final Long DEFAULT_ONOFFLOG_ID = 1L;
    private static final Long UPDATED_ONOFFLOG_ID = 2L;

    @Autowired
    private TimingLogRepository timingLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimingLogMockMvc;

    private TimingLog timingLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TimingLogResource timingLogResource = new TimingLogResource(timingLogRepository);
        this.restTimingLogMockMvc = MockMvcBuilders.standaloneSetup(timingLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimingLog createEntity() {
        TimingLog timingLog = new TimingLog()
                .datetimeLog(DEFAULT_DATETIME_LOG)
                .switcherId(DEFAULT_SWITCHER_ID)
                .deviceId(DEFAULT_DEVICE_ID)
                .startTime(DEFAULT_START_TIME)
                .duration(DEFAULT_DURATION)
                .uid(DEFAULT_UID)
                .command(DEFAULT_COMMAND)
                .valueTiming(DEFAULT_VALUE_TIMING)
                .valueLoop(DEFAULT_VALUE_LOOP)
                .onofflogId(DEFAULT_ONOFFLOG_ID);
        return timingLog;
    }

    @Before
    public void initTest() {
        timingLogRepository.deleteAll();
        timingLog = createEntity();
    }

    @Test
    public void createTimingLog() throws Exception {
        int databaseSizeBeforeCreate = timingLogRepository.findAll().size();

        // Create the TimingLog

        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isCreated());

        // Validate the TimingLog in the database
        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeCreate + 1);
        TimingLog testTimingLog = timingLogList.get(timingLogList.size() - 1);
        assertThat(testTimingLog.getDatetimeLog()).isEqualTo(DEFAULT_DATETIME_LOG);
        assertThat(testTimingLog.getSwitcherId()).isEqualTo(DEFAULT_SWITCHER_ID);
        assertThat(testTimingLog.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testTimingLog.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimingLog.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTimingLog.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testTimingLog.getCommand()).isEqualTo(DEFAULT_COMMAND);
        assertThat(testTimingLog.getValueTiming()).isEqualTo(DEFAULT_VALUE_TIMING);
        assertThat(testTimingLog.getValueLoop()).isEqualTo(DEFAULT_VALUE_LOOP);
        assertThat(testTimingLog.getOnofflogId()).isEqualTo(DEFAULT_ONOFFLOG_ID);
    }

    @Test
    public void createTimingLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timingLogRepository.findAll().size();

        // Create the TimingLog with an existing ID
        TimingLog existingTimingLog = new TimingLog();
        existingTimingLog.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTimingLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDatetimeLogIsRequired() throws Exception {
        int databaseSizeBeforeTest = timingLogRepository.findAll().size();
        // set the field null
        timingLog.setDatetimeLog(null);

        // Create the TimingLog, which fails.

        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isBadRequest());

        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSwitcherIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = timingLogRepository.findAll().size();
        // set the field null
        timingLog.setSwitcherId(null);

        // Create the TimingLog, which fails.

        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isBadRequest());

        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = timingLogRepository.findAll().size();
        // set the field null
        timingLog.setDeviceId(null);

        // Create the TimingLog, which fails.

        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isBadRequest());

        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timingLogRepository.findAll().size();
        // set the field null
        timingLog.setStartTime(null);

        // Create the TimingLog, which fails.

        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isBadRequest());

        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = timingLogRepository.findAll().size();
        // set the field null
        timingLog.setDuration(null);

        // Create the TimingLog, which fails.

        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isBadRequest());

        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = timingLogRepository.findAll().size();
        // set the field null
        timingLog.setUid(null);

        // Create the TimingLog, which fails.

        restTimingLogMockMvc.perform(post("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isBadRequest());

        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTimingLogs() throws Exception {
        // Initialize the database
        timingLogRepository.save(timingLog);

        // Get all the timingLogList
        restTimingLogMockMvc.perform(get("/api/timing-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timingLog.getId())))
            .andExpect(jsonPath("$.[*].datetimeLog").value(hasItem(DEFAULT_DATETIME_LOG.toString())))
            .andExpect(jsonPath("$.[*].switcherId").value(hasItem(DEFAULT_SWITCHER_ID.intValue())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].command").value(hasItem(DEFAULT_COMMAND)))
            .andExpect(jsonPath("$.[*].valueTiming").value(hasItem(DEFAULT_VALUE_TIMING)))
            .andExpect(jsonPath("$.[*].valueLoop").value(hasItem(DEFAULT_VALUE_LOOP.toString())))
            .andExpect(jsonPath("$.[*].onofflogId").value(hasItem(DEFAULT_ONOFFLOG_ID.intValue())));
    }

    @Test
    public void getTimingLog() throws Exception {
        // Initialize the database
        timingLogRepository.save(timingLog);

        // Get the timingLog
        restTimingLogMockMvc.perform(get("/api/timing-logs/{id}", timingLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timingLog.getId()))
            .andExpect(jsonPath("$.datetimeLog").value(DEFAULT_DATETIME_LOG.toString()))
            .andExpect(jsonPath("$.switcherId").value(DEFAULT_SWITCHER_ID.intValue()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.command").value(DEFAULT_COMMAND))
            .andExpect(jsonPath("$.valueTiming").value(DEFAULT_VALUE_TIMING))
            .andExpect(jsonPath("$.valueLoop").value(DEFAULT_VALUE_LOOP.toString()))
            .andExpect(jsonPath("$.onofflogId").value(DEFAULT_ONOFFLOG_ID.intValue()));
    }

    @Test
    public void getNonExistingTimingLog() throws Exception {
        // Get the timingLog
        restTimingLogMockMvc.perform(get("/api/timing-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTimingLog() throws Exception {
        // Initialize the database
        timingLogRepository.save(timingLog);
        int databaseSizeBeforeUpdate = timingLogRepository.findAll().size();

        // Update the timingLog
        TimingLog updatedTimingLog = timingLogRepository.findOne(timingLog.getId());
        updatedTimingLog
                .datetimeLog(UPDATED_DATETIME_LOG)
                .switcherId(UPDATED_SWITCHER_ID)
                .deviceId(UPDATED_DEVICE_ID)
                .startTime(UPDATED_START_TIME)
                .duration(UPDATED_DURATION)
                .uid(UPDATED_UID)
                .command(UPDATED_COMMAND)
                .valueTiming(UPDATED_VALUE_TIMING)
                .valueLoop(UPDATED_VALUE_LOOP)
                .onofflogId(UPDATED_ONOFFLOG_ID);

        restTimingLogMockMvc.perform(put("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimingLog)))
            .andExpect(status().isOk());

        // Validate the TimingLog in the database
        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeUpdate);
        TimingLog testTimingLog = timingLogList.get(timingLogList.size() - 1);
        assertThat(testTimingLog.getDatetimeLog()).isEqualTo(UPDATED_DATETIME_LOG);
        assertThat(testTimingLog.getSwitcherId()).isEqualTo(UPDATED_SWITCHER_ID);
        assertThat(testTimingLog.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testTimingLog.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimingLog.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTimingLog.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testTimingLog.getCommand()).isEqualTo(UPDATED_COMMAND);
        assertThat(testTimingLog.getValueTiming()).isEqualTo(UPDATED_VALUE_TIMING);
        assertThat(testTimingLog.getValueLoop()).isEqualTo(UPDATED_VALUE_LOOP);
        assertThat(testTimingLog.getOnofflogId()).isEqualTo(UPDATED_ONOFFLOG_ID);
    }

    @Test
    public void updateNonExistingTimingLog() throws Exception {
        int databaseSizeBeforeUpdate = timingLogRepository.findAll().size();

        // Create the TimingLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimingLogMockMvc.perform(put("/api/timing-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timingLog)))
            .andExpect(status().isCreated());

        // Validate the TimingLog in the database
        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTimingLog() throws Exception {
        // Initialize the database
        timingLogRepository.save(timingLog);
        int databaseSizeBeforeDelete = timingLogRepository.findAll().size();

        // Get the timingLog
        restTimingLogMockMvc.perform(delete("/api/timing-logs/{id}", timingLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimingLog> timingLogList = timingLogRepository.findAll();
        assertThat(timingLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimingLog.class);
    }
}
