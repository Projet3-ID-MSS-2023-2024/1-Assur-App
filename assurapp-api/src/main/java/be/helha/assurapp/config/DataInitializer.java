package be.helha.assurapp.config;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.expertise.enums.ClaimStatus;
import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.models.Expertise;
import be.helha.assurapp.expertise.repositories.ClaimRepository;
import be.helha.assurapp.expertise.repositories.ExpertiseRepository;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.models.Subscription;
import be.helha.assurapp.insurance.models.Term;
import be.helha.assurapp.insurance.repositories.InsuranceRepository;
import be.helha.assurapp.insurance.repositories.SubscriptionRepository;
import be.helha.assurapp.insurance.repositories.TermRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository, TermRepository termRepository, InsuranceRepository insuranceRepository, SubscriptionRepository subscriptionRepository, ClaimRepository claimRepository, ExpertiseRepository expertiseRepository) {
        return args -> {
            List<Role> roles = new ArrayList<>();
            List<User> users = new ArrayList<>();
            List<Term> terms = new ArrayList<>();
            List<Insurance> insurances = new ArrayList<>();
            List<Subscription> subscriptions = new ArrayList<>();
            List<Claim> claims = new ArrayList<>();
            List<Expertise> expertises = new ArrayList<>();

            roles.add(new Role(1L, RoleList.ADMINISTRATOR));
            roles.add(new Role(2L, RoleList.CLIENT));
            roles.add(new Role(3L, RoleList.EXPERT));
            roles.add(new Role(4L, RoleList.INSURER));

            users.add(new User(1L, "Administrator", "Administrator", "admin@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(0), 123456, true, null, "1234 Elm Street Citytown, Stateville 56789 Countryland", "4378.9021-456-78", "0400 000000"));
            users.add(new User(2L, "Client", "Client", "client@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(1), 123456, true, null, "1234 Elm Street Citytown, Stateville 56789 Countryland", "4378.9021-456-78", "0400 000000"));
            users.add(new User(3L, "Expert", "Expert", "expert@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(2), 123456, true, null, "1234 Elm Street Citytown, Stateville 56789 Countryland", "4378.9021-456-78", "0400 000000"));
            users.add(new User(4L, "Guardian", "Lite", "insurer@guardianlite.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown, Stateville 56789 Countryland", "4378.9021-456-78", "0400 000000"));
            users.add(new User(5L, "Voyager", "Protect", "insurer@voyagerprotect.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown, Stateville 56789 Countryland", "4378.9021-456-78", "0400 000000"));
            users.add(new User(6L, "Peak", "Secure", "insurer@peaksecure.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown, Stateville 56789 Countryland", "4378.9021-456-78", "0400 000000"));

            terms.add(new Term(1L, "Premium", "15 per month"));
            terms.add(new Term(2L, "Coverage", "Covers natural and accidental death"));
            terms.add(new Term(3L, "Benefit", "Lump-sum payment of $100,000 on death"));
            terms.add(new Term(4L, "Premium", "50 per month"));
            terms.add(new Term(5L, "Coverage", "Lifelong coverage with cash value accumulation"));
            terms.add(new Term(6L, "Benefit", "Cash-out option available after 20 years"));
            terms.add(new Term(7L, "Premium", "30 per month"));
            terms.add(new Term(8L, "Coverage", "Covers critical illnesses and terminal illnesses"));
            terms.add(new Term(9L, "Benefit", "Early payout option for critical illness"));
            // HEALTH Insurance Plans
            terms.add(new Term(10L, "Premium", "40 per month"));
            terms.add(new Term(11L, "Coverage", "Covers general health check-ups and hospitalization"));
            terms.add(new Term(12L, "Deductible", "$200 deductible for each claim"));
            terms.add(new Term(13L, "Premium", "70 per month"));
            terms.add(new Term(14L, "Coverage", "Includes dental, vision, and specialist consultations"));
            terms.add(new Term(15L, "Deductible", "$100 deductible for specialized care"));
            terms.add(new Term(16L, "Premium", "55 per month"));
            terms.add(new Term(17L, "Coverage", "Covers alternative medicine and therapy sessions"));
            terms.add(new Term(18L, "Deductible", "No deductible for alternative treatments"));
            terms.add(new Term(19L, "Premium", "20 per month"));
            terms.add(new Term(20L, "Coverage", "Covers fire, theft, and natural disasters"));
            terms.add(new Term(21L, "Deductible", "500 deductible for each claim"));
            terms.add(new Term(22L, "Premium", "35 per month"));
            terms.add(new Term(23L, "Coverage", "Includes coverage for property damage and personal liability"));
            terms.add(new Term(24L, "Deductible", "300 deductible for property claims"));
            terms.add(new Term(25L, "Premium", "50 per month"));
            terms.add(new Term(26L, "Coverage", "Covers all risks, including high-value items and accidental damage"));
            terms.add(new Term(27L, "Deductible", "1000 deductible for high-value claims"));
            // TRAVEL Insurance Plans
            terms.add(new Term(28L, "Premium", "15 per trip"));
            terms.add(new Term(29L, "Coverage", "Covers trip cancellations, lost baggage, and medical emergencies"));
            terms.add(new Term(30L, "Deductible", "No deductible for emergency claims"));
            terms.add(new Term(31L, "Premium", "25 per trip"));
            terms.add(new Term(32L, "Coverage", "Includes adventure sports and car rental damage cover"));
            terms.add(new Term(33L, "Deductible", "100 deductible for sports-related claims"));
            terms.add(new Term(34L, "Premium", "40 per trip"));
            terms.add(new Term(35L, "Coverage", "Comprehensive coverage including emergency evacuation and repatriation"));
            terms.add(new Term(36L, "Deductible", "No deductible for evacuation claims"));
            // PET Insurance Plans
            terms.add(new Term(37L, "Premium", "10 per month"));
            terms.add(new Term(38L, "Coverage", "Covers basic veterinary visits and vaccinations"));
            terms.add(new Term(39L, "Deductible", "50 deductible for each vet visit"));
            terms.add(new Term(40L, "Premium", "30 per month"));
            terms.add(new Term(41L, "Coverage", "Includes surgeries, chronic conditions, and emergency treatments"));
            terms.add(new Term(42L, "Deductible", "200 deductible for surgeries"));
            terms.add(new Term(43L, "Premium", "20 per month"));
            terms.add(new Term(44L, "Coverage", "Covers accidents and illnesses, excludes pre-existing conditions"));
            terms.add(new Term(45L, "Deductible", "100 deductible for illness treatments"));
            terms.add(new Term(46L, "Premium", "100 per month"));
            terms.add(new Term(47L, "Coverage", "Covers property damage, liability, and business interruption"));
            terms.add(new Term(48L, "Deductible", "500 deductible for property damage claims"));
            terms.add(new Term(49L, "Premium", "200 per month"));
            terms.add(new Term(50L, "Coverage", "Includes employee theft, cyber liability, and legal expenses"));
            terms.add(new Term(51L, "Deductible", "1000 deductible for legal claims"));
            terms.add(new Term(52L, "Premium", "150 per month"));
            terms.add(new Term(53L, "Coverage", "Covers commercial vehicles, equipment breakdowns, and workers' compensation"));
            terms.add(new Term(54L, "Deductible", "750 deductible for equipment claims"));
            // LIABILITY Insurance Plans
            terms.add(new Term(55L, "Premium", "20 per month"));
            terms.add(new Term(56L, "Coverage", "Covers personal injury and property damage liability"));
            terms.add(new Term(57L, "Deductible", "No deductible for liability claims"));
            terms.add(new Term(58L, "Premium", "30 per month"));
            terms.add(new Term(59L, "Coverage", "Includes professional liability and legal defense costs"));
            terms.add(new Term(60L, "Deductible", "500 deductible for legal defense"));
            terms.add(new Term(61L, "Premium", "40 per month"));
            terms.add(new Term(62L, "Coverage", "Covers all forms of liability including slander and libel"));
            terms.add(new Term(63L, "Deductible", "1000 deductible for slander/libel claims"));
            // DISABILITY Insurance Plans
            terms.add(new Term(64L, "Premium", "15 per month"));
            terms.add(new Term(65L, "Coverage", "Covers short-term disability due to illness or injury"));
            terms.add(new Term(66L, "Benefit", "Pays 60% of salary for up to 6 months"));
            terms.add(new Term(67L, "Premium", "25 per month"));
            terms.add(new Term(68L, "Coverage", "Includes long-term disability with rehabilitation benefits"));
            terms.add(new Term(69L, "Benefit", "Pays 50% of salary for up to 5 years"));
            terms.add(new Term(70L, "Premium", "20 per month"));
            terms.add(new Term(71L, "Coverage", "Covers disability due to accidents, excludes pre-existing conditions"));
            terms.add(new Term(72L, "Benefit", "Lump-sum payment based on injury severity"));
            // RENTERS Insurance Plans
            terms.add(new Term(73L, "Premium", "10 per month"));
            terms.add(new Term(74L, "Coverage", "Covers personal property against theft and damage"));
            terms.add(new Term(75L, "Deductible", "200 deductible for property claims"));
            terms.add(new Term(76L, "Premium", "20 per month"));
            terms.add(new Term(77L, "Coverage", "Includes liability coverage and temporary living expenses"));
            terms.add(new Term(78L, "Deductible", "300 deductible for liability claims"));
            terms.add(new Term(79L, "Premium", "15 per month"));
            terms.add(new Term(80L, "Coverage", "Covers high-value items like electronics and jewelry"));
            terms.add(new Term(81L, "Deductible", "500 deductible for high-value item claims"));
            terms.add(new Term(82L, "Premium", "20 per month"));
            terms.add(new Term(83L, "Coverage", "Covers both domestic and international travel"));
            terms.add(new Term(84L, "Deductible", "300 deductible for travel claims"));
            terms.add(new Term(85L, "Premium", "25 per month"));
            terms.add(new Term(86L, "Coverage", "Includes roadside assistance and towing"));
            terms.add(new Term(87L, "Deductible", "No deductible for roadside assistance"));
            terms.add(new Term(88L, "Premium", "18 per month"));
            terms.add(new Term(89L, "Coverage", "Covers accidental damage and theft"));
            terms.add(new Term(90L, "Deductible", "250 deductible for accidental damage"));
            terms.add(new Term(91L, "Premium", "30 per month"));
            terms.add(new Term(92L, "Coverage", "Comprehensive coverage including natural disasters"));
            terms.add(new Term(93L, "Deductible", "1000 deductible for natural disasters"));
            terms.add(new Term(94L, "Premium", "22 per month"));
            terms.add(new Term(95L, "Coverage", "Covers mechanical failures and electrical issues"));
            terms.add(new Term(96L, "Deductible", "200 deductible for mechanical repairs"));
            terms.add(new Term(97L, "Premium", "35 per month"));
            terms.add(new Term(98L, "Coverage", "Full coverage including collision and liability"));
            terms.add(new Term(99L, "Deductible", "500 deductible for collision"));
            terms.add(new Term(100L, "Premium", "40 per month"));
            terms.add(new Term(101L, "Coverage", "Extended coverage for family members"));
            terms.add(new Term(102L, "Deductible", "400 deductible for family claims"));
            terms.add(new Term(103L, "Premium", "17 per month"));
            terms.add(new Term(104L, "Coverage", "Basic coverage for third-party liability"));
            terms.add(new Term(105L, "Deductible", "No deductible for third-party claims"));
            terms.add(new Term(106L, "Premium", "50 per month"));
            terms.add(new Term(107L, "Coverage", "Premium plan with all-inclusive coverage"));
            terms.add(new Term(108L, "Deductible", "700 deductible for all claims"));

            insurances.add(new Insurance(1L, "Standard Life Protection Plan", InsuranceType.LIFE, 100000.00, users.get(3), terms.subList(0, 3)));
            insurances.add(new Insurance(2L, "Whole Life Premium Plan", InsuranceType.LIFE, 300000.00, users.get(4), terms.subList(3, 6)));
            insurances.add(new Insurance(3L, "Critical Illness Life Plan", InsuranceType.LIFE, 200000.00, users.get(5), terms.subList(6, 9)));
            insurances.add(new Insurance(4L, "Basic Health Coverage", InsuranceType.HEALTH, 50000.00, users.get(3), terms.subList(9, 12)));
            insurances.add(new Insurance(5L, "Comprehensive Health Plan", InsuranceType.HEALTH, 100000.00, users.get(4), terms.subList(12, 15)));
            insurances.add(new Insurance(6L, "Alternative Health Care Plan", InsuranceType.HEALTH, 75000.00, users.get(5), terms.subList(15, 18)));
            insurances.add(new Insurance(7L, "Basic Home Protection", InsuranceType.HOME, 100000.00, users.get(3), terms.subList(18, 21)));
            insurances.add(new Insurance(8L, "Comprehensive Home Insurance", InsuranceType.HOME, 200000.00, users.get(4), terms.subList(21, 24)));
            insurances.add(new Insurance(9L, "Premium Home Coverage Plan", InsuranceType.HOME, 300000.00, users.get(5), terms.subList(24, 27)));
            insurances.add(new Insurance(10L, "Basic Travel Insurance", InsuranceType.TRAVEL, 5000.00, users.get(3), terms.subList(27, 30)));
            insurances.add(new Insurance(11L, "Adventure Travel Plan", InsuranceType.TRAVEL, 10000.00, users.get(4), terms.subList(30, 33)));
            insurances.add(new Insurance(12L, "Premium Global Travel Insurance", InsuranceType.TRAVEL, 20000.00, users.get(5), terms.subList(33, 36)));
            insurances.add(new Insurance(13L, "Basic Pet Care Plan", InsuranceType.PET, 5000.00, users.get(3), terms.subList(36, 39)));
            insurances.add(new Insurance(14L, "Comprehensive Pet Health Plan", InsuranceType.PET, 15000.00, users.get(4), terms.subList(39, 42)));
            insurances.add(new Insurance(15L, "Accident & Illness Pet Plan", InsuranceType.PET, 10000.00, users.get(5), terms.subList(42, 45)));
            insurances.add(new Insurance(16L, "Small Business Coverage Plan", InsuranceType.BUSINESS, 50000.00, users.get(3), terms.subList(45, 48)));
            insurances.add(new Insurance(17L, "Comprehensive Business Protection", InsuranceType.BUSINESS, 100000.00, users.get(4), terms.subList(48, 51)));
            insurances.add(new Insurance(18L, "Advanced Business Insurance", InsuranceType.BUSINESS, 75000.00, users.get(5), terms.subList(51, 54)));
            insurances.add(new Insurance(19L, "Personal Liability Plan", InsuranceType.LIABILITY, 30000.00, users.get(3), terms.subList(54, 57)));
            insurances.add(new Insurance(20L, "Professional Liability Coverage", InsuranceType.LIABILITY, 50000.00, users.get(4), terms.subList(57, 60)));
            insurances.add(new Insurance(21L, "Comprehensive Liability Protection", InsuranceType.LIABILITY, 100000.00, users.get(5), terms.subList(60, 63)));
            insurances.add(new Insurance(22L, "Short-term Disability Plan", InsuranceType.DISABILITY, 20000.00, users.get(3), terms.subList(63, 66)));
            insurances.add(new Insurance(23L, "Long-term Disability Coverage", InsuranceType.DISABILITY, 50000.00, users.get(4), terms.subList(66, 69)));
            insurances.add(new Insurance(24L, "Accident Disability Plan", InsuranceType.DISABILITY, 30000.00, users.get(5), terms.subList(69, 72)));
            insurances.add(new Insurance(25L, "Basic Renters Insurance", InsuranceType.RENTERS, 10000.00, users.get(3), terms.subList(72, 75)));
            insurances.add(new Insurance(26L, "Comprehensive Renters Plan", InsuranceType.RENTERS, 20000.00, users.get(4), terms.subList(75, 78)));
            insurances.add(new Insurance(27L, "Premium Renters Coverage", InsuranceType.RENTERS, 30000.00, users.get(5), terms.subList(78, 81)));
            insurances.add(new Insurance(28L, "Domestic Travel Car Insurance", InsuranceType.CAR, 15000.00, users.get(3), terms.subList(81, 84)));
            insurances.add(new Insurance(29L, "Roadside Assistance Car Plan", InsuranceType.CAR, 18000.00, users.get(4), terms.subList(84, 87)));
            insurances.add(new Insurance(30L, "Accidental Damage Car Insurance", InsuranceType.CAR, 20000.00, users.get(5), terms.subList(87, 90)));
            insurances.add(new Insurance(31L, "Natural Disaster Car Coverage", InsuranceType.CAR, 25000.00, users.get(3), terms.subList(90, 93)));
            insurances.add(new Insurance(32L, "Mechanical Failure Car Insurance", InsuranceType.CAR, 22000.00, users.get(4), terms.subList(93, 96)));
            insurances.add(new Insurance(33L, "Full Coverage Car Insurance", InsuranceType.CAR, 27000.00, users.get(5), terms.subList(96, 99)));
            insurances.add(new Insurance(34L, "Family Car Insurance Plan", InsuranceType.CAR, 30000.00, users.get(3), terms.subList(99, 102)));
            insurances.add(new Insurance(35L, "Third-Party Liability Car Insurance", InsuranceType.CAR, 17000.00, users.get(4), terms.subList(102, 105)));
            insurances.add(new Insurance(36L, "All-Inclusive Car Insurance Premium", InsuranceType.CAR, 35000.00, users.get(5), terms.subList(105, 108)));

            subscriptions.add(new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), false, users.get(1), insurances.get(0), Collections.emptyList(), Collections.emptyList()));
            subscriptions.add(new Subscription(2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), false, users.get(1), insurances.get(1), Collections.emptyList(), Collections.emptyList()));
            subscriptions.add(new Subscription(3L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(12)), false, users.get(1), insurances.get(2), Collections.emptyList(), Collections.emptyList()));

            claims.add(new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.IN_PROGRESS.toString(), "assets/clio.jpeg", null));
            claims.add(new Claim(2L, "Crash Citroen C3",  Date.valueOf(LocalDate.now().plusDays(12)), ClaimStatus.PENDING.toString(), "assets/citroen.jpeg", null));
            claims.add(new Claim(3L, "Crash Peugeut 206",  Date.valueOf(LocalDate.now().plusDays(16)), ClaimStatus.WAITING_FOR_EXPERT.toString(), "assets/peugeot.jpeg", null));

            expertises.add(new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, claims.get(0)));
            expertises.add(new Expertise(2L, "Wheel damage", Date.valueOf(LocalDate.now().plusDays(13)), 1200.00, claims.get(1)));
            expertises.add(new Expertise(3L, "Hood damage", Date.valueOf(LocalDate.now().plusDays(18)), 700.00, claims.get(2)));

            roleRepository.saveAll(roles);
            userRepository.saveAll(users);
            termRepository.saveAll(terms);
            insuranceRepository.saveAll(insurances);
            subscriptionRepository.saveAll(subscriptions);
            claimRepository.saveAll(claims);
            expertiseRepository.saveAll(expertises);
        };
    }
}
