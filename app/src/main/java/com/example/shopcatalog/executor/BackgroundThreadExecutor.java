package com.example.shopcatalog.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundThreadExecutor implements Executor {
    private ExecutorService executorService =
            Executors.newFixedThreadPool(2);

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }
}