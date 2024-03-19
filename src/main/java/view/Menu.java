package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * La clase Menu sirve para referenciar los textos entre métodos
 */
public class Menu {
    private int option;

    public Menu() {
        super();
    }
    Scanner scanner = new Scanner(System.in);
    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println(" \nMENU PRINCIPAL \n");

            System.out.println("1. Listar Pokemons ");
            System.out.println("2. Listar Objetos ");
            System.out.println("3. Listar Movimientos ");
            System.out.println("4. Poblar masivamente Pokemons vía csv ");
            System.out.println("5. Poblar masivamente Objetos vía csv ");
            System.out.println("6. Poblar masivamente Movimientos vía csv ");
            System.out.println("7. Eliminar un Pokemon por nombre ");
            System.out.println("8. Eliminar Pokemons filtrados");
            System.out.println("9. Crear las tablas de la base de datos ");
            System.out.println("10. Modificar un valor de los movimientos por tipo");
            System.out.println("11. Borrar las tablas de la base de datos ");
            System.out.println("12. Modificar algún valor de un objeto ");
            System.out.println("0. Sortir. ");

            System.out.println("Esculli opció: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
        } while (option != 1  && option != 0 && option!=2 && option!=3 && option!=4 && option!=5 && option!=6 && option!=7 && option !=8 && option!=9 && option!=10 && option!=11 && option!=12);

        return option;
    }
    public String menuEliminarPokemon(){
        System.out.println("Que Pokemon quieres eliminar?");

        String pokemon = scanner.nextLine();

        return pokemon;
    }
    public String menuEliminarPokemonsPorTipo(){
        System.out.println("Cual es el tipo de los pokemons que quires purgar?");
        String tipo = scanner.nextLine();

        return tipo;
    }

    public int menuFiltrosPokemon(){
        System.out.println("Porque atributo quieres filtrar a los Pokemons?");
        System.out.println("1.Tipos");
        System.out.println("2.Habilidad oculta");
        System.out.println("3.Objeto Equpado");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        return opcion;
    }

    public String menuEliminarPokemonsPorHabilidad(){
        System.out.println("Cual es la habilidad oculta de los pokemons que quires purgar?");

        String habilidad = scanner.nextLine();

        return habilidad;
    }

    public String menuEliminarPokemonsPorObjeto(){
        System.out.println("Cual es el objeto de los pokemons que quires purgar?");

        String objeto = scanner.nextLine();

        return objeto;
    }

    public String menuTipoMovimientos(){
        System.out.println("Cual es el tipo de los movimientos que quieres modificar?");

        String tipo = scanner.nextLine();

        return tipo;
    }

    public int menuNuevoValorMovimientos(){
        System.out.println("Cual es el nuevo valor de los movimientos que quieres modificar?");

        int nuevoValor = scanner.nextInt();
        scanner.nextLine();

        return nuevoValor;
    }

    public int menuOpcionMovimientos(){
        System.out.println("Que valor de los movimientos que quieres modificar?");
        System.out.println("1.Poder");
        System.out.println("2.Pp");

        int nuevoValor = scanner.nextInt();
        scanner.nextLine();

        return nuevoValor;
    }

    public String menuObjeto(){
        System.out.println("Cual es el objeto que quieres modificar?");

        String objeto = scanner.nextLine();

        return objeto;
    }

    public String menuNuevoValorObjeto(){
        System.out.println("Cual es el nuevo valor del objeto que quieres modificar?");

        String nuevoValor = scanner.nextLine();
        scanner.nextLine();

        return nuevoValor;
    }

    public int menuOpcionObjeto(){
        System.out.println("Que valor del objeto que quieres modificar?");
        System.out.println("2.Generación");
        System.out.println("3.Precio de Compra");
        System.out.println("4.Precio de Venta");
        System.out.println("5.Tipo");

        int nuevoValor = scanner.nextInt();
        scanner.nextLine();

        return nuevoValor;
    }
}