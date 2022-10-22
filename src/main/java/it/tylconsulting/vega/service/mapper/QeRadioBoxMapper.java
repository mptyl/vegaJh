package it.tylconsulting.vega.service.mapper;

import it.tylconsulting.vega.domain.QeRadioBox;
import it.tylconsulting.vega.domain.QeRadioGroup;
import it.tylconsulting.vega.service.dto.QeRadioBoxDTO;
import it.tylconsulting.vega.service.dto.QeRadioGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QeRadioBox} and its DTO {@link QeRadioBoxDTO}.
 */
@Mapper(componentModel = "spring")
public interface QeRadioBoxMapper extends EntityMapper<QeRadioBoxDTO, QeRadioBox> {
    @Mapping(target = "qeRadioGroup", source = "qeRadioGroup", qualifiedByName = "qeRadioGroupId")
    QeRadioBoxDTO toDto(QeRadioBox s);

    @Named("qeRadioGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QeRadioGroupDTO toDtoQeRadioGroupId(QeRadioGroup qeRadioGroup);
}
