package com.dungnt.filter;

import com.dungnt.constant.CommonConstants;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import java.io.IOException;

@Provider
public class LoggingResponseFilter implements ContainerResponseFilter {
    @ConfigProperty(name = "header", defaultValue = "DefaultHeader")
    private String header;
    private static final Logger LOG = Logger.getLogger(LoggingResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Add custom response header
        responseContext.getHeaders().add(CommonConstants.SIG, header);

        // Log response body (if it's a readable format like JSON or text)
        Object entity = responseContext.getEntity();
        if (entity != null) {
            // Convert entity to string for logging
            String responseBody = entity.toString();
            if (responseContext.getMediaType() != null &&
                (responseContext.getMediaType().toString().contains("application/json") ||
                 responseContext.getMediaType().toString().contains("text/plain"))) {
                LOG.infof("Response body: %s", responseBody);
            } else {
                LOG.info("Response body is not logged (non-text or non-JSON content)");
            }
        } else {
            LOG.info("No response body");
        }
    }
}