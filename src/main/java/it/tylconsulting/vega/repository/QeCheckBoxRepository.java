package it.tylconsulting.vega.repository;

import it.tylconsulting.vega.domain.QeCheckBox;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QeCheckBox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QeCheckBoxRepository extends JpaRepository<QeCheckBox, Long> {}
