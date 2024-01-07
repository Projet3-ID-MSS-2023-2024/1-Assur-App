package be.helha.assurapp.insurance.services;

import be.helha.assurapp.insurance.enums.PaymentStatus;
import be.helha.assurapp.insurance.models.Payment;
import be.helha.assurapp.insurance.repositories.PaymentRepository;
import be.helha.assurapp.insurance.services.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void getAllPaymentsSuccess() {
        List<Payment> payments = Arrays.asList(
                new Payment(1L, 200.00, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED),
                new Payment(2L, 270.00, Date.valueOf(LocalDate.now()), PaymentStatus.PENDING)
        );
        when(repository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAll();

        assertEquals(payments, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getOnePaymentSuccess() {
        Long id = 1L;
        Payment payment = new Payment(1L, 200.00, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED);
        when(repository.findById(id)).thenReturn(Optional.of(payment));

        Payment result = paymentService.getOne(id);

        assertEquals(payment, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getByClientSuccess() {
        Long clientId = 1L;
        List<Payment> payments = Arrays.asList(
                new Payment(1L, 200.00, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED),
                new Payment(2L, 270.00, Date.valueOf(LocalDate.now()), PaymentStatus.PENDING)
        );
        when(repository.findPaymentsByClient(clientId)).thenReturn(payments);

        List<Payment> result = paymentService.getByClient(clientId);

        assertEquals(payments, result);
        verify(repository, times(1)).findPaymentsByClient(clientId);
    }

    @Test
    void getByInsurerSuccess() {
        Long insurerId = 1L;
        List<Payment> payments = Arrays.asList(
                new Payment(1L, 200.00, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED),
                new Payment(2L, 270.00, Date.valueOf(LocalDate.now()), PaymentStatus.PENDING)
        );
        when(repository.findPaymentsByInsurer(insurerId)).thenReturn(payments);

        List<Payment> result = paymentService.getByInsurer(insurerId);

        assertEquals(payments, result);
        verify(repository, times(1)).findPaymentsByInsurer(insurerId);
    }

    @Test
    void addPaymentSuccess() {
        Payment payment = new Payment(1L, 200.00, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED);
        when(repository.save(payment)).thenReturn(payment);

        Payment result = paymentService.add(payment);

        assertEquals(payment, result);
        verify(repository, times(1)).save(payment);
    }

    @Test
    void updatePaymentSuccess() {
        Payment payment = new Payment(1L, 200.00, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED);
        when(repository.save(payment)).thenReturn(payment);

        Payment result = paymentService.update(payment);

        assertEquals(payment, result);
        verify(repository, times(1)).save(payment);
    }

    @Test
    void deletePaymentSuccess() {
        Long id = 1L;
        Payment payment = new Payment(1L, 200.00, Date.valueOf(LocalDate.now()), PaymentStatus.COMPLETED);
        when(repository.save(payment)).thenReturn(payment);

        paymentService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
