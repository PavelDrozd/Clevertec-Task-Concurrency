package ru.clevertec.shared;

import ru.clevertec.exception.ConcurrencyException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DelayRegister {

    private final static Random random = new Random();

    public static void delayWithRange(long from, long to, TimeUnit timeUnit) {
        try {
            long time = random.nextLong(from, to);
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            throw new ConcurrencyException("Impossibly to register a delay in: " + timeUnit +
                                           " from: " + from +
                                           " to: " + to +
                                           e);
        }
    }
}
