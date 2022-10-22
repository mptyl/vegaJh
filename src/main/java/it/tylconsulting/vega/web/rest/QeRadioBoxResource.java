package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeRadioBoxRepository;
import it.tylconsulting.vega.service.QeRadioBoxService;
import it.tylconsulting.vega.service.dto.QeRadioBoxDTO;
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
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeRadioBox}.
 */
@RestController
@RequestMapping("/api")
public class QeRadioBoxResource {

    private final Logger log = LoggerFactory.getLogger(QeRadioBoxResource.class);

    private static final String ENTITY_NAME = "qeRadioBox";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeRadioBoxService qeRadioBoxService;

    private final QeRadioBoxRepository qeRadioBoxRepository;

    public QeRadioBoxResource(QeRadioBoxService qeRadioBoxService, QeRadioBoxRepository qeRadioBoxRepository) {
        this.qeRadioBoxService = qeRadioBoxService;
        this.qeRadioBoxRepository = qeRadioBoxRepository;
    }

    /**
     * {@code POST  /qe-radio-boxes} : Create a new qeRadioBox.
     *
     * @param qeRadioBoxDTO the qeRadioBoxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeRadioBoxDTO, or with status {@code 400 (Bad Request)} if the qeRadioBox has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-radio-boxes")
    public ResponseEntity<QeRadioBoxDTO> createQeRadioBox(@RequestBody QeRadioBoxDTO qeRadioBoxDTO) throws URISyntaxException {
        log.debug("REST request to save QeRadioBox : {}", qeRadioBoxDTO);
        if (qeRadioBoxDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeRadioBox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeRadioBoxDTO result = qeRadioBoxService.save(qeRadioBoxDTO);
        return ResponseEntity
            .created(new URI("/api/qe-radio-boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-radio-boxes/:id} : Updates an existing qeRadioBox.
     *
     * @param id the id of the qeRadioBoxDTO to save.
     * @param qeRadioBoxDTO the qeRadioBoxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeRadioBoxDTO,
     * or with status {@code 400 (Bad Request)} if the qeRadioBoxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeRadioBoxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-radio-boxes/{id}")
    public ResponseEntity<QeRadioBoxDTO> updateQeRadioBox(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeRadioBoxDTO qeRadioBoxDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeRadioBox : {}, {}", id, qeRadioBoxDTO);
        if (qeRadioBoxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeRadioBoxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeRadioBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeRadioBoxDTO result = qeRadioBoxService.update(qeRadioBoxDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeRadioBoxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-radio-boxes/:id} : Partial updates given fields of an existing qeRadioBox, field will ignore if it is null
     *
     * @param id the id of the qeRadioBoxDTO to save.
     * @param qeRadioBoxDTO the qeRadioBoxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeRadioBoxDTO,
     * or with status {@code 400 (Bad Request)} if the qeRadioBoxDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeRadioBoxDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeRadioBoxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-radio-boxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeRadioBoxDTO> partialUpdateQeRadioBox(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeRadioBoxDTO qeRadioBoxDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeRadioBox partially : {}, {}", id, qeRadioBoxDTO);
        if (qeRadioBoxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeRadioBoxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeRadioBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeRadioBoxDTO> result = qeRadioBoxService.partialUpdate(qeRadioBoxDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeRadioBoxDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-radio-boxes} : get all the qeRadioBoxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeRadioBoxes in body.
     */
    @GetMapping("/qe-radio-boxes")
    public List<QeRadioBoxDTO> getAllQeRadioBoxes() {
        log.debug("REST request to get all QeRadioBoxes");
        return qeRadioBoxService.findAll();
    }

    /**
     * {@code GET  /qe-radio-boxes/:id} : get the "id" qeRadioBox.
     *
     * @param id the id of the qeRadioBoxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeRadioBoxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-radio-boxes/{id}")
    public ResponseEntity<QeRadioBoxDTO> getQeRadioBox(@PathVariable Long id) {
        log.debug("REST request to get QeRadioBox : {}", id);
        Optional<QeRadioBoxDTO> qeRadioBoxDTO = qeRadioBoxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeRadioBoxDTO);
    }

    /**
     * {@code DELETE  /qe-radio-boxes/:id} : delete the "id" qeRadioBox.
     *
     * @param id the id of the qeRadioBoxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-radio-boxes/{id}")
    public ResponseEntity<Void> deleteQeRadioBox(@PathVariable Long id) {
        log.debug("REST request to delete QeRadioBox : {}", id);
        qeRadioBoxService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
