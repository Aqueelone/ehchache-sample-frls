package com.luxiti.frls.service;

import com.luxiti.frls.service.dto.SLADTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.luxiti.frls.domain.SLA}.
 */
public interface SLAService {

    /**
     * Save a sLA.
     *
     * @param sLADTO the entity to save.
     * @return the persisted entity.
     */
    SLADTO save(SLADTO sLADTO);

    /**
     * Get all the sLAS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SLADTO> findAll(Pageable pageable);


    /**
     * Get the "id" sLA.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SLADTO> findOne(Long id);

    /**
     * Delete the "id" sLA.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
