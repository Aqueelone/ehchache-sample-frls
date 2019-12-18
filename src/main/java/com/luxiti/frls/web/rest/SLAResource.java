package com.luxiti.frls.web.rest;

import com.luxiti.frls.service.SLAService;
import com.luxiti.frls.web.rest.errors.BadRequestAlertException;
import com.luxiti.frls.service.dto.SLADTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.luxiti.frls.domain.SLA}.
 */
@RestController
@RequestMapping("/api")
public class SLAResource {

    private final Logger log = LoggerFactory.getLogger(SLAResource.class);

    private static final String ENTITY_NAME = "sLA";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SLAService sLAService;

    public SLAResource(SLAService sLAService) {
        this.sLAService = sLAService;
    }

    /**
     * {@code POST  /slas} : Create a new sLA.
     *
     * @param sLADTO the sLADTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sLADTO, or with status {@code 400 (Bad Request)} if the sLA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/slas")
    public ResponseEntity<SLADTO> createSLA(@RequestBody SLADTO sLADTO) throws URISyntaxException {
        log.debug("REST request to save SLA : {}", sLADTO);
        if (sLADTO.getId() != null) {
            throw new BadRequestAlertException("A new sLA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SLADTO result = sLAService.save(sLADTO);
        return ResponseEntity.created(new URI("/api/slas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /slas} : Updates an existing sLA.
     *
     * @param sLADTO the sLADTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sLADTO,
     * or with status {@code 400 (Bad Request)} if the sLADTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sLADTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/slas")
    public ResponseEntity<SLADTO> updateSLA(@RequestBody SLADTO sLADTO) throws URISyntaxException {
        log.debug("REST request to update SLA : {}", sLADTO);
        if (sLADTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SLADTO result = sLAService.save(sLADTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sLADTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /slas} : get all the sLAS.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sLAS in body.
     */
    @GetMapping("/slas")
    public ResponseEntity<List<SLADTO>> getAllSLAS(Pageable pageable) {
        log.debug("REST request to get a page of SLAS");
        Page<SLADTO> page = sLAService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /slas/:id} : get the "id" sLA.
     *
     * @param id the id of the sLADTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sLADTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/slas/{id}")
    public ResponseEntity<SLADTO> getSLA(@PathVariable Long id) {
        log.debug("REST request to get SLA : {}", id);
        Optional<SLADTO> sLADTO = sLAService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sLADTO);
    }

    /**
     * {@code DELETE  /slas/:id} : delete the "id" sLA.
     *
     * @param id the id of the sLADTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/slas/{id}")
    public ResponseEntity<Void> deleteSLA(@PathVariable Long id) {
        log.debug("REST request to delete SLA : {}", id);
        sLAService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
