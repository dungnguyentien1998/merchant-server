package com.dungnt.resource;

import com.dungnt.client.TokenClient;
import com.dungnt.dto.common.PaymentTokenBaseRequest;
import com.dungnt.dto.common.PaymentTokenBaseResponse;
import com.dungnt.dto.common.PartnerResponse;
import com.dungnt.dto.common.TokenResult;
import com.dungnt.dto.partner.TokenInfoRequest;
import com.dungnt.dto.token.*;
import com.dungnt.entity.Order;
import com.dungnt.entity.User;
import com.dungnt.service.OrderService;
import com.dungnt.service.UserService;
import com.dungnt.util.CommonUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.dungnt.service.SecretReader;
import java.util.Properties;

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
    OrderService orderService;

    @Inject
    UserService userService;

    @Inject
    CommonUtils utils;

    @Inject
    SecretReader secretReader;

    @POST
    @Path("/init")
    public Response initializeLink(InitializeLinkRequest clientRequest, @Context HttpHeaders headers) {
        Properties props = secretReader.readSecretFile();
        Order order = orderService.createLinkOrder();
        User user = userService.createUser();

        clientRequest.setOrderId(order.getOrderId());
        clientRequest.setMerchantUserId(user.getUserId());
        clientRequest.setMerchantCustMsisdn(props.getProperty("msisdn", "84123456789"));
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        PartnerResponse<InitializeLinkResponse> clientResponse = tokenClient.initLink(
                authorization, "", clientRequest);

        Map<String, Object> response = new HashMap<>();
        InitializeLinkResponse baseResponse = clientResponse.getData();
        PartnerResponse.Status status = clientResponse.getStatus();
        if (baseResponse != null) {
            response.put("url", baseResponse.getUrl());
            response.put("qrCode", baseResponse.getQrCode());
            response.put("deepLink", baseResponse.getDeepLink());
        }
        response.put("code", status.getCode());
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/search")
    public Response search(TokenSearchRequest clientRequest, @Context HttpHeaders headers) {
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        PartnerResponse<List<TokenSearchResponse>> clientResponse = tokenClient.searchLink(
                authorization, "", clientRequest);
        Map<String, Object> response = new HashMap<>();
        PartnerResponse.Status status = clientResponse.getStatus();
        response.put("code", status.getCode());
        response.put("data", clientResponse.getData());
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/check-available")
    public Response checkAvailable(PaymentAvailabilityRequest clientRequest, @Context HttpHeaders headers) {
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        Map<String, Object> response = new HashMap<>();
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/token-payment")
    public Response paymentByToken(PaymentTokenBaseRequest clientRequest, @Context HttpHeaders headers) {
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        clientRequest.setOrderId(utils.generateULID());
        clientRequest.setToken("");
        PartnerResponse<PaymentTokenBaseResponse> clientResponse = tokenClient.paymentByToken(
                authorization, "", clientRequest);

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
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/token-payment-confirm")
    public Response paymentByTokenConfirm(PaymentTokenBaseRequest clientRequest, @Context HttpHeaders headers) {
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        PartnerResponse<PaymentTokenBaseResponse> clientResponse = tokenClient.paymentByTokenConfirm(
                authorization, "", clientRequest);
        Map<String, Object> response = new HashMap<>();

        if (clientResponse != null && clientResponse.getStatus() != null) {
            PartnerResponse.Status status = clientResponse.getStatus();
            PaymentTokenBaseResponse baseResponse = clientResponse.getData();
            response.put("code", baseResponse.getTransactionStatus());
        }
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/payment-retry-otp")
    public Response paymentRetryOtp(PaymentTokenBaseRequest clientRequest, @Context HttpHeaders headers) {
        String authorization = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new WebApplicationException("Missing Authorization header", 401);
        }
        PartnerResponse<PaymentTokenBaseResponse> clientResponse = tokenClient.paymentRetryOtp(
                authorization, "", clientRequest);

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
        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/verify")
    public Response verify(
            @HeaderParam("Signature") String signature,
            TokenInfoRequest body) {
        if (signature == null || signature.isBlank()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");

        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/ipn")
    public Response linkIpn(
            @HeaderParam("Signature") String signature,
            TokenResult body) {
        if (signature == null || signature.isBlank()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");

        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/payment/ipn")
    public Response paymentIpn(
            @HeaderParam("Signature") String signature,
            TokenResult body) {
        if (signature == null || signature.isBlank()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");

        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/unlink-ipn")
    public Response unlinkIpn(
            @HeaderParam("Signature") String signature,
            TokenResult body) {
        String orderId = body.getOrderId();
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", orderId);
        try {
            if (signature == null || signature.isBlank()) {
                response.put("code", "03");
                return Response.ok(response).build();
            }

            Integer status = body.getTransactionStatus();
            String userId = body.getMerchantUserId();
            String token = body.getToken();

            Order order = Order.find("orderId", orderId).firstResult();
            order.setStatus(status);
            order.persist();
            User user = User.find("userId", userId).firstResult();
            user.setToken(token);
            user.persist();

            response.put("code", "00");
        } catch (Exception e) {
            response.put("code", "04");
        }

        return Response.ok(response).type(MediaType.APPLICATION_JSON).build();
    }
}
