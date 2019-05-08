package kr.ac.gachon.hong.chatting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main_login.*

class MainLogin : AppCompatActivity() {
    val url = "https://rlgus.run.goorm.io"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        SignIn.setOnClickListener {
            test1(LoginID.text.toString() + " " + LoginPass.text.toString())
        }
        SignUp.setOnClickListener {
            var intent = Intent(this, MainSignup::class.java)
            startActivity(intent)
        }
    }

    fun test1(msg: String?) {
        var queue:RequestQueue = Volley.newRequestQueue(this);
        val request = StringRequest(Request.Method.GET, url + "/testN5/" + msg,
            Response.Listener {
                    response -> run {
                        if(response.toString() == "False")
                            Toast.makeText(this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                        else {
                            var temp = response.split(" ")
                            MainActivity.ID = temp[0]
                            MainActivity.USER = temp[1]
                            MainActivity.NAME = temp[2]
                            MainActivity.PASS = temp[3]
                            var intent = Intent(this, MainMenu::class.java)
                            startActivity(intent)
                        }
                    }
            }, null
        )
        queue.add(request)
    }
}
