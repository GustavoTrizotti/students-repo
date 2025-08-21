package br.edu.ifsp.studentsrepository.services;

import br.edu.ifsp.studentsrepository.dao.AlunoDAO;
import jakarta.persistence.EntityNotFoundException;

public class DeleteStudentService {
    private final IOService ioService;
    private final AlunoDAO alunoDAO;

    public DeleteStudentService(IOService ioService) {
        this.ioService = ioService;
        this.alunoDAO = ioService.getAlunoDAO();
    }

    public void deleteStudent() {
        System.out.println("\n** Excluir Aluno **".toLowerCase());
        System.out.print("Digite o nome do aluno: ");
        String name = ioService.getScanner().nextLine();

        if (name == null) {
            System.out.println("Nome inválido! Tente outra vez!");
            return;
        }

        try {
            alunoDAO.deleteByName(name);
            System.out.println("\nAluno removido com sucesso!");
        } catch (EntityNotFoundException e) {
            ioService.outputNotFound();
        }
    }
}
