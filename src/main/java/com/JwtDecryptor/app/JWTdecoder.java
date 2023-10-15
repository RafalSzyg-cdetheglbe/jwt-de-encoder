package com.JwtDecryptor.app;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTdecoder {
    public static String decodeJWT(String jwtToken) {
        String[] jwtParts = jwtToken.split("\\.");
        if (jwtParts.length != 3) {
            return "Invalid JWT format";
        }

        String jwtHeader = new String(Base64.getUrlDecoder().decode(jwtParts[0]), StandardCharsets.UTF_8);
        String jwtPayload = new String(Base64.getUrlDecoder().decode(jwtParts[1]), StandardCharsets.UTF_8);

        JSONObject headerObject = new JSONObject(jwtHeader);
        JSONObject payloadObject = new JSONObject(jwtPayload);

        return headerObject.toString(4) + "\n" + payloadObject.toString(4);
    }
}
