package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Controller que maneja los metodos crearTablas() y eliminarTablas()
 */
public class BBDDController {
    private final EntityManagerFactory entityManagerFactory;

    public BBDDController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void crearTablas() {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();

            // Crear la tabla objeto
            em.createNativeQuery("CREATE TABLE IF NOT EXISTS public.objeto ( " +
                    "nombre character varying(255) NOT NULL, " +
                    "generacion character varying(255), " +
                    "precio_compra character varying(255), " +
                    "precio_venta character varying(255), " +
                    "tipo character varying(255), " +
                    "PRIMARY KEY (nombre) " +
                    ")").executeUpdate();

            // Crear la tabla movimiento
            em.createNativeQuery("CREATE TABLE IF NOT EXISTS public.movimiento ( " +
                    "nombre character varying(255) NOT NULL, " +
                    "tipo character varying(255), " +
                    "categoria character varying(255), " +
                    "poder integer, " +
                    "pp integer, " +
                    "precision character varying(255), " +
                    "descripcion character varying(255), " +
                    "PRIMARY KEY (nombre) " +
                    ")").executeUpdate();

            // Crear la tabla pokemon
            em.createNativeQuery("CREATE TABLE IF NOT EXISTS public.pokemon ( " +
                    "nombre character varying(255) NOT NULL, " +
                    "numero integer, " +
                    "tipo_primario character varying(255), " +
                    "tipo_secundario character varying(255), " +
                    "habilidad character varying(255), " +
                    "habilidad_oculta character varying(255), " +
                    "objeto_equipado character varying(255), " +
                    "hp integer, " +
                    "ataque integer, " +
                    "defensa integer, " +
                    "velocidad integer, " +
                    "ataque_especial integer, " +
                    "defensa_especial integer, " +
                    "PRIMARY KEY (nombre), " +
                    "FOREIGN KEY (objeto_equipado) REFERENCES public.objeto(nombre) " +
                    ")").executeUpdate();

            // Crear la tabla pokemon_movimiento
            em.createNativeQuery("CREATE TABLE IF NOT EXISTS public.pokemon_movimiento ( " +
                    "id serial NOT NULL, " +
                    "pokemon_nombre character varying(255), " +
                    "movimiento_nombre character varying(255), " +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (movimiento_nombre) REFERENCES public.movimiento(nombre), " +
                    "FOREIGN KEY (pokemon_nombre) REFERENCES public.pokemon(nombre) " +
                    ")").executeUpdate();

            // Crear secuencia "pokemon_movimiento_id_seq"
            em.createNativeQuery("CREATE SEQUENCE IF NOT EXISTS public.pokemon_movimiento_id_seq " +
                    "START WITH 1 " +
                    "INCREMENT BY 1 " +
                    "NO MINVALUE " +
                    "NO MAXVALUE " +
                    "CACHE 1").executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void eliminarTablas() {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();

            em.createNativeQuery("DROP TABLE pokemon_movimiento").executeUpdate();
            em.createNativeQuery("DROP TABLE pokemon").executeUpdate();
            em.createNativeQuery("DROP TABLE objeto").executeUpdate();
            em.createNativeQuery("DROP TABLE movimiento").executeUpdate();

            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            System.out.println("Ha petau");
        }
    }

}
