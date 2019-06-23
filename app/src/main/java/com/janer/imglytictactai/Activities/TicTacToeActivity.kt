package com.janer.imglytictactai.Activities

import android.content.Intent
import android.graphics.Typeface.BOLD
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.janer.imglytictactai.DialogFragments.ProcessingAIMoveDialog
import com.janer.imglytictactai.R
import com.janer.imglytictactai.TicTacToe
import com.janer.imglytictactai.Utils.MyAnimationUtils
import kotlinx.android.synthetic.main.activity_tictactoe.*
import java.lang.ref.WeakReference

lateinit var resultHandler: Handler
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

        ProcessingAIMoveDialog().show(
            this.supportFragmentManager,
            "tag_AIProcessing"
        )

        resultHandler = TimerHandlerExtention(this)

        startCount()

    }


    override fun onDestroy() {
        super.onDestroy()
        stopCount()
    }

    lateinit var countThread: TimerThread
    fun startCount(){
        countThread = TimerThread()
        countThread.start()
    }

    fun stopCount(){
        countThread.interrupt()

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


}

// Test Thread Run
class TimerThread : Thread() {
    val TAG = "Timer Thread"

    override fun run() {
        super.run()
        Log.i(TAG, "Running in Timer thread")
        for(i in 0 until 100)
        {
            //Send Message to UI here
            publishProgress(i)
            sleep(1000)
        }
    }

    fun publishProgress(count:Int){
        Log.v(TAG,"Sending back to the UI Thread")
        var msgBundle = Bundle().also {
            it.putString("result", count.toString())
        }
        var msg: Message = Message()
        msg.data = msgBundle
        resultHandler.sendMessage(msg)

    }
}

class TimerHandlerExtention(activity: TicTacToeActivity) : Handler() {

    private var activityWeakReference: WeakReference<TicTacToeActivity> = WeakReference<TicTacToeActivity>(activity)

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val activity = activityWeakReference.get()
        activity?.let{
            activity.updateResult(msg.data.getString("result"))
        }
    }



}