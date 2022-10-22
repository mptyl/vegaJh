package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeRadioGroup;
import it.tylconsulting.vega.repository.QeRadioGroupRepository;
import it.tylconsulting.vega.service.dto.QeRadioGroupDTO;
import it.tylconsulting.vega.service.mapper.QeRadioGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeRadioGroup}.
 */
@Service
@Transactional
public class QeRadioGroupService {

    private final Logger log = LoggerFactory.getLogger(QeRadioGroupService.class);

    private final QeRadioGroupRepository qeRadioGroupRepository;

    private final QeRadioGroupMapper qeRadioGroupMapper;

    public QeRadioGroupService(QeRadioGroupRepository qeRadioGroupRepository, QeRadioGroupMapper qeRadioGroupMapper) {
        this.qeRadioGroupRepository = qeRadioGroupRepository;
        this.qeRadioGroupMapper = qeRadioGroupMapper;
    }

    /**
     * Save a qeRadioGroup.
     *
     * @param qeRadioGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public QeRadioGroupDTO save(QeRadioGroupDTO qeRadioGroupDTO) {
        log.debug("Request to save QeRadioGroup : {}", qeRadioGroupDTO);
        QeRadioGroup qeRadioGroup = qeRadioGroupMapper.toEntity(qeRadioGroupDTO);
        qeRadioGroup = qeRadioGroupRepository.save(qeRadioGroup);
        return qeRadioGroupMapper.toDto(qeRadioGroup);
    }

    /**
     * Update a qeRadioGroup.
     *
     * @param qeRadioGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public QeRadioGroupDTO update(QeRadioGroupDTO qeRadioGroupDTO) {
        log.debug("Request to update QeRadioGroup : {}", qeRadioGroupDTO);
        QeRadioGroup qeRadioGroup = qeRadioGroupMapper.toEntity(qeRadioGroupDTO);
        qeRadioGroup = qeRadioGroupRepository.save(qeRadioGroup);
        return qeRadioGroupMapper.toDto(qeRadioGroup);
    }

    /**
     * Partially update a qeRadioGroup.
     *
     * @param qeRadioGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeRadioGroupDTO> partialUpdate(QeRadioGroupDTO qeRadioGroupDTO) {
        log.debug("Request to partially update QeRadioGroup : {}", qeRadioGroupDTO);

        return qeRadioGroupRepository
            .findById(qeRadioGroupDTO.getId())
            .map(existingQeRadioGroup -> {
                qeRadioGroupMapper.partialUpdate(existingQeRadioGroup, qeRadioGroupDTO);

                return existingQeRadioGroup;
            })
            .map(qeRadioGroupRepository::save)
            .map(qeRadioGroupMapper::toDto);
    }

    /**
     * Get all the qeRadioGroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QeRadioGroupDTO> findAll() {
        log.debug("Request to get all QeRadioGroups");
        return qeRadioGroupRepository.findAll().stream().map(qeRadioGroupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one qeRadioGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeRadioGroupDTO> findOne(Long id) {
        log.debug("Request to get QeRadioGroup : {}", id);
        return qeRadioGroupRepository.findById(id).map(qeRadioGroupMapper::toDto);
    }

    /**
     * Delete the qeRadioGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeRadioGroup : {}", id);
        qeRadioGroupRepository.deleteById(id);
    }
}
