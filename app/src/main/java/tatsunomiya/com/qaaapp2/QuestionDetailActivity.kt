package tatsunomiya.com.qaaapp2

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_question_detail.*


class QuestionDetailActivity: AppCompatActivity() {
    var favoriteSwitch: String = "0"

    var switcher: String = ""

    private var favoriteGenre: String = "5"

    private var mGenre = 0

    private var mGenreRef2: DatabaseReference? = null
    private lateinit var mDatabaseReference: DatabaseReference


    private lateinit var mQuestion: Question
    private lateinit var mAdapter: QuestionDetailListAdapter
    private lateinit var mAnswerRef: DatabaseReference
    private lateinit var mQuestionArrayList: ArrayList<Question>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)


        val mEventListener = object : ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                if(p0.key == "favorite") {
//
//
//                    if (p0.value == "0") {
//                        button1.setImageResource(R.drawable.favorite1)
//                    } else {
//                        button1.setImageResource(R.drawable.favorite2)
//                    }
//
//                }

//                val map = p0.value as Map<String, String>
//                val title = map["title"] ?: ""
//                val body = map["body"] ?: ""
//                val name = map["name"] ?: ""
//                val uid = map["uid"] ?: ""
//                val imageString = map["image"] ?: ""
//                val favoriteSwitcher = map["favorite"] ?: ""
//
//                val bytes = if (imageString.isNotEmpty()) {
//                    Base64.decode(imageString, Base64.DEFAULT)
//
//
//                } else {
//                    byteArrayOf()
//                }
//
//                val answerArrayList = ArrayList<Answer>()
//                val answerMap = map["answers"] as Map<String, String>?
//                if (answerMap != null) {
//                    for (key in answerMap.keys) {
//                        val temp = answerMap[key] as Map<String, String>
//                        val answerBody = temp["body"] ?: ""
//                        val answerName = temp["name"] ?: ""
//                        val answerUid = temp["uid"] ?: ""
//                        val answer = Answer(answerBody, answerName, answerUid, key)
//
//                        answerArrayList.add(answer)
//
//                    }
//                }
//
//                val question = Question(
//                    title,
//                    body,
//                    name,
//                    uid,
//                    p0.key ?: "",
//                    mGenre,
//                    bytes,
//                    answerArrayList
//                )
//
//
//                if (favoriteSwitcher == "1") {
//                    button1.setImageResource(R.drawable.favorite1)
//                } else {
//                    button1.setImageResource(R.drawable.favorite2)
//                }
//
            }


            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {


                if(dataSnapshot.key == "favorite") {


                    if (dataSnapshot.value == "0") {
                        button1.setImageResource(R.drawable.favorite1)
                    } else {
                        button1.setImageResource(R.drawable.favorite2)
                    }

                }
//                val map = dataSnapshot.value as Map<String, String>
//                val title = map["title"] ?: ""
//                val body = map["body"] ?: ""
//                val name = map["name"] ?: ""
//                val uid = map["uid"] ?: ""
//                val imageString = map["image"] ?: ""
//                val favoriteSwitcher = map["favorite"] ?: ""
//
//
//
//                val bytes = if (imageString.isNotEmpty()) {
//                    Base64.decode(imageString, Base64.DEFAULT)
//
//
//                } else {
//                    byteArrayOf()
//                }
//
//                val answerArrayList = ArrayList<Answer>()
//                val answerMap = map["answers"] as Map<String, String>?
//                if (answerMap != null) {
//                    for (key in answerMap.keys) {
//                        val temp = answerMap[key] as Map<String, String>
//                        val answerBody = temp["body"] ?: ""
//                        val answerName = temp["name"] ?: ""
//                        val answerUid = temp["uid"] ?: ""
//                        val answer = Answer(answerBody, answerName, answerUid, key)
//
//                        answerArrayList.add(answer)
//
//                    }
//                }
//
//                val question = Question(
//                    title,
//                    body,
//                    name,
//                    uid,
//                    dataSnapshot.key ?: "",
//                    mGenre,
//                    bytes,
//                    answerArrayList
//                )
//
//
//            }
//
            }

        }


        val extras = intent.extras
        mQuestion = extras.get("question") as Question


        val user = FirebaseAuth.getInstance().currentUser
//
        val dataBaseReference2 = FirebaseDatabase.getInstance().reference
        val genreRef = dataBaseReference2.child(ContentsPATH).child(
            mQuestion.genre
                .toString()
        ).child(mQuestion.questionUid)
