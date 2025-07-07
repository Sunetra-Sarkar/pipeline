package com.artisantek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class ResourcesTest {

    @Test
    @DisplayName("Should have index.html in public resources")
    void shouldHaveIndexHtmlInPublicResources() {
        InputStream resource = getClass().getResourceAsStream("/public/index.html");
        assertNotNull(resource, "public/index.html should exist in resources");
    }

    @Test
    @DisplayName("Should have index.html in templates resources")
    void shouldHaveIndexHtmlInTemplatesResources() {
        InputStream resource = getClass().getResourceAsStream("/templates/index.html");
        assertNotNull(resource, "templates/index.html should exist in resources");
    }

    @Test
    @DisplayName("Public index.html should contain expected content")
    void publicIndexHtmlShouldContainExpectedContent() {
        try (InputStream resource = getClass().getResourceAsStream("/public/index.html")) {
            assertNotNull(resource, "public/index.html should exist");
            
            String content = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(content.contains("ArtisanTek"), "Should contain 'ArtisanTek'");
            assertTrue(content.contains("May2025"), "Should contain 'May2025'");
            assertTrue(content.contains("Built with Modern Technology"), "Should contain technology section");
            assertTrue(content.contains("port 5000"), "Should mention port 5000");
        } catch (Exception e) {
            fail("Failed to read public/index.html: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Templates index.html should contain Thymeleaf attributes")
    void templatesIndexHtmlShouldContainThymeleafAttributes() {
        try (InputStream resource = getClass().getResourceAsStream("/templates/index.html")) {
            assertNotNull(resource, "templates/index.html should exist");
            
            String content = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(content.contains("th:text"), "Should contain Thymeleaf th:text attributes");
            assertTrue(content.contains("xmlns:th"), "Should contain Thymeleaf namespace declaration");
        } catch (Exception e) {
            fail("Failed to read templates/index.html: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Both HTML files should be valid HTML")
    void bothHtmlFilesShouldBeValidHtml() {
        String[] htmlFiles = {"/public/index.html", "/templates/index.html"};
        
        for (String htmlFile : htmlFiles) {
            try (InputStream resource = getClass().getResourceAsStream(htmlFile)) {
                assertNotNull(resource, htmlFile + " should exist");
                
                String content = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
                assertTrue(content.contains("<!DOCTYPE html>"), htmlFile + " should have DOCTYPE declaration");
                assertTrue(content.contains("<html"), htmlFile + " should have html tag");
                assertTrue(content.contains("</html>"), htmlFile + " should have closing html tag");
                assertTrue(content.contains("<head>"), htmlFile + " should have head section");
                assertTrue(content.contains("<body>"), htmlFile + " should have body section");
            } catch (Exception e) {
                fail("Failed to validate " + htmlFile + ": " + e.getMessage());
            }
        }
    }
} 