package controller;

import model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controller de la clase Pokemon
 */
public class PokemonController {
    private EntityManagerFactory entityManagerFactory;

    public PokemonController() { }

    public PokemonController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * El método listarPokemons lo que hace es una query a la bbdd de todos los pokemons y luego va printando sus atributos en un foreach de la list creada en base a la query
     */
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

    /**
     * El método poblarPokemonCsv lo que hace es abrir una ventana del explodor de ficheros para elegir un csv que con BufferReader irá rellenando todos los campos de los pokemons linea por linea para luego hacer el merge de cada pokemon en bucle en la bbdd
     */
    public void poblarPokemonCsv(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Objeto> listaObjeto = em.createQuery("from Objeto", Objeto.class).getResultList();
        List<Movimiento> listaMovimiento = em.createQuery("from Movimiento", Movimiento.class).getResultList();
        Objeto objetoComparado = null;
        JFileChooser fileChooser = new JFileChooser();

        // Configurar el filtro para mostrar solo archivos con extensión CSV
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filter);
        File csvFile = null;
        // Mostrar la ventana de selección de archivos
        int result = fileChooser.showOpenDialog(null);

        // Verificar si el usuario seleccionó un archivo
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            csvFile = fileChooser.getSelectedFile();
            System.out.println("Archivo seleccionado: " + csvFile.getAbsolutePath());
        } else {
            System.out.println("No se seleccionó ningún archivo.");
            return;
        }
        // Leer el archivo CSV
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String[] header = br.readLine().split(cvsSplitBy); // Leer el encabezado del CSV

            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);

                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replaceAll("^\"|\"$", ""); // Eliminar comillas al principio y al final
                }
                // Crear un objeto Objeto y asignar los valores desde el CSV
                Pokemon pokemon = new  Pokemon();
                pokemon.setNombre(data[0]);
                pokemon.setNumero(Integer.parseInt(data[1]));
                pokemon.setTipoPrimario(data[2]);// Eliminar espacios en blanco
                pokemon.setTipoSecundario(data[3]); // Convertir la cadena a un número entero
                pokemon.setHabilidad(data[4]);
                pokemon.setHabilidadOculta(data[5]);
                objetoComparado = null;
                for (Objeto objeto : listaObjeto) {
                   if (objeto.getNombre().equals(data[6])){
                       objetoComparado = objeto;
                       break;
                   }
                }
                pokemon.setObjetoEquipado(objetoComparado);
                pokemon.setHp(Integer.parseInt(data[7]));
                pokemon.setAtaque(Integer.parseInt(data[8]));
                pokemon.setDefensa(Integer.parseInt(data[9]));
                pokemon.setVelocidad(Integer.parseInt(data[10]));
                pokemon.setAtaqueEspecial(Integer.parseInt(data[11]));
                pokemon.setDefensaEspecial(Integer.parseInt(data[12]));

                System.out.println(pokemon.getNombre());
                em.merge(pokemon);

                for (int i = 0; i < 4; i++) {
                    PokemonMovimiento pokemonMovimiento = new PokemonMovimiento();
                    pokemonMovimiento.setPokemon(pokemon);

                    Movimiento movimiento = null;
                    for (Movimiento mov : listaMovimiento) {
                        if (mov.getNombre().equals(data[13 + i])) {
                            movimiento = mov;
                            break;
                        }
                    }
                        pokemonMovimiento.setMovimiento(movimiento);
                        em.merge(pokemonMovimiento);
                    }
            }
            em.getTransaction().commit();
            em.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * El método eliminarPokemon lo que hace hacer una query con el nombrePokemon y luego printar sus atributos
     * @param nombrePokemon es el nombre del pokemon que quieres ver sus datos
     */
    public void eliminarPokemon(String nombrePokemon) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        // Buscar los PokemonMovimiento asociados al Pokemon con el nombre especificado
        Query query = em.createQuery("SELECT pm FROM PokemonMovimiento pm WHERE pm.pokemon.nombre = :nombrePokemon");
        query.setParameter("nombrePokemon", nombrePokemon);
        List<PokemonMovimiento> pokemonMovimientos = query.getResultList();
        // Eliminar cada PokemonMovimiento encontrado
        for (PokemonMovimiento pokemonMovimiento : pokemonMovimientos) {
            em.remove(pokemonMovimiento);
        }

        em.getTransaction().commit();
        em.getTransaction().begin();
        Pokemon pokemon = em.find(Pokemon.class, nombrePokemon);
        System.out.println(pokemon.getNombre());
        em.remove(pokemon);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * El método PokemonPorTipo lo que hace es hacer una query con el tipo (los pokemons que tengan uno de los 2 tipos == tipo) para en base al resultado de la query en un bucle foreach ir eliminadolos
     * @param tipo es el tipo del pokemon, ya sea primario o secundario
     */
    public void eliminarPokemonPorTipo(String tipo) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("SELECT p FROM Pokemon p WHERE p.tipoPrimario = :tipo OR p.tipoSecundario = :tipo");
        query.setParameter("tipo", tipo);
        List<Pokemon> pokemons = query.getResultList();

        for (Pokemon pokemon : pokemons) {
            // Obtener los movimientos asociados al Pokémon y eliminarlos
            Query movimientoQuery = em.createQuery("SELECT pm FROM PokemonMovimiento pm WHERE pm.pokemon = :pokemon");
            movimientoQuery.setParameter("pokemon", pokemon);

            em.remove(pokemon);
        }

        // Finalizar la transacción
        em.getTransaction().commit();
        em.close();
    }

    /**
     * El método eliminarPokemonPorHabilidad lo que hace es hacer una query con la habilidad (los pokemons que tengan esa habilidad) para en base al resultado de la query en un bucle foreach ir eliminadolos
     * @param habilidad es la habilida del pokemon
     */
    public void eliminarPokemonPorHabilidad(String habilidad) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("SELECT p FROM Pokemon p WHERE p.habilidadOculta = :habilidad");
        query.setParameter("habilidad", habilidad);
        List<Pokemon> pokemons = query.getResultList();

        for (Pokemon pokemon : pokemons) {
            // Obtener los movimientos asociados al Pokémon y eliminarlos
            Query movimientoQuery = em.createQuery("SELECT pm FROM PokemonMovimiento pm WHERE pm.pokemon = :pokemon");
            movimientoQuery.setParameter("pokemon", pokemon);

            em.remove(pokemon);
        }

        // Finalizar la transacción
        em.getTransaction().commit();
        em.close();
    }

    /**
     *El método eliminarPokemonPorObjeto lo que hace es hacer una query con el objeto equipado del pokemon para en base al resultado de la query en un bucle foreach ir eliminadolos
     * @param objetoNombre es el objeto equipado del pokemon
     */
    public void eliminarPokemonPorObjeto(String objetoNombre) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        // Obtener el objeto Objeto correspondiente al nombre proporcionado
        Query objetoQuery = em.createQuery("SELECT o FROM Objeto o WHERE o.nombre = :nombre");
        objetoQuery.setParameter("nombre", objetoNombre);
        Objeto objeto = (Objeto) objetoQuery.getSingleResult();

        // Consulta para encontrar los pokemons que tienen el objeto equipado
        Query query = em.createQuery("SELECT p FROM Pokemon p WHERE p.objetoEquipado = :objeto");
        query.setParameter("objeto", objeto);
        List<Pokemon> pokemons = query.getResultList();

        for (Pokemon pokemon : pokemons) {
            // Obtener los movimientos asociados al Pokémon y eliminarlos
            Query movimientoQuery = em.createQuery("SELECT pm FROM PokemonMovimiento pm WHERE pm.pokemon = :pokemon");
            movimientoQuery.setParameter("pokemon", pokemon);

            em.remove(pokemon);
        }

        // Finalizar la transacción
        em.getTransaction().commit();
        em.close();
    }





}
