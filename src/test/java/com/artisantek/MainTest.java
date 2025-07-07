package com.artisantek;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private Javalin app;

    @BeforeEach
    void setUp() {
        // Use the actual Main.createApp() method for testing
        app = Main.createApp();
    }

    @AfterEach
    void tearDown() {
        if (app != null) {
            app.stop();
        }
    }

    @Test
    @DisplayName("Should create app using Main.createApp() method")
    void shouldCreateAppUsingMainCreateAppMethod() {
        Javalin testApp = Main.createApp();
        assertNotNull(testApp, "Main.createApp() should return a non-null Javalin instance");
        testApp.stop(); // Clean up
    }

    @Test
    @DisplayName("Should create Javalin app successfully")
    void shouldCreateJavalinApp() {
        assertNotNull(app, "Javalin app should be created successfully");
    }

    @Test
    @DisplayName("Should serve index.html on root path")
    void shouldServeIndexHtmlOnRoot() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertEquals(200, response.code(), "Should return 200 OK status");
            String responseBody = response.body().string();
            assertTrue(responseBody.contains("ArtisanTek"), 
                "Response should contain 'ArtisanTek' text");
            assertTrue(responseBody.contains("May2025"), 
                "Response should contain 'May2025' text");
        });
    }

    @Test
    @DisplayName("Should return HTML content type for root path")
    void shouldReturnHtmlContentType() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            String contentType = response.header("Content-Type");
            assertNotNull(contentType, "Content-Type header should be present");
            assertTrue(contentType.contains("text/html"), 
                "Content-Type should be text/html");
        });
    }

    @Test
    @DisplayName("Should handle favicon requests")
    void shouldHandleFaviconRequests() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/favicon.ico");
            // SPA configuration serves index.html for all routes, including favicon
            assertEquals(200, response.code(), "Should return 200 for favicon (SPA behavior)");
            String responseBody = response.body().string();
            assertTrue(responseBody.contains("ArtisanTek"), 
                "Should serve index.html content for favicon request");
        });
    }

    @Test
    @DisplayName("Should handle non-existent routes")
    void shouldHandleNonExistentRoutes() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/nonexistent");
            // Should return the SPA root (index.html) for any non-existent route
            assertEquals(200, response.code(), "Should return 200 for SPA routing");
            String responseBody = response.body().string();
            assertTrue(responseBody.contains("ArtisanTek"), 
                "Should serve index.html for non-existent routes");
        });
    }
} 