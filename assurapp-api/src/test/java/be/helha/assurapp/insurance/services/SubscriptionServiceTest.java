package be.helha.assurapp.insurance.services;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.models.Subscription;
import be.helha.assurapp.insurance.repositories.SubscriptionRepository;
import be.helha.assurapp.insurance.services.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository repository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    void getAllSubscriptionsSuccess() {
        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList()),
                new Subscription(2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(12)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList())
        );
        when(repository.findAll()).thenReturn(subscriptions);

        List<Subscription> result = subscriptionService.getAll();

        assertEquals(subscriptions, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getOneSubscriptionSuccess() {
        Long id = 1L;
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList());
        when(repository.findById(id)).thenReturn(Optional.of(subscription));

        Subscription result = subscriptionService.getOne(id);

        assertEquals(subscription, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getByClientSuccess() {
        Long clientId = 1L;
        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList()),
                new Subscription(2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(12)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList())
                );
        when(repository.findSubscriptionsByClient(clientId)).thenReturn(subscriptions);

        List<Subscription> result = subscriptionService.getByClient(clientId);

        assertEquals(subscriptions, result);
        verify(repository, times(1)).findSubscriptionsByClient(clientId);
    }

    @Test
    void getByInsurerSuccess() {
        Long insurerId = 1L;
        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList()),
                new Subscription(2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(12)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList())
                );
        when(repository.findSubscriptionsByInsurer(insurerId)).thenReturn(subscriptions);

        List<Subscription> result = subscriptionService.getByInsurer(insurerId);

        assertEquals(subscriptions, result);
        verify(repository, times(1)).findSubscriptionsByInsurer(insurerId);
    }

    @Test
    void getByPaymentSuccess() {
        Long paymentId = 1L;
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList());
        when(repository.findByPaymentsId(paymentId)).thenReturn(subscription);

        Subscription result = subscriptionService.getByPayment(paymentId);

        assertEquals(subscription, result);
        verify(repository, times(1)).findByPaymentsId(paymentId);
    }

    @Test
    void addSubscriptionSuccess() {
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList());
        when(repository.save(subscription)).thenReturn(subscription);

        Subscription result = subscriptionService.add(subscription);

        assertEquals(subscription, result);
        verify(repository, times(1)).save(subscription);
    }

    @Test
    void updateSubscriptionSuccess() {
        Subscription subscription = new Subscription(1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(6)), true, new User(), new Insurance(), Collections.emptyList(), Collections.emptyList());
        when(repository.save(subscription)).thenReturn(subscription);

        Subscription result = subscriptionService.update(subscription);

        assertEquals(subscription, result);
        verify(repository, times(1)).save(subscription);
    }

    @Test
    void deleteSubscriptionSuccess() {
        Long id = 1L;

        subscriptionService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
