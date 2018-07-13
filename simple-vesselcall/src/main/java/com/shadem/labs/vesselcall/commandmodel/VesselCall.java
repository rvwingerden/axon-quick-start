package com.shadem.labs.vesselcall.commandmodel;

import com.shadem.labs.vesselcall.coreapi.*;
import com.shadem.labs.vesselcall.vesselcallapi.api.UpdateVoyageDetails;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static com.shadem.labs.vesselcall.commandmodel.DeclarationStatus.ACCEPTED;
import static com.shadem.labs.vesselcall.commandmodel.DeclarationStatus.DECLARED;
import static com.shadem.labs.vesselcall.commandmodel.DeclarationStatus.NEW;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class VesselCall {

    @AggregateIdentifier
    private String callReferenceNumber;
    private String vessel;
    private String port;
    private String carrier;
    private String eta;
    private Set<String> berths;

    private DeclarationStatus paStatus = NEW;

    public VesselCall() {
    }

    @CommandHandler
    public VesselCall(CreateVesselCallCommand command) {
        apply(new VesselCallCreatedEvent(command.getCallReferenceNumber(), command.getVessel(), command.getPort()));
    }

    @CommandHandler
    public void handle(UpdateVoyageDetailsCommand command) {
        apply(new VoyageDetailsUpdatedEvent(command.getCallReferenceNumber(), command.getCarrier(), command.getEta()));
    }

    @CommandHandler
    public void handle(SendVesselVisit2PaCommand command) {
        apply(new VesselVisitSent2PaEvent(command.getCallReferenceNumber(), this));
    }

    @CommandHandler
    public void handle(AcceptVesselVisit2PaCommand command) {
        apply(new VesselVisit2PaAcceptedEvent(command.getCallReferenceNumber()));
    }

    @EventSourcingHandler
    protected void on(VesselCallCreatedEvent event) {
        this.callReferenceNumber = event.getCallReferenceNumber();
        this.vessel = event.getVessel();
        this.port = event.getPort();
        this.berths = new HashSet<>();
    }

    @EventSourcingHandler
    protected void on(VoyageDetailsUpdatedEvent event) {
        this.carrier = event.getCarrier();
        this.eta = event.getEta();
    }

    @EventSourcingHandler
    protected void on(VesselVisitSent2PaEvent event) {
        this.paStatus = DECLARED;
    }

    @EventSourcingHandler
    protected void on(VesselVisit2PaAcceptedEvent event) {
        System.out.println("jiahaalllallla accepted.....");
        this.paStatus = ACCEPTED;
    }

    public String getVessel() {
        return vessel;
    }
}
