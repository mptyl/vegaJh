package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeReplyRepository;
import it.tylconsulting.vega.service.QeReplyService;
import it.tylconsulting.vega.service.dto.QeReplyDTO;
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
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeReply}.
 */
@RestController
@RequestMapping("/api")
public class QeReplyResource {

    private final Logger log = LoggerFactory.getLogger(QeReplyResource.class);

    private static final String ENTITY_NAME = "qeReply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeReplyService qeReplyService;

    private final QeReplyRepository qeReplyRepository;

    public QeReplyResource(QeReplyService qeReplyService, QeReplyRepository qeReplyRepository) {
        this.qeReplyService = qeReplyService;
        this.qeReplyRepository = qeReplyRepository;
    }

    /**
     * {@code POST  /qe-replies} : Create a new qeReply.
     *
     * @param qeReplyDTO the qeReplyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeReplyDTO, or with status {@code 400 (Bad Request)} if the qeReply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-replies")
    public ResponseEntity<QeReplyDTO> createQeReply(@RequestBody QeReplyDTO qeReplyDTO) throws URISyntaxException {
        log.debug("REST request to save QeReply : {}", qeReplyDTO);
        if (qeReplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeReply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeReplyDTO result = qeReplyService.save(qeReplyDTO);
        return ResponseEntity
            .created(new URI("/api/qe-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-replies/:id} : Updates an existing qeReply.
     *
     * @param id the id of the qeReplyDTO to save.
     * @param qeReplyDTO the qeReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeReplyDTO,
     * or with status {@code 400 (Bad Request)} if the qeReplyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-replies/{id}")
    public ResponseEntity<QeReplyDTO> updateQeReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeReplyDTO qeReplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeReply : {}, {}", id, qeReplyDTO);
        if (qeReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeReplyDTO result = qeReplyService.update(qeReplyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeReplyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-replies/:id} : Partial updates given fields of an existing qeReply, field will ignore if it is null
     *
     * @param id the id of the qeReplyDTO to save.
     * @param qeReplyDTO the qeReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeReplyDTO,
     * or with status {@code 400 (Bad Request)} if the qeReplyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeReplyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-replies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeReplyDTO> partialUpdateQeReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeReplyDTO qeReplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeReply partially : {}, {}", id, qeReplyDTO);
        if (qeReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeReplyDTO> result = qeReplyService.partialUpdate(qeReplyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeReplyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-replies} : get all the qeReplies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeReplies in body.
     */
    @GetMapping("/qe-replies")
    public List<QeReplyDTO> getAllQeReplies() {
        log.debug("REST request to get all QeReplies");
        return qeReplyService.findAll();
    }

    /**
     * {@code GET  /qe-replies/:id} : get the "id" qeReply.
     *
     * @param id the id of the qeReplyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeReplyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-replies/{id}")
    public ResponseEntity<QeReplyDTO> getQeReply(@PathVariable Long id) {
        log.debug("REST request to get QeReply : {}", id);
        Optional<QeReplyDTO> qeReplyDTO = qeReplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeReplyDTO);
    }

    /**
     * {@code DELETE  /qe-replies/:id} : delete the "id" qeReply.
     *
     * @param id the id of the qeReplyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-replies/{id}")
    public ResponseEntity<Void> deleteQeReply(@PathVariable Long id) {
        log.debug("REST request to delete QeReply : {}", id);
        qeReplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
