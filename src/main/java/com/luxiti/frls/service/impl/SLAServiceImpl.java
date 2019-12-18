package com.luxiti.frls.service.impl;

import com.luxiti.frls.service.SLAService;
import com.luxiti.frls.domain.SLA;
import com.luxiti.frls.repository.SLARepository;
import com.luxiti.frls.service.dto.SLADTO;
import com.luxiti.frls.service.mapper.SLAMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SLA}.
 */
@Service
@Transactional
public class SLAServiceImpl implements SLAService {

    private final Logger log = LoggerFactory.getLogger(SLAServiceImpl.class);

    private final SLARepository sLARepository;

    private final SLAMapper sLAMapper;

    public SLAServiceImpl(SLARepository sLARepository, SLAMapper sLAMapper) {
        this.sLARepository = sLARepository;
        this.sLAMapper = sLAMapper;
    }

    /**
     * Save a sLA.
     *
     * @param sLADTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SLADTO save(SLADTO sLADTO) {
        log.debug("Request to save SLA : {}", sLADTO);
        SLA sLA = sLAMapper.toEntity(sLADTO);
        sLA = sLARepository.save(sLA);
        return sLAMapper.toDto(sLA);
    }

    /**
     * Get all the sLAS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SLADTO> findAll(Pageable pageable) {
        log.debug("Request to get all SLAS");
        return sLARepository.findAll(pageable)
            .map(sLAMapper::toDto);
    }


    /**
     * Get one sLA by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SLADTO> findOne(Long id) {
        log.debug("Request to get SLA : {}", id);
        return sLARepository.findById(id)
            .map(sLAMapper::toDto);
    }

    /**
     * Delete the sLA by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SLA : {}", id);
        sLARepository.deleteById(id);
    }
}
