package com.dungnt.client;

import com.dungnt.dto.common.*;
import com.dungnt.dto.common.PartnerResponse;
import com.dungnt.dto.pg.CreateTransactionRequest;
import com.dungnt.dto.pg.CreateTransactionResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/paybiz/payment-gateway/public/api")
@RegisterRestClient(baseUri = "http://125.235.38.229:8080")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PaymentGatewayClient {
    @POST
    @Path("/v2/create-transaction")
    PartnerResponse<CreateTransactionResponse> createTransaction(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            CreateTransactionRequest request
    );

    @POST
    @Path("/v3/merchant/search-transaction")
    PartnerResponse<List<SearchTransactionResponse>> searchTransaction(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            SearchTransactionRequest request
    );

    @POST
    @Path("/v2/merchant/refund-transaction")
    PartnerResponse<RefundTransactionResponse> refundTransaction(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            RefundTransactionRequest request
    );
}
