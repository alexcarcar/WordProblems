package alex.carcar.wordproblems;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView question;
    EditText answer;
    String answerText;
    TextView iconHeader;
    ArrayList<String> questions;
    Random random;
    LinearLayout linearLayout;
    int[] backgroundImages;
    int[] squareColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        linearLayout = findViewById(R.id.linearLayout);
        iconHeader = findViewById(R.id.iconHeader);
        random = new Random();
        backgroundImages = new int[]{R.drawable.bg_001, R.drawable.bg_002, R.drawable.bg_003, R.drawable.bg_004, R.drawable.bg_005, R.drawable.bg_005, R.drawable.bg_007, R.drawable.bg_006, R.drawable.bg_009, R.drawable.bg_010};
        squareColors = new int[]{R.color.square1, R.color.square2, R.color.square3, R.color.square4};
        readQuestions();
        createQuestion();
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(answerText)) {
                    createQuestion();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void readQuestions() {
        questions = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.questions);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.ready()) {
                questions.add(reader.readLine());
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createQuestion() {
        if (!questions.isEmpty()) {
            String item = questions.get(random.nextInt(questions.size()));
            String[] problem = item.split("\t");
            if (problem.length == 2) {
                answerText = problem[0];
                question.setText(problem[1]);
                answer.setText("");
                changeBackground();
            } else {
                createQuestion();
            }
        }
    }

    private void changeBackground() {
        int image = backgroundImages[random.nextInt(backgroundImages.length)];
        int color = squareColors[random.nextInt(squareColors.length)];
        Drawable resource = getResources().getDrawable(image);
        linearLayout.setBackground(resource);
        iconHeader.setBackgroundColor(color);
    }
}