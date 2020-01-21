package tatsunomiya.com.qaaapp2

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FMainActivity : AppCompatActivity() {

    private var mGenerX = 0
    private lateinit var mToolbar: Toolbar
    private var mGenre = 0
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mListView: ListView
    private lateinit var mQuestionArrayList: ArrayList<Question>
    private lateinit var mAdapter: QuestionsListAdapter
    private var mGenreRef: DatabaseReference? = null
    private var mGenreRef2: DatabaseReference? = null
    private var mContents: DatabaseReference? = null
    private var title2 = ""
    private var body2 = ""
    private var name2 = ""
    private var imageString2 = ""
    private var Genre2: Int = 0
    private   var QuestionID2 = ArrayList<String>()
    lateinit var listmap : Map<String,Map<String,String>>
    lateinit var listmap2: Map<String,String>
    lateinit var lst3: ArrayList<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fmain)


        mDatabaseReference = FirebaseDatabase.getInstance().reference


        val userRef = FirebaseAuth.getInstance().currentUser



                val mEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val questionId = dataSnapshot.key.toString()
                Log.d("tag",questionId)
                val map = dataSnapshot.value as Map<String, String>
                val map2 = dataSnapshot.value as Map<String, Any>
//            val title = map["title"] ?: ""
//            val body = map["body"] ?: ""
//            val name = map["name"] ?: ""
//            val uid = map["uid"] ?: ""
//            val imageString = map["image"] ?: ""
//            val favoriteSwitch = map["favorite"] ?: ""
                val  genre  = map2["genre"]
//            title2 = title
//            body2 = body
//            imageString2 = imageString
//            name2 = name
                QuestionID2.add(questionId)
                Genre2 = genre.toString().toInt()
//                answerArrayList
//            )
// val bytes = if (imageString.isNotEmpty()) {
//                Base64.decode(imageString, Base64.DEFAULT)
//
//
//            } else {
//                byteArrayOf()
//            }
//
//            val answerArrayList = ArrayList<Answer>()
//            val answerMap = map["answers"] as Map<String, String>?
//            if (answerMap != null) {
//                for (key in answerMap.keys) {
//                    val temp = answerMap[key] as Map<String, String>
//                    val answerBody = temp["body"] ?: ""
//                    val answerName = temp["name"] ?: ""
//                    val answerUid = temp["uid"] ?: ""
//                    val answer = Answer(answerBody, answerName, answerUid, key)
//
//                    answerArrayList.add(answer)
//
//                }
//            }
//
//            val question = Question(
//                title,
//                body,
//                name,
//                uid,
//                dataSnapshot.key ?: "",
//                Genre2,
//                bytes,
//            mQuestionArrayList.add(question)
//            mAdapter.notifyDataSetChanged()
            }
            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
//            val map = dataSnapshot.value as Map<String, String>
//
//            for (question in mQuestionArrayList) {
//                if (dataSnapshot.key.equals(question.questionUid)) {
//
//                    question.answers.clear()
//                    val answerMap = map["answers"] as Map<String, String>?
//                    if (answerMap != null) {
//                        for (key in answerMap.keys) {
//                            val temp = answerMap[key] as Map<String, String>
//                            val answerBody = temp["body"] ?: ""
//                            val answerName = temp["name"] ?: ""
//                            val answerUid = temp["uid"] ?: ""
//                            val answer = Answer(answerBody, answerName, answerUid, key)
//                            question.answers.add(answer)
//                        }
//                    }
//                    mAdapter.notifyDataSetChanged()
//                }
//            }
            }
            override fun onChildRemoved(p0: DataSnapshot) {
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        }





        val mEventListener2 = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val quesionId3 = p0.key.toString()
//            Log.d("debug", "key = " + p0.key.toString())
//
//            Log.d("debug", "value = " + p0.value.toString())
//            val answerArrayList = ArrayList<Answer>()
//            val answerMap = listmap["answers"] as Map<String, String>?
//            if (answerMap != null) {
//                for (key in answerMap.keys) {
//                    val temp = answerMap[key] as Map<String, String>
//                    val answerBody = temp["body"] ?: ""
//                    val answerName = temp["name"] ?: ""
//                    val answerUid = temp["uid"] ?: ""
//                    val answer = Answer(answerBody, answerName, answerUid, key)
//
//                    answerArrayList.add(answer)
                listmap = p0.value as Map<String, Map<String, String>>
                val lst = (QuestionID2)
//            val lst2 = listOf(p0.value)
//            val lst2 = map[p0.value]
                lst3 = ArrayList<Question>()
                for (i in lst) {
//                for (g in listmap) {
                    val favorite: Map<String, Map<String, String>>? =
                        listmap[i] as Map<String, Map<String, String>>?
                    if (favorite != null) {
                        var title = listmap[i]!!["title"]
                        var body = listmap[i]!!["body"]
                        var name = listmap[i]!!["name"]
                        var uid = listmap[i]!!["uid"]
                        var question = listmap[i]
                        var imageString = listmap[i]!!["image"]
                        val bytes = if (imageString!!.isNotEmpty()) {
                            Base64.decode(imageString, Base64.DEFAULT)
                        } else {
                            byteArrayOf()
                        }
                        val answerArrayList = ArrayList<Answer>()
//            val answerMap = map["answers"] as Map<String, String>?
//            if (answerMap != null) {
//                for (key in answerMap.keys) {
//                    val temp = answerMap[key] as Map<String, String>
//                    val answerBody = temp["body"] ?: ""
//                    val answerName = temp["name"] ?: ""
//                    val answerUid = temp["uid"] ?: ""
//                    val answer = Answer(answerBody, answerName, answerUid, key)
//
//                    answerArrayList.add(answer)
                        var q = Question(
                            title!!,
                            body!!,
                            name!!,
                            uid!!,
                            QuestionID2.toString(),
                            Genre2,
                            bytes,
                            answerArrayList
                        )
                        lst3.add(q)
//}
                    }
//
//
                }
//            mAdapter.setQuestionArrayList(lst3)
////            mAdapter.notifyDataSetChanged()
//            mAdapter.notifyDataSetInvalidated()
                mQuestionArrayList.addAll(lst3)


                mAdapter.notifyDataSetChanged()



            }
            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }


        if (mGenreRef2 != null) {
            mGenreRef2!!.removeEventListener(mEventListener)
        }




        mListView = findViewById(R.id.listView)
        mAdapter = QuestionsListAdapter(this)
        mQuestionArrayList = ArrayList<Question>()
        mListView.setOnItemClickListener { parent, view, position, id ->
            // Questionのインスタンスを渡して質問詳細画面を起動する
            val intent = Intent(applicationContext, QuestionDetailActivity::class.java)
            intent.putExtra("question", mQuestionArrayList[position])
            startActivity(intent)
        }
        mListView.setOnItemClickListener { parent, view, position, id ->
            // Questionのインスタンスを渡して質問詳細画面を起動する
            val intent = Intent(applicationContext, QuestionDetailActivity::class.java)
            intent.putExtra("question", mQuestionArrayList[position])
            startActivity(intent)
        }
        mGenreRef2 = mDatabaseReference.child("favorites").child(userRef?.uid.toString())
//            mGenreRef2 = mDatabaseReference.child("favorites").child(userRef.toString())
        mGenreRef2!!.addChildEventListener(mEventListener)
//            mContents = mDatabaseReference.child("contents")
        mContents = mDatabaseReference.child("contents")
        mContents!!.addChildEventListener (mEventListener2)


        mQuestionArrayList.clear()
        mAdapter.setQuestionArrayList(mQuestionArrayList)
        mListView.adapter = mAdapter
    }
}