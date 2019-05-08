package kr.ac.gachon.hong.chatting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_makeroom.*

class MakeroomActivity : AppCompatActivity() {
    val url = "https://rlgus.run.goorm.io"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makeroom)

        makeRoom.setOnClickListener {
            test1(roomName.text.toString() + " " + myTeam.text.toString() +  " " + MainActivity.ID)
        }
    }

    fun test1(msg: String?) {
        var queue:RequestQueue = Volley.newRequestQueue(this);
        val request = StringRequest(Request.Method.GET, url + "/testC/" + msg,
            Response.Listener {
                //response -> Log.d("tag", response)
                    response -> run {
                        if(response == "ok")
                            Toast.makeText(this, "채팅방 생성 완료", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "채팅방 생성 실패", Toast.LENGTH_SHORT).show()
                    }
            }, null
        )
        queue.add(request)
    }
}
