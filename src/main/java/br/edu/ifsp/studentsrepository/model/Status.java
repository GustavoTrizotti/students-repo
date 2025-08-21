package br.edu.ifsp.studentsrepository.model;

public enum Status {
    PASS("Aprovado"),
    FAILED("Reprovado"),
    REMEDIAL("Recuperação");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
