package kr.ac.gachon.hong.chatting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        //test
        UserName.append(MainActivity.NAME)

        Make.setOnClickListener {
            var intent = Intent(this, MakeroomActivity::class.java)
            startActivity(intent)
        }
        enter.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        exit.setOnClickListener {
            finish()
        }
        myRoom.setOnClickListener {
            var intent = Intent(this, MyRoomActivity::class.java)
            startActivity(intent)
        }
    }
}
