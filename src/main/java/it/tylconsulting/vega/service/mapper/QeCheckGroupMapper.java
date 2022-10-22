package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeCheckGroup;
import it.tylconsulting.vega.domain.QeQuestion;
import it.tylconsulting.vega.service.dto.QeCheckGroupDTO;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeCheckGroup} and its DTO {@link QeCheckGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeCheckGroupMapper extends EntityMapper<QeCheckGroupDTO, QeCheckGroup> {
    @Mapping(target = "qeQuestion", source = "qeQuestion", qualifiedByName = "qeQuestionId")
    QeCheckGroupDTO toDto(QeCheckGroup s);

    @Named("qeQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QeQuestionDTO toDtoQeQuestionId(QeQuestion qeQuestion);
}
