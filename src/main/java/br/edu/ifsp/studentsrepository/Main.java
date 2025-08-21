package br.edu.ifsp.studentsrepository;

import br.edu.ifsp.studentsrepository.dao.AlunoDAO;
import br.edu.ifsp.studentsrepository.model.Aluno;
import br.edu.ifsp.studentsrepository.services.IOService;
import br.edu.ifsp.studentsrepository.utils.JPAUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {

    // Integrantes:
    // Arthur Mascaro e Gustavo Trizotti

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        AlunoDAO alunoDAO = new AlunoDAO(entityManager);

        try {
            // --- TESTES DO DAO ---
            runDaoSmokeTests(alunoDAO);

            IOService ioService = new IOService(alunoDAO);
            ioService.start();
        } finally {
            alunoDAO.close();
        }
    }

    private static BigDecimal bd(double v) {
        return BigDecimal.valueOf(v);
    }

    private static void printAluno(Aluno a) {
        System.out.printf(
                "Aluno{id=%s, nome=%s, ra=%s, email=%s, n1=%s, n2=%s, n3=%s}%n",
                a.getId(), a.getNome(), a.getRa(), a.getEmail(),
                a.getNota1(), a.getNota2(), a.getNota3()
        );
    }

    private static void safeDelete(AlunoDAO dao, String name) {
        dao.findByName(name).ifPresent(a -> {
            try {
                dao.deleteByName(name);
            } catch (Exception ignored) { }
        });
    }

    private static void runDaoSmokeTests(AlunoDAO dao) {
        System.out.println("\n=== Iniciando smoke tests de AlunoDAO ===");

        safeDelete(dao, "Gustavo");
        safeDelete(dao, "Maria");
        safeDelete(dao, "João");

        Aluno gustavo = new Aluno("Gustavo", "RA123", "gustavo@email.com",
                bd(7.5), bd(8.0), bd(9.0));
        Aluno maria = new Aluno("Maria", "RA124", "maria@email.com",
                bd(9.0), bd(9.5), bd(10.0));
        Aluno joao = new Aluno("João", "RA125", "joao@email.com",
                bd(5.0), bd(6.5), bd(7.0));

        dao.save(gustavo);
        dao.save(maria);
        dao.save(joao);
        System.out.println("-> Salvos: Gustavo, Maria, João");

        try {
            dao.save(new Aluno("Gustavo", "RA999", "dup@email.com",
                    bd(1), bd(1), bd(1)));
            System.out.println("ERRO: era esperado EntityExistsException para nome duplicado.");
        } catch (EntityExistsException e) {
            System.out.println("-> OK: bloqueou cadastro duplicado por nome (Gustavo).");
        }

        Optional<Aluno> foundGustavo = dao.findByName("Gustavo");
        if (foundGustavo.isPresent()) {
            System.out.println("-> Encontrado por nome (Gustavo):");
            printAluno(foundGustavo.get());
        } else {
            System.out.println("ERRO: Gustavo deveria existir.");
        }

        List<Aluno> all = dao.findAll();
        System.out.println("-> Listar todos (" + all.size() + "):");
        all.forEach(Main::printAluno);

        foundGustavo.ifPresent(a -> {
            a.setEmail("gustavo+atualizado@email.com");
            a.setNota1(bd(8.5));
            a.setNota2(bd(8.75));
            a.setNota3(bd(9.25));
            dao.update(a);
        });

        System.out.println("-> Gustavo atualizado.");
        dao.findByName("Gustavo").ifPresent(Main::printAluno);

        dao.deleteByName("João");
        System.out.println("-> João deletado.");

        try {
            dao.deleteByName("Inexistente");
            System.out.println("ERRO: era esperado EntityNotFoundException ao deletar inexistente.");
        } catch (EntityNotFoundException e) {
            System.out.println("-> OK: bloqueou remoção de aluno inexistente.");
        }

        System.out.println("=== Smoke tests concluídos ===\n");
    }
}