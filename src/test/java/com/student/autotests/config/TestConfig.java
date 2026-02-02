package com.student.autotests.config;

public final class TestConfig {

    private TestConfig() {
    }

    public static String webBaseUrl() {
        return System.getProperty("webBaseUrl", "https://en.wikipedia.org");
    }

    public static String appiumServerUrl() {
        return System.getProperty("appiumServerUrl", "http://127.0.0.1:4723");
    }

    public static String deviceName() {
        return System.getProperty("deviceName", "Android Emulator");
    }

    public static String platformVersion() {
        return System.getProperty("platformVersion", "");
    }

    public static String browser() {
        return System.getProperty("browser", "chrome");
    }

    public static String udid() {
        return System.getProperty("udid", "");
    }

    public static String appPackage() {
        return System.getProperty("appPackage", "org.wikipedia.alpha");
    }

    public static String appActivity() {
        return System.getProperty("appActivity", "org.wikipedia.DefaultIcon");
    }

}
