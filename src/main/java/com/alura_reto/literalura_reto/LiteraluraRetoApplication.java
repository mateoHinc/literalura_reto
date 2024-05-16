package com.alura_reto.literalura_reto;

import com.alura_reto.literalura_reto.principal.Principal;
import com.alura_reto.literalura_reto.repository.AutorRepository;
import com.alura_reto.literalura_reto.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraRetoApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repositorioLibro;
	@Autowired
	private AutorRepository repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraRetoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLibro, repositorioAutor);
		principal.menu();
	}
}
