package br.edu.ifsp.studentsrepository.services;

import br.edu.ifsp.studentsrepository.dao.AlunoDAO;
import br.edu.ifsp.studentsrepository.model.Aluno;
import br.edu.ifsp.studentsrepository.utils.AsciiHelper;

import java.math.BigDecimal;
import java.util.Scanner;

public class IOService {
    private final Scanner scanner = new Scanner(System.in);
    private final AlunoDAO alunoDAO;

    private final SaveStudentService saveService;
    private final DeleteStudentService deleteService;
    private final UpdateStudentService updateService;
    private final FindStudentService findService;
    private final FindAllStudentsService findAllService;

    public IOService(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
        saveService = new SaveStudentService(this);
        deleteService = new DeleteStudentService(this);
        updateService = new UpdateStudentService(this);
        findService = new FindStudentService(this);
        findAllService = new FindAllStudentsService(this);
    }

    public void start() {
        AsciiHelper.showTitle();

        while (true) {
            AsciiHelper.showMenu();
            System.out.print(getMenu());

            try {
                int option = serializeOption(scanner.nextLine());

                switch (option) {
                    case 1:
                        saveService.saveStudent();
                        break;
                    case 2:
                        deleteService.deleteStudent();
                        break;
                    case 3:
                        updateService.updateStudent();
                        break;
                    case 4:
                        findService.findStudent();
                        break;
                    case 5:
                        findAllService.findAllStudents();
                        break;
                    default: {
                        scanner.close();

                        System.out.println("\nFeito por Arthur e Gustavo com <3\n");
                        System.out.println("Fim do programa.\n");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Tente novamente...\n");
            }
        }
    }

    private int serializeOption(String option) {
        return Integer.parseInt(option);
    }

    private String getMenu() {
        return """
                1       Cadastrar Aluno
                2       Excluir Aluno
                3       Alterar Aluno
                4       Buscar Aluno por Nome
                5       Listar Alunos (com status aprovação)
                Outro   Fim
                
                Digite a opção desejada:""" + " ";
    }

    public AlunoDAO getAlunoDAO() {
        return alunoDAO;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void outputStudent(Aluno aluno) {
        System.out.printf("\nAluno: %s%n", aluno.getNome());
        System.out.printf("Email: %s%n", aluno.getEmail());
        System.out.printf("RA: %s%n", aluno.getRa());
        System.out.printf("Notas: %.2f - %.2f - %.2f%n", aluno.getNota1(), aluno.getNota2(), aluno.getNota3());
    }

    public Aluno inputStudent() {
        System.out.print("Digite o nome: ");
        String name = scanner.nextLine();

        System.out.print("Digite o RA: ");
        String ra = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        double g1 = readDouble("Digite a nota 1: ");
        double g2 = readDouble("Digite a nota 2: ");
        double g3 = readDouble("Digite a nota 3: ");

        BigDecimal grade1 = new BigDecimal(g1);
        BigDecimal grade2 = new BigDecimal(g2);
        BigDecimal grade3 = new BigDecimal(g3);

        return new Aluno(name, ra, email, grade1, grade2, grade3);
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.\n");
            }
        }
    }

    public void outputNotFound() {
        System.out.println("\nAluno não encontrado!\n");
    }

    public void outputAlreadyExists() {
        System.out.println("\nAluno já existe!\n");
    }
}
