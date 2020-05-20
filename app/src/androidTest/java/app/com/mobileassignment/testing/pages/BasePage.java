package app.com.mobileassignment.testing.pages;

import android.view.View;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.instanceOf;


public class BasePage {

    protected void isVisibleView(int id) {
         onView(withId(id)).
                check(matches(isDisplayed()));
    }

    protected void isVisibleItemInViewWithText(int id, String text) {
         onView(
                allOf(withId(id),
                        withText(text)))
                .check(matches(isDisplayed()));
    }

    protected void isVisibleItemInDataAdapterViewWithText(int id, String text) {
        onData(
                allOf(withId(id),
                        withText(text)))
                .check(matches(isDisplayed()));
    }

    protected void tapOnListItemWithText(int id, String text) {
         onView(
                allOf(withId(id),
                        withText(text)))
                .perform(click());
    }

    protected void replaceItemText(int id, String text) {
         onView(withId(id))
                .perform(click(),
                        replaceText(text),
                        closeSoftKeyboard());
    }

    protected void isClickableElement(int id) {
         onView(withId(id))
                .check(matches(isClickable()));
    }

    protected void applyViewMatcher(int id, Matcher<View> matcher) {
        onView(withId(id))
                .check(matches(matcher));
    }

    protected void applyObjectMatcherOnData(int resourceId, Matcher<Object> matcher, Class adapterClass) {
        onData(instanceOf(adapterClass))
                .inAdapterView(allOf(withId(resourceId), matcher));
    }

    protected  void isNotCaseSensitive(int id, String smallSearchTerm, String capitalSearchTerm) {
        onView(withId(id))
                .perform(click(),
                        replaceText(smallSearchTerm),
                        closeSoftKeyboard());

        onView(
                allOf(withId(id),
                        withText(equalToIgnoringCase(capitalSearchTerm))))
                .check(matches(isDisplayed()));
    }

    protected void scrollList(int resourceId) {
        /**
         * onData() will scroll to the item for you, just remove scrollTo() and it should click just fine.
         *
         * scrollTo() is used only for items that are inside the ScrollView combined with onView()
         */

        onData(anything())
                .inAdapterView(withId(resourceId))
                .atPosition(100)
                .perform();

    }
}
