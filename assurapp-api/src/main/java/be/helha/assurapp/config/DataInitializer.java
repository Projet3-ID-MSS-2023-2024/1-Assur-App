package be.helha.assurapp.config;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.models.Term;
import be.helha.assurapp.insurance.repositories.InsuranceRepository;
import be.helha.assurapp.insurance.repositories.TermRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository, TermRepository termRepository, InsuranceRepository offerRepository) {
        return args -> {
            List<Role> roles = new ArrayList<>();
            List<User> users = new ArrayList<>();
            List<Term> terms = new ArrayList<>();
            List<Insurance> insurances = new ArrayList<>();

            roles.add(new Role(1L, RoleList.ADMINISTRATOR));
            roles.add(new Role(2L, RoleList.CLIENT));
            roles.add(new Role(3L, RoleList.EXPERT));
            roles.add(new Role(4L, RoleList.INSURER));

            users.add(new User("Administrator", "Administrator", "admin@assurapp.com", "admin", roles.get(0)));
            users.add(new User("Client", "Client", "client@assurapp.com", "client", roles.get(1)));
            users.add(new User("Expert", "Expert", "expert@assurapp.com", "expert", roles.get(2)));
            users.add(new User("Insurer", "Insurer", "insurer@assurapp.com", "insurer", roles.get(3)));

            terms.add(new Term(1L, "Premium", "30 per month"));
            terms.add(new Term(2L, "Coverage", "Covers collision and theft, excludes natural disasters and vandalism"));
            terms.add(new Term(3L, "Deductible", "500 deductible for each claim"));

            terms.add(new Term(4L, "Premium", "45 per month"));
            terms.add(new Term(5L, "Coverage", "Covers all accidents, theft, and natural disasters"));
            terms.add(new Term(6L, "Deductible", "250 deductible for collisions, $1000 for natural disasters"));

            terms.add(new Term(7L, "Premium", "25 per month"));
            terms.add(new Term(8L, "Coverage", "Liability only, does not cover vehicle damage"));
            terms.add(new Term(9L, "Deductible", "No deductible for liability claims"));

            terms.add(new Term(10L, "Premium", "40 per month for eco-friendly vehicles"));
            terms.add(new Term(11L, "Coverage", "Includes coverage for collisions, theft, and vandalism"));
            terms.add(new Term(12L, "Deductible", "350 deductible for all claims"));

            terms.add(new Term(13L, "Premium", "50 per month"));
            terms.add(new Term(14L, "Coverage", "Covers off-road and long-distance travel incidents"));
            terms.add(new Term(15L, "Deductible", "1000 deductible for luxury-specific claims"));

            terms.add(new Term(16L, "Premium", "28 per month"));
            terms.add(new Term(17L, "Coverage", "Covers minor accidents and theft, excludes major collisions"));
            terms.add(new Term(18L, "Deductible", "200 deductible for minor accidents"));

            terms.add(new Term(19L, "Premium", "35 per month for drivers over 60"));
            terms.add(new Term(20L, "Coverage", "Covers all accidents, provides roadside assistance"));
            terms.add(new Term(21L, "Deductible", "500 deductible for accidents, no deductible for assistance"));

            terms.add(new Term(22L, "Premium", "70 per month"));
            terms.add(new Term(23L, "Coverage", "Covers all risks, including luxury vehicle specific damages"));
            terms.add(new Term(24L, "Deductible", "1000 deductible for luxury-specific claims"));

            terms.add(new Term(25L, "Premium", "100 per month for fleets up to 5 vehicles"));
            terms.add(new Term(26L, "Coverage", "Covers collisions, theft, and liability for business vehicles"));
            terms.add(new Term(27L, "Deductible", "800 deductible for each vehicle claim"));

            terms.add(new Term(28L, "Premium", "20 per month for new drivers"));
            terms.add(new Term(29L, "Coverage", "Covers basic accidents and theft, excludes high-risk incidents"));
            terms.add(new Term(30L, "Deductible", "300 deductible for claims"));


            insurances.add(new Insurance(1L, "AutoGuard Basic Plan", InsuranceType.CAR, 50000.00, users.get(3), terms.subList(0, 3)));
            insurances.add(new Insurance(2L, "FamilyCar Comprehensive Coverage", InsuranceType.CAR, 100000.00, users.get(3), terms.subList(3, 6)));
            insurances.add(new Insurance(3L, "UrbanSafe Liability Plan", InsuranceType.CAR, 30000.00, users.get(3), terms.subList(6, 9)));
            insurances.add(new Insurance(4L, "EcoDrive Green Plan", InsuranceType.CAR, 70000.00, users.get(3), terms.subList(9, 12)));
            insurances.add(new Insurance(5L, "HighMileage Adventure Coverage", InsuranceType.CAR, 80000.00, users.get(3), terms.subList(12, 15)));
            insurances.add(new Insurance(6L, "CityDrive Compact Plan", InsuranceType.CAR, 40000.00, users.get(3), terms.subList(15, 18)));
            insurances.add(new Insurance(7L, "SeniorDriver Safety Plan", InsuranceType.CAR, 60000.00, users.get(3), terms.subList(18, 21)));
            insurances.add(new Insurance(8L, "LuxuryAuto Elite Plan", InsuranceType.CAR, 150000.00, users.get(3), terms.subList(21, 24)));
            insurances.add(new Insurance(9L, "BusinessFleet Coverage Plan", InsuranceType.CAR, 200000.00, users.get(3), terms.subList(24, 27)));
            insurances.add(new Insurance(10L, "FirstTime Driver Assurance", InsuranceType.CAR, 35000.00, users.get(3), terms.subList(27, 30)));

            roleRepository.saveAll(roles);
            userRepository.saveAll(users);
            termRepository.saveAll(terms);
            offerRepository.saveAll(insurances);

        };
    }
}
