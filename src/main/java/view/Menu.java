package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

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

            System.out.println("0. Sortir. ");

            System.out.println("Esculli opció: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
        } while (option != 1  && option != 0 && option!=2 && option!=3 && option!=4 && option!=5 && option!=6 && option!=7);

        return option;
    }
    public String menuEliminarPokemon(){
        System.out.println("Que Pokemon quieres eliminar?");

        String pokemon = scanner.nextLine();

        return pokemon;
    }






}