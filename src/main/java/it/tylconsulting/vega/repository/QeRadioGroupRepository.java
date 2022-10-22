package it.tylconsulting.vega.repository;

import it.tylconsulting.vega.domain.QeRadioGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QeRadioGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QeRadioGroupRepository extends JpaRepository<QeRadioGroup, Long> {}
