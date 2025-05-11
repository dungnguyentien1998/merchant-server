package com.dungnt.util;

import de.huxhorn.sulky.ulid.ULID;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommonUtils {
    private static final ULID ulid = new ULID();

    public String generateULID() {
        return ulid.nextULID();
    }
}
