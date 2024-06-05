package com.example.automate;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AllureUtils {
    public static String takeScreenshot(WebDriver driver, String name) {
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        return Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes), "image/png");
    }

    public static String addTextAttachment(String name, String text) {
        return Allure.addAttachment(name, new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), "text/plain");
    }

    public static String addJsonAttachment(String name, String json) {
        return Allure.addAttachment(name, new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), "application/json");
    }

    public static String addAttachment(String name, String type, String content) {
        return Allure.addAttachment(name, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), type);
    }

    public static void addStep(String name, Status status) {
        Allure.step(name, status);
    }

    public static void addStep(String name, Runnable runnable) {
        Allure.step(name, runnable);
    }

    public static String startStep(String name) {
        String uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().startStep(uuid, new io.qameta.allure.model.StepResult().setName(name));
        return uuid;
    }

    public static void stopStep() {
        Allure.getLifecycle().stopStep();
    }

    public static void stopStep(Status status) {
        Allure.getLifecycle().updateStep(step -> step.setStatus(status));
        Allure.getLifecycle().stopStep();
    }

    public static void stopStep(String uuid, Status status) {
        Allure.getLifecycle().updateStep(uuid, step -> step.setStatus(status));
        Allure.getLifecycle().stopStep(uuid);
    }

    private static String determineExtension(String mimeType) {
        switch (mimeType) {
            case "text/plain": return "txt";
            case "application/json": return "json";
            case "application/xml": return "xml";
            case "text/html": return "html";
            case "text/csv": return "csv";
            case "image/png": return "png";
            case "image/jpeg": return "jpg";
            case "image/gif": return "gif";
            case "image/bmp": return "bmp";
            default: return "bin";
        }
    }
}