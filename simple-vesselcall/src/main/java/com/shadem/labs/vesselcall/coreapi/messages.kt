package com.shadem.labs.vesselcall.coreapi

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class CreateVesselCallCommand(@TargetAggregateIdentifier val callReferenceNumber: String,val vessel: String, val port: String)

data class VesselCallCreatedEvent(@TargetAggregateIdentifier val callReferenceNumber: String, val vessel: String, val port: String)
