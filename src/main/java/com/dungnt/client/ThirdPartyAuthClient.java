package com.dungnt.client;

import com.dungnt.dto.common.AuthRequest;
import com.dungnt.dto.common.AuthResponse;
import com.dungnt.dto.common.PartnerResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;



@RegisterRestClient(baseUri = "http://125.235.38.229:8080")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth/v1/third-party/token")
public interface ThirdPartyAuthClient {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    PartnerResponse<AuthResponse> authenticate(@HeaderParam("App-Key") String appKey, @HeaderParam("App-Secret") String appSecret);
}
