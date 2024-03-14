import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ArticleController;
import controller.PokemonController;
import controller.MagazineController;
import model.*;
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
        int opcio;
        do {
            Menu menu = new Menu();

            opcio = menu.mainMenu();
            switch (opcio) {

                case 1:
                    break;
                case 2:
                    pokemonController.listarPokemons();
                    break;
                case 3:
                    Scanner input = new Scanner(System.in);
                    String pokemon = input.nextLine();
                    pokemonController.eliminarPokemon(pokemon);
                    break;
                default:
                    System.out.println("Adeu!!");
                    System.exit(1);

            }
        } while (opcio != 0);
    }
}