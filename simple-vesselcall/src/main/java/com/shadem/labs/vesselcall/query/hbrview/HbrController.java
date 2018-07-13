package com.shadem.labs.vesselcall.query.hbrview;

import com.shadem.labs.vesselcall.coreapi.*;
import lombok.Builder;
import lombok.Data;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/hbr")
public class HbrController {

    private List<VesselVisit> vesselVisits;
    private final CommandGateway commandGateway;
    public HbrController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
        this.vesselVisits = new ArrayList();
    }

    @GetMapping
    public List<String> vesselVisits() {
        return vesselVisits.stream()
                         .map(VesselVisit::getUcrn).sorted().collect(toList());
    }

    @EventHandler
    public void on(VesselVisitSent2PaEvent event) {
        VesselVisit vesselVisit = VesselVisit.builder()
                .ucrn(event.getCallReferenceNumber())
                .vessel(event.getVesselVisit().getVessel())
                .build();
        vesselVisits.add(vesselVisit);

        CompletableFuture<Void> contactHbr = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return event.getCallReferenceNumber();
        }).thenAccept(s -> handleAccept(s));
    }

    public CompletableFuture<Object> handleAccept(String ucrn) {
        return commandGateway.send(new AcceptVesselVisit2PaCommand(ucrn));
    }

    @Data
    @Builder
    private static class VesselVisit {
        String ucrn;
        String vessel;
    }
}
