package com.shadem.labs.vesselcall.vesselcallapi;

import com.shadem.labs.vesselcall.coreapi.CreateVesselCallCommand;
import com.shadem.labs.vesselcall.vesselcallapi.api.CreateVesselCall;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class VesselCallController {

    private static AtomicInteger atomicInteger = new AtomicInteger(1800000);
    private final CommandGateway commandGateway;

    public VesselCallController(@SuppressWarnings("SpringJavaAutowiringInspection") CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/newvisit")
    public Future<String> createVesselCall(@RequestBody @Valid CreateVesselCall createVesselCall) {
        String callReferenceNumber = createVesselCall.getPort() + atomicInteger.incrementAndGet();
        return commandGateway.send(new CreateVesselCallCommand(callReferenceNumber, createVesselCall.getVessel(), createVesselCall.getPort()));
    }
}