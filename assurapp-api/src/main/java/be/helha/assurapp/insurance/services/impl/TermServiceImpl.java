package be.helha.assurapp.insurance.services.impl;

import be.helha.assurapp.insurance.models.Term;
import be.helha.assurapp.insurance.repositories.TermRepository;
import be.helha.assurapp.insurance.services.ITermService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TermServiceImpl implements ITermService {

    private TermRepository repository;

    @Override
    public List<Term> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Term getOne(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public Term add(Term term) {
        return this.repository.save(term);
    }

    @Override
    public Term update(Term term) {
        return this.repository.save(term);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
