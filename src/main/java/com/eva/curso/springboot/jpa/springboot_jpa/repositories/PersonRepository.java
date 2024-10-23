package com.eva.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;


public interface PersonRepository extends CrudRepository<Person, Long>
{
    // Filtrar las personas por el lenguaje de programación, consulta basada en la nomenclatura Query Methods
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    // Consulta personalizada, JPA
    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    // Cumple con la nomenclatura de nombre de método
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);
}
