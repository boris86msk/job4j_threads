package ru.job4j.cas;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class CountCasTest {

    @Test
    public void wenTwoThrowsIncrementOneCounter() throws InterruptedException {
        CountCas countCas = new CountCas();
        Thread firstThread = new Thread(
                () -> {
                    IntStream.range(0,5)
                            .forEach(i -> countCas.increment());
                }
        );
        Thread secondThread = new Thread(
                () -> {
                    IntStream.range(0,5)
                            .forEach(i -> countCas.increment());
                }
        );
        Thread thirdThread = new Thread(
                () -> {
                    IntStream.range(0,5)
                            .forEach(i -> countCas.increment());
                }
        );
        firstThread.start();
        secondThread.start();
        thirdThread.start();
        Thread.sleep(10);
        assertThat(countCas.get()).isEqualTo(15);
    }
}