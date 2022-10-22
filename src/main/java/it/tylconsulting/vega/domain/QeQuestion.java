package it.tylconsulting.vega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QeQuestion.
 */
@Entity
@Table(name = "qe_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeQuestion implements Serializable {

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

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "note")
    private String note;

    @Column(name = "min_reply_number")
    private Integer minReplyNumber;

    @Column(name = "max_reply_number")
    private Integer maxReplyNumber;

    @Column(name = "random_replies_order")
    private Boolean randomRepliesOrder;

    @Column(name = "value_of_answer_sum")
    private Integer valueOfAnswerSum;

    @Column(name = "attachments_required")
    private Integer attachmentsRequired;

    @Column(name = "image_64")
    private String image64;

    @Column(name = "image_alt")
    private String imageAlt;

    @Column(name = "node_to_expand")
    private Long nodeToExpand;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "qeQuestion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qeQuestion" }, allowSetters = true)
    private Set<QeReply> qeReplies = new HashSet<>();

    @OneToMany(mappedBy = "qeQuestion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qeQuestion" }, allowSetters = true)
    private Set<QeJumpExpression> qeJumpExpressions = new HashSet<>();

    @OneToMany(mappedBy = "qeQuestion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qeRadioBoxes", "qeQuestion" }, allowSetters = true)
    private Set<QeRadioGroup> qeRadioGroups = new HashSet<>();

    @OneToMany(mappedBy = "qeQuestion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qeCheckBoxes", "qeQuestion" }, allowSetters = true)
    private Set<QeCheckGroup> qeCheckGroups = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "qeQuestions", "questionnaire" }, allowSetters = true)
    private QeGroup qeGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeQuestion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public QeQuestion nodeId(String nodeId) {
        this.setNodeId(nodeId);
        return this;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return this.text;
    }

    public QeQuestion text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public QeQuestion title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public QeQuestion questionText(String questionText) {
        this.setQuestionText(questionText);
        return this;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getNote() {
        return this.note;
    }

    public QeQuestion note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getMinReplyNumber() {
        return this.minReplyNumber;
    }

    public QeQuestion minReplyNumber(Integer minReplyNumber) {
        this.setMinReplyNumber(minReplyNumber);
        return this;
    }

    public void setMinReplyNumber(Integer minReplyNumber) {
        this.minReplyNumber = minReplyNumber;
    }

    public Integer getMaxReplyNumber() {
        return this.maxReplyNumber;
    }

    public QeQuestion maxReplyNumber(Integer maxReplyNumber) {
        this.setMaxReplyNumber(maxReplyNumber);
        return this;
    }

    public void setMaxReplyNumber(Integer maxReplyNumber) {
        this.maxReplyNumber = maxReplyNumber;
    }

    public Boolean getRandomRepliesOrder() {
        return this.randomRepliesOrder;
    }

    public QeQuestion randomRepliesOrder(Boolean randomRepliesOrder) {
        this.setRandomRepliesOrder(randomRepliesOrder);
        return this;
    }

    public void setRandomRepliesOrder(Boolean randomRepliesOrder) {
        this.randomRepliesOrder = randomRepliesOrder;
    }

    public Integer getValueOfAnswerSum() {
        return this.valueOfAnswerSum;
    }

    public QeQuestion valueOfAnswerSum(Integer valueOfAnswerSum) {
        this.setValueOfAnswerSum(valueOfAnswerSum);
        return this;
    }

    public void setValueOfAnswerSum(Integer valueOfAnswerSum) {
        this.valueOfAnswerSum = valueOfAnswerSum;
    }

    public Integer getAttachmentsRequired() {
        return this.attachmentsRequired;
    }

    public QeQuestion attachmentsRequired(Integer attachmentsRequired) {
        this.setAttachmentsRequired(attachmentsRequired);
        return this;
    }

    public void setAttachmentsRequired(Integer attachmentsRequired) {
        this.attachmentsRequired = attachmentsRequired;
    }

    public String getImage64() {
        return this.image64;
    }

    public QeQuestion image64(String image64) {
        this.setImage64(image64);
        return this;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }

    public String getImageAlt() {
        return this.imageAlt;
    }

    public QeQuestion imageAlt(String imageAlt) {
        this.setImageAlt(imageAlt);
        return this;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Long getNodeToExpand() {
        return this.nodeToExpand;
    }

    public QeQuestion nodeToExpand(Long nodeToExpand) {
        this.setNodeToExpand(nodeToExpand);
        return this;
    }

    public void setNodeToExpand(Long nodeToExpand) {
        this.nodeToExpand = nodeToExpand;
    }

    public Integer getPosition() {
        return this.position;
    }

    public QeQuestion position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Set<QeReply> getQeReplies() {
        return this.qeReplies;
    }

    public void setQeReplies(Set<QeReply> qeReplies) {
        if (this.qeReplies != null) {
            this.qeReplies.forEach(i -> i.setQeQuestion(null));
        }
        if (qeReplies != null) {
            qeReplies.forEach(i -> i.setQeQuestion(this));
        }
        this.qeReplies = qeReplies;
    }

    public QeQuestion qeReplies(Set<QeReply> qeReplies) {
        this.setQeReplies(qeReplies);
        return this;
    }

    public QeQuestion addQeReply(QeReply qeReply) {
        this.qeReplies.add(qeReply);
        qeReply.setQeQuestion(this);
        return this;
    }

    public QeQuestion removeQeReply(QeReply qeReply) {
        this.qeReplies.remove(qeReply);
        qeReply.setQeQuestion(null);
        return this;
    }

    public Set<QeJumpExpression> getQeJumpExpressions() {
        return this.qeJumpExpressions;
    }

    public void setQeJumpExpressions(Set<QeJumpExpression> qeJumpExpressions) {
        if (this.qeJumpExpressions != null) {
            this.qeJumpExpressions.forEach(i -> i.setQeQuestion(null));
        }
        if (qeJumpExpressions != null) {
            qeJumpExpressions.forEach(i -> i.setQeQuestion(this));
        }
        this.qeJumpExpressions = qeJumpExpressions;
    }

    public QeQuestion qeJumpExpressions(Set<QeJumpExpression> qeJumpExpressions) {
        this.setQeJumpExpressions(qeJumpExpressions);
        return this;
    }

    public QeQuestion addQeJumpExpression(QeJumpExpression qeJumpExpression) {
        this.qeJumpExpressions.add(qeJumpExpression);
        qeJumpExpression.setQeQuestion(this);
        return this;
    }

    public QeQuestion removeQeJumpExpression(QeJumpExpression qeJumpExpression) {
        this.qeJumpExpressions.remove(qeJumpExpression);
        qeJumpExpression.setQeQuestion(null);
        return this;
    }

    public Set<QeRadioGroup> getQeRadioGroups() {
        return this.qeRadioGroups;
    }

    public void setQeRadioGroups(Set<QeRadioGroup> qeRadioGroups) {
        if (this.qeRadioGroups != null) {
            this.qeRadioGroups.forEach(i -> i.setQeQuestion(null));
        }
        if (qeRadioGroups != null) {
            qeRadioGroups.forEach(i -> i.setQeQuestion(this));
        }
        this.qeRadioGroups = qeRadioGroups;
    }

    public QeQuestion qeRadioGroups(Set<QeRadioGroup> qeRadioGroups) {
        this.setQeRadioGroups(qeRadioGroups);
        return this;
    }

    public QeQuestion addQeRadioGroup(QeRadioGroup qeRadioGroup) {
        this.qeRadioGroups.add(qeRadioGroup);
        qeRadioGroup.setQeQuestion(this);
        return this;
    }

    public QeQuestion removeQeRadioGroup(QeRadioGroup qeRadioGroup) {
        this.qeRadioGroups.remove(qeRadioGroup);
        qeRadioGroup.setQeQuestion(null);
        return this;
    }

    public Set<QeCheckGroup> getQeCheckGroups() {
        return this.qeCheckGroups;
    }

    public void setQeCheckGroups(Set<QeCheckGroup> qeCheckGroups) {
        if (this.qeCheckGroups != null) {
            this.qeCheckGroups.forEach(i -> i.setQeQuestion(null));
        }
        if (qeCheckGroups != null) {
            qeCheckGroups.forEach(i -> i.setQeQuestion(this));
        }
        this.qeCheckGroups = qeCheckGroups;
    }

    public QeQuestion qeCheckGroups(Set<QeCheckGroup> qeCheckGroups) {
        this.setQeCheckGroups(qeCheckGroups);
        return this;
    }

    public QeQuestion addQeCheckGroup(QeCheckGroup qeCheckGroup) {
        this.qeCheckGroups.add(qeCheckGroup);
        qeCheckGroup.setQeQuestion(this);
        return this;
    }

    public QeQuestion removeQeCheckGroup(QeCheckGroup qeCheckGroup) {
        this.qeCheckGroups.remove(qeCheckGroup);
        qeCheckGroup.setQeQuestion(null);
        return this;
    }

    public QeGroup getQeGroup() {
        return this.qeGroup;
    }

    public void setQeGroup(QeGroup qeGroup) {
        this.qeGroup = qeGroup;
    }

    public QeQuestion qeGroup(QeGroup qeGroup) {
        this.setQeGroup(qeGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeQuestion)) {
            return false;
        }
        return id != null && id.equals(((QeQuestion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeQuestion{" +
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
            "}";
    }
}
