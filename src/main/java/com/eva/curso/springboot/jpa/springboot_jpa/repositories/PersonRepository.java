package com.eva.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;
import java.util.Optional;

// Consulta JPQL
public interface PersonRepository extends CrudRepository<Person, Long>
{
    // Consulta personalizada para obtener el valor del NOMBRE que coincida con el id
    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    // Consulta personalizada para obtener el valor del ID que coincida con el id
    @Query("select p.id from Person p where p.id=?1")
    Long getIdById(Long id);

    // Consulta personalizada para obtener el valor del NOMBRE y APELLIDO CONCATENADOS que coincida con el id
    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);


    // Filtrar las personas por el lenguaje de programación, consulta basada en la nomenclatura Query Methods
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    // Consulta personalizada, devuelve los objetos que coincidan con el nombre y lenguaje programación
    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    // Cumple con la nomenclatura de nombre de método
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    // Consulta que devuelve los valores de los campos indicados como lista
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataList();

    // Consulta que devuelve los valores de los campos indicados según el id, como un solo objeto
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id= ?1")
    Object obtenerPersonDataById(Long id); // con JPA se quitan los corchete Object[]

    // Consulta que devuelve solo los valores, de los campos indicados
    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    // Buscar un solo objeto por id pero la consulta de forma personalizada
    // Optional: para envolver el objeto y saber si está presente o no
    @Query(" select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    // Buscar un solo objeto por name pero la consulta de forma personalizada
    @Query(" select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    // Buscar por una coincidencia en el nombre, que se encuentre tanto a la derecha como a la izquierda del nombre
    @Query(" select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    // Buscar por coincidencia en el nombre, pero SIN @Query con el query methods "Containing"
    Optional<Person> findByNameContaining(String name);

    
}
