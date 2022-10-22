package it.tylconsulting.vega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QeJumpExpression.
 */
@Entity
@Table(name = "qe_jump_expression")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeJumpExpression implements Serializable {

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

    @Column(name = "expression")
    private String expression;

    @Column(name = "jump_to")
    private String jumpTo;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JsonIgnoreProperties(value = { "qeReplies", "qeJumpExpressions", "qeRadioGroups", "qeCheckGroups", "qeGroup" }, allowSetters = true)
    private QeQuestion qeQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeJumpExpression id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public QeJumpExpression nodeId(String nodeId) {
        this.setNodeId(nodeId);
        return this;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return this.text;
    }

    public QeJumpExpression text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExpression() {
        return this.expression;
    }

    public QeJumpExpression expression(String expression) {
        this.setExpression(expression);
        return this;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getJumpTo() {
        return this.jumpTo;
    }

    public QeJumpExpression jumpTo(String jumpTo) {
        this.setJumpTo(jumpTo);
        return this;
    }

    public void setJumpTo(String jumpTo) {
        this.jumpTo = jumpTo;
    }

    public Integer getPosition() {
        return this.position;
    }

    public QeJumpExpression position(Integer position) {
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

    public QeJumpExpression qeQuestion(QeQuestion qeQuestion) {
        this.setQeQuestion(qeQuestion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeJumpExpression)) {
            return false;
        }
        return id != null && id.equals(((QeJumpExpression) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeJumpExpression{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", expression='" + getExpression() + "'" +
            ", jumpTo='" + getJumpTo() + "'" +
            ", position=" + getPosition() +
            "}";
    }
}
