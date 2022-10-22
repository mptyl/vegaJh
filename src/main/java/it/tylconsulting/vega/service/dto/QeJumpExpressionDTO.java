package it.tylconsulting.vega.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.QeJumpExpression} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeJumpExpressionDTO implements Serializable {

    private Long id;

    private String nodeId;

    private String text;

    private String expression;

    private String jumpTo;

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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getJumpTo() {
        return jumpTo;
    }

    public void setJumpTo(String jumpTo) {
        this.jumpTo = jumpTo;
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
        if (!(o instanceof QeJumpExpressionDTO)) {
            return false;
        }

        QeJumpExpressionDTO qeJumpExpressionDTO = (QeJumpExpressionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qeJumpExpressionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeJumpExpressionDTO{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", expression='" + getExpression() + "'" +
            ", jumpTo='" + getJumpTo() + "'" +
            ", position=" + getPosition() +
            ", qeQuestion=" + getQeQuestion() +
            "}";
    }
}
