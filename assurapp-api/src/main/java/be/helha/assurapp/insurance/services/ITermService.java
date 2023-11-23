package be.helha.assurapp.insurance.services;

import be.helha.assurapp.insurance.models.Term;

import java.util.List;

public interface ITermService {

    List<Term> getAll();
    Term getOne(Long id);
    Term add(Term term);
    Term update(Term term);
    void delete(Long id);

}
