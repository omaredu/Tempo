package io.github.omaredu.tempo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_config.*


class Config : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        val myPreference = MyPreference(this)
        var selection = myPreference.getLoginCount()

        changeSel(selection)

        github.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/Omaredu/Tempo"))
            startActivity(browserIntent)
        }

        celsius.setOnClickListener {
            var gradesU = myPreference.getLoginCount()
            gradesU = 1
            myPreference.setLoginCount(gradesU)

            restartMain()
        }
        farenheit.setOnClickListener {
            var gradesU = myPreference.getLoginCount()
            gradesU = 0
            myPreference.setLoginCount(gradesU)

            restartMain()
        }

    }

    fun changeSel(sel : Int) {
        if (sel == 1){
            celsius.background = ContextCompat.getDrawable(this, R.drawable.button_scale_sel)
            celsius.setTextColor(getApplication().getResources().getColor(R.color.whiteSel))
            farenheit.background = ContextCompat.getDrawable(this,R.drawable.button_scale)
            farenheit.setTextColor(getApplication().getResources().getColor(R.color.blackSel))

        }
        else {
            celsius.background = ContextCompat.getDrawable(this, R.drawable.button_scale)
            celsius.setTextColor(getApplication().getResources().getColor(R.color.blackSel))
            farenheit.background = ContextCompat.getDrawable(this,R.drawable.button_scale_sel)
            farenheit.setTextColor(getApplication().getResources().getColor(R.color.whiteSel))
        }
    }

    fun restartMain() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("EXIT", true)
        startActivity(intent)

        val intent_res = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
