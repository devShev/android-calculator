package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();  // MainActivity

    private TextView tvOperand;
    private TextView tvOperation;
    private TextView tvOperator;

    private EditText etResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_3);

        Button btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(this);
        Button btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(this);
        Button btnMulti = findViewById(R.id.btnMulti);
        btnMulti.setOnClickListener(this);
        Button btnDiv = findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(this);

        tvOperand = findViewById(R.id.operandTextView);
        tvOperation = findViewById(R.id.operationTextView);
        tvOperator = findViewById(R.id.operatorTextView);

        etResult = findViewById(R.id.fieldEditText);
    }

    @Override
    public void onClick(View view) {
        // Checking ET for the presence of a number
        if (etResult.getText().toString().isEmpty()) {
            return;
        }

        // 1. Move operand
        String operandStr = etResult.getText().toString();
        tvOperand.setText(operandStr);

        // 2. Save operation
        Button button = (Button) view;
        String operationStr = button.getText().toString();

        // Checking the operator and operand for NULL
        if (!(tvOperation.getText().toString().isEmpty() && tvOperand.getText().toString().isEmpty())) {
            tvOperand.setText(etResult.getText().toString());
            tvOperator.setText("");
            etResult.setText("");
        }

        // Checking the equivalence of the current operation with the specified
        if (operationStr.equals(tvOperation.getText().toString())) {
            return;
        }

        tvOperation.setText(operationStr);
        etResult.setText("");
    }

    public void onClearClick(View v) {
        etResult.setText("");

        if (v.getId() == R.id.btnDEL) {
            tvOperand.setText("");
            tvOperation.setText("");
            tvOperator.setText("");
        }
    }

    public void onNumberClick(View v) {
            Button button = (Button) v;
            String numberStr = button.getText().toString();

            if (numberStr.equals(".") && etResult.getText().toString().contains(".")) {
                return;
            }
            if (etResult.getText().toString().isEmpty() && numberStr.equals(".")) {
                etResult.setText("0.");
                return;
            }

            etResult.append(numberStr);
    }

    public void onEqualsClick(View v) {
        // 1. Move operator
        String operator = etResult.getText().toString();
        tvOperator.setText(operator);
        if (tvOperation.getText().toString().equals("/") && tvOperator.getText().toString().equals("0")) {
            resultError("Division by zero");
        } else {
            double result = calculate();
            String resultStr = String.valueOf(result);
            etResult.setText(resultStr);
        }
    }

    private void resultError(String errorText) {
        etResult.setText("Error: " + errorText);
    }

    private void resultError() {
        etResult.setText("Error");
    }

    private double calculate() {
        String operandStr = tvOperand.getText().toString();
        String operationStr = tvOperation.getText().toString();
        String operatorStr = tvOperator.getText().toString();

        double operand = convertStringToDouble(operandStr);
        double operator = convertStringToDouble(operatorStr);

        switch (operationStr) {
            case "+":
                return operand + operator;
            case "-":
                return operand - operator;
            case "*":
                return operand * operator;
            case "/":
                return operand / operator;
            default:
                return 0;
        }
    }

    private double convertStringToDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}