package dp.singleton;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Singleton3Test {

  @Test
  public void testSingleton3() {
    Singleton3 instance = Singleton3.getInstance();
    Assert.assertNotNull("Object is null", instance);
    // make another call, it should be same as first one
    Singleton3 instance1 = Singleton3.getInstance();
    Assert.assertEquals(instance1.hashCode(), instance.hashCode());
  }

  @Test
  public void testSingleton3Multithreaded() throws InterruptedException, ExecutionException {
    Callable<Singleton3> callable = new Callable<Singleton3>() {
      @Override
      public Singleton3 call() throws Exception {
        return Singleton3.getInstance();
      }
    };

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    List<Future<Singleton3>> futures = new ArrayList<>();
    Future<Singleton3> instance1 = executorService.submit(callable);
    Future<Singleton3> instance2 = executorService.submit(callable);
    Future<Singleton3> instance3 = executorService.submit(callable);

    futures.add(instance1);futures.add(instance2);futures.add(instance3);
    // block for all of them to finish
    for(Future<Singleton3> future : futures){
      future.get();
    }

    Assert.assertEquals(instance1.get().hashCode(), instance2.get().hashCode());
    Assert.assertEquals(instance1.get().hashCode(), instance3.get().hashCode());
    Assert.assertEquals(instance2.get().hashCode(), instance3.get().hashCode());

  }
}
