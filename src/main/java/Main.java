import java.util.Scanner;

import controller.BBDDController;
import controller.MovimientoController;
import controller.ObjetoController;
import controller.PokemonController;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    /**
     * Es el main que contiene las instancias como el menu o los demás métodos de la logica de las demás clases
     */
    static SessionFactory sessionFactoryObj;

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }

    public static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("Pokemon");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        PokemonController pokemonController = new PokemonController(entityManagerFactory);
        ObjetoController objetoController = new ObjetoController(entityManagerFactory);
        MovimientoController movimientoController = new MovimientoController(entityManagerFactory);
        BBDDController bbddController = new BBDDController(entityManagerFactory);
        int opcio;
        int opcion2;
        do {
            Menu menu = new Menu();

            opcio = menu.mainMenu();
            switch (opcio) {

                case 1:
                    pokemonController.listarPokemons();
                    break;
                case 2:
                    objetoController.listarObjetos();
                    break;
                case 3:
                    movimientoController.listarMovimientos();
                    break;
                case 4:
                    pokemonController.poblarPokemonCsv();
                    break;
                case 5:
                    objetoController.poblarObjetoCsv();
                    break;
                case 6:
                    movimientoController.poblarMovimientoCsv();
                    break;
                case 7:
                    String pokemon = menu.menuEliminarPokemon();
                    pokemonController.eliminarPokemon(pokemon);
                    break;
                case 8:
                    opcion2 = menu.menuFiltrosPokemon();
                    switch (opcion2) {
                        case 1:
                            String tipo = menu.menuEliminarPokemonsPorTipo();
                            pokemonController.eliminarPokemonPorTipo(tipo);
                            break;

                        case 2:
                            String habilidad_oculta = menu.menuEliminarPokemonsPorHabilidad();
                            pokemonController.eliminarPokemonPorHabilidad(habilidad_oculta);
                            break;

                        case 3:
                            String objeto = menu.menuEliminarPokemonsPorObjeto();
                            pokemonController.eliminarPokemonPorObjeto(objeto);
                            break;
                    }
                case 9:
                    bbddController.crearTablas();
                    break;
                case 10:
                    String tipo = menu.menuTipoMovimientos();
                    int opcion3 = menu.menuOpcionMovimientos();
                    int nuevoValor = menu.menuNuevoValorMovimientos();
                    movimientoController.actualizarMovimientosPorTipo(tipo, nuevoValor, opcion3);
                case 11:
                    bbddController.eliminarTablas();
                    break;
                case 12:
                    String objeto = menu.menuObjeto();
                    int opcion4 = menu.menuOpcionObjeto();
                    String nuevoValorObjeto = menu.menuNuevoValorObjeto();
                    objetoController.modificarObjeto(objeto, nuevoValorObjeto, opcion4);
                default:
                    System.out.println("Chau Pescau!!");
                    System.exit(1);

            }
        }
        while (opcio != 0);
    }
}