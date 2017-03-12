package vn.psvm.jhipstermongo.web.rest;

import com.codahale.metrics.annotation.Timed;
import vn.psvm.jhipstermongo.domain.TimingLog;

import vn.psvm.jhipstermongo.repository.TimingLogRepository;
import vn.psvm.jhipstermongo.web.rest.util.HeaderUtil;
import vn.psvm.jhipstermongo.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TimingLog.
 */
@RestController
@RequestMapping("/api")
public class TimingLogResource {

    private final Logger log = LoggerFactory.getLogger(TimingLogResource.class);

    private static final String ENTITY_NAME = "timingLog";
        
    private final TimingLogRepository timingLogRepository;

    public TimingLogResource(TimingLogRepository timingLogRepository) {
        this.timingLogRepository = timingLogRepository;
    }

    /**
     * POST  /timing-logs : Create a new timingLog.
     *
     * @param timingLog the timingLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timingLog, or with status 400 (Bad Request) if the timingLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timing-logs")
    @Timed
    public ResponseEntity<TimingLog> createTimingLog(@Valid @RequestBody TimingLog timingLog) throws URISyntaxException {
        log.debug("REST request to save TimingLog : {}", timingLog);
        if (timingLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new timingLog cannot already have an ID")).body(null);
        }
        TimingLog result = timingLogRepository.save(timingLog);
        return ResponseEntity.created(new URI("/api/timing-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timing-logs : Updates an existing timingLog.
     *
     * @param timingLog the timingLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timingLog,
     * or with status 400 (Bad Request) if the timingLog is not valid,
     * or with status 500 (Internal Server Error) if the timingLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timing-logs")
    @Timed
    public ResponseEntity<TimingLog> updateTimingLog(@Valid @RequestBody TimingLog timingLog) throws URISyntaxException {
        log.debug("REST request to update TimingLog : {}", timingLog);
        if (timingLog.getId() == null) {
            return createTimingLog(timingLog);
        }
        TimingLog result = timingLogRepository.save(timingLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timingLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timing-logs : get all the timingLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timingLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/timing-logs")
    @Timed
    public ResponseEntity<List<TimingLog>> getAllTimingLogs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TimingLogs");
        Page<TimingLog> page = timingLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timing-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timing-logs/:id : get the "id" timingLog.
     *
     * @param id the id of the timingLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timingLog, or with status 404 (Not Found)
     */
    @GetMapping("/timing-logs/{id}")
    @Timed
    public ResponseEntity<TimingLog> getTimingLog(@PathVariable String id) {
        log.debug("REST request to get TimingLog : {}", id);
        TimingLog timingLog = timingLogRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timingLog));
    }

    /**
     * DELETE  /timing-logs/:id : delete the "id" timingLog.
     *
     * @param id the id of the timingLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timing-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimingLog(@PathVariable String id) {
        log.debug("REST request to delete TimingLog : {}", id);
        timingLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
