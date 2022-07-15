package com.practicePoc;

import com.practicePoc.constant.AppConstant;
import com.practicePoc.entity.Role;
import com.practicePoc.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class PracticePocApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(PracticePocApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Override
    public void run(String... args) throws Exception {
        try {
            Role role = new Role();
            role.setId(AppConstant.ADMIN_USER);
            role.setName("ADMIN_USER");

            Role role1 = new Role();
            role1.setId(AppConstant.NORMAL_USER);
            role1.setName("NORMAL_USER");
            List<Role> role2 = List.of(role, role1);
            List<Role> result = this.roleRepository.saveAll(role2);

            result.forEach(role3 -> {
                System.out.println(role3.getName());
            });
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
