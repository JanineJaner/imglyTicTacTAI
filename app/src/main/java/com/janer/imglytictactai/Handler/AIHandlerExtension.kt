package com.janer.imglytictactai.Handler

import android.os.Handler
import android.os.Message
import com.janer.imglytictactai.Activities.TicTacToeActivity
import java.lang.ref.WeakReference



class AIHandlerExtention(activity: TicTacToeActivity) : Handler() {

    private var activityWeakReference: WeakReference<TicTacToeActivity> = WeakReference<TicTacToeActivity>(activity)

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val activity = activityWeakReference.get()
        activity?.let{
            var msg_data = msg.data.getInt("btn_selected")
            when(msg_data)
            {
                in 1..9   -> activity.AI_btn_click(msg.data.getInt("btn_selected"))
                in 23..28 ->  activity.showWaitingDialog(msg_data - 20)
                     21   ->   activity.dismissWaitingDialog()
            }

        }
    }
}