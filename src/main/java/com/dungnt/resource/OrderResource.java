package com.dungnt.resource;

import com.dungnt.client.PaymentGatewayClient;
import com.dungnt.dto.*;
import com.dungnt.repository.ConfigRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.Map;

@Path("/merchant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class OrderResource {
    @Inject
    ConfigRepository configRepository;

    @Inject
    @RestClient
    PaymentGatewayClient paymentGatewayClient;

    @POST
    @Path("/order/verify")
    public Response verify(
            @HeaderParam("Signature") String signature,
            OrderInfoRequest body
    ) {
        if (signature == null || signature.isBlank()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String defaultConfig = configRepository.findByParamNameAndIsActive("DEFAULT_CONFIG");

        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");
        response.put("orderId", "1234");

        return Response.ok(response).build();
    }

    @POST
    @Path("/ipn")
    public Response ipn(
            @HeaderParam("Signature") String signature,
            PaymentResult body
    ) {
        if (signature == null || signature.isBlank()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");
        response.put("orderId", "1234");

        return Response.ok(response).build();
    }

    @POST
    @Path("create-transaction")
    public Response createTransaction() {
        CreateTransactionRequest clientRequest = new CreateTransactionRequest();
        PartnerResponse<CreateTransactionResponse> clientResponse = paymentGatewayClient.createTransaction(
                "", "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");
        response.put("orderId", "1234");

        return Response.ok(response).build();
    }

    @POST
    @Path("search-transaction")
    public Response searchTransaction() {
        SearchTransactionRequest clientRequest = new SearchTransactionRequest();
        PartnerResponse<SearchTransactionResponse> clientResponse = paymentGatewayClient.searchTransaction(
                "", "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");
        response.put("orderId", "1234");

        return Response.ok(response).build();
    }

}
