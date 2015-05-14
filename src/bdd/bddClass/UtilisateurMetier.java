package bdd.bddClass;

import bdd.DAO;

/**
 * Created by David on 22/04/2015.
 */
public class UtilisateurMetier  {
    private int numero;
    private int code;
    private boolean tech;

    public UtilisateurMetier () {}

    public UtilisateurMetier(int numero, int code , boolean t) {
        this.numero = numero;
        this.code = code;
        this.tech=t;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean estTechnicien() { return tech; }

    public void notifier(StationMetier st){
        if(this.estTechnicien()){
            System.out.println("L\'utilisateur : "+this.toString()+" Doit se rendre à la station " + st.toString());
        }
    }

    @Override
    public String toString() {
        return "UtilisateurMetier{" +
                "numero=" + numero +
                ", code=" + code +
                ", Technicien ? : " + tech +
                '}';
    }
}

