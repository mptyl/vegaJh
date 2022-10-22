package it.tylconsulting.vega.service.dto;

import it.tylconsulting.vega.domain.enumeration.ReplyType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.QeReply} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeReplyDTO implements Serializable {

    private Long id;

    private String nodeId;

    private String text;

    private String title;

    private String label;

    private ReplyType replyType;

    private LocalDate dateMinValue;

    private LocalDate dateMaxValue;

    private Integer integerMinValue;

    private Integer integerMaxValue;

    private Double doubleMinValue;

    private Double doubleMaxValue;

    private Integer rangeMinValue;

    private Integer rangeMaxValue;

    private String selectList;

    private Integer step;

    private String replyPattern;

    private Boolean multiple;

    private String placeHolder;

    private Boolean replyRequired;

    private Boolean booleanValue;

    private Boolean withComment;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ReplyType getReplyType() {
        return replyType;
    }

    public void setReplyType(ReplyType replyType) {
        this.replyType = replyType;
    }

    public LocalDate getDateMinValue() {
        return dateMinValue;
    }

    public void setDateMinValue(LocalDate dateMinValue) {
        this.dateMinValue = dateMinValue;
    }

    public LocalDate getDateMaxValue() {
        return dateMaxValue;
    }

    public void setDateMaxValue(LocalDate dateMaxValue) {
        this.dateMaxValue = dateMaxValue;
    }

    public Integer getIntegerMinValue() {
        return integerMinValue;
    }

    public void setIntegerMinValue(Integer integerMinValue) {
        this.integerMinValue = integerMinValue;
    }

    public Integer getIntegerMaxValue() {
        return integerMaxValue;
    }

    public void setIntegerMaxValue(Integer integerMaxValue) {
        this.integerMaxValue = integerMaxValue;
    }

    public Double getDoubleMinValue() {
        return doubleMinValue;
    }

    public void setDoubleMinValue(Double doubleMinValue) {
        this.doubleMinValue = doubleMinValue;
    }

    public Double getDoubleMaxValue() {
        return doubleMaxValue;
    }

    public void setDoubleMaxValue(Double doubleMaxValue) {
        this.doubleMaxValue = doubleMaxValue;
    }

    public Integer getRangeMinValue() {
        return rangeMinValue;
    }

    public void setRangeMinValue(Integer rangeMinValue) {
        this.rangeMinValue = rangeMinValue;
    }

    public Integer getRangeMaxValue() {
        return rangeMaxValue;
    }

    public void setRangeMaxValue(Integer rangeMaxValue) {
        this.rangeMaxValue = rangeMaxValue;
    }

    public String getSelectList() {
        return selectList;
    }

    public void setSelectList(String selectList) {
        this.selectList = selectList;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getReplyPattern() {
        return replyPattern;
    }

    public void setReplyPattern(String replyPattern) {
        this.replyPattern = replyPattern;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public Boolean getReplyRequired() {
        return replyRequired;
    }

    public void setReplyRequired(Boolean replyRequired) {
        this.replyRequired = replyRequired;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Boolean getWithComment() {
        return withComment;
    }

    public void setWithComment(Boolean withComment) {
        this.withComment = withComment;
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
        if (!(o instanceof QeReplyDTO)) {
            return false;
        }

        QeReplyDTO qeReplyDTO = (QeReplyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qeReplyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeReplyDTO{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", title='" + getTitle() + "'" +
            ", label='" + getLabel() + "'" +
            ", replyType='" + getReplyType() + "'" +
            ", dateMinValue='" + getDateMinValue() + "'" +
            ", dateMaxValue='" + getDateMaxValue() + "'" +
            ", integerMinValue=" + getIntegerMinValue() +
            ", integerMaxValue=" + getIntegerMaxValue() +
            ", doubleMinValue=" + getDoubleMinValue() +
            ", doubleMaxValue=" + getDoubleMaxValue() +
            ", rangeMinValue=" + getRangeMinValue() +
            ", rangeMaxValue=" + getRangeMaxValue() +
            ", selectList='" + getSelectList() + "'" +
            ", step=" + getStep() +
            ", replyPattern='" + getReplyPattern() + "'" +
            ", multiple='" + getMultiple() + "'" +
            ", placeHolder='" + getPlaceHolder() + "'" +
            ", replyRequired='" + getReplyRequired() + "'" +
            ", booleanValue='" + getBooleanValue() + "'" +
            ", withComment='" + getWithComment() + "'" +
            ", position=" + getPosition() +
            ", qeQuestion=" + getQeQuestion() +
            "}";
    }
}
