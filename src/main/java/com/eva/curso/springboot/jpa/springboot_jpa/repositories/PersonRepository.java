package com.eva.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eva.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>
{

}
