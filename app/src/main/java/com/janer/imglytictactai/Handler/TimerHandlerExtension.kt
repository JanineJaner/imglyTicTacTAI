package com.janer.imglytictactai.Handler

import android.os.Handler
import android.os.Message
import com.janer.imglytictactai.Activities.TicTacToeActivity
import java.lang.ref.WeakReference

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