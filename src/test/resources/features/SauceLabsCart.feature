# language: es

@SauceLabsCart
Característica: Validar carrito de compras en Sauce Labs My Demo App

  Como usuario de la tienda móvil
  Quiero agregar productos al carrito
  Para validar que el carrito se actualice correctamente

  Esquema del escenario: Agregar productos al carrito de compras

    Dado ingreso al aplicativo "saucedemo"
    Y valido que carguen correctamente los productos en la galeria
    Cuando agrego <UNIDADES> del siguiente producto "<PRODUCTO>"
    Entonces valido el carrito de compra actualice correctamente


    Ejemplos:
      | PRODUCTO                  | UNIDADES |
      | Sauce Labs Backpack       | 1        |
      | Sauce Labs Bolt - T-Shirt | 1        |
      | Sauce Labs Bike Light     | 2        |