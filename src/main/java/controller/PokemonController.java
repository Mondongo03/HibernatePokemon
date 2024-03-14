package controller;

import model.Author;
import model.Movimiento;
import model.Pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PokemonController {
    private EntityManagerFactory entityManagerFactory;

    public PokemonController() { }

    public PokemonController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Author> readAuthorsFile(String filename) throws IOException {
        int id;
        String name, year, country;
        boolean active;
        List<Author> authorsList = new ArrayList<Author>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linea = "";
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            id = Integer.parseInt(str.nextToken());
            name = str.nextToken();
            year = str.nextToken();
            country = str.nextToken();
            active = Boolean.parseBoolean(str.nextToken());
            // System.out.println(id + name + country + year + active);
            authorsList.add(new Author(id, name, country, year, active));
        }
        br.close();

        return authorsList;
    }

    public void printAutors(ArrayList<Author> authorsList) {
        for (int i = 0; i < authorsList.size(); i++) {
            System.out.println(authorsList.get(i).toString());
        }
    }

    /* Method to CREATE an Author in the database */
    public void addAuthor(Author author) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Author authotExists = (Author) em.find(Author.class, author.getAuthorId());
        if (authotExists == null) {
            System.out.println("insert autor");
            em.persist(author);
        }
        em.getTransaction().commit();
        em.close();
    }

    /* Method to READ all Authors */
    public void listarPokemons() {
        int i = 0;
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Pokemon> listaPokemon = em.createQuery("SELECT DISTINCT p FROM Pokemon p JOIN FETCH p.movimientos ORDER BY p.numero", Pokemon.class).getResultList();
        for (Pokemon pokemon : listaPokemon) {
            System.out.print(pokemon.getNombre().toString()+", ");
            System.out.print(pokemon.getNumero()+", ");
            System.out.print(pokemon.getTipoPrimario().toString()+", ");
            System.out.print(pokemon.getTipoSecundario().toString()+", ");
            System.out.print(pokemon.getHabilidad().toString()+", ");
            System.out.print(pokemon.getHabilidadOculta().toString()+", ");
            System.out.print(pokemon.getObjetoEquipado().getNombre()+", ");
            System.out.print(pokemon.getHp()+", ");
            System.out.print(pokemon.getAtaque()+", ");
            System.out.print(pokemon.getDefensa()+", ");
            System.out.print(pokemon.getVelocidad()+", ");
            System.out.print(pokemon.getAtaqueEspecial()+", ");
            System.out.print(pokemon.getDefensaEspecial());
            for (Movimiento movimiento : pokemon.getMovimientos()) {
                System.out.print(", "+movimiento.getNombre());
            }
            System.out.println();
            i++;
        }
        System.out.println(i+" Pokemons listados");
        em.getTransaction().commit();
        em.close();
    }

    /* Method to UPDATE activity for an Author */
    public void updateAutor(Integer authorId, boolean active) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Author author = (Author) em.find(Author.class, authorId);
        author.setActive(active);
        em.merge(author);
        em.getTransaction().commit();
        em.close();
    }

    /* Method to DELETE an Author from the records */
    public void eliminarPokemon(String nombrePokemon) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Pokemon pokemon = (Pokemon) em.find(Pokemon.class, nombrePokemon);
        em.remove(pokemon);
        em.getTransaction().commit();
        em.close();
    }
}