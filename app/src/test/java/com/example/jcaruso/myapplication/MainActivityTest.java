package com.example.jcaruso.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.example.jcaruso.myapplication.movie.MovieActivity;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private EditText username;
    private EditText password;
    private Button signin;

    private Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
        username = (EditText) activity.findViewById(R.id.main_username);
        password = (EditText) activity.findViewById(R.id.main_password);
        signin = (Button) activity.findViewById(R.id.main_sign_in);
    }

    @Test
    public void loginSuccess() {
        username.setText("username");
        password.setText("password");
        signin.performClick();

        ShadowApplication application = Shadows.shadowOf(RuntimeEnvironment.application);
        Assert.assertThat("MovieActivity has started", application.getNextStartedActivity(), Is.is(CoreMatchers.notNullValue()));

        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivityForResult().intent;
        Assert.assertThat("MovieActivity has started", intent.getComponent(), Matchers.equalTo(new ComponentName(activity, MovieActivity.class)));
    }

    @Test
    public void loginWithEmptyUsernameAndPassword() {
        signin.performClick();

        ShadowApplication application = Shadows.shadowOf(RuntimeEnvironment.application);
        Assert.assertThat("MovieActivity should not start", application.getNextStartedActivity(), Is.is(CoreMatchers.nullValue()));
        Assert.assertThat("Username field should display error", username.getError(), CoreMatchers.notNullValue());
        Assert.assertThat("Password field should display error", password.getError(), CoreMatchers.notNullValue());
    }

    @Test
    public void loginWithEmptyUsername() {
        password.setText("password");
        signin.performClick();

        ShadowApplication application = Shadows.shadowOf(RuntimeEnvironment.application);
        Assert.assertThat("MovieActivity should not start", application.getNextStartedActivity(), Is.is(CoreMatchers.nullValue()));
        Assert.assertThat("Username field should display error", username.getError(), CoreMatchers.notNullValue());
        Assert.assertThat("Password field shouldn't display error", password.getError(), CoreMatchers.nullValue());
    }

    @Test
    public void loginWithEmptyPassword() {
        username.setText("username");
        signin.performClick();

        ShadowApplication application = Shadows.shadowOf(RuntimeEnvironment.application);
        Assert.assertThat("MovieActivity should not start", application.getNextStartedActivity(), Is.is(CoreMatchers.nullValue()));
        Assert.assertThat("Password field should display error", password.getError(), CoreMatchers.notNullValue());
        Assert.assertThat("Username field shouldn't display error", username.getError(), CoreMatchers.nullValue());
    }

}
