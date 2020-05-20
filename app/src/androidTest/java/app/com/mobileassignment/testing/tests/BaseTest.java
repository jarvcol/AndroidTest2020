package app.com.mobileassignment.testing.tests;


import org.junit.After;
import org.junit.Before;

import androidx.test.espresso.intent.Intents;

abstract class BaseTest {

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void teardown() {
        //any teardown goes here
    }
}