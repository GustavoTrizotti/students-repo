package br.edu.ifsp.studentsrepository.services;

import br.edu.ifsp.studentsrepository.dao.AlunoDAO;
import br.edu.ifsp.studentsrepository.model.Aluno;
import jakarta.persistence.EntityExistsException;

public class SaveStudentService {
    private final IOService ioService;
    private final AlunoDAO alunoDAO;

    public SaveStudentService(IOService ioService) {
        this.ioService = ioService;
        this.alunoDAO = ioService.getAlunoDAO();
    }

    public void saveStudent() {
        System.out.println("\n** Cadastrar Aluno **".toUpperCase());
        Aluno aluno = ioService.inputStudent();
        try {
            alunoDAO.save(aluno);
            System.out.println("\nAluno adicionado com sucesso!");
        } catch (EntityExistsException e) {
            ioService.outputAlreadyExists();
        }
    }
}
