package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.components.GoogleLogoIcon
import com.example.ui.components.MicrosoftLogoIcon
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardElevated
import com.example.ui.theme.CardElevatedHover
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

private enum class AuthMode {
    SIGN_IN_EMAIL,
    SIGN_IN_PASSWORD,
    CREATE_ACCOUNT
}

// Google web branding colors
private val GoogleWhiteCard = Color(0xFFFFFFFF)
private val GoogleTextDark = Color(0xFF000000) // Pitch black (#000000)
private val GooglePlaceholder = Color(0xFF757575) // Subtle light gray (#757575)
private val GoogleTextMuted = Color(0xFF5F6368)
private val GoogleBlue = Color(0xFF1A73E8)
private val GoogleBorder = Color(0xFFDADCE0)
private val InputCaretColor = Color(0xFF000000) // Solid black cursor / caret
private val InputTextStyle = TextStyle(
    color = Color(0xFF000000), // 100% visible solid black ink
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp
)

@Composable
fun GoogleSignInScreen(
    onSignInSuccess: (email: String, allowNotifications: Boolean) -> Unit
) {
    var mode by remember { mutableStateOf(AuthMode.SIGN_IN_EMAIL) }

    // Sign in fields - BLANK by default so any user can type their own real email or phone number
    var emailInput by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordInput by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Create account fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var newEmailInput by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    // Notification permission dialog prompt
    var showNotificationDialog by remember { mutableStateOf(false) }
    var pendingEmail by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .testTag("google_signin_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Google White Card Container
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(BorderStroke(1.dp, GoogleBorder), RoundedCornerShape(24.dp))
                    .testTag("google_white_card"),
                color = GoogleWhiteCard,
                tonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // 1. Google Logo
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("google_official_logo"),
                        contentAlignment = Alignment.Center
                    ) {
                        GoogleLogoIcon(modifier = Modifier.size(28.dp))
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    when (mode) {
                        // -----------------------------------------------------------------
                        // MODE 1: Sign in with Email
                        // -----------------------------------------------------------------
                        AuthMode.SIGN_IN_EMAIL -> {
                            Text(
                                text = "Sign in",
                                style = MaterialTheme.typography.headlineMedium,
                                color = GoogleTextDark,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "with your Google Account to continue to Learnicle",
                                style = MaterialTheme.typography.bodyMedium,
                                color = GoogleTextMuted
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            OutlinedTextField(
                                value = emailInput,
                                onValueChange = {
                                    emailInput = it
                                    if (emailError != null) emailError = null
                                },
                                label = { Text("Email or phone") },
                                textStyle = InputTextStyle,
                                placeholder = {
                                    Text(
                                        text = "Email or phone",
                                        color = GooglePlaceholder,
                                        fontSize = 14.sp
                                    )
                                },
                                isError = emailError != null,
                                supportingText = {
                                    emailError?.let { err ->
                                        Text(text = err, color = Color(0xFFD93025), fontSize = 12.sp)
                                    }
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("google_email_input"),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = GoogleBlue,
                                    unfocusedBorderColor = GoogleBorder,
                                    focusedTextColor = GoogleTextDark,
                                    unfocusedTextColor = GoogleTextDark,
                                    focusedLabelColor = GoogleBlue,
                                    unfocusedLabelColor = GooglePlaceholder,
                                    focusedPlaceholderColor = GooglePlaceholder,
                                    unfocusedPlaceholderColor = GooglePlaceholder,
                                    cursorColor = InputCaretColor,
                                    errorBorderColor = Color(0xFFD93025),
                                    errorLabelColor = Color(0xFFD93025)
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Forgot email?",
                                style = MaterialTheme.typography.labelLarge,
                                color = GoogleBlue,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable { }
                                    .padding(vertical = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Not your computer? Use Guest mode to sign in privately. Learn more",
                                style = MaterialTheme.typography.bodySmall,
                                color = GoogleTextMuted,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(26.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Create account",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = GoogleBlue,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .clickable { mode = AuthMode.CREATE_ACCOUNT }
                                        .padding(vertical = 8.dp)
                                        .testTag("create_account_button")
                                )

                                Button(
                                    onClick = {
                                        val trimmed = emailInput.trim()
                                        if (trimmed.isEmpty()) {
                                            emailError = "Enter an email or phone number"
                                        } else {
                                            emailError = null
                                            mode = AuthMode.SIGN_IN_PASSWORD
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = GoogleBlue,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.testTag("google_next_button")
                                ) {
                                    Text(
                                        text = "Next",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }

                        // -----------------------------------------------------------------
                        // MODE 2: Sign in with Password
                        // -----------------------------------------------------------------
                        AuthMode.SIGN_IN_PASSWORD -> {
                            // User pill
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFF1F3F4))
                                    .border(BorderStroke(1.dp, GoogleBorder), RoundedCornerShape(16.dp))
                                    .clickable { mode = AuthMode.SIGN_IN_EMAIL }
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(GoogleBlue),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = emailInput.take(1).uppercase().ifBlank { "G" },
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = emailInput.ifBlank { "Google User" },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = GoogleTextDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Welcome",
                                style = MaterialTheme.typography.headlineMedium,
                                color = GoogleTextDark,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Enter your password to continue to Learnicle",
                                style = MaterialTheme.typography.bodyMedium,
                                color = GoogleTextMuted
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            OutlinedTextField(
                                value = passwordInput,
                                onValueChange = { passwordInput = it },
                                label = { Text("Enter your password") },
                                textStyle = InputTextStyle,
                                placeholder = {
                                    Text(
                                        text = "Enter your password",
                                        color = GooglePlaceholder,
                                        fontSize = 14.sp
                                    )
                                },
                                singleLine = true,
                                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(
                                        onClick = { isPasswordVisible = !isPasswordVisible },
                                        modifier = Modifier.testTag("toggle_password_visibility")
                                    ) {
                                        Icon(
                                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                            tint = if (isPasswordVisible) GoogleBlue else GoogleTextMuted
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("google_password_input"),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = GoogleBlue,
                                    unfocusedBorderColor = GoogleBorder,
                                    focusedTextColor = GoogleTextDark,
                                    unfocusedTextColor = GoogleTextDark,
                                    focusedLabelColor = GoogleBlue,
                                    unfocusedLabelColor = GooglePlaceholder,
                                    focusedPlaceholderColor = GooglePlaceholder,
                                    unfocusedPlaceholderColor = GooglePlaceholder,
                                    cursorColor = InputCaretColor
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
                            ) {
                                Checkbox(
                                    checked = isPasswordVisible,
                                    onCheckedChange = { isPasswordVisible = it },
                                    colors = CheckboxDefaults.colors(checkedColor = GoogleBlue)
                                )
                                Text(
                                    text = "Show password",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = GoogleTextDark
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Forgot password?",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = GoogleBlue,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable { }
                                )

                                Button(
                                    onClick = {
                                        pendingEmail = emailInput
                                        showNotificationDialog = true
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = GoogleBlue,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.testTag("google_signin_submit_button")
                                ) {
                                    Text(
                                        text = "Next",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }

                        // -----------------------------------------------------------------
                        // MODE 3: Create account setup form
                        // -----------------------------------------------------------------
                        AuthMode.CREATE_ACCOUNT -> {
                            Text(
                                text = "Create a Google Account",
                                style = MaterialTheme.typography.headlineMedium,
                                color = GoogleTextDark,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Enter your name and details to continue to Learnicle",
                                style = MaterialTheme.typography.bodyMedium,
                                color = GoogleTextMuted
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            // First name & Last name
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = firstName,
                                    onValueChange = { firstName = it },
                                    label = { Text("First name") },
                                    textStyle = InputTextStyle,
                                    placeholder = {
                                        Text(
                                            text = "e.g. John",
                                            color = GooglePlaceholder,
                                            fontSize = 13.sp
                                        )
                                    },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("new_first_name_input"),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = GoogleBlue,
                                        unfocusedBorderColor = GoogleBorder,
                                        focusedTextColor = GoogleTextDark,
                                        unfocusedTextColor = GoogleTextDark,
                                        focusedLabelColor = GoogleBlue,
                                        unfocusedLabelColor = GooglePlaceholder,
                                        focusedPlaceholderColor = GooglePlaceholder,
                                        unfocusedPlaceholderColor = GooglePlaceholder,
                                        cursorColor = InputCaretColor
                                    )
                                )

                                OutlinedTextField(
                                    value = lastName,
                                    onValueChange = { lastName = it },
                                    label = { Text("Last name") },
                                    textStyle = InputTextStyle,
                                    placeholder = {
                                        Text(
                                            text = "e.g. Doe",
                                            color = GooglePlaceholder,
                                            fontSize = 13.sp
                                        )
                                    },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("new_last_name_input"),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = GoogleBlue,
                                        unfocusedBorderColor = GoogleBorder,
                                        focusedTextColor = GoogleTextDark,
                                        unfocusedTextColor = GoogleTextDark,
                                        focusedLabelColor = GoogleBlue,
                                        unfocusedLabelColor = GooglePlaceholder,
                                        focusedPlaceholderColor = GooglePlaceholder,
                                        unfocusedPlaceholderColor = GooglePlaceholder,
                                        cursorColor = InputCaretColor
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Desired Gmail address
                            OutlinedTextField(
                                value = newEmailInput,
                                onValueChange = { newEmailInput = it },
                                label = { Text("Choose your Gmail address") },
                                textStyle = InputTextStyle,
                                placeholder = {
                                    Text(
                                        text = "username@gmail.com",
                                        color = GooglePlaceholder,
                                        fontSize = 14.sp
                                    )
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("new_email_input"),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = GoogleBlue,
                                    unfocusedBorderColor = GoogleBorder,
                                    focusedTextColor = GoogleTextDark,
                                    unfocusedTextColor = GoogleTextDark,
                                    focusedLabelColor = GoogleBlue,
                                    unfocusedLabelColor = GooglePlaceholder,
                                    focusedPlaceholderColor = GooglePlaceholder,
                                    unfocusedPlaceholderColor = GooglePlaceholder,
                                    cursorColor = InputCaretColor
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Password & Confirm Password
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = newPassword,
                                    onValueChange = { newPassword = it },
                                    label = { Text("Password") },
                                    textStyle = InputTextStyle,
                                    placeholder = {
                                        Text(
                                            text = "Password",
                                            color = GooglePlaceholder,
                                            fontSize = 13.sp
                                        )
                                    },
                                    singleLine = true,
                                    visualTransformation = if (isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        IconButton(
                                            onClick = { isNewPasswordVisible = !isNewPasswordVisible },
                                            modifier = Modifier.testTag("toggle_new_password_visibility")
                                        ) {
                                            Icon(
                                                imageVector = if (isNewPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                                contentDescription = if (isNewPasswordVisible) "Hide password" else "Show password",
                                                tint = if (isNewPasswordVisible) GoogleBlue else GoogleTextMuted
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("new_password_input"),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = GoogleBlue,
                                        unfocusedBorderColor = GoogleBorder,
                                        focusedTextColor = GoogleTextDark,
                                        unfocusedTextColor = GoogleTextDark,
                                        focusedLabelColor = GoogleBlue,
                                        unfocusedLabelColor = GooglePlaceholder,
                                        focusedPlaceholderColor = GooglePlaceholder,
                                        unfocusedPlaceholderColor = GooglePlaceholder,
                                        cursorColor = InputCaretColor
                                    )
                                )

                                OutlinedTextField(
                                    value = confirmPassword,
                                    onValueChange = { confirmPassword = it },
                                    label = { Text("Confirm") },
                                    textStyle = InputTextStyle,
                                    placeholder = {
                                        Text(
                                            text = "Confirm",
                                            color = GooglePlaceholder,
                                            fontSize = 13.sp
                                        )
                                    },
                                    singleLine = true,
                                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        IconButton(
                                            onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                                            modifier = Modifier.testTag("toggle_confirm_password_visibility")
                                        ) {
                                            Icon(
                                                imageVector = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                                contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password",
                                                tint = if (isConfirmPasswordVisible) GoogleBlue else GoogleTextMuted
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("confirm_password_input"),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = GoogleBlue,
                                        unfocusedBorderColor = GoogleBorder,
                                        focusedTextColor = GoogleTextDark,
                                        unfocusedTextColor = GoogleTextDark,
                                        focusedLabelColor = GoogleBlue,
                                        unfocusedLabelColor = GooglePlaceholder,
                                        focusedPlaceholderColor = GooglePlaceholder,
                                        unfocusedPlaceholderColor = GooglePlaceholder,
                                        cursorColor = InputCaretColor
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    val bothVisible = isNewPasswordVisible && isConfirmPasswordVisible
                                    isNewPasswordVisible = !bothVisible
                                    isConfirmPasswordVisible = !bothVisible
                                }
                            ) {
                                Checkbox(
                                    checked = isNewPasswordVisible && isConfirmPasswordVisible,
                                    onCheckedChange = { checked ->
                                        isNewPasswordVisible = checked
                                        isConfirmPasswordVisible = checked
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = GoogleBlue)
                                )
                                Text(
                                    text = "Show password",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = GoogleTextDark
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Sign in instead",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = GoogleBlue,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .clickable { mode = AuthMode.SIGN_IN_EMAIL }
                                        .padding(vertical = 8.dp)
                                        .testTag("sign_in_instead_button")
                                )

                                Button(
                                    onClick = {
                                        pendingEmail = newEmailInput
                                        showNotificationDialog = true
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = GoogleBlue,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.testTag("create_account_submit_button")
                                ) {
                                    Text(
                                        text = "Next",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Google Footer inside card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "English (United States)", style = MaterialTheme.typography.bodySmall, color = GoogleTextMuted, fontSize = 11.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(text = "Help", style = MaterialTheme.typography.bodySmall, color = GoogleTextMuted, fontSize = 11.sp)
                            Text(text = "Privacy", style = MaterialTheme.typography.bodySmall, color = GoogleTextMuted, fontSize = 11.sp)
                            Text(text = "Terms", style = MaterialTheme.typography.bodySmall, color = GoogleTextMuted, fontSize = 11.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Alternative Login Option: Continue with Microsoft
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "— or use an alternate provider —",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        pendingEmail = "azraf.m@outlook.com"
                        showNotificationDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("continue_with_microsoft_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CardElevated,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, BorderSubtle)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MicrosoftLogoIcon()
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Continue with Microsoft",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }
            }
        }

        // Notification Permission Prompt Dialog: [Allow] [Skip]
        if (showNotificationDialog) {
            Dialog(onDismissRequest = { /* require explicit button click */ }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(20.dp))
                        .testTag("notification_permission_dialog"),
                    color = CardElevated,
                    tonalElevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2C1016))
                                .border(1.dp, CrimsonAccent.copy(alpha = 0.5f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = CrimsonAccent,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Enable daily study reminders?",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Stay consistent with daily 5-minute study reminders, streak alerts, and SSC board exam drills.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    showNotificationDialog = false
                                    onSignInSuccess(pendingEmail, false)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp)
                                    .testTag("skip_notifications_button"),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                                border = BorderStroke(1.dp, BorderSubtle),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Skip", style = MaterialTheme.typography.labelLarge)
                            }

                            Button(
                                onClick = {
                                    showNotificationDialog = false
                                    onSignInSuccess(pendingEmail, true)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp)
                                    .testTag("allow_notifications_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CrimsonAccent,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Allow", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
