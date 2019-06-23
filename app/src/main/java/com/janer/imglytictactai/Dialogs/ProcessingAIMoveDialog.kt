package com.janer.imglytictactai.Dialogs

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.janer.imglytictactai.R
import kotlinx.android.synthetic.main.dialog_aiprogress.view.*

class ProcessingAIMoveDialog: DialogFragment() {

    lateinit var mDialogView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        LayoutInflater.from(context).inflate(R.layout.dialog_aiprogress, null).apply{
            textview_msg_aiprocess.text = SpannableString(resources.getString(R.string.s_AI_move_processing)).apply{
                setSpan(StyleSpan(Typeface.BOLD),0,2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        return mDialogView
    }


}

