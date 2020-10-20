package com.example.neostore

import android.content.Context
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.neostore.R


open class BaseClassActivity:AppCompatActivity() {

    open fun showToast(mContext: Context?, message: String?) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }
   open fun setScreenTitle(title: String) {
       if (getSupportActionBar() != null){
           getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
           getSupportActionBar()?.setDisplayShowHomeEnabled(true);
           getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
           getSupportActionBar()?.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + title + "</font>")));
       }    }

    open fun setEnabledTitle(){
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
            getSupportActionBar()?.setDisplayShowTitleEnabled(false);

            //supportActionBar?.setTitle("Tables")
            //    getSupportActionBar()?.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.Myaccount) + "</font>")));

        }
    }
    open fun setDefaults(key: String?, value: Float?, context: Context?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        if (value != null) {
            editor.putFloat(key, value)
        }
        editor.commit()
    }

    open fun getDefaults(key: String?, context: Context?): Float {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getFloat(key, 0f)
    }
}