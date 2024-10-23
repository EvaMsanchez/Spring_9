package com.eva.curso.springboot.jpa.springboot_jpa;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.eva.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.eva.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner
{
	// Añadiendo el componente (repositorio con CrudRepository)
	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}


	// Para probar en la línea de comandos el trabajar con la base de datos
	@Override
	public void run(String... args) throws Exception 
	{
		create();	
	}


	// Cuando son métodos crear, modificar, eliminar
	@Transactional
	public void create()
	{
		Person person = new Person(null, "Lalo", "Thor", "Phyton");

		// Guardamos un nuevo registro y devuelve el objeto que se guardó
		Person personNew = repository.save(person);
		System.out.println(personNew);
	}


	// Cuando es consulta "select", solo lectura
	@Transactional(readOnly = true)
	public void findOne()
	{
		/*
		Person person = null;
		Optional<Person> optionalPerson = repository.findById(1L);
		// if(!optionalPerson.Empty())
		if(optionalPerson.isPresent())
		{
			person = optionalPerson.get();
		}
		System.out.println(person);
		*/

		// Otra forma de realizarlo más simplificada
		repository.findByNameContaining("rí").ifPresent(person -> System.out.println(person));
	}


	@Transactional(readOnly = true)
	public void list()
	{
		//List<Person> persons = (List<Person>) repository.findAll();
		//List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java", "Andrés");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andrés");

		persons.stream().forEach(person -> System.out.println(person));

		List<Object[]> personsValues = repository.obtenerPersonData();
		personsValues.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
	}

}
