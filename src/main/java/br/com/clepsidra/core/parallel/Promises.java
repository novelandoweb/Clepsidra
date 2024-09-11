package br.com.clepsidra.core.parallel;

import java.util.ArrayList;
import java.util.List;

public class Promises {
    private final List<TaskPromise> promises;

    public Promises() {
        this.promises = new ArrayList<>();
    }

    public void add(String taskDescription, Promise<Runnable> promise) {
        this.promises.add(new TaskPromise(taskDescription, promise));
    }

    public void thenAll() {
        try{
            promises.forEach(taskPromise -> taskPromise.promise.exec(taskPromise.taskDescription));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class TaskPromise {
        String taskDescription;
        Promise<Runnable> promise;

        TaskPromise(String taskDescription, Promise<Runnable> promise) {
            this.taskDescription = taskDescription;
            this.promise = promise;
        }
    }
}

