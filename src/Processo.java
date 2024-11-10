public class Processo {
    private int pid;
    private int tp;
    private int cp;
    private String ep;
    private int nes;
    private int numCpu;

    public Processo(int pid, int tp) {
        this.pid = pid;
        this.tp = 0; // Inicializa tp como 0
        this.cp = tp + 1;
        this.ep = "PRONTO";
        this.nes = 0;
        this.numCpu = 0;
    }

    public void updateEstado(String novoEstado) {
        this.ep = novoEstado;
    }

    public void incrementTP(int value) {
        this.tp += value;
        this.cp = this.tp + 1;
    }

    public void incrementNES() {
        this.nes++;
    }

    public void incrementNumCPU() {
        this.numCpu++;
    }

    public void incrementCP() {
        this.cp++;
    }

    public void logEstadoProceso(String transicao) {
        System.out.println("PID: " + pid
                + ", TP: " + tp
                + ", CP: " + cp
                + ", NES: " + nes
                + ", Num_CPU: " + numCpu
                + ", " + transicao
                + ", EP: " + ep);
    }

    public String getEstado() {
        return ep;
    }

    public int getTp() {
        return tp;
    }

    public int getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "PID: " + pid
                + ", TP: " + tp
                + ", CP: " + cp
                + ", NES: " + nes
                + ", Num_CPU: " + numCpu
                + ", EP: " + ep;
    }
}