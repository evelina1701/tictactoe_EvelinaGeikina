// Avots: https://github.com/Practical-Coding3/TicTacToeApp/tree/master (tika modificēts oriģinālais kods)
///////////////////////////////////////////////////////////////////////

package com.example.tictactoe_evelinageikina

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PVPActivityPlayerSetup : AppCompatActivity() {

    private lateinit var player1: EditText
    private lateinit var player2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pvpplayer_setup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        player1 = findViewById(R.id.enterPlayer1)
        player2 = findViewById(R.id.enterPlayer2)
    }

    fun backButtonClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun playPVPButtonClick(view: View) {
        val player1Name = player1.text.toString()
        val player2Name = player2.text.toString()
        val intent = Intent(this, PVPGameDisplay::class.java)
        intent.putExtra("PLAYER_NAMES", arrayOf(player1Name, player2Name))
        startActivity(intent)
    }
}