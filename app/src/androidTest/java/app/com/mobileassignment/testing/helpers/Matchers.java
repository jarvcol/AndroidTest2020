package app.com.mobileassignment.testing.helpers;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.common.collect.Ordering;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

import androidx.test.espresso.matcher.BoundedMatcher;
import app.com.mobileassignment.model.City;

public class Matchers {
    /**
     * Matches a City with Name
     */
    public static Matcher<Object> withCityName(final String cityName) {
        return new BoundedMatcher<Object, City>(City.class) {
            @Override
            protected boolean matchesSafely(City city) {
                return cityName == city.getName();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("city with name: " + cityName);
            }
        };
    }

    /**
     * Matches a City with Country
     */

    public static Matcher<Object> withCountry(final String country) {
        return new BoundedMatcher<Object, City>(City.class) {
            @Override
            protected boolean matchesSafely(City city) {
                return country == city.getCountry();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with country: " + country);
            }
        };
    }

    /**
     * Matches view list is empty
     */
    public static Matcher<View> isListEmpty() {
        return new TypeSafeMatcher<View>() {

            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;
                ListAdapter citiesAdapter = listView.getAdapter();
                return citiesAdapter.isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("search for cities has no results ");
            }
        };
    }

    /**
     * Matches results list count
     */
    public static Matcher<View> isSameListCount(final int size) {
        return new TypeSafeMatcher<View>() {
            int listSize;

            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;
                ListAdapter citiesAdapter = listView.getAdapter();
                listSize = citiesAdapter.getCount();
                return listSize == size;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("search on city should produce " + size + "but got " + listSize);
            }
        };
    }

    /**
     * Matches results list sorted alphabetically
     */
    public static Matcher<View> isSortedAlphabetically() {
        return new TypeSafeMatcher<View>() {

            private final List<City> cities = new ArrayList<>();

            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;
                ListAdapter citiesAdapter = listView.getAdapter();
                cities.clear();
                cities.addAll(getCities(citiesAdapter));

                List<String> citiesNames = new ArrayList<>();

                for(City city : cities) {
                    citiesNames.add(city.getName());
                }

                return Ordering.natural().isOrdered(citiesNames);
            }

            private List<City> getCities(ListAdapter citiesAdapter) {
                List<City> cities = new ArrayList<>();
                for (int i = 0; i < citiesAdapter.getCount(); i++) {
                    cities.add((City) citiesAdapter.getItem(i));
                }
                return cities;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has items sorted alphabetically");
            }
        };
    }

    /**
     * Matches results list has same count after clearing search
     */
    public static Matcher<View> isSameList() {
        return new TypeSafeMatcher<View>() {

            private final List<City> cities = new ArrayList<>();

            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;
                ListAdapter citiesAdapter = listView.getAdapter();
                cities.clear();
                cities.addAll(getCitiesNames(citiesAdapter));
                int citiesCount = cities.size();
                return citiesCount == getCitiesListCount(citiesAdapter);
            }

            private int getCitiesListCount(ListAdapter citiesAdapter) {
                return citiesAdapter.getCount();
            }

            private List<City> getCitiesNames(ListAdapter citiesAdapter) {
                List<City> citiesNames = new ArrayList<>();
                for (int i = 0; i < citiesAdapter.getCount(); i++) {
                    citiesNames.add((City) citiesAdapter.getItem(i));
                }
                return citiesNames;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("List has same count as before search " + cities);
            }
        };
    }
}
