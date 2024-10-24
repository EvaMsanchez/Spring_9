package com.eva.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.eva.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
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
		personalizedQueriesDistinct();	
	}


	// DISTINCT y COUNT: devuelve valores que no se repiten y contar.
	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct()
	{
		System.out.println("========= consulta con nombres de personas =========");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("========= consulta con nombres únicos(distinct) de personas =========");
		names = repository.findAllNamesDistinct();
		names.forEach(System.out::println);

		System.out.println("========= consulta con lenguajes de programación únicos(distinct) =========");
		List<String> languages = repository.findAllProgrammingLanguageDistinct();
		languages.forEach(System.out::println);

		System.out.println("========= consulta con total de lenguajes de programación únicos(distinct y count) =========");
		Long totalLanguages = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("Total de lenguajes de programación: " + totalLanguages);
	}


	// Más consultas personalizadas
	@Transactional(readOnly = true)
	public void personalizedQueries2()
	{
		System.out.println("=========================== consultas personalizadas  ===========================");
	
		System.out.println("========= mostrando el objeto persona y un campo =========");
		List<Object[]> personRegs = repository.findAllMixPerson();
		personRegs.forEach(reg -> {
			System.out.println("programmingLanguage=" + reg[1] + ", person=" + reg[0]);
		});

		//Tener en cuenta que los demás campos del objeto de una INSTACIA PERSONALIZADA que no se han seleccionado, SI aparecen pero como "null"
		System.out.println("========= mostrando el objeto de una instancia personalizada =========");
		List<Person> persons = repository.findAllObjectPersonPersonalized();
		persons.forEach(System.out::println);

		// A diferencia del anterior, al ser una clase DTO personalizada, solo aparecen poblados los campos de esa clase, NO campos "null"
		System.out.println("========= mostrando el objeto DTO de una instancia personalizada =========");
		List<PersonDto> personsDto = repository.findAllPersonDto();
		personsDto.forEach(System.out::println);
	}


	// Consultas personalizadas
	@Transactional(readOnly = true)
	public void personalizedQueries()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("=========================== consultas personalizadas ===========================");
		System.out.println("Ingrese el id:");
		Long id = scanner.nextLong();
		scanner.close();

		System.out.println("========= mostrando solo el nombre =========");
		String name = repository.getNameById(id);
		System.out.println(name);

		System.out.println("========= mostrando solo el id =========");
		Long idDB = repository.getIdById(id);
		System.out.println(idDB);

		System.out.println("========= mostrando nombre completo con concat =========");
		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("========= mostrando ciertos campos por el id (objeto) =========");
		Object[] personReg = (Object[]) repository.obtenerPersonDataById(id);
		System.out.println("id=" + personReg[0] + ", nombre=" + personReg[1] + ", apellido=" + personReg[2] + ", lenguaje=" + personReg[3]);

		System.out.println("========= mostrando ciertos campos (lista) =========");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> System.out.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido=" + reg[2] + ", lenguaje=" + reg[3]));
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
