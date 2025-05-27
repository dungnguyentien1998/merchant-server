package com.dungnt.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@ApplicationScoped
public class SecretReader {
    private static final Logger LOG = Logger.getLogger(SecretReader.class);

    public Properties readSecretFile() {
        LOG.info("env: " + System.getenv("QUARKUS_PROFILE"));
        try {
            String path = System.getenv("QUARKUS_PROFILE") != null && System.getenv("QUARKUS_PROFILE").equals("prod")
                ? "/etc/secrets/secrets.properties"
                : "./secrets.properties";
            Properties props = new Properties();
            props.load(Files.newInputStream(Paths.get(path)));
            return props;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read secret file", e);
        }
    }
}