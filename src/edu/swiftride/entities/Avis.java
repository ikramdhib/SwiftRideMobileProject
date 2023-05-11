package edu.swiftride.entities;

/**
 *
 * @author sami
 */
public class Avis {

    private String commentaire;
    private int etoile;
    private int id;

    public Avis() {
    }

    public Avis(String commentaire, int etoile) {
        this.commentaire = commentaire;
        this.etoile = etoile;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setEtoile(int etoile) {
        this.etoile = etoile;
    }

    public int getId() {
        return id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public int getEtoile() {
        return etoile;
    }

    @Override
    public String toString() {
        return "Avis: commentaire : " + commentaire + "etoile : " + etoile;
    }

}
