package it.tylconsulting.vega.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.QeGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeGroupDTO implements Serializable {

    private Long id;

    private String nodeId;

    private String text;

    private Boolean random;

    private Integer position;

    private QuestionnaireDTO questionnaire;

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

    public Boolean getRandom() {
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QuestionnaireDTO getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(QuestionnaireDTO questionnaire) {
        this.questionnaire = questionnaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeGroupDTO)) {
            return false;
        }

        QeGroupDTO qeGroupDTO = (QeGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qeGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeGroupDTO{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", random='" + getRandom() + "'" +
            ", position=" + getPosition() +
            ", questionnaire=" + getQuestionnaire() +
            "}";
    }
}
