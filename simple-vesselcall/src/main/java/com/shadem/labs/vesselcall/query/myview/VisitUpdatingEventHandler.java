package com.shadem.labs.vesselcall.query.myview;

import com.shadem.labs.vesselcall.coreapi.VesselCallCreatedEvent;
import com.shadem.labs.vesselcall.coreapi.VoyageDetailsUpdatedEvent;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author albert
 */
@Component
public class VisitUpdatingEventHandler {

	private final VisitRepository visitRepository;
	
	@Autowired
	public VisitUpdatingEventHandler(VisitRepository taskEntryRepository) {
		this.visitRepository = taskEntryRepository;
	}

	@Autowired
	public void configure(EventHandlingConfiguration config) {
		config.usingTrackingProcessors(); // default all processors to tracking mode.
	}

	@EventHandler
	void on(VesselCallCreatedEvent event) {
		Visit visit = new Visit(event.getCallReferenceNumber(), event.getVessel() + "bootje", event.getPort(), false, "");
		visitRepository.save(visit);
	}

	@EventHandler
	void on(VoyageDetailsUpdatedEvent event) {
		Visit visit = visitRepository.findOne(event.getCallReferenceNumber());
		visit.setCarrier(event.getCarrier());
		visit.setStatus(true);
		
		visitRepository.save(visit);
	}
}
