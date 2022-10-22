package it.tylconsulting.vega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QeCheckBox.
 */
@Entity
@Table(name = "qe_check_box")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeCheckBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "boxvalue")
    private String boxvalue;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JsonIgnoreProperties(value = { "qeCheckBoxes", "qeQuestion" }, allowSetters = true)
    private QeCheckGroup qeCheckGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeCheckBox id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public QeCheckBox label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBoxvalue() {
        return this.boxvalue;
    }

    public QeCheckBox boxvalue(String boxvalue) {
        this.setBoxvalue(boxvalue);
        return this;
    }

    public void setBoxvalue(String boxvalue) {
        this.boxvalue = boxvalue;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public QeCheckBox checked(Boolean checked) {
        this.setChecked(checked);
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getPosition() {
        return this.position;
    }

    public QeCheckBox position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QeCheckGroup getQeCheckGroup() {
        return this.qeCheckGroup;
    }

    public void setQeCheckGroup(QeCheckGroup qeCheckGroup) {
        this.qeCheckGroup = qeCheckGroup;
    }

    public QeCheckBox qeCheckGroup(QeCheckGroup qeCheckGroup) {
        this.setQeCheckGroup(qeCheckGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeCheckBox)) {
            return false;
        }
        return id != null && id.equals(((QeCheckBox) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeCheckBox{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", boxvalue='" + getBoxvalue() + "'" +
            ", checked='" + getChecked() + "'" +
            ", position=" + getPosition() +
            "}";
    }
}
