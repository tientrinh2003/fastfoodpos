
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastfoodpos.R
import com.example.fastfoodpos.ui.UserType
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: (UserType) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserType.CUSTOMER) }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var loginAttempted by remember { mutableStateOf(false) }

    LaunchedEffect(loginAttempted) {
        if (loginAttempted) {
            Log.d("LoginScreen", "LaunchedEffect: before delay")
            delay(1000)
            Log.d("LoginScreen", "LaunchedEffect: after delay")
            isLoading = false
            Log.d("LoginScreen", "LaunchedEffect: before onLoginSuccess")
            onLoginSuccess(selectedRole)
            Log.d("LoginScreen", "LaunchedEffect: after onLoginSuccess")
            loginAttempted = false
        }
    }

    // Make the screen scrollable
    androidx.compose.foundation.rememberScrollState().let { scrollState ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.error)
                .padding(16.dp)
                .verticalScroll(scrollState), // Add vertical scroll
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.twohams),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(175.dp)
                    .padding(top = 60.dp)
            )

            // App Title
            Text(
                text = "POSVN",
                fontFamily = FontFamily.Cursive,
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Email Input Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password Input Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    PasswordVisibilityToggle(passwordVisible = passwordVisible) {
                        passwordVisible = !passwordVisible
                    }
                }
            )

            // Role Selection
            Row(
                modifier = Modifier.padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                RoleRadioButton(
                    label = "Admin",
                    selected = selectedRole == UserType.ADMIN,
                    onSelect = { selectedRole = UserType.ADMIN }
                )
                Spacer(modifier = Modifier.width(30.dp))
                RoleRadioButton(
                    label = "Customer",
                    selected = selectedRole == UserType.CUSTOMER,
                    onSelect = { selectedRole = UserType.CUSTOMER }
                )
            }

            // Login Button
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        isLoading = true
                        loginError = null
                        loginAttempted = true
                    } else {
                        loginError = "Please fill in all fields"
                    }
                },
                modifier = Modifier.padding(top = 30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(
                        text = "LOGIN",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Error Message
            if (loginError != null) {
                Text(
                    text = loginError!!,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // "Don't have an account?" Text
            Text(
                text = "Don't have an account?",
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}


@Composable
fun PasswordVisibilityToggle(passwordVisible: Boolean, onToggle: () -> Unit) {
    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    val description = if (passwordVisible) "Hide password" else "Show password"

    IconButton(onClick = onToggle) {
        Icon(imageVector = image, contentDescription = description, tint = MaterialTheme.
        colorScheme.onSurface)
    }
}

@Composable
fun RoleRadioButton(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.onPrimary)
        )
        Text(text = label, color = MaterialTheme.colorScheme.onPrimary)
    }
}