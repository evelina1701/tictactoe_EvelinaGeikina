// Avots: https://github.com/Practical-Coding3/TicTacToeApp/tree/master
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

class PVCActivityPlayerSetup : AppCompatActivity() {

    private lateinit var player: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pvcplayer_setup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        player = findViewById(R.id.enterPlayer)

    }

    fun backButtonClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun playPVCButtonClick(view: View) {
        val playerName = player.text.toString()
        val intent = Intent(this, PVCGameDisplay::class.java)
        intent.putExtra("PLAYER_NAMES", arrayOf(playerName))
        startActivity(intent)
    }
}