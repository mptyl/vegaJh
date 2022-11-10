package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.Questionnaire;
import it.tylconsulting.vega.domain.QuestionnaireGroup;
import it.tylconsulting.vega.domain.QuestionnaireProfile;
import it.tylconsulting.vega.service.dto.QuestionnaireDTO;
import it.tylconsulting.vega.service.dto.QuestionnaireGroupDTO;
import it.tylconsulting.vega.service.dto.QuestionnaireProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Questionnaire} and its DTO {@link QuestionnaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuestionnaireMapper extends EntityMapper<QuestionnaireDTO, Questionnaire> {
    @Mapping(target = "questionnaireGroup", source = "questionnaireGroup", qualifiedByName = "questionnaireGroupId")
    @Mapping(target = "questionnaireProfile", source = "questionnaireProfile", qualifiedByName = "questionnaireProfileId")
    QuestionnaireDTO toDto(Questionnaire s);

    @Named("questionnaireGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    QuestionnaireGroupDTO toDtoQuestionnaireGroupId(QuestionnaireGroup questionnaireGroup);


    @Named("questionnaireProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    QuestionnaireProfileDTO toDtoQuestionnaireProfileId(QuestionnaireProfile questionnaireProfile);
}
