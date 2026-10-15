package com.nttdata.hooks;

import com.nttdata.support.ScreenshotAttacher;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.nttdata.screens.AppConfigScreen;

/**
 * Hooks globales de Cucumber: aplican a TODOS los escenarios, sin
 * importar el feature. Utiles para dar contexto en consola al
 * aprendiz sobre en que punto va la ejecucion.
 */
public class Hooks {

    @Before
    public void antesDelEscenario(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("[Hooks] Iniciando escenario: " + scenario.getName());
        System.out.println("========================================");
    }

    @After
    public void despuesDelEscenario(Scenario scenario) {

        String estado = scenario.isFailed() ? "FALLIDO" : "EXITOSO";

        System.out.println(
                "[Hooks] Escenario '" + scenario.getName()
                        + "' finalizo: " + estado
        );

        // Primero guardamos la evidencia del estado final
        String nombreEvidencia =
                scenario.isFailed() ? "Estado final (FALLO)" : "Estado final";

        ScreenshotAttacher.attach(scenario, nombreEvidencia);

        // Para los escenarios del carrito siempre cerramos la sesion,
        // incluso cuando el escenario falla antes del ultimo step.
        if (scenario.getSourceTagNames().contains("@SauceLabsCart")) {

            AppConfigScreen app = new AppConfigScreen();

            app.cerrarAplicacion(
                    "com.saucelabs.mydemoapp.android"
            );
        }
    }
}
