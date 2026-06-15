package com.brandmotion.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private View welcomeScreen;
    private View quizScreen;
    private View resultsScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen — hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Hide navigation bar (immersive mode)
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        setContentView(R.layout.activity_main);

        // Initialize screens
        welcomeScreen = findViewById(R.id.welcome_screen);
        quizScreen = findViewById(R.id.quiz_screen);
        resultsScreen = findViewById(R.id.results_screen);

        // Set up start quiz button
        findViewById(R.id.btn_start_quiz).setOnClickListener(v -> startQuiz());

        // Set up restart button
        findViewById(R.id.btn_restart_quiz).setOnClickListener(v -> restartQuiz());

        // Initialize QuizManager
        QuizManager.getInstance().initialize(this, quizScreen, resultsScreen);

        showWelcomeScreen();
    }

    private void startQuiz() {
        hideAllScreens();
        quizScreen.setVisibility(View.VISIBLE);
        QuizManager.getInstance().loadQuestion();
    }

    private void restartQuiz() {
        QuizManager.getInstance().reset();
        hideAllScreens();
        quizScreen.setVisibility(View.VISIBLE);
        QuizManager.getInstance().loadQuestion();
    }

    private void showWelcomeScreen() {
        hideAllScreens();
        welcomeScreen.setVisibility(View.VISIBLE);
    }

    private void hideAllScreens() {
        welcomeScreen.setVisibility(View.GONE);
        quizScreen.setVisibility(View.GONE);
        resultsScreen.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        // Disable back button to prevent exiting
        // User must complete quiz or restart
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }
}
