package com.klaus.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Got the result by running supplier, then consume it.
 * <p>
 * Can add a timeout checker with <code>{@link ConsumerTask#withTimeout(long)}</code>
 * If timeout before consume normally, consume the result as null value.
 * </p>
 *
 * Created by KlausZ on 2020/8/5.
 */
public class ConsumerTask<R> {

     private static final Logger logger = LoggerFactory.getLogger(ConsumerTask.class);

    private final Supplier<R> supplier;
    private final ExecutorService supplierExecutor;
    private final ResultConsumer consumer;

    private ScheduledFuture<?> timeoutFuture;

    /**
     * Supplier run in supplierExecutor, Consumer run in consumerExecutor
     */
    public ConsumerTask(Supplier<R> supplier, ExecutorService supplierExecutor,
                        BiConsumer<R, Boolean> consumer, ExecutorService consumerExecutor) {
        this.supplier = supplier;
        this.supplierExecutor = supplierExecutor;
        this.consumer = new ResultConsumer(consumer, consumerExecutor);
    }

    /**
     * Supplier run in supplierExecutor, Consumer run directly
     */
    public ConsumerTask(Supplier<R> supplier, ExecutorService supplierExecutor, BiConsumer<R, Boolean> consumer) {
        this(supplier, supplierExecutor, consumer, null);
    }

    /**
     * Return this with timeout check
     * @param delay MILLISECONDS
     */
    public ConsumerTask<R> withTimeout(long delay) {
        initDelayer(delay);
        return this;
    }

    private void initDelayer(long delay) {
        this.timeoutFuture = Delayer.delay(new Timeout(), delay);
    }

    /**
     * submit this task into supplierExecutor
     */
    public void submit() {
        supplierExecutor.submit(this::exec);
    }

    /**
     * Causes the current thread to wait the consumer finishing
     */
    public void await() throws InterruptedException {
        submit();
        consumer.await();
    }

    private void exec() {
        consumer.accept(supplier.get(), false);
        if (timeoutFuture != null) {
            timeoutFuture.cancel(true);
        }
    }

    /** Consume the result */
    private class ResultConsumer {
        final ExecutorService executor;
        final BiConsumer<R, Boolean> consumer;
        final AtomicBoolean flag;
        CountDownLatch latch;

        private ResultConsumer(BiConsumer<R, Boolean> consumer, ExecutorService executor) {
            this.consumer = consumer;
            this.executor = executor;
            this.flag = new AtomicBoolean(false);
        }

        void accept(R result, boolean timeout) {
            if (flag.compareAndSet(false, true)) {
                if (executor != null) {
                    executor.submit(() -> consumer.accept(result, timeout));
                } else {
                    consumer.accept(result, timeout);
                }
                if (latch != null) {
                    latch.countDown();
                }
            }
        }

        /** Create a CountDownLatch and then await */
        void await() throws InterruptedException {
            latch = new CountDownLatch(1);
            latch.await();
        }
    }

    private class Timeout implements Runnable {
        @Override
        public void run() {
            ConsumerTask.this.consumer.accept(null, true);
            logger.warn("ConsumerAction exec timeout.");
        }
    }

    /** Singleton delay scheduler, used only for timeout tasks */
    static final class Delayer {
        static ScheduledFuture<?> delay(Runnable command, long delay) {
            return delayer.schedule(command, delay, TimeUnit.MILLISECONDS);
        }

        static final class DaemonThreadFactory implements ThreadFactory {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("ConsumerActionDelayScheduler");
                return t;
            }
        }

        static final ScheduledThreadPoolExecutor delayer;

        static {
            (delayer = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory()))
                    .setRemoveOnCancelPolicy(true);
        }
    }

}
