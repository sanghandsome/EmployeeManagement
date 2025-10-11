package com.example.employeemanagement.config;

import com.example.employeemanagement.model.Country;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.CountryRepository;
import com.example.employeemanagement.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(CountryRepository countryRepository, UserRepository userRepository) {
        return args -> {
            Faker faker = new Faker(new Locale("en"));
            Random random = new Random();
            if (countryRepository.count() == 0) {
                Set<String> usedCodes = new HashSet<>();

                for (int i = 0; i < 20; i++) {
                    String code;
                    do {
                        code = faker.country().countryCode2();
                    } while (usedCodes.contains(code));
                    usedCodes.add(code);

                    String name = faker.country().name();
                    String desc = faker.lorem().sentence(10);

                    countryRepository.save(new Country(null, name, code, desc));
                }

                System.out.println("✅ Generated 20 random countries!");
            } else {
                System.out.println("ℹ️ Countries already exist, skipping seeding...");
            }

            if (userRepository.count() == 0) {
                System.out.println("🚀 Seeding random users & persons...");

                for (int i = 0; i < 10; i++) {
                    // Create person
                    Person person = new Person();
                    person.setFull_name(faker.name().fullName());
                    person.setGender(random.nextBoolean() ? "Male" : "Female");
                    int year = faker.number().numberBetween(1980, 2005);
                    int month = faker.number().numberBetween(1, 13);
                    int day = faker.number().numberBetween(1, 29);
                    person.setBirthdate(LocalDate.of(year, month, day));
                    person.setPhone_number(faker.phoneNumber().cellPhone());
                    person.setAddress(faker.address().cityName());

                    // Create user
                    User user = new User();
                    user.setEmail(faker.internet().emailAddress());
                    user.setPassword("123456");
                    user.setActive(random.nextBoolean());
                    user.setPerson(person); // cascade tự lưu person

                    userRepository.save(user);
                }

                System.out.println("✅ Generated 10 random users & persons!");
            } else {
                System.out.println("ℹ️ Users already exist, skipping seeding...");
            }
        };
    }
}