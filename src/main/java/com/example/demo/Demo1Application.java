package com.example.demo;

import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.entity.enume.Role;
import com.example.demo.user.service.Impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }


    @Bean
    CommandLineRunner seedUsers(UserServiceImpl userService) {
        return args -> {

            userService.createUser(
                    Role.ADMIN,
                    new UserDTO(
                            "Admin",
                            "User",
                            "admin@example.com",
                                "admin123"
                    )
            );

            userService.createUser(
                    Role.GESTIONNAIRE_APPROVISIONNEMENT,
                    new UserDTO(
                            "Gestionnaire",
                            "Approvisionnement",
                            "gestionnaireApprov@example.com",
                            "gest123"
                    )
            );

            userService.createUser(
                    Role.RESPONSABLE_ACHATS,
                    new UserDTO(
                            "Responsable",
                            "Achats",
                            "achats@example.com",
                            "achats123"
                    )
            );

            userService.createUser(
                    Role.SUPERVISEUR_LOGISTIQUE,
                    new UserDTO(
                            "Superviseur",
                            "Logistique",
                            "superviseurLog@example.com",
                            "log123"
                    )
            );

            userService.createUser(
                    Role.CHEF_PRODUCTION,
                    new UserDTO(
                            "Chef",
                            "Production",
                            "chefProd@example.com",
                            "prod123"
                    )
            );

            userService.createUser(
                    Role.PLANIFICATEUR,
                    new UserDTO(
                            "Planificateur",
                            "Production",
                            "planificateur@example.com",
                            "plan123"
                    )
            );

            userService.createUser(
                    Role.SUPERVISEUR_PRODUCTION,
                    new UserDTO(
                            "Superviseur",
                            "Production",
                            "superviseurProd@example.com",
                            "superProd123"
                    )
            );

            userService.createUser(
                    Role.GESTIONNAIRE_COMMERCIAL,
                    new UserDTO(
                            "Gestionnaire",
                            "Commercial",
                            "gestCommercial@example.com",
                            "com123"
                    )
            );

            userService.createUser(
                    Role.RESPONSABLE_LOGISTIQUE,
                    new UserDTO(
                            "Responsable",
                            "Logistique",
                            "respLogistique@example.com",
                            "respLog123"
                    )
            );

            userService.createUser(
                    Role.SUPERVISEUR_LIVRAISONS,
                    new UserDTO(
                            "Superviseur",
                            "Livraisons",
                            "superviseurLiv@example.com",
                            "liv123"
                    )
            );
        };
    }



}
