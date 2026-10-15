package com.nttdata.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.Duration;

public class SauceLabsProductsScreen {

    // Tiempo máximo para esperar los elementos
    private static final Duration TIMEOUT = Duration.ofSeconds(20);

    // Elementos de la aplicación
    private final By tituloProducto =
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV");

    private final By btnAumentarCantidad =
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/plusIV");

    private final By btnAgregarCarrito =
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt");

    private final By cantidadCarrito =
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV");


    // Obtiene el driver que ya fue creado por el framework
    private AndroidDriver driver() {
        return AppConfigScreen.getDriver();
    }


    // Espera explícita reutilizable para los elementos de esta pantalla
    private WebElement esperarElemento(By locator) {

        return new WebDriverWait(driver(), TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    public boolean galeriaEstaVisible() {

        System.out.println(
                "[SauceLabsProductsScreen] Validando galeria de productos..."
        );

        // Si encontramos el titulo de un producto,
        // consideramos que la galeria cargó correctamente
        return esperarElemento(tituloProducto).isDisplayed();
    }


    public void seleccionarProducto(String producto) {

        System.out.println(
                "[SauceLabsProductsScreen] Buscando producto: " + producto
        );

        // El nombre recibido desde el feature no coincide exactamente
        // con el texto mostrado por la aplicación para Bolt T-Shirt.
        String nombreEnApp = producto.replace(" - ", " ");

        By productoLocator =
                AppiumBy.accessibilityId(nombreEnApp);

        WebElement elemento =
                buscarConScroll(productoLocator);

        elemento.click();

        System.out.println(
                "[SauceLabsProductsScreen] Producto seleccionado: " + nombreEnApp
        );

        // Validamos que después del clic la aplicación siga abierta.
        String paqueteActual = driver().getCurrentPackage();

        if (!"com.saucelabs.mydemoapp.android".equals(paqueteActual)) {

            throw new AssertionError(
                    "La aplicacion se cerro o paso a segundo plano "
                            + "al seleccionar el producto: " + producto
            );
        }
    }


    public void seleccionarCantidad(int unidades) {

        System.out.println(
                "[SauceLabsProductsScreen] Cantidad solicitada: " + unidades
        );

        for (int i = 1; i < unidades; i++) {

            WebElement botonMas =
                    buscarConScroll(btnAumentarCantidad);

            botonMas.click();

            System.out.println(
                    "[SauceLabsProductsScreen] Se aumento una unidad"
            );
        }
    }


    public void agregarAlCarrito() {

        System.out.println(
                "[SauceLabsProductsScreen] Buscando boton Add to cart..."
        );

        WebElement boton =
                buscarConScroll(btnAgregarCarrito);

        boton.click();

        System.out.println(
                "[SauceLabsProductsScreen] Producto agregado al carrito"
        );
    }

    // Realiza un desplazamiento hacia abajo en la pantalla
    private void hacerScroll() {

        Dimension pantalla = driver().manage().window().getSize();

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("left", pantalla.width / 10);
        parametros.put("top", pantalla.height / 4);
        parametros.put("width", pantalla.width * 8 / 10);
        parametros.put("height", pantalla.height / 2);
        parametros.put("direction", "up");
        parametros.put("percent", 0.70);

        driver().executeScript(
                "mobile: swipeGesture",
                parametros
        );
    }


    // Busca un elemento y, si no aparece,
// hace scroll varias veces hasta encontrarlo
    private WebElement buscarConScroll(By locator) {

        for (int intento = 1; intento <= 4; intento++) {

            List<WebElement> elementos =
                    driver().findElements(locator);

            if (!elementos.isEmpty() &&
                    elementos.get(0).isDisplayed()) {

                return elementos.get(0);
            }

            System.out.println(
                    "[SauceLabsProductsScreen] Elemento no visible. Scroll "
                            + intento
            );

            hacerScroll();
        }

        throw new NoSuchElementException(
                "No se encontro el elemento despues de realizar scroll"
        );
    }

    public int obtenerCantidadCarrito() {

        // Si el carrito no tiene badge, consideramos cantidad 0
        List<WebElement> elementos =
                driver().findElements(cantidadCarrito);

        if (elementos.isEmpty()) {
            return 0;
        }

        String texto = elementos.get(0).getText();

        if (texto == null || texto.isBlank()) {
            return 0;
        }

        return Integer.parseInt(texto);
    }
}