package com.oreocube.mail_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oreocube.mail_app.databinding.ActivityLoginBinding
import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val EXTRA_NICKNAME = "NICKNAME"
        const val EXTRA_EMAIL = "EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_NICKNAME, binding.nicknameEditText.text.toString())
        outState.putString(EXTRA_EMAIL, binding.emailEditText.text.toString())

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.nicknameEditText.setText(savedInstanceState.getString(EXTRA_NICKNAME))
        binding.emailEditText.setText(savedInstanceState.getString(EXTRA_EMAIL))
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


        nextButton.setOnClickListener {
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_NICKNAME, nicknameEditText.text.toString())
                    putExtra(MainActivity.EXTRA_EMAIL, emailEditText.text.toString())
                }
            )
            finish()
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