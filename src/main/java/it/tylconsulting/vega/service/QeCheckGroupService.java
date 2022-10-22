package it.tylconsulting.vega.service;

import it.tylconsulting.vega.domain.QeCheckGroup;
import it.tylconsulting.vega.repository.QeCheckGroupRepository;
import it.tylconsulting.vega.service.dto.QeCheckGroupDTO;
import it.tylconsulting.vega.service.mapper.QeCheckGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QeCheckGroup}.
 */
@Service
@Transactional
public class QeCheckGroupService {

    private final Logger log = LoggerFactory.getLogger(QeCheckGroupService.class);

    private final QeCheckGroupRepository qeCheckGroupRepository;

    private final QeCheckGroupMapper qeCheckGroupMapper;

    public QeCheckGroupService(QeCheckGroupRepository qeCheckGroupRepository, QeCheckGroupMapper qeCheckGroupMapper) {
        this.qeCheckGroupRepository = qeCheckGroupRepository;
        this.qeCheckGroupMapper = qeCheckGroupMapper;
    }

    /**
     * Save a qeCheckGroup.
     *
     * @param qeCheckGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public QeCheckGroupDTO save(QeCheckGroupDTO qeCheckGroupDTO) {
        log.debug("Request to save QeCheckGroup : {}", qeCheckGroupDTO);
        QeCheckGroup qeCheckGroup = qeCheckGroupMapper.toEntity(qeCheckGroupDTO);
        qeCheckGroup = qeCheckGroupRepository.save(qeCheckGroup);
        return qeCheckGroupMapper.toDto(qeCheckGroup);
    }

    /**
     * Update a qeCheckGroup.
     *
     * @param qeCheckGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public QeCheckGroupDTO update(QeCheckGroupDTO qeCheckGroupDTO) {
        log.debug("Request to update QeCheckGroup : {}", qeCheckGroupDTO);
        QeCheckGroup qeCheckGroup = qeCheckGroupMapper.toEntity(qeCheckGroupDTO);
        qeCheckGroup = qeCheckGroupRepository.save(qeCheckGroup);
        return qeCheckGroupMapper.toDto(qeCheckGroup);
    }

    /**
     * Partially update a qeCheckGroup.
     *
     * @param qeCheckGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QeCheckGroupDTO> partialUpdate(QeCheckGroupDTO qeCheckGroupDTO) {
        log.debug("Request to partially update QeCheckGroup : {}", qeCheckGroupDTO);

        return qeCheckGroupRepository
            .findById(qeCheckGroupDTO.getId())
            .map(existingQeCheckGroup -> {
                qeCheckGroupMapper.partialUpdate(existingQeCheckGroup, qeCheckGroupDTO);

                return existingQeCheckGroup;
            })
            .map(qeCheckGroupRepository::save)
            .map(qeCheckGroupMapper::toDto);
    }

    /**
     * Get all the qeCheckGroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<QeCheckGroupDTO> findAll() {
        log.debug("Request to get all QeCheckGroups");
        return qeCheckGroupRepository.findAll().stream().map(qeCheckGroupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one qeCheckGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QeCheckGroupDTO> findOne(Long id) {
        log.debug("Request to get QeCheckGroup : {}", id);
        return qeCheckGroupRepository.findById(id).map(qeCheckGroupMapper::toDto);
    }

    /**
     * Delete the qeCheckGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QeCheckGroup : {}", id);
        qeCheckGroupRepository.deleteById(id);
    }
}
