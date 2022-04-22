package com.rodolfozamora.webservice;

import com.rodolfozamora.webservice.repository.RoleRepository;
import com.rodolfozamora.webservice.model.Role;
import com.rodolfozamora.webservice.model.User;
import com.rodolfozamora.webservice.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//Loads roles and users in H2Database Memory
@Component
@Profile("default")
public class UserCommandLineRunner implements CommandLineRunner {
    private final Logger LOG = LoggerFactory.getLogger(WebServiceApplication.class);

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserCommandLineRunner(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        LOG.info("Creating users to store in the database.");

        var userRole = new Role();
        userRole.setId(1L);
        userRole.setName("USER");
        userRole.setDescription("Basic permissions");
        this.roleRepository.save(userRole);

        var user1 = new User();
        user1.setId(1L);
        user1.setName("Juan");
        user1.setLastName("Lopez");
        user1.setPassword("123");
        user1.setEmail("juan@email.com");
        user1.setRole(userRole);

        var user2 = new User();
        user2.setId(2L);
        user2.setName("Roberto");
        user2.setLastName("Zazueta");
        user2.setPassword("123");
        user2.setEmail("roberto@email.com");
        user2.setRole(userRole);

        var user3 = new User();
        user3.setId(3L);
        user3.setName("Melissa");
        user3.setLastName("Ortega");
        user3.setPassword("123");
        user3.setEmail("melissa@email.com");
        user3.setRole(userRole);

        var user4 = new User();
        user4.setId(4L);
        user4.setName("ADMIN");
        user4.setLastName("SUPER");
        user4.setPassword("123");
        user4.setEmail("admin@email.com");
        user4.setRole(userRole);

        var user5 = new User();
        user5.setId(5L);
        user5.setName("WithoutRole");
        user5.setLastName("Test");
        user5.setPassword("123");
        user5.setEmail("role@email.com");

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
        userService.saveUser(user5);
    }
}
