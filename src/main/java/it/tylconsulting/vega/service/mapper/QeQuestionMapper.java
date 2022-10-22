package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeGroup;
import it.tylconsulting.vega.domain.QeQuestion;
import it.tylconsulting.vega.service.dto.QeGroupDTO;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeQuestion} and its DTO {@link QeQuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeQuestionMapper extends EntityMapper<QeQuestionDTO, QeQuestion> {
    @Mapping(target = "qeGroup", source = "qeGroup", qualifiedByName = "qeGroupId")
    QeQuestionDTO toDto(QeQuestion s);

    @Named("qeGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QeGroupDTO toDtoQeGroupId(QeGroup qeGroup);
}
