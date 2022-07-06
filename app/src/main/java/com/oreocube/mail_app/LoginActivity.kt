package com.oreocube.mail_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oreocube.mail_app.databinding.ActivityLoginBinding
import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        nicknameEditText.addTextChangedListener {
            if (isAvailableNickname(it.toString())) {
                // 올바른 닉네임 형식이면
                nicknameTextInput.helperText = null
                if (isAvailableEmail(emailEditText.text.toString())) {
                    showNextButton()
                }
            } else {
                nicknameTextInput.helperText = getString(R.string.error_nickname)
                hideNextButton()
            }
        }
        emailEditText.addTextChangedListener {
            // 올바른 이메일 형식이면
            if (isAvailableEmail(it.toString())) {
                emailTextInput.helperText = null
                // 닉네임까지 모두 올바른 형식이면
                if (isAvailableNickname(nicknameEditText.text.toString())) {
                    showNextButton()
                }
            } else {
                emailTextInput.helperText = getString(R.string.error_email)
                hideNextButton()
            }
        }
    }

    private fun isAvailableNickname(nickname: String): Boolean =
        Pattern.matches("^[a-zA-Z0-9]{4,12}\$", nickname)
                && nickname.contains("[0-9]".toRegex())
                && nickname.contains("[a-zA-Z]".toRegex())

    private fun isAvailableEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun showNextButton() {
        binding.nextButton.isEnabled = true
    }

    private fun hideNextButton() {
        binding.nextButton.isEnabled = false
    }
}