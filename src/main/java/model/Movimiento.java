package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "movimiento")
public class Movimiento {
    /**
     * La clase movimento es la entity de movmiento en la base de datos
     * @param nombre es el nombre del movimiento
     * @param tipo es el tipo del movimiento
     * @param categoria es el tipo de movmiento o categoria del movimiento
     * @param poder es la fuerza del movimiento
     * @param pp es los usos del movimiento
     * @param precision es el porcentaje de acertar del movimiento
     * @param descripcion es la descripcion del movimiento
     */
    @Id
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "poder")
    private int poder;

    @Column(name = "pp")
    private int pp;

    @Column(name = "precision")
    private String precision;

    @Column(name = "descripcion")
        private String descripcion;

    public Movimiento(String nombre, String tipo, String categoria, int poder, int pp, String precision, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = categoria;
        this.poder = poder;
        this.pp = pp;
        this.precision = precision;
        this.descripcion = descripcion;
    }

    public Movimiento() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
