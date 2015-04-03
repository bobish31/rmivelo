/**
 * Created by Robin on 03/04/2015.
 */
public class Velo {

    private String identifiantVelo;
    private boolean operationnel;
    private boolean enCirculation;

    public Velo(String identifiantVelo, boolean enCirculation, boolean operationnel) {
        this.identifiantVelo = identifiantVelo;
        this.enCirculation = enCirculation;
        this.operationnel = operationnel;
    }

    public String getIdentifiantVelo() {
        return identifiantVelo;
    }

    public void setIdentifiantVelo(String identifiantVelo) {
        this.identifiantVelo = identifiantVelo;
    }

    public boolean isOperationnel() {
        return operationnel;
    }

    public void setOperationnel(boolean operationnel) {
        this.operationnel = operationnel;
    }

    public boolean isEnCirculation() {
        return enCirculation;
    }

    public void setEnCirculation(boolean enCirculation) {
        this.enCirculation = enCirculation;
    }
}
