/**
 * Created by Robin on 03/04/2015.
 */
public class Utilisateur {

    private String numero;
    private int code;

    public Utilisateur(String numero, int code) {
        this.numero = numero;
        this.code = code;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
