package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeCheckBox;
import it.tylconsulting.vega.repository.QeCheckBoxRepository;
import it.tylconsulting.vega.service.dto.QeCheckBoxDTO;
import it.tylconsulting.vega.service.mapper.QeCheckBoxMapper;
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
 * Integration tests for the {@link QeCheckBoxResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeCheckBoxResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_BOXVALUE = "AAAAAAAAAA";
    private static final String UPDATED_BOXVALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/qe-check-boxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeCheckBoxRepository qeCheckBoxRepository;

    @Autowired
    private QeCheckBoxMapper qeCheckBoxMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeCheckBoxMockMvc;

    private QeCheckBox qeCheckBox;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeCheckBox createEntity(EntityManager em) {
        QeCheckBox qeCheckBox = new QeCheckBox()
            .label(DEFAULT_LABEL)
            .boxvalue(DEFAULT_BOXVALUE)
            .checked(DEFAULT_CHECKED)
            .position(DEFAULT_POSITION);
        return qeCheckBox;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeCheckBox createUpdatedEntity(EntityManager em) {
        QeCheckBox qeCheckBox = new QeCheckBox()
            .label(UPDATED_LABEL)
            .boxvalue(UPDATED_BOXVALUE)
            .checked(UPDATED_CHECKED)
            .position(UPDATED_POSITION);
        return qeCheckBox;
    }

    @BeforeEach
    public void initTest() {
        qeCheckBox = createEntity(em);
    }

    @Test
    @Transactional
    void createQeCheckBox() throws Exception {
        int databaseSizeBeforeCreate = qeCheckBoxRepository.findAll().size();
        // Create the QeCheckBox
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);
        restQeCheckBoxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO)))
            .andExpect(status().isCreated());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeCreate + 1);
        QeCheckBox testQeCheckBox = qeCheckBoxList.get(qeCheckBoxList.size() - 1);
        assertThat(testQeCheckBox.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testQeCheckBox.getBoxvalue()).isEqualTo(DEFAULT_BOXVALUE);
        assertThat(testQeCheckBox.getChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testQeCheckBox.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createQeCheckBoxWithExistingId() throws Exception {
        // Create the QeCheckBox with an existing ID
        qeCheckBox.setId(1L);
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);

        int databaseSizeBeforeCreate = qeCheckBoxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeCheckBoxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeCheckBoxes() throws Exception {
        // Initialize the database
        qeCheckBoxRepository.saveAndFlush(qeCheckBox);

        // Get all the qeCheckBoxList
        restQeCheckBoxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeCheckBox.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].boxvalue").value(hasItem(DEFAULT_BOXVALUE)))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    void getQeCheckBox() throws Exception {
        // Initialize the database
        qeCheckBoxRepository.saveAndFlush(qeCheckBox);

        // Get the qeCheckBox
        restQeCheckBoxMockMvc
            .perform(get(ENTITY_API_URL_ID, qeCheckBox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeCheckBox.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.boxvalue").value(DEFAULT_BOXVALUE))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingQeCheckBox() throws Exception {
        // Get the qeCheckBox
        restQeCheckBoxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeCheckBox() throws Exception {
        // Initialize the database
        qeCheckBoxRepository.saveAndFlush(qeCheckBox);

        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();

        // Update the qeCheckBox
        QeCheckBox updatedQeCheckBox = qeCheckBoxRepository.findById(qeCheckBox.getId()).get();
        // Disconnect from session so that the updates on updatedQeCheckBox are not directly saved in db
        em.detach(updatedQeCheckBox);
        updatedQeCheckBox.label(UPDATED_LABEL).boxvalue(UPDATED_BOXVALUE).checked(UPDATED_CHECKED).position(UPDATED_POSITION);
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(updatedQeCheckBox);

        restQeCheckBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeCheckBoxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
        QeCheckBox testQeCheckBox = qeCheckBoxList.get(qeCheckBoxList.size() - 1);
        assertThat(testQeCheckBox.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQeCheckBox.getBoxvalue()).isEqualTo(UPDATED_BOXVALUE);
        assertThat(testQeCheckBox.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testQeCheckBox.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingQeCheckBox() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();
        qeCheckBox.setId(count.incrementAndGet());

        // Create the QeCheckBox
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeCheckBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeCheckBoxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeCheckBox() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();
        qeCheckBox.setId(count.incrementAndGet());

        // Create the QeCheckBox
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeCheckBox() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();
        qeCheckBox.setId(count.incrementAndGet());

        // Create the QeCheckBox
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckBoxMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeCheckBoxWithPatch() throws Exception {
        // Initialize the database
        qeCheckBoxRepository.saveAndFlush(qeCheckBox);

        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();

        // Update the qeCheckBox using partial update
        QeCheckBox partialUpdatedQeCheckBox = new QeCheckBox();
        partialUpdatedQeCheckBox.setId(qeCheckBox.getId());

        partialUpdatedQeCheckBox.checked(UPDATED_CHECKED).position(UPDATED_POSITION);

        restQeCheckBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeCheckBox.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeCheckBox))
            )
            .andExpect(status().isOk());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
        QeCheckBox testQeCheckBox = qeCheckBoxList.get(qeCheckBoxList.size() - 1);
        assertThat(testQeCheckBox.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testQeCheckBox.getBoxvalue()).isEqualTo(DEFAULT_BOXVALUE);
        assertThat(testQeCheckBox.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testQeCheckBox.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateQeCheckBoxWithPatch() throws Exception {
        // Initialize the database
        qeCheckBoxRepository.saveAndFlush(qeCheckBox);

        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();

        // Update the qeCheckBox using partial update
        QeCheckBox partialUpdatedQeCheckBox = new QeCheckBox();
        partialUpdatedQeCheckBox.setId(qeCheckBox.getId());

        partialUpdatedQeCheckBox.label(UPDATED_LABEL).boxvalue(UPDATED_BOXVALUE).checked(UPDATED_CHECKED).position(UPDATED_POSITION);

        restQeCheckBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeCheckBox.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeCheckBox))
            )
            .andExpect(status().isOk());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
        QeCheckBox testQeCheckBox = qeCheckBoxList.get(qeCheckBoxList.size() - 1);
        assertThat(testQeCheckBox.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQeCheckBox.getBoxvalue()).isEqualTo(UPDATED_BOXVALUE);
        assertThat(testQeCheckBox.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testQeCheckBox.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingQeCheckBox() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();
        qeCheckBox.setId(count.incrementAndGet());

        // Create the QeCheckBox
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeCheckBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeCheckBoxDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeCheckBox() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();
        qeCheckBox.setId(count.incrementAndGet());

        // Create the QeCheckBox
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeCheckBox() throws Exception {
        int databaseSizeBeforeUpdate = qeCheckBoxRepository.findAll().size();
        qeCheckBox.setId(count.incrementAndGet());

        // Create the QeCheckBox
        QeCheckBoxDTO qeCheckBoxDTO = qeCheckBoxMapper.toDto(qeCheckBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeCheckBoxMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qeCheckBoxDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeCheckBox in the database
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeCheckBox() throws Exception {
        // Initialize the database
        qeCheckBoxRepository.saveAndFlush(qeCheckBox);

        int databaseSizeBeforeDelete = qeCheckBoxRepository.findAll().size();

        // Delete the qeCheckBox
        restQeCheckBoxMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeCheckBox.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeCheckBox> qeCheckBoxList = qeCheckBoxRepository.findAll();
        assertThat(qeCheckBoxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
