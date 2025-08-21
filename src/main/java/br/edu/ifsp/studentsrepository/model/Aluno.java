package br.edu.ifsp.studentsrepository.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String ra;
    private String email;
    private BigDecimal nota1;
    private BigDecimal nota2;
    private BigDecimal nota3;

    public Aluno() {
    }

    public Aluno(String nome, String ra, String email, BigDecimal nota1, BigDecimal nota2, BigDecimal nota3) {
        this.nome = nome;
        this.ra = ra;
        this.email = email;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getNota1() {
        return nota1;
    }

    public void setNota1(BigDecimal nota1) {
        this.nota1 = nota1;
    }

    public BigDecimal getNota2() {
        return nota2;
    }

    public void setNota2(BigDecimal nota2) {
        this.nota2 = nota2;
    }

    public BigDecimal getNota3() {
        return nota3;
    }

    public void setNota3(BigDecimal nota3) {
        this.nota3 = nota3;
    }

    public double getAverage() {
        return (nota1.doubleValue() + nota2.doubleValue() + nota3.doubleValue()) / 3;
    }

    public Status getStudentStatus() {
        double average = getAverage();
        if (average < 4) {
            return Status.FAILED;
        } else if (average < 6) {
            return Status.REMEDIAL;
        }
        return Status.PASS;
    }

    public Aluno merge(Aluno toMerge) {
        this.setNome(toMerge.getNome());
        this.setRa(toMerge.getRa());
        this.setEmail(toMerge.getEmail());
        this.setNota1(toMerge.getNota1());
        this.setNota2(toMerge.getNota2());
        this.setNota3(toMerge.getNota3());
        return this;
    }
}
