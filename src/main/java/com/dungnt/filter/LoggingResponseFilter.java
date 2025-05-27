package com.dungnt.filter;

import com.dungnt.constant.CommonConstants;
import com.dungnt.service.SecretReader;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import java.io.IOException;
import java.util.Properties;

@Provider
public class LoggingResponseFilter implements ContainerResponseFilter {

    @Inject
    SecretReader secretReader;

    private static final Logger LOG = Logger.getLogger(LoggingResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Properties props = secretReader.readSecretFile();

        // Add custom response header
        responseContext.getHeaders().add(CommonConstants.SIG, props.getProperty("header", "not-set"));

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