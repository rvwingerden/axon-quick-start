package com.shadem.labs.vesselcall.query.hbrview;

import com.shadem.labs.vesselcall.coreapi.VesselCallCreatedEvent;
import com.shadem.labs.vesselcall.coreapi.VesselVisitSent2PaEvent;
import lombok.Builder;
import lombok.Data;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/hbr")
public class HbrController {

    private List<VesselVisit> vesselVisits;

    public HbrController() {
        this.vesselVisits = new ArrayList();
    }

    @GetMapping
    public List<String> vesselVisits() {
        return vesselVisits.stream()
                         .map(VesselVisit::getUcrn).sorted().collect(toList());
    }

    @EventHandler
    public void on(VesselVisitSent2PaEvent event) {
        vesselVisits.add(VesselVisit.builder()
                .ucrn(event.getCallReferenceNumber())
                .vessel(event.getVesselVisit().getVessel())
                .build());
    }

    @Data
    @Builder
    private static class VesselVisit {
        String ucrn;
        String vessel;
    }
}
