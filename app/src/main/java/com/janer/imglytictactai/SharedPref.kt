package com.janer.imglytictactai

import android.content.Context
import android.content.SharedPreferences

class SharedPref (val context: Context,var prefName:String = "prefs")
{
    var sharedPref: SharedPreferences


    init{
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }
    //---------------------Shared Pref----------------------------//


    fun func_saveBoolState(sharedPrefKey: String, state:Boolean) {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            .edit().run {
                putBoolean(sharedPrefKey, state)
                apply()
            }
    }

    fun func_loadBoolState(sharedPrefKey:String):Boolean{
        return sharedPref.getBoolean(sharedPrefKey,false)
    }

    fun func_saveString(sharedPrefKey:String, stringTosSave:String)
    {
        context.getSharedPreferences(prefName,Context.MODE_PRIVATE).edit().run{
            putString(sharedPrefKey,stringTosSave)
            apply()
        }

    }

    fun func_loadString(sharedPrefKey:String):String
    {
        return sharedPref.getString(sharedPrefKey,"Default String: Nothing Found")

    }

}