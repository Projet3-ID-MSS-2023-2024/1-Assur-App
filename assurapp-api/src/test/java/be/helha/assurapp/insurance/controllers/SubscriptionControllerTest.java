package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.insurance.models.Subscription;
import be.helha.assurapp.insurance.services.ISubscriptionService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SubscriptionControllerTest {

    @Mock
    private ISubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
    }

    @Test
    @WithMockUser
    void getAllSubscriptionsSuccess() throws Exception {
        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList()),
                new Subscription(2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList()));

        when(subscriptionService.getAll()).thenReturn(subscriptions);

        mockMvc.perform(get("/api/v1/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(subscriptions.size()));

        verify(subscriptionService, times(1)).getAll();
    }

    @Test
    @WithMockUser
    void getOneSubscriptionSuccess() throws Exception {
        Long id = 1L;
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList());

        when(subscriptionService.getOne(id)).thenReturn(subscription);

        mockMvc.perform(get("/api/v1/subscriptions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(subscriptionService, times(1)).getOne(id);
    }

    @Test
    @WithMockUser
    void getByClientSubscriptionsSuccess() throws Exception {
        Long clientId = 1L;
        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList()),
                new Subscription(2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList()));

        when(subscriptionService.getByClient(clientId)).thenReturn(subscriptions);

        mockMvc.perform(get("/api/v1/subscriptions/client/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(subscriptions.size()));

        verify(subscriptionService, times(1)).getByClient(clientId);
    }

    @Test
    @WithMockUser
    void getByInsurerSubscriptionsSuccess() throws Exception {
        Long insurerId = 1L;
        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList()),
                new Subscription(2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList()));

        when(subscriptionService.getByInsurer(insurerId)).thenReturn(subscriptions);

        mockMvc.perform(get("/api/v1/subscriptions/insurer/{id}", insurerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(subscriptions.size()));

        verify(subscriptionService, times(1)).getByInsurer(insurerId);
    }

    @Test
    @WithMockUser
    void getByPaymentSubscriptionSuccess() throws Exception {
        Long paymentId = 1L;
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList());

        when(subscriptionService.getByPayment(paymentId)).thenReturn(subscription);

        mockMvc.perform(get("/api/v1/subscriptions/payment/{id}", paymentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(paymentId));

        verify(subscriptionService, times(1)).getByPayment(paymentId);
    }

    @Test
    @WithMockUser
    void addSubscriptionSuccess() throws Exception {
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList());

        mockMvc.perform(post("/api/v1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(subscription)))
                .andExpect(status().isOk());

        verify(subscriptionService, times(1)).add(any(Subscription.class));
    }

    @Test
    @WithMockUser
    void updateSubscriptionSuccess() throws Exception {
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), true, null, null, Collections.emptyList(), Collections.emptyList());

        mockMvc.perform(put("/api/v1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(subscription)))
                .andExpect(status().isOk());

        verify(subscriptionService, times(1)).update(any(Subscription.class));
    }

    @Test
    @WithMockUser
    void deleteSubscriptionSuccess() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/subscriptions/{id}", id))
                .andExpect(status().isOk());

        verify(subscriptionService, times(1)).delete(id);
    }

    private String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
