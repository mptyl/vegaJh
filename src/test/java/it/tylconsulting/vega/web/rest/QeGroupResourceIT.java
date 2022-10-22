package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeGroup;
import it.tylconsulting.vega.repository.QeGroupRepository;
import it.tylconsulting.vega.service.dto.QeGroupDTO;
import it.tylconsulting.vega.service.mapper.QeGroupMapper;
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
 * Integration tests for the {@link QeGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeGroupResourceIT {

    private static final String DEFAULT_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RANDOM = false;
    private static final Boolean UPDATED_RANDOM = true;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/qe-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeGroupRepository qeGroupRepository;

    @Autowired
    private QeGroupMapper qeGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeGroupMockMvc;

    private QeGroup qeGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeGroup createEntity(EntityManager em) {
        QeGroup qeGroup = new QeGroup().nodeId(DEFAULT_NODE_ID).text(DEFAULT_TEXT).random(DEFAULT_RANDOM).position(DEFAULT_POSITION);
        return qeGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeGroup createUpdatedEntity(EntityManager em) {
        QeGroup qeGroup = new QeGroup().nodeId(UPDATED_NODE_ID).text(UPDATED_TEXT).random(UPDATED_RANDOM).position(UPDATED_POSITION);
        return qeGroup;
    }

    @BeforeEach
    public void initTest() {
        qeGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createQeGroup() throws Exception {
        int databaseSizeBeforeCreate = qeGroupRepository.findAll().size();
        // Create the QeGroup
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);
        restQeGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeCreate + 1);
        QeGroup testQeGroup = qeGroupList.get(qeGroupList.size() - 1);
        assertThat(testQeGroup.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeGroup.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeGroup.getRandom()).isEqualTo(DEFAULT_RANDOM);
        assertThat(testQeGroup.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createQeGroupWithExistingId() throws Exception {
        // Create the QeGroup with an existing ID
        qeGroup.setId(1L);
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);

        int databaseSizeBeforeCreate = qeGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeGroups() throws Exception {
        // Initialize the database
        qeGroupRepository.saveAndFlush(qeGroup);

        // Get all the qeGroupList
        restQeGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].random").value(hasItem(DEFAULT_RANDOM.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    void getQeGroup() throws Exception {
        // Initialize the database
        qeGroupRepository.saveAndFlush(qeGroup);

        // Get the qeGroup
        restQeGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, qeGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeGroup.getId().intValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.random").value(DEFAULT_RANDOM.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingQeGroup() throws Exception {
        // Get the qeGroup
        restQeGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeGroup() throws Exception {
        // Initialize the database
        qeGroupRepository.saveAndFlush(qeGroup);

        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();

        // Update the qeGroup
        QeGroup updatedQeGroup = qeGroupRepository.findById(qeGroup.getId()).get();
        // Disconnect from session so that the updates on updatedQeGroup are not directly saved in db
        em.detach(updatedQeGroup);
        updatedQeGroup.nodeId(UPDATED_NODE_ID).text(UPDATED_TEXT).random(UPDATED_RANDOM).position(UPDATED_POSITION);
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(updatedQeGroup);

        restQeGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
        QeGroup testQeGroup = qeGroupList.get(qeGroupList.size() - 1);
        assertThat(testQeGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeGroup.getRandom()).isEqualTo(UPDATED_RANDOM);
        assertThat(testQeGroup.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingQeGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();
        qeGroup.setId(count.incrementAndGet());

        // Create the QeGroup
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();
        qeGroup.setId(count.incrementAndGet());

        // Create the QeGroup
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();
        qeGroup.setId(count.incrementAndGet());

        // Create the QeGroup
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeGroupWithPatch() throws Exception {
        // Initialize the database
        qeGroupRepository.saveAndFlush(qeGroup);

        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();

        // Update the qeGroup using partial update
        QeGroup partialUpdatedQeGroup = new QeGroup();
        partialUpdatedQeGroup.setId(qeGroup.getId());

        partialUpdatedQeGroup.text(UPDATED_TEXT);

        restQeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeGroup))
            )
            .andExpect(status().isOk());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
        QeGroup testQeGroup = qeGroupList.get(qeGroupList.size() - 1);
        assertThat(testQeGroup.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeGroup.getRandom()).isEqualTo(DEFAULT_RANDOM);
        assertThat(testQeGroup.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateQeGroupWithPatch() throws Exception {
        // Initialize the database
        qeGroupRepository.saveAndFlush(qeGroup);

        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();

        // Update the qeGroup using partial update
        QeGroup partialUpdatedQeGroup = new QeGroup();
        partialUpdatedQeGroup.setId(qeGroup.getId());

        partialUpdatedQeGroup.nodeId(UPDATED_NODE_ID).text(UPDATED_TEXT).random(UPDATED_RANDOM).position(UPDATED_POSITION);

        restQeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeGroup))
            )
            .andExpect(status().isOk());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
        QeGroup testQeGroup = qeGroupList.get(qeGroupList.size() - 1);
        assertThat(testQeGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeGroup.getRandom()).isEqualTo(UPDATED_RANDOM);
        assertThat(testQeGroup.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingQeGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();
        qeGroup.setId(count.incrementAndGet());

        // Create the QeGroup
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();
        qeGroup.setId(count.incrementAndGet());

        // Create the QeGroup
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeGroupRepository.findAll().size();
        qeGroup.setId(count.incrementAndGet());

        // Create the QeGroup
        QeGroupDTO qeGroupDTO = qeGroupMapper.toDto(qeGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qeGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeGroup in the database
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeGroup() throws Exception {
        // Initialize the database
        qeGroupRepository.saveAndFlush(qeGroup);

        int databaseSizeBeforeDelete = qeGroupRepository.findAll().size();

        // Delete the qeGroup
        restQeGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeGroup> qeGroupList = qeGroupRepository.findAll();
        assertThat(qeGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
