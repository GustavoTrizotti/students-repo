package br.edu.ifsp.studentsrepository.services;

import br.edu.ifsp.studentsrepository.dao.AlunoDAO;
import br.edu.ifsp.studentsrepository.model.Aluno;

import java.util.List;

public class FindAllStudentsService {
    private final IOService ioService;
    private final AlunoDAO alunoDAO;

    public FindAllStudentsService(IOService ioService) {
        this.ioService = ioService;
        this.alunoDAO = ioService.getAlunoDAO();
    }

    public void findAllStudents() {
        List<Aluno> students = alunoDAO.findAll();
        students.forEach(this::outputStudentsWithStatus);
    }

    private void outputStudentsWithStatus(Aluno aluno) {
        ioService.outputStudent(aluno);
        System.out.printf("Média: %.2f%n", aluno.getAverage());
        System.out.printf("Situação: %s%n", aluno.getStudentStatus().getStatus());
    }
}
