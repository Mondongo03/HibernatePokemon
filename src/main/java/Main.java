import java.util.Scanner;

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
            emf = Persistence.createEntityManagerFactory("JPAMagazines");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        PokemonController pokemonController = new PokemonController(entityManagerFactory);
        ObjetoController objetoController= new ObjetoController(entityManagerFactory);
        MovimientoController movimientoController = new MovimientoController(entityManagerFactory);
        int opcio;
        Scanner input = new Scanner(System.in);
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
                    //String pokemon = input.nextLine();
                    //pokemonController.eliminarPokemon(pokemon);
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
                default:
                    System.out.println("Adeu!!");
                    System.exit(1);

            }
        } while (opcio != 0);
    }
}