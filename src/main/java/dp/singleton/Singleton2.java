package dp.singleton;

/**
 * Thread safe Eagerly creation.
 */
public class Singleton2 {

  private static Singleton2 singleton2 = new Singleton2();

  // make a private constructor to avoid object creation using new from outside the class.
  private Singleton2() {

  }

  public static Singleton2 getInstance() {
    return singleton2;
  }
}
