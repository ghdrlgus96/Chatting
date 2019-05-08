package kr.ac.gachon.hong.chatting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main_signup.*

class MainSignup : AppCompatActivity() {
    val url = "https://rlgus.run.goorm.io"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_signup)

        OK.setOnClickListener {
            test1(MainActivity.ID + " " + SignUpId.text + " " + SignUpName.text + " " + SignUpPass.text)
        }
    }
    fun test1(msg: String?) {
        var queue: RequestQueue = Volley.newRequestQueue(this);
        val request = StringRequest(
            Request.Method.GET, url + "/testU/" + msg,
                Response.Listener {
                    response -> run {
                        if(response.toString() == "fail")
                            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
                }
            }, null
        )
        queue.add(request)
    }
}
