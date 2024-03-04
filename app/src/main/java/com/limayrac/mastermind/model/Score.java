package com.limayrac.mastermind.model;

import java.time.LocalDate;

public class Score {
    private Integer id;
    private LocalDate date;
    private Integer nbTentative;
    private CodeCouleur codeCouleur;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNbTentative() {
        return nbTentative;
    }

    public void setNbTentative(Integer nbTentative) {
        this.nbTentative = nbTentative;
    }

    public CodeCouleur getCodeCouleur() {
        return codeCouleur;
    }

    public void setCodeCouleur(CodeCouleur codeCouleur) {
        this.codeCouleur = codeCouleur;
    }
}
