package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etNumber1: EditText
    private lateinit var etNumber2: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSub: Button
    private lateinit var btnMul: Button
    private lateinit var btnDiv: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNumber1 = findViewById(R.id.etNumber1)
        etNumber2 = findViewById(R.id.etNumber2)
        tvResult = findViewById(R.id.tvResult)
        btnAdd = findViewById(R.id.btnAdd)
        btnSub = findViewById(R.id.btnSub)
        btnMul = findViewById(R.id.btnMul)
        btnDiv = findViewById(R.id.btnDiv)
        btnClear = findViewById(R.id.btnClear)

        btnAdd.setOnClickListener { calculate(Operation.ADD) }
        btnSub.setOnClickListener { calculate(Operation.SUBTRACT) }
        btnMul.setOnClickListener { calculate(Operation.MULTIPLY) }
        btnDiv.setOnClickListener { calculate(Operation.DIVIDE) }
        btnClear.setOnClickListener { clearAll() }
    }

    private fun String.toDoubleOrNullSafe(): Double? {
        return this.trim().takeIf { it.isNotEmpty() }?.toDoubleOrNull()
    }

    private fun calculate(op: Operation) {
        val s1 = etNumber1.text.toString()
        val s2 = etNumber2.text.toString()

        val n1 = s1.toDoubleOrNullSafe()
        val n2 = s2.toDoubleOrNullSafe()

        if (n1 == null || n2 == null) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (op) {
            Operation.ADD -> n1 + n2
            Operation.SUBTRACT -> n1 - n2
            Operation.MULTIPLY -> n1 * n2
            Operation.DIVIDE -> {
                if (n2 == 0.0) {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    n1 / n2
                }
            }
        }

        val resultText = if (result % 1.0 == 0.0) {
            result.toLong().toString()
        } else {
            result.toString()
        }

        tvResult.text = "Result: $resultText"
    }

    private fun clearAll() {
        etNumber1.text.clear()
        etNumber2.text.clear()
        tvResult.text = "Result:"
    }

    private enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
}
