package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeJumpExpression;
import it.tylconsulting.vega.repository.QeJumpExpressionRepository;
import it.tylconsulting.vega.service.dto.QeJumpExpressionDTO;
import it.tylconsulting.vega.service.mapper.QeJumpExpressionMapper;
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
 * Integration tests for the {@link QeJumpExpressionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeJumpExpressionResourceIT {

    private static final String DEFAULT_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_EXPRESSION = "BBBBBBBBBB";

    private static final String DEFAULT_JUMP_TO = "AAAAAAAAAA";
    private static final String UPDATED_JUMP_TO = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/qe-jump-expressions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeJumpExpressionRepository qeJumpExpressionRepository;

    @Autowired
    private QeJumpExpressionMapper qeJumpExpressionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeJumpExpressionMockMvc;

    private QeJumpExpression qeJumpExpression;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeJumpExpression createEntity(EntityManager em) {
        QeJumpExpression qeJumpExpression = new QeJumpExpression()
            .nodeId(DEFAULT_NODE_ID)
            .text(DEFAULT_TEXT)
            .expression(DEFAULT_EXPRESSION)
            .jumpTo(DEFAULT_JUMP_TO)
            .position(DEFAULT_POSITION);
        return qeJumpExpression;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeJumpExpression createUpdatedEntity(EntityManager em) {
        QeJumpExpression qeJumpExpression = new QeJumpExpression()
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .expression(UPDATED_EXPRESSION)
            .jumpTo(UPDATED_JUMP_TO)
            .position(UPDATED_POSITION);
        return qeJumpExpression;
    }

    @BeforeEach
    public void initTest() {
        qeJumpExpression = createEntity(em);
    }

    @Test
    @Transactional
    void createQeJumpExpression() throws Exception {
        int databaseSizeBeforeCreate = qeJumpExpressionRepository.findAll().size();
        // Create the QeJumpExpression
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);
        restQeJumpExpressionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeCreate + 1);
        QeJumpExpression testQeJumpExpression = qeJumpExpressionList.get(qeJumpExpressionList.size() - 1);
        assertThat(testQeJumpExpression.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeJumpExpression.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeJumpExpression.getExpression()).isEqualTo(DEFAULT_EXPRESSION);
        assertThat(testQeJumpExpression.getJumpTo()).isEqualTo(DEFAULT_JUMP_TO);
        assertThat(testQeJumpExpression.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createQeJumpExpressionWithExistingId() throws Exception {
        // Create the QeJumpExpression with an existing ID
        qeJumpExpression.setId(1L);
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);

        int databaseSizeBeforeCreate = qeJumpExpressionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeJumpExpressionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeJumpExpressions() throws Exception {
        // Initialize the database
        qeJumpExpressionRepository.saveAndFlush(qeJumpExpression);

        // Get all the qeJumpExpressionList
        restQeJumpExpressionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeJumpExpression.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].expression").value(hasItem(DEFAULT_EXPRESSION)))
            .andExpect(jsonPath("$.[*].jumpTo").value(hasItem(DEFAULT_JUMP_TO)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    void getQeJumpExpression() throws Exception {
        // Initialize the database
        qeJumpExpressionRepository.saveAndFlush(qeJumpExpression);

        // Get the qeJumpExpression
        restQeJumpExpressionMockMvc
            .perform(get(ENTITY_API_URL_ID, qeJumpExpression.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeJumpExpression.getId().intValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.expression").value(DEFAULT_EXPRESSION))
            .andExpect(jsonPath("$.jumpTo").value(DEFAULT_JUMP_TO))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingQeJumpExpression() throws Exception {
        // Get the qeJumpExpression
        restQeJumpExpressionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeJumpExpression() throws Exception {
        // Initialize the database
        qeJumpExpressionRepository.saveAndFlush(qeJumpExpression);

        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();

        // Update the qeJumpExpression
        QeJumpExpression updatedQeJumpExpression = qeJumpExpressionRepository.findById(qeJumpExpression.getId()).get();
        // Disconnect from session so that the updates on updatedQeJumpExpression are not directly saved in db
        em.detach(updatedQeJumpExpression);
        updatedQeJumpExpression
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .expression(UPDATED_EXPRESSION)
            .jumpTo(UPDATED_JUMP_TO)
            .position(UPDATED_POSITION);
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(updatedQeJumpExpression);

        restQeJumpExpressionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeJumpExpressionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
        QeJumpExpression testQeJumpExpression = qeJumpExpressionList.get(qeJumpExpressionList.size() - 1);
        assertThat(testQeJumpExpression.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeJumpExpression.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeJumpExpression.getExpression()).isEqualTo(UPDATED_EXPRESSION);
        assertThat(testQeJumpExpression.getJumpTo()).isEqualTo(UPDATED_JUMP_TO);
        assertThat(testQeJumpExpression.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingQeJumpExpression() throws Exception {
        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();
        qeJumpExpression.setId(count.incrementAndGet());

        // Create the QeJumpExpression
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeJumpExpressionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeJumpExpressionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeJumpExpression() throws Exception {
        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();
        qeJumpExpression.setId(count.incrementAndGet());

        // Create the QeJumpExpression
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeJumpExpressionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeJumpExpression() throws Exception {
        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();
        qeJumpExpression.setId(count.incrementAndGet());

        // Create the QeJumpExpression
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeJumpExpressionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeJumpExpressionWithPatch() throws Exception {
        // Initialize the database
        qeJumpExpressionRepository.saveAndFlush(qeJumpExpression);

        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();

        // Update the qeJumpExpression using partial update
        QeJumpExpression partialUpdatedQeJumpExpression = new QeJumpExpression();
        partialUpdatedQeJumpExpression.setId(qeJumpExpression.getId());

        partialUpdatedQeJumpExpression.expression(UPDATED_EXPRESSION).position(UPDATED_POSITION);

        restQeJumpExpressionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeJumpExpression.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeJumpExpression))
            )
            .andExpect(status().isOk());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
        QeJumpExpression testQeJumpExpression = qeJumpExpressionList.get(qeJumpExpressionList.size() - 1);
        assertThat(testQeJumpExpression.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeJumpExpression.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeJumpExpression.getExpression()).isEqualTo(UPDATED_EXPRESSION);
        assertThat(testQeJumpExpression.getJumpTo()).isEqualTo(DEFAULT_JUMP_TO);
        assertThat(testQeJumpExpression.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateQeJumpExpressionWithPatch() throws Exception {
        // Initialize the database
        qeJumpExpressionRepository.saveAndFlush(qeJumpExpression);

        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();

        // Update the qeJumpExpression using partial update
        QeJumpExpression partialUpdatedQeJumpExpression = new QeJumpExpression();
        partialUpdatedQeJumpExpression.setId(qeJumpExpression.getId());

        partialUpdatedQeJumpExpression
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .expression(UPDATED_EXPRESSION)
            .jumpTo(UPDATED_JUMP_TO)
            .position(UPDATED_POSITION);

        restQeJumpExpressionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeJumpExpression.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeJumpExpression))
            )
            .andExpect(status().isOk());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
        QeJumpExpression testQeJumpExpression = qeJumpExpressionList.get(qeJumpExpressionList.size() - 1);
        assertThat(testQeJumpExpression.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeJumpExpression.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeJumpExpression.getExpression()).isEqualTo(UPDATED_EXPRESSION);
        assertThat(testQeJumpExpression.getJumpTo()).isEqualTo(UPDATED_JUMP_TO);
        assertThat(testQeJumpExpression.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingQeJumpExpression() throws Exception {
        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();
        qeJumpExpression.setId(count.incrementAndGet());

        // Create the QeJumpExpression
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeJumpExpressionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeJumpExpressionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeJumpExpression() throws Exception {
        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();
        qeJumpExpression.setId(count.incrementAndGet());

        // Create the QeJumpExpression
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeJumpExpressionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeJumpExpression() throws Exception {
        int databaseSizeBeforeUpdate = qeJumpExpressionRepository.findAll().size();
        qeJumpExpression.setId(count.incrementAndGet());

        // Create the QeJumpExpression
        QeJumpExpressionDTO qeJumpExpressionDTO = qeJumpExpressionMapper.toDto(qeJumpExpression);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeJumpExpressionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeJumpExpressionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeJumpExpression in the database
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeJumpExpression() throws Exception {
        // Initialize the database
        qeJumpExpressionRepository.saveAndFlush(qeJumpExpression);

        int databaseSizeBeforeDelete = qeJumpExpressionRepository.findAll().size();

        // Delete the qeJumpExpression
        restQeJumpExpressionMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeJumpExpression.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeJumpExpression> qeJumpExpressionList = qeJumpExpressionRepository.findAll();
        assertThat(qeJumpExpressionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
