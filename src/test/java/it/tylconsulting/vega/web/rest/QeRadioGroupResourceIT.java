package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeRadioGroup;
import it.tylconsulting.vega.domain.enumeration.Orientation;
import it.tylconsulting.vega.repository.QeRadioGroupRepository;
import it.tylconsulting.vega.service.dto.QeRadioGroupDTO;
import it.tylconsulting.vega.service.mapper.QeRadioGroupMapper;
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
 * Integration tests for the {@link QeRadioGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeRadioGroupResourceIT {

    private static final String DEFAULT_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_RADIOBOX_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RADIOBOX_GROUP_NAME = "BBBBBBBBBB";

    private static final Orientation DEFAULT_ORIENTATION = Orientation.ORIZZONTALE;
    private static final Orientation UPDATED_ORIENTATION = Orientation.VERTICALE;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/qe-radio-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeRadioGroupRepository qeRadioGroupRepository;

    @Autowired
    private QeRadioGroupMapper qeRadioGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeRadioGroupMockMvc;

    private QeRadioGroup qeRadioGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeRadioGroup createEntity(EntityManager em) {
        QeRadioGroup qeRadioGroup = new QeRadioGroup()
            .nodeId(DEFAULT_NODE_ID)
            .text(DEFAULT_TEXT)
            .radioboxGroupName(DEFAULT_RADIOBOX_GROUP_NAME)
            .orientation(DEFAULT_ORIENTATION)
            .position(DEFAULT_POSITION);
        return qeRadioGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeRadioGroup createUpdatedEntity(EntityManager em) {
        QeRadioGroup qeRadioGroup = new QeRadioGroup()
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .radioboxGroupName(UPDATED_RADIOBOX_GROUP_NAME)
            .orientation(UPDATED_ORIENTATION)
            .position(UPDATED_POSITION);
        return qeRadioGroup;
    }

    @BeforeEach
    public void initTest() {
        qeRadioGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createQeRadioGroup() throws Exception {
        int databaseSizeBeforeCreate = qeRadioGroupRepository.findAll().size();
        // Create the QeRadioGroup
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);
        restQeRadioGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeCreate + 1);
        QeRadioGroup testQeRadioGroup = qeRadioGroupList.get(qeRadioGroupList.size() - 1);
        assertThat(testQeRadioGroup.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeRadioGroup.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeRadioGroup.getRadioboxGroupName()).isEqualTo(DEFAULT_RADIOBOX_GROUP_NAME);
        assertThat(testQeRadioGroup.getOrientation()).isEqualTo(DEFAULT_ORIENTATION);
        assertThat(testQeRadioGroup.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createQeRadioGroupWithExistingId() throws Exception {
        // Create the QeRadioGroup with an existing ID
        qeRadioGroup.setId(1L);
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);

        int databaseSizeBeforeCreate = qeRadioGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeRadioGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeRadioGroups() throws Exception {
        // Initialize the database
        qeRadioGroupRepository.saveAndFlush(qeRadioGroup);

        // Get all the qeRadioGroupList
        restQeRadioGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeRadioGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].radioboxGroupName").value(hasItem(DEFAULT_RADIOBOX_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].orientation").value(hasItem(DEFAULT_ORIENTATION.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    void getQeRadioGroup() throws Exception {
        // Initialize the database
        qeRadioGroupRepository.saveAndFlush(qeRadioGroup);

        // Get the qeRadioGroup
        restQeRadioGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, qeRadioGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeRadioGroup.getId().intValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.radioboxGroupName").value(DEFAULT_RADIOBOX_GROUP_NAME))
            .andExpect(jsonPath("$.orientation").value(DEFAULT_ORIENTATION.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingQeRadioGroup() throws Exception {
        // Get the qeRadioGroup
        restQeRadioGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeRadioGroup() throws Exception {
        // Initialize the database
        qeRadioGroupRepository.saveAndFlush(qeRadioGroup);

        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();

        // Update the qeRadioGroup
        QeRadioGroup updatedQeRadioGroup = qeRadioGroupRepository.findById(qeRadioGroup.getId()).get();
        // Disconnect from session so that the updates on updatedQeRadioGroup are not directly saved in db
        em.detach(updatedQeRadioGroup);
        updatedQeRadioGroup
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .radioboxGroupName(UPDATED_RADIOBOX_GROUP_NAME)
            .orientation(UPDATED_ORIENTATION)
            .position(UPDATED_POSITION);
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(updatedQeRadioGroup);

        restQeRadioGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeRadioGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
        QeRadioGroup testQeRadioGroup = qeRadioGroupList.get(qeRadioGroupList.size() - 1);
        assertThat(testQeRadioGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeRadioGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeRadioGroup.getRadioboxGroupName()).isEqualTo(UPDATED_RADIOBOX_GROUP_NAME);
        assertThat(testQeRadioGroup.getOrientation()).isEqualTo(UPDATED_ORIENTATION);
        assertThat(testQeRadioGroup.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingQeRadioGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();
        qeRadioGroup.setId(count.incrementAndGet());

        // Create the QeRadioGroup
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeRadioGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeRadioGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeRadioGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();
        qeRadioGroup.setId(count.incrementAndGet());

        // Create the QeRadioGroup
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeRadioGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();
        qeRadioGroup.setId(count.incrementAndGet());

        // Create the QeRadioGroup
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioGroupMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeRadioGroupWithPatch() throws Exception {
        // Initialize the database
        qeRadioGroupRepository.saveAndFlush(qeRadioGroup);

        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();

        // Update the qeRadioGroup using partial update
        QeRadioGroup partialUpdatedQeRadioGroup = new QeRadioGroup();
        partialUpdatedQeRadioGroup.setId(qeRadioGroup.getId());

        partialUpdatedQeRadioGroup
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .radioboxGroupName(UPDATED_RADIOBOX_GROUP_NAME)
            .orientation(UPDATED_ORIENTATION);

        restQeRadioGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeRadioGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeRadioGroup))
            )
            .andExpect(status().isOk());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
        QeRadioGroup testQeRadioGroup = qeRadioGroupList.get(qeRadioGroupList.size() - 1);
        assertThat(testQeRadioGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeRadioGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeRadioGroup.getRadioboxGroupName()).isEqualTo(UPDATED_RADIOBOX_GROUP_NAME);
        assertThat(testQeRadioGroup.getOrientation()).isEqualTo(UPDATED_ORIENTATION);
        assertThat(testQeRadioGroup.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateQeRadioGroupWithPatch() throws Exception {
        // Initialize the database
        qeRadioGroupRepository.saveAndFlush(qeRadioGroup);

        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();

        // Update the qeRadioGroup using partial update
        QeRadioGroup partialUpdatedQeRadioGroup = new QeRadioGroup();
        partialUpdatedQeRadioGroup.setId(qeRadioGroup.getId());

        partialUpdatedQeRadioGroup
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .radioboxGroupName(UPDATED_RADIOBOX_GROUP_NAME)
            .orientation(UPDATED_ORIENTATION)
            .position(UPDATED_POSITION);

        restQeRadioGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeRadioGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeRadioGroup))
            )
            .andExpect(status().isOk());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
        QeRadioGroup testQeRadioGroup = qeRadioGroupList.get(qeRadioGroupList.size() - 1);
        assertThat(testQeRadioGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeRadioGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeRadioGroup.getRadioboxGroupName()).isEqualTo(UPDATED_RADIOBOX_GROUP_NAME);
        assertThat(testQeRadioGroup.getOrientation()).isEqualTo(UPDATED_ORIENTATION);
        assertThat(testQeRadioGroup.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingQeRadioGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();
        qeRadioGroup.setId(count.incrementAndGet());

        // Create the QeRadioGroup
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeRadioGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeRadioGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeRadioGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();
        qeRadioGroup.setId(count.incrementAndGet());

        // Create the QeRadioGroup
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeRadioGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioGroupRepository.findAll().size();
        qeRadioGroup.setId(count.incrementAndGet());

        // Create the QeRadioGroup
        QeRadioGroupDTO qeRadioGroupDTO = qeRadioGroupMapper.toDto(qeRadioGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeRadioGroup in the database
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeRadioGroup() throws Exception {
        // Initialize the database
        qeRadioGroupRepository.saveAndFlush(qeRadioGroup);

        int databaseSizeBeforeDelete = qeRadioGroupRepository.findAll().size();

        // Delete the qeRadioGroup
        restQeRadioGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeRadioGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeRadioGroup> qeRadioGroupList = qeRadioGroupRepository.findAll();
        assertThat(qeRadioGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
