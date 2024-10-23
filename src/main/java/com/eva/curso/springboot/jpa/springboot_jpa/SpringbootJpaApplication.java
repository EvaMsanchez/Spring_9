package com.eva.curso.springboot.jpa.springboot_jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		// List<Person> persons = (List<Person>) repository.findAll();
		//List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java", "Andrés");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andrés");


		persons.stream().forEach(person -> System.out.println(person));
	}

}
