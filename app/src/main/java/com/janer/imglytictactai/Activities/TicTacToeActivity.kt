package com.janer.imglytictactai.Activities

import android.content.Intent
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
import com.janer.imglytictactai.TicTacToe
import com.janer.imglytictactai.Threads.TimerThread
import com.janer.imglytictactai.Utils.MyAnimationUtils
import kotlinx.android.synthetic.main.activity_tictactoe.*

lateinit var resultHandler: Handler
lateinit var AIHandler:Handler
class TicTacToeActivity : AppCompatActivity() {

    val TAG = "TicTacToe"
    private lateinit var myTicTacToe: TicTacToe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        val endGameIntent = Intent(this, EndScreenActivity::class.java)
        var layoutView = LayoutInflater.from(this).inflate(R.layout.activity_tictactoe, null)
        myTicTacToe = TicTacToe(layoutView)

        val spannable = SpannableString(resources.getString(R.string.s_title))


        spannable.setSpan(
            StyleSpan(BOLD), 7, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textview_gameTitle.text = spannable


        resultHandler = TimerHandlerExtention(this)
        AIHandler = AIHandlerExtention(this)

        startCount()

    }
    lateinit var countThread: TimerThread
    override fun onResume() {
        super.onResume()
        countThread.resume()
    }

    override fun onPause() {
        super.onPause()
        countThread.pause()
    }


    override fun onStop() {
        super.onStop()
        countThread.pause()
    }


    override fun onDestroy() {
        super.onDestroy()
        stopCount()
        finish()

    }


    fun startCount(){
        countThread = TimerThread()
        countThread.run()
    }

    fun stopCount(){
        countThread.stop()

    }

    fun updateResult(result:String){
        textview_timer.text = result
    }

    fun btnClick(view: View) {
        MyAnimationUtils(view).apply {
            func_bounceAnimation()
        }

        myTicTacToe.btnClick(view)
    }

    fun AI_btn_click(id:Int){
        var buSelect: Button

        when(id)
        {
            1 -> buSelect = btn1
            2 -> buSelect = btn2
            4 -> buSelect = btn3
            5 -> buSelect = btn4
            6 -> buSelect = btn5
            7 -> buSelect = btn6
            8 -> buSelect = btn7
            9 -> buSelect = btn9
            else -> buSelect = btn1
        }

        btnClick(buSelect)
    }

    fun showWaitingDialog(dismissCountdown:Int){
        ProcessingAIMoveDialog(dismissCountdown).show(
            this.supportFragmentManager,
            "tag_AIProcessing"
        )


    }

    fun dismissWaitingDialog() {
       //  ProcessingAIMoveDialog().hide()
     //   Dialog(this).hideAlertDialog(R.layout.dialog_aiprogress)
    }

}

