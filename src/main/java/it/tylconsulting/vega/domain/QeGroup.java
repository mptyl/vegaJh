package it.tylconsulting.vega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QeGroup.
 */
@Entity
@Table(name = "qe_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeGroup implements Serializable {

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

    @Column(name = "random")
    private Boolean random;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "qeGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qeReplies", "qeJumpExpressions", "qeRadioGroups", "qeCheckGroups", "qeGroup" }, allowSetters = true)
    private Set<QeQuestion> qeQuestions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "qeGroups", "questionnaireGroup", "questionnaireProfile" }, allowSetters = true)
    private Questionnaire questionnaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public QeGroup nodeId(String nodeId) {
        this.setNodeId(nodeId);
        return this;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return this.text;
    }

    public QeGroup text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRandom() {
        return this.random;
    }

    public QeGroup random(Boolean random) {
        this.setRandom(random);
        return this;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public Integer getPosition() {
        return this.position;
    }

    public QeGroup position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Set<QeQuestion> getQeQuestions() {
        return this.qeQuestions;
    }

    public void setQeQuestions(Set<QeQuestion> qeQuestions) {
        if (this.qeQuestions != null) {
            this.qeQuestions.forEach(i -> i.setQeGroup(null));
        }
        if (qeQuestions != null) {
            qeQuestions.forEach(i -> i.setQeGroup(this));
        }
        this.qeQuestions = qeQuestions;
    }

    public QeGroup qeQuestions(Set<QeQuestion> qeQuestions) {
        this.setQeQuestions(qeQuestions);
        return this;
    }

    public QeGroup addQeQuestion(QeQuestion qeQuestion) {
        this.qeQuestions.add(qeQuestion);
        qeQuestion.setQeGroup(this);
        return this;
    }

    public QeGroup removeQeQuestion(QeQuestion qeQuestion) {
        this.qeQuestions.remove(qeQuestion);
        qeQuestion.setQeGroup(null);
        return this;
    }

    public Questionnaire getQuestionnaire() {
        return this.questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public QeGroup questionnaire(Questionnaire questionnaire) {
        this.setQuestionnaire(questionnaire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeGroup)) {
            return false;
        }
        return id != null && id.equals(((QeGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeGroup{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", random='" + getRandom() + "'" +
            ", position=" + getPosition() +
            "}";
    }
}
