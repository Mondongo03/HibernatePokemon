package controller;

import model.Article;
import model.Movimiento;
import model.Objeto;
import model.Pokemon;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ObjetoController {

  private EntityManagerFactory entityManagerFactory;

  public ObjetoController() { }

  public ObjetoController(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

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


  public void poblarObjetoCsv(){
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    // Leer el archivo CSV
    String csvFile = "src/main/resources/Objeto.csv";
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
  public List<Article>  readArticlesFile(String articlesFile, String authorsFile) throws IOException {
    int articleId, magazineId, authorId;
    String title;
    Date creationDate;
    boolean publishable;
    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

    BufferedReader br = new BufferedReader(new FileReader(articlesFile));
    String linea = "";
    //List<Author> authorList = pokemonController.readAuthorsFile(authorsFile);
    List<Article> articlesList = new ArrayList<Article>();

    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      articleId = Integer.parseInt(str.nextToken());
      magazineId = Integer.parseInt(str.nextToken());
      authorId = Integer.parseInt(str.nextToken());
      title = str.nextToken();

      try {
        creationDate = dateFormat.parse(str.nextToken());
        publishable = Boolean.parseBoolean(str.nextToken());

        //articlesList.add(new Article(articleId, title, creationDate, publishable, authorList.get(authorId - 1)));

      } catch (ParseException e) {

        e.printStackTrace();
      }

    }
    br.close();
    return articlesList;

  }

  /* Method to CREATE an Article in the database */
  public void addArticle(Article article) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(article);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to READ all Articles */
  public void listArticles() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Article> result = em.createQuery("from Article", Article.class)
        .getResultList();
    for (Article article : result) {
      System.out.println(article.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /* Method to UPDATE activity for an Article */
  public void updateArticle(Integer articleId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Article article = (Article) em.find(Article.class, articleId);
    em.merge(article);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to DELETE an Article from the records */
  public void deleteArticle(Integer articleId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Article article = (Article) em.find(Article.class, articleId);
    em.remove(article);
    em.getTransaction().commit();
    em.close();
  }


}
