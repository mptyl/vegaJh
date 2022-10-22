package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeQuestion;
import it.tylconsulting.vega.repository.QeQuestionRepository;
import it.tylconsulting.vega.service.dto.QeQuestionDTO;
import it.tylconsulting.vega.service.mapper.QeQuestionMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link QeQuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeQuestionResourceIT {

    private static final String DEFAULT_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_REPLY_NUMBER = 1;
    private static final Integer UPDATED_MIN_REPLY_NUMBER = 2;

    private static final Integer DEFAULT_MAX_REPLY_NUMBER = 1;
    private static final Integer UPDATED_MAX_REPLY_NUMBER = 2;

    private static final Boolean DEFAULT_RANDOM_REPLIES_ORDER = false;
    private static final Boolean UPDATED_RANDOM_REPLIES_ORDER = true;

    private static final Integer DEFAULT_VALUE_OF_ANSWER_SUM = 1;
    private static final Integer UPDATED_VALUE_OF_ANSWER_SUM = 2;

    private static final Integer DEFAULT_ATTACHMENTS_REQUIRED = 1;
    private static final Integer UPDATED_ATTACHMENTS_REQUIRED = 2;

    private static final String DEFAULT_IMAGE_64 = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_64 = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_ALT = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_ALT = "BBBBBBBBBB";

    private static final Long DEFAULT_NODE_TO_EXPAND = 1L;
    private static final Long UPDATED_NODE_TO_EXPAND = 2L;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/qe-questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeQuestionRepository qeQuestionRepository;

    @Autowired
    private QeQuestionMapper qeQuestionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeQuestionMockMvc;

    private QeQuestion qeQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeQuestion createEntity(EntityManager em) {
        QeQuestion qeQuestion = new QeQuestion()
            .nodeId(DEFAULT_NODE_ID)
            .text(DEFAULT_TEXT)
            .title(DEFAULT_TITLE)
            .questionText(DEFAULT_QUESTION_TEXT)
            .note(DEFAULT_NOTE)
            .minReplyNumber(DEFAULT_MIN_REPLY_NUMBER)
            .maxReplyNumber(DEFAULT_MAX_REPLY_NUMBER)
            .randomRepliesOrder(DEFAULT_RANDOM_REPLIES_ORDER)
            .valueOfAnswerSum(DEFAULT_VALUE_OF_ANSWER_SUM)
            .attachmentsRequired(DEFAULT_ATTACHMENTS_REQUIRED)
            .image64(DEFAULT_IMAGE_64)
            .imageAlt(DEFAULT_IMAGE_ALT)
            .nodeToExpand(DEFAULT_NODE_TO_EXPAND)
            .position(DEFAULT_POSITION);
        return qeQuestion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeQuestion createUpdatedEntity(EntityManager em) {
        QeQuestion qeQuestion = new QeQuestion()
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .title(UPDATED_TITLE)
            .questionText(UPDATED_QUESTION_TEXT)
            .note(UPDATED_NOTE)
            .minReplyNumber(UPDATED_MIN_REPLY_NUMBER)
            .maxReplyNumber(UPDATED_MAX_REPLY_NUMBER)
            .randomRepliesOrder(UPDATED_RANDOM_REPLIES_ORDER)
            .valueOfAnswerSum(UPDATED_VALUE_OF_ANSWER_SUM)
            .attachmentsRequired(UPDATED_ATTACHMENTS_REQUIRED)
            .image64(UPDATED_IMAGE_64)
            .imageAlt(UPDATED_IMAGE_ALT)
            .nodeToExpand(UPDATED_NODE_TO_EXPAND)
            .position(UPDATED_POSITION);
        return qeQuestion;
    }

    @BeforeEach
    public void initTest() {
        qeQuestion = createEntity(em);
    }

    @Test
    @Transactional
    void createQeQuestion() throws Exception {
        int databaseSizeBeforeCreate = qeQuestionRepository.findAll().size();
        // Create the QeQuestion
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);
        restQeQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        QeQuestion testQeQuestion = qeQuestionList.get(qeQuestionList.size() - 1);
        assertThat(testQeQuestion.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeQuestion.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeQuestion.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testQeQuestion.getQuestionText()).isEqualTo(DEFAULT_QUESTION_TEXT);
        assertThat(testQeQuestion.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testQeQuestion.getMinReplyNumber()).isEqualTo(DEFAULT_MIN_REPLY_NUMBER);
        assertThat(testQeQuestion.getMaxReplyNumber()).isEqualTo(DEFAULT_MAX_REPLY_NUMBER);
        assertThat(testQeQuestion.getRandomRepliesOrder()).isEqualTo(DEFAULT_RANDOM_REPLIES_ORDER);
        assertThat(testQeQuestion.getValueOfAnswerSum()).isEqualTo(DEFAULT_VALUE_OF_ANSWER_SUM);
        assertThat(testQeQuestion.getAttachmentsRequired()).isEqualTo(DEFAULT_ATTACHMENTS_REQUIRED);
        assertThat(testQeQuestion.getImage64()).isEqualTo(DEFAULT_IMAGE_64);
        assertThat(testQeQuestion.getImageAlt()).isEqualTo(DEFAULT_IMAGE_ALT);
        assertThat(testQeQuestion.getNodeToExpand()).isEqualTo(DEFAULT_NODE_TO_EXPAND);
        assertThat(testQeQuestion.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createQeQuestionWithExistingId() throws Exception {
        // Create the QeQuestion with an existing ID
        qeQuestion.setId(1L);
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);

        int databaseSizeBeforeCreate = qeQuestionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeQuestions() throws Exception {
        // Initialize the database
        qeQuestionRepository.saveAndFlush(qeQuestion);

        // Get all the qeQuestionList
        restQeQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].questionText").value(hasItem(DEFAULT_QUESTION_TEXT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].minReplyNumber").value(hasItem(DEFAULT_MIN_REPLY_NUMBER)))
            .andExpect(jsonPath("$.[*].maxReplyNumber").value(hasItem(DEFAULT_MAX_REPLY_NUMBER)))
            .andExpect(jsonPath("$.[*].randomRepliesOrder").value(hasItem(DEFAULT_RANDOM_REPLIES_ORDER.booleanValue())))
            .andExpect(jsonPath("$.[*].valueOfAnswerSum").value(hasItem(DEFAULT_VALUE_OF_ANSWER_SUM)))
            .andExpect(jsonPath("$.[*].attachmentsRequired").value(hasItem(DEFAULT_ATTACHMENTS_REQUIRED)))
            .andExpect(jsonPath("$.[*].image64").value(hasItem(DEFAULT_IMAGE_64)))
            .andExpect(jsonPath("$.[*].imageAlt").value(hasItem(DEFAULT_IMAGE_ALT)))
            .andExpect(jsonPath("$.[*].nodeToExpand").value(hasItem(DEFAULT_NODE_TO_EXPAND.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    void getQeQuestion() throws Exception {
        // Initialize the database
        qeQuestionRepository.saveAndFlush(qeQuestion);

        // Get the qeQuestion
        restQeQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, qeQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeQuestion.getId().intValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.questionText").value(DEFAULT_QUESTION_TEXT))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.minReplyNumber").value(DEFAULT_MIN_REPLY_NUMBER))
            .andExpect(jsonPath("$.maxReplyNumber").value(DEFAULT_MAX_REPLY_NUMBER))
            .andExpect(jsonPath("$.randomRepliesOrder").value(DEFAULT_RANDOM_REPLIES_ORDER.booleanValue()))
            .andExpect(jsonPath("$.valueOfAnswerSum").value(DEFAULT_VALUE_OF_ANSWER_SUM))
            .andExpect(jsonPath("$.attachmentsRequired").value(DEFAULT_ATTACHMENTS_REQUIRED))
            .andExpect(jsonPath("$.image64").value(DEFAULT_IMAGE_64))
            .andExpect(jsonPath("$.imageAlt").value(DEFAULT_IMAGE_ALT))
            .andExpect(jsonPath("$.nodeToExpand").value(DEFAULT_NODE_TO_EXPAND.intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingQeQuestion() throws Exception {
        // Get the qeQuestion
        restQeQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeQuestion() throws Exception {
        // Initialize the database
        qeQuestionRepository.saveAndFlush(qeQuestion);

        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();

        // Update the qeQuestion
        QeQuestion updatedQeQuestion = qeQuestionRepository.findById(qeQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedQeQuestion are not directly saved in db
        em.detach(updatedQeQuestion);
        updatedQeQuestion
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .title(UPDATED_TITLE)
            .questionText(UPDATED_QUESTION_TEXT)
            .note(UPDATED_NOTE)
            .minReplyNumber(UPDATED_MIN_REPLY_NUMBER)
            .maxReplyNumber(UPDATED_MAX_REPLY_NUMBER)
            .randomRepliesOrder(UPDATED_RANDOM_REPLIES_ORDER)
            .valueOfAnswerSum(UPDATED_VALUE_OF_ANSWER_SUM)
            .attachmentsRequired(UPDATED_ATTACHMENTS_REQUIRED)
            .image64(UPDATED_IMAGE_64)
            .imageAlt(UPDATED_IMAGE_ALT)
            .nodeToExpand(UPDATED_NODE_TO_EXPAND)
            .position(UPDATED_POSITION);
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(updatedQeQuestion);

        restQeQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeQuestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
        QeQuestion testQeQuestion = qeQuestionList.get(qeQuestionList.size() - 1);
        assertThat(testQeQuestion.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeQuestion.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeQuestion.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQeQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
        assertThat(testQeQuestion.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testQeQuestion.getMinReplyNumber()).isEqualTo(UPDATED_MIN_REPLY_NUMBER);
        assertThat(testQeQuestion.getMaxReplyNumber()).isEqualTo(UPDATED_MAX_REPLY_NUMBER);
        assertThat(testQeQuestion.getRandomRepliesOrder()).isEqualTo(UPDATED_RANDOM_REPLIES_ORDER);
        assertThat(testQeQuestion.getValueOfAnswerSum()).isEqualTo(UPDATED_VALUE_OF_ANSWER_SUM);
        assertThat(testQeQuestion.getAttachmentsRequired()).isEqualTo(UPDATED_ATTACHMENTS_REQUIRED);
        assertThat(testQeQuestion.getImage64()).isEqualTo(UPDATED_IMAGE_64);
        assertThat(testQeQuestion.getImageAlt()).isEqualTo(UPDATED_IMAGE_ALT);
        assertThat(testQeQuestion.getNodeToExpand()).isEqualTo(UPDATED_NODE_TO_EXPAND);
        assertThat(testQeQuestion.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingQeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();
        qeQuestion.setId(count.incrementAndGet());

        // Create the QeQuestion
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeQuestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();
        qeQuestion.setId(count.incrementAndGet());

        // Create the QeQuestion
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();
        qeQuestion.setId(count.incrementAndGet());

        // Create the QeQuestion
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeQuestionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeQuestionWithPatch() throws Exception {
        // Initialize the database
        qeQuestionRepository.saveAndFlush(qeQuestion);

        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();

        // Update the qeQuestion using partial update
        QeQuestion partialUpdatedQeQuestion = new QeQuestion();
        partialUpdatedQeQuestion.setId(qeQuestion.getId());

        partialUpdatedQeQuestion
            .nodeId(UPDATED_NODE_ID)
            .questionText(UPDATED_QUESTION_TEXT)
            .note(UPDATED_NOTE)
            .minReplyNumber(UPDATED_MIN_REPLY_NUMBER)
            .maxReplyNumber(UPDATED_MAX_REPLY_NUMBER)
            .randomRepliesOrder(UPDATED_RANDOM_REPLIES_ORDER)
            .image64(UPDATED_IMAGE_64);

        restQeQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeQuestion))
            )
            .andExpect(status().isOk());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
        QeQuestion testQeQuestion = qeQuestionList.get(qeQuestionList.size() - 1);
        assertThat(testQeQuestion.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeQuestion.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeQuestion.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testQeQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
        assertThat(testQeQuestion.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testQeQuestion.getMinReplyNumber()).isEqualTo(UPDATED_MIN_REPLY_NUMBER);
        assertThat(testQeQuestion.getMaxReplyNumber()).isEqualTo(UPDATED_MAX_REPLY_NUMBER);
        assertThat(testQeQuestion.getRandomRepliesOrder()).isEqualTo(UPDATED_RANDOM_REPLIES_ORDER);
        assertThat(testQeQuestion.getValueOfAnswerSum()).isEqualTo(DEFAULT_VALUE_OF_ANSWER_SUM);
        assertThat(testQeQuestion.getAttachmentsRequired()).isEqualTo(DEFAULT_ATTACHMENTS_REQUIRED);
        assertThat(testQeQuestion.getImage64()).isEqualTo(UPDATED_IMAGE_64);
        assertThat(testQeQuestion.getImageAlt()).isEqualTo(DEFAULT_IMAGE_ALT);
        assertThat(testQeQuestion.getNodeToExpand()).isEqualTo(DEFAULT_NODE_TO_EXPAND);
        assertThat(testQeQuestion.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateQeQuestionWithPatch() throws Exception {
        // Initialize the database
        qeQuestionRepository.saveAndFlush(qeQuestion);

        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();

        // Update the qeQuestion using partial update
        QeQuestion partialUpdatedQeQuestion = new QeQuestion();
        partialUpdatedQeQuestion.setId(qeQuestion.getId());

        partialUpdatedQeQuestion
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .title(UPDATED_TITLE)
            .questionText(UPDATED_QUESTION_TEXT)
            .note(UPDATED_NOTE)
            .minReplyNumber(UPDATED_MIN_REPLY_NUMBER)
            .maxReplyNumber(UPDATED_MAX_REPLY_NUMBER)
            .randomRepliesOrder(UPDATED_RANDOM_REPLIES_ORDER)
            .valueOfAnswerSum(UPDATED_VALUE_OF_ANSWER_SUM)
            .attachmentsRequired(UPDATED_ATTACHMENTS_REQUIRED)
            .image64(UPDATED_IMAGE_64)
            .imageAlt(UPDATED_IMAGE_ALT)
            .nodeToExpand(UPDATED_NODE_TO_EXPAND)
            .position(UPDATED_POSITION);

        restQeQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeQuestion))
            )
            .andExpect(status().isOk());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
        QeQuestion testQeQuestion = qeQuestionList.get(qeQuestionList.size() - 1);
        assertThat(testQeQuestion.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeQuestion.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeQuestion.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQeQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
        assertThat(testQeQuestion.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testQeQuestion.getMinReplyNumber()).isEqualTo(UPDATED_MIN_REPLY_NUMBER);
        assertThat(testQeQuestion.getMaxReplyNumber()).isEqualTo(UPDATED_MAX_REPLY_NUMBER);
        assertThat(testQeQuestion.getRandomRepliesOrder()).isEqualTo(UPDATED_RANDOM_REPLIES_ORDER);
        assertThat(testQeQuestion.getValueOfAnswerSum()).isEqualTo(UPDATED_VALUE_OF_ANSWER_SUM);
        assertThat(testQeQuestion.getAttachmentsRequired()).isEqualTo(UPDATED_ATTACHMENTS_REQUIRED);
        assertThat(testQeQuestion.getImage64()).isEqualTo(UPDATED_IMAGE_64);
        assertThat(testQeQuestion.getImageAlt()).isEqualTo(UPDATED_IMAGE_ALT);
        assertThat(testQeQuestion.getNodeToExpand()).isEqualTo(UPDATED_NODE_TO_EXPAND);
        assertThat(testQeQuestion.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingQeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();
        qeQuestion.setId(count.incrementAndGet());

        // Create the QeQuestion
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeQuestionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();
        qeQuestion.setId(count.incrementAndGet());

        // Create the QeQuestion
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = qeQuestionRepository.findAll().size();
        qeQuestion.setId(count.incrementAndGet());

        // Create the QeQuestion
        QeQuestionDTO qeQuestionDTO = qeQuestionMapper.toDto(qeQuestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qeQuestionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeQuestion in the database
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeQuestion() throws Exception {
        // Initialize the database
        qeQuestionRepository.saveAndFlush(qeQuestion);

        int databaseSizeBeforeDelete = qeQuestionRepository.findAll().size();

        // Delete the qeQuestion
        restQeQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeQuestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeQuestion> qeQuestionList = qeQuestionRepository.findAll();
        assertThat(qeQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
