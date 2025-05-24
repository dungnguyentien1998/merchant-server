package com.dungnt.resource;


import com.dungnt.client.ThirdPartyAuthClient;
import com.dungnt.dto.common.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;


@Path("/merchant/authenticate")
public class AuthResource {
    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    @RestClient
    ThirdPartyAuthClient authClient;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response authenticate(AuthRequest request) {
        try {
            PartnerResponse<AuthResponse> clientResponse = authClient.authenticate(request.username, request.password);
            LOG.info("Authentication successful");
            Map<String, Object> response = new HashMap<>();
            AuthResponse baseResponse = clientResponse.getData();
            PartnerResponse.Status status = clientResponse.getStatus();
            if (baseResponse != null) {
                response.put("token", baseResponse.getToken());
            }

            return Response.ok(response).build();
        } catch (WebApplicationException e) {
            LOG.errorf("Authentication failed: %s", e.getMessage());
            return Response.status(e.getResponse().getStatus()).entity(new ErrorResponse("Authentication failed")).build();
        }
    }
}

class ErrorResponse {
    public String message;

    public ErrorResponse() {}
    public ErrorResponse(String message) {
        this.message = message;
    }
}
