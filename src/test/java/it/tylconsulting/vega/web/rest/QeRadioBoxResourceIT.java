package it.tylconsulting.vega.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.tylconsulting.vega.IntegrationTest;
import it.tylconsulting.vega.domain.QeRadioBox;
import it.tylconsulting.vega.repository.QeRadioBoxRepository;
import it.tylconsulting.vega.service.dto.QeRadioBoxDTO;
import it.tylconsulting.vega.service.mapper.QeRadioBoxMapper;
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
 * Integration tests for the {@link QeRadioBoxResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QeRadioBoxResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_BOXVALUE = "AAAAAAAAAA";
    private static final String UPDATED_BOXVALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/qe-radio-boxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QeRadioBoxRepository qeRadioBoxRepository;

    @Autowired
    private QeRadioBoxMapper qeRadioBoxMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQeRadioBoxMockMvc;

    private QeRadioBox qeRadioBox;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeRadioBox createEntity(EntityManager em) {
        QeRadioBox qeRadioBox = new QeRadioBox()
            .label(DEFAULT_LABEL)
            .boxvalue(DEFAULT_BOXVALUE)
            .checked(DEFAULT_CHECKED)
            .position(DEFAULT_POSITION);
        return qeRadioBox;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QeRadioBox createUpdatedEntity(EntityManager em) {
        QeRadioBox qeRadioBox = new QeRadioBox()
            .label(UPDATED_LABEL)
            .boxvalue(UPDATED_BOXVALUE)
            .checked(UPDATED_CHECKED)
            .position(UPDATED_POSITION);
        return qeRadioBox;
    }

    @BeforeEach
    public void initTest() {
        qeRadioBox = createEntity(em);
    }

    @Test
    @Transactional
    void createQeRadioBox() throws Exception {
        int databaseSizeBeforeCreate = qeRadioBoxRepository.findAll().size();
        // Create the QeRadioBox
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);
        restQeRadioBoxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO)))
            .andExpect(status().isCreated());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeCreate + 1);
        QeRadioBox testQeRadioBox = qeRadioBoxList.get(qeRadioBoxList.size() - 1);
        assertThat(testQeRadioBox.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testQeRadioBox.getBoxvalue()).isEqualTo(DEFAULT_BOXVALUE);
        assertThat(testQeRadioBox.getChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testQeRadioBox.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createQeRadioBoxWithExistingId() throws Exception {
        // Create the QeRadioBox with an existing ID
        qeRadioBox.setId(1L);
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);

        int databaseSizeBeforeCreate = qeRadioBoxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQeRadioBoxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQeRadioBoxes() throws Exception {
        // Initialize the database
        qeRadioBoxRepository.saveAndFlush(qeRadioBox);

        // Get all the qeRadioBoxList
        restQeRadioBoxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qeRadioBox.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].boxvalue").value(hasItem(DEFAULT_BOXVALUE)))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    void getQeRadioBox() throws Exception {
        // Initialize the database
        qeRadioBoxRepository.saveAndFlush(qeRadioBox);

        // Get the qeRadioBox
        restQeRadioBoxMockMvc
            .perform(get(ENTITY_API_URL_ID, qeRadioBox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qeRadioBox.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.boxvalue").value(DEFAULT_BOXVALUE))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingQeRadioBox() throws Exception {
        // Get the qeRadioBox
        restQeRadioBoxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQeRadioBox() throws Exception {
        // Initialize the database
        qeRadioBoxRepository.saveAndFlush(qeRadioBox);

        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();

        // Update the qeRadioBox
        QeRadioBox updatedQeRadioBox = qeRadioBoxRepository.findById(qeRadioBox.getId()).get();
        // Disconnect from session so that the updates on updatedQeRadioBox are not directly saved in db
        em.detach(updatedQeRadioBox);
        updatedQeRadioBox.label(UPDATED_LABEL).boxvalue(UPDATED_BOXVALUE).checked(UPDATED_CHECKED).position(UPDATED_POSITION);
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(updatedQeRadioBox);

        restQeRadioBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeRadioBoxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO))
            )
            .andExpect(status().isOk());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
        QeRadioBox testQeRadioBox = qeRadioBoxList.get(qeRadioBoxList.size() - 1);
        assertThat(testQeRadioBox.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQeRadioBox.getBoxvalue()).isEqualTo(UPDATED_BOXVALUE);
        assertThat(testQeRadioBox.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testQeRadioBox.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingQeRadioBox() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();
        qeRadioBox.setId(count.incrementAndGet());

        // Create the QeRadioBox
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeRadioBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qeRadioBoxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQeRadioBox() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();
        qeRadioBox.setId(count.incrementAndGet());

        // Create the QeRadioBox
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQeRadioBox() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();
        qeRadioBox.setId(count.incrementAndGet());

        // Create the QeRadioBox
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioBoxMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQeRadioBoxWithPatch() throws Exception {
        // Initialize the database
        qeRadioBoxRepository.saveAndFlush(qeRadioBox);

        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();

        // Update the qeRadioBox using partial update
        QeRadioBox partialUpdatedQeRadioBox = new QeRadioBox();
        partialUpdatedQeRadioBox.setId(qeRadioBox.getId());

        partialUpdatedQeRadioBox.label(UPDATED_LABEL);

        restQeRadioBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeRadioBox.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeRadioBox))
            )
            .andExpect(status().isOk());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
        QeRadioBox testQeRadioBox = qeRadioBoxList.get(qeRadioBoxList.size() - 1);
        assertThat(testQeRadioBox.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQeRadioBox.getBoxvalue()).isEqualTo(DEFAULT_BOXVALUE);
        assertThat(testQeRadioBox.getChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testQeRadioBox.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateQeRadioBoxWithPatch() throws Exception {
        // Initialize the database
        qeRadioBoxRepository.saveAndFlush(qeRadioBox);

        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();

        // Update the qeRadioBox using partial update
        QeRadioBox partialUpdatedQeRadioBox = new QeRadioBox();
        partialUpdatedQeRadioBox.setId(qeRadioBox.getId());

        partialUpdatedQeRadioBox.label(UPDATED_LABEL).boxvalue(UPDATED_BOXVALUE).checked(UPDATED_CHECKED).position(UPDATED_POSITION);

        restQeRadioBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQeRadioBox.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQeRadioBox))
            )
            .andExpect(status().isOk());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
        QeRadioBox testQeRadioBox = qeRadioBoxList.get(qeRadioBoxList.size() - 1);
        assertThat(testQeRadioBox.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQeRadioBox.getBoxvalue()).isEqualTo(UPDATED_BOXVALUE);
        assertThat(testQeRadioBox.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testQeRadioBox.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingQeRadioBox() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();
        qeRadioBox.setId(count.incrementAndGet());

        // Create the QeRadioBox
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQeRadioBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qeRadioBoxDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQeRadioBox() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();
        qeRadioBox.setId(count.incrementAndGet());

        // Create the QeRadioBox
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQeRadioBox() throws Exception {
        int databaseSizeBeforeUpdate = qeRadioBoxRepository.findAll().size();
        qeRadioBox.setId(count.incrementAndGet());

        // Create the QeRadioBox
        QeRadioBoxDTO qeRadioBoxDTO = qeRadioBoxMapper.toDto(qeRadioBox);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQeRadioBoxMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qeRadioBoxDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QeRadioBox in the database
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQeRadioBox() throws Exception {
        // Initialize the database
        qeRadioBoxRepository.saveAndFlush(qeRadioBox);

        int databaseSizeBeforeDelete = qeRadioBoxRepository.findAll().size();

        // Delete the qeRadioBox
        restQeRadioBoxMockMvc
            .perform(delete(ENTITY_API_URL_ID, qeRadioBox.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QeRadioBox> qeRadioBoxList = qeRadioBoxRepository.findAll();
        assertThat(qeRadioBoxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
