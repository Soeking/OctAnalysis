package net.soeki.octanalysis.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import net.soeki.octanalysis.logic.Auth

@Composable
fun LoginScreen(context: Context) {
    val auth = Auth(context)
    val savedInfo = auth.getUserInfo()

    var email by remember { mutableStateOf(savedInfo.first) }
    var password by remember { mutableStateOf(savedInfo.second) }
    var accNumber by remember { mutableStateOf(savedInfo.third) }

    var loginResult by remember { mutableStateOf(false) }
    Column() {
        TextInput(
            label = "email",
            type = VisualTransformation.None,
            value = email,
            changeEvent = { new -> email = new })
        TextInput(
            label = "password",
            type = PasswordVisualTransformation(),
            value = password,
            changeEvent = { new -> password = new })
        Button(onClick = { loginResult = auth.manualLogin(email, password) }) {
            Text(text = "Login")
        }
        TextInput(
            label = "account number",
            type = VisualTransformation.None,
            value = accNumber,
            changeEvent = { new -> accNumber = new })
        Button(onClick = { auth.saveAccountNumber(accNumber) }) {
            Text(text = "save")
        }
    }
}

@Composable
fun TextInput(
    label: String,
    type: VisualTransformation,
    value: String,
    changeEvent: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = changeEvent,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(5.dp)),
        label = { Text(label) },
        visualTransformation = type,
        maxLines = 1
    )
}