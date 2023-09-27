package com.aec.store.seeders;

import com.aec.store.enums.Role;
import com.aec.store.models.ProductEntity;
import com.aec.store.models.UserEntity;
import com.aec.store.repositories.ProductRepository;
import com.aec.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        String password = passwordEncoder.encode("Clave123");
        if (userRepository.count() == 0) {
            createAdmin(password);
            createUser(password);
        }
        if(productRepository.count() ==0){
            createProduct();
        }
    }

    private void createProduct() {
        ProductEntity p1 = new ProductEntity(null, "XGt24", "Mouse Wireless", new BigDecimal("18.78"), 100, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p1);
        ProductEntity p2 = new ProductEntity(null, "KBp72", "Mechanical Keyboard", new BigDecimal("89.99"), 50, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p2);
        ProductEntity p3 = new ProductEntity(null, "HDt56", "2TB Hard Drive", new BigDecimal("149.99"), 30, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p3);
        ProductEntity p4 = new ProductEntity(null, "Spk12", "Bluetooth Speaker", new BigDecimal("29.95"), 80, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p4);
        ProductEntity p5 = new ProductEntity(null, "HPh44", "Wireless Headphones", new BigDecimal("59.99"), 60, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p5);
        ProductEntity p6 = new ProductEntity(null, "Mntr22", "27-inch Monitor", new BigDecimal("249.99"), 20, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p6);
        ProductEntity p7 = new ProductEntity(null, "Cmra10", "Security Camera", new BigDecimal("79.99"), 40, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p7);
        ProductEntity p8 = new ProductEntity(null, "Drn88", "Drone with Camera", new BigDecimal("199.99"), 15, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p8);
        ProductEntity p9 = new ProductEntity(null, "GmC23", "Gaming Console", new BigDecimal("299.99"), 10, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p9);
        ProductEntity p10 = new ProductEntity(null, "Prnt17", "Wireless Printer", new BigDecimal("129.95"), 25, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p10);
        ProductEntity p11 = new ProductEntity(null, "PhnX1", "Smartphone", new BigDecimal("599.99"), 35, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p11);
        ProductEntity p12 = new ProductEntity(null, "TbM17", "Tablet", new BigDecimal("349.99"), 30, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p12);
        ProductEntity p13 = new ProductEntity(null, "LpSv5", "Laptop", new BigDecimal("899.99"), 20, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p13);
        ProductEntity p14 = new ProductEntity(null, "SpPd9", "Spinning Pedals", new BigDecimal("79.95"), 50, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p14);
        ProductEntity p15 = new ProductEntity(null, "HlthTrk2", "Health Tracker", new BigDecimal("49.99"), 70, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p15);
        ProductEntity p16 = new ProductEntity(null, "VrHd4", "Virtual Reality Headset", new BigDecimal("149.99"), 15, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p16);
        ProductEntity p17 = new ProductEntity(null, "DgtCmr8", "Digital Camera", new BigDecimal("199.95"), 25, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p17);
        ProductEntity p18 = new ProductEntity(null, "SprSpd6", "Spray Paint Set", new BigDecimal("19.99"), 90, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p18);
        ProductEntity p19 = new ProductEntity(null, "BkShlf3", "Bookshelf", new BigDecimal("129.99"), 10, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p19);
        ProductEntity p20 = new ProductEntity(null, "CmptrDsk1", "Computer Desk", new BigDecimal("199.99"), 15, Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        productRepository.save(p20);
    }

    private void createUser(String password) {
        UserEntity u1 = new UserEntity(null,"test@mail.com", password, "User", "Test", Role.USER, Boolean.TRUE, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(u1);
        UserEntity u2 = new UserEntity(null,"rosebaldwin@mail.com", password, "Rose", "Baldwin", Role.USER, Boolean.TRUE, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(u2);
        UserEntity u3 = new UserEntity(null,"stephenmorris@mail.com", password, "Stephen", "Morris", Role.USER, Boolean.TRUE, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(u3);
        UserEntity u4 = new UserEntity(null,"rachelturner@mail.com", password, "Rachel", "Turner", Role.USER, Boolean.TRUE, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(u4);
        UserEntity u5 = new UserEntity(null,"geraldhunter@mail.com", password, "Gerald", "Hunter", Role.USER, Boolean.TRUE, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(u5);
    }

    private void createAdmin(String password) {
        UserEntity admin1 = new UserEntity(null, "admin@example.com", password, "Admin", "Root", Role.ADMIN, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(admin1);
        UserEntity admin2 = new UserEntity(null, "admin2@example.com", password, "Sarah", "Admin", Role.ADMIN, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(admin2);
        UserEntity admin3 = new UserEntity(null, "admin3@example.com", password, "James", "Admin", Role.ADMIN, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(admin3);
        UserEntity admin4 = new UserEntity(null, "admin4@example.com", password, "Robert", "Admin", Role.ADMIN, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(admin4);
        UserEntity admin5 = new UserEntity(null, "admin5@example.com", password, "Brooke", "Admin", Role.ADMIN, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(admin5);
    }

}
