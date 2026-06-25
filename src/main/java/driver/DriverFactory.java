package driver;

import configuration.Configuration;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static AndroidDriver getAndroidDriver()
            throws MalformedURLException {

        UiAutomator2Options options = buildAndroidOptions();

        return new AndroidDriver(
                URI.create(
                                Configuration.get("appium.server.url"))
                        .toURL(),
                options
        );
    }

    private static UiAutomator2Options buildAndroidOptions() {

        UiAutomator2Options options =
                new UiAutomator2Options();

        // Platform
        options.setPlatformName(
                Configuration.get("platform.name"));

        options.setAutomationName(
                Configuration.get("automation.name"));

        // Device
        options.setDeviceName(
                Configuration.get("device.name"));

        // Optional
        String platformVersion =
                Configuration.get("platform.version");

        if (platformVersion != null
                && !platformVersion.isBlank()) {

            options.setPlatformVersion(
                    platformVersion);
        }

        // App
        options.setAppPackage(
                Configuration.get("app.package"));

        options.setAppActivity(
                Configuration.get("app.activity"));

        // Session
        options.setNoReset(
                Configuration.getBoolean("no.reset"));

        if (Configuration.getBoolean(
                "auto.grant.permissions")) {

            options.autoGrantPermissions();
        }

// Stability
        options.setCapability(
                "ignoreHiddenApiPolicyError",
                Configuration.getBoolean(
                        "ignore.hidden.api.policy.error"));

        options.setCapability(
                "disableWindowAnimation",
                Configuration.getBoolean(
                        "disable.window.animation"));

        options.setNewCommandTimeout(
                Duration.ofSeconds(
                        Configuration.getInt(
                                "new.command.timeout"
                        ), 300));

        // Optional wait activity
        String waitActivity =
                Configuration.get("app.wait.activity");

        if (waitActivity != null
                && !waitActivity.isBlank()) {

            options.setAppWaitActivity(
                    waitActivity);
        }

        return options;
    }
}