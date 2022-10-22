package it.tylconsulting.vega.repository;

import it.tylconsulting.vega.domain.QeReply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QeReply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QeReplyRepository extends JpaRepository<QeReply, Long> {}
