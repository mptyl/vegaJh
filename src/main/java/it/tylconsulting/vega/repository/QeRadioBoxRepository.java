package it.tylconsulting.vega.repository;

import it.tylconsulting.vega.domain.QeRadioBox;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QeRadioBox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QeRadioBoxRepository extends JpaRepository<QeRadioBox, Long> {}
