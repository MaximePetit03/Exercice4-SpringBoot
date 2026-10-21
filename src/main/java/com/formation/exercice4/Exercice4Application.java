package com.formation.exercice4;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Point d'entrée de l'application Spring Boot.
 * L'annotation @SpringBootApplication active :
 * - @Configuration : classe de configuration Spring
 * - @EnableAutoConfiguration : configuration automatique
 * - @ComponentScan : détecte les composants dans ce package et ses sous-packages
 */
@SpringBootApplication
public class Exercice4Application {
	public static void main(String[] args) {
		SpringApplication.run(Exercice4Application.class, args);
	}
}