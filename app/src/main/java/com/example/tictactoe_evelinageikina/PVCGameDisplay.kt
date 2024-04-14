// Avoti: https://github.com/Practical-Coding3/TicTacToeApp/tree/master (tika modificēts oriģinālais kods)
//        ChatGPT (fragments, kurš tika iegūts ar mākslīgo intelektu, ir nodalīts ar /// no augšas un apakšas)
///////////////////////////////////////////////////////////////////////

package com.example.tictactoe_evelinageikina

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment

//tiek izveidots greeting message
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// ChatGPT prompt:
// *kods no PVPGameDisplay.kt faila*
// how can i add dialog fragment which displays greeting message using
// playerNames like "Let's start the game, Eva and Chris!"
class GreetingDialogFragmentPVC : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val playerNames = arguments?.getStringArray("PLAYER_NAMES") ?: emptyArray()
        val greetingMessage = "Let's start the game, ${playerNames[0]}!"

        return AlertDialog.Builder(requireContext())
            .setMessage(greetingMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////
class PVCGameDisplay : AppCompatActivity() {
    //tiek ieviests pats spēles lauks, Play Again un Home pogas, teksta lauks
    private lateinit var pvcTicTacToeBoard: PVCTicTacToeBoard
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pvcgame_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val playAgainBTN: Button = findViewById(R.id.play_again)
        val homeBTN: Button = findViewById(R.id.home_button)
        val playerTurn: TextView = findViewById(R.id.pvcPlayerDisplay)

        playAgainBTN.visibility = View.GONE
        homeBTN.visibility = View.GONE

        // tiek paņemts spēlētāja ievadītais vārds, kurš tika saglabāts masīvā "PLAYER_NAMES", un vārds tiek izmantots paziņojumu izvadei
        // un tekstam virs spēles lauka
        var playerNames: Array<String> = intent.getStringArrayExtra("PLAYER_NAMES") as Array<String>
        
        playerTurn.setText("")

        pvcTicTacToeBoard = findViewById(R.id.pvcTicTacToeBoard)
        //tiek uzstādītas pogas un teksta lauks, paņemti spēlētāju vārds
        pvcTicTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerNames)
        Log.d("PVCGameDisplay", "PVC Tic Tac Toe Board set up")
        //tiek izvadīts greeting message
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        // ChatGPT prompt:
        // *kods no PVPGameDisplay.kt faila*
        // how can i add dialog fragment which displays greeting message using
        // playerNames like "Let's start the game, Eva and Chris!"
        if (playerNames != null) {
            val dialogFragment = GreetingDialogFragmentPVC()
            val bundle = Bundle()
            bundle.putStringArray("PLAYER_NAMES", playerNames)
            dialogFragment.arguments = bundle
            dialogFragment.show(supportFragmentManager, "GreetingDialog")
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    fun playAgainBTNClick(view: View){
        pvcTicTacToeBoard.resetGame()
        pvcTicTacToeBoard.invalidate()
    }

    fun homeBTNClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}