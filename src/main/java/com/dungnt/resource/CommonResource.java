package com.dungnt.resource;

import com.dungnt.dto.common.PaymentResult;
import com.dungnt.util.CommonUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("/merchant/common")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class CommonResource {
    @Inject
    CommonUtils utils;

    @POST
    @Path("verify-signature")
    public Response verifySignature(PaymentResult payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", true);
        return Response.ok(response).build();
    }

    @GET
    @Path("id")
    public Response getOrderId() {
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", utils.generateULID());
        return Response.ok(response).build();
    }
}
