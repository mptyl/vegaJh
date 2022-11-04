package it.tylconsulting.vega.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.tylconsulting.vega.domain.enumeration.QuestionnaireType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.tylconsulting.vega.domain.Questionnaire} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionnaireDTO implements Serializable {

    private Long id;

    private String name;

    private String questionnaireVersion;

    private String title;

    private String subTitle;

    private String notes;

    private String image;

    private String imageAlt;

    private String instructions;

    private Integer compilationTime;

    private Integer forcedTerminationTime;

    private Integer usedSeconds;

    private Integer status;

    private String xml;

    private String json;

    private String saveText;

    /**
     * Campo utilizzato nella ricerca
     */
    @Schema(description = "Campo utilizzato nella ricerca")
    private String searchText;

    private String subjectToEvaluation;

    private QuestionnaireType questionnaireType;

    private Integer attachments;

    private String createdBy;

    private String modifiedBy;

    private Long version;

    private Long modifiedDate;

    private Long createdDate;

    private QuestionnaireGroupDTO questionnaireGroup;

    private QuestionnaireProfileDTO questionnaireProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionnaireVersion() {
        return questionnaireVersion;
    }

    public void setQuestionnaireVersion(String questionnaireVersion) {
        this.questionnaireVersion = questionnaireVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getCompilationTime() {
        return compilationTime;
    }

    public void setCompilationTime(Integer compilationTime) {
        this.compilationTime = compilationTime;
    }

    public Integer getForcedTerminationTime() {
        return forcedTerminationTime;
    }

    public void setForcedTerminationTime(Integer forcedTerminationTime) {
        this.forcedTerminationTime = forcedTerminationTime;
    }

    public Integer getUsedSeconds() {
        return usedSeconds;
    }

    public void setUsedSeconds(Integer usedSeconds) {
        this.usedSeconds = usedSeconds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getSaveText() {
        return saveText;
    }

    public void setSaveText(String saveText) {
        this.saveText = saveText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSubjectToEvaluation() {
        return subjectToEvaluation;
    }

    public void setSubjectToEvaluation(String subjectToEvaluation) {
        this.subjectToEvaluation = subjectToEvaluation;
    }

    public QuestionnaireType getQuestionnaireType() {
        return questionnaireType;
    }

    public void setQuestionnaireType(QuestionnaireType questionnaireType) {
        this.questionnaireType = questionnaireType;
    }

    public Integer getAttachments() {
        return attachments;
    }

    public void setAttachments(Integer attachments) {
        this.attachments = attachments;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public QuestionnaireGroupDTO getQuestionnaireGroup() {
        return questionnaireGroup;
    }

    public void setQuestionnaireGroup(QuestionnaireGroupDTO questionnaireGroup) {
        this.questionnaireGroup = questionnaireGroup;
    }

    public QuestionnaireProfileDTO getQuestionnaireProfile() {
        return questionnaireProfile;
    }

    public void setQuestionnaireProfile(QuestionnaireProfileDTO questionnaireProfile) {
        this.questionnaireProfile = questionnaireProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionnaireDTO)) {
            return false;
        }

        QuestionnaireDTO questionnaireDTO = (QuestionnaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, questionnaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionnaireDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", questionnaireVersion='" + getQuestionnaireVersion() + "'" +
            ", title='" + getTitle() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", notes='" + getNotes() + "'" +
            ", image='" + getImage() + "'" +
            ", imageAlt='" + getImageAlt() + "'" +
            ", instructions='" + getInstructions() + "'" +
            ", compilationTime=" + getCompilationTime() +
            ", forcedTerminationTime=" + getForcedTerminationTime() +
            ", usedSeconds=" + getUsedSeconds() +
            ", status=" + getStatus() +
            ", xml='" + getXml() + "'" +
            ", json='" + getJson() + "'" +
            ", saveText='" + getSaveText() + "'" +
            ", searchText='" + getSearchText() + "'" +
            ", subjectToEvaluation='" + getSubjectToEvaluation() + "'" +
            ", questionnaireType='" + getQuestionnaireType() + "'" +
            ", attachments=" + getAttachments() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", version=" + getVersion() +
            ", modifiedDate=" + getModifiedDate() +
            ", createdDate=" + getCreatedDate() +
            ", questionnaireGroup=" + getQuestionnaireGroup() +
            ", questionnaireProfile=" + getQuestionnaireProfile() +
            "}";
    }
}
