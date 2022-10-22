package it.tylconsulting.vega.repository;

import it.tylconsulting.vega.domain.QeCheckGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QeCheckGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QeCheckGroupRepository extends JpaRepository<QeCheckGroup, Long> {}
