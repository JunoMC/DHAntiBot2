package xyz.junomc.dhantibot.utils;

import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DiHoaUtils {
    private AntiBotUtils antiBotUtils;
    private String name = antiBotUtils.w(68, 72, 45, 65, 110, 116, 105, 66, 111, 116);
    private String dihoaUrl = antiBotUtils.w(104, 116, 116, 112, 115, 58, 47, 47, 100, 105, 104, 111, 97, 115, 116, 111, 114, 101, 45, 109, 99, 46, 116, 107, 47);
    private RequestUtils requestUtils;

    public DiHoaUtils() {
        this.requestUtils = new RequestUtils();
        this.antiBotUtils = new AntiBotUtils();
    }

    public JsonObject checkLicense(String license) {
        String address   = getServerIP();
        String signature = hash(license + this.antiBotUtils.w(58, 58, 118, 50, 58, 58) + address);
        return this.requestUtils.response(dihoaUrl + this.antiBotUtils.w(118, 50, 47, 108, 105, 99, 101, 110, 115, 101, 47) + signature + this.antiBotUtils.w(47) + license + this.antiBotUtils.w(47) + this.name + this.antiBotUtils.w(47) + address);
    }

    public JsonObject activeLicense(String license) {
        String address   = getServerIP();
        String signature = hash(license + this.antiBotUtils.w(58, 58, 118, 50, 58, 58) + address);
        return this.requestUtils.response(dihoaUrl +  this.antiBotUtils.w(118, 50, 47, 97, 99, 116, 105, 118, 101, 76, 105, 99, 101, 110, 115, 101, 47) + signature + this.antiBotUtils.w(47) + license + this.antiBotUtils.w(47) + this.name + this.antiBotUtils.w(47) + address);
    }

    public JsonObject inactiveLicense(String license) {
        String address   = getServerIP();
        String signature = hash(license + this.antiBotUtils.w(58, 58, 118, 50, 58, 58) + address);
        return this.requestUtils.response(dihoaUrl +  this.antiBotUtils.w(118, 50, 47, 105, 110, 97, 99, 116, 105, 118, 101, 76, 105, 99, 101, 110, 115, 101, 47) + signature + this.antiBotUtils.w(47) + license + this.antiBotUtils.w(47) + this.name + this.antiBotUtils.w(47) + address);
    }

    public String lastest() {
        JsonObject object = this.requestUtils.response(dihoaUrl +  this.antiBotUtils.w(118, 50, 47, 108, 97, 115, 116, 101, 115, 116, 47, 112, 108, 117, 103, 105, 110, 47) + this.name + this.antiBotUtils.w(47));
        return object.get(this.antiBotUtils.w(118, 101, 114, 115, 105, 111, 110)).getAsString();
    }

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(this.antiBotUtils.w(77, 68, 53));
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32)
                hashtext = this.antiBotUtils.w(48) + hashtext;
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getServerIP() {
        JsonObject object = this.requestUtils.response(this.antiBotUtils.w(104, 116, 116, 112, 115, 58, 47, 47, 97, 112, 105, 46, 105, 112, 105, 102, 121, 46, 111, 114, 103, 63, 102, 111, 114, 109, 97, 116, 61, 106, 115, 111, 110));
        return object.get(this.antiBotUtils.w(105, 112)).getAsString();
    }

    public JsonObject isIPv4(String license) {
        String address   = getServerIP();
        String signature = hash(license + this.antiBotUtils.w(58, 58, 118, 50, 58, 58) + address);
        return this.requestUtils.response(dihoaUrl +  this.antiBotUtils.w(118, 50, 47, 105, 115, 73, 80, 118, 52, 47) + signature + this.antiBotUtils.w(47) + license + this.antiBotUtils.w(47) + this.name + this.antiBotUtils.w(47) + address);
    }
}