package utils;

import java.util.concurrent.*;

public class Debouncer {
    // A single scheduler shared across the whole app
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture<?> delayedTask;

    /**
     * Static debounce method. 
     * Note: synchronized ensures that if two threads call this at once, 
     * they don't both schedule a task simultaneously.
     */
    public static synchronized void debounce(Runnable task, long delay, TimeUnit unit) {
        // Cancel the previous task if it exists and hasn't finished
        if (delayedTask != null && !delayedTask.isDone()) {
            delayedTask.cancel(false);
        }

        // Schedule the new task
        delayedTask = scheduler.schedule(task, delay, unit);
    }

    // Call this only when the app is shutting down completely
    public static void shutdown() {
        scheduler.shutdown();
    }
}