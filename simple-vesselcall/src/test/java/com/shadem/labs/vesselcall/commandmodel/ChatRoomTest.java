package com.shadem.labs.vesselcall.commandmodel;

import com.shadem.labs.vesselcall.coreapi.CreateVesselCallCommand;
import com.shadem.labs.vesselcall.coreapi.VesselCallCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;

public class ChatRoomTest {
    private AggregateTestFixture<VesselCall> testFixture;

    @Before
    public void setUp() throws Exception {
        testFixture = new AggregateTestFixture<>(VesselCall.class);
    }

    @Test
    public void testCreateVesselCall() throws Exception {
        testFixture.givenNoPriorActivity()
                   .when(new CreateVesselCallCommand("xxx", "boot", "NLRTM"))
                   .expectEvents(new VesselCallCreatedEvent("xxx", "boot", "NLRTM"));
    }

}
