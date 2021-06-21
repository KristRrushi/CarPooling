package krist.car.biometric


import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class BiometricAdapter(val activity: AppCompatActivity) {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    init {
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> initBiometricPrompt()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> activity.displayMsg("No hardware")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> activity.displayMsg("Hardware Unavailable")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> activity.displayMsg("Biomteric not enrolled")
        }
    }

    private fun initBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(activity);

        biometricPrompt = BiometricPrompt(activity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(activity.applicationContext,
                                "Authentication error: $errString", Toast.LENGTH_SHORT)
                                .show()
                    }

                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)


                        Log.d("lol", result.cryptoObject.toString())
                        Toast.makeText(activity.applicationContext,
                                "Authentication succeeded!", Toast.LENGTH_SHORT)
                                .show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(activity.applicationContext, "Authentication failed",
                                Toast.LENGTH_SHORT)
                                .show()
                    }
                })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

fun AppCompatActivity.displayMsg(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}