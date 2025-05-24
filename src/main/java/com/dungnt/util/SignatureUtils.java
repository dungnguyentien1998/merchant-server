package com.dungnt.util;

import com.dungnt.constant.CommonConstants;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SignatureUtils {
    private static final Logger LOG = Logger.getLogger(SignatureUtils.class);

    @ConfigProperty(name = "variable", defaultValue = "DefaultVariable")
    private static String variable;
    public static String signMessage(String message) throws Exception {
        // Giải mã private key từ Base64
        byte[] privateKeyBytes = Base64.getDecoder().decode(variable);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CommonConstants.KALG);
        PrivateKey privKey = keyFactory.generatePrivate(keySpec);

        // Khởi tạo đối tượng Signature để ký
        Signature signature = Signature.getInstance(CommonConstants.ALG);
        signature.initSign(privKey);
        signature.update(message.getBytes("UTF-8"));

        // Tạo chữ ký
        byte[] signatureBytes = signature.sign();

        // Mã hóa chữ ký dưới dạng Base64
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

}
