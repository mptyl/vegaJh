package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeCheckBox;
import it.tylconsulting.vega.domain.QeCheckGroup;
import it.tylconsulting.vega.service.dto.QeCheckBoxDTO;
import it.tylconsulting.vega.service.dto.QeCheckGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeCheckBox} and its DTO {@link QeCheckBoxDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeCheckBoxMapper extends EntityMapper<QeCheckBoxDTO, QeCheckBox> {
    @Mapping(target = "qeCheckGroup", source = "qeCheckGroup", qualifiedByName = "qeCheckGroupId")
    QeCheckBoxDTO toDto(QeCheckBox s);

    @Named("qeCheckGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QeCheckGroupDTO toDtoQeCheckGroupId(QeCheckGroup qeCheckGroup);
}
