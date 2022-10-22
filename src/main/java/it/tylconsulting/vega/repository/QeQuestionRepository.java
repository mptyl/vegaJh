package it.tylconsulting.vega.repository;

import it.tylconsulting.vega.domain.QeQuestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QeQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QeQuestionRepository extends JpaRepository<QeQuestion, Long> {}
