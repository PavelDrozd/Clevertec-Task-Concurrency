package ru.clevertec.client;

import lombok.Getter;
import ru.clevertec.data.Request;
import ru.clevertec.shared.DelayRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Client {

    private List<Integer> data;

    @Getter
    private AtomicInteger accumulator = new AtomicInteger();

    private final Random random = new Random();

    private final ReentrantLock lock = new ReentrantLock();

    public Client(int dataPool) {
        data = new ArrayList<>();
        data = initializeListData(dataPool);
    }

    public Request getRequest() {
        lock.lock();
        try {
            DelayRegister.delayWithRange(100, 500, TimeUnit.MILLISECONDS);
            int index = random.nextInt(0, data.size());
            Integer requestData = data.remove(index);
            return new Request(requestData);
        } finally {
            lock.unlock();
        }
    }

    public int dataSize() {
        return data.size();
    }

    public void accumulate(int value) {
        accumulator.addAndGet(value);
    }

    private List<Integer> initializeListData(int dataPool) {
        return IntStream.rangeClosed(1, dataPool)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
