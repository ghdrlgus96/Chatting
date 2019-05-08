package kr.ac.gachon.hong.chatting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_chatting_actovity.*
import org.json.JSONObject
import java.util.*



class ChattingActovity : AppCompatActivity() {
    val url = "https://rlgus.run.goorm.io"
    var namelist = ArrayList<String>()
    var contentlist = ArrayList<String>()
    var chattime = ArrayList<String>()
    var roomNo: String? = null

    var mTask: TimerTask? = null
    var mTimer: Timer? = null

    var chatsize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_actovity)

        var rN = intent.getStringExtra("roomNum")

        roomNo = rN

        var Adapter = ChattingAdapter(this, namelist, contentlist, chattime, MainActivity.ID)

        var queue: RequestQueue = Volley.newRequestQueue(this);
        val request = StringRequest(Request.Method.GET, url + "/chatlist/" + rN,
            Response.Listener {
                response ->
                    run {
                        var result = JSONObject(response)
                        var temp = result.getJSONArray("names")
                        var temp2 = result.getJSONArray("chatLog")
                        var temp3 = result.getJSONArray("chattime")
                        for (i in 0..temp.length() - 1) {
                            namelist.add(temp.getString(i))
                            contentlist.add(temp2.getString(i))
                            chattime.add(temp3.getString(i))
                        }
                        chatsize = temp.length() - 1

                        chatLog.adapter = Adapter
                        Adapter.notifyDataSetChanged()

                    }
                }, Response.ErrorListener {
                        error ->  Log.d("asdf1", error.toString())
            }
        )
        queue.add(request)

        SendBtn.setOnClickListener {
            test(editText.text.toString(), MainActivity.USER)
            editText.setText("")
        }

        mTask = object : TimerTask() {
            override fun run() {
                Log.d("qwer", "testst")
                var queue: RequestQueue = Volley.newRequestQueue(this@ChattingActovity);
                val request = StringRequest(Request.Method.GET, url + "/chatlist/" + rN,
                    Response.Listener {
                            response ->
                        run {
                            Log.d("qwer", "testststst")
                            var result = JSONObject(response)
                            var temp = result.getJSONArray("names")
                            var temp2 = result.getJSONArray("chatLog")
                            var temp3 = result.getJSONArray("chattime")

                            if(chatsize != temp.length() - 1) {
                                namelist.add(temp.getString(temp.length() - 1))
                                contentlist.add(temp2.getString(temp.length() - 1))
                                chattime.add(temp3.getString(temp.length() - 1))

                                chatsize = temp.length() - 1
                                Adapter.notifyDataSetChanged()
                            }
                        }
                    }, Response.ErrorListener {
                            error ->  Log.d("asdf1", error.toString())
                    }
                )
                queue.add(request)

                chatLog.setOnItemClickListener { parent, view, position, id ->
                    var temp = contentlist.get(position)
                    var temps = temp.split(" ")
                    //Toast.makeText(this@ChattingActovity, temps[i].size.toString(), Toast.LENGTH_SHORT).show()

                    //링크 구현 ver1.0
                    for(i in 0..temps.size - 1) {
                        //Toast.makeText(this@ChattingActovity, temps.size.toString(), Toast.LENGTH_SHORT).show()
                        if(temps[i].length > 4 && (temps[i][0].toString() + temps[i][1] + temps[i][2] + temps[i][3]) == "www.") {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://" + temps[i]))
                            if (intent.resolveActivity(packageManager) != null)
                                startActivity(intent)
                            break
                        }
                        else if(temps[i].length > 7 && (temps[i][0].toString() + temps[i][1] + temps[i][2] + temps[i][3] + temps[i][4] + temps[i][5] + temps[i][6]) == "http://") {
                            var myurl = temps[i].substring(7)
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://" + myurl))
                            if (intent.resolveActivity(packageManager) != null)
                                startActivity(intent)
                            break
                        }
                        else if( temps[i].length > 8 && (temps[i][0].toString() + temps[i][1] + temps[i][2] + temps[i][3] + temps[i][4] + temps[i][5] + temps[i][6] + temps[i][7]) == "https://") {
                            var myurl = temps[i].substring(8)
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://" + myurl))
                            if (intent.resolveActivity(packageManager) != null)
                                startActivity(intent)
                            break
                        }
                        else {
                            //break
                        }

                    }

                }
            }
        }
        mTimer = Timer()

        mTimer?.schedule(mTask, 1000, 1000)


    }

    fun toasting(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun test(msg: String?, id: String?) {
        var json = JSONObject()
        json.put("content", msg)
        json.put("name", id)
        json.put("roomno", roomNo)
        //json.put("Content-Type", "application/json");
        var queue:RequestQueue = Volley.newRequestQueue(this);
        val request = object : JsonObjectRequest(Request.Method.POST, "${url}/sendmsg", json,
            Response.Listener {
                    response -> run {

                var temp = response.getString("result")
                if(temp == "ok"){
                    toasting("ok")
                }
                else {
                    toasting("fail")
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

    override fun onDestroy() {
        super.onDestroy()
        mTimer?.cancel()
    }
}
