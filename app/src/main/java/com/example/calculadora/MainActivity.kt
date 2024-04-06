package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Button
import com.example.calculadora.databinding.ActivityMainBinding
import kotlin.String as String
import kotlinx.coroutines.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityMainBinding
    var strArray = arrayListOf<String>()
    var strTemp = ""
    var displayText = ""
    var validation = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContent()

    }
    // Esta es una función suspendida que puede ser llamada dentro de una Coroutine
    suspend fun delayFunction() {
        delay(1000)
    }
    fun setContent() {
        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnDot.setOnClickListener(this)
        binding.btnSum.setOnClickListener(this)
        binding.btnSub.setOnClickListener(this)
        binding.btnMul.setOnClickListener(this)
        binding.btnDiv.setOnClickListener(this)
        binding.btnOpenParenthesis.setOnClickListener(this)
        binding.btnCloseParenthesis.setOnClickListener(this)
        binding.btnClear.setOnClickListener(this)
        binding.btnEqual.setOnClickListener(this)
    }
    fun solveArray(array: MutableList<String>): Double {
        var stack = arrayListOf<String>()
        var stack2 = arrayListOf<String>()
        var result = 0.0
        var bandera = true
        var arraynew = array
        var i = 0
        while (i < arraynew.size) {
            if (arraynew[i] == "*" && arraynew[i+1] != "(" && arraynew[i+1] != ")"){
                if (stack.size == 0){
                    stack.add((arraynew[i-1].toDouble() * arraynew[i+1].toDouble()).toString())
                    i++
                }
                else if (arraynew[i-1]==")") {
                    stack.add(arraynew[i])
                }
                else {
                    stack[stack.size-1] = (stack[stack.size-1].toDouble() * arraynew[i+1].toDouble()).toString()
                    i++
                }

            }
            else if (arraynew[i] == "/" && arraynew[i+1] != "(" && arraynew[i+1] != ")") {
                if (stack.size == 0){
                    stack.add((arraynew[i-1].toDouble() / arraynew[i+1].toDouble()).toString())
                    i++
                }
                else if (arraynew[i-1]==")") {
                    stack.add(arraynew[i])
                }
                else {
                    stack[stack.size-1] = (stack[stack.size-1].toDouble() / arraynew[i+1].toDouble()).toString()
                    i++
                }
            }
            else {
                stack.add(arraynew[i])
            }
            i++
        }
        Log.d("array", stack.toString())
        i= 0
        while (i < stack.size) {
            if (stack[i] == "+" && stack[i+1] != "(" && stack[i+1] != ")"){
                if (i+2 < stack.size){
                    if (stack[i+2] == "*"){
                        stack2.add(stack[i])
                    }
                    else if (stack2.size == 0){
                        stack2.add((stack[i-1].toDouble() + stack[i+1].toDouble()).toString())
                        i++
                    }
                    else if (stack[i-1]==")") {
                        stack2.add(stack[i])
                    }
                    else {
                        stack2[stack2.size-1] = (stack2[stack2.size-1].toDouble() + stack[i+1].toDouble()).toString()
                        i++
                    }
                }
                else if (stack2.size == 0){
                    stack2.add((stack[i-1].toDouble() + stack[i+1].toDouble()).toString())
                    i++
                }
                else if (stack[i-1]==")") {
                    stack2.add(stack[i])
                }
                else {
                    stack2[stack2.size-1] = (stack2[stack2.size-1].toDouble() + stack[i+1].toDouble()).toString()
                    i++
                }

            }
            else if (stack[i] == "-" && stack[i+1] != "(" && stack[i+1] != ")"){
                if (i+2 < stack.size){
                    if (stack[i+2] == "*"){
                        stack2.add(stack[i])
                    }
                    else if (stack2.size == 0){
                        stack2.add((stack[i-1].toDouble() - stack[i+1].toDouble()).toString())
                        i++
                    }
                    else if (stack[i-1]==")") {
                        stack2.add(stack[i])
                    }
                    else {
                        stack2[stack2.size-1] = (stack2[stack2.size-1].toDouble() - stack[i+1].toDouble()).toString()
                        i++
                    }
                }
                else if (stack2.size == 0){
                    stack2.add((stack[i-1].toDouble() - stack[i+1].toDouble()).toString())
                    i++
                }
                else if (stack[i-1]==")") {
                    stack2.add(stack[i])
                }
                else {
                    stack2[stack2.size-1] = (stack2[stack2.size-1].toDouble() - stack[i+1].toDouble()).toString()
                    i++
                }

            }
            else {
                stack2.add(stack[i])
            }
            i++
        }
        i=0
        while (i < stack2.size) {
            if (stack2[i] == ")" && i+1 < stack2.size){
                if (stack2[i+1] == "(" || isNumeric(stack2[i+1])) {
                    stack2[i] = "*"
                }
                else{
                    stack2.removeAt(i)
                }
                bandera = false
            }
            else if (stack2[i] == "(" && i >0){
                if (isNumeric(stack2[i-1])) {
                    stack2[i] = "*"
                }
                else{
                    stack2.removeAt(i)
                }
                bandera = false
            }
            else if (stack2[i] == "(" || stack2[i] == ")"){
                stack2.removeAt(i)
                bandera = false
            }
            i++
        }
        Log.d("array", stack2.toString())
        if (bandera){
            result = stack2[0].toDouble()
            Log.d("Resultado", result.toString())
        }
        else
            result = solveArray(stack2)
        return result
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9 -> {
                    displayText = displayText + (v.tag.toString())
                    strTemp = strTemp + (v.tag.toString())
                    binding.textResult.setText(displayText)
                }
                R.id.btnDot -> {
                    displayText = displayText + "."
                    strTemp = strTemp + "."
                    binding.textResult.setText(displayText)
                }

                R.id.btnSum, R.id.btnSub, R.id.btnMul, R.id.btnDiv ->{
                    displayText = displayText + (v.tag.toString())
                    if (strTemp != "") {strArray.add(strTemp)}
                    strArray.add(v.tag.toString())
                    strTemp = ""
                    binding.textResult.setText(displayText)
                }
                R.id.btnOpenParenthesis -> {
                    validation ++
                    displayText = displayText + (v.tag.toString())
                    if (strTemp != "") {strArray.add(strTemp)}
                    strArray.add(v.tag.toString())
                    strTemp = ""
                    binding.textResult.setText(displayText)
                    Log.d("validation", validation.toString())
                }
                R.id.btnCloseParenthesis -> {
                    validation = validation-1
                    if (validation < 0){
                        binding.textResult.setText("Math Error")
                        GlobalScope.launch {
                            delayFunction()
                            withContext(Dispatchers.Main) {
                                binding.textResult.setText(displayText)
                            }
                        }
                        validation ++
                    }
                    else{
                        displayText = displayText + (v.tag.toString())
                        if (strTemp != "") {strArray.add(strTemp)}
                        strArray.add(v.tag.toString())
                        strTemp =   ""
                        binding.textResult.setText(displayText)
                    }
                    //Log.d("validation", validation.toString())
                }

                R.id.btnClear -> {
                    displayText = ""
                    strArray.clear()
                    strTemp = ""
                    validation = 0.0
                    binding.textResult.setText(displayText)
                }

                R.id.btnEqual -> {

                    try {

                        if(validation != 0.0){
                            Log.d("array", strArray.toString())
                            binding.textResult.setText("Math Error")
                            GlobalScope.launch {
                                delayFunction()
                                withContext(Dispatchers.Main) {
                                    binding.textResult.setText(displayText)
                                }
                            }
                        }
                        else{
                            if (strTemp != "") {strArray.add(strTemp)}
                            Log.d("array", strArray.toString())
                            val result = solveArray(strArray)
                            if (result % 1 == 0.0)
                                displayText = result.roundToInt().toString()// Redondear a entero si no hay decimales
                            else
                                displayText = result.toString()// Dejar decimales si los hay
                            strTemp = result.toString()
                            strArray.clear()
                            binding.textResult.setText(displayText)
                        }
                    } catch (e: Exception) {
                        binding.textResult.setText("Math Error")
                    }
                }
            }
        }
    }
    fun isNumeric(str: String): Boolean {
        return str.toDoubleOrNull() != null
    }

}
