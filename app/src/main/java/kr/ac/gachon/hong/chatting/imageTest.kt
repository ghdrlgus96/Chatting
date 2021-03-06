package kr.ac.gachon.hong.chatting

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_image_test.*


class imageTest : AppCompatActivity() {
    var chatlist = ArrayList<String>()
    var namelist = ArrayList<String>()
    val url = "https://rlgus.run.goorm.io"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_test)

        test()

    }
    fun toasting(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    fun test() {
        //json.put("Content-Type", "application/json");
        var queue: RequestQueue = Volley.newRequestQueue(this);
        val request = object : ImageRequest(
            url + "/downloadFile/Anmi.jpg",
            Response.Listener<Bitmap> {
                    imageView.setImageBitmap(it)
            }, 0, 0, Bitmap.Config.ARGB_8888, Response.ErrorListener {
                    toasting(it.toString())
            }
        ) {
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
