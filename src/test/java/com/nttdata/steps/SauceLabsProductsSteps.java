package com.nttdata.steps;

import com.nttdata.screens.SauceLabsProductsScreen;

public class SauceLabsProductsSteps {

    private final SauceLabsProductsScreen screen =
            new SauceLabsProductsScreen();

    public boolean validarGaleriaProductos() {

        System.out.println(
                "[SauceLabsProductsSteps] Validando carga de productos..."
        );

        return screen.galeriaEstaVisible();
    }


    public void agregarProducto(String producto, int unidades) {

        System.out.println(
                "[SauceLabsProductsSteps] Agregando producto: "
                        + producto + " - Unidades: " + unidades
        );

        screen.seleccionarProducto(producto);
        screen.seleccionarCantidad(unidades);
        screen.agregarAlCarrito();
    }


    public int obtenerCantidadCarrito() {
        return screen.obtenerCantidadCarrito();
    }
}