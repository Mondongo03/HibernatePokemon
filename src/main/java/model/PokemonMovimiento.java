package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pokemon_movimiento")
public class PokemonMovimiento implements Serializable {

    /**
     * La clase PokemonMovimiento es una tabla intermedia para las relaciones
     * @param id es el id del registro
     * @param pokemon hace referencia al join de la tabla pokemon
     * @param movimiento hace referencia al join de la tabla pokemon
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pokemon_nombre", referencedColumnName = "nombre")
    private Pokemon pokemon;


    @ManyToOne
    @JoinColumn(name = "movimiento_nombre", referencedColumnName = "nombre")
    private Movimiento movimiento;

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public PokemonMovimiento() {
    }

    public PokemonMovimiento(Long id, Pokemon pokemon, Movimiento movimiento) {
        this.id = id;
        this.pokemon = pokemon;
        this.movimiento = movimiento;
    }
}
