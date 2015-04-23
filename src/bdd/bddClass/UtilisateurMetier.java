package bdd.bddClass;

/**
 * Created by David on 22/04/2015.
 */
public class UtilisateurMetier {
    private int numero;
    private int code;

    public UtilisateurMetier(int numero, int code) {
        this.numero = numero;
        this.code = code;
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

    @Override
    public String toString() {
        return "UtilisateurMetier{" +
                "numero=" + numero +
                ", code=" + code +
                '}';
    }
}

