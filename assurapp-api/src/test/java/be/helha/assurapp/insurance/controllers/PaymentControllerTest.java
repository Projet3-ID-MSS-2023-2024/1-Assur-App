package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.enums.PaymentStatus;
import be.helha.assurapp.insurance.models.Payment;
import be.helha.assurapp.insurance.models.Subscription;
import be.helha.assurapp.insurance.services.IPaymentService;
import be.helha.assurapp.insurance.services.ISubscriptionService;
import be.helha.assurapp.utils.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Mock
    private IPaymentService paymentService;

    @Mock
    private ISubscriptionService subscriptionService;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    @WithMockUser
    void getAllPaymentsSuccess() throws Exception {
        List<Payment> payments = Arrays.asList(
                new Payment(1L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED),
                new Payment(2L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED));

        when(paymentService.getAll()).thenReturn(payments);

        mockMvc.perform(get("/api/v1/payments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(payments.size()));

        verify(paymentService, times(1)).getAll();
    }

    @Test
    @WithMockUser
    void getOnePaymentSuccess() throws Exception {
        Long id = 1L;
        Payment payment = new Payment(1L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED);

        when(paymentService.getOne(id)).thenReturn(payment);

        mockMvc.perform(get("/api/v1/payments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(paymentService, times(1)).getOne(id);
    }

    @Test
    @WithMockUser
    void getByClientPaymentsSuccess() throws Exception {
        Long clientId = 1L;
        List<Payment> payments = Arrays.asList(
                new Payment(1L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED),
                new Payment(2L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED));

        when(paymentService.getByClient(clientId)).thenReturn(payments);

        mockMvc.perform(get("/api/v1/payments/client/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(payments.size()));

        verify(paymentService, times(1)).getByClient(clientId);
    }

    @Test
    @WithMockUser
    void getByInsurerPaymentsSuccess() throws Exception {
        Long insurerId = 1L;
        List<Payment> payments = Arrays.asList(
                new Payment(1L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED),
                new Payment(2L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED));

        when(paymentService.getByInsurer(insurerId)).thenReturn(payments);

        mockMvc.perform(get("/api/v1/payments/insurer/{id}", insurerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(payments.size()));

        verify(paymentService, times(1)).getByInsurer(insurerId);
    }

    @Test
    @WithMockUser
    void addPaymentSuccess() throws Exception {
        Payment payment = new Payment(1L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED);

        System.out.println(asJsonString(payment));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payment)))
                .andExpect(status().isOk());

        verify(paymentService, times(1)).add(any(Payment.class));
    }

    @Test
    @WithMockUser
    void updatePaymentSuccess() throws Exception {
        Payment payment = new Payment(1L, 220.80, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED);

        mockMvc.perform(put("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payment)))
                .andExpect(status().isOk());

        verify(paymentService, times(1)).update(any(Payment.class));
    }

    @Test
    @WithMockUser
    void deletePaymentSuccess() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/payments/{id}", id))
                .andExpect(status().isOk());

        verify(paymentService, times(1)).delete(id);
    }

    private String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
