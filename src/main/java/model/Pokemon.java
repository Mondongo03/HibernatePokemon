package model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numero")
    private int numero;

    @Column(name = "tipo_primario")
    private String tipoPrimario;

    @Column(name = "tipo_secundario")
    private String tipoSecundario;

    @Column(name = "habilidad")
    private String habilidad;

    @Column(name = "habilidad_oculta")
    private String habilidadOculta;

    @ManyToOne
    @JoinColumn(name = "objeto_equipado", referencedColumnName = "nombre")
    private Objeto objetoEquipado;

    @Column(name = "hp")
    private int hp;

    @Column(name = "ataque")
    private int ataque;

    @Column(name = "defensa")
    private int defensa;

    @Column(name = "velocidad")
    private int velocidad;

    @Column(name = "ataque_especial")
    private int ataqueEspecial;

    @Column(name = "defensa_especial")
    private int defensaEspecial;

    @ManyToMany
    @JoinTable(
            name = "pokemon_movimiento",
            joinColumns = @JoinColumn(name = "pokemon_nombre"),
            inverseJoinColumns = @JoinColumn(name = "movimiento_nombre")
    )
    private Set<Movimiento> movimientos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipoPrimario() {
        return tipoPrimario;
    }

    public void setTipoPrimario(String tipoPrimario) {
        this.tipoPrimario = tipoPrimario;
    }

    public String getTipoSecundario() {
        return tipoSecundario;
    }

    public void setTipoSecundario(String tipoSecundario) {
        this.tipoSecundario = tipoSecundario;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
    }

    public String getHabilidadOculta() {
        return habilidadOculta;
    }

    public void setHabilidadOculta(String habilidadOculta) {
        this.habilidadOculta = habilidadOculta;
    }

    public Objeto getObjetoEquipado() {
        return objetoEquipado;
    }

    public void setObjetoEquipado(Objeto objetoEquipado) {
        this.objetoEquipado = objetoEquipado;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public void setAtaqueEspecial(int ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public void setDefensaEspecial(int defensaEspecial) {
        this.defensaEspecial = defensaEspecial;
    }

    public Set<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Set<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public Pokemon() {
    }

    public Pokemon(String nombre, int numero, String tipoPrimario, String tipoSecundario, String habilidad, String habilidadOculta, Objeto objetoEquipado, int hp, int ataque, int defensa, int velocidad, int ataqueEspecial, int defensaEspecial, Set<Movimiento> movimientos) {
        this.nombre = nombre;
        this.numero = numero;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.habilidad = habilidad;
        this.habilidadOculta = habilidadOculta;
        this.objetoEquipado = objetoEquipado;
        this.hp = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.movimientos = movimientos;
    }
}
