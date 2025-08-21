package br.edu.ifsp.studentsrepository.dao;

import br.edu.ifsp.studentsrepository.model.Aluno;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class AlunoDAO {
    private final EntityManager em;

    public AlunoDAO(EntityManager em) {
        this.em = em;
    }

    public Optional<Aluno> findByName(String name) {
        String sanitizedName = name.trim().toLowerCase();
        Aluno query = em.createQuery("SELECT a FROM Aluno a WHERE LOWER(a.nome) = :name", Aluno.class)
                .setParameter("name", sanitizedName)
                .getSingleResultOrNull();
        return Optional.ofNullable(query);
    }

    public void save(Aluno aluno) {
        if (findByName(aluno.getNome()).isPresent())
            throw new EntityExistsException(String.format("Aluno \"%s\" já existe!", aluno.getNome()));

        em.getTransaction().begin();
        this.em.persist(aluno);
        em.getTransaction().commit();
    }

    public void deleteByName(String name) {
        if (findByName(name).isEmpty())
            throw new EntityNotFoundException(String.format("Aluno \"%s\" não existe!", name));

        em.getTransaction().begin();
        this.em.remove(findByName(name).get());
        em.getTransaction().commit();
    }

    public void update(Aluno aluno) {
        em.getTransaction().begin();
        this.em.merge(aluno);
        em.getTransaction().commit();
    }

    public List<Aluno> findAll() {
        return em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
    }

    public void close() {
        this.em.close();
    }
}
