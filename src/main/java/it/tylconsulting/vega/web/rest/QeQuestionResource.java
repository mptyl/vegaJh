package it.tylconsulting.vega.web.rest;

import it.tylconsulting.vega.repository.QeQuestionRepository;
import it.tylconsulting.vega.service.QeQuestionService;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import it.tylconsulting.vega.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.tylconsulting.vega.domain.QeQuestion}.
 */
@RestController
@RequestMapping("/api")
public class QeQuestionResource {

    private final Logger log = LoggerFactory.getLogger(QeQuestionResource.class);

    private static final String ENTITY_NAME = "qeQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QeQuestionService qeQuestionService;

    private final QeQuestionRepository qeQuestionRepository;

    public QeQuestionResource(QeQuestionService qeQuestionService, QeQuestionRepository qeQuestionRepository) {
        this.qeQuestionService = qeQuestionService;
        this.qeQuestionRepository = qeQuestionRepository;
    }

    /**
     * {@code POST  /qe-questions} : Create a new qeQuestion.
     *
     * @param qeQuestionDTO the qeQuestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qeQuestionDTO, or with status {@code 400 (Bad Request)} if the qeQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qe-questions")
    public ResponseEntity<QeQuestionDTO> createQeQuestion(@RequestBody QeQuestionDTO qeQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save QeQuestion : {}", qeQuestionDTO);
        if (qeQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new qeQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QeQuestionDTO result = qeQuestionService.save(qeQuestionDTO);
        return ResponseEntity
            .created(new URI("/api/qe-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qe-questions/:id} : Updates an existing qeQuestion.
     *
     * @param id the id of the qeQuestionDTO to save.
     * @param qeQuestionDTO the qeQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the qeQuestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qeQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qe-questions/{id}")
    public ResponseEntity<QeQuestionDTO> updateQeQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeQuestionDTO qeQuestionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QeQuestion : {}, {}", id, qeQuestionDTO);
        if (qeQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeQuestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QeQuestionDTO result = qeQuestionService.update(qeQuestionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qe-questions/:id} : Partial updates given fields of an existing qeQuestion, field will ignore if it is null
     *
     * @param id the id of the qeQuestionDTO to save.
     * @param qeQuestionDTO the qeQuestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qeQuestionDTO,
     * or with status {@code 400 (Bad Request)} if the qeQuestionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qeQuestionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qeQuestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/qe-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QeQuestionDTO> partialUpdateQeQuestion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QeQuestionDTO qeQuestionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QeQuestion partially : {}, {}", id, qeQuestionDTO);
        if (qeQuestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qeQuestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qeQuestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QeQuestionDTO> result = qeQuestionService.partialUpdate(qeQuestionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qeQuestionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qe-questions} : get all the qeQuestions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qeQuestions in body.
     */
    @GetMapping("/qe-questions")
    public ResponseEntity<List<QeQuestionDTO>> getAllQeQuestions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of QeQuestions");
        Page<QeQuestionDTO> page = qeQuestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qe-questions/:id} : get the "id" qeQuestion.
     *
     * @param id the id of the qeQuestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qeQuestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qe-questions/{id}")
    public ResponseEntity<QeQuestionDTO> getQeQuestion(@PathVariable Long id) {
        log.debug("REST request to get QeQuestion : {}", id);
        Optional<QeQuestionDTO> qeQuestionDTO = qeQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qeQuestionDTO);
    }

    /**
     * {@code DELETE  /qe-questions/:id} : delete the "id" qeQuestion.
     *
     * @param id the id of the qeQuestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qe-questions/{id}")
    public ResponseEntity<Void> deleteQeQuestion(@PathVariable Long id) {
        log.debug("REST request to delete QeQuestion : {}", id);
        qeQuestionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
