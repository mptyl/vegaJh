package it.tylconsulting.vega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.tylconsulting.vega.domain.enumeration.ReplyType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QeReply.
 */
@Entity
@Table(name = "qe_reply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "text")
    private String text;

    @Column(name = "title")
    private String title;

    @Column(name = "label")
    private String label;

    @Enumerated(EnumType.STRING)
    @Column(name = "reply_type")
    private ReplyType replyType;

    @Column(name = "date_min_value")
    private LocalDate dateMinValue;

    @Column(name = "date_max_value")
    private LocalDate dateMaxValue;

    @Column(name = "integer_min_value")
    private Integer integerMinValue;

    @Column(name = "integer_max_value")
    private Integer integerMaxValue;

    @Column(name = "double_min_value")
    private Double doubleMinValue;

    @Column(name = "double_max_value")
    private Double doubleMaxValue;

    @Column(name = "range_min_value")
    private Integer rangeMinValue;

    @Column(name = "range_max_value")
    private Integer rangeMaxValue;

    @Column(name = "select_list")
    private String selectList;

    @Column(name = "step")
    private Integer step;

    @Column(name = "reply_pattern")
    private String replyPattern;

    @Column(name = "multiple")
    private Boolean multiple;

    @Column(name = "place_holder")
    private String placeHolder;

    @Column(name = "reply_required")
    private Boolean replyRequired;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    @Column(name = "with_comment")
    private Boolean withComment;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JsonIgnoreProperties(value = { "qeReplies", "qeJumpExpressions", "qeRadioGroups", "qeCheckGroups", "qeGroup" }, allowSetters = true)
    private QeQuestion qeQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeReply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public QeReply nodeId(String nodeId) {
        this.setNodeId(nodeId);
        return this;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return this.text;
    }

    public QeReply text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public QeReply title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return this.label;
    }

    public QeReply label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ReplyType getReplyType() {
        return this.replyType;
    }

    public QeReply replyType(ReplyType replyType) {
        this.setReplyType(replyType);
        return this;
    }

    public void setReplyType(ReplyType replyType) {
        this.replyType = replyType;
    }

    public LocalDate getDateMinValue() {
        return this.dateMinValue;
    }

    public QeReply dateMinValue(LocalDate dateMinValue) {
        this.setDateMinValue(dateMinValue);
        return this;
    }

    public void setDateMinValue(LocalDate dateMinValue) {
        this.dateMinValue = dateMinValue;
    }

    public LocalDate getDateMaxValue() {
        return this.dateMaxValue;
    }

    public QeReply dateMaxValue(LocalDate dateMaxValue) {
        this.setDateMaxValue(dateMaxValue);
        return this;
    }

    public void setDateMaxValue(LocalDate dateMaxValue) {
        this.dateMaxValue = dateMaxValue;
    }

    public Integer getIntegerMinValue() {
        return this.integerMinValue;
    }

    public QeReply integerMinValue(Integer integerMinValue) {
        this.setIntegerMinValue(integerMinValue);
        return this;
    }

    public void setIntegerMinValue(Integer integerMinValue) {
        this.integerMinValue = integerMinValue;
    }

    public Integer getIntegerMaxValue() {
        return this.integerMaxValue;
    }

    public QeReply integerMaxValue(Integer integerMaxValue) {
        this.setIntegerMaxValue(integerMaxValue);
        return this;
    }

    public void setIntegerMaxValue(Integer integerMaxValue) {
        this.integerMaxValue = integerMaxValue;
    }

    public Double getDoubleMinValue() {
        return this.doubleMinValue;
    }

    public QeReply doubleMinValue(Double doubleMinValue) {
        this.setDoubleMinValue(doubleMinValue);
        return this;
    }

    public void setDoubleMinValue(Double doubleMinValue) {
        this.doubleMinValue = doubleMinValue;
    }

    public Double getDoubleMaxValue() {
        return this.doubleMaxValue;
    }

    public QeReply doubleMaxValue(Double doubleMaxValue) {
        this.setDoubleMaxValue(doubleMaxValue);
        return this;
    }

    public void setDoubleMaxValue(Double doubleMaxValue) {
        this.doubleMaxValue = doubleMaxValue;
    }

    public Integer getRangeMinValue() {
        return this.rangeMinValue;
    }

    public QeReply rangeMinValue(Integer rangeMinValue) {
        this.setRangeMinValue(rangeMinValue);
        return this;
    }

    public void setRangeMinValue(Integer rangeMinValue) {
        this.rangeMinValue = rangeMinValue;
    }

    public Integer getRangeMaxValue() {
        return this.rangeMaxValue;
    }

    public QeReply rangeMaxValue(Integer rangeMaxValue) {
        this.setRangeMaxValue(rangeMaxValue);
        return this;
    }

    public void setRangeMaxValue(Integer rangeMaxValue) {
        this.rangeMaxValue = rangeMaxValue;
    }

    public String getSelectList() {
        return this.selectList;
    }

    public QeReply selectList(String selectList) {
        this.setSelectList(selectList);
        return this;
    }

    public void setSelectList(String selectList) {
        this.selectList = selectList;
    }

    public Integer getStep() {
        return this.step;
    }

    public QeReply step(Integer step) {
        this.setStep(step);
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getReplyPattern() {
        return this.replyPattern;
    }

    public QeReply replyPattern(String replyPattern) {
        this.setReplyPattern(replyPattern);
        return this;
    }

    public void setReplyPattern(String replyPattern) {
        this.replyPattern = replyPattern;
    }

    public Boolean getMultiple() {
        return this.multiple;
    }

    public QeReply multiple(Boolean multiple) {
        this.setMultiple(multiple);
        return this;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public String getPlaceHolder() {
        return this.placeHolder;
    }

    public QeReply placeHolder(String placeHolder) {
        this.setPlaceHolder(placeHolder);
        return this;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public Boolean getReplyRequired() {
        return this.replyRequired;
    }

    public QeReply replyRequired(Boolean replyRequired) {
        this.setReplyRequired(replyRequired);
        return this;
    }

    public void setReplyRequired(Boolean replyRequired) {
        this.replyRequired = replyRequired;
    }

    public Boolean getBooleanValue() {
        return this.booleanValue;
    }

    public QeReply booleanValue(Boolean booleanValue) {
        this.setBooleanValue(booleanValue);
        return this;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Boolean getWithComment() {
        return this.withComment;
    }

    public QeReply withComment(Boolean withComment) {
        this.setWithComment(withComment);
        return this;
    }

    public void setWithComment(Boolean withComment) {
        this.withComment = withComment;
    }

    public Integer getPosition() {
        return this.position;
    }

    public QeReply position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QeQuestion getQeQuestion() {
        return this.qeQuestion;
    }

    public void setQeQuestion(QeQuestion qeQuestion) {
        this.qeQuestion = qeQuestion;
    }

    public QeReply qeQuestion(QeQuestion qeQuestion) {
        this.setQeQuestion(qeQuestion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeReply)) {
            return false;
        }
        return id != null && id.equals(((QeReply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeReply{" +
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
            "}";
    }
}
