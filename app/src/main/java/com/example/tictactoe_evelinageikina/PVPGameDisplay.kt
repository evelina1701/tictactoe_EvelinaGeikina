// Avoti: https://github.com/Practical-Coding3/TicTacToeApp/tree/master
//        ChatGPT (fragments, kurš tika iegūts ar mākslīgo intelektu, ir nodalīts ar /// no augšas un apakšas)
///////////////////////////////////////////////////////////////////////

package com.example.tictactoe_evelinageikina

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment

//import com.example.tictactoe_evelinageikina.R

//tiek izvadīts greeting message
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// ChatGPT prompt:
// *kods no PVPGameDisplay.kt faila*
// how can i add dialog fragment which displays greeting message using
// playerNames like "Let's start the game, Eva and Chris!"
class GreetingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val playerNames = arguments?.getStringArray("PLAYER_NAMES") ?: emptyArray()
        val greetingMessage = "Let's start the game, ${playerNames.joinToString(" and ")}!"

        return AlertDialog.Builder(requireContext())
            .setMessage(greetingMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////

class PVPGameDisplay : AppCompatActivity() {
//tiek ieviests pats spēles lauks, Play Again un Home pogas, teksta lauks
    private lateinit var ticTacToeBoard: TicTacToeBoard

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pvpgame_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val playAgainBTN: Button = findViewById(R.id.play_again)
        val homeBTN: Button = findViewById(R.id.home_button)
        val playerTurn: TextView = findViewById(R.id.pvpPlayerDisplay)

        playAgainBTN.visibility = View.GONE
        homeBTN.visibility = View.GONE

        var playerNames: Array<String> = intent.getStringArrayExtra("PLAYER_NAMES") as Array<String>

        if(playerNames != null){
            playerTurn.setText((playerNames[0] + "'s Turn"))
        }

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard)
        //tiek uzstādītas pogas un teksta lauks, paņemti spēlētāju vārdi
        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerNames)
        //tiek izvadīts greeting message

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        // ChatGPT prompt:
        // *kods no PVPGameDisplay.kt faila*
        // how can i add dialog fragment which displays greeting message using
        // playerNames like "Let's start the game, Eva and Chris!"
        if (playerNames != null) {
            val dialogFragment = GreetingDialogFragment()
            val bundle = Bundle()
            bundle.putStringArray("PLAYER_NAMES", playerNames)
            dialogFragment.arguments = bundle
            dialogFragment.show(supportFragmentManager, "GreetingDialog")
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    fun playAgainBTNClick(view: View){
        ticTacToeBoard.resetGame()
        ticTacToeBoard.invalidate()
    }

    fun homeBTNClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}