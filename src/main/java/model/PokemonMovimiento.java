package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pokemon_movimiento")
public class PokemonMovimiento implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pokemon_nombre", referencedColumnName = "nombre")
    private Pokemon pokemon;

    @Id
    @ManyToOne
    @JoinColumn(name = "movimiento_nombre", referencedColumnName = "nombre")
    private Movimiento movimiento;

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public PokemonMovimiento() {
    }

    public PokemonMovimiento(Pokemon pokemon, Movimiento movimiento) {
        this.pokemon = pokemon;
        this.movimiento = movimiento;
    }
}
