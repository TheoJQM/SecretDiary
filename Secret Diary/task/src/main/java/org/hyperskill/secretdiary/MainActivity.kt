package org.hyperskill.secretdiary
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.app.AlertDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import java.text.SimpleDateFormat
import java.util.Locale

const val PREFERENCES_NAME = "PREF_DIARY"

class MainActivity : AppCompatActivity() {
    private val notes = mutableListOf<String>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        load()

        val editText = findViewById<EditText>(R.id.etNewWriting)
        val saveButton = findViewById<Button>(R.id.btnSave)
        val undoButton = findViewById<Button>(R.id.btnUndo)

        saveButton.setOnClickListener {
            if (editText.text.toString().trim().isNotEmpty()) {
                addNote(editText.text.toString().trim())
                editText.text.clear()
                save()
            } else {
                editText.text.clear()
                Toast.makeText(this, "Empty or blank input cannot be saved", Toast.LENGTH_SHORT).show()
            }
        }

        undoButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Remove last note")
                .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton("Yes") { _, _ ->
                    if (notes.isNotEmpty()) notes.removeLast()
                    updateTextView()
                    save()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun addNote(note: String) {
        val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Clock.System.now().toEpochMilliseconds())

        val newNote = "$dateTime\n$note"
        notes.add(newNote)
        updateTextView()
    }

    private fun updateTextView() {
        findViewById<TextView>(R.id.tvDiary).text = notes.reversed().joinToString("\n\n").trim()
    }

    private fun save() {
        val editor = sharedPreferences.edit()
        editor.putString("KEY_DIARY_TEXT", notes.reversed().joinToString("\n\n").trim())
        editor.apply()
    }

    private fun load() {
        sharedPreferences.getString("KEY_DIARY_TEXT", "")?.let { notes.addAll(it.trim().split("\n\n").reversed()) }
        updateTextView()
    }

}