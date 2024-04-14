// Avots: https://github.com/Practical-Coding3/TicTacToeApp/tree/master (tika modificēts oriģinālais kods)
///////////////////////////////////////////////////////////////////////

package com.example.tictactoe_evelinageikina

import android.view.View
import android.widget.Button
import android.widget.TextView

class PVPGameLogic {
    //lauks tiek kodā attēlots kā 3x3 masīvs, 0 - tukšs lauks, 1 - lauks aizpildīts ar X jeb 1.spēlētāja gājienu, 2 - lauks aizpildīts ar O
    // jeb 2.spēlētāja gājienu
    private var boardArray: Array<IntArray>
    private var playerNames = arrayOf("Player 1", "Player 2")

    // 1st element -> row, 2nd element -> col, 3rd element -> line type
    private var winType = arrayOf(-1, -1, -1)

    private lateinit var playAgainBTN: Button
    private lateinit var homeBTN: Button
    private lateinit var playerTurn: TextView

    private var currentPlayer = 1

    init {
        boardArray = Array(3) { IntArray(3) { 0 } }
    }
    //tiek atjaunots masīvs un teksts, kur rakstīts, kura gājiens šobrīd esot
    fun updateBoard(row: Int, col: Int): Boolean {
        if (boardArray[row - 1][col - 1] == 0) {
            boardArray[row - 1][col - 1] = currentPlayer

            if (currentPlayer == 1){
                playerTurn.setText((playerNames[1] + "'s Turn"))
            }
            else {
                playerTurn.setText((playerNames[0] + "'s Turn"))
            }
            return true
        } else {
            return false
        }
    }
    //tiek pārbaudīts, kāda veida uzvara ir notikusi
    fun winnerCheck(): Boolean {
        var isWin: Boolean = false

        //pārbauda, vai ir horizontāla līnija ar vienādām zīmēm
        for (r in 0..2) {
            if (boardArray[r][0] == boardArray[r][1] && boardArray[r][0] == boardArray[r][2] && boardArray[r][0] != 0) {
                winType = arrayOf(r, 0, 1)
                isWin = true
            }
        }
        //pārbauda, vai ir vertikāla līnija ar vienādām zīmēm
        for (c in 0..2) {
            if (boardArray[0][c] == boardArray[1][c] && boardArray[2][c] == boardArray[0][c] && boardArray[0][c] != 0) {
                winType = arrayOf(0, c, 2)
                isWin = true
            }
        }
        //pārbauda, vai ir diagonāla līnija no kreisā augšējā stūra uz labo apakšējo stūri ar vienādām zīmēm
        if (boardArray[0][0] == boardArray[1][1] && boardArray[0][0] == boardArray[2][2] && boardArray[0][0] != 0) {
            winType = arrayOf(0, 2, 3)
            isWin = true
        }
        //pārbauda, vai ir diagonāla līnija no labā augšējā stūra uz kreiso apakšējo stūri ar vienādām zīmēm
        if (boardArray[2][0] == boardArray[1][1] && boardArray[2][0] == boardArray[0][2] && boardArray[2][0] != 0) {
            winType = arrayOf(2, 2, 4)
            isWin = true
        }
        //tiek skaitīts, cik lauki šobrīd ir aizņemti
        var boardFilled: Int = 0
        for (r in 0..2){
            for(c in 0..2){
                if (boardArray[r][c] != 0){
                    boardFilled++
                }
            }
        }
        //ja ir uzvara, tad parādās Play Again un Home pogas un teksts ar uzvarētāju
        if (isWin){
            playAgainBTN.visibility = View.VISIBLE
            homeBTN.visibility = View.VISIBLE
            playerTurn.setText((playerNames[currentPlayer-1]) + " Won!")
            return true
        }
        else{
            //ja visi lauki aizpildīti un nav uzvaras, tad ir neizšķirts un pogas arī parādās
            if (boardFilled == 9){
                playAgainBTN.visibility = View.VISIBLE
                homeBTN.visibility = View.VISIBLE
                playerTurn.setText("Tie Game!")
                return false
            }
            else {
                return false
            }
        }
    }
    //tiek atjaunots spēles lauks pēc Play Again pogas nospiešanas
    fun resetGame() {
        boardArray = Array(3) { IntArray(3) { 0 } }
        currentPlayer = 1
        playAgainBTN.visibility = View.GONE
        homeBTN.visibility = View.GONE
        playerTurn.setText((playerNames[0] + "'s Turn"))
    }

    fun setPlayAgainBTN (playAgainBTN: Button){
        this.playAgainBTN = playAgainBTN
    }

    fun setHomeBTN (homeBTN: Button){
        this.homeBTN = homeBTN
    }

    fun setPlayerTurn (playerTurn: TextView){
        this.playerTurn = playerTurn
    }

    fun setPlayerNames(playerNames: Array<String>) {
        this.playerNames = playerNames
    }

    fun getGameBoard(): Array<IntArray> {
        return boardArray
    }

    fun setPlayer(currentPlayer: Int) {
        this.currentPlayer = currentPlayer
    }

    fun getPlayer(): Int {
        return currentPlayer
    }

    fun getWinType(): Array<Int>{
        return winType
    }
}
