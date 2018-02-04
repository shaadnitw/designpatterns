package dp.singleton;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Singleton2Test {
  @Test
  public void testSingleton2() {
    Singleton2 instance = Singleton2.getInstance();
    Assert.assertNotNull("Object is null", instance);
    // make another call, it should be same as first one
    Singleton2 instance1 = Singleton2.getInstance();
    Assert.assertEquals(instance1.hashCode(), instance.hashCode());
  }

  @Test
  public void testSingleton2Multithreaded() throws InterruptedException, ExecutionException {
    Callable<Singleton2> callable = new Callable<Singleton2>() {
      @Override
      public Singleton2 call() throws Exception {
        return Singleton2.getInstance();
      }
    };

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    List<Future<Singleton2>> futures = new ArrayList<>();
    Future<Singleton2> instance1 = executorService.submit(callable);
    Future<Singleton2> instance2 = executorService.submit(callable);
    Future<Singleton2> instance3 = executorService.submit(callable);

    futures.add(instance1);futures.add(instance2);futures.add(instance3);
    // block for all of them to finish
    for(Future<Singleton2> future : futures){
      future.get();
    }

    Assert.assertEquals(instance1.get().hashCode(), instance2.get().hashCode());
    Assert.assertEquals(instance1.get().hashCode(), instance3.get().hashCode());
    Assert.assertEquals(instance2.get().hashCode(), instance3.get().hashCode());

  }
}
