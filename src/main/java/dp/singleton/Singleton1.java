package dp.singleton;

/**
 * @author sahmad
 */
public class Singleton1 {
  private static Singleton1 singleObject;
  // make a private constructor to avoid object creation using new from outside the class.
  private Singleton1() {

  }

  public static synchronized Singleton1 getInstance() {
    if(singleObject == null){
      singleObject = new Singleton1();
    }

    return singleObject;
  }

}
