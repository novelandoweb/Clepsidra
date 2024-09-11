package br.com.clepsidra.core.parallel;

import java.time.Duration;

public class TestePromisse {
    public static void main(String[] args) {
        var timeOfTest = System.currentTimeMillis();
        var promises = new Promises();
        for (int i = 1; i < 101; i++) {
            promises.add("SQL " + i,
                    (taskDesc) -> () -> System.out.printf("Tarefa: %s, executada!\n", taskDesc));
        }
        promises.thenAll();
        System.out.println(Duration.ofMillis(System.currentTimeMillis() - timeOfTest).toSeconds() + " seconds");
    }

}
