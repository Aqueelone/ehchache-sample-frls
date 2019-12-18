package com.luxiti.frls.web.rest;

import com.luxiti.frls.EhcacheFrlStestExampleApp;
import com.luxiti.frls.domain.SLA;
import com.luxiti.frls.repository.SLARepository;
import com.luxiti.frls.service.SLAService;
import com.luxiti.frls.service.dto.SLADTO;
import com.luxiti.frls.service.mapper.SLAMapper;
import com.luxiti.frls.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.luxiti.frls.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SLAResource} REST controller.
 */
@SpringBootTest(classes = EhcacheFrlStestExampleApp.class)
public class SLAResourceIT {

    private static final Integer DEFAULT_RTS = 1;
    private static final Integer UPDATED_RTS = 2;

    @Autowired
    private SLARepository sLARepository;

    @Autowired
    private SLAMapper sLAMapper;

    @Autowired
    private SLAService sLAService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSLAMockMvc;

    private SLA sLA;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SLAResource sLAResource = new SLAResource(sLAService);
        this.restSLAMockMvc = MockMvcBuilders.standaloneSetup(sLAResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SLA createEntity(EntityManager em) {
        SLA sLA = new SLA()
            .rts(DEFAULT_RTS);
        return sLA;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SLA createUpdatedEntity(EntityManager em) {
        SLA sLA = new SLA()
            .rts(UPDATED_RTS);
        return sLA;
    }

    @BeforeEach
    public void initTest() {
        sLA = createEntity(em);
    }

    @Test
    @Transactional
    public void createSLA() throws Exception {
        int databaseSizeBeforeCreate = sLARepository.findAll().size();

        // Create the SLA
        SLADTO sLADTO = sLAMapper.toDto(sLA);
        restSLAMockMvc.perform(post("/api/slas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sLADTO)))
            .andExpect(status().isCreated());

        // Validate the SLA in the database
        List<SLA> sLAList = sLARepository.findAll();
        assertThat(sLAList).hasSize(databaseSizeBeforeCreate + 1);
        SLA testSLA = sLAList.get(sLAList.size() - 1);
        assertThat(testSLA.getRts()).isEqualTo(DEFAULT_RTS);
    }

    @Test
    @Transactional
    public void createSLAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sLARepository.findAll().size();

        // Create the SLA with an existing ID
        sLA.setId(1L);
        SLADTO sLADTO = sLAMapper.toDto(sLA);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSLAMockMvc.perform(post("/api/slas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sLADTO)))
            .andExpect(status().isBadRequest());

        // Validate the SLA in the database
        List<SLA> sLAList = sLARepository.findAll();
        assertThat(sLAList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSLAS() throws Exception {
        // Initialize the database
        sLARepository.saveAndFlush(sLA);

        // Get all the sLAList
        restSLAMockMvc.perform(get("/api/slas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sLA.getId().intValue())))
            .andExpect(jsonPath("$.[*].rts").value(hasItem(DEFAULT_RTS)));
    }
    
    @Test
    @Transactional
    public void getSLA() throws Exception {
        // Initialize the database
        sLARepository.saveAndFlush(sLA);

        // Get the sLA
        restSLAMockMvc.perform(get("/api/slas/{id}", sLA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sLA.getId().intValue()))
            .andExpect(jsonPath("$.rts").value(DEFAULT_RTS));
    }

    @Test
    @Transactional
    public void getNonExistingSLA() throws Exception {
        // Get the sLA
        restSLAMockMvc.perform(get("/api/slas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSLA() throws Exception {
        // Initialize the database
        sLARepository.saveAndFlush(sLA);

        int databaseSizeBeforeUpdate = sLARepository.findAll().size();

        // Update the sLA
        SLA updatedSLA = sLARepository.findById(sLA.getId()).get();
        // Disconnect from session so that the updates on updatedSLA are not directly saved in db
        em.detach(updatedSLA);
        updatedSLA
            .rts(UPDATED_RTS);
        SLADTO sLADTO = sLAMapper.toDto(updatedSLA);

        restSLAMockMvc.perform(put("/api/slas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sLADTO)))
            .andExpect(status().isOk());

        // Validate the SLA in the database
        List<SLA> sLAList = sLARepository.findAll();
        assertThat(sLAList).hasSize(databaseSizeBeforeUpdate);
        SLA testSLA = sLAList.get(sLAList.size() - 1);
        assertThat(testSLA.getRts()).isEqualTo(UPDATED_RTS);
    }

    @Test
    @Transactional
    public void updateNonExistingSLA() throws Exception {
        int databaseSizeBeforeUpdate = sLARepository.findAll().size();

        // Create the SLA
        SLADTO sLADTO = sLAMapper.toDto(sLA);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSLAMockMvc.perform(put("/api/slas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sLADTO)))
            .andExpect(status().isBadRequest());

        // Validate the SLA in the database
        List<SLA> sLAList = sLARepository.findAll();
        assertThat(sLAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSLA() throws Exception {
        // Initialize the database
        sLARepository.saveAndFlush(sLA);

        int databaseSizeBeforeDelete = sLARepository.findAll().size();

        // Delete the sLA
        restSLAMockMvc.perform(delete("/api/slas/{id}", sLA.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SLA> sLAList = sLARepository.findAll();
        assertThat(sLAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
