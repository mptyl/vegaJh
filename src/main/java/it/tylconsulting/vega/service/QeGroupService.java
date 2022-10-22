package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeGroup;
import it.tylconsulting.vega.repository.QeGroupRepository;
import it.tylconsulting.vega.service.dto.QeGroupDTO;
import it.tylconsulting.vega.service.mapper.QeGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeGroup}.
 */
@Service
@Transactional
public class QeGroupService {

    private final Logger log = LoggerFactory.getLogger(QeGroupService.class);

    private final QeGroupRepository qeGroupRepository;

    private final QeGroupMapper qeGroupMapper;

    public QeGroupService(QeGroupRepository qeGroupRepository, QeGroupMapper qeGroupMapper) {
        this.qeGroupRepository = qeGroupRepository;
        this.qeGroupMapper = qeGroupMapper;
    }

    /**
     * Save a qeGroup.
     *
     * @param qeGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public QeGroupDTO save(QeGroupDTO qeGroupDTO) {
        log.debug("Request to save QeGroup : {}", qeGroupDTO);
        QeGroup qeGroup = qeGroupMapper.toEntity(qeGroupDTO);
        qeGroup = qeGroupRepository.save(qeGroup);
        return qeGroupMapper.toDto(qeGroup);
    }

    /**
     * Update a qeGroup.
     *
     * @param qeGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public QeGroupDTO update(QeGroupDTO qeGroupDTO) {
        log.debug("Request to update QeGroup : {}", qeGroupDTO);
        QeGroup qeGroup = qeGroupMapper.toEntity(qeGroupDTO);
        qeGroup = qeGroupRepository.save(qeGroup);
        return qeGroupMapper.toDto(qeGroup);
    }

    /**
     * Partially update a qeGroup.
     *
     * @param qeGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeGroupDTO> partialUpdate(QeGroupDTO qeGroupDTO) {
        log.debug("Request to partially update QeGroup : {}", qeGroupDTO);

        return qeGroupRepository
            .findById(qeGroupDTO.getId())
            .map(existingQeGroup -> {
                qeGroupMapper.partialUpdate(existingQeGroup, qeGroupDTO);

                return existingQeGroup;
            })
            .map(qeGroupRepository::save)
            .map(qeGroupMapper::toDto);
    }

    /**
     * Get all the qeGroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QeGroupDTO> findAll() {
        log.debug("Request to get all QeGroups");
        return qeGroupRepository.findAll().stream().map(qeGroupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one qeGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeGroupDTO> findOne(Long id) {
        log.debug("Request to get QeGroup : {}", id);
        return qeGroupRepository.findById(id).map(qeGroupMapper::toDto);
    }

    /**
     * Delete the qeGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeGroup : {}", id);
        qeGroupRepository.deleteById(id);
    }
}
