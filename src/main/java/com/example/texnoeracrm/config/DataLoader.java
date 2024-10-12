package com.example.texnoeracrm.config;

import com.example.texnoeracrm.dao.entity.RoleEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.repository.RoleRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName(RoleEnum.ADMIN);
            roleRepository.save(adminRole);

            RoleEntity userRole = new RoleEntity();
            userRole.setName(RoleEnum.TEACHER);
            roleRepository.save(userRole);

            RoleEntity managerRole = new RoleEntity();
            managerRole.setName(RoleEnum.MENTOR);
            roleRepository.save(managerRole);

            RoleEntity guestRole = new RoleEntity();
            guestRole.setName(RoleEnum.STUDENT);
            roleRepository.save(guestRole);
        }

        if (userRepository.count() == 0) {
            UserEntity adminUser = new UserEntity();
            adminUser.setName("Admin");
            adminUser.setSurname("User");
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin_password"));
            adminUser.setIsActive(true);

            RoleEntity adminRole = roleRepository.findByName(RoleEnum.ADMIN);
            adminUser.setRoleEntity(adminRole);

            userRepository.save(adminUser);
        }
    }
}
