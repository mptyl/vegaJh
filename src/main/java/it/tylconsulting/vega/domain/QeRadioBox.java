package it.tylconsulting.vega.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QeRadioBox.
 */
@Entity
@Table(name = "qe_radio_box")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QeRadioBox implements Serializable {

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
    @JsonIgnoreProperties(value = { "qeRadioBoxes", "qeQuestion" }, allowSetters = true)
    private QeRadioGroup qeRadioGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QeRadioBox id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public QeRadioBox label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBoxvalue() {
        return this.boxvalue;
    }

    public QeRadioBox boxvalue(String boxvalue) {
        this.setBoxvalue(boxvalue);
        return this;
    }

    public void setBoxvalue(String boxvalue) {
        this.boxvalue = boxvalue;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public QeRadioBox checked(Boolean checked) {
        this.setChecked(checked);
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getPosition() {
        return this.position;
    }

    public QeRadioBox position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QeRadioGroup getQeRadioGroup() {
        return this.qeRadioGroup;
    }

    public void setQeRadioGroup(QeRadioGroup qeRadioGroup) {
        this.qeRadioGroup = qeRadioGroup;
    }

    public QeRadioBox qeRadioGroup(QeRadioGroup qeRadioGroup) {
        this.setQeRadioGroup(qeRadioGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QeRadioBox)) {
            return false;
        }
        return id != null && id.equals(((QeRadioBox) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QeRadioBox{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", boxvalue='" + getBoxvalue() + "'" +
            ", checked='" + getChecked() + "'" +
            ", position=" + getPosition() +
            "}";
    }
}
