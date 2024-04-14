// Avots: https://github.com/Practical-Coding3/TicTacToeApp/tree/master (tika modificēts oriģinālais kods)
///////////////////////////////////////////////////////////////////////

package com.example.tictactoe_evelinageikina

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView

class PVPTicTacToeBoard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val boardColor: Int
    private val xColor: Int
    private val oColor: Int
    private val winLineColor: Int

    private var winLine: Boolean = false

    private val paint = Paint()
    private var cellSize = 0
    private val gamePVP: PVPGameLogic

    //tiek ieviesti atribūti, lai tiktu zīmēti un iekrāsoti lauki, zīmes un uzvaras līnijas
    init {
        gamePVP = PVPGameLogic()
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0)
        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0)
            xColor = a.getInteger(R.styleable.TicTacToeBoard_xColor, 0)
            oColor = a.getInteger(R.styleable.TicTacToeBoard_oColor, 0)
            winLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0)
        } finally {
            a.recycle()
        }
    }
    //nosaka, cik liels būs spēles lauks, ņemot vērā telefona ekrāna šaurāko vietu, tas ir, platumu vai augstumu,
    //lai spēles lauks pareizi ietilptu ekrānā
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dimension = kotlin.math.min(measuredWidth, measuredHeight)
        cellSize = dimension / 3
        setMeasuredDimension(dimension, dimension)
    }
    //tiek pārbaudīts, uz kura lauka cilvēks uzspieda, vai ir uzvara. tiek arī atjaunots lauks pēc spēlētāja gājiena, pārbauda, vai pēc
    //spēlētāja gājiena ir uzvara, un kurš spēlētājs būs nākamais
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //if (checkCurrentPlayerIsComputer){
        val x = event.x
        val y = event.y
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            val row = kotlin.math.ceil(y / cellSize).toInt()
            val col = kotlin.math.ceil(x / cellSize).toInt()

            if (!winLine){
                if (gamePVP.updateBoard(row, col)) {
                    invalidate()

                    if (gamePVP.winnerCheck()){
                        winLine = true
                        invalidate()
                    }

                    if (gamePVP.getPlayer() % 2 == 0){
                        gamePVP.setPlayer(gamePVP.getPlayer() - 1)
                    }
                    else {
                        gamePVP.setPlayer(gamePVP.getPlayer() + 1)
                    }
                }
            }

            invalidate()
            return true
        }
        return false
    }

    //tiek zīmēti lauki un X un O, kā arī līniju virs tiem laukiem, kur ir uzvara
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        drawGameBoard(canvas)
        drawMarkers(canvas)

        if (winLine) {
            paint.setColor(winLineColor)
            drawWinLine(canvas)
        }
    }
    // tiek zīmēti spēles lauki
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
    //tiek zīmēti X un O atkarībā no spēlētāja, 1 ir X, 2 ir O
    private fun drawMarkers(canvas: Canvas) {
        for (r in 0..2) {
            for (c in 0..2) {
                if (gamePVP.getGameBoard()[r][c] != 0) {
                    if (gamePVP.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c)
                    } else {
                        drawO(canvas, r, c)
                    }
                }
            }
        }
    }
    //tiek zīmēts X
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
    //tiek zīmēts O
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
    //tiek zīmētas uzvaras horizontālās, vertikālās un diagonālās līnijas atkarībā no uzvaras veida
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
        var row: Int = gamePVP.getWinType()[0]
        var col: Int = gamePVP.getWinType()[1]

        when (gamePVP.getWinType()[2]) {
            1 -> drawHorizontalLine(canvas, row, col)
            2 -> drawVerticalLine(canvas, row, col)
            3 -> drawDiagLineNeg(canvas)
            4 -> drawDiagLinePos(canvas)
        }
    }
    //spēles sākumā tiek uzstādītas pogas, spēlētāju kārtas teksts un paņemti spēlētāju ievadītie vārdi
    fun setUpGame (playAgain: Button, home: Button, playerDisplay: TextView, names: Array<String>){
        gamePVP.setPlayAgainBTN(playAgain)
        gamePVP.setHomeBTN(home)
        gamePVP.setPlayerTurn(playerDisplay)
        gamePVP.setPlayerNames(names)
    }

    fun resetGame() {
        gamePVP.resetGame()
        winLine = false
    }
}
