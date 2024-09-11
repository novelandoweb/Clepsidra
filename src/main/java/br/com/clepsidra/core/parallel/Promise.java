package br.com.clepsidra.core.parallel;

import lombok.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@FunctionalInterface
public interface Promise<T extends Runnable> {
    T then( @NonNull String taskDescription);

    default void exec(@NonNull String taskDescription){
        getVirtualThread().submit(this.then(taskDescription));
    }
    static ExecutorService getVirtualThread(){
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
