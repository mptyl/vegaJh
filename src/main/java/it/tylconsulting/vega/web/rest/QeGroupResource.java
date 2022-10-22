package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeGroupRepository;
import it.tylconsulting.vega.service.QeGroupService;
import it.tylconsulting.vega.service.dto.QeGroupDTO;
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
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeGroup}.
 */
@RestController
@RequestMapping("/api")
public class QeGroupResource {

    private final Logger log = LoggerFactory.getLogger(QeGroupResource.class);

    private static final String ENTITY_NAME = "qeGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeGroupService qeGroupService;

    private final QeGroupRepository qeGroupRepository;

    public QeGroupResource(QeGroupService qeGroupService, QeGroupRepository qeGroupRepository) {
        this.qeGroupService = qeGroupService;
        this.qeGroupRepository = qeGroupRepository;
    }

    /**
     * {@code POST  /qe-groups} : Create a new qeGroup.
     *
     * @param qeGroupDTO the qeGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeGroupDTO, or with status {@code 400 (Bad Request)} if the qeGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-groups")
    public ResponseEntity<QeGroupDTO> createQeGroup(@RequestBody QeGroupDTO qeGroupDTO) throws URISyntaxException {
        log.debug("REST request to save QeGroup : {}", qeGroupDTO);
        if (qeGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeGroupDTO result = qeGroupService.save(qeGroupDTO);
        return ResponseEntity
            .created(new URI("/api/qe-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-groups/:id} : Updates an existing qeGroup.
     *
     * @param id the id of the qeGroupDTO to save.
     * @param qeGroupDTO the qeGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeGroupDTO,
     * or with status {@code 400 (Bad Request)} if the qeGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-groups/{id}")
    public ResponseEntity<QeGroupDTO> updateQeGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeGroupDTO qeGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeGroup : {}, {}", id, qeGroupDTO);
        if (qeGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeGroupDTO result = qeGroupService.update(qeGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-groups/:id} : Partial updates given fields of an existing qeGroup, field will ignore if it is null
     *
     * @param id the id of the qeGroupDTO to save.
     * @param qeGroupDTO the qeGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeGroupDTO,
     * or with status {@code 400 (Bad Request)} if the qeGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeGroupDTO> partialUpdateQeGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeGroupDTO qeGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeGroup partially : {}, {}", id, qeGroupDTO);
        if (qeGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeGroupDTO> result = qeGroupService.partialUpdate(qeGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-groups} : get all the qeGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeGroups in body.
     */
    @GetMapping("/qe-groups")
    public List<QeGroupDTO> getAllQeGroups() {
        log.debug("REST request to get all QeGroups");
        return qeGroupService.findAll();
    }

    /**
     * {@code GET  /qe-groups/:id} : get the "id" qeGroup.
     *
     * @param id the id of the qeGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-groups/{id}")
    public ResponseEntity<QeGroupDTO> getQeGroup(@PathVariable Long id) {
        log.debug("REST request to get QeGroup : {}", id);
        Optional<QeGroupDTO> qeGroupDTO = qeGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeGroupDTO);
    }

    /**
     * {@code DELETE  /qe-groups/:id} : delete the "id" qeGroup.
     *
     * @param id the id of the qeGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-groups/{id}")
    public ResponseEntity<Void> deleteQeGroup(@PathVariable Long id) {
        log.debug("REST request to delete QeGroup : {}", id);
        qeGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
