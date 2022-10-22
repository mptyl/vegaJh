package it.tylconsulting.vega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.tylconsulting.vega.domain.enumeration.Orientation;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QeCheckGroup.
 */
@Entity
@Table(name = "qe_check_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeCheckGroup implements Serializable {

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

    @Column(name = "radiobox_group_name")
    private String radioboxGroupName;

    @Enumerated(EnumType.STRING)
    @Column(name = "orientation")
    private Orientation orientation;

    @Column(name = "positio")
    private Integer positio;

    @OneToMany(mappedBy = "qeCheckGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qeCheckGroup" }, allowSetters = true)
    private Set<QeCheckBox> qeCheckBoxes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "qeReplies", "qeJumpExpressions", "qeRadioGroups", "qeCheckGroups", "qeGroup" }, allowSetters = true)
    private QeQuestion qeQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeCheckGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public QeCheckGroup nodeId(String nodeId) {
        this.setNodeId(nodeId);
        return this;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return this.text;
    }

    public QeCheckGroup text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRadioboxGroupName() {
        return this.radioboxGroupName;
    }

    public QeCheckGroup radioboxGroupName(String radioboxGroupName) {
        this.setRadioboxGroupName(radioboxGroupName);
        return this;
    }

    public void setRadioboxGroupName(String radioboxGroupName) {
        this.radioboxGroupName = radioboxGroupName;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public QeCheckGroup orientation(Orientation orientation) {
        this.setOrientation(orientation);
        return this;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Integer getPositio() {
        return this.positio;
    }

    public QeCheckGroup positio(Integer positio) {
        this.setPositio(positio);
        return this;
    }

    public void setPositio(Integer positio) {
        this.positio = positio;
    }

    public Set<QeCheckBox> getQeCheckBoxes() {
        return this.qeCheckBoxes;
    }

    public void setQeCheckBoxes(Set<QeCheckBox> qeCheckBoxes) {
        if (this.qeCheckBoxes != null) {
            this.qeCheckBoxes.forEach(i -> i.setQeCheckGroup(null));
        }
        if (qeCheckBoxes != null) {
            qeCheckBoxes.forEach(i -> i.setQeCheckGroup(this));
        }
        this.qeCheckBoxes = qeCheckBoxes;
    }

    public QeCheckGroup qeCheckBoxes(Set<QeCheckBox> qeCheckBoxes) {
        this.setQeCheckBoxes(qeCheckBoxes);
        return this;
    }

    public QeCheckGroup addQeCheckBox(QeCheckBox qeCheckBox) {
        this.qeCheckBoxes.add(qeCheckBox);
        qeCheckBox.setQeCheckGroup(this);
        return this;
    }

    public QeCheckGroup removeQeCheckBox(QeCheckBox qeCheckBox) {
        this.qeCheckBoxes.remove(qeCheckBox);
        qeCheckBox.setQeCheckGroup(null);
        return this;
    }

    public QeQuestion getQeQuestion() {
        return this.qeQuestion;
    }

    public void setQeQuestion(QeQuestion qeQuestion) {
        this.qeQuestion = qeQuestion;
    }

    public QeCheckGroup qeQuestion(QeQuestion qeQuestion) {
        this.setQeQuestion(qeQuestion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeCheckGroup)) {
            return false;
        }
        return id != null && id.equals(((QeCheckGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeCheckGroup{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", radioboxGroupName='" + getRadioboxGroupName() + "'" +
            ", orientation='" + getOrientation() + "'" +
            ", positio=" + getPositio() +
            "}";
    }
}
