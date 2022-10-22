package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeCheckBox;
import it.tylconsulting.vega.repository.QeCheckBoxRepository;
import it.tylconsulting.vega.service.dto.QeCheckBoxDTO;
import it.tylconsulting.vega.service.mapper.QeCheckBoxMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeCheckBox}.
 */
@Service
@Transactional
public class QeCheckBoxService {

    private final Logger log = LoggerFactory.getLogger(QeCheckBoxService.class);

    private final QeCheckBoxRepository qeCheckBoxRepository;

    private final QeCheckBoxMapper qeCheckBoxMapper;

    public QeCheckBoxService(QeCheckBoxRepository qeCheckBoxRepository, QeCheckBoxMapper qeCheckBoxMapper) {
        this.qeCheckBoxRepository = qeCheckBoxRepository;
        this.qeCheckBoxMapper = qeCheckBoxMapper;
    }

    /**
     * Save a qeCheckBox.
     *
     * @param qeCheckBoxDTO the entity to save.
     * @return the persisted entity.
     */
    public QeCheckBoxDTO save(QeCheckBoxDTO qeCheckBoxDTO) {
        log.debug("Request to save QeCheckBox : {}", qeCheckBoxDTO);
        QeCheckBox qeCheckBox = qeCheckBoxMapper.toEntity(qeCheckBoxDTO);
        qeCheckBox = qeCheckBoxRepository.save(qeCheckBox);
        return qeCheckBoxMapper.toDto(qeCheckBox);
    }

    /**
     * Update a qeCheckBox.
     *
     * @param qeCheckBoxDTO the entity to save.
     * @return the persisted entity.
     */
    public QeCheckBoxDTO update(QeCheckBoxDTO qeCheckBoxDTO) {
        log.debug("Request to update QeCheckBox : {}", qeCheckBoxDTO);
        QeCheckBox qeCheckBox = qeCheckBoxMapper.toEntity(qeCheckBoxDTO);
        qeCheckBox = qeCheckBoxRepository.save(qeCheckBox);
        return qeCheckBoxMapper.toDto(qeCheckBox);
    }

    /**
     * Partially update a qeCheckBox.
     *
     * @param qeCheckBoxDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeCheckBoxDTO> partialUpdate(QeCheckBoxDTO qeCheckBoxDTO) {
        log.debug("Request to partially update QeCheckBox : {}", qeCheckBoxDTO);

        return qeCheckBoxRepository
            .findById(qeCheckBoxDTO.getId())
            .map(existingQeCheckBox -> {
                qeCheckBoxMapper.partialUpdate(existingQeCheckBox, qeCheckBoxDTO);

                return existingQeCheckBox;
            })
            .map(qeCheckBoxRepository::save)
            .map(qeCheckBoxMapper::toDto);
    }

    /**
     * Get all the qeCheckBoxes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QeCheckBoxDTO> findAll() {
        log.debug("Request to get all QeCheckBoxes");
        return qeCheckBoxRepository.findAll().stream().map(qeCheckBoxMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one qeCheckBox by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeCheckBoxDTO> findOne(Long id) {
        log.debug("Request to get QeCheckBox : {}", id);
        return qeCheckBoxRepository.findById(id).map(qeCheckBoxMapper::toDto);
    }

    /**
     * Delete the qeCheckBox by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeCheckBox : {}", id);
        qeCheckBoxRepository.deleteById(id);
    }
}
