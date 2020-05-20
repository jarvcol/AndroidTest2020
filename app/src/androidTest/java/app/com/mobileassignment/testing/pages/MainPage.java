package app.com.mobileassignment.testing.pages;

import android.view.View;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.test.rule.ActivityTestRule;
import app.com.mobileassignment.R;
import app.com.mobileassignment.model.City;

import static app.com.mobileassignment.testing.helpers.Matchers.isListEmpty;
import static app.com.mobileassignment.testing.helpers.Matchers.isSameList;
import static app.com.mobileassignment.testing.helpers.Matchers.isSameListCount;
import static app.com.mobileassignment.testing.helpers.Matchers.isSortedAlphabetically;
import static app.com.mobileassignment.testing.helpers.Matchers.withCityName;
import static app.com.mobileassignment.testing.helpers.Matchers.withCountry;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class MainPage extends BasePage {

    private int citiesList = R.id.citiesList;
    private int cityName = R.id.cityName;
    private int searchTextField = R.id.search;
    private int searchResults = R.id.results;
    private int progressBar = R.id.progress_bar;

    public void isCitiesListDisplayed() {
        isVisibleView(citiesList);
    }

    public MainPage tapOnCity(String city) {
        isVisibleItemInViewWithText(cityName, city);
        tapOnListItemWithText(cityName, city);
        return this;
    }

    public MainPage searchOnCity(String city) {
        isClickableElement(searchTextField);
        replaceItemText(searchTextField, city);
        return this;
    }

    public MainPage isSearchTextFieldClickable() {
        isClickableElement(searchTextField);
        return this;
    }

    public MainPage openCityMap(String city) {
        tapOnListItemWithText(cityName, city);
        return this;
    }

    public  MainPage isVisibleCity(String city) {
        isVisibleItemInViewWithText(cityName, city);
        return this;
    }

    public MainPage isSameCitySearchResults(int searchResultsCount) {
        applyViewMatcher(citiesList, isSameListCount(searchResultsCount));
        return this;
    }

    public MainPage isCitiesListSortedAlphapetically() {
        applyViewMatcher(citiesList, isSortedAlphabetically());
        return this;
    }

    public MainPage isCitiesListEmpty() {
        applyViewMatcher(citiesList, isListEmpty());
        return this;
    }

    public MainPage allCitiesDisplayedAfterClearingSearch() {
        applyViewMatcher(citiesList, isSameList());
        return this;
    }

    public MainPage searchResultsContainsCity(String city) {
        applyObjectMatcherOnData(citiesList, withCityName(city), City.class);
        return this;
    }

    public MainPage searchResultsContainsMultipleCities(String city, List<String> countries) {
        countries.forEach( country -> {
            applyObjectMatcherOnData(citiesList, withCountry(country), City.class);
            applyObjectMatcherOnData(citiesList, withCityName(city), City.class);
        });
        return this;
    }

    public MainPage scrollListToCity() {
        scrollList(citiesList);
        return this;
    }

    public MainPage waitForProgressBarToDisappear(ActivityTestRule activityTestRule) {
        await()
                .atMost(30, SECONDS)
                .until(isProgressBarGone(activityTestRule));
        return  this;
    }

    public MainPage waitForSearchResultsToAppear(ActivityTestRule activityTestRule) {
        await()
                .atMost(30, SECONDS)
                .until(isSearchResultsVisible(activityTestRule));
        return  this;
    }

    public MainPage searchCaseInsensitive(String smallSearchTerm, String capitalSearchTerm) {
        searchOnCity(smallSearchTerm);
        isNotCaseSensitive(cityName, smallSearchTerm, capitalSearchTerm);
        return this;
    }


    // MARK: Private Await methods
    private Callable<Boolean> isProgressBarGone(ActivityTestRule activityTestRule) {
        return () -> activityTestRule.getActivity().findViewById(progressBar).getVisibility() == View.GONE;
    }

    private Callable<Boolean> isSearchResultsVisible(ActivityTestRule activityTestRule) {
        return () -> activityTestRule.getActivity().findViewById(searchResults).getVisibility() == View.VISIBLE;
    }

}
