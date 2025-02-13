package com.eva.curso.springboot.jpa.springboot_jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // entidad = tabla
@Table(name="persons")
public class Person 
{
    // atributo = columna
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indicar cual es el tipo de dato
    private Long id;

    private String name;
    private String lastname;

    @Column(name="programming_language")
    private String programmingLanguage;

    // Atributo de la clase eventos del ciclo de vida
    @Embedded
    private Audit audit = new Audit();

    // Obligatorio, ya que Hibernate utiliza el constructor vacío para poblar tabla
    public Person() {}

    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }

    // Constructor con solo 2 parámetros y en el repositorio se crea una instancia personalizada del objeto persona pero con estos dos parámetros
    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getProgrammingLanguage() {
        return programmingLanguage;
    }
    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    // Por si queremos imprimir el objeto persona
    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage="
                + programmingLanguage + ", createAt=" + audit.getCreatAt() + ", updated=" + audit.getUpdatedAt() + "]";
    }
    
}
