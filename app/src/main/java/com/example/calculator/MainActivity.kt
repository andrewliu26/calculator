package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private var currentInput: String = ""
    private var currentOperator: String = ""
    private var result: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.numInput)

        // Set click listeners for number buttons
        val numberButtons = listOf(
            R.id.buttonZero, R.id.buttonOne, R.id.buttonTwo, R.id.buttonThree,
            R.id.buttonFour, R.id.buttonFive, R.id.buttonSix, R.id.buttonSeven,
            R.id.buttonEight, R.id.buttonNine, R.id.buttonDecimal
        )

        numberButtons.forEach { buttonId ->
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { onNumberButtonClick(button) }
        }

        // Set click listeners for operator buttons
        val operatorButtons = listOf(
            R.id.buttonAdd, R.id.buttonSub, R.id.buttonMult, R.id.buttonDiv, R.id.buttonSqrt
        )

        operatorButtons.forEach { buttonId ->
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { onOperatorButtonClick(button) }
        }

        // Equal button click listener
        val equalButton = findViewById<Button>(R.id.buttonEqual)
        equalButton.setOnClickListener { onEqualButtonClick() }

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener { onClearButtonClick() }
    }

    private fun onNumberButtonClick(button: Button) {
        currentInput += button.text.toString()
        editText.setText(currentInput)
    }

    private fun onOperatorButtonClick(button: Button) {
        if (currentInput.isNotEmpty()) {
            currentOperator = button.text.toString()
            result = currentInput.toDouble()
            currentInput = ""
            editText.setText(currentOperator)
        }
    }

    private fun onEqualButtonClick() {
        if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            try {
                result = performOperation(result, secondOperand, currentOperator)
                currentInput = ""
                currentOperator = ""
                editText.setText(result.toString())
            } catch (e: ArithmeticException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClearButtonClick() {
        currentInput = ""
        currentOperator = ""
        result = 0.0
        editText.setText("") // Clear the EditText
    }
}

fun performOperation(operand1: Double, operand2: Double, operator: String): Double {
    return when (operator) {
        "+" -> operand1 + operand2
        "-" -> operand1 - operand2
        "*" -> operand1 * operand2
        "/" -> {
            if (operand2 != 0.0) {
                operand1 / operand2
            } else {
                throw ArithmeticException("Cannot divide by zero")
            }
        }
        "√" -> sqrt(operand1)
        else -> throw IllegalArgumentException("Invalid operator: $operator")
    }
}