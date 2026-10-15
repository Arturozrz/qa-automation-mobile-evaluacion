package com.nttdata.stepsdefinitions;

import com.nttdata.steps.SauceLabsProductsSteps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import static org.junit.Assert.assertTrue;

public class SauceLabsProductsStepDefs {

    private final SauceLabsProductsSteps steps =
            new SauceLabsProductsSteps();

    // Guarda la cantidad del Example actual para validarla en el Then
    private int unidadesEsperadas;

    private int cantidadInicial;


    @Y("valido que carguen correctamente los productos en la galeria")
    public void validarCargaProductos() {

        assertTrue(
                "La galeria de productos no se cargo correctamente",
                steps.validarGaleriaProductos()
        );
    }


    @Cuando("agrego {int} del siguiente producto {string}")
    public void agregarProducto(int unidades, String producto) {

        // Guardamos cuántos productos había antes de agregar
        cantidadInicial = steps.obtenerCantidadCarrito();

        unidadesEsperadas = unidades;

        System.out.println(
                "[SauceLabsProductsStepDefs] Cantidad inicial del carrito: "
                        + cantidadInicial
        );

        steps.agregarProducto(producto, unidades);
    }


    @Entonces("valido el carrito de compra actualice correctamente")
    public void validarCarritoCompra() {

        int cantidadEsperada = cantidadInicial + unidadesEsperadas;
        int cantidadActual = steps.obtenerCantidadCarrito();

        System.out.println(
                "[SauceLabsProductsStepDefs] Cantidad esperada: "
                        + cantidadEsperada
                        + " | Cantidad actual: "
                        + cantidadActual
        );

        assertTrue(
                "El carrito no se actualizo correctamente. "
                        + "Esperado: " + cantidadEsperada
                        + " | Actual: " + cantidadActual,
                cantidadActual == cantidadEsperada
        );
    }
}