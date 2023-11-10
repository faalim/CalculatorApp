package com.example.calculatorapplication;

import android.util.Log;



public class CalBrain {
    char[] textnums;
    boolean errorFlag = false;
    public void push(String allValues) {
        textnums = allValues.toCharArray();
    }

    public int calculate() {
        int result = 0;
        char operator = '+'; // Initialize with addition

        // Check if the expression starts with a digit
        if (textnums.length == 0 || !Character.isDigit(textnums[0]) || !Character.isDigit(textnums[textnums.length - 1])) {
            errorFlag = true;
            return 0; // Exit and handle the error
        }
        for (int i = 0; i < textnums.length; i++) {
            char c = textnums[i];

            if (i % 2 == 0){ //even position so MUST be a number
             if (Character.isDigit(c)) { //if it is a number continue with operation
                Log.d("cal", "Result: " + result);
                // Even positions should be numbers
                int num = Character.getNumericValue(c);

                // Perform the operation based on the operator
                switch (operator) {
                    case '+':
                        result += num;
                        break;
                    case '-':
                        result -= num;
                        break;
                    case '*':
                        result *= num;
                        break;
                    case '/':
                        if (num != 0) {
                            result /= num;
                        } else {
                            errorFlag = true;
                            return 0; // Exit and handle the error
                        }
                        break;
                }
            }
        }
        else {              // Odd positions should be operators
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    operator = c;
                } else if (Character.isDigit(c)) {
                    errorFlag = true;
                    return 0; // Exit and handle the error
                }
            }
        }

        return result;
    }
}
