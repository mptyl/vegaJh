package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeCheckGroup;
import it.tylconsulting.vega.domain.enumeration.Orientation;
import it.tylconsulting.vega.repository.QeCheckGroupRepository;
import it.tylconsulting.vega.service.dto.QeCheckGroupDTO;
import it.tylconsulting.vega.service.mapper.QeCheckGroupMapper;
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
 * Integration tests for the {@link QeCheckGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeCheckGroupResourceIT {

    private static final String DEFAULT_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_RADIOBOX_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RADIOBOX_GROUP_NAME = "BBBBBBBBBB";

    private static final Orientation DEFAULT_ORIENTATION = Orientation.ORIZZONTALE;
    private static final Orientation UPDATED_ORIENTATION = Orientation.VERTICALE;

    private static final Integer DEFAULT_POSITIO = 1;
    private static final Integer UPDATED_POSITIO = 2;

    private static final String ENTITY_API_URL = "/api/qe-check-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeCheckGroupRepository qeCheckGroupRepository;

    @Autowired
    private QeCheckGroupMapper qeCheckGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeCheckGroupMockMvc;

    private QeCheckGroup qeCheckGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeCheckGroup createEntity(EntityManager em) {
        QeCheckGroup qeCheckGroup = new QeCheckGroup()
            .nodeId(DEFAULT_NODE_ID)
            .text(DEFAULT_TEXT)
            .radioboxGroupName(DEFAULT_RADIOBOX_GROUP_NAME)
            .orientation(DEFAULT_ORIENTATION)
            .positio(DEFAULT_POSITIO);
        return qeCheckGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeCheckGroup createUpdatedEntity(EntityManager em) {
        QeCheckGroup qeCheckGroup = new QeCheckGroup()
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .radioboxGroupName(UPDATED_RADIOBOX_GROUP_NAME)
            .orientation(UPDATED_ORIENTATION)
            .positio(UPDATED_POSITIO);
        return qeCheckGroup;
    }

    @BeforeEach
    public void initTest() {
        qeCheckGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createQeCheckGroup() throws Exception {
        int databaseSizeBeforeCreate = qeCheckGroupRepository.findAll().size();
        // Create the QeCheckGroup
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);
        restQeCheckGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeCreate + 1);
        QeCheckGroup testQeCheckGroup = qeCheckGroupList.get(qeCheckGroupList.size() - 1);
        assertThat(testQeCheckGroup.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testQeCheckGroup.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testQeCheckGroup.getRadioboxGroupName()).isEqualTo(DEFAULT_RADIOBOX_GROUP_NAME);
        assertThat(testQeCheckGroup.getOrientation()).isEqualTo(DEFAULT_ORIENTATION);
        assertThat(testQeCheckGroup.getPositio()).isEqualTo(DEFAULT_POSITIO);
    }

    @Test
    @Transactional
    void createQeCheckGroupWithExistingId() throws Exception {
        // Create the QeCheckGroup with an existing ID
        qeCheckGroup.setId(1L);
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);

        int databaseSizeBeforeCreate = qeCheckGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeCheckGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeCheckGroups() throws Exception {
        // Initialize the database
        qeCheckGroupRepository.saveAndFlush(qeCheckGroup);

        // Get all the qeCheckGroupList
        restQeCheckGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeCheckGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].radioboxGroupName").value(hasItem(DEFAULT_RADIOBOX_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].orientation").value(hasItem(DEFAULT_ORIENTATION.toString())))
            .andExpect(jsonPath("$.[*].positio").value(hasItem(DEFAULT_POSITIO)));
    }

    @Test
    @Transactional
    void getQeCheckGroup() throws Exception {
        // Initialize the database
        qeCheckGroupRepository.saveAndFlush(qeCheckGroup);

        // Get the qeCheckGroup
        restQeCheckGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, qeCheckGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeCheckGroup.getId().intValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.radioboxGroupName").value(DEFAULT_RADIOBOX_GROUP_NAME))
            .andExpect(jsonPath("$.orientation").value(DEFAULT_ORIENTATION.toString()))
            .andExpect(jsonPath("$.positio").value(DEFAULT_POSITIO));
    }

    @Test
    @Transactional
    void getNonExistingQeCheckGroup() throws Exception {
        // Get the qeCheckGroup
        restQeCheckGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeCheckGroup() throws Exception {
        // Initialize the database
        qeCheckGroupRepository.saveAndFlush(qeCheckGroup);

        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();

        // Update the qeCheckGroup
        QeCheckGroup updatedQeCheckGroup = qeCheckGroupRepository.findById(qeCheckGroup.getId()).get();
        // Disconnect from session so that the updates on updatedQeCheckGroup are not directly saved in db
        em.detach(updatedQeCheckGroup);
        updatedQeCheckGroup
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .radioboxGroupName(UPDATED_RADIOBOX_GROUP_NAME)
            .orientation(UPDATED_ORIENTATION)
            .positio(UPDATED_POSITIO);
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(updatedQeCheckGroup);

        restQeCheckGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeCheckGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
        QeCheckGroup testQeCheckGroup = qeCheckGroupList.get(qeCheckGroupList.size() - 1);
        assertThat(testQeCheckGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeCheckGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeCheckGroup.getRadioboxGroupName()).isEqualTo(UPDATED_RADIOBOX_GROUP_NAME);
        assertThat(testQeCheckGroup.getOrientation()).isEqualTo(UPDATED_ORIENTATION);
        assertThat(testQeCheckGroup.getPositio()).isEqualTo(UPDATED_POSITIO);
    }

    @Test
    @Transactional
    void putNonExistingQeCheckGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();
        qeCheckGroup.setId(count.incrementAndGet());

        // Create the QeCheckGroup
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeCheckGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeCheckGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeCheckGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();
        qeCheckGroup.setId(count.incrementAndGet());

        // Create the QeCheckGroup
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeCheckGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();
        qeCheckGroup.setId(count.incrementAndGet());

        // Create the QeCheckGroup
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckGroupMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeCheckGroupWithPatch() throws Exception {
        // Initialize the database
        qeCheckGroupRepository.saveAndFlush(qeCheckGroup);

        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();

        // Update the qeCheckGroup using partial update
        QeCheckGroup partialUpdatedQeCheckGroup = new QeCheckGroup();
        partialUpdatedQeCheckGroup.setId(qeCheckGroup.getId());

        partialUpdatedQeCheckGroup.nodeId(UPDATED_NODE_ID).text(UPDATED_TEXT).orientation(UPDATED_ORIENTATION);

        restQeCheckGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeCheckGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeCheckGroup))
            )
            .andExpect(status().isOk());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
        QeCheckGroup testQeCheckGroup = qeCheckGroupList.get(qeCheckGroupList.size() - 1);
        assertThat(testQeCheckGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeCheckGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeCheckGroup.getRadioboxGroupName()).isEqualTo(DEFAULT_RADIOBOX_GROUP_NAME);
        assertThat(testQeCheckGroup.getOrientation()).isEqualTo(UPDATED_ORIENTATION);
        assertThat(testQeCheckGroup.getPositio()).isEqualTo(DEFAULT_POSITIO);
    }

    @Test
    @Transactional
    void fullUpdateQeCheckGroupWithPatch() throws Exception {
        // Initialize the database
        qeCheckGroupRepository.saveAndFlush(qeCheckGroup);

        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();

        // Update the qeCheckGroup using partial update
        QeCheckGroup partialUpdatedQeCheckGroup = new QeCheckGroup();
        partialUpdatedQeCheckGroup.setId(qeCheckGroup.getId());

        partialUpdatedQeCheckGroup
            .nodeId(UPDATED_NODE_ID)
            .text(UPDATED_TEXT)
            .radioboxGroupName(UPDATED_RADIOBOX_GROUP_NAME)
            .orientation(UPDATED_ORIENTATION)
            .positio(UPDATED_POSITIO);

        restQeCheckGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeCheckGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeCheckGroup))
            )
            .andExpect(status().isOk());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
        QeCheckGroup testQeCheckGroup = qeCheckGroupList.get(qeCheckGroupList.size() - 1);
        assertThat(testQeCheckGroup.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testQeCheckGroup.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testQeCheckGroup.getRadioboxGroupName()).isEqualTo(UPDATED_RADIOBOX_GROUP_NAME);
        assertThat(testQeCheckGroup.getOrientation()).isEqualTo(UPDATED_ORIENTATION);
        assertThat(testQeCheckGroup.getPositio()).isEqualTo(UPDATED_POSITIO);
    }

    @Test
    @Transactional
    void patchNonExistingQeCheckGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();
        qeCheckGroup.setId(count.incrementAndGet());

        // Create the QeCheckGroup
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeCheckGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeCheckGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeCheckGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();
        qeCheckGroup.setId(count.incrementAndGet());

        // Create the QeCheckGroup
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeCheckGroup() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckGroupRepository.findAll().size();
        qeCheckGroup.setId(count.incrementAndGet());

        // Create the QeCheckGroup
        QeCheckGroupDTO qeCheckGroupDTO = qeCheckGroupMapper.toDto(qeCheckGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeCheckGroup in the database
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeCheckGroup() throws Exception {
        // Initialize the database
        qeCheckGroupRepository.saveAndFlush(qeCheckGroup);

        int databaseSizeBeforeDelete = qeCheckGroupRepository.findAll().size();

        // Delete the qeCheckGroup
        restQeCheckGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeCheckGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeCheckGroup> qeCheckGroupList = qeCheckGroupRepository.findAll();
        assertThat(qeCheckGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
