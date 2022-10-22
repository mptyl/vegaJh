package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeJumpExpression;
import it.tylconsulting.vega.repository.QeJumpExpressionRepository;
import it.tylconsulting.vega.service.dto.QeJumpExpressionDTO;
import it.tylconsulting.vega.service.mapper.QeJumpExpressionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeJumpExpression}.
 */
@Service
@Transactional
public class QeJumpExpressionService {

    private final Logger log = LoggerFactory.getLogger(QeJumpExpressionService.class);

    private final QeJumpExpressionRepository qeJumpExpressionRepository;

    private final QeJumpExpressionMapper qeJumpExpressionMapper;

    public QeJumpExpressionService(QeJumpExpressionRepository qeJumpExpressionRepository, QeJumpExpressionMapper qeJumpExpressionMapper) {
        this.qeJumpExpressionRepository = qeJumpExpressionRepository;
        this.qeJumpExpressionMapper = qeJumpExpressionMapper;
    }

    /**
     * Save a qeJumpExpression.
     *
     * @param qeJumpExpressionDTO the entity to save.
     * @return the persisted entity.
     */
    public QeJumpExpressionDTO save(QeJumpExpressionDTO qeJumpExpressionDTO) {
        log.debug("Request to save QeJumpExpression : {}", qeJumpExpressionDTO);
        QeJumpExpression qeJumpExpression = qeJumpExpressionMapper.toEntity(qeJumpExpressionDTO);
        qeJumpExpression = qeJumpExpressionRepository.save(qeJumpExpression);
        return qeJumpExpressionMapper.toDto(qeJumpExpression);
    }

    /**
     * Update a qeJumpExpression.
     *
     * @param qeJumpExpressionDTO the entity to save.
     * @return the persisted entity.
     */
    public QeJumpExpressionDTO update(QeJumpExpressionDTO qeJumpExpressionDTO) {
        log.debug("Request to update QeJumpExpression : {}", qeJumpExpressionDTO);
        QeJumpExpression qeJumpExpression = qeJumpExpressionMapper.toEntity(qeJumpExpressionDTO);
        qeJumpExpression = qeJumpExpressionRepository.save(qeJumpExpression);
        return qeJumpExpressionMapper.toDto(qeJumpExpression);
    }

    /**
     * Partially update a qeJumpExpression.
     *
     * @param qeJumpExpressionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeJumpExpressionDTO> partialUpdate(QeJumpExpressionDTO qeJumpExpressionDTO) {
        log.debug("Request to partially update QeJumpExpression : {}", qeJumpExpressionDTO);

        return qeJumpExpressionRepository
            .findById(qeJumpExpressionDTO.getId())
            .map(existingQeJumpExpression -> {
                qeJumpExpressionMapper.partialUpdate(existingQeJumpExpression, qeJumpExpressionDTO);

                return existingQeJumpExpression;
            })
            .map(qeJumpExpressionRepository::save)
            .map(qeJumpExpressionMapper::toDto);
    }

    /**
     * Get all the qeJumpExpressions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QeJumpExpressionDTO> findAll() {
        log.debug("Request to get all QeJumpExpressions");
        return qeJumpExpressionRepository
            .findAll()
            .stream()
            .map(qeJumpExpressionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one qeJumpExpression by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeJumpExpressionDTO> findOne(Long id) {
        log.debug("Request to get QeJumpExpression : {}", id);
        return qeJumpExpressionRepository.findById(id).map(qeJumpExpressionMapper::toDto);
    }

    /**
     * Delete the qeJumpExpression by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeJumpExpression : {}", id);
        qeJumpExpressionRepository.deleteById(id);
    }
}
