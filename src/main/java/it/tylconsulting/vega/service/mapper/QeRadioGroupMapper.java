package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeQuestion;
import it.tylconsulting.vega.domain.QeRadioGroup;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import it.tylconsulting.vega.service.dto.QeRadioGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeRadioGroup} and its DTO {@link QeRadioGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeRadioGroupMapper extends EntityMapper<QeRadioGroupDTO, QeRadioGroup> {
    @Mapping(target = "qeQuestion", source = "qeQuestion", qualifiedByName = "qeQuestionId")
    QeRadioGroupDTO toDto(QeRadioGroup s);

    @Named("qeQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QeQuestionDTO toDtoQeQuestionId(QeQuestion qeQuestion);
}
