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

        // Buscar los PokemonMovimiento asociados al Pokemon con el nombre especificado
        Query query = em.createQuery("SELECT pm FROM PokemonMovimiento pm WHERE pm.pokemon.nombre = :nombrePokemon");
        query.setParameter("nombrePokemon", nombrePokemon);
        List<PokemonMovimiento> pokemonMovimientos = query.getResultList();
        // Eliminar cada PokemonMovimiento encontrado
        for (PokemonMovimiento pokemonMovimiento : pokemonMovimientos) {
            em.remove(pokemonMovimiento);
        }
// Eliminar el Pokemon
        // Finalizar la transacción
        em.getTransaction().commit();
        em.getTransaction().begin();
        Pokemon pokemon = em.find(Pokemon.class, nombrePokemon);
        System.out.println(pokemon.getNombre());
        em.remove(pokemon);
        em.getTransaction().commit();
        em.close();
    }




}
