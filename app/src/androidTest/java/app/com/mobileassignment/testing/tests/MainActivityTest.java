package app.com.mobileassignment.testing.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import app.com.mobileassignment.testing.pages.MainPage;
import app.com.mobileassignment.views.MainActivity;
import app.com.mobileassignment.views.MapActivity;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest extends BaseTest {

    /**
     * This test includes the following Acceptance Criteria on set of Tests:
     * - The cities are listed in ascending alphabetical order
     * - The cities list is scrollable
     * - The user of the application can search for specific city(ies)
     * - The user of the application can select a city to be displayed on the map
     */

    MainPage mainPage = new MainPage();

    //MARK: Test Data
    private static final String CITY_NAME_TO_TAP_ON = "A Rua, ES";
    private static final String CITY_NAME_TO_SEARCH_FOR = "Hamburg";
    private static final String CITY_NAME_WITH_COUNTRY = "Hamburg, DE";

    private static final String SEARCH_TERM_SPECIAL_CHAR = "'";
    private static final String SEARCH_RESULT_SPECIAL_CHAR = "'t Zand, NL";

    private static final String SEARCH_TERM_SPECIAL_LETTER = "é";
    private static final String SEARCH_RESULT_SPECIAL_LETTER = "Ébano, MX";

    private static final String SEARCH_TERM_CAPITAL = "hamburg";
    private static final String SEARCH_TERM_SMALL= "Hamburg";


    private static final String SEARCH_TERM_NOT_FOUND = "78";

    private static final String CITY_TO_SCROLL_TO = "Abalak, NE";

    private static final List<String> COUNTRIES = Stream.of("DE", "US").collect(Collectors.toList());


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Override
    public void setup() {
        super.setup();
        mainPage
                .waitForProgressBarToDisappear(mainActivityActivityTestRule);
    }

    @Test
    public void testAppIsOpenedSuccessfully() {
        mainPage
                .waitForSearchResultsToAppear(mainActivityActivityTestRule)
                .isCitiesListDisplayed();
    }

    @Test
    public void testTapOnVisibleCity() {
        mainPage.tapOnCity(CITY_NAME_TO_TAP_ON);
    }

    @Test
    public void testSearchOnCityByName() {
        mainPage
                .searchOnCity(CITY_NAME_TO_SEARCH_FOR)
                .searchResultsContainsCity(CITY_NAME_TO_SEARCH_FOR);
    }

    @Test
    public void testResultsListSizeWhenSearchByCity() {
        mainPage
                .searchOnCity(CITY_NAME_TO_SEARCH_FOR)
                .isSameCitySearchResults(9);
    }

    @Test
    public void testSearchOnMultipleCities() {
        mainPage
                .searchOnCity(CITY_NAME_TO_SEARCH_FOR)
                .searchResultsContainsMultipleCities(CITY_NAME_TO_SEARCH_FOR, COUNTRIES);
    }

    @Test
    public void testSearchTextFieldClickable() {
        mainPage.isSearchTextFieldClickable();
    }

    @Test
    public void testCitiesListSortedBeforeSearch() {
        mainPage.isCitiesListSortedAlphapetically();
    }

    @Test
    public void testCitiesListSortedAfterSearch() {
        mainPage
                .searchOnCity(CITY_NAME_TO_SEARCH_FOR)
                .isCitiesListSortedAlphapetically();
    }

    @Test
    public void testCitiesListIsScrollable() {
        mainPage
                .scrollListToCity()
                .isVisibleCity(CITY_TO_SCROLL_TO);
    }

    @Test
    public void testTapOnCityToShowMap() {
        mainPage.openCityMap(CITY_NAME_TO_TAP_ON);

        //TODO: Move this to Main Page and call it here
        intended(hasComponent(MapActivity.class.getName()));
    }

    @Test
    public void testAllCitiesDisplayedIfClearSearchTextField() {
        mainPage
                .searchOnCity(CITY_NAME_TO_SEARCH_FOR)
                .allCitiesDisplayedAfterClearingSearch();
    }

    @Test
    public void testSearchBySpecialNameLetters() {
        mainPage
                .searchOnCity(SEARCH_TERM_SPECIAL_LETTER)
                .isVisibleCity(SEARCH_RESULT_SPECIAL_LETTER);
    }

    @Test
    public void testSearchBySpecialChars() {
        mainPage
                .searchOnCity(SEARCH_TERM_SPECIAL_CHAR)
                .isVisibleCity(SEARCH_RESULT_SPECIAL_CHAR);
    }

    @Test
    public void testSearchNoResults() {
        mainPage
                .searchOnCity(SEARCH_TERM_NOT_FOUND)
                .isCitiesListEmpty();
    }

    @Test
    public void testSearchIgnoringCase() {
        mainPage.searchCaseInsensitive(SEARCH_TERM_SMALL,SEARCH_TERM_CAPITAL);
    }
}