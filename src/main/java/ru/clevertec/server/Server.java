package ru.clevertec.server;

import lombok.RequiredArgsConstructor;
import ru.clevertec.data.Request;
import ru.clevertec.data.Response;
import ru.clevertec.shared.DelayRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


@RequiredArgsConstructor
public class Server {

    private final List<Integer> data = new ArrayList<>();

    private final ReentrantLock lock = new ReentrantLock();

    public Response getResponse(Request request) {
        lock.lock();
        try {
            DelayRegister.delayWithRange(100, 1000, TimeUnit.MILLISECONDS);
            Integer requestData = request.getData();
            data.add(requestData);
            return new Response(data.size());
        } finally {
            lock.unlock();
        }
    }

    public int dataSize() {
        return data.size();
    }
}
