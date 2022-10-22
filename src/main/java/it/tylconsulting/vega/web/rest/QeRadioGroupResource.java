package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeRadioGroupRepository;
import it.tylconsulting.vega.service.QeRadioGroupService;
import it.tylconsulting.vega.service.dto.QeRadioGroupDTO;
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
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeRadioGroup}.
 */
@RestController
@RequestMapping("/api")
public class QeRadioGroupResource {

    private final Logger log = LoggerFactory.getLogger(QeRadioGroupResource.class);

    private static final String ENTITY_NAME = "qeRadioGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeRadioGroupService qeRadioGroupService;

    private final QeRadioGroupRepository qeRadioGroupRepository;

    public QeRadioGroupResource(QeRadioGroupService qeRadioGroupService, QeRadioGroupRepository qeRadioGroupRepository) {
        this.qeRadioGroupService = qeRadioGroupService;
        this.qeRadioGroupRepository = qeRadioGroupRepository;
    }

    /**
     * {@code POST  /qe-radio-groups} : Create a new qeRadioGroup.
     *
     * @param qeRadioGroupDTO the qeRadioGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeRadioGroupDTO, or with status {@code 400 (Bad Request)} if the qeRadioGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-radio-groups")
    public ResponseEntity<QeRadioGroupDTO> createQeRadioGroup(@RequestBody QeRadioGroupDTO qeRadioGroupDTO) throws URISyntaxException {
        log.debug("REST request to save QeRadioGroup : {}", qeRadioGroupDTO);
        if (qeRadioGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeRadioGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeRadioGroupDTO result = qeRadioGroupService.save(qeRadioGroupDTO);
        return ResponseEntity
            .created(new URI("/api/qe-radio-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-radio-groups/:id} : Updates an existing qeRadioGroup.
     *
     * @param id the id of the qeRadioGroupDTO to save.
     * @param qeRadioGroupDTO the qeRadioGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeRadioGroupDTO,
     * or with status {@code 400 (Bad Request)} if the qeRadioGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeRadioGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-radio-groups/{id}")
    public ResponseEntity<QeRadioGroupDTO> updateQeRadioGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeRadioGroupDTO qeRadioGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeRadioGroup : {}, {}", id, qeRadioGroupDTO);
        if (qeRadioGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeRadioGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeRadioGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeRadioGroupDTO result = qeRadioGroupService.update(qeRadioGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeRadioGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-radio-groups/:id} : Partial updates given fields of an existing qeRadioGroup, field will ignore if it is null
     *
     * @param id the id of the qeRadioGroupDTO to save.
     * @param qeRadioGroupDTO the qeRadioGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeRadioGroupDTO,
     * or with status {@code 400 (Bad Request)} if the qeRadioGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeRadioGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeRadioGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-radio-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeRadioGroupDTO> partialUpdateQeRadioGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeRadioGroupDTO qeRadioGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeRadioGroup partially : {}, {}", id, qeRadioGroupDTO);
        if (qeRadioGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeRadioGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeRadioGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeRadioGroupDTO> result = qeRadioGroupService.partialUpdate(qeRadioGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeRadioGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-radio-groups} : get all the qeRadioGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeRadioGroups in body.
     */
    @GetMapping("/qe-radio-groups")
    public List<QeRadioGroupDTO> getAllQeRadioGroups() {
        log.debug("REST request to get all QeRadioGroups");
        return qeRadioGroupService.findAll();
    }

    /**
     * {@code GET  /qe-radio-groups/:id} : get the "id" qeRadioGroup.
     *
     * @param id the id of the qeRadioGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeRadioGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-radio-groups/{id}")
    public ResponseEntity<QeRadioGroupDTO> getQeRadioGroup(@PathVariable Long id) {
        log.debug("REST request to get QeRadioGroup : {}", id);
        Optional<QeRadioGroupDTO> qeRadioGroupDTO = qeRadioGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeRadioGroupDTO);
    }

    /**
     * {@code DELETE  /qe-radio-groups/:id} : delete the "id" qeRadioGroup.
     *
     * @param id the id of the qeRadioGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-radio-groups/{id}")
    public ResponseEntity<Void> deleteQeRadioGroup(@PathVariable Long id) {
        log.debug("REST request to delete QeRadioGroup : {}", id);
        qeRadioGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
