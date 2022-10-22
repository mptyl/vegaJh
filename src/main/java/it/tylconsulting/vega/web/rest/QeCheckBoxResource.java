package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeCheckBoxRepository;
import it.tylconsulting.vega.service.QeCheckBoxService;
import it.tylconsulting.vega.service.dto.QeCheckBoxDTO;
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
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeCheckBox}.
 */
@RestController
@RequestMapping("/api")
public class QeCheckBoxResource {

    private final Logger log = LoggerFactory.getLogger(QeCheckBoxResource.class);

    private static final String ENTITY_NAME = "qeCheckBox";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeCheckBoxService qeCheckBoxService;

    private final QeCheckBoxRepository qeCheckBoxRepository;

    public QeCheckBoxResource(QeCheckBoxService qeCheckBoxService, QeCheckBoxRepository qeCheckBoxRepository) {
        this.qeCheckBoxService = qeCheckBoxService;
        this.qeCheckBoxRepository = qeCheckBoxRepository;
    }

    /**
     * {@code POST  /qe-check-boxes} : Create a new qeCheckBox.
     *
     * @param qeCheckBoxDTO the qeCheckBoxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeCheckBoxDTO, or with status {@code 400 (Bad Request)} if the qeCheckBox has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-check-boxes")
    public ResponseEntity<QeCheckBoxDTO> createQeCheckBox(@RequestBody QeCheckBoxDTO qeCheckBoxDTO) throws URISyntaxException {
        log.debug("REST request to save QeCheckBox : {}", qeCheckBoxDTO);
        if (qeCheckBoxDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeCheckBox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeCheckBoxDTO result = qeCheckBoxService.save(qeCheckBoxDTO);
        return ResponseEntity
            .created(new URI("/api/qe-check-boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-check-boxes/:id} : Updates an existing qeCheckBox.
     *
     * @param id the id of the qeCheckBoxDTO to save.
     * @param qeCheckBoxDTO the qeCheckBoxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeCheckBoxDTO,
     * or with status {@code 400 (Bad Request)} if the qeCheckBoxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeCheckBoxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-check-boxes/{id}")
    public ResponseEntity<QeCheckBoxDTO> updateQeCheckBox(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeCheckBoxDTO qeCheckBoxDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeCheckBox : {}, {}", id, qeCheckBoxDTO);
        if (qeCheckBoxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeCheckBoxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeCheckBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeCheckBoxDTO result = qeCheckBoxService.update(qeCheckBoxDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeCheckBoxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-check-boxes/:id} : Partial updates given fields of an existing qeCheckBox, field will ignore if it is null
     *
     * @param id the id of the qeCheckBoxDTO to save.
     * @param qeCheckBoxDTO the qeCheckBoxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeCheckBoxDTO,
     * or with status {@code 400 (Bad Request)} if the qeCheckBoxDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeCheckBoxDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeCheckBoxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-check-boxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeCheckBoxDTO> partialUpdateQeCheckBox(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeCheckBoxDTO qeCheckBoxDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeCheckBox partially : {}, {}", id, qeCheckBoxDTO);
        if (qeCheckBoxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeCheckBoxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeCheckBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeCheckBoxDTO> result = qeCheckBoxService.partialUpdate(qeCheckBoxDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeCheckBoxDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-check-boxes} : get all the qeCheckBoxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeCheckBoxes in body.
     */
    @GetMapping("/qe-check-boxes")
    public List<QeCheckBoxDTO> getAllQeCheckBoxes() {
        log.debug("REST request to get all QeCheckBoxes");
        return qeCheckBoxService.findAll();
    }

    /**
     * {@code GET  /qe-check-boxes/:id} : get the "id" qeCheckBox.
     *
     * @param id the id of the qeCheckBoxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeCheckBoxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-check-boxes/{id}")
    public ResponseEntity<QeCheckBoxDTO> getQeCheckBox(@PathVariable Long id) {
        log.debug("REST request to get QeCheckBox : {}", id);
        Optional<QeCheckBoxDTO> qeCheckBoxDTO = qeCheckBoxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeCheckBoxDTO);
    }

    /**
     * {@code DELETE  /qe-check-boxes/:id} : delete the "id" qeCheckBox.
     *
     * @param id the id of the qeCheckBoxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-check-boxes/{id}")
    public ResponseEntity<Void> deleteQeCheckBox(@PathVariable Long id) {
        log.debug("REST request to delete QeCheckBox : {}", id);
        qeCheckBoxService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
