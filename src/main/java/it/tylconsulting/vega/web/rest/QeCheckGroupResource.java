package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeCheckGroupRepository;
import it.tylconsulting.vega.service.QeCheckGroupService;
import it.tylconsulting.vega.service.dto.QeCheckGroupDTO;
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
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeCheckGroup}.
 */
@RestController
@RequestMapping("/api")
public class QeCheckGroupResource {

    private final Logger log = LoggerFactory.getLogger(QeCheckGroupResource.class);

    private static final String ENTITY_NAME = "qeCheckGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeCheckGroupService qeCheckGroupService;

    private final QeCheckGroupRepository qeCheckGroupRepository;

    public QeCheckGroupResource(QeCheckGroupService qeCheckGroupService, QeCheckGroupRepository qeCheckGroupRepository) {
        this.qeCheckGroupService = qeCheckGroupService;
        this.qeCheckGroupRepository = qeCheckGroupRepository;
    }

    /**
     * {@code POST  /qe-check-groups} : Create a new qeCheckGroup.
     *
     * @param qeCheckGroupDTO the qeCheckGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeCheckGroupDTO, or with status {@code 400 (Bad Request)} if the qeCheckGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-check-groups")
    public ResponseEntity<QeCheckGroupDTO> createQeCheckGroup(@RequestBody QeCheckGroupDTO qeCheckGroupDTO) throws URISyntaxException {
        log.debug("REST request to save QeCheckGroup : {}", qeCheckGroupDTO);
        if (qeCheckGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeCheckGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeCheckGroupDTO result = qeCheckGroupService.save(qeCheckGroupDTO);
        return ResponseEntity
            .created(new URI("/api/qe-check-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-check-groups/:id} : Updates an existing qeCheckGroup.
     *
     * @param id the id of the qeCheckGroupDTO to save.
     * @param qeCheckGroupDTO the qeCheckGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeCheckGroupDTO,
     * or with status {@code 400 (Bad Request)} if the qeCheckGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeCheckGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-check-groups/{id}")
    public ResponseEntity<QeCheckGroupDTO> updateQeCheckGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeCheckGroupDTO qeCheckGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeCheckGroup : {}, {}", id, qeCheckGroupDTO);
        if (qeCheckGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeCheckGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeCheckGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeCheckGroupDTO result = qeCheckGroupService.update(qeCheckGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeCheckGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-check-groups/:id} : Partial updates given fields of an existing qeCheckGroup, field will ignore if it is null
     *
     * @param id the id of the qeCheckGroupDTO to save.
     * @param qeCheckGroupDTO the qeCheckGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeCheckGroupDTO,
     * or with status {@code 400 (Bad Request)} if the qeCheckGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeCheckGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeCheckGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-check-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeCheckGroupDTO> partialUpdateQeCheckGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeCheckGroupDTO qeCheckGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeCheckGroup partially : {}, {}", id, qeCheckGroupDTO);
        if (qeCheckGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeCheckGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeCheckGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeCheckGroupDTO> result = qeCheckGroupService.partialUpdate(qeCheckGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeCheckGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-check-groups} : get all the qeCheckGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeCheckGroups in body.
     */
    @GetMapping("/qe-check-groups")
    public List<QeCheckGroupDTO> getAllQeCheckGroups() {
        log.debug("REST request to get all QeCheckGroups");
        return qeCheckGroupService.findAll();
    }

    /**
     * {@code GET  /qe-check-groups/:id} : get the "id" qeCheckGroup.
     *
     * @param id the id of the qeCheckGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeCheckGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-check-groups/{id}")
    public ResponseEntity<QeCheckGroupDTO> getQeCheckGroup(@PathVariable Long id) {
        log.debug("REST request to get QeCheckGroup : {}", id);
        Optional<QeCheckGroupDTO> qeCheckGroupDTO = qeCheckGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeCheckGroupDTO);
    }

    /**
     * {@code DELETE  /qe-check-groups/:id} : delete the "id" qeCheckGroup.
     *
     * @param id the id of the qeCheckGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-check-groups/{id}")
    public ResponseEntity<Void> deleteQeCheckGroup(@PathVariable Long id) {
        log.debug("REST request to delete QeCheckGroup : {}", id);
        qeCheckGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
