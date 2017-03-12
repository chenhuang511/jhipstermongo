package vn.psvm.jhipstermongo.web.rest;

import com.codahale.metrics.annotation.Timed;
import vn.psvm.jhipstermongo.domain.Switcher;

import vn.psvm.jhipstermongo.repository.SwitcherRepository;
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
 * REST controller for managing Switcher.
 */
@RestController
@RequestMapping("/api")
public class SwitcherResource {

    private final Logger log = LoggerFactory.getLogger(SwitcherResource.class);

    private static final String ENTITY_NAME = "switcher";
        
    private final SwitcherRepository switcherRepository;

    public SwitcherResource(SwitcherRepository switcherRepository) {
        this.switcherRepository = switcherRepository;
    }

    /**
     * POST  /switchers : Create a new switcher.
     *
     * @param switcher the switcher to create
     * @return the ResponseEntity with status 201 (Created) and with body the new switcher, or with status 400 (Bad Request) if the switcher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/switchers")
    @Timed
    public ResponseEntity<Switcher> createSwitcher(@Valid @RequestBody Switcher switcher) throws URISyntaxException {
        log.debug("REST request to save Switcher : {}", switcher);
        if (switcher.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new switcher cannot already have an ID")).body(null);
        }
        Switcher result = switcherRepository.save(switcher);
        return ResponseEntity.created(new URI("/api/switchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /switchers : Updates an existing switcher.
     *
     * @param switcher the switcher to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated switcher,
     * or with status 400 (Bad Request) if the switcher is not valid,
     * or with status 500 (Internal Server Error) if the switcher couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/switchers")
    @Timed
    public ResponseEntity<Switcher> updateSwitcher(@Valid @RequestBody Switcher switcher) throws URISyntaxException {
        log.debug("REST request to update Switcher : {}", switcher);
        if (switcher.getId() == null) {
            return createSwitcher(switcher);
        }
        Switcher result = switcherRepository.save(switcher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, switcher.getId().toString()))
            .body(result);
    }

    /**
     * GET  /switchers : get all the switchers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of switchers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/switchers")
    @Timed
    public ResponseEntity<List<Switcher>> getAllSwitchers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Switchers");
        Page<Switcher> page = switcherRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/switchers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /switchers/:id : get the "id" switcher.
     *
     * @param id the id of the switcher to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the switcher, or with status 404 (Not Found)
     */
    @GetMapping("/switchers/{id}")
    @Timed
    public ResponseEntity<Switcher> getSwitcher(@PathVariable String id) {
        log.debug("REST request to get Switcher : {}", id);
        Switcher switcher = switcherRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(switcher));
    }

    /**
     * DELETE  /switchers/:id : delete the "id" switcher.
     *
     * @param id the id of the switcher to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/switchers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSwitcher(@PathVariable String id) {
        log.debug("REST request to delete Switcher : {}", id);
        switcherRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
