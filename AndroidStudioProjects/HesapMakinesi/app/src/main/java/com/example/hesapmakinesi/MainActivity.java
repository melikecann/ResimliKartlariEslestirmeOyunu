package com.example.hesapmakinesi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        setNumberButtonListeners();
        setOperatorButtonListeners();
        setControlButtonListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
                R.id.button_8, R.id.button_9, R.id.button_dot
        };

        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(view -> {
                if (isNewInput) {
                    currentInput = "";
                    isNewInput = false;
                }
                currentInput += button.getText().toString();
                textView.setText(currentInput);
            });
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtonIds = {
                R.id.button_plus, R.id.button_minus, R.id.button_multiply, R.id.button_divide
        };

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(view -> {
                if (!currentInput.isEmpty()) {
                    firstNumber = Double.parseDouble(currentInput);
                    operator = button.getText().toString();
                    isNewInput = true;
                }
            });
        }

        // Eşittir butonu işlemi tamamlamak için
        Button equalsButton = findViewById(R.id.button_equal);
        equalsButton.setOnClickListener(view -> calculateResult());
    }

    private void setControlButtonListeners() {
        Button clearButton = findViewById(R.id.button_temizle);
        clearButton.setOnClickListener(view -> {
            currentInput = "";
            firstNumber = 0;
            operator = "";
            textView.setText("0");
            isNewInput = true;
        });

        Button deleteButton = findViewById(R.id.button_sil);
        deleteButton.setOnClickListener(view -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                textView.setText(currentInput.isEmpty() ? "0" : currentInput);
            }
        });
    }

    private void calculateResult() {
        if (currentInput.isEmpty() || operator.isEmpty()) {
            return;
        }

        double secondNumber = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "×": result = firstNumber * secondNumber; break;
            case "÷":
                if (secondNumber == 0) {
                    textView.setText("Hata");
                    return;
                }
                result = firstNumber / secondNumber;
                break;
        }

        textView.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
        operator = "";
        isNewInput = true;
    }
}