// Avoti: https://github.com/Practical-Coding3/TicTacToeApp/tree/master
//        Gemini (fragments, kurš tika iegūts ar mākslīgo intelektu, ir nodalīts ar /// no augšas un apakšas)
///////////////////////////////////////////////////////////////////////

package com.example.tictactoe_evelinageikina

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView

class PVCTicTacToeBoard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val boardColor: Int
    private val xColor: Int
    private val oColor: Int
    private val winningLineColor: Int

    private var winLine: Boolean = false

    private val paint = Paint()
    private var cellSize = 0
    private val gamePVC: PVCGameLogic

    init {
        gamePVC = PVCGameLogic()
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0)
        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0)
            xColor = a.getInteger(R.styleable.TicTacToeBoard_xColor, 0)
            oColor = a.getInteger(R.styleable.TicTacToeBoard_oColor, 0)
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dimension = kotlin.math.min(measuredWidth, measuredHeight)
        cellSize = dimension / 3
        setMeasuredDimension(dimension, dimension)
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Gemini prompt:
    // *kods no PVCTicTacToeBoard.kt faila*
    // *kods no PVCGameLogic.kt*
    // analyze code and comments, how can I change this code,
    // so that it would work like PvC mode. show exact places where I can add or change something
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!gamePVC.checkCurrentPlayerIsComputer()) {
            val x = event.x
            val y = event.y
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN) {
                val row = kotlin.math.ceil(y / cellSize).toInt()
                val col = kotlin.math.ceil(x / cellSize).toInt()

                if (!winLine) {
                    if (gamePVC.updateBoard(row, col)) {
                        invalidate()
                        Log.d("PVCTicTacToeBoard", "Game board updated")

                        if (gamePVC.winnerCheck()) {
                            winLine = true
                            invalidate()
                        } else {
                            // Make AI move after successful human move
                            gamePVC.makeAIMove()
                            invalidate()
                            Log.d("PVCTicTacToeBoard", "Computer made a move")
                            if (gamePVC.winnerCheck()) {
                                winLine = true
                            }
                            invalidate()

                        }
                    }
                }
                invalidate()
                return true
            }
        }
        return false
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("PVCTicTacToeBoard", "onDraw called")
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        drawGameBoard(canvas)
        drawMarkers(canvas)

        if (winLine) {
            paint.setColor(winningLineColor)
            drawWinLine(canvas)
        }
    }

    private fun drawGameBoard(canvas: Canvas) {
        paint.color = boardColor
        paint.strokeWidth = 16f
        for (c in 1 until 3) {
            canvas.drawLine(cellSize * c.toFloat(), 0f, cellSize * c.toFloat(), canvas.width.toFloat(), paint)
        }
        for (r in 1 until 3) {
            canvas.drawLine(0f, cellSize * r.toFloat(), canvas.width.toFloat(), cellSize * r.toFloat(), paint)
        }
    }

    private fun drawMarkers(canvas: Canvas) {
        for (r in 0..2) {
            for (c in 0..2) {
                if (gamePVC.getGameBoard()[r][c] != 0) {
                    if (gamePVC.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c)
                    } else {
                        drawO(canvas, r, c)
                    }
                }
            }
        }
    }

    private fun drawX(canvas: Canvas, row: Int, col: Int) {
        paint.color = xColor
        canvas.drawLine(
            (col * cellSize + cellSize * 0.2).toFloat(),
            (row * cellSize + cellSize * 0.2).toFloat(),
            ((col + 1) * cellSize - cellSize * 0.2).toFloat(),
            ((row + 1) * cellSize - cellSize * 0.2).toFloat(),
            paint
        )
        canvas.drawLine(
            ((col + 1) * cellSize - cellSize * 0.2).toFloat(),
            (row * cellSize + cellSize * 0.2).toFloat(),
            (col * cellSize + cellSize * 0.2).toFloat(),
            ((row + 1) * cellSize - cellSize * 0.2).toFloat(),
            paint
        )
    }

    private fun drawO(canvas: Canvas, row: Int, col: Int) {
        paint.color = oColor
        canvas.drawOval(
            (col * cellSize + cellSize * 0.2).toFloat(),
            (row * cellSize + cellSize * 0.2).toFloat(),
            ((col * cellSize + cellSize) - cellSize * 0.2).toFloat(),
            ((row * cellSize + cellSize) - cellSize * 0.2).toFloat(),
            paint
        )
    }

    private fun drawHorizontalLine(canvas: Canvas, row: Int, col: Int) {
        canvas.drawLine(col.toFloat(), (row*cellSize + cellSize/2).toFloat(), (cellSize*3).toFloat(), (row*cellSize + cellSize/2).toFloat(), paint)
    }

    private fun drawVerticalLine(canvas: Canvas, row: Int, col: Int) {
        canvas.drawLine((col*cellSize + cellSize/2).toFloat(), row.toFloat(), (col*cellSize + cellSize/2).toFloat(), (cellSize*3).toFloat(), paint)
    }

    private fun drawDiagLinePos(canvas: Canvas) {
        canvas.drawLine(0.toFloat(), (cellSize*3).toFloat(), (cellSize*3).toFloat(), 0.toFloat(), paint)
    }

    private fun drawDiagLineNeg(canvas: Canvas) {
        canvas.drawLine(0.toFloat(), 0.toFloat(), (cellSize*3).toFloat(), (cellSize*3).toFloat(), paint)
    }

    private fun drawWinLine(canvas: Canvas) {
        var row: Int = gamePVC.getWinType()[0]
        var col: Int = gamePVC.getWinType()[1]

        when (gamePVC.getWinType()[2]) {
            1 -> drawHorizontalLine(canvas, row, col)
            2 -> drawVerticalLine(canvas, row, col)
            3 -> drawDiagLineNeg(canvas)
            4 -> drawDiagLinePos(canvas)
        }
    }

    fun setUpGame (playAgain: Button, home: Button, playerDisplay: TextView, names: Array<String>){
        gamePVC.setPlayAgainBTN(playAgain)
        gamePVC.setHomeBTN(home)
        gamePVC.setPlayerTurn(playerDisplay)
        gamePVC.setPlayerNames(names)
        Log.d("PVCTicTacToeBoard", "Game elements set up")
    }

    fun resetGame() {
        gamePVC.resetGame()
        winLine = false
    }


}
