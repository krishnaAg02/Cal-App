package com.example.calculatorapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorUI() {
    var input by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf("") }
    var firstNumber by remember { mutableStateOf("") }
    var shouldClearInput by remember { mutableStateOf(false) }

    fun appendNumber(number: String) {
        if (shouldClearInput) {
            input = number
            shouldClearInput = false
            return
        }

        // Handle decimal point
        if (number == ".") {
            if (!input.contains(".")) {
                input = if (input == "0") "0." else "$input."
            }
            return
        }

        // Handle leading zeros
        if (input == "0") {
            input = number
            return
        }

        // Prevent number from getting too long
        if (input.length < 15) {
            input += number
        }
    }

    fun calculateResult() {
        val secondNumber = input
        val result = when (operation) {
            "+" -> firstNumber.toDoubleOrNull()?.plus(secondNumber.toDoubleOrNull() ?: 0.0)
            "-" -> firstNumber.toDoubleOrNull()?.minus(secondNumber.toDoubleOrNull() ?: 0.0)
            "×" -> firstNumber.toDoubleOrNull()?.times(secondNumber.toDoubleOrNull() ?: 0.0)
            "÷" -> firstNumber.toDoubleOrNull()?.div(secondNumber.toDoubleOrNull() ?: 1.0)
            else -> secondNumber.toDoubleOrNull()
        }
        input = result?.toString() ?: "0"
        operation = ""
        firstNumber = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1C))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Display
        Text(
            text = input,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            textAlign = TextAlign.End,
            fontSize = 48.sp,
            color = Color.White
        )

        // Calculator buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("C", Color(0xFFA5A5A5), Modifier.weight(1f)) {
                input = "0"
                operation = ""
                firstNumber = ""
            }
            CalculatorButton("±", Color(0xFFA5A5A5), Modifier.weight(1f)) {
                input = if (input.startsWith("-")) input.substring(1) else "-$input"
            }
            CalculatorButton("%", Color(0xFFA5A5A5), Modifier.weight(1f)) {
                input = (input.toDoubleOrNull()?.div(100) ?: 0.0).toString()
            }
            CalculatorButton("÷", Color(0xFFFF9F0A), Modifier.weight(1f)) {
                operation = "÷"
                firstNumber = input
                shouldClearInput = true
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("7", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("7") }
            CalculatorButton("8", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("8") }
            CalculatorButton("9", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("9") }
            CalculatorButton("×", Color(0xFFFF9F0A), Modifier.weight(1f)) {
                operation = "×"
                firstNumber = input
                shouldClearInput = true
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("4", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("4") }
            CalculatorButton("5", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("5") }
            CalculatorButton("6", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("6") }
            CalculatorButton("-", Color(0xFFFF9F0A), Modifier.weight(1f)) {
                operation = "-"
                firstNumber = input
                shouldClearInput = true
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("1", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("1") }
            CalculatorButton("2", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("2") }
            CalculatorButton("3", Color(0xFF505050), Modifier.weight(1f)) { appendNumber("3") }
            CalculatorButton("+", Color(0xFFFF9F0A), Modifier.weight(1f)) {
                operation = "+"
                firstNumber = input
                shouldClearInput = true
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton("0", Color(0xFF505050), Modifier.weight(2f)) { appendNumber("0") }
            CalculatorButton(".", Color(0xFF505050), Modifier.weight(1f)) { appendNumber(".") }
            CalculatorButton("=", Color(0xFFFF9F0A), Modifier.weight(1f)) { calculateResult() }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Color.White
        )
    }
}