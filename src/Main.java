import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int QUANTUM = 1000;
    private static final int[] TemposExecucao = {10000, 5000, 7000, 3000, 3000, 8000, 2000, 5000, 4000, 10000};
    private static final Random random = new Random();

    // Grava os dados do processo no txt.
    private static void logProcessoTxt(List<Processo> processos) {
        try (FileWriter writer = new FileWriter("TabelaProcessos.txt")) {
            for (Processo processo : processos) {
                writer.write(processo.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Processo> processos = new ArrayList<>();

        for (int i = 0; i < TemposExecucao.length; i++) {
            processos.add(new Processo(i, 0));
        }

        logProcessoTxt(processos);

        boolean finalizado = false;

        while (!finalizado) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Processo processo : processos) {
                if (!processo.getEstado().equals("FINALIZADO")) {
                    finalizado = false;

                    if (processo.getEstado().equals("BLOQUEADO")) {
                        if (random.nextInt(100) < 30) {
                            processo.updateEstado("PRONTO");
                            processo.logEstadoProceso("BLOQUEADO >>> PRONTO");
                            logProcessoTxt(processos);
                        }

                    } else if (processo.getEstado().equals("PRONTO")) {
                        processo.updateEstado("EXECUTANDO");
                        processo.incrementNumCPU();
                        processo.logEstadoProceso("PRONTO >>> EXECUTANDO");
                        logProcessoTxt(processos);

                        for (int cycle = 0; cycle < QUANTUM; cycle++) {
                            processo.incrementCP();

                            if (random.nextInt(100) < 1) {
                                processo.incrementNES();
                                processo.updateEstado("BLOQUEADO");
                                processo.logEstadoProceso("EXECUTANDO >>> BLOQUEADO");
                                logProcessoTxt(processos);
                                break;
                            }

                            processo.incrementTP(1);

                            if (processo.getTp() >= TemposExecucao[processo.getPid()]) {
                                processo.updateEstado("FINALIZADO");
                                processo.logEstadoProceso("EXECUTANDO >>> FINALIZADO");
                                logProcessoTxt(processos);
                                System.out.println(processo);
                                break;
                            }

                            if (cycle == QUANTUM - 1) {
                                processo.updateEstado("PRONTO");
                                processo.logEstadoProceso("EXECUTANDO >>> PRONTO");
                                logProcessoTxt(processos);
                            }
                        }
                    }
                } else {
                    finalizado = true;
                }
            }
        }
    }
}