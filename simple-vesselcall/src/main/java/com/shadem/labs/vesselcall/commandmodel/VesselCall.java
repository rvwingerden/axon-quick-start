package com.shadem.labs.vesselcall.commandmodel;

import com.shadem.labs.vesselcall.coreapi.CreateVesselCallCommand;
import com.shadem.labs.vesselcall.coreapi.VesselCallCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class VesselCall {

    @AggregateIdentifier
    private String callReferenceNumber;
    private String vessel;
    private String port;
    private Set<String> berths;

    public VesselCall() {
    }

    @CommandHandler
    public VesselCall(CreateVesselCallCommand command) {
        apply(new VesselCallCreatedEvent(command.getCallReferenceNumber(), command.getVessel(), command.getPort()));
    }

    @EventSourcingHandler
    protected void on(VesselCallCreatedEvent event) {
        this.callReferenceNumber = event.getCallReferenceNumber();
        this.vessel = event.getVessel();
        this.port = event.getPort();
        this.berths = new HashSet<>();
    }
}
