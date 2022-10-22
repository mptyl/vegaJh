package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeJumpExpressionRepository;
import it.tylconsulting.vega.service.QeJumpExpressionService;
import it.tylconsulting.vega.service.dto.QeJumpExpressionDTO;
import it.tylconsulting.vega.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeJumpExpression}.
 */
@RestController
@RequestMapping("/api")
public class QeJumpExpressionResource {

    private final Logger log = LoggerFactory.getLogger(QeJumpExpressionResource.class);

    private static final String ENTITY_NAME = "qeJumpExpression";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeJumpExpressionService qeJumpExpressionService;

    private final QeJumpExpressionRepository qeJumpExpressionRepository;

    public QeJumpExpressionResource(
        QeJumpExpressionService qeJumpExpressionService,
        QeJumpExpressionRepository qeJumpExpressionRepository
    ) {
        this.qeJumpExpressionService = qeJumpExpressionService;
        this.qeJumpExpressionRepository = qeJumpExpressionRepository;
    }

    /**
     * {@code POST  /qe-jump-expressions} : Create a new qeJumpExpression.
     *
     * @param qeJumpExpressionDTO the qeJumpExpressionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeJumpExpressionDTO, or with status {@code 400 (Bad Request)} if the qeJumpExpression has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-jump-expressions")
    public ResponseEntity<QeJumpExpressionDTO> createQeJumpExpression(@RequestBody QeJumpExpressionDTO qeJumpExpressionDTO)
        throws URISyntaxException {
        log.debug("REST request to save QeJumpExpression : {}", qeJumpExpressionDTO);
        if (qeJumpExpressionDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeJumpExpression cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeJumpExpressionDTO result = qeJumpExpressionService.save(qeJumpExpressionDTO);
        return ResponseEntity
            .created(new URI("/api/qe-jump-expressions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-jump-expressions/:id} : Updates an existing qeJumpExpression.
     *
     * @param id the id of the qeJumpExpressionDTO to save.
     * @param qeJumpExpressionDTO the qeJumpExpressionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeJumpExpressionDTO,
     * or with status {@code 400 (Bad Request)} if the qeJumpExpressionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeJumpExpressionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-jump-expressions/{id}")
    public ResponseEntity<QeJumpExpressionDTO> updateQeJumpExpression(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeJumpExpressionDTO qeJumpExpressionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeJumpExpression : {}, {}", id, qeJumpExpressionDTO);
        if (qeJumpExpressionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeJumpExpressionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeJumpExpressionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeJumpExpressionDTO result = qeJumpExpressionService.update(qeJumpExpressionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeJumpExpressionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-jump-expressions/:id} : Partial updates given fields of an existing qeJumpExpression, field will ignore if it is null
     *
     * @param id the id of the qeJumpExpressionDTO to save.
     * @param qeJumpExpressionDTO the qeJumpExpressionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeJumpExpressionDTO,
     * or with status {@code 400 (Bad Request)} if the qeJumpExpressionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeJumpExpressionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeJumpExpressionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-jump-expressions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeJumpExpressionDTO> partialUpdateQeJumpExpression(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeJumpExpressionDTO qeJumpExpressionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeJumpExpression partially : {}, {}", id, qeJumpExpressionDTO);
        if (qeJumpExpressionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeJumpExpressionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeJumpExpressionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeJumpExpressionDTO> result = qeJumpExpressionService.partialUpdate(qeJumpExpressionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeJumpExpressionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-jump-expressions} : get all the qeJumpExpressions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeJumpExpressions in body.
     */
    @GetMapping("/qe-jump-expressions")
    public List<QeJumpExpressionDTO> getAllQeJumpExpressions() {
        log.debug("REST request to get all QeJumpExpressions");
        return qeJumpExpressionService.findAll();
    }

    /**
     * {@code GET  /qe-jump-expressions/:id} : get the "id" qeJumpExpression.
     *
     * @param id the id of the qeJumpExpressionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeJumpExpressionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-jump-expressions/{id}")
    public ResponseEntity<QeJumpExpressionDTO> getQeJumpExpression(@PathVariable Long id) {
        log.debug("REST request to get QeJumpExpression : {}", id);
        Optional<QeJumpExpressionDTO> qeJumpExpressionDTO = qeJumpExpressionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeJumpExpressionDTO);
    }

    /**
     * {@code DELETE  /qe-jump-expressions/:id} : delete the "id" qeJumpExpression.
     *
     * @param id the id of the qeJumpExpressionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-jump-expressions/{id}")
    public ResponseEntity<Void> deleteQeJumpExpression(@PathVariable Long id) {
        log.debug("REST request to delete QeJumpExpression : {}", id);
        qeJumpExpressionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
