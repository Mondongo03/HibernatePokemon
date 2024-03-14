package model;

import javax.persistence.*;

@Entity
@Table(name = "objeto")
public class Objeto {

    @Id
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "generacion")
    private String generacion;

    @Column(name = "precio_compra")
    private String precioCompra;

    @Column(name = "precio_venta")
    private String precioVenta;

    @Column(name = "tipo")
    private String tipo;

    // Constructor, getters y setters
}
