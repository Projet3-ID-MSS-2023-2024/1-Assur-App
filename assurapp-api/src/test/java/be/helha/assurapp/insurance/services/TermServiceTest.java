package be.helha.assurapp.insurance.services;

import be.helha.assurapp.insurance.models.Term;
import be.helha.assurapp.insurance.repositories.TermRepository;
import be.helha.assurapp.insurance.services.impl.TermServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class TermServiceTest {

    @Mock
    private TermRepository repository;

    @InjectMocks
    private TermServiceImpl termService;

    @Test
    void getAllTermsSuccess() {
        List<Term> terms = Arrays.asList(new Term(1L, "Premium", "15 per month"), new Term(2L, "Premium", "20 per month"));
        when(repository.findAll()).thenReturn(terms);

        List<Term> result = termService.getAll();

        assertEquals(terms, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getOneTermSuccess() {
        Long id = 1L;
        Term term = new Term(1L, "Premium", "15 per month");
        when(repository.findById(id)).thenReturn(Optional.of(term));

        Term result = termService.getOne(id);

        assertEquals(term, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void addTermSuccess() {
        Term term = new Term(1L, "Premium", "15 per month");
        when(repository.save(term)).thenReturn(term);

        Term result = termService.add(term);

        assertEquals(term, result);
        verify(repository, times(1)).save(term);
    }

    @Test
    void updateTermSuccess() {
        Term term = new Term();
        when(repository.save(term)).thenReturn(term);

        Term result = termService.update(term);

        assertEquals(term, result);
        verify(repository, times(1)).save(term);
    }

    @Test
    void deleteTermSuccess() {
        Long id = 1L;

        termService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
