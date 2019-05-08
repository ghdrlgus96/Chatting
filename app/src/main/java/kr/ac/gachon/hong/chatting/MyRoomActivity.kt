package kr.ac.gachon.hong.chatting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_my_room.*
import org.json.JSONObject

class MyRoomActivity : AppCompatActivity() {

    var chatlist1 = ArrayList<String>()
    var namelist1 = ArrayList<String>()
    val url1 = "https://rlgus.run.goorm.io"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_room)

        test(MainActivity.ID)

        myRoomList.setOnItemClickListener { parent, view, position, id ->
            var temp = chatlist1.get(position)
            test2(temp, MainActivity.USER)
        }
    }
    fun toasting(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    fun test(msg: String?) {
        var queue: RequestQueue = Volley.newRequestQueue(this);
        val request = StringRequest(Request.Method.GET, url1 + "/mylist/" + msg,
            Response.Listener {
                    response -> run {
                        Log.d("asdd", response)
                        var result = JSONObject(response)
                        var temp = result.getJSONArray("list")
                        var temp2 = result.getJSONArray("names")
                        for (i in 0..temp.length() - 1) {
                            chatlist1.add(temp.getString(i))
                            namelist1.add(temp2.getString(i))
                        }
                        var tempAdpater = ArrayAdapter(this, android.R.layout.simple_list_item_1, namelist1)
                        myRoomList.adapter = tempAdpater
                        tempAdpater.notifyDataSetChanged()
                    }
            }, Response.ErrorListener {
                    error ->  Log.d("asdf1", error.toString())
            }

        )
        queue.add(request)
    }
    fun test2(msg: String?, id: String?) {
        var json = JSONObject()
        json.put("room_num",msg)
        json.put("user", id)
        //json.put("Content-Type", "application/json");
        var queue: RequestQueue = Volley.newRequestQueue(this);
        val request = object : JsonObjectRequest(
            Request.Method.POST, "${url1}/mycon", json,
            Response.Listener {
                    response -> run {

                var temp = response.getString("result")
                if(temp == "ok"){
                    toasting("${msg} 방에 접속")
                    var intent = Intent(this, ChattingActovity::class.java)
                    intent.putExtra("roomNum", msg)
                    startActivity(intent)
                }
                else {
                    toasting("${msg} 방에 접속 실패")
                }

            }

            }, null)  {
            /** Passing some request headers*  */
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String>? {

                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers

            }
        }
        queue.add(request)
    }
}
