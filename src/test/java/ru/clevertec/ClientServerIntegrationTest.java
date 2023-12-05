package ru.clevertec;

import org.junit.jupiter.api.Test;
import ru.clevertec.client.Client;
import ru.clevertec.data.Request;
import ru.clevertec.data.Response;
import ru.clevertec.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientServerIntegrationTest {

    private static final int DATA_POOL = 20;

    private static final int THREAD_POOL = 5;

    private final Client client = new Client(DATA_POOL);

    private final Server server = new Server();

    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);

    @Test
    public void test() {
        // giver
        int expected = (1 + DATA_POOL) * (DATA_POOL / 2);
        List<Callable<Response>> tasks = new ArrayList<>();

        for (int i = 0; i < DATA_POOL; i++) {
            tasks.add(() -> {
                Request request = client.getRequest();
                return server.getResponse(request);
            });
        }

        // when
        try {
            List<Future<Response>> futures = executor.invokeAll(tasks);

            for (Future<Response> future : futures) {
                Response response = future.get();
                client.accumulate(response.getData());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }

        int actual = client.getAccumulator().get();

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }
}
