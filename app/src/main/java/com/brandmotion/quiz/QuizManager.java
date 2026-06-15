package com.brandmotion.quiz;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private static QuizManager instance;
    private List<Question> questions;
    private int currentQuestion = 0;
    private int score = 0;
    private boolean answered = false;

    private Activity activity;
    private View quizScreen;
    private View resultsScreen;

    private QuizManager() {
        questions = new ArrayList<>();
        initializeQuestions();
    }

    public static QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }

    public void initialize(Activity activity, View quizScreen, View resultsScreen) {
        this.activity = activity;
        this.quizScreen = quizScreen;
        this.resultsScreen = resultsScreen;
    }

    private void initializeQuestions() {
        questions.add(new Question(
            "What is the primary color of the Brand Motion brand?",
            new String[]{"Blue", "Orange", "Green", "Red"},
            1
        ));

        questions.add(new Question(
            "Which year was Brand Motion founded?",
            new String[]{"2015", "2018", "2020", "2022"},
            2
        ));

        questions.add(new Question(
            "What does Brand Motion specialize in?",
            new String[]{"Web Design", "Motion Graphics", "Photography", "Copywriting"},
            1
        ));

        questions.add(new Question(
            "What is our mission?",
            new String[]{"Sell products", "Create engaging brand experiences", "Build websites", "Write content"},
            1
        ));

        questions.add(new Question(
            "How many team members does Brand Motion have?",
            new String[]{"5", "10", "15+", "50+"},
            2
        ));

        questions.add(new Question(
            "Which platform do we primarily use for social media?",
            new String[]{"LinkedIn", "Instagram", "TikTok", "Twitter"},
            1
        ));

        questions.add(new Question(
            "What is Brand Motion's core service?",
            new String[]{"Consulting", "Animation & Motion Design", "SEO", "Advertising"},
            1
        ));

        questions.add(new Question(
            "How long does a typical Brand Motion project take?",
            new String[]{"1 week", "2-4 weeks", "2-3 months", "6+ months"},
            2
        ));

        questions.add(new Question(
            "What industries does Brand Motion work with?",
            new String[]{"Only Tech", "Tech, Finance, Healthcare, Retail", "Only Retail", "Only Non-profit"},
            1
        ));

        questions.add(new Question(
            "What is the Brand Motion guarantee?",
            new String[]{"Lowest price", "Fast delivery", "Premium quality & customer satisfaction", "Free revisions forever"},
            2
        ));
    }

    public void loadQuestion() {
        if (currentQuestion >= questions.size()) {
            showResults();
            return;
        }

        Question question = questions.get(currentQuestion);
        answered = false;

        // Update question number and score
        TextView questionNumber = quizScreen.findViewById(R.id.question_number);
        questionNumber.setText(String.format("Question %d of %d", currentQuestion + 1, questions.size()));

        TextView scoreView = quizScreen.findViewById(R.id.score_view);
        scoreView.setText(String.format("Score: %d", score));

        // Update progress bar
        ProgressBar progressBar = quizScreen.findViewById(R.id.progress_bar);
        int progress = ((currentQuestion + 1) * 100) / questions.size();
        progressBar.setProgress(progress);

        // Update question text
        TextView questionText = quizScreen.findViewById(R.id.question_text);
        questionText.setText(question.getQuestion());

        // Update options
        for (int i = 0; i < 4; i++) {
            Button optionBtn = quizScreen.findViewById(getOptionButtonId(i));
            optionBtn.setText(question.getOption(i));
            optionBtn.setEnabled(true);
            optionBtn.setBackgroundResource(R.drawable.option_button_bg);
            optionBtn.setTextColor(0xFF333333);

            final int index = i;
            optionBtn.setOnClickListener(v -> selectAnswer(index));
        }

        // Hide next button
        Button nextBtn = quizScreen.findViewById(R.id.btn_next_question);
        nextBtn.setVisibility(View.GONE);
    }

    public void selectAnswer(int index) {
        if (answered) return;

        answered = true;
        Question question = questions.get(currentQuestion);

        // Disable all buttons
        for (int i = 0; i < 4; i++) {
            Button optionBtn = quizScreen.findViewById(getOptionButtonId(i));
            optionBtn.setEnabled(false);
        }

        // Show correct/incorrect answers
        for (int i = 0; i < 4; i++) {
            Button optionBtn = quizScreen.findViewById(getOptionButtonId(i));
            if (i == question.getCorrectAnswer()) {
                optionBtn.setBackgroundResource(R.drawable.option_button_correct);
                optionBtn.setTextColor(0xFF4CAF50);
            } else if (i == index && index != question.getCorrectAnswer()) {
                optionBtn.setBackgroundResource(R.drawable.option_button_incorrect);
                optionBtn.setTextColor(0xFFf44336);
            }
        }

        // Update score if correct
        if (question.isCorrect(index)) {
            score++;
        }

        // Show next button
        Button nextBtn = quizScreen.findViewById(R.id.btn_next_question);
        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setOnClickListener(v -> nextQuestion());
    }

    public void nextQuestion() {
        currentQuestion++;
        loadQuestion();
    }

    public void showResults() {
        quizScreen.setVisibility(View.GONE);
        resultsScreen.setVisibility(View.VISIBLE);

        int percentage = (score * 100) / questions.size();

        TextView finalScore = resultsScreen.findViewById(R.id.final_score);
        finalScore.setText(String.valueOf(score));

        TextView scorePercentage = resultsScreen.findViewById(R.id.score_percentage);
        scorePercentage.setText(String.format("%d%%", percentage));

        TextView resultMessage = resultsScreen.findViewById(R.id.result_message);
        if (percentage == 100) {
            resultMessage.setText("Perfect Score! You're a Brand Motion expert! 🎉");
        } else if (percentage >= 80) {
            resultMessage.setText("Excellent! You know Brand Motion well! 👏");
        } else if (percentage >= 60) {
            resultMessage.setText("Great job! You're doing well! 👍");
        } else if (percentage >= 40) {
            resultMessage.setText("Good effort! Keep learning about Brand Motion! 📚");
        } else {
            resultMessage.setText("Nice try! Take the quiz again to improve! 💪");
        }
    }

    public void reset() {
        currentQuestion = 0;
        score = 0;
        answered = false;
    }

    private int getOptionButtonId(int index) {
        switch (index) {
            case 0:
                return R.id.option_btn_a;
            case 1:
                return R.id.option_btn_b;
            case 2:
                return R.id.option_btn_c;
            case 3:
                return R.id.option_btn_d;
            default:
                return -1;
        }
    }
}
