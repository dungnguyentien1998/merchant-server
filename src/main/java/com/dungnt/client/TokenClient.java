package com.dungnt.client;

import com.dungnt.dto.common.*;
import com.dungnt.dto.common.PartnerResponse;
import com.dungnt.dto.token.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/paybiz/payment-gateway/public/api")
@RegisterRestClient(baseUri = "http://125.235.38.229:8080")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TokenClient {
    @POST
    @Path("/v2/merchant/refund-transaction")
    PartnerResponse<RefundTransactionResponse> refundTransaction(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            RefundTransactionRequest request
    );

    @POST
    @Path("/v2/token/initialize-link")
    PartnerResponse<InitializeLinkResponse> initLink(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            InitializeLinkRequest request
    );

    @POST
    @Path("/sandbox-payment-gateway/public/api/v2/token/search")
    PartnerResponse<List<TokenSearchResponse>> searchLink(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            TokenSearchRequest request
    );

    @POST
    @Path("/v2/token/unlink")
    PartnerResponse<List<SearchTransactionResponse>> unlink(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            SearchTransactionRequest request
    );

    @POST
    @Path("/sandbox-payment-gateway/public/api/v2/token/payment-availability")
    PartnerResponse<PaymentAvailabilityResponse> checkAvailable(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            PaymentAvailabilityRequest request
    );

    @POST
    @Path("/sandbox-payment-gateway/public/api/v2/token/payment")
    PartnerResponse<PaymentTokenBaseResponse> paymentByToken(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            PaymentTokenBaseRequest request
    );

    @POST
    @Path("/sandbox-payment-gateway/public/api/v2/token/payment-confirm")
    PartnerResponse<PaymentTokenBaseResponse> paymentByTokenConfirm(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            PaymentTokenBaseRequest request
    );

    @POST
    @Path("/sandbox-payment-gateway/public/api/v2/token/payment-retry-otp")
    PartnerResponse<PaymentTokenBaseResponse> paymentRetryOtp(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            PaymentTokenBaseRequest request
    );

    @POST
    @Path("/v3/merchant/search-transaction")
    PartnerResponse<List<SearchTransactionResponse>> searchTransaction(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            SearchTransactionRequest request
    );
}
