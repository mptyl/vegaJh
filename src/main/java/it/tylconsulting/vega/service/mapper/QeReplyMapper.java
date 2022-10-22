package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeQuestion;
import it.tylconsulting.vega.domain.QeReply;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import it.tylconsulting.vega.service.dto.QeReplyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeReply} and its DTO {@link QeReplyDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeReplyMapper extends EntityMapper<QeReplyDTO, QeReply> {
    @Mapping(target = "qeQuestion", source = "qeQuestion", qualifiedByName = "qeQuestionId")
    QeReplyDTO toDto(QeReply s);

    @Named("qeQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QeQuestionDTO toDtoQeQuestionId(QeQuestion qeQuestion);
}
