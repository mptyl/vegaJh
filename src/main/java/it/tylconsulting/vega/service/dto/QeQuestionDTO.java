package it.tylconsulting.vega.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.QeQuestion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeQuestionDTO implements Serializable {

    private Long id;

    private String nodeId;

    private String text;

    private String title;

    private String questionText;

    private String note;

    private Integer minReplyNumber;

    private Integer maxReplyNumber;

    private Boolean randomRepliesOrder;

    private Integer valueOfAnswerSum;

    private Integer attachmentsRequired;

    private String image64;

    private String imageAlt;

    private Long nodeToExpand;

    private Integer position;

    private QeGroupDTO qeGroup;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getMinReplyNumber() {
        return minReplyNumber;
    }

    public void setMinReplyNumber(Integer minReplyNumber) {
        this.minReplyNumber = minReplyNumber;
    }

    public Integer getMaxReplyNumber() {
        return maxReplyNumber;
    }

    public void setMaxReplyNumber(Integer maxReplyNumber) {
        this.maxReplyNumber = maxReplyNumber;
    }

    public Boolean getRandomRepliesOrder() {
        return randomRepliesOrder;
    }

    public void setRandomRepliesOrder(Boolean randomRepliesOrder) {
        this.randomRepliesOrder = randomRepliesOrder;
    }

    public Integer getValueOfAnswerSum() {
        return valueOfAnswerSum;
    }

    public void setValueOfAnswerSum(Integer valueOfAnswerSum) {
        this.valueOfAnswerSum = valueOfAnswerSum;
    }

    public Integer getAttachmentsRequired() {
        return attachmentsRequired;
    }

    public void setAttachmentsRequired(Integer attachmentsRequired) {
        this.attachmentsRequired = attachmentsRequired;
    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Long getNodeToExpand() {
        return nodeToExpand;
    }

    public void setNodeToExpand(Long nodeToExpand) {
        this.nodeToExpand = nodeToExpand;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QeGroupDTO getQeGroup() {
        return qeGroup;
    }

    public void setQeGroup(QeGroupDTO qeGroup) {
        this.qeGroup = qeGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeQuestionDTO)) {
            return false;
        }

        QeQuestionDTO qeQuestionDTO = (QeQuestionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qeQuestionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeQuestionDTO{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", title='" + getTitle() + "'" +
            ", questionText='" + getQuestionText() + "'" +
            ", note='" + getNote() + "'" +
            ", minReplyNumber=" + getMinReplyNumber() +
            ", maxReplyNumber=" + getMaxReplyNumber() +
            ", randomRepliesOrder='" + getRandomRepliesOrder() + "'" +
            ", valueOfAnswerSum=" + getValueOfAnswerSum() +
            ", attachmentsRequired=" + getAttachmentsRequired() +
            ", image64='" + getImage64() + "'" +
            ", imageAlt='" + getImageAlt() + "'" +
            ", nodeToExpand=" + getNodeToExpand() +
            ", position=" + getPosition() +
            ", qeGroup=" + getQeGroup() +
            "}";
    }
}
