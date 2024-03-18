package controller;

import model.Movimiento;
import model.Objeto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MovimientoController {
    private EntityManagerFactory entityManagerFactory;

    public MovimientoController() { }

    public MovimientoController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void listarMovimientos() {
        int i = 0;
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Movimiento> listaMovimiento = em.createQuery("from Movimiento", Movimiento.class).getResultList();
        for (Movimiento movimiento : listaMovimiento) {
            System.out.print(movimiento.getNombre().toString()+", ");
            System.out.print(movimiento.getTipo().toString()+", ");
            System.out.print(movimiento.getCategoria().toString()+", ");
            System.out.print(movimiento.getPoder() +", ");
            System.out.print(movimiento.getPp()+", ");
            System.out.print(movimiento.getPrecision().toString()+", ");
            System.out.print(movimiento.getDescripcion().toString()+", ");
            System.out.println();
            i++;
        }
        System.out.println(i+" Pokemons listados");
        em.getTransaction().commit();
        em.close();
    }
    public void poblarMovimientoCsv(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        // Leer el archivo CSV
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
                Movimiento movimiento = new Movimiento();
                movimiento.setNombre(data[0]);
                movimiento.setTipo(data[1]);
                movimiento.setCategoria(data[2]);// Eliminar espacios en blanco
                if (!data[3].trim().isEmpty()) { // Verificar si la cadena no está vacía
                    movimiento.setPoder(Integer.parseInt(data[3])); // Convertir la cadena a un número entero
                } else {
                    movimiento.setPoder(0); // Establecer un valor predeterminado si la cadena está vacía
                }
                movimiento.setPp(Integer.parseInt((data[4])));
                movimiento.setPrecision(data[5]);
                movimiento.setDescripcion(data[6]);

                em.merge(movimiento);

            }em.getTransaction().commit();
            em.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
