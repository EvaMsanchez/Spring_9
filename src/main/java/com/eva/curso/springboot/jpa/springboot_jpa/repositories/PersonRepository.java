package com.eva.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.eva.curso.springboot.jpa.springboot_jpa.entities.Person;
import java.util.List;
import java.util.Optional;

// Consulta JPQL(query) y basa en nombre del método(nomenclatura Query Methods)
public interface PersonRepository extends CrudRepository<Person, Long>
{
    // Consulta que devuelve las personas que su id esté entre el 2 y el 5 inclusive, y ORDENADAS
    @Query("select p from Person p where p.id between ?1 and ?2 order by p.name desc") // ordenar descendente (desc)
    List<Person> findAllBetweenId(Long id1, Long id2);

    // Consulta que devuelve las personas que su nombre esté entre la J y P del abeceracio (nombres que empiezas por esas letras) y ORDENADAS
    // A diferencia de cantidades, cuando son carácteres, inclusive el primero pero no el último (se excluye)
    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name, p.lastname asc") 
    List<Person> findAllBetweenName(String c1, String c2);

    // Mismas consultas con el between, pero basada en el nombre del método (Query Methods SIN @Query)
    List<Person> findByIdBetween(Long id1, Long id2);
    List<Person> findByNameBetween(String c1, String c2);
    // Ahora incluyendo el order by (para ordenar sin between se lo quitamos)
    List<Person> findByIdBetweenOrderByNameDesc(Long id1, Long id2); // ordenando de forma descendente por nombre
    List<Person> findByNameBetweenOrderByNameAsc(String c1, String c2);
    List<Person> findByNameBetweenOrderByNameDescLastnameDesc(String c1, String c2); // ordenando por DOS CAMPOS


    // Mostrar en mayúscula, se puede aplicar individualmente por cada campo
    @Query("select upper(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatUpper();

    // Mostrar en minúscula
    @Query("select lower(concat(p.name, ' ', p.lastname)) from Person p")
    List<String> findAllFullNameConcatLower();

    // Otra forma de concatenar
    //@Query("select concat(p.name, ' ', p.lastname) from Person p")
    @Query("select p.name || ' ' || p.lastname from Person p")
    List<String> findAllFullNameConcat();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    //Consulta que devuelve los nombres de personas sin repetir
    @Query("select distinct(p.name) from Person p")
    List<String> findAllNamesDistinct();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguageDistinct();

    //Consulta que devuelve los lenguajes de programación sin repetir y el número de esos lenguajes (contados)
    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long findAllProgrammingLanguageDistinctCount();


    // Devuelve también una lista de personas pero a través de una clase DTO.
    // No es entity y por eso hay que indicar el package para que la encuentre, pero lo demás se realiza igual
    @Query("select new com.eva.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    // Devuelve una lista de personas de una forma distinta, de una INSTANCIA PERSONALIZADA
    // Importante: los demás campos de la instancia personalizada que no están en el constructor, SI aparecen pero como "null"
    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllObjectPersonPersonalized();


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


    // Consulta que devuelve el objeto completo y algunos campos concretos
    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();


    // Consulta que devuelve los valores de los campos indicados según el id, como un solo objeto
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id= ?1")
    Object obtenerPersonDataById(Long id); // con JPA se quitan los corchete Object[]

    // Consulta que devuelve solo los valores, de los campos indicados
    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    // Buscar un solo objeto por id pero la consulta de forma personalizada
    // Optional: para envolver el objeto y saber si está presente o no
    @Query("select p from Person p where p.id=?1")
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
