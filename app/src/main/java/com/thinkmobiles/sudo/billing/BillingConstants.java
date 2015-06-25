package com.thinkmobiles.sudo.billing;

/**
 * Created by omar on 25.06.15.
 */
public class BillingConstants {
    public static final String SKU_CREDITS10 = "credits10";
    public static final String SKU_CREDITS20 = "credits20";
    public static final String SKU_CREDITS30 = "credits30";
    public static final String SKU_CREDITS40 = "credits40";


    public static final int RC_REQUEST = 10001;


    public static final String key_part1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsjfBHu9QIKPU4U4kkolM1UZ7DuYSKr65aI4EcO24Qr0u4JfqyfKlde0LSLLFagOjZlY";
    public static final String key_part2 = "Y1aoIBiyOCgd0Fm9EygC2idZPHz39is7PTq5KUsKv0FHTTwPEBdSRceEuLOQkKYrBchf+A5bxtpMPKyT2Zt2klnnrUJhSqAP+OVFSRViP944BQw3t";
    public static final String key_part3 = "QIiq3zOLMymBKavnlNKTaqoAuysRVnncb5Rrkb2riIqMGtxbMJSFL93ujV7J1oq3HrIKBF73MADNhJypz9cAMW5Cv";
    public static final String key_part4 = "XgSS6BFHJ2aEJOFa8cXS/wiiKl32jcW1xfBgqjtYLF0i6Q3XdcmTdXTdJCZpkQM7++HPGGV9QIDAQAB";


    public static String base64EncodedPublicKey = key_part1 + key_part2 + key_part3 + key_part4;

}
