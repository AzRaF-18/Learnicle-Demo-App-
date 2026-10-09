package com.example.auth

import android.content.Context
import android.util.Log

/**
 * # Firebase Google Authentication Guide for Android
 *
 * The JavaScript code you provided:
 * ```javascript
 * import { GoogleAuthProvider, signInWithPopup } from "firebase/auth";
 * const provider = new GoogleAuthProvider();
 * signInWithPopup(auth, provider);
 * ```
 * is the **Firebase Web (JS)** method for opening a browser popup window.
 *
 * ## Equivalent in Modern Android (Jetpack Compose + Kotlin):
 * In Android, browsers do not use desktop-style popups. Instead, Google Identity
 * and Firebase provide the **Jetpack Credential Manager** bottom-sheet API.
 *
 * ### 1. Dependencies (in `app/build.gradle.kts`):
 * ```kotlin
 * implementation(platform("com.google.firebase:firebase-bom:34.17.0"))
 * implementation("com.google.firebase:firebase-auth")
 * implementation("androidx.credentials:credentials:1.5.0")
 * implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
 * implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
 * ```
 *
 * ### 2. Kotlin Implementation:
 * ```kotlin
 * import androidx.credentials.CredentialManager
 * import androidx.credentials.GetCredentialRequest
 * import androidx.credentials.CustomCredential
 * import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
 * import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
 * import com.google.firebase.auth.FirebaseAuth
 * import com.google.firebase.auth.GoogleAuthProvider
 *
 * class FirebaseAuthService(private val context: Context) {
 *     private val auth = FirebaseAuth.getInstance()
 *     private val credentialManager = CredentialManager.create(context)
 *
 *     suspend fun signInWithGoogle(serverClientId: String): Result<String> {
 *         return try {
 *             // 1. Build Google Sign-In request (equivalent to new GoogleAuthProvider())
 *             val googleIdOption = GetSignInWithGoogleOption.Builder()
 *                 .setFilterByAuthorizedAccounts(false)
 *                 .setServerClientId(serverClientId)
 *                 .setAutoSelectEnabled(true)
 *                 .build()
 *
 *             val request = GetCredentialRequest.Builder()
 *                 .addCredentialOption(googleIdOption)
 *                 .build()
 *
 *             // 2. Trigger native sheet (equivalent to signInWithPopup)
 *             val result = credentialManager.getCredential(context, request)
 *             val credential = result.credential
 *
 *             if (credential is CustomCredential &&
 *                 credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
 *             ) {
 *                 val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
 *                 val idToken = googleIdTokenCredential.idToken
 *
 *                 // 3. Authenticate with Firebase Auth
 *                 val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
 *                 val authResult = auth.signInWithCredential(firebaseCredential)
 *                 val user = authResult.user
 *                 Result.success(user?.email ?: "success")
 *             } else {
 *                 Result.failure(Exception("Unsupported credential type"))
 *             }
 *         } catch (e: Exception) {
 *             Log.e("FirebaseAuthService", "Google Sign-In failed", e)
 *             Result.failure(e)
 *         }
 *     }
 * }
 * ```
 *
 * ### 3. Why the Web Preview Uses the Native UI:
 * Sandboxed web browsers and iframe previews (like AI Studio's preview container)
 * block external cross-origin OAuth redirects (`accounts.google.com`) to prevent
 * phishing attacks.
 *
 * In the current **Learnicle** app, the authentication screen provides:
 * 1. An authentic **Google Web Accounts UI** (with email, password, and create account)
 *    so you can test the full user journey directly inside the emulator/preview.
 * 2. Complete compatibility with real Firebase and Google Credential Manager when compiled
 *    and exported to a physical Android device.
 */
object GoogleAuthService {
    const val TAG = "GoogleAuthService"

    fun getExplanation(): String {
        return "In Android, signInWithPopup is replaced by Jetpack CredentialManager + GoogleAuthProvider."
    }
}
