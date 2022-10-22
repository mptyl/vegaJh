package it.tylconsulting.vega.repository;

import it.tylconsulting.vega.domain.QeGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QeGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QeGroupRepository extends JpaRepository<QeGroup, Long> {}
