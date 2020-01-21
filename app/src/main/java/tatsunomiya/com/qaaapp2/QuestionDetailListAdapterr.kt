package tatsunomiya.com.qaaapp2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


//class QuestionsListAdapter(context: Context) : BaseAdapter() {
////    private var mLayoutInflater: LayoutInflater
////    private var mQuestionArrayList = ArrayList<Question>()
////
////    init {
////        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
////    }
////
////    override fun getCount(): Int {
////        return mQuestionArrayList.size
////    }
////
////    override fun getItem(position: Int): Any {
////        return mQuestionArrayList[position]
////    }
////
////    override fun getItemId(position: Int): Long {
////        return position.toLong()
////    }
////
////    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
////        var convertView = convertView
////
////        if (convertView == null) {
////            convertView = mLayoutInflater.inflate(R.layout.list_questions, parent, false)
////        }
////
////        val titleText = convertView!!.findViewById<View>(R.id.titleTextView) as TextView
////        titleText.text = mQuestionArrayList[position].title
////
////        val nameText = convertView.findViewById<View>(R.id.nameTextView) as TextView
////        nameText.text = mQuestionArrayList[position].name
////
////        val resText = convertView.findViewById<View>(R.id.resTextView) as TextView
////        val resNum = mQuestionArrayList[position].answers.size
////        resText.text = resNum.toString()
////
////        val bytes = mQuestionArrayList[position].imageBytes
////        if (bytes.isNotEmpty()) {
////            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).copy(Bitmap.Config.ARGB_8888, true)
////            val imageView = convertView.findViewById<View>(R.id.imageView) as ImageView
////            imageView.setImageBitmap(image)
////        }
////
////        return convertView
////    }
////
////    fun setQuestionArrayList(questionArrayList: ArrayList<Question>) {
////        mQuestionArrayList = questionArrayList
////    }
////}

class QuestionDetailListAdapter(context: Context, private val  mQustion: Question) : BaseAdapter() {


//    private lateinit  var mDataBaseReference: DatabaseReference
    private var mGenre: Int = 1

    var favoriteSwitch : String = "0"

    companion object {
        private val TYPE_QUESTION = 0
        private val TYPE_ANSWER = 1
    }

    private var mLayoutInflater: LayoutInflater? = null

    init {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return 1 + mQustion.answers.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_QUESTION
        } else {
            TYPE_ANSWER
        }
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Any {
        return mQustion
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        if (getItemViewType(position) == TYPE_QUESTION) {
            if (convertView == null) {
                convertView = mLayoutInflater!!.inflate(R.layout.list_question_detail, parent, false)!!
            }
            val body = mQustion.body
            val name = mQustion.name

            val bodyTextView = convertView.findViewById<View>(R.id.bodyTextView) as TextView
            bodyTextView.text = body

            val nameTextView = convertView.findViewById<View>(R.id.nameTextView) as TextView
            nameTextView.text = name

            val bytes = mQustion.imageBytes
            if (bytes.isNotEmpty()) {
                val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).copy(Bitmap.Config.ARGB_8888, true)
                val imageView = convertView.findViewById<View>(R.id.imageView) as ImageView
                imageView.setImageBitmap(image)
            }


//        val favoriteButton = convertView.findViewById<View>(R.id.button1) as ImageButton
//        favoriteButton.setImageResource(R.drawable.favorite1)
//
//        favoriteButton.setOnClickListener { v ->
//            if (favoriteSwitch == "0") {
//                favoriteSwitch = "1"
//
//                favoriteButton.setImageResource(R.drawable.favorite2)
//                FirebaseDatabase.getInstance().reference
//                val user = FirebaseAuth.getInstance().currentUser
//
//                val dataBaseReference = FirebaseDatabase.getInstance().reference
////                val genreRef = dataBaseReference.child(ContentsPATH).child(mGenre.toString()).child()
//
//                if (user == null) {
//                    // ログインしていない場合は何もしない
//                    Snackbar.make(v, "ログインしていません", Snackbar.LENGTH_LONG).show()
//
//                }else{
//                    // 変更した表示名をFirebaseに保存する
////                    val userRef = mDataBaseReference.child(UsersPATH).child(user!!.uid)
//                    val data = HashMap<String, String>()
//                    data["favorite"] = favoriteSwitch
////                    genreRef.push().setValue(data)
//
//                }
//
//            } else {
//                favoriteButton.setImageResource(R.drawable.favorite1)
//                favoriteSwitch = "0"
//
//                FirebaseDatabase.getInstance().reference
//
//                FirebaseDatabase.getInstance().reference
//                val user = FirebaseAuth.getInstance().currentUser
//
//                val dataBaseReference = FirebaseDatabase.getInstance().reference
//                val genreRef = dataBaseReference.child(ContentsPATH).child(mGenre.toString())
//
//                if (user == null) {
//                    // ログインしていない場合は何もしない
//                    Snackbar.make(v, "ログインしていません", Snackbar.LENGTH_LONG).show()
//                } else {
//                    // 変更した表示名をFirebaseに保存する
////                    val userRef = mDataBaseReference.child(UsersPATH).child(user!!.uid)
//                    val data = HashMap<String, String>()
//                    data["favorite"] = favoriteSwitch
//                    genreRef.push().setValue(data)
//
//
//                }

//            }
//        }

        } else {
            if (convertView == null) {
                convertView = mLayoutInflater!!.inflate(R.layout.list_answer, parent, false)!!
            }

            val answer = mQustion.answers[position - 1]
            val body = answer.body
            val name = answer.name

            val bodyTextView = convertView.findViewById<View>(R.id.bodyTextView) as TextView
            bodyTextView.text = body

            val nameTextView = convertView.findViewById<View>(R.id.nameTextView) as TextView
            nameTextView.text = name
        }

        return convertView
    }



}
