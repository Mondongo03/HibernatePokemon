package controller;

import model.Movimiento;
import model.Objeto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Controller de la clase Objeto
 */
public class ObjetoController {

  private EntityManagerFactory entityManagerFactory;

  public ObjetoController() { }

  public ObjetoController(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * El método listarObjetos lo que hace es una query a la bbdd de todos los objetos y luego va printando sus atributos en un foreach de la list creada en base a la query
   */
  public void listarObjetos() {
    int i = 0;
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Objeto> listaObjeto = em.createQuery("from Objeto", Objeto.class).getResultList();
    for (Objeto objeto : listaObjeto) {
      System.out.print(objeto.getNombre().toString()+", ");
      System.out.print(objeto.getGeneracion().toString()+", ");
      System.out.print(objeto.getPrecioCompra().toString()+", ");
      System.out.print(objeto.getPrecioVenta().toString()+", ");
      System.out.print(objeto.getTipo().toString());
      System.out.println();
      i++;
    }
    System.out.println(i+" Pokemons listados");
    em.getTransaction().commit();
    em.close();
  }

  /**
   * El método poblarObjetosCsv lo que hace es abrir una ventana del explodor de ficheros para elegir un csv que con BufferReader irá rellenando todos los campos de los objetos linea por linea para luego hacer el merge de cada objeto en bucle en la bbdd
   */
  public void poblarObjetoCsv(){
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
        Objeto objeto = new Objeto();
        objeto.setNombre(data[0]);
        objeto.setGeneracion(data[1]);
        objeto.setPrecioCompra(data[2].trim()); // Eliminar espacios en blanco
        objeto.setPrecioVenta(data[3].trim()); // Eliminar espacios en blanco
        objeto.setTipo(data[4]);

        em.merge(objeto);

      }em.getTransaction().commit();
      em.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Modifica un objeto en concreto
   * @param objeto es el objeto que vas a modificar
   * @param nuevoValor es el valor nuevo que vas a asignarle al atributo que modifiques
   * @param opcion es para elegir que atributo vas a modificar
   */
  public void modificarObjeto(String objeto, String nuevoValor, int opcion){
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();

    Query query = em.createQuery("SELECT m FROM Objeto m WHERE m.nombre = :nombre", Objeto.class);
    query.setParameter("nombre", objeto);
    Objeto objetoSeleccionado = (Objeto) query.getSingleResult();
      if(opcion == 2) objetoSeleccionado.setGeneracion(nuevoValor);
      if(opcion == 3) objetoSeleccionado.setPrecioCompra(nuevoValor);
      if(opcion == 4) objetoSeleccionado.setPrecioVenta(nuevoValor);
      if(opcion == 5) objetoSeleccionado.setTipo(nuevoValor);

      em.merge(objetoSeleccionado);

    em.getTransaction().commit();
    em.close();
    System.out.println(" objeto actualizado con éxito");
  }
}
