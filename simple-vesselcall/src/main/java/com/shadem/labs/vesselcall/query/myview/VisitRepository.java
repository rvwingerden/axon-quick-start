package com.shadem.labs.vesselcall.query.myview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author albert
 */
public interface VisitRepository extends CrudRepository<Visit, String> {
	Page<Visit> findByVesselNameAndStatus(String vesselName, boolean status, Pageable pageable);
}
