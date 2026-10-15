package com.nttdata.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner principal para ejecutar la prueba
 * del carrito de Sauce Labs My Demo App.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.nttdata.stepsdefinitions",
                "com.nttdata.hooks"
        },
        tags = "@SauceLabsCart",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json"
        },
        monochrome = true
)
public class CucumberTestSuite {

}
