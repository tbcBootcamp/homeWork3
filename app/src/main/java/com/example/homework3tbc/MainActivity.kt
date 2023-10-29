package com.example.homework3tbc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.homework3tbc.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isInfoSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            onSaveClick()
        }
        binding.btnClear.setOnLongClickListener {
            clearEditTexts(
                binding.etUsername,
                binding.etEmail,
                binding.etAge,
                binding.etLastName,
                binding.etFirstName
            )
            true
        }
        binding.btnAgain.setOnClickListener {
            resetForm()
        }
    }

    private fun onSaveClick() {
        checkOnEmpty(
            binding.etUsername,
            binding.etEmail,
            binding.etAge,
            binding.etLastName,
            binding.etFirstName
        )

        val isEmailValid = checkEmailField()
        val isLastNameValid = checkLastNameField()
        val isUseNameValid = checkUsernameField()
        val isAgeValid = emptyAge()
        val isFirstNameValid = checkFirstNameField()
        if (isEmailValid && isLastNameValid && isUseNameValid && isAgeValid && isFirstNameValid) {
            displaySavedInfo()
        } else {
            binding.grpInputs.isVisible = true
        }
    }

    private fun checkUsernameField(): Boolean {
        binding.tvUsernameError.isVisible = !validUsername(binding.etUsername.text.toString())
        return validUsername(binding.etUsername.text.toString())
    }

    private fun checkFirstNameField(): Boolean {
        binding.tvNameError.isVisible = binding.etFirstName.text.isNullOrEmpty()
        return !binding.etFirstName.text.isNullOrEmpty()
    }

    private fun checkLastNameField(): Boolean {
        binding.tvLastNameError.isVisible = binding.etLastName.text.isNullOrEmpty()
        return !binding.etLastName.text.isNullOrEmpty()
    }

    private fun checkEmailField(): Boolean {
        return if (isEmailValid(binding.etEmail.text.toString())) {
            binding.tvEmailError.isVisible = false
            true
        } else {
            setEmailErrorText()
            binding.tvEmailError.isVisible = true
            false
        }
    }

    private fun setEmailErrorText() {
        if (binding.etEmail.text.isNullOrEmpty()) {
            binding.tvEmailError.text = getString(R.string.this_field_should_not_be_empty)
        } else {
            binding.tvEmailError.text = getString(R.string.email_format_is_not_correct)
        }
    }

    private fun emptyAge(): Boolean {
        binding.tvAgeError.isVisible = binding.etAge.text.isNullOrEmpty()
        return !binding.etAge.text.isNullOrEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validUsername(username: String): Boolean {
        return username.length >= 10
    }

    private fun checkOnEmpty(vararg editTexts: EditText): Boolean {
        var allFieldsFilled = true
        val redColor = ContextCompat.getColor(this, R.color.red)

        editTexts.forEach { editText ->
            if (editText.text.toString().isEmpty()) {
                editText.setBackgroundColor(redColor)
                allFieldsFilled = false
            } else {
                editText.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        return allFieldsFilled
    }

    private fun clearEditTexts(vararg editTexts: EditText) {
        editTexts.forEach { editText ->
            editText.text.clear()
        }
    }

    private fun displaySavedInfo() {
        binding.grpInputs.isVisible = false

        val savedInfo = getString(
            R.string.saved_information,
            binding.etEmail.text.toString(),
            binding.etUsername.text.toString(),
            binding.etFirstName.text.toString(),
            binding.etLastName.text.toString(),
            binding.etAge.text.toString(),
        )
        binding.tvSavedInfo.text = savedInfo

        binding.tvSavedInfo.isVisible = true
        binding.btnAgain.isVisible = true

        isInfoSaved = true
    }

    private fun resetForm() {
        binding.tvSavedInfo.isVisible = false
        binding.btnAgain.isVisible = false

        clearEditTexts(
            binding.etUsername,
            binding.etEmail,
            binding.etAge,
            binding.etLastName,
            binding.etFirstName
        )
        binding.grpInputs.isVisible = true

        isInfoSaved = false
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isInfoSaved) {
            resetForm()
        } else {
            super.onBackPressed()
        }
    }
}