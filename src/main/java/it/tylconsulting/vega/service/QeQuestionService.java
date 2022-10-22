package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeQuestion;
import it.tylconsulting.vega.repository.QeQuestionRepository;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import it.tylconsulting.vega.service.mapper.QeQuestionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeQuestion}.
 */
@Service
@Transactional
public class QeQuestionService {

    private final Logger log = LoggerFactory.getLogger(QeQuestionService.class);

    private final QeQuestionRepository qeQuestionRepository;

    private final QeQuestionMapper qeQuestionMapper;

    public QeQuestionService(QeQuestionRepository qeQuestionRepository, QeQuestionMapper qeQuestionMapper) {
        this.qeQuestionRepository = qeQuestionRepository;
        this.qeQuestionMapper = qeQuestionMapper;
    }

    /**
     * Save a qeQuestion.
     *
     * @param qeQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    public QeQuestionDTO save(QeQuestionDTO qeQuestionDTO) {
        log.debug("Request to save QeQuestion : {}", qeQuestionDTO);
        QeQuestion qeQuestion = qeQuestionMapper.toEntity(qeQuestionDTO);
        qeQuestion = qeQuestionRepository.save(qeQuestion);
        return qeQuestionMapper.toDto(qeQuestion);
    }

    /**
     * Update a qeQuestion.
     *
     * @param qeQuestionDTO the entity to save.
     * @return the persisted entity.
     */
    public QeQuestionDTO update(QeQuestionDTO qeQuestionDTO) {
        log.debug("Request to update QeQuestion : {}", qeQuestionDTO);
        QeQuestion qeQuestion = qeQuestionMapper.toEntity(qeQuestionDTO);
        qeQuestion = qeQuestionRepository.save(qeQuestion);
        return qeQuestionMapper.toDto(qeQuestion);
    }

    /**
     * Partially update a qeQuestion.
     *
     * @param qeQuestionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeQuestionDTO> partialUpdate(QeQuestionDTO qeQuestionDTO) {
        log.debug("Request to partially update QeQuestion : {}", qeQuestionDTO);

        return qeQuestionRepository
            .findById(qeQuestionDTO.getId())
            .map(existingQeQuestion -> {
                qeQuestionMapper.partialUpdate(existingQeQuestion, qeQuestionDTO);

                return existingQeQuestion;
            })
            .map(qeQuestionRepository::save)
            .map(qeQuestionMapper::toDto);
    }

    /**
     * Get all the qeQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QeQuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QeQuestions");
        return qeQuestionRepository.findAll(pageable).map(qeQuestionMapper::toDto);
    }

    /**
     * Get one qeQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeQuestionDTO> findOne(Long id) {
        log.debug("Request to get QeQuestion : {}", id);
        return qeQuestionRepository.findById(id).map(qeQuestionMapper::toDto);
    }

    /**
     * Delete the qeQuestion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeQuestion : {}", id);
        qeQuestionRepository.deleteById(id);
    }
}
