package com.dungnt.resource;

import com.dungnt.client.PaymentGatewayClient;
//import com.dungnt.repository.ConfigRepository;
import com.dungnt.dto.common.*;
import com.dungnt.dto.partner.OrderInfoRequest;
import com.dungnt.dto.partner.PartnerResponse;
import com.dungnt.dto.common.PaymentResult;
import com.dungnt.dto.pg.CreateTransactionRequest;
import com.dungnt.dto.pg.CreateTransactionResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/merchant/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class OrderResource {
//    @Inject
//    ConfigRepository configRepository;

    @Inject
    @RestClient
    PaymentGatewayClient paymentGatewayClient;

    @POST
    @Path("/verify")
    public Response verify(
            @HeaderParam("Signature") String signature,
            OrderInfoRequest body
    ) {
        if (signature == null || signature.isBlank()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

//        String defaultConfig = configRepository.findByParamNameAndIsActive("DEFAULT_CONFIG");

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
    public Response createTransaction(CreateTransactionRequest clientRequest) {
        clientRequest.setExpireAfter(900);
        clientRequest.setDescription(StringUtils.isEmpty(clientRequest.getDescription()) ? "TTDV" : clientRequest.getDescription());
        clientRequest.setCancelUrl("https://google.com");
        clientRequest.setReturnUrl("https://google.com");
        PartnerResponse<CreateTransactionResponse> clientResponse = paymentGatewayClient.createTransaction(
                "", "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        CreateTransactionResponse baseResponse = clientResponse.getData();
        PartnerResponse.Status status = clientResponse.getStatus();
        if (baseResponse != null) {
            response.put("url", baseResponse.getUrl());
            response.put("qrCode", baseResponse.getQrCode());
            response.put("deepLink", baseResponse.getDeepLink());
        }
        response.put("code", status.getCode());
        response.put("orderId", clientRequest.getOrderId());

        return Response.ok(response).build();
    }

    @POST
    @Path("search-transaction")
    public Response searchTransaction(SearchTransactionRequest clientRequest) {
        PartnerResponse<List<SearchTransactionResponse>> clientResponse = paymentGatewayClient.searchTransaction(
                "", "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        PartnerResponse.Status status = clientResponse.getStatus();
        response.put("code", status.getCode());
        response.put("data", clientResponse.getData());

        return Response.ok(response).build();
    }

    @POST
    @Path("refund-transaction")
    public Response refundTransaction(RefundTransactionRequest clientRequest) {
        PartnerResponse<RefundTransactionResponse> clientResponse = paymentGatewayClient.refundTransaction(
                "", "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        PartnerResponse.Status status = clientResponse.getStatus();
        response.put("code", status.getCode());

        return Response.ok(response).build();
    }

}
