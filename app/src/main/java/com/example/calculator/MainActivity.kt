package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
        private var strNumber = StringBuilder()
        private lateinit var tvDisplay: TextView
        private lateinit var numberButtons: Array<Button>
        private lateinit var operatorButton: List<Button>
        private var operator: Operator = Operator.NONE
        private var isOperatorClicked: Boolean = false
        private var operand1: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeComponents()

        buttonDel.setOnClickListener{strNumber.clear()
                                     tvDisplay.text = strNumber}
        buttonDot.setOnClickListener{
               strNumber.append(".")
               operand1 = strNumber.toString().toDouble().roundToInt()
               tvDisplay.text = operand1.toString()

            }

    }

    private fun initializeComponents() {
        tvDisplay = findViewById(R.id.tvDisplay)
        numberButtons = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        operatorButton = listOf(buttonAdd,buttonSub,buttonMul,buttonDiv,buttonSQr)
        for (i in numberButtons){i.setOnClickListener{numberButtonsClick(i)}}
        for (i in operatorButton){i.setOnClickListener{operatorButtonClick(i)}}
        buttonEquals.setOnClickListener{buttonEqualClick()}

    }

    private fun buttonEqualClick() {
        val operand2 = strNumber.toString().toDouble()
        val result= when(operator){
            Operator.ADD ->  operand1 + operand2
            Operator.SUB ->  operand1 - operand2
            Operator.MUL ->  operand1 * operand2
            Operator.DIV ->  operand1 / operand2
            Operator.SQR ->  operand1 * operand1
            else ->  0
        }
        strNumber.clear()
        strNumber.append(result.toString())
        updateDisplay()
        isOperatorClicked= true

    }

    private fun updateDisplay() {
        try {
            val textValue = strNumber.toString().toDouble()
            tvDisplay.text = textValue.toString()
        } catch (e: IllegalArgumentException){
            strNumber.clear()
            tvDisplay.text = "ERROR"
        }
        }

    private fun operatorButtonClick(btn: Button) {
        if (btn.text == "+") operator = Operator.ADD
        else if (btn.text == "-") operator = Operator.SUB
        else if (btn.text == "*") operator = Operator.MUL
        else if (btn.text == "/") operator = Operator.DIV
        else if (btn.text == "Sq") operator = Operator.SQR
        else operator = Operator.NONE
        isOperatorClicked = true
    }

    private fun numberButtonsClick(btn: Button) {
        if (isOperatorClicked){
            operand1 = strNumber.toString().toInt()
            strNumber.clear()
            isOperatorClicked = false
        }
        strNumber.append(btn.text)
        updateDisplay()
    }
}

enum class Operator{ADD, SUB, MUL ,DIV ,SQR, NONE}