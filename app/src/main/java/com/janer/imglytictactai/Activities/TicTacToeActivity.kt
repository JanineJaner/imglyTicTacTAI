package com.janer.imglytictactai.Activities

import android.graphics.Typeface.BOLD
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.janer.imglytictactai.DialogFragments.ProcessingAIMoveDialog
import com.janer.imglytictactai.Handler.AIHandlerExtention
import com.janer.imglytictactai.Handler.TimerHandlerExtention
import com.janer.imglytictactai.R
import com.janer.imglytictactai.GameLoop.TicTacToe
import com.janer.imglytictactai.GameLoop.TimerThread
import com.janer.imglytictactai.Utils.MyAnimationUtils
import kotlinx.android.synthetic.main.activity_tictactoe.*

lateinit var timerHandler: Handler
lateinit var AIHandler:Handler

class TicTacToeActivity : AppCompatActivity() {
    lateinit var timerThread: TimerThread

    val TAG = "TicTacToe"
    private lateinit var myTicTacToe: TicTacToe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)

        timerHandler = TimerHandlerExtention(this)
        AIHandler = AIHandlerExtention(this)

        var layoutView = LayoutInflater.from(this).inflate(R.layout.activity_tictactoe, null)
        myTicTacToe = TicTacToe(layoutView)

       // val endGameIntent = Intent(this, EndScreenActivity::class.java)

        val spannable = SpannableString(resources.getString(R.string.s_title))
        spannable.setSpan(
            StyleSpan(BOLD), 7, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textview_gameTitle.text = spannable


        //startCount()
        timerThread = TimerThread()
        timerThread.run()
        myTicTacToe.startGame(timerThread)

    }

    override fun onResume() {
        super.onResume()
        timerThread.resume()
    }

    override fun onPause() {
        super.onPause()
        timerThread.stop()
    }


    override fun onStop() {
        super.onStop()
        timerThread.stop()
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        timerThread.stop()
        finish()

    }



    fun updateResult(result:String){
        textview_timer.text = result
    }

    fun player_btn_click(view: View) {
        MyAnimationUtils(view).apply {
            func_bounceAnimation()
        }

        myTicTacToe.selectCell(view,timerThread)
    }

    fun AI_btn_click(cellID:Int){
        var btnSelected: Button

        when(cellID)

        {
            0 -> btnSelected = btn1
            1 -> btnSelected = btn2
            2 -> btnSelected = btn3
            3 -> btnSelected = btn4
            4 -> btnSelected = btn5
            5 -> btnSelected = btn6
            6 -> btnSelected = btn7
            7 -> btnSelected = btn8
            8 -> btnSelected = btn9
            else -> btnSelected = btn1
        }

        myTicTacToe.selectCell(btnSelected,timerThread)
    }

    fun showWaitingDialog(dismissCountdown:Int){
        // actvity resumed when dialog called
        // TODO: DIALOG CALLED - ACTIVITY RESUME: find better solution
        timerThread.pause()
        ProcessingAIMoveDialog(dismissCountdown).show(
            this.supportFragmentManager,
            "tag_AIProcessing"
        )

}



}

