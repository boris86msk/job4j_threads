package ru.job4j.cas;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
class TaskCasExampleTest {
    @Test
    public void when3PushThen3Poll() {
        TaskExampleCas<Integer> stack = new TaskExampleCas<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThat(stack.poll()).isEqualTo(3);
        assertThat(stack.poll()).isEqualTo(2);
        assertThat(stack.poll()).isEqualTo(1);
    }

    @Test
    public void when1PushThen1Poll() {
        TaskExampleCas<Integer> stack = new TaskExampleCas<>();
        stack.push(1);
        assertThat(stack.poll()).isEqualTo(1);
    }

    @Test
    public void when2PushThen2Poll() {
        TaskExampleCas<Integer> stack = new TaskExampleCas<>();
        stack.push(1);
        stack.push(2);
        assertThat(stack.poll()).isEqualTo(2);
        assertThat(stack.poll()).isEqualTo(1);
    }
}