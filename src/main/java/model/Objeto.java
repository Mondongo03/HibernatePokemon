package model;

import javax.persistence.*;

@Entity
@Table(name = "objeto")
public class Objeto {
    /**
     * La clase Objeto representa un objeto en el sistema.
     *
     * @param nombre El nombre del objeto.
     * @param generacion La generación a la que pertenece el objeto.
     * @param precioCompra El precio de compra del objeto.
     * @param precioVenta El precio de venta del objeto.
     * @param tipo El tipo o categoría del objeto.
     */
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGeneracion() {
        return generacion;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Objeto() {
    }

    public Objeto(String nombre, String generacion, String precioCompra, String precioVenta, String tipo) {
        this.nombre = nombre;
        this.generacion = generacion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.tipo = tipo;
    }
}
