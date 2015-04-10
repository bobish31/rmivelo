/**
 * Created by Robin on 03/04/2015.
 */
public class Utilisateur {

    private int numero;
    private int code;

    public Utilisateur(int numero, int code) {
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
}
