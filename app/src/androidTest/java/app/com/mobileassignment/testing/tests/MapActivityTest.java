package app.com.mobileassignment.testing.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import app.com.mobileassignment.IdlingResource.CountingIdlingResourceSingleton;
import app.com.mobileassignment.views.MainActivity;
import app.com.mobileassignment.views.MapActivity;

import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;

public class MapActivityTest extends BaseTest {

    /**
     * This test includes the following Acceptance Criteria on set of Tests:
     * - The user of the application can interact with the map (i.e zoom in/out, move to the left/right)
     * ------There was a problem with my emulator and google map service that I could not solve------
     */

    @Rule
    public IntentsTestRule<MapActivity> mapIntentsTestRule = new IntentsTestRule<>(MapActivity.class);

    @Override
    public void setup() {
        IdlingRegistry.getInstance().register(CountingIdlingResourceSingleton.countingIdlingResource);
    }

    @Override
    public void teardown() {
        IdlingRegistry.getInstance().unregister(CountingIdlingResourceSingleton.countingIdlingResource);
    }

    @Test
    public void testMapShownWithExtraInfo() {
        // TODO: put this in base page or Map Page
        Intent cityData = new Intent();

        cityData.putExtra(MainActivity.COORDINATES_LAT, "lat");
        cityData.putExtra(MainActivity.COORDINATES_LON, "lon");

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, cityData);

        intending(toPackage(MapActivity.class.getName())).respondWith(result);
    }

    @Test
    public void testZoomInMap() {
        //TODO
    }

    @Test
    public void testZoomOutMap() {
        //TODO
    }

    @Test
    public void testMoveMapLeft() {
        //TODO
    }

    @Test
    public void testMoveMapRight() {
        //TODO
    }
}
