package com.example.employeemanagement.config;

import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Country;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.enums.Roles;
import com.example.employeemanagement.repository.CompanyRepository;
import com.example.employeemanagement.repository.CountryRepository;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.RoleRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(CountryRepository countryRepository,
                                   CompanyRepository companyRepository,
                                   RoleRepository roleRepository,
                                   DepartmentRepository departmentRepository) {
        return args -> {
            Faker faker = new Faker(new Locale("en"));
            Random random = new Random();

            // 🗺️ Seed Country
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

            // 🏢 Seed Company
            if (companyRepository.count() == 0) {
                System.out.println("🚀 Seeding random companies...");
                for (int i = 0; i < 10; i++) {
                    Company company = new Company();
                    company.setName(faker.company().name());
                    company.setCode("CMP-" + (1000 + i));
                    company.setAddress(faker.address().fullAddress());

                    companyRepository.save(company);
                }
                System.out.println("✅ Generated 10 random companies!");
            } else {
                System.out.println("ℹ️ Companies already exist, skipping seeding...");
            }

            // 🔑 Seed Role
            if (roleRepository.count() == 0) {
                System.out.println("🚀 Seeding roles...");

                String[] roleNames = {"ADMIN", "USER", "MANAGER", "GUEST"};
                for (String roleName : roleNames) {
                    Role role = new Role();
                    role.setRole(Roles.valueOf(roleName));
                    role.setDescription(faker.lorem().sentence(6));

                    roleRepository.save(role);
                }

                System.out.println("✅ Generated " + roleNames.length + " roles!");
            } else {
                System.out.println("ℹ️ Roles already exist, skipping seeding...");
            }

            // 🔧 Seed Department
            if (departmentRepository.count() == 0) {
                System.out.println("🚀 Seeding departments...");

                Random randomDept = new Random();

                // Lấy tất cả company đã có
                List<Company> companies = companyRepository.findAll();

                for (Company company : companies) {
                    // Tạo 3-5 department root cho mỗi công ty
                    List<Department> rootDepartments = new ArrayList<>();
                    int rootCount = 3 + randomDept.nextInt(3); // 3-5 root department

                    for (int i = 0; i < rootCount; i++) {
                        Department dept = new Department();
                        dept.setCode("DEP-" + company.getId() + "-" + (i + 1));
                        dept.setName(faker.company().industry() + " Dept " + (i + 1));
                        dept.setCompany(company);
                        dept.setParent(null); // root department
                        departmentRepository.save(dept);
                        rootDepartments.add(dept);
                    }

                    // Tạo thêm 1-3 sub-department cho mỗi root department
                    for (Department root : rootDepartments) {
                        int subCount = 1 + randomDept.nextInt(3);
                        for (int j = 0; j < subCount; j++) {
                            Department subDept = new Department();
                            subDept.setCode(root.getCode() + "-" + (j + 1));
                            subDept.setName(root.getName() + " Sub " + (j + 1));
                            subDept.setCompany(company);
                            subDept.setParent(root); // gán parent
                            departmentRepository.save(subDept);
                        }
                    }
                }

                System.out.println("✅ Departments generated for all companies!");
            } else {
                System.out.println("ℹ️ Departments already exist, skipping seeding...");
            }
        };
    }
}