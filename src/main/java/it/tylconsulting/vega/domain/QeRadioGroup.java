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
 * A QeRadioGroup.
 */
@Entity
@Table(name = "qe_radio_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeRadioGroup implements Serializable {

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

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "qeRadioGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qeRadioGroup" }, allowSetters = true)
    private Set<QeRadioBox> qeRadioBoxes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "qeReplies", "qeJumpExpressions", "qeRadioGroups", "qeCheckGroups", "qeGroup" }, allowSetters = true)
    private QeQuestion qeQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeRadioGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public QeRadioGroup nodeId(String nodeId) {
        this.setNodeId(nodeId);
        return this;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getText() {
        return this.text;
    }

    public QeRadioGroup text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRadioboxGroupName() {
        return this.radioboxGroupName;
    }

    public QeRadioGroup radioboxGroupName(String radioboxGroupName) {
        this.setRadioboxGroupName(radioboxGroupName);
        return this;
    }

    public void setRadioboxGroupName(String radioboxGroupName) {
        this.radioboxGroupName = radioboxGroupName;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public QeRadioGroup orientation(Orientation orientation) {
        this.setOrientation(orientation);
        return this;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Integer getPosition() {
        return this.position;
    }

    public QeRadioGroup position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Set<QeRadioBox> getQeRadioBoxes() {
        return this.qeRadioBoxes;
    }

    public void setQeRadioBoxes(Set<QeRadioBox> qeRadioBoxes) {
        if (this.qeRadioBoxes != null) {
            this.qeRadioBoxes.forEach(i -> i.setQeRadioGroup(null));
        }
        if (qeRadioBoxes != null) {
            qeRadioBoxes.forEach(i -> i.setQeRadioGroup(this));
        }
        this.qeRadioBoxes = qeRadioBoxes;
    }

    public QeRadioGroup qeRadioBoxes(Set<QeRadioBox> qeRadioBoxes) {
        this.setQeRadioBoxes(qeRadioBoxes);
        return this;
    }

    public QeRadioGroup addQeRadioBox(QeRadioBox qeRadioBox) {
        this.qeRadioBoxes.add(qeRadioBox);
        qeRadioBox.setQeRadioGroup(this);
        return this;
    }

    public QeRadioGroup removeQeRadioBox(QeRadioBox qeRadioBox) {
        this.qeRadioBoxes.remove(qeRadioBox);
        qeRadioBox.setQeRadioGroup(null);
        return this;
    }

    public QeQuestion getQeQuestion() {
        return this.qeQuestion;
    }

    public void setQeQuestion(QeQuestion qeQuestion) {
        this.qeQuestion = qeQuestion;
    }

    public QeRadioGroup qeQuestion(QeQuestion qeQuestion) {
        this.setQeQuestion(qeQuestion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeRadioGroup)) {
            return false;
        }
        return id != null && id.equals(((QeRadioGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeRadioGroup{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", text='" + getText() + "'" +
            ", radioboxGroupName='" + getRadioboxGroupName() + "'" +
            ", orientation='" + getOrientation() + "'" +
            ", position=" + getPosition() +
            "}";
    }
}
