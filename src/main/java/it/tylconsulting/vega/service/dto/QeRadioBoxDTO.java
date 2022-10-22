package it.tylconsulting.vega.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.QeRadioBox} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeRadioBoxDTO implements Serializable {

    private Long id;

    private String label;

    private String boxvalue;

    private Boolean checked;

    private Integer position;

    private QeRadioGroupDTO qeRadioGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBoxvalue() {
        return boxvalue;
    }

    public void setBoxvalue(String boxvalue) {
        this.boxvalue = boxvalue;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QeRadioGroupDTO getQeRadioGroup() {
        return qeRadioGroup;
    }

    public void setQeRadioGroup(QeRadioGroupDTO qeRadioGroup) {
        this.qeRadioGroup = qeRadioGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeRadioBoxDTO)) {
            return false;
        }

        QeRadioBoxDTO qeRadioBoxDTO = (QeRadioBoxDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qeRadioBoxDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeRadioBoxDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", boxvalue='" + getBoxvalue() + "'" +
            ", checked='" + getChecked() + "'" +
            ", position=" + getPosition() +
            ", qeRadioGroup=" + getQeRadioGroup() +
            "}";
    }
}
