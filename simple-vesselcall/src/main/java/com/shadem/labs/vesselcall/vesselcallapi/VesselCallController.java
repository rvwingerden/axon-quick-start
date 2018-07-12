package com.shadem.labs.vesselcall.vesselcallapi;

import com.shadem.labs.vesselcall.coreapi.CreateVesselCallCommand;
import com.shadem.labs.vesselcall.coreapi.SendVesselVisit2PaCommand;
import com.shadem.labs.vesselcall.coreapi.UpdateVoyageDetailsCommand;
import com.shadem.labs.vesselcall.vesselcallapi.api.CreateVesselCall;
import com.shadem.labs.vesselcall.vesselcallapi.api.UpdateVoyageDetails;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/vesselcall")
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

    @PostMapping("/{callReferenceNumber}/voyagedetails")
    public Future<String> createVesselCall(@RequestParam String callReferenceNumber, @RequestBody @Valid UpdateVoyageDetails updateVoyageDetails) {
        return commandGateway.send(new UpdateVoyageDetailsCommand(callReferenceNumber, updateVoyageDetails.getCarrier(), updateVoyageDetails.getEta()));
    }

    @GetMapping("/{callReferenceNumber}/send2pa")
    public Future<String> createVesselCall(@RequestParam String callReferenceNumber) {
        return commandGateway.send(new SendVesselVisit2PaCommand(callReferenceNumber));
    }
}