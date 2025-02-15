package com.bitirmeproje;

import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.KullaniciRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
public class BitirmeprojeApplication {

	public static void main(String[] args) {SpringApplication.run(BitirmeprojeApplication.class, args);}

}
