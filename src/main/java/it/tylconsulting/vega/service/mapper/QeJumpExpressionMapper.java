package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeJumpExpression;
import it.tylconsulting.vega.domain.QeQuestion;
import it.tylconsulting.vega.service.dto.QeJumpExpressionDTO;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeJumpExpression} and its DTO {@link QeJumpExpressionDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeJumpExpressionMapper extends EntityMapper<QeJumpExpressionDTO, QeJumpExpression> {
    @Mapping(target = "qeQuestion", source = "qeQuestion", qualifiedByName = "qeQuestionId")
    QeJumpExpressionDTO toDto(QeJumpExpression s);

    @Named("qeQuestionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QeQuestionDTO toDtoQeQuestionId(QeQuestion qeQuestion);
}
