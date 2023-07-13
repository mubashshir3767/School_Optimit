package com.example.config;


import com.example.entity.*;
import com.example.enums.Gender;
import com.example.kitchen.entity.Measurement;
import com.example.kitchen.entity.Product;
import com.example.kitchen.repository.MeasurementRepository;
import com.example.kitchen.repository.ProductRepository;
import com.example.repository.*;
import com.example.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PermissionService permissionService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;
    private final MeasurementRepository measurementRepository;
    private final ProductRepository productRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {

        if (permissionService.isEmpty()) {
            permissionService.create(new Permission(1, "ROLE_ACCESS"));
            permissionService.create(new Permission(2, "ADD"));
            permissionService.create(new Permission(3, "read"));
        }

        if (initMode.equals("always")) {
            Role supper_admin = new Role(1, "SUPER_ADMIN");
            supper_admin.setPermissions(permissionService.getAll());
            Role role = new Role();
            if (roleRepository.findByName("SUPER_ADMIN").isEmpty()) {
                role = roleRepository.save(supper_admin);
            }
            User admin = User.builder()
                    .fullName("ADMIN")
                    .phoneNumber("907403767")
                    .birthDate(LocalDate.parse("1998-05-13"))
                    .gender(Gender.ERKAK)
                    .registeredDate(LocalDateTime.now())
                    .verificationCode(0)
                    .password(passwordEncoder.encode("111111"))
                    .isBlocked(true)
                    .roles(List.of(role))
                    .build();
            Optional<User> phoneNumber = userRepository.findByPhoneNumber("907403767");
            if (phoneNumber.isEmpty()) {
                userRepository.save(admin);
            }
            Business business = Business.builder()
                    .name("Demo business")
                    .address("Demo")
                    .description("Demo")
                    .phoneNumber("Demo")
                    .active(true)
                    .delete(false)
                    .build();
            Business save = businessRepository.save(business);

            Branch branch = Branch.builder()
                    .name("Demo branch")
                    .business(save)
                    .delete(false)
                    .build();
            Branch save1 = branchRepository.save(branch);

            Measurement measurement = Measurement.builder()
                    .name("KG")
                    .branch(save1)
                    .active(true)
                    .build();
            Measurement save2 = measurementRepository.save(measurement);

            Measurement measurement1 = Measurement.builder()
                    .name("Liter")
                    .branch(save1)
                    .active(true)
                    .build();
            Measurement save3 = measurementRepository.save(measurement1);

            Product product = Product.builder()
                    .branch(save1)
                    .measurement(save2)
                    .name("Kartoshka")
                    .description("malumoti")
                    .active(true)
                    .build();
            productRepository.save(product);

            Product product1 = Product.builder()
                    .branch(save1)
                    .measurement(save3)
                    .name("Sut")
                    .description("malumoti")
                    .active(true)
                    .build();
            productRepository.save(product1);

            PaymentType xisobdanXisobga = PaymentType.builder().name("Xisobdan xisobga").build();
            PaymentType karta = PaymentType.builder().name("Karta orqali").build();
            PaymentType elektron = PaymentType.builder().name("Elektron to'lov").build();
            PaymentType naqt = PaymentType.builder().name("Naqt").build();

            paymentTypeRepository.saveAll(List.of(karta, elektron, xisobdanXisobga, naqt));



        }


    }
}
