package com.example.employeemanagement.config;

import com.example.employeemanagement.model.*;
import com.example.employeemanagement.model.ProjectPerson;
import com.example.employeemanagement.model.enums.Gender;
import com.example.employeemanagement.model.enums.Priority;
import com.example.employeemanagement.model.enums.Roles;
import com.example.employeemanagement.model.enums.Status;
import com.example.employeemanagement.repository.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;
    private final ProjectPersonRepository projectPersonRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final TaskRepository taskRepository;

    private final Faker faker = new Faker(new Locale("en"));
    private final Random random = new Random();

    public DataSeeder(CountryRepository countryRepository,
                      CompanyRepository companyRepository,
                      RoleRepository roleRepository,
                      DepartmentRepository departmentRepository,
                      PersonRepository personRepository,
                      ProjectRepository projectRepository,
                      ProjectPersonRepository projectPersonRepository,
                      UserRepository userRepository,
                      UserRoleRepository userRoleRepository,
                      TaskRepository taskRepository) {
        this.countryRepository = countryRepository;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.projectPersonRepository = projectPersonRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedCountries();
        seedCompanies();
        seedRoles();
        seedDepartments();
        seedPersons();
        seedProjects();
        seedProjectPersons(); // ✅ Thêm mới
        seedUsers();
        seedTasks();
    }

    // 🌍 COUNTRY
    private void seedCountries() {
        if (countryRepository.count() == 0) {
            Set<String> usedCodes = new HashSet<>();
            for (int i = 0; i < 20; i++) {
                String code;
                do {
                    code = faker.country().countryCode2();
                } while (usedCodes.contains(code));
                usedCodes.add(code);

                Country country = new Country();
                country.setCode(code);
                country.setName(faker.country().name());
                country.setDescription(faker.lorem().sentence(8));
                countryRepository.save(country);
            }
            System.out.println("✅ Seeded 20 countries!");
        }
    }

    // 🏢 COMPANY
    private void seedCompanies() {
        if (companyRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Company company = new Company();
                company.setCode("CMP-" + (1000 + i));
                company.setName(faker.company().name());
                company.setAddress(faker.address().fullAddress());
                companyRepository.save(company);
            }
            System.out.println("✅ Seeded 10 companies!");
        }
    }

    // 🔑 ROLE
    private void seedRoles() {
        if (roleRepository.count() == 0) {
            for (Roles roleName : Roles.values()) {
                Role role = new Role();
                role.setRole(roleName);
                role.setDescription(faker.lorem().sentence(6));
                roleRepository.save(role);
            }
            System.out.println("✅ Seeded Roles!");
        }
    }

    // 🧩 DEPARTMENT
    private void seedDepartments() {
        if (departmentRepository.count() == 0) {
            List<Company> companies = companyRepository.findAll();

            for (Company company : companies) {
                int rootCount = 3 + random.nextInt(3);
                List<Department> roots = new ArrayList<>();

                for (int i = 0; i < rootCount; i++) {
                    Department dept = new Department();
                    dept.setCode("DEP-" + company.getId() + "-" + (i + 1));
                    dept.setName(faker.company().industry() + " Dept " + (i + 1));
                    dept.setCompany(company);
                    dept.setParent(null);
                    departmentRepository.save(dept);
                    roots.add(dept);
                }

                for (Department root : roots) {
                    int subCount = 1 + random.nextInt(3);
                    for (int j = 0; j < subCount; j++) {
                        Department sub = new Department();
                        sub.setCode(root.getCode() + "-" + (j + 1));
                        sub.setName(root.getName() + " Sub " + (j + 1));
                        sub.setCompany(company);
                        sub.setParent(root);
                        departmentRepository.save(sub);
                    }
                }
            }
            System.out.println("✅ Departments seeded for all companies!");
        }
    }

    // 👤 PERSON
    private void seedPersons() {
        if (personRepository.count() == 0) {
            List<Company> companies = companyRepository.findAll();

            for (Company company : companies) {
                for (int i = 0; i < 8; i++) {
                    Person person = new Person();
                    person.setFull_name(faker.name().fullName());
                    person.setGender(random.nextBoolean() ? Gender.MALE : Gender.FEMALE);
                    person.setBirthdate(LocalDate.now().minusYears(20 + random.nextInt(15)));
                    person.setPhone_number(faker.phoneNumber().cellPhone());
                    person.setAddress(faker.address().streetAddress());
                    person.setCompany(company);

                    personRepository.save(person);
                }
            }
            System.out.println("✅ Seeded 8 persons per company!");
        }
    }

    // 🚀 PROJECT
    // 🚀 PROJECT
    private void seedProjects() {
        if (projectRepository.count() == 0) {
            List<Company> companies = companyRepository.findAll();

            for (Company company : companies) {
                // ✅ Mỗi công ty random 5–10 dự án
                int projectCount = 5 + random.nextInt(6);

                for (int i = 0; i < projectCount; i++) {
                    Project project = new Project();
                    project.setCode("PRJ-" + company.getId() + "-" + (100 + i));
                    project.setName(faker.company().buzzword() + " "
                            + faker.app().name() + " App");
                    project.setDescription(faker.lorem().sentence(15));
                    project.setCompany(company); // ✅ Gán Company
                    projectRepository.save(project); // ✅ Lưu sau khi set Company
                }
            }

            System.out.println("✅ Seeded Projects: Each company has 5–10 projects!");
        }
    }

    // 🔗 PROJECT_PERSON
    private void seedProjectPersons() {
        if (projectPersonRepository.count() == 0) {
            List<Project> projects = projectRepository.findAll();
            List<Person> persons = personRepository.findAll();

            for (Project project : projects) {
                // Lấy danh sách person thuộc cùng công ty
                List<Person> sameCompanyPersons = new ArrayList<>(persons.stream()
                        .filter(p -> p.getCompany().getId().equals(project.getCompany().getId()))
                        .toList());

                // Chọn ngẫu nhiên 2–5 người tham gia
                Collections.shuffle(sameCompanyPersons);
                int numParticipants = 2 + random.nextInt(4);
                for (Person person : sameCompanyPersons.subList(0, Math.min(numParticipants, sameCompanyPersons.size()))) {
                    ProjectPerson pp = new ProjectPerson();
                    pp.setProject(project);
                    pp.setPerson(person);
                    projectPersonRepository.save(pp);
                }
            }

            System.out.println("✅ Seeded ProjectPerson relationships!");
        }
    }

    // 👥 USER + USER_ROLE
    private void seedUsers() {
        if (userRepository.count() == 0) {
            List<Person> persons = personRepository.findAll();
            List<Role> roles = roleRepository.findAll();

            for (int i = 0; i < Math.min(10, persons.size()); i++) {
                Person person = persons.get(i);

                User user = new User();
                user.setEmail("user" + (i + 1) + "@example.com");
                user.setPassword("{noop}123456");
                user.setActive(true);
                user.setPerson(person);
                userRepository.save(user);

                Collections.shuffle(roles);
                int numRoles = 1 + random.nextInt(2);
                for (Role r : roles.subList(0, numRoles)) {
                    UserRole ur = new UserRole();
                    ur.setUser(user);
                    ur.setRole(r);
                    userRoleRepository.save(ur);
                }
            }

            System.out.println("✅ Seeded 10 users with linked roles & persons!");
        }
    }

    @Transactional
    public void seedTasks() {

//        List<Project> projects = projectRepository.findAll();
//        List<Person> persons = personRepository.findAll();
//
//        if (projects.isEmpty() || persons.isEmpty()) {
//            System.out.println("⚠️ Skip seedTasks: Missing Project or Person");
//            return;
//        }
//
//        for (int i = 1; i <= 50; i++) {
//
//            Project randomProject = projects.get(i % projects.size());
//            Person randomPerson = persons.get(i % persons.size());
//
//            if (randomProject.getCompany() == null) {
//                System.out.println("⚠️ Skip Task: Project " + randomProject.getId() + " lacks company!");
//                continue;
//            }
//
//            Task task = new Task();
//            task.setProject(randomProject);
//            task.setPerson(randomPerson);
//            task.setName("Task " + i);
//            task.setDescription("Description for task " + i);
//            Priority[] priorities = Priority.values();
//            Status[] statuses = Status.values();
//
//            task.setPriority(priorities[i % priorities.length]);
//            task.setStatus(statuses[i % statuses.length]);
//            task.setStart_time(LocalDate.now().minusDays(i));
//            task.setEnd_time(LocalDate.now().plusDays(i));
//
//            taskRepository.save(task);
//        }

        System.out.println("✅ Seeded Tasks successfully!");
    }
}