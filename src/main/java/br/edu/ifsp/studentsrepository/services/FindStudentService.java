package br.edu.ifsp.studentsrepository.services;

import br.edu.ifsp.studentsrepository.dao.AlunoDAO;
import br.edu.ifsp.studentsrepository.model.Aluno;

import java.util.Optional;

public class FindStudentService {
    private final IOService ioService;
    private final AlunoDAO alunoDAO;

    public FindStudentService(IOService ioService) {
        this.ioService = ioService;
        this.alunoDAO = ioService.getAlunoDAO();
    }

    public void findStudent() {
        System.out.println("\n** Buscar Aluno por Nome **".toLowerCase());
        System.out.print("Digite o nome do aluno: ");
        String name = ioService.getScanner().nextLine();

        if (name == null) {
            System.out.println("Nome inválido! Tente outra vez!");
            return;
        }

        Optional<Aluno> queryStudent = alunoDAO.findByName(name);
        queryStudent.ifPresentOrElse(ioService::outputStudent, ioService::outputNotFound);
    }
}
