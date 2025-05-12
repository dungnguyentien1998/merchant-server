package com.dungnt.resource;

import com.dungnt.client.PaymentGatewayClient;
//import com.dungnt.repository.ConfigRepository;
import com.dungnt.dto.common.*;
import com.dungnt.dto.partner.OrderInfoRequest;
import com.dungnt.dto.common.PartnerResponse;
import com.dungnt.dto.common.PaymentResult;
import com.dungnt.dto.pg.CreateTransactionRequest;
import com.dungnt.dto.pg.CreateTransactionResponse;
import com.dungnt.entity.Order;
import com.dungnt.service.OrderService;
import com.dungnt.util.CommonUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/merchant/paygate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class OrderResource {
//    @Inject
//    ConfigRepository configRepository;

    @Inject
    @RestClient
    PaymentGatewayClient paymentGatewayClient;

    @Inject
    @ConfigProperty(name = "partner.return-url")
    String returnUrl;

    @Inject
    @ConfigProperty(name = "partner.cancel-url")
    String cancelUrl;

    @Inject
    CommonUtils utils;

    @Inject
    OrderService orderService;

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
        Integer status = body.getTransactionStatus();
        String orderId = body.getOrderId();

        orderService.updateOrder(orderId, status);

        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");
        response.put("orderId", "1234");

        return Response.ok(response).build();
    }

    @POST
    @Path("create-transaction")
    public Response createTransaction(CreateTransactionRequest clientRequest, @Context HttpHeaders headers) {
        Order order = orderService.createPayOrder(clientRequest.getOrderId(), clientRequest.getTransAmount());
        clientRequest.setExpireAfter(900);
        clientRequest.setDescription(StringUtils.isEmpty(clientRequest.getDescription()) ? "TTDV" : clientRequest.getDescription());
        clientRequest.setCancelUrl(cancelUrl);
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        PartnerResponse<CreateTransactionResponse> clientResponse = paymentGatewayClient.createTransaction(
                authorization, "", clientRequest);
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
    public Response searchTransaction(SearchTransactionRequest clientRequest, @Context HttpHeaders headers) {
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        PartnerResponse<List<SearchTransactionResponse>> clientResponse = paymentGatewayClient.searchTransaction(
                authorization, "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        PartnerResponse.Status status = clientResponse.getStatus();
        List<SearchTransactionResponse> baseResponse = clientResponse.getData();
        response.put("code", status.getCode());
        if (baseResponse != null && !baseResponse.isEmpty()) {
            response.put("status", baseResponse.get(0).getTransactionStatus());
            response.put("orderId", clientRequest.getOrderId());
        }

        return Response.ok(response).build();
    }

    @POST
    @Path("refund-transaction")
    public Response refundTransaction(RefundTransactionRequest clientRequest, @Context HttpHeaders headers) {
        String refundOrderId = utils.generateULID();
        Order order = orderService.createRefundOrder(refundOrderId, clientRequest.getTransAmount());
        clientRequest.setOrderId(refundOrderId);
        clientRequest.setReason("Refund TTDV");
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        PartnerResponse<RefundTransactionResponse> clientResponse = paymentGatewayClient.refundTransaction(
                authorization, "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        PartnerResponse.Status status = clientResponse.getStatus();
        response.put("code", status.getCode());
        response.put("orderId", refundOrderId);
        if ("00".equals(status.getCode())) {
            response.put("status", 1);
        } else {
            response.put("status", 0);
        }

        return Response.ok(response).build();
    }

}
