package it.tylconsulting.vega.service.dto;

import it.tylconsulting.vega.domain.enumeration.Orientation;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.QeCheckGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeCheckGroupDTO implements Serializable {

    private Long id;

    private String nodeId;

    private String text;

    private String radioboxGroupName;

    private Orientation orientation;

    private Integer positio;

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

    public Integer getPositio() {
        return positio;
    }

    public void setPositio(Integer positio) {
        this.positio = positio;
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
        if (!(o instanceof QeCheckGroupDTO)) {
            return false;
        }

        QeCheckGroupDTO qeCheckGroupDTO = (QeCheckGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qeCheckGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeCheckGroupDTO{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", radioboxGroupName='" + getRadioboxGroupName() + "'" +
            ", orientation='" + getOrientation() + "'" +
            ", positio=" + getPositio() +
            ", qeQuestion=" + getQeQuestion() +
            "}";
    }
}
