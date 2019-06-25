package com.janer.imglytictactai.Utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView


class TextViewStyles{

    fun toBold(textview:TextView,text:String,startBoldIndex:Int = 0,endBoldIndex:Int = text.length ){
        val spannable = SpannableString(text)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startBoldIndex,
            endBoldIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textview.text = spannable
    }
}