// Avoti: https://github.com/Practical-Coding3/TicTacToeApp/tree/master
//        ChatGPT (fragments, kurš tika iegūts ar mākslīgo intelektu, ir nodalīts ar /// no augšas un apakšas)
///////////////////////////////////////////////////////////////////////

package com.example.tictactoe_evelinageikina

import android.view.View
import android.widget.Button
import android.widget.TextView

class PVCGameLogic {
    private var boardArray: Array<IntArray>

    private var pNames = arrayOf("Player")
    //private var playerTypes = arrayOf("Human", "Computer")
    //private String[] playerNames = {"Player 1", "Player 2"};
    // 1st element -> row, 2nd element -> col, 3rd element -> line type
    private var winType = arrayOf(-1, -1, -1)

    private lateinit var playAgainBTN: Button
    private lateinit var homeBTN: Button
    private lateinit var pTurn: TextView

    private var currentPlayer = 1

    init {
        boardArray = Array(3) { IntArray(3) { 0 } }
    }

    fun updateBoard(row: Int, col: Int): Boolean {
        if (boardArray[row - 1][col - 1] == 0) {
            boardArray[row - 1][col - 1] = currentPlayer
            currentPlayer = 3 - currentPlayer
//            if (currentPlayer == 1){
//                //playerTurn.setText((playerNames[1] + "'s Turn"))
//                makeAIMove()
//                //if (playerTypes[currentPlayer-1] == "Computer"){
//                // computerMove()
//                // change current player to 2
//                // }
//            }
//            else {
//                playerTurn.setText((playerNames[0] + "'s Turn"))
//            }

            return true
        } else {
            return false
        }
    }

    fun winnerCheck(): Boolean {
        var isWin: Boolean = false

        for (r in 0..2) {
            if (boardArray[r][0] == boardArray[r][1] && boardArray[r][0] == boardArray[r][2] && boardArray[r][0] != 0) {
                winType = arrayOf(r, 0, 1)
                isWin = true
            }
        }

        for (c in 0..2) {
            if (boardArray[0][c] == boardArray[1][c] && boardArray[2][c] == boardArray[0][c] && boardArray[0][c] != 0) {
                winType = arrayOf(0, c, 2)
                isWin = true
            }
        }

        if (boardArray[0][0] == boardArray[1][1] && boardArray[0][0] == boardArray[2][2] && boardArray[0][0] != 0) {
            winType = arrayOf(0, 2, 3)
            isWin = true
        }

        if (boardArray[2][0] == boardArray[1][1] && boardArray[2][0] == boardArray[0][2] && boardArray[2][0] != 0) {
            winType = arrayOf(2, 2, 4)
            isWin = true
        }

        var boardFilled: Int = 0
        for (r in 0..2){
            for(c in 0..2){
                if (boardArray[r][c] != 0){
                    boardFilled++
                }
            }
        }

        if (isWin){
            if(currentPlayer == 2) {
                playAgainBTN.visibility = View.VISIBLE
                homeBTN.visibility = View.VISIBLE
                pTurn.setText((pNames[0]) + " Won!")
                return true
            }
            else{
                playAgainBTN.visibility = View.VISIBLE
                homeBTN.visibility = View.VISIBLE
                pTurn.setText((pNames[0]) + " Lose!")
                return true
            }
        }
        else{
            if (boardFilled == 9){
                playAgainBTN.visibility = View.VISIBLE
                homeBTN.visibility = View.VISIBLE
                pTurn.setText("Tie Game!")
                return false
            }
            else {
                return false
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ChatGPT prompt:
    // *kods no GameLogic.kt faila*
    // here is the game logic for PVP mode tic tac toe. change code, so that a player can play against computer
    fun makeAIMove() {
        val emptyCells = mutableListOf<Move>()
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (boardArray[i][j] == 0) {
                    emptyCells.add(Move(i, j))
                }
            }
        }
        if (emptyCells.isNotEmpty()) {
            val rndMove = emptyCells.random()
            boardArray[rndMove.row][rndMove.col] = currentPlayer
            currentPlayer = 3 - currentPlayer
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun checkCurrentPlayerIsComputer(): Boolean {
        if (currentPlayer == 2){
            return true
        }
        return false;
    }

    fun resetGame() {
        boardArray = Array(3) { IntArray(3) { 0 } }
        currentPlayer = 1
        playAgainBTN.visibility = View.GONE
        homeBTN.visibility = View.GONE
        pTurn.setText("")
    }

    fun setPlayAgainBTN (playAgainBTN: Button){
        this.playAgainBTN = playAgainBTN
    }

    fun setHomeBTN (homeBTN: Button){
        this.homeBTN = homeBTN
    }

    fun setPlayerTurn (playerTurn: TextView){
        this.pTurn = playerTurn
    }

    fun setPlayerNames(playerNames: Array<String>) {
        this.pNames = playerNames
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

   data class Move(val row: Int, val col: Int)
}
