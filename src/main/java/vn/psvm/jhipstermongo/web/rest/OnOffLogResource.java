package vn.psvm.jhipstermongo.web.rest;

import com.codahale.metrics.annotation.Timed;
import vn.psvm.jhipstermongo.domain.OnOffLog;

import vn.psvm.jhipstermongo.repository.OnOffLogRepository;
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
 * REST controller for managing OnOffLog.
 */
@RestController
@RequestMapping("/api")
public class OnOffLogResource {

    private final Logger log = LoggerFactory.getLogger(OnOffLogResource.class);

    private static final String ENTITY_NAME = "onOffLog";
        
    private final OnOffLogRepository onOffLogRepository;

    public OnOffLogResource(OnOffLogRepository onOffLogRepository) {
        this.onOffLogRepository = onOffLogRepository;
    }

    /**
     * POST  /on-off-logs : Create a new onOffLog.
     *
     * @param onOffLog the onOffLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new onOffLog, or with status 400 (Bad Request) if the onOffLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/on-off-logs")
    @Timed
    public ResponseEntity<OnOffLog> createOnOffLog(@Valid @RequestBody OnOffLog onOffLog) throws URISyntaxException {
        log.debug("REST request to save OnOffLog : {}", onOffLog);
        if (onOffLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new onOffLog cannot already have an ID")).body(null);
        }
        OnOffLog result = onOffLogRepository.save(onOffLog);
        return ResponseEntity.created(new URI("/api/on-off-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /on-off-logs : Updates an existing onOffLog.
     *
     * @param onOffLog the onOffLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated onOffLog,
     * or with status 400 (Bad Request) if the onOffLog is not valid,
     * or with status 500 (Internal Server Error) if the onOffLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/on-off-logs")
    @Timed
    public ResponseEntity<OnOffLog> updateOnOffLog(@Valid @RequestBody OnOffLog onOffLog) throws URISyntaxException {
        log.debug("REST request to update OnOffLog : {}", onOffLog);
        if (onOffLog.getId() == null) {
            return createOnOffLog(onOffLog);
        }
        OnOffLog result = onOffLogRepository.save(onOffLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, onOffLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /on-off-logs : get all the onOffLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of onOffLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/on-off-logs")
    @Timed
    public ResponseEntity<List<OnOffLog>> getAllOnOffLogs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of OnOffLogs");
        Page<OnOffLog> page = onOffLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/on-off-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /on-off-logs/:id : get the "id" onOffLog.
     *
     * @param id the id of the onOffLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the onOffLog, or with status 404 (Not Found)
     */
    @GetMapping("/on-off-logs/{id}")
    @Timed
    public ResponseEntity<OnOffLog> getOnOffLog(@PathVariable String id) {
        log.debug("REST request to get OnOffLog : {}", id);
        OnOffLog onOffLog = onOffLogRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(onOffLog));
    }

    /**
     * DELETE  /on-off-logs/:id : delete the "id" onOffLog.
     *
     * @param id the id of the onOffLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/on-off-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOnOffLog(@PathVariable String id) {
        log.debug("REST request to delete OnOffLog : {}", id);
        onOffLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
