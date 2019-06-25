package com.janer.imglytictactai.DialogFragments

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.janer.imglytictactai.R
import com.janer.imglytictactai.Utils.TextViewStyles
import kotlinx.android.synthetic.main.activity_tictactoe.*
import kotlinx.android.synthetic.main.dialog_aiprogress.view.*


@SuppressLint("ValidFragment")
class ProcessingAIMoveDialog(var dismissCountdown: Int): DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        inflater.inflate(R.layout.dialog_aiprogress, null).apply{
            TextViewStyles().toBold(
                textview_msg_aiprocess,
                resources.getString(R.string.s_AI_move_processing),
                0,
                2
            )
            Log.i("SELECTION", "Text Style change -> Dialog")
            dialog.setCanceledOnTouchOutside(false) //prevent dialog closing when screen touched
            Handler().postDelayed(Runnable {
                kotlin.run {
                    dismiss()
                }
            },dismissCountdown*1000.toLong())

            return this
        }
    }





}

