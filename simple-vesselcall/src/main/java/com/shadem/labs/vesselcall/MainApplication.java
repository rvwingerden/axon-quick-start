package com.shadem.labs.vesselcall;

import com.shadem.labs.vesselcall.commandmodel.VesselCall;
import com.shadem.labs.vesselcall.coreapi.CreateVesselCallCommand;
import com.shadem.labs.vesselcall.coreapi.SendVesselVisit2PaCommand;
import com.shadem.labs.vesselcall.coreapi.UpdateVoyageDetailsCommand;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

public class MainApplication {

    public static void main(String[] args) {
        Configuration configuration = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(VesselCall.class)
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .buildConfiguration();

        configuration.start();
        configuration.commandBus().dispatch(asCommandMessage(new CreateVesselCallCommand("NLRTM1800001", "AMALIA", "NLRTM")));
        configuration.commandBus().dispatch(asCommandMessage(new UpdateVoyageDetailsCommand("NLRTM1800001","CARRIE", "Morgen")));

        configuration.commandBus().dispatch(asCommandMessage(new SendVesselVisit2PaCommand("NLRTM1800001")));
    }


}
