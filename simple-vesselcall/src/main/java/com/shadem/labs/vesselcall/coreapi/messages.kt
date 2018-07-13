package com.shadem.labs.vesselcall.coreapi

import com.shadem.labs.vesselcall.commandmodel.VesselCall
import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.time.Instant

data class CreateVesselCallCommand(@TargetAggregateIdentifier val callReferenceNumber: String,val vessel: String, val port: String)
data class UpdateVoyageDetailsCommand(@TargetAggregateIdentifier val callReferenceNumber: String,val carrier: String, val eta: String)
data class SendVesselVisit2PaCommand(@TargetAggregateIdentifier val callReferenceNumber: String)
data class AcceptVesselVisit2PaCommand(@TargetAggregateIdentifier val callReferenceNumber: String)

data class VesselCallCreatedEvent(val callReferenceNumber: String, val vessel: String, val port: String)
data class VoyageDetailsUpdatedEvent(val callReferenceNumber: String, val carrier: String, val eta: String)
data class VesselVisitSent2PaEvent(val callReferenceNumber: String, val vesselVisit: VesselCall)
data class VesselVisit2PaAcceptedEvent(val callReferenceNumber: String)
