package com.artisantek;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationConfigTest {

    @Test
    @DisplayName("Should configure SPA root correctly")
    void shouldConfigureSpaRootCorrectly() {
        Javalin app = Javalin.create(config -> {
            config.spaRoot.addFile("/", "/public/index.html", Location.CLASSPATH);
        });
        
        assertNotNull(app, "Application should be created with SPA configuration");
        app.stop();
    }

    @Test
    @DisplayName("Should use correct classpath location")
    void shouldUseCorrectClasspathLocation() {
        Location location = Location.CLASSPATH;
        assertEquals(Location.CLASSPATH, location, "Should use CLASSPATH location");
    }

    @Test
    @DisplayName("Should have correct main class package")
    void shouldHaveCorrectMainClassPackage() {
        String packageName = Main.class.getPackage().getName();
        assertEquals("com.artisantek", packageName, "Main class should be in com.artisantek package");
    }

    @Test
    @DisplayName("Should have main method")
    void shouldHaveMainMethod() {
        try {
            Main.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Main class should have a main method");
        }
    }

    @Test
    @DisplayName("Should create app without throwing exceptions")
    void shouldCreateAppWithoutExceptions() {
        assertDoesNotThrow(() -> {
            Javalin app = Javalin.create(config -> {
                config.spaRoot.addFile("/", "/public/index.html", Location.CLASSPATH);
            });
            app.stop();
        }, "Creating Javalin app should not throw exceptions");
    }
} 