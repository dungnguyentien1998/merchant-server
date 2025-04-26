package com.dungnt.resource;

import com.dungnt.client.PaymentGatewayClient;
import com.dungnt.client.TokenClient;
import com.dungnt.dto.common.PaymentTokenBaseRequest;
import com.dungnt.dto.common.PaymentTokenBaseResponse;
import com.dungnt.dto.partner.PartnerResponse;
import com.dungnt.dto.pg.CreateTransactionResponse;
import com.dungnt.dto.token.*;
import com.dungnt.util.CommonUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/merchant/link")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class LinkResource {
    @Inject
    @RestClient
    TokenClient tokenClient;

    @Inject
    CommonUtils utils;

    @POST
    @Path("initialize-link")
    public Response initializeLink(InitializeLinkRequest clientRequest) {
        clientRequest.setOrderId(utils.generateULID());
        clientRequest.setMerchantUserId(utils.generateULID());
        PartnerResponse<InitializeLinkResponse> clientResponse = tokenClient.initLink(
                "", "", clientRequest);

        Map<String, Object> response = new HashMap<>();
        InitializeLinkResponse baseResponse = clientResponse.getData();
        PartnerResponse.Status status = clientResponse.getStatus();
        if (baseResponse != null) {
            response.put("url", baseResponse.getUrl());
            response.put("qrCode", baseResponse.getQrCode());
            response.put("deepLink", baseResponse.getDeepLink());
        }
        response.put("code", status.getCode());
        return Response.ok(response).build();
    }

    @POST
    @Path("search")
    public Response search(TokenSearchRequest clientRequest) {
        PartnerResponse<List<TokenSearchResponse>> clientResponse = tokenClient.searchLink(
                "", "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        PartnerResponse.Status status = clientResponse.getStatus();
        response.put("code", status.getCode());
        response.put("data", clientResponse.getData());
        return Response.ok(response).build();
    }

    @POST
    @Path("check-available")
    public Response checkAvailable(PaymentAvailabilityRequest clientRequest) {
        Map<String, Object> response = new HashMap<>();
        return Response.ok(response).build();
    }

    @POST
    @Path("token-payment")
    public Response paymentByToken(PaymentTokenBaseRequest clientRequest) {
        clientRequest.setOrderId(utils.generateULID());
        clientRequest.setToken("");
        PartnerResponse<PaymentTokenBaseResponse> clientResponse = tokenClient.paymentByToken(
                "", "", clientRequest);

        Map<String, Object> response = new HashMap<>();
        if (clientResponse != null && clientResponse.getStatus() != null) {
            PartnerResponse.Status status = clientResponse.getStatus();
            if ("OTP".equals(status.getCode())) {
                response.put("code", status.getCode());
            } else {
                PaymentTokenBaseResponse baseResponse = clientResponse.getData();
                response.put("code", baseResponse.getTransactionStatus());
            }
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("token-payment-confirm")
    public Response paymentByTokenConfirm(PaymentTokenBaseRequest clientRequest) {
        PartnerResponse<PaymentTokenBaseResponse> clientResponse = tokenClient.paymentByTokenConfirm(
                "", "", clientRequest);
        Map<String, Object> response = new HashMap<>();

        if (clientResponse != null && clientResponse.getStatus() != null) {
            PartnerResponse.Status status = clientResponse.getStatus();
            PaymentTokenBaseResponse baseResponse = clientResponse.getData();
            response.put("code", baseResponse.getTransactionStatus());
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("payment-retry-otp")
    public Response paymentRetryOtp(PaymentTokenBaseRequest clientRequest) {
        PartnerResponse<PaymentTokenBaseResponse> clientResponse = tokenClient.paymentRetryOtp(
                "", "", clientRequest);

        Map<String, Object> response = new HashMap<>();
        if (clientResponse != null && clientResponse.getStatus() != null) {
            PartnerResponse.Status status = clientResponse.getStatus();
            if ("OTP".equals(status.getCode())) {
                response.put("code", status.getCode());
            } else {
                PaymentTokenBaseResponse baseResponse = clientResponse.getData();
                response.put("code", baseResponse.getTransactionStatus());
            }
        }
        return Response.ok(response).build();
    }



}
