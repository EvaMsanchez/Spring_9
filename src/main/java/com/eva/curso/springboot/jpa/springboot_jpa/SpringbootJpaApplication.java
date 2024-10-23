package com.eva.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
		delete2();	
	}


	// Otra forma de ELIMINAR registros, se borra pasando el objeto
	@Transactional
	public void delete2()
	{
		repository.findAll().forEach(System.out::println);

		// Simular que nos pasan el id de la persona a eliminar
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar:");
		Long id = scanner.nextLong();

		// Buscar el objeto persona por el id. Con el "Optional" se envuelve el objeto para preguntar si está presente o no
		Optional<Person> optionalPerson = repository.findById(id);

		// ifPresentOrElse: si está presente el objeto lo elimina, sino lo está lanza el mensaje
		optionalPerson.ifPresentOrElse(person -> repository.delete(person), 
			() -> System.out.println("Lo sentimos no existe la persona con ese id!"));

		repository.findAll().forEach(System.out::println);

		scanner.close();
	}


	// ELIMINAR registros por id
	@Transactional
	public void delete()
	{
		repository.findAll().forEach(System.out::println); // Mostrar la lista antes de borrar

		// Simular que nos pasan el id de la persona a eliminar
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar:");
		Long id = scanner.nextLong();

		repository.deleteById(id);
		repository.findAll().forEach(System.out::println); // Mostrar la lista

		scanner.close();
	}


	// ACTUALIZAR registros
	@Transactional
	public void update()
	{
		// Simular que nos pasan el id de la persona a modificar y los datos que se modifican
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona:");
		Long id = scanner.nextLong();

		// Buscar el objeto persona por el id. Con el "Optional" se envuelve el objeto para preguntar si está presente o no
		Optional<Person> optionalPerson = repository.findById(id);
		
		// optionalPerson.ifPresent(person -> {
		if(optionalPerson.isPresent())
		{
			Person personDB = optionalPerson.orElseThrow();

			System.out.println(personDB);
			System.out.println("Ingrese el lenguaje de programación:");
			String programmingLanguage = scanner.next();
			// Se modifica y se guarda
			personDB.setProgrammingLanguage(programmingLanguage);
			Person personUpdated = repository.save(personDB);

			//Mostrar el objeto actualizado
			System.out.println(personUpdated);
		}	
		else
		{
			System.out.println("El usuario no está presente! no existe!");
		}
		// });

		scanner.close();
	}


	// Cuando son métodos crear, modificar, eliminar
	// CREAR un registro
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
