package com.dungnt.client;

import com.dungnt.dto.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/paybiz/payment-gateway/public/api")
@RegisterRestClient(configKey = "payment-gateway-url")
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
    PartnerResponse<SearchTransactionResponse> searchTransaction(
            @HeaderParam("Authorization") String authToken,
            @HeaderParam("Signature") String signature,
            SearchTransactionRequest request
    );
}
