package app.com.mobileassignment.IdlingResource;

import androidx.test.espresso.idling.CountingIdlingResource;

public class CountingIdlingResourceSingleton {
    private static String RESOURCE = "GLOBAL";
    public static  CountingIdlingResource countingIdlingResource = new CountingIdlingResource(RESOURCE);

    public static void increment() {
        countingIdlingResource.increment();
    }

    public  static void decrement() {
        if(!countingIdlingResource.isIdleNow()) {
            countingIdlingResource.decrement();
        }
    }
}
