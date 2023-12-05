package ru.clevertec.server;

import org.junit.jupiter.api.Test;
import ru.clevertec.data.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class ServerTest {

    private final Server server = new Server();

    @Test
    void getResponseShouldReturnExpectedDataSize() {
        // given
        int dataPool = 20;
        List<Request> requests = IntStream.rangeClosed(1, dataPool)
                .boxed()
                .map(Request::new)
                .collect(Collectors.toCollection(ArrayList::new));
        int expected = 20;

        // when
        for (Request request : requests) {
            server.getResponse(request);
        }
        int actual = server.dataSize();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }
}