package kr.ac.gachon.hong.chatting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class ChattingAdapter(val context: Context, val nameList: ArrayList<String>, val content: ArrayList<String>, val chattime: ArrayList<String>, val id: String): BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount() = content.size

    override fun getItem(position: Int) = content[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        lateinit var view: View
        if (nameList[position] == id) {
            view = mInflater.inflate(R.layout.mychatlog, parent, false)
            view.findViewById<TextView>(R.id.ChatLog1).text = content[position]
            view.findViewById<TextView>(R.id.chatDate1).text = chattime[position]
        } else {
            view = mInflater.inflate(R.layout.otherchatlog, parent, false)
            view.findViewById<TextView>(R.id.ID1).text = nameList[position]
            view.findViewById<TextView>(R.id.ChatLog2).text = content[position]
            view.findViewById<TextView>(R.id.ChatDate2).text = chattime[position]
        }
        return view
    }

    fun addItem(item1:String, item2:String, item3:String) {
        nameList.add(item1)
        content.add(item2)
        chattime.add(item3)

    }
}
