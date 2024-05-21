package org.hyperskill.secretdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

const val PASSWORD = "1234"

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button = findViewById<Button>(R.id.btnLogin)
        val editText = findViewById<EditText>(R.id.etPin)

        button.setOnClickListener {
            if (editText.text.toString() != PASSWORD) {
                editText.error = "Wrong PIN!"
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}