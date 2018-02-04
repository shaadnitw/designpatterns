package dp.singleton;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Singleton1Test {

  @Test
  public void testSingleton1() {
    Singleton1 instance = Singleton1.getInstance();
    Assert.assertNotNull("Object is null", instance);
    // make another call, it should be same as first one
    Singleton1 instance1 = Singleton1.getInstance();
    Assert.assertEquals(instance1.hashCode(), instance.hashCode());
  }

  @Test
  public void testSingleton1Multithreaded() throws InterruptedException, ExecutionException {
    Callable<Singleton1> callable = new Callable<Singleton1>() {
      @Override
      public Singleton1 call() throws Exception {
        return Singleton1.getInstance();
      }
    };

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    List<Future<Singleton1>> futures = new ArrayList<>();
    Future<Singleton1> instance1 = executorService.submit(callable);
    Future<Singleton1> instance2 = executorService.submit(callable);
    Future<Singleton1> instance3 = executorService.submit(callable);

    futures.add(instance1);futures.add(instance2);futures.add(instance3);
    // block for all of them to finish
    for(Future<Singleton1> future : futures){
      future.get();
    }

    Assert.assertEquals(instance1.get().hashCode(), instance2.get().hashCode());
    Assert.assertEquals(instance1.get().hashCode(), instance3.get().hashCode());
    Assert.assertEquals(instance2.get().hashCode(), instance3.get().hashCode());

  }
}
