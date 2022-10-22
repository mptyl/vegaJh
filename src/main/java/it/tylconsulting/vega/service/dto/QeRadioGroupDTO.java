package it.tylconsulting.vega.service.dto;

import it.tylconsulting.vega.domain.enumeration.Orientation;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.QeRadioGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeRadioGroupDTO implements Serializable {

    private Long id;

    private String nodeId;

    private String text;

    private String radioboxGroupName;

    private Orientation orientation;

    private Integer position;

    private QeQuestionDTO qeQuestion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRadioboxGroupName() {
        return radioboxGroupName;
    }

    public void setRadioboxGroupName(String radioboxGroupName) {
        this.radioboxGroupName = radioboxGroupName;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QeQuestionDTO getQeQuestion() {
        return qeQuestion;
    }

    public void setQeQuestion(QeQuestionDTO qeQuestion) {
        this.qeQuestion = qeQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeRadioGroupDTO)) {
            return false;
        }

        QeRadioGroupDTO qeRadioGroupDTO = (QeRadioGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qeRadioGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeRadioGroupDTO{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", radioboxGroupName='" + getRadioboxGroupName() + "'" +
            ", orientation='" + getOrientation() + "'" +
            ", position=" + getPosition() +
            ", qeQuestion=" + getQeQuestion() +
            "}";
    }
}
