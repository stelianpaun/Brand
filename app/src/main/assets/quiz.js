// Quiz Data
const quizData = [
    {
        question: "What is the primary color of the Brand Motion brand?",
        options: ["Blue", "Orange", "Green", "Red"],
        correct: 1
    },
    {
        question: "Which year was Brand Motion founded?",
        options: ["2015", "2018", "2020", "2022"],
        correct: 2
    },
    {
        question: "What does Brand Motion specialize in?",
        options: ["Web Design", "Motion Graphics", "Photography", "Copywriting"],
        correct: 1
    },
    {
        question: "What is our mission?",
        options: ["Sell products", "Create engaging brand experiences", "Build websites", "Write content"],
        correct: 1
    },
    {
        question: "How many team members does Brand Motion have?",
        options: ["5", "10", "15+", "50+"],
        correct: 2
    },
    {
        question: "Which platform do we primarily use for social media?",
        options: ["LinkedIn", "Instagram", "TikTok", "Twitter"],
        correct: 1
    },
    {
        question: "What is Brand Motion's core service?",
        options: ["Consulting", "Animation & Motion Design", "SEO", "Advertising"],
        correct: 1
    },
    {
        question: "How long does a typical Brand Motion project take?",
        options: ["1 week", "2-4 weeks", "2-3 months", "6+ months"],
        correct: 2
    },
    {
        question: "What industries does Brand Motion work with?",
        options: ["Only Tech", "Tech, Finance, Healthcare, Retail", "Only Retail", "Only Non-profit"],
        correct: 1
    },
    {
        question: "What is the Brand Motion guarantee?",
        options: ["Lowest price", "Fast delivery", "Premium quality & customer satisfaction", "Free revisions forever"],
        correct: 2
    }
];

// State Variables
let currentQuestion = 0;
let score = 0;
let answered = false;
let selectedAnswer = null;

// Initialize
function init() {
    currentQuestion = 0;
    score = 0;
    answered = false;
    selectedAnswer = null;
}

// Start Quiz
function startQuiz() {
    init();
    showScreen('quizScreen');
    loadQuestion();
}

// Load Current Question
function loadQuestion() {
    const question = quizData[currentQuestion];
    
    // Update question number and score
    document.getElementById('questionNumber').textContent = `Question ${currentQuestion + 1} of ${quizData.length}`;
    document.getElementById('score').textContent = `Score: ${score}`;
    
    // Update progress bar
    const progress = ((currentQuestion + 1) / quizData.length) * 100;
    document.getElementById('progressFill').style.width = progress + '%';
    
    // Update question text
    document.getElementById('questionText').textContent = question.question;
    
    // Update options
    question.options.forEach((option, index) => {
        document.getElementById(`option${index}`).textContent = option;
    });
    
    // Reset state
    answered = false;
    selectedAnswer = null;
    document.getElementById('nextBtn').style.display = 'none';
    
    // Remove previous styling
    document.querySelectorAll('.option-btn').forEach((btn, index) => {
        btn.classList.remove('selected', 'correct', 'incorrect');
        btn.disabled = false;
    });
}

// Select Answer
function selectAnswer(index) {
    if (answered) return;
    
    answered = true;
    selectedAnswer = index;
    const question = quizData[currentQuestion];
    const buttons = document.querySelectorAll('.option-btn');
    
    // Show all options
    buttons.forEach((btn, i) => {
        btn.disabled = true;
        if (i === question.correct) {
            btn.classList.add('correct');
        } else if (i === index && index !== question.correct) {
            btn.classList.add('incorrect');
        }
    });
    
    // Update score if correct
    if (index === question.correct) {
        score++;
        document.getElementById('score').textContent = `Score: ${score}`;
    }
    
    // Show next button
    document.getElementById('nextBtn').style.display = 'block';
}

// Next Question
function nextQuestion() {
    currentQuestion++;
    
    if (currentQuestion < quizData.length) {
        loadQuestion();
    } else {
        showResults();
    }
}

// Show Results
function showResults() {
    const percentage = Math.round((score / quizData.length) * 100);
    
    document.getElementById('finalScore').textContent = score;
    document.getElementById('scorePercentage').textContent = percentage + '%';
    
    // Determine message based on score
    let message = '';
    if (percentage === 100) {
        message = 'Perfect Score! You\'re a Brand Motion expert! 🎉';
    } else if (percentage >= 80) {
        message = 'Excellent! You know Brand Motion well! 👏';
    } else if (percentage >= 60) {
        message = 'Great job! You\'re doing well! 👍';
    } else if (percentage >= 40) {
        message = 'Good effort! Keep learning about Brand Motion! 📚';
    } else {
        message = 'Nice try! Take the quiz again to improve! 💪';
    }
    
    document.getElementById('resultMessage').textContent = message;
    showScreen('resultsScreen');
}

// Restart Quiz
function restartQuiz() {
    showScreen('welcomeScreen');
}

// Show Screen
function showScreen(screenId) {
    document.querySelectorAll('.screen').forEach(screen => {
        screen.classList.remove('active');
    });
    document.getElementById(screenId).classList.add('active');
}

// Initialize welcome screen on load
document.addEventListener('DOMContentLoaded', () => {
    showScreen('welcomeScreen');
});
