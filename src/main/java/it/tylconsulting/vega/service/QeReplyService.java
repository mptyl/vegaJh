package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeReply;
import it.tylconsulting.vega.repository.QeReplyRepository;
import it.tylconsulting.vega.service.dto.QeReplyDTO;
import it.tylconsulting.vega.service.mapper.QeReplyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeReply}.
 */
@Service
@Transactional
public class QeReplyService {

    private final Logger log = LoggerFactory.getLogger(QeReplyService.class);

    private final QeReplyRepository qeReplyRepository;

    private final QeReplyMapper qeReplyMapper;

    public QeReplyService(QeReplyRepository qeReplyRepository, QeReplyMapper qeReplyMapper) {
        this.qeReplyRepository = qeReplyRepository;
        this.qeReplyMapper = qeReplyMapper;
    }

    /**
     * Save a qeReply.
     *
     * @param qeReplyDTO the entity to save.
     * @return the persisted entity.
     */
    public QeReplyDTO save(QeReplyDTO qeReplyDTO) {
        log.debug("Request to save QeReply : {}", qeReplyDTO);
        QeReply qeReply = qeReplyMapper.toEntity(qeReplyDTO);
        qeReply = qeReplyRepository.save(qeReply);
        return qeReplyMapper.toDto(qeReply);
    }

    /**
     * Update a qeReply.
     *
     * @param qeReplyDTO the entity to save.
     * @return the persisted entity.
     */
    public QeReplyDTO update(QeReplyDTO qeReplyDTO) {
        log.debug("Request to update QeReply : {}", qeReplyDTO);
        QeReply qeReply = qeReplyMapper.toEntity(qeReplyDTO);
        qeReply = qeReplyRepository.save(qeReply);
        return qeReplyMapper.toDto(qeReply);
    }

    /**
     * Partially update a qeReply.
     *
     * @param qeReplyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeReplyDTO> partialUpdate(QeReplyDTO qeReplyDTO) {
        log.debug("Request to partially update QeReply : {}", qeReplyDTO);

        return qeReplyRepository
            .findById(qeReplyDTO.getId())
            .map(existingQeReply -> {
                qeReplyMapper.partialUpdate(existingQeReply, qeReplyDTO);

                return existingQeReply;
            })
            .map(qeReplyRepository::save)
            .map(qeReplyMapper::toDto);
    }

    /**
     * Get all the qeReplies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QeReplyDTO> findAll() {
        log.debug("Request to get all QeReplies");
        return qeReplyRepository.findAll().stream().map(qeReplyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one qeReply by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeReplyDTO> findOne(Long id) {
        log.debug("Request to get QeReply : {}", id);
        return qeReplyRepository.findById(id).map(qeReplyMapper::toDto);
    }

    /**
     * Delete the qeReply by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeReply : {}", id);
        qeReplyRepository.deleteById(id);
    }
}
