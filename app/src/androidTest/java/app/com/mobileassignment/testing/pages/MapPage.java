package app.com.mobileassignment.testing.pages;

import java.util.concurrent.Callable;

import androidx.test.rule.ActivityTestRule;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class MapPage extends BasePage {


    public MapPage waitForMapToBeDisplayed(ActivityTestRule activityTestRule) {
        await()
                .atMost(30, SECONDS)
                .until(isMapIntentFinishing(activityTestRule));
        return  this;
    }

    /*
    public MainPage waitForProgressBarToDisappear(ActivityTestRule activityTestRule) {
        await()
                .atMost(30, SECONDS)
                .until(isProgressBarGone(activityTestRule));
        return  this;
    }
     */

    //MARK: Private await methods
    private Callable<Boolean> isMapIntentFinishing(ActivityTestRule activityTestRule) {
        return () -> activityTestRule.getActivity().isFinishing() && activityTestRule.getActivity() == null;
    }
}
