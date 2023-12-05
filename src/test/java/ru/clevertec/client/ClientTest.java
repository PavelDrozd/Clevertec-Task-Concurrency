package ru.clevertec.client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {

    private static final int DATA_POOL = 20;

    private final Client client = new Client(DATA_POOL);

    @Test
    public void getRequestShouldReturnZeroDataSize() {
        // given
        int dataSize = client.dataSize();
        int expected = 0;

        // when
        for (int i = 0; i < dataSize; i++) {
            client.getRequest();
        }
        int actual = client.dataSize();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    public void accumulateShouldReturnExpectedValue() {
        // given
        int value = 7;
        int repeat = 5;
        int expected = 35;

        // when
        for (int i = 0; i < repeat; i++) {
            client.accumulate(value);
        }
        int actual = client.getAccumulator().get();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }
}