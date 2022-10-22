package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeGroup;
import it.tylconsulting.vega.domain.Questionnaire;
import it.tylconsulting.vega.service.dto.QeGroupDTO;
import it.tylconsulting.vega.service.dto.QuestionnaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeGroup} and its DTO {@link QeGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeGroupMapper extends EntityMapper<QeGroupDTO, QeGroup> {
    @Mapping(target = "questionnaire", source = "questionnaire", qualifiedByName = "questionnaireId")
    QeGroupDTO toDto(QeGroup s);

    @Named("questionnaireId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuestionnaireDTO toDtoQuestionnaireId(Questionnaire questionnaire);
}
