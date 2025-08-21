package br.edu.ifsp.studentsrepository.services;

import br.edu.ifsp.studentsrepository.dao.AlunoDAO;
import br.edu.ifsp.studentsrepository.model.Aluno;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public class UpdateStudentService {
    private final IOService ioService;
    private final AlunoDAO alunoDAO;

    public UpdateStudentService(IOService ioService) {
        this.ioService = ioService;
        this.alunoDAO = ioService.getAlunoDAO();
    }

    public void updateStudent() {
        System.out.println("\n** Alterar Aluno **".toLowerCase());
        System.out.print("Digite o nome do aluno: ");
        String name = ioService.getScanner().nextLine();

        if (name == null) {
            System.out.println("Nome inválido! Tente outra vez!");
            return;
        }

        Optional<Aluno> found = alunoDAO.findByName(name);
        if (found.isEmpty()) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        System.out.println("Dados:");
        Aluno original = found.get();
        ioService.outputStudent(original);

        System.out.println("Digite os novos dados do aluno:");
        Aluno updated = ioService.inputStudent();

        try {
            alunoDAO.update(original.merge(updated));
            System.out.println("\nAluno atualizado com sucesso!");
        } catch (EntityNotFoundException e) {
            ioService.outputNotFound();
        }
    }
}