//
        val switcher =     genreRef.child("favorite")

        genreRef.addChildEventListener(mEventListener)


        title = mQuestion.title

        mAdapter = QuestionDetailListAdapter(this, mQuestion)
        listView.adapter = mAdapter
        mAdapter.notifyDataSetChanged()

//         if(favoriteSwitcher== "1") {
//             button1.setImageResource(R.drawable.favorite2)
//
//
//         } else if(mQuestion.favoriteSwitcher == "0") {
//             button1.setImageResource(R.drawable.favorite1)
//
//
//        val dataBaseReference = FirebaseDatabase.getInstance().reference


        fab.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, AnswerSendActivity::class.java)
                intent.putExtra("question", mQuestion)
                startActivity(intent)
            }


        }


//        val dataBaseReference = FirebaseDatabase.getInstance().reference
//        mAnswerRef = dataBaseReference.child(ContentsPATH).child(mQuestion.genre.toString()).child(AnswersPATH)
//        mAnswerRef.addChildEventListener(mEventListener)


        button1.setOnClickListener() {

            if (favoriteSwitch == "0") {
                favoriteSwitch = "1"




                button1.setImageResource(R.drawable.favorite2)
                FirebaseDatabase.getInstance().reference
                val user = FirebaseAuth.getInstance().currentUser

                val dataBaseReference = FirebaseDatabase.getInstance().reference
                val genreRef = dataBaseReference.child(ContentsPATH).child(
                    mQuestion.genre
                        .toString()
                ).child(mQuestion.questionUid)

                val userRef = FirebaseAuth.getInstance().currentUser

//            var userReff = dataBaseReference.child("users").child(userRef?.uid.toString())
//                .child(mQuestion.questionUid)

                var userReff = dataBaseReference.child("favorites").child(userRef?.uid.toString())
                    .child(mQuestion.questionUid)


                val favoriteRef = dataBaseReference.child(ContentsPATH).child(favoriteGenre)
                    .child(mQuestion.questionUid)

                if (user == null) {
                    // ログインしていない場合は何もしない

                } else {
                    // 変更した表示名をFirebaseに保存する
//                    val userRef = mDataBaseReference.child(UsersPATH).child(user!!.uid)
                    val data = HashMap<String, Any>()
                    val data2 = HashMap<String, Any>()
                    val bitmapString = Base64.encodeToString(mQuestion.imageBytes, Base64.DEFAULT)
//                    data["favorite"] = favoriteSwitch

//
                    data2["favorite"] = favoriteSwitch
//                    data2["title"] = mQuestion.title
//                    data2["body"] = mQuestion.body
//                    data2["name"] = mQuestion.name
//                    data2["image"] = bitmapString
                    data2["uid"] = mQuestion.uid
                    data2["genre"] = mQuestion.genre
//                    genreRef.updateChildren(data)
//                favoriteRef.updateChildren(data2)
//                favoriteRef.updateChildren(data2)
                    userReff.setValue(data2)

//                val userRef =  FirebaseAuth.getInstance().currentUser
//
//
//                if (mGenreRef2 != null) {
//                    mGenreRef2!!.removeEventListener(mEventListener)
//                }
////                mDatabaseReference = FirebaseDatabase.getInstance().reference
//
//                mGenreRef2 = mDatabaseReference.child("users").child(userRef?.uid.toString())
//                mGenreRef2!!.addChildEventListener(mEventListener)


                }

            } else {
                button1.setImageResource(R.drawable.favorite1)
                favoriteSwitch = "0"

                FirebaseDatabase.getInstance().reference

                val user = FirebaseAuth.getInstance().currentUser

                val dataBaseReference = FirebaseDatabase.getInstance().reference
                val genreRef = dataBaseReference.child(ContentsPATH).child(
                    mQuestion.genre
                        .toString()
                ).child(mQuestion.questionUid)
                val favoriteRef = dataBaseReference.child(ContentsPATH).child(favoriteGenre)
                    .child(mQuestion.questionUid)
                val userRef = FirebaseAuth.getInstance().currentUser


//                var userReff = dataBaseReference.child("users").child(userRef?.uid.toString())
//                    .child(mQuestion.questionUid)
                var userReff = dataBaseReference.child("favorites").child(userRef?.uid.toString())
                    .child(mQuestion.questionUid)









                if (user == null) {
                    // ログインしていない場合は何もしない
                } else {
                    // 変更した表示名をFirebaseに保存する
//                    val userRef = mDataBaseReference.child(UsersPATH).child(user!!.uid)
                    val data = HashMap<String, Any>()
                    data["favorite"] = favoriteSwitch
                    genreRef.updateChildren(data)
                    favoriteRef.removeValue()
                    userReff.removeValue()


                }
            }

        }

    }

}


























