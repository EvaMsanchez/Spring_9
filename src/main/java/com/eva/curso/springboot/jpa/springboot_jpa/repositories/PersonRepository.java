package com.eva.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;
import java.util.Optional;


public interface PersonRepository extends CrudRepository<Person, Long>
{
    // Filtrar las personas por el lenguaje de programación, consulta basada en la nomenclatura Query Methods
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    // Consulta personalizada, JPA
    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    // Cumple con la nomenclatura de nombre de método
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

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
