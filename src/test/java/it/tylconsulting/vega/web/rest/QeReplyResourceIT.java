package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeReply;
import it.tylconsulting.vega.domain.enumeration.ReplyType;
import it.tylconsulting.vega.repository.QeReplyRepository;
import it.tylconsulting.vega.service.dto.QeReplyDTO;
import it.tylconsulting.vega.service.mapper.QeReplyMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link QeReplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeReplyResourceIT {

    private static final String DEFAULT_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final ReplyType DEFAULT_REPLY_TYPE = ReplyType.TEXT;
    private static final ReplyType UPDATED_REPLY_TYPE = ReplyType.CHECKBOX;

    private static final LocalDate DEFAULT_DATE_MIN_VALUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MIN_VALUE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MAX_VALUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MAX_VALUE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_INTEGER_MIN_VALUE = 1;
    private static final Integer UPDATED_INTEGER_MIN_VALUE = 2;

    private static final Integer DEFAULT_INTEGER_MAX_VALUE = 1;
    private static final Integer UPDATED_INTEGER_MAX_VALUE = 2;

    private static final Double DEFAULT_DOUBLE_MIN_VALUE = 1D;
    private static final Double UPDATED_DOUBLE_MIN_VALUE = 2D;

    private static final Double DEFAULT_DOUBLE_MAX_VALUE = 1D;
    private static final Double UPDATED_DOUBLE_MAX_VALUE = 2D;

    private static final Integer DEFAULT_RANGE_MIN_VALUE = 1;
    private static final Integer UPDATED_RANGE_MIN_VALUE = 2;

    private static final Integer DEFAULT_RANGE_MAX_VALUE = 1;
    private static final Integer UPDATED_RANGE_MAX_VALUE = 2;

    private static final String DEFAULT_SELECT_LIST = "AAAAAAAAAA";
    private static final String UPDATED_SELECT_LIST = "BBBBBBBBBB";

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    private static final String DEFAULT_REPLY_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_REPLY_PATTERN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MULTIPLE = false;
    private static final Boolean UPDATED_MULTIPLE = true;

    private static final String DEFAULT_PLACE_HOLDER = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_HOLDER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REPLY_REQUIRED = false;
    private static final Boolean UPDATED_REPLY_REQUIRED = true;

    private static final Boolean DEFAULT_BOOLEAN_VALUE = false;
    private static final Boolean UPDATED_BOOLEAN_VALUE = true;

    private static final Boolean DEFAULT_WITH_COMMENT = false;
    private static final Boolean UPDATED_WITH_COMMENT = true;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/qe-replies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeReplyRepository qeReplyRepository;

    @Autowired
    private QeReplyMapper qeReplyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeReplyMockMvc;

    private QeReply qeReply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeReply createEntity(EntityManager em) {
        QeReply qeReply = new QeReply()
            .nodeId(DEFAULT_NODE_ID)
            .text(DEFAULT_TEXT)
            .title(DEFAULT_TITLE)
            .label(DEFAULT_LABEL)
            .replyType(DEFAULT_REPLY_TYPE)
            .dateMinValue(DEFAULT_DATE_MIN_VALUE)
            .dateMaxValue(DEFAULT_DATE_MAX_VALUE)
            .integerMinValue(DEFAULT_INTEGER_MIN_VALUE)
            .integerMaxValue(DEFAULT_INTEGER_MAX_VALUE)
            .doubleMinValue(DEFAULT_DOUBLE_MIN_VALUE)
            .doubleMaxValue(DEFAULT_DOUBLE_MAX_VALUE)
            .rangeMinValue(DEFAULT_RANGE_MIN_VALUE)
            .rangeMaxValue(DEFAULT_RANGE_MAX_VALUE)
            .selectList(DEFAULT_SELECT_LIST)
            .step(DEFAULT_STEP)
            .replyPattern(DEFAULT_REPLY_PATTERN)
            .multiple(DEFAULT_MULTIPLE)
            .placeHolder(DEFAULT_PLACE_HOLDER)
            .replyRequired(DEFAULT_REPLY_REQUIRED)
            .booleanValue(DEFAULT_BOOLEAN_VALUE)
            .withComment(DEFAULT_WITH_COMMENT)
            .position(DEFAULT_POSITION);
        return qeReply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeReply createUpdatedEntity(EntityManager em) {
        QeReply qeReply = new QeReply()
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .title(UPDATED_TITLE)
            .label(UPDATED_LABEL)
            .replyType(UPDATED_REPLY_TYPE)
            .dateMinValue(UPDATED_DATE_MIN_VALUE)
            .dateMaxValue(UPDATED_DATE_MAX_VALUE)
            .integerMinValue(UPDATED_INTEGER_MIN_VALUE)
            .integerMaxValue(UPDATED_INTEGER_MAX_VALUE)
            .doubleMinValue(UPDATED_DOUBLE_MIN_VALUE)
            .doubleMaxValue(UPDATED_DOUBLE_MAX_VALUE)
            .rangeMinValue(UPDATED_RANGE_MIN_VALUE)
            .rangeMaxValue(UPDATED_RANGE_MAX_VALUE)
            .selectList(UPDATED_SELECT_LIST)
            .step(UPDATED_STEP)
            .replyPattern(UPDATED_REPLY_PATTERN)
            .multiple(UPDATED_MULTIPLE)
            .placeHolder(UPDATED_PLACE_HOLDER)
            .replyRequired(UPDATED_REPLY_REQUIRED)
            .booleanValue(UPDATED_BOOLEAN_VALUE)
            .withComment(UPDATED_WITH_COMMENT)
            .position(UPDATED_POSITION);
        return qeReply;
    }

    @BeforeEach
    public void initTest() {
        qeReply = createEntity(em);
    }

    @Test
    @Transactional
    void createQeReply() throws Exception {
        int databaseSizeBeforeCreate = qeReplyRepository.findAll().size();
        // Create the QeReply
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);
        restQeReplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeReplyDTO)))
            .andExpect(status().isCreated());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeCreate + 1);
        QeReply testQeReply = qeReplyList.get(qeReplyList.size() - 1);
        assertThat(testQeReply.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeReply.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeReply.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testQeReply.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testQeReply.getReplyType()).isEqualTo(DEFAULT_REPLY_TYPE);
        assertThat(testQeReply.getDateMinValue()).isEqualTo(DEFAULT_DATE_MIN_VALUE);
        assertThat(testQeReply.getDateMaxValue()).isEqualTo(DEFAULT_DATE_MAX_VALUE);
        assertThat(testQeReply.getIntegerMinValue()).isEqualTo(DEFAULT_INTEGER_MIN_VALUE);
        assertThat(testQeReply.getIntegerMaxValue()).isEqualTo(DEFAULT_INTEGER_MAX_VALUE);
        assertThat(testQeReply.getDoubleMinValue()).isEqualTo(DEFAULT_DOUBLE_MIN_VALUE);
        assertThat(testQeReply.getDoubleMaxValue()).isEqualTo(DEFAULT_DOUBLE_MAX_VALUE);
        assertThat(testQeReply.getRangeMinValue()).isEqualTo(DEFAULT_RANGE_MIN_VALUE);
        assertThat(testQeReply.getRangeMaxValue()).isEqualTo(DEFAULT_RANGE_MAX_VALUE);
        assertThat(testQeReply.getSelectList()).isEqualTo(DEFAULT_SELECT_LIST);
        assertThat(testQeReply.getStep()).isEqualTo(DEFAULT_STEP);
        assertThat(testQeReply.getReplyPattern()).isEqualTo(DEFAULT_REPLY_PATTERN);
        assertThat(testQeReply.getMultiple()).isEqualTo(DEFAULT_MULTIPLE);
        assertThat(testQeReply.getPlaceHolder()).isEqualTo(DEFAULT_PLACE_HOLDER);
        assertThat(testQeReply.getReplyRequired()).isEqualTo(DEFAULT_REPLY_REQUIRED);
        assertThat(testQeReply.getBooleanValue()).isEqualTo(DEFAULT_BOOLEAN_VALUE);
        assertThat(testQeReply.getWithComment()).isEqualTo(DEFAULT_WITH_COMMENT);
        assertThat(testQeReply.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createQeReplyWithExistingId() throws Exception {
        // Create the QeReply with an existing ID
        qeReply.setId(1L);
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);

        int databaseSizeBeforeCreate = qeReplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeReplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeReplyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeReplies() throws Exception {
        // Initialize the database
        qeReplyRepository.saveAndFlush(qeReply);

        // Get all the qeReplyList
        restQeReplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeReply.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].replyType").value(hasItem(DEFAULT_REPLY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateMinValue").value(hasItem(DEFAULT_DATE_MIN_VALUE.toString())))
            .andExpect(jsonPath("$.[*].dateMaxValue").value(hasItem(DEFAULT_DATE_MAX_VALUE.toString())))
            .andExpect(jsonPath("$.[*].integerMinValue").value(hasItem(DEFAULT_INTEGER_MIN_VALUE)))
            .andExpect(jsonPath("$.[*].integerMaxValue").value(hasItem(DEFAULT_INTEGER_MAX_VALUE)))
            .andExpect(jsonPath("$.[*].doubleMinValue").value(hasItem(DEFAULT_DOUBLE_MIN_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].doubleMaxValue").value(hasItem(DEFAULT_DOUBLE_MAX_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].rangeMinValue").value(hasItem(DEFAULT_RANGE_MIN_VALUE)))
            .andExpect(jsonPath("$.[*].rangeMaxValue").value(hasItem(DEFAULT_RANGE_MAX_VALUE)))
            .andExpect(jsonPath("$.[*].selectList").value(hasItem(DEFAULT_SELECT_LIST)))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)))
            .andExpect(jsonPath("$.[*].replyPattern").value(hasItem(DEFAULT_REPLY_PATTERN)))
            .andExpect(jsonPath("$.[*].multiple").value(hasItem(DEFAULT_MULTIPLE.booleanValue())))
            .andExpect(jsonPath("$.[*].placeHolder").value(hasItem(DEFAULT_PLACE_HOLDER)))
            .andExpect(jsonPath("$.[*].replyRequired").value(hasItem(DEFAULT_REPLY_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].booleanValue").value(hasItem(DEFAULT_BOOLEAN_VALUE.booleanValue())))
            .andExpect(jsonPath("$.[*].withComment").value(hasItem(DEFAULT_WITH_COMMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    void getQeReply() throws Exception {
        // Initialize the database
        qeReplyRepository.saveAndFlush(qeReply);

        // Get the qeReply
        restQeReplyMockMvc
            .perform(get(ENTITY_API_URL_ID, qeReply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeReply.getId().intValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.replyType").value(DEFAULT_REPLY_TYPE.toString()))
            .andExpect(jsonPath("$.dateMinValue").value(DEFAULT_DATE_MIN_VALUE.toString()))
            .andExpect(jsonPath("$.dateMaxValue").value(DEFAULT_DATE_MAX_VALUE.toString()))
            .andExpect(jsonPath("$.integerMinValue").value(DEFAULT_INTEGER_MIN_VALUE))
            .andExpect(jsonPath("$.integerMaxValue").value(DEFAULT_INTEGER_MAX_VALUE))
            .andExpect(jsonPath("$.doubleMinValue").value(DEFAULT_DOUBLE_MIN_VALUE.doubleValue()))
            .andExpect(jsonPath("$.doubleMaxValue").value(DEFAULT_DOUBLE_MAX_VALUE.doubleValue()))
            .andExpect(jsonPath("$.rangeMinValue").value(DEFAULT_RANGE_MIN_VALUE))
            .andExpect(jsonPath("$.rangeMaxValue").value(DEFAULT_RANGE_MAX_VALUE))
            .andExpect(jsonPath("$.selectList").value(DEFAULT_SELECT_LIST))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP))
            .andExpect(jsonPath("$.replyPattern").value(DEFAULT_REPLY_PATTERN))
            .andExpect(jsonPath("$.multiple").value(DEFAULT_MULTIPLE.booleanValue()))
            .andExpect(jsonPath("$.placeHolder").value(DEFAULT_PLACE_HOLDER))
            .andExpect(jsonPath("$.replyRequired").value(DEFAULT_REPLY_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.booleanValue").value(DEFAULT_BOOLEAN_VALUE.booleanValue()))
            .andExpect(jsonPath("$.withComment").value(DEFAULT_WITH_COMMENT.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingQeReply() throws Exception {
        // Get the qeReply
        restQeReplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeReply() throws Exception {
        // Initialize the database
        qeReplyRepository.saveAndFlush(qeReply);

        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();

        // Update the qeReply
        QeReply updatedQeReply = qeReplyRepository.findById(qeReply.getId()).get();
        // Disconnect from session so that the updates on updatedQeReply are not directly saved in db
        em.detach(updatedQeReply);
        updatedQeReply
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .title(UPDATED_TITLE)
            .label(UPDATED_LABEL)
            .replyType(UPDATED_REPLY_TYPE)
            .dateMinValue(UPDATED_DATE_MIN_VALUE)
            .dateMaxValue(UPDATED_DATE_MAX_VALUE)
            .integerMinValue(UPDATED_INTEGER_MIN_VALUE)
            .integerMaxValue(UPDATED_INTEGER_MAX_VALUE)
            .doubleMinValue(UPDATED_DOUBLE_MIN_VALUE)
            .doubleMaxValue(UPDATED_DOUBLE_MAX_VALUE)
            .rangeMinValue(UPDATED_RANGE_MIN_VALUE)
            .rangeMaxValue(UPDATED_RANGE_MAX_VALUE)
            .selectList(UPDATED_SELECT_LIST)
            .step(UPDATED_STEP)
            .replyPattern(UPDATED_REPLY_PATTERN)
            .multiple(UPDATED_MULTIPLE)
            .placeHolder(UPDATED_PLACE_HOLDER)
            .replyRequired(UPDATED_REPLY_REQUIRED)
            .booleanValue(UPDATED_BOOLEAN_VALUE)
            .withComment(UPDATED_WITH_COMMENT)
            .position(UPDATED_POSITION);
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(updatedQeReply);

        restQeReplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeReplyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeReplyDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
        QeReply testQeReply = qeReplyList.get(qeReplyList.size() - 1);
        assertThat(testQeReply.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeReply.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeReply.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQeReply.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQeReply.getReplyType()).isEqualTo(UPDATED_REPLY_TYPE);
        assertThat(testQeReply.getDateMinValue()).isEqualTo(UPDATED_DATE_MIN_VALUE);
        assertThat(testQeReply.getDateMaxValue()).isEqualTo(UPDATED_DATE_MAX_VALUE);
        assertThat(testQeReply.getIntegerMinValue()).isEqualTo(UPDATED_INTEGER_MIN_VALUE);
        assertThat(testQeReply.getIntegerMaxValue()).isEqualTo(UPDATED_INTEGER_MAX_VALUE);
        assertThat(testQeReply.getDoubleMinValue()).isEqualTo(UPDATED_DOUBLE_MIN_VALUE);
        assertThat(testQeReply.getDoubleMaxValue()).isEqualTo(UPDATED_DOUBLE_MAX_VALUE);
        assertThat(testQeReply.getRangeMinValue()).isEqualTo(UPDATED_RANGE_MIN_VALUE);
        assertThat(testQeReply.getRangeMaxValue()).isEqualTo(UPDATED_RANGE_MAX_VALUE);
        assertThat(testQeReply.getSelectList()).isEqualTo(UPDATED_SELECT_LIST);
        assertThat(testQeReply.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testQeReply.getReplyPattern()).isEqualTo(UPDATED_REPLY_PATTERN);
        assertThat(testQeReply.getMultiple()).isEqualTo(UPDATED_MULTIPLE);
        assertThat(testQeReply.getPlaceHolder()).isEqualTo(UPDATED_PLACE_HOLDER);
        assertThat(testQeReply.getReplyRequired()).isEqualTo(UPDATED_REPLY_REQUIRED);
        assertThat(testQeReply.getBooleanValue()).isEqualTo(UPDATED_BOOLEAN_VALUE);
        assertThat(testQeReply.getWithComment()).isEqualTo(UPDATED_WITH_COMMENT);
        assertThat(testQeReply.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingQeReply() throws Exception {
        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();
        qeReply.setId(count.incrementAndGet());

        // Create the QeReply
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeReplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeReplyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeReplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeReply() throws Exception {
        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();
        qeReply.setId(count.incrementAndGet());

        // Create the QeReply
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeReplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeReplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeReply() throws Exception {
        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();
        qeReply.setId(count.incrementAndGet());

        // Create the QeReply
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeReplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeReplyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeReplyWithPatch() throws Exception {
        // Initialize the database
        qeReplyRepository.saveAndFlush(qeReply);

        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();

        // Update the qeReply using partial update
        QeReply partialUpdatedQeReply = new QeReply();
        partialUpdatedQeReply.setId(qeReply.getId());

        partialUpdatedQeReply
            .nodeId(UPDATED_NODE_ID)
            .title(UPDATED_TITLE)
            .step(UPDATED_STEP)
            .multiple(UPDATED_MULTIPLE)
            .withComment(UPDATED_WITH_COMMENT);

        restQeReplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeReply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeReply))
            )
            .andExpect(status().isOk());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
        QeReply testQeReply = qeReplyList.get(qeReplyList.size() - 1);
        assertThat(testQeReply.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeReply.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeReply.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQeReply.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testQeReply.getReplyType()).isEqualTo(DEFAULT_REPLY_TYPE);
        assertThat(testQeReply.getDateMinValue()).isEqualTo(DEFAULT_DATE_MIN_VALUE);
        assertThat(testQeReply.getDateMaxValue()).isEqualTo(DEFAULT_DATE_MAX_VALUE);
        assertThat(testQeReply.getIntegerMinValue()).isEqualTo(DEFAULT_INTEGER_MIN_VALUE);
        assertThat(testQeReply.getIntegerMaxValue()).isEqualTo(DEFAULT_INTEGER_MAX_VALUE);
        assertThat(testQeReply.getDoubleMinValue()).isEqualTo(DEFAULT_DOUBLE_MIN_VALUE);
        assertThat(testQeReply.getDoubleMaxValue()).isEqualTo(DEFAULT_DOUBLE_MAX_VALUE);
        assertThat(testQeReply.getRangeMinValue()).isEqualTo(DEFAULT_RANGE_MIN_VALUE);
        assertThat(testQeReply.getRangeMaxValue()).isEqualTo(DEFAULT_RANGE_MAX_VALUE);
        assertThat(testQeReply.getSelectList()).isEqualTo(DEFAULT_SELECT_LIST);
        assertThat(testQeReply.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testQeReply.getReplyPattern()).isEqualTo(DEFAULT_REPLY_PATTERN);
        assertThat(testQeReply.getMultiple()).isEqualTo(UPDATED_MULTIPLE);
        assertThat(testQeReply.getPlaceHolder()).isEqualTo(DEFAULT_PLACE_HOLDER);
        assertThat(testQeReply.getReplyRequired()).isEqualTo(DEFAULT_REPLY_REQUIRED);
        assertThat(testQeReply.getBooleanValue()).isEqualTo(DEFAULT_BOOLEAN_VALUE);
        assertThat(testQeReply.getWithComment()).isEqualTo(UPDATED_WITH_COMMENT);
        assertThat(testQeReply.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateQeReplyWithPatch() throws Exception {
        // Initialize the database
        qeReplyRepository.saveAndFlush(qeReply);

        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();

        // Update the qeReply using partial update
        QeReply partialUpdatedQeReply = new QeReply();
        partialUpdatedQeReply.setId(qeReply.getId());

        partialUpdatedQeReply
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .title(UPDATED_TITLE)
            .label(UPDATED_LABEL)
            .replyType(UPDATED_REPLY_TYPE)
            .dateMinValue(UPDATED_DATE_MIN_VALUE)
            .dateMaxValue(UPDATED_DATE_MAX_VALUE)
            .integerMinValue(UPDATED_INTEGER_MIN_VALUE)
            .integerMaxValue(UPDATED_INTEGER_MAX_VALUE)
            .doubleMinValue(UPDATED_DOUBLE_MIN_VALUE)
            .doubleMaxValue(UPDATED_DOUBLE_MAX_VALUE)
            .rangeMinValue(UPDATED_RANGE_MIN_VALUE)
            .rangeMaxValue(UPDATED_RANGE_MAX_VALUE)
            .selectList(UPDATED_SELECT_LIST)
            .step(UPDATED_STEP)
            .replyPattern(UPDATED_REPLY_PATTERN)
            .multiple(UPDATED_MULTIPLE)
            .placeHolder(UPDATED_PLACE_HOLDER)
            .replyRequired(UPDATED_REPLY_REQUIRED)
            .booleanValue(UPDATED_BOOLEAN_VALUE)
            .withComment(UPDATED_WITH_COMMENT)
            .position(UPDATED_POSITION);

        restQeReplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeReply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeReply))
            )
            .andExpect(status().isOk());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
        QeReply testQeReply = qeReplyList.get(qeReplyList.size() - 1);
        assertThat(testQeReply.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeReply.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeReply.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQeReply.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQeReply.getReplyType()).isEqualTo(UPDATED_REPLY_TYPE);
        assertThat(testQeReply.getDateMinValue()).isEqualTo(UPDATED_DATE_MIN_VALUE);
        assertThat(testQeReply.getDateMaxValue()).isEqualTo(UPDATED_DATE_MAX_VALUE);
        assertThat(testQeReply.getIntegerMinValue()).isEqualTo(UPDATED_INTEGER_MIN_VALUE);
        assertThat(testQeReply.getIntegerMaxValue()).isEqualTo(UPDATED_INTEGER_MAX_VALUE);
        assertThat(testQeReply.getDoubleMinValue()).isEqualTo(UPDATED_DOUBLE_MIN_VALUE);
        assertThat(testQeReply.getDoubleMaxValue()).isEqualTo(UPDATED_DOUBLE_MAX_VALUE);
        assertThat(testQeReply.getRangeMinValue()).isEqualTo(UPDATED_RANGE_MIN_VALUE);
        assertThat(testQeReply.getRangeMaxValue()).isEqualTo(UPDATED_RANGE_MAX_VALUE);
        assertThat(testQeReply.getSelectList()).isEqualTo(UPDATED_SELECT_LIST);
        assertThat(testQeReply.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testQeReply.getReplyPattern()).isEqualTo(UPDATED_REPLY_PATTERN);
        assertThat(testQeReply.getMultiple()).isEqualTo(UPDATED_MULTIPLE);
        assertThat(testQeReply.getPlaceHolder()).isEqualTo(UPDATED_PLACE_HOLDER);
        assertThat(testQeReply.getReplyRequired()).isEqualTo(UPDATED_REPLY_REQUIRED);
        assertThat(testQeReply.getBooleanValue()).isEqualTo(UPDATED_BOOLEAN_VALUE);
        assertThat(testQeReply.getWithComment()).isEqualTo(UPDATED_WITH_COMMENT);
        assertThat(testQeReply.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingQeReply() throws Exception {
        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();
        qeReply.setId(count.incrementAndGet());

        // Create the QeReply
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeReplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeReplyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeReplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeReply() throws Exception {
        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();
        qeReply.setId(count.incrementAndGet());

        // Create the QeReply
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeReplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeReplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeReply() throws Exception {
        int databaseSizeBeforeUpdate = qeReplyRepository.findAll().size();
        qeReply.setId(count.incrementAndGet());

        // Create the QeReply
        QeReplyDTO qeReplyDTO = qeReplyMapper.toDto(qeReply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeReplyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qeReplyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeReply in the database
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeReply() throws Exception {
        // Initialize the database
        qeReplyRepository.saveAndFlush(qeReply);

        int databaseSizeBeforeDelete = qeReplyRepository.findAll().size();

        // Delete the qeReply
        restQeReplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeReply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeReply> qeReplyList = qeReplyRepository.findAll();
        assertThat(qeReplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
