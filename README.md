# HibernatePokemon
HIbernatePokemon

# Proyecto Pokemon - README

Este es un proyecto de ejemplo que maneja información de Pokemon utilizando JPA-Hibernate.

## Configuración de la Persistencia

Para configurar la conexión a la base de datos, asegúrate de tener correctamente configurado tu archivo `persistence.xml`. Aquí tienes un ejemplo de cómo podría ser la configuración:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="Pokemon">
        <description>Persistence unit for the JPA-Hibernate example in UF2NF2</description>
        <provider>org.hibernate.ejb.HibernatePersistence</provider>

        <properties>
            <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://192.168.1.133:5432/prueba"/>
            <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver"/>
            <property name="javax.persistence.jdbc.user" value="usuario"/>
            <property name="javax.persistence.jdbc.password" value="usuario"/>
        </properties>
    </persistence-unit>
</persistence>

```

## Controllers

Este proyecto cuenta con varios controladores para manejar distintas entidades, incluyendo BBDDController, MovimientoController, ObjetoController y PokemonController. Cada controlador tiene métodos para realizar operaciones específicas en la base de datos.

Para más detalles sobre cómo funcionan estos controladores, revisa el código fuente proporcionado.


## Ejecución

Para ejecutar el proyecto, simplemente importa el código fuente a tu IDE preferido y ejecuta la clase principal que contiene el punto de entrada de la aplicación.

Este README proporciona una visión general del proyecto, incluyendo la configuración de persistencia, los controladores disponibles y cómo ejecutar el proyecto. Puedes agregar más detalles o secciones según sea necesario para tu proyecto.


