package kr.ac.gachon.hong.chatting

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_get_image.*

class getImage : AppCompatActivity() {

    //var imageView: ImageView? = null
    //var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_image)

        testButton.setOnClickListener {
            var intent = Intent()
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    override fun onActivityResult(requestCode : Int, resultCode : Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    var temp = getContentResolver().openInputStream(data?.getData());
                    var img = BitmapFactory.decodeStream(temp);
                    temp.close();
                    // 이미지 표시
                    testImageView.setImageBitmap(img);
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
        }
    }
}