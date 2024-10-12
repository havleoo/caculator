package com.example.caculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var resultText: TextView

    var firstNumber: Int = 0
    var secondNumber: Int = 0
    var currentOperation: String = ""
    var isSecondNumber: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.result_text)

        // Set up click listeners for number buttons
        val buttonIds = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Set up click listeners for operation buttons
        findViewById<Button>(R.id.button_plus).setOnClickListener(this)
        findViewById<Button>(R.id.button_minus).setOnClickListener(this)
        findViewById<Button>(R.id.button_multiply).setOnClickListener(this)
        findViewById<Button>(R.id.button_divide).setOnClickListener(this)
        findViewById<Button>(R.id.button_equals).setOnClickListener(this)
        findViewById<Button>(R.id.button_c).setOnClickListener(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onClick(v: View?) {
        val id = v?.id

        when (id) {
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9 -> {
                val number = (v as Button).text.toString().toInt()
                handleNumberInput(number)
            }

            R.id.button_plus -> setOperation("+")
            R.id.button_minus -> setOperation("-")
            R.id.button_multiply -> setOperation("*")
            R.id.button_divide -> setOperation("/")
            R.id.button_equals -> calculateResult()
            R.id.button_c -> clearCalculator()
        }
    }

    private fun handleNumberInput(number: Int) {
        if (!isSecondNumber) {
            firstNumber = firstNumber * 10 + number
            resultText.text = firstNumber.toString()
        } else {
            secondNumber = secondNumber * 10 + number
            resultText.text = secondNumber.toString()
        }
    }

    private fun setOperation(operation: String) {
        currentOperation = operation
        isSecondNumber = true
    }

    private fun calculateResult() {
        var result = 0

        when (currentOperation) {
            "+" -> result = firstNumber + secondNumber
            "-" -> result = firstNumber - secondNumber
            "*" -> result = firstNumber * secondNumber
            "/" -> if (secondNumber != 0) result = firstNumber / secondNumber else result = 0
        }

        resultText.text = result.toString()
        firstNumber = result
        secondNumber = 0
        isSecondNumber = false
    }

    private fun clearCalculator() {
        firstNumber = 0
        secondNumber = 0
        currentOperation = ""
        isSecondNumber = false
        resultText.text = "0"
    }
}