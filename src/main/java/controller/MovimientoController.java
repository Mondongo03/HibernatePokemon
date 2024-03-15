package controller;

import model.Movimiento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MovimientoController {
    private EntityManagerFactory entityManagerFactory;

    public MovimientoController() { }

    public MovimientoController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void poblarMovimientoCsv(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        // Leer el archivo CSV
        String csvFile = "src/main/resources/Movimiento.csv";
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
