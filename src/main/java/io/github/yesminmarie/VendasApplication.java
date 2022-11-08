package io.github.yesminmarie;

import io.github.yesminmarie.domain.entity.Cliente;
import io.github.yesminmarie.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){
        return args -> {
            System.out.println("Salvando clientes");
            clientes.save(new Cliente("Yesmin Marie"));
            clientes.save(new Cliente("Outro cliente"));

            boolean existe = clientes.existsByNome("Yesmin");
            System.out.println("Existe um cliente com nome Yesmin: " + existe);


        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
