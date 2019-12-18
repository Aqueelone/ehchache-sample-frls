package com.luxiti.frls.repository;
import com.luxiti.frls.domain.SLA;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SLA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SLARepository extends JpaRepository<SLA, Long> {

}
