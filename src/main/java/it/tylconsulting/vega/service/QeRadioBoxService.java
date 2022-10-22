package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeRadioBox;
import it.tylconsulting.vega.repository.QeRadioBoxRepository;
import it.tylconsulting.vega.service.dto.QeRadioBoxDTO;
import it.tylconsulting.vega.service.mapper.QeRadioBoxMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeRadioBox}.
 */
@Service
@Transactional
public class QeRadioBoxService {

    private final Logger log = LoggerFactory.getLogger(QeRadioBoxService.class);

    private final QeRadioBoxRepository qeRadioBoxRepository;

    private final QeRadioBoxMapper qeRadioBoxMapper;

    public QeRadioBoxService(QeRadioBoxRepository qeRadioBoxRepository, QeRadioBoxMapper qeRadioBoxMapper) {
        this.qeRadioBoxRepository = qeRadioBoxRepository;
        this.qeRadioBoxMapper = qeRadioBoxMapper;
    }

    /**
     * Save a qeRadioBox.
     *
     * @param qeRadioBoxDTO the entity to save.
     * @return the persisted entity.
     */
    public QeRadioBoxDTO save(QeRadioBoxDTO qeRadioBoxDTO) {
        log.debug("Request to save QeRadioBox : {}", qeRadioBoxDTO);
        QeRadioBox qeRadioBox = qeRadioBoxMapper.toEntity(qeRadioBoxDTO);
        qeRadioBox = qeRadioBoxRepository.save(qeRadioBox);
        return qeRadioBoxMapper.toDto(qeRadioBox);
    }

    /**
     * Update a qeRadioBox.
     *
     * @param qeRadioBoxDTO the entity to save.
     * @return the persisted entity.
     */
    public QeRadioBoxDTO update(QeRadioBoxDTO qeRadioBoxDTO) {
        log.debug("Request to update QeRadioBox : {}", qeRadioBoxDTO);
        QeRadioBox qeRadioBox = qeRadioBoxMapper.toEntity(qeRadioBoxDTO);
        qeRadioBox = qeRadioBoxRepository.save(qeRadioBox);
        return qeRadioBoxMapper.toDto(qeRadioBox);
    }

    /**
     * Partially update a qeRadioBox.
     *
     * @param qeRadioBoxDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeRadioBoxDTO> partialUpdate(QeRadioBoxDTO qeRadioBoxDTO) {
        log.debug("Request to partially update QeRadioBox : {}", qeRadioBoxDTO);

        return qeRadioBoxRepository
            .findById(qeRadioBoxDTO.getId())
            .map(existingQeRadioBox -> {
                qeRadioBoxMapper.partialUpdate(existingQeRadioBox, qeRadioBoxDTO);

                return existingQeRadioBox;
            })
            .map(qeRadioBoxRepository::save)
            .map(qeRadioBoxMapper::toDto);
    }

    /**
     * Get all the qeRadioBoxes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QeRadioBoxDTO> findAll() {
        log.debug("Request to get all QeRadioBoxes");
        return qeRadioBoxRepository.findAll().stream().map(qeRadioBoxMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one qeRadioBox by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeRadioBoxDTO> findOne(Long id) {
        log.debug("Request to get QeRadioBox : {}", id);
        return qeRadioBoxRepository.findById(id).map(qeRadioBoxMapper::toDto);
    }

    /**
     * Delete the qeRadioBox by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeRadioBox : {}", id);
        qeRadioBoxRepository.deleteById(id);
    }
}
