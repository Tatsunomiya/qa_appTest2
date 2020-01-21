package tatsunomiya.com.qaaapp2

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
    private var QuestionID2 = ArrayList<String>()

    lateinit var listmap: Map<String, Map<String, String>>
    lateinit var listmap2: Map<String, String>
    lateinit var lst3: ArrayList<Question>
    private lateinit var mFavoriteRef: DatabaseReference



    private val mEventListener0 = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val questionId = dataSnapshot.key.toString()


            val map = dataSnapshot.value as Map<String, String>
            val title = map["title"] ?: ""
            val body = map["body"] ?: ""
            val name = map["name"] ?: ""
            val uid = map["uid"] ?: ""
            val imageString = map["image"] ?: ""
            val favoriteSwitch = map["favorite"] ?: ""

//            title2 = title
//            body2 = body
//            imageString2 = imageString
//            name2 = name
//            QuestionID2 = questionId
//            Genre2 = genre


            val bytes = if (imageString.isNotEmpty()) {
                Base64.decode(imageString, Base64.DEFAULT)


            } else {
                byteArrayOf()
            }

            val answerArrayList = ArrayList<Answer>()
            val answerMap = map["answers"] as Map<String, String>?
            if (answerMap != null) {
                for (key in answerMap.keys) {
                    val temp = answerMap[key] as Map<String, String>
                    val answerBody = temp["body"] ?: ""
                    val answerName = temp["name"] ?: ""
                    val answerUid = temp["uid"] ?: ""
                    val answer = Answer(answerBody, answerName, answerUid, key)

                    answerArrayList.add(answer)

                }
            }

            val question = Question(
                title,
                body,
                name,
                uid,
                dataSnapshot.key ?: "",
                mGenre,
                bytes,
                answerArrayList
            )

            mQuestionArrayList.add(question)
            mAdapter.notifyDataSetChanged()


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



    private val mEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val dataBaseReference = FirebaseDatabase.getInstance().reference
            val map = dataSnapshot.value as Map<String, String>
            val genre = map["genre"].toString()
            val questionId = dataSnapshot.key
            val mQuestionRef = dataBaseReference.child(ContentsPATH).child(genre).child(questionId.toString())

            mQuestionRef.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val map = snapshot.value as Map<String, String>
                    val title = map["title"] ?: ""
                    val body = map["body"] ?: ""
                    val name = map["name"] ?: ""
                    val uid = map["uid"] ?: ""
                    val imageString = map["image"] ?: ""
                    val bytes =
                        if (imageString.isNotEmpty()) {
                            Base64.decode(imageString, Base64.DEFAULT)
                        } else {
                            byteArrayOf()
                        }
                    val answerArrayList = ArrayList<Answer>()
                    val answerMap = map["answers"] as Map<String, String>?
                    if (answerMap != null) {
                        for (key in answerMap.keys) {
                            val temp = answerMap[key] as Map<String, String>
                            val answerBody = temp["body"] ?: ""
                            val answerName = temp["name"] ?: ""
                            val answerUid = temp["uid"] ?: ""
                            val answer = Answer(answerBody, answerName, answerUid, key)
                            answerArrayList.add(answer)
                        }
                    }
                    val question = Question(title, body, name, uid, snapshot.key?: "",
                        genre.toInt(), bytes, answerArrayList)
                    mQuestionArrayList.add(question)
                    mAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(firebaseError: DatabaseError) {}
            })
        }
        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
        }
        override fun onChildRemoved(p0: DataSnapshot) {
        }
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }
        override fun onCancelled(p0: DatabaseError) {
        }
    }


//    private val mEventListener = object : ChildEventListener {
//        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//            val questionId = dataSnapshot.key.toString()
//
//            Log.d("tag", questionId)
//
//
//            val map = dataSnapshot.value as Map<String, String>
//            val map2 = dataSnapshot.value as Map<String, Any>
//
////            val title = map["title"] ?: ""
////            val body = map["body"] ?: ""
////            val name = map["name"] ?: ""
////            val uid = map["uid"] ?: ""
////            val imageString = map["image"] ?: ""
////            val favoriteSwitch = map["favorite"] ?: ""
//            val genre = map2["genre"]
//
//
////            title2 = title
////            body2 = body
////            imageString2 = imageString
////            name2 = name
//            QuestionID2.add(questionId)
//
//
//
//
//            Genre2 = genre.toString().toInt()
//
//
////                answerArrayList
////            )
//// val bytes = if (imageString.isNotEmpty()) {
////                Base64.decode(imageString, Base64.DEFAULT)
////
////
////            } else {
////                byteArrayOf()
////            }
////
////            val answerArrayList = ArrayList<Answer>()
////            val answerMap = map["answers"] as Map<String, String>?
////            if (answerMap != null) {
////                for (key in answerMap.keys) {
////                    val temp = answerMap[key] as Map<String, String>
////                    val answerBody = temp["body"] ?: ""
////                    val answerName = temp["name"] ?: ""
////                    val answerUid = temp["uid"] ?: ""
////                    val answer = Answer(answerBody, answerName, answerUid, key)
////
////                    answerArrayList.add(answer)
////
////                }
////            }
////
////            val question = Question(
////                title,
////                body,
////                name,
////                uid,
////                dataSnapshot.key ?: "",
////                Genre2,
////                bytes,
////            mQuestionArrayList.add(question)
////            mAdapter.notifyDataSetChanged()
//
//
//        }
//
//        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
////            val map = dataSnapshot.value as Map<String, String>
////
////            for (question in mQuestionArrayList) {
////                if (dataSnapshot.key.equals(question.questionUid)) {
////
////                    question.answers.clear()
////                    val answerMap = map["answers"] as Map<String, String>?
////                    if (answerMap != null) {
////                        for (key in answerMap.keys) {
////                            val temp = answerMap[key] as Map<String, String>
////                            val answerBody = temp["body"] ?: ""
////                            val answerName = temp["name"] ?: ""
////                            val answerUid = temp["uid"] ?: ""
////                            val answer = Answer(answerBody, answerName, answerUid, key)
////                            question.answers.add(answer)
////                        }
////                    }
////                    mAdapter.notifyDataSetChanged()
////                }
////            }
//        }
//
//        override fun onChildRemoved(p0: DataSnapshot) {
//        }
//
//        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//        }
//
//        override fun onCancelled(p0: DatabaseError) {
//
//        }
//
//    }
//
//
//    private val mEventListener2 = object : ChildEventListener {
//        override fun onCancelled(p0: DatabaseError) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//        }
//
//        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//
//            val quesionId3 = p0.key.toString()
//
//
////            Log.d("debug", "key = " + p0.key.toString())
////
////            Log.d("debug", "value = " + p0.value.toString())
//
//
////            val answerArrayList = ArrayList<Answer>()
////            val answerMap = listmap["answers"] as Map<String, String>?
////            if (answerMap != null) {
////                for (key in answerMap.keys) {
////                    val temp = answerMap[key] as Map<String, String>
////                    val answerBody = temp["body"] ?: ""
////                    val answerName = temp["name"] ?: ""
////                    val answerUid = temp["uid"] ?: ""
////                    val answer = Answer(answerBody, answerName, answerUid, key)
////
////                    answerArrayList.add(answer)
//
//            listmap = p0.value as Map<String, Map<String, String>>
//
//
//            val lst = (QuestionID2)
////            val lst2 = listOf(p0.value)
////            val lst2 = map[p0.value]
//            lst3 = ArrayList<Question>()
//
//            for (i in lst) {
////                for (g in listmap) {
//
//
//                val favorite: Map<String, Map<String, String>>? =
//                    listmap[i] as Map<String, Map<String, String>>?
//
//
//
//                if (favorite != null) {
//
//
//                    var title = listmap[i]!!["title"]
//
//
//                    var body = listmap[i]!!["body"]
//
//
//                    var name = listmap[i]!!["name"]
//
//
//                    var uid = listmap[i]!!["uid"]
//
//
//                    var question = listmap[i]
//
//                    var imageString = listmap[i]!!["image"]
//
//
//                    val bytes = if (imageString!!.isNotEmpty()) {
//                        Base64.decode(imageString, Base64.DEFAULT)
//
//
//                    } else {
//                        byteArrayOf()
//                    }
//
//
//                    val answerArrayList = ArrayList<Answer>()
////            val answerMap = map["answers"] as Map<String, String>?
////            if (answerMap != null) {
////                for (key in answerMap.keys) {
////                    val temp = answerMap[key] as Map<String, String>
////                    val answerBody = temp["body"] ?: ""
////                    val answerName = temp["name"] ?: ""
////                    val answerUid = temp["uid"] ?: ""
////                    val answer = Answer(answerBody, answerName, answerUid, key)
////
////                    answerArrayList.add(answer)
//
//
//                    var q = Question(
//
//                        title!!,
//
//                        body!!,
//
//                        name!!,
//
//                        uid!!,
//
//                        QuestionID2.toString(),
//
//                        Genre2,
//
//                        bytes,
//
//                        answerArrayList
//
//
//                    )
//
//
//
//                    lst3.add(q)
//
//
////}
//                }
//
//
////
//
////
//            }
//
//
////            mAdapter.setQuestionArrayList(lst3)
//////            mAdapter.notifyDataSetChanged()
////            mAdapter.notifyDataSetInvalidated()
//
//            mQuestionArrayList.addAll(lst3)
//
//
//            mAdapter.notifyDataSetChanged()
//
//
//        }
//
//
//        override fun onChildRemoved(p0: DataSnapshot) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
//        mDatabaseReference = FirebaseDatabase.getInstance().reference

        val user = FirebaseAuth.getInstance().currentUser


        if (user == null) {
            val navigationDrawer: NavigationView = findViewById(R.id.nav_view);
            val tmpm: Menu = navigationDrawer.getMenu()
            tmpm.findItem(R.id.nav_favorite)
            tmpm.findItem(R.id.nav_favorite).setVisible(false)

        } else {
            val navigationDrawer: NavigationView = findViewById(R.id.nav_view);
            val tmpm: Menu = navigationDrawer.getMenu()
            tmpm.findItem(R.id.nav_favorite)
            tmpm.findItem(R.id.nav_favorite).setVisible(true)

        }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            // ジャンルを選択していない場合（mGenre == 0）はエラーを表示するだけ
            if (mGenre == 0) {
                Snackbar.make(view, "ジャンルを選択して下さい", Snackbar.LENGTH_LONG).show()
            } else {

            }
            // ログイン済みのユーザーを取得する
            val user = FirebaseAuth.getInstance().currentUser

            if (user == null) {
                // ログインしていなければログイン画面に遷移させる
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            } else {
                // ジャンルを渡して質問作成画面を起動する
                val intent = Intent(applicationContext, QuestionSendActivity::class.java)
                intent.putExtra("genre", mGenre)
                startActivity(intent)
            }
        }


//
//        val user = FirebaseAuth.getInstance().currentUser
//        if (user == null) {
//            setContentView(R.layout.activity_mainl)
//
//            mToolbar = findViewById(R.id.toolbar)
//            setSupportActionBar(mToolbar)
//
//
//                // ログインしていない場合は何もしない
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            mToolbar,
            R.string.app_name,
            R.string.app_name
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
//
//                val navigationView = findViewById<NavigationView>(R.id.nav_view)
//                navigationView.setNavigationItemSelectedListener(this)
//
//            val fab = findViewById<FloatingActionButton>(R.id.fab)
//            fab.setOnClickListener { view ->
//                // ジャンルを選択していない場合（mGenre == 0）はエラーを表示するだけ
//                if (mGenre == 0) {
//                    Snackbar.make(view, "ジャンルを選択して下さい", Snackbar.LENGTH_LONG).show()
//                } else {
//
//                }
//
//            }
//
//
//        } else {
//            // ナビゲーションドロワーの設定
//            setContentView(R.layout.activity_main)
//
//            val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//            val toggle = ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name)
//            drawer.addDrawerListener(toggle)
//            toggle.syncState()
//
//            mToolbar = findViewById(R.id.toolbar)
//            setSupportActionBar(mToolbar)
//
//            val fab = findViewById<FloatingActionButton>(R.id.fab)
//            fab.setOnClickListener { view ->
//                // ジャンルを選択していない場合（mGenre == 0）はエラーを表示するだけ
//                if (mGenre == 0) {
//                    Snackbar.make(view, "ジャンルを選択して下さい", Snackbar.LENGTH_LONG).show()
//                } else {
//
//                }
//
//
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
//            }
//        }


//         val mEventListener = object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                val questionId = dataSnapshot.key.toString()
//
//                Log.d("tag", questionId)
//
//
//                val map = dataSnapshot.value as Map<String, String>
//                val map2 = dataSnapshot.value as Map<String, Any>
//
////            val title = map["title"] ?: ""
////            val body = map["body"] ?: ""
////            val name = map["name"] ?: ""
////            val uid = map["uid"] ?: ""
////            val imageString = map["image"] ?: ""
////            val favoriteSwitch = map["favorite"] ?: ""
//                val genre = map2["genre"]
//
//
////            title2 = title
////            body2 = body
////            imageString2 = imageString
////            name2 = name
//                QuestionID2.add(questionId)
//
//
//
//
//                Genre2 = genre.toString().toInt()
//
//
////                answerArrayList
////            )
//// val bytes = if (imageString.isNotEmpty()) {
////                Base64.decode(imageString, Base64.DEFAULT)
////
////
////            } else {
////                byteArrayOf()
////            }
////
////            val answerArrayList = ArrayList<Answer>()
////            val answerMap = map["answers"] as Map<String, String>?
////            if (answerMap != null) {
////                for (key in answerMap.keys) {
////                    val temp = answerMap[key] as Map<String, String>
////                    val answerBody = temp["body"] ?: ""
////                    val answerName = temp["name"] ?: ""
////                    val answerUid = temp["uid"] ?: ""
////                    val answer = Answer(answerBody, answerName, answerUid, key)
////
////                    answerArrayList.add(answer)
////
////                }
////            }
////
////            val question = Question(
////                title,
////                body,
////                name,
////                uid,
////                dataSnapshot.key ?: "",
////                Genre2,
////                bytes,
////            mQuestionArrayList.add(question)
////            mAdapter.notifyDataSetChanged()
//
//
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
////            val map = dataSnapshot.value as Map<String, String>
////
////            for (question in mQuestionArrayList) {
////                if (dataSnapshot.key.equals(question.questionUid)) {
////
////                    question.answers.clear()
////                    val answerMap = map["answers"] as Map<String, String>?
////                    if (answerMap != null) {
////                        for (key in answerMap.keys) {
////                            val temp = answerMap[key] as Map<String, String>
////                            val answerBody = temp["body"] ?: ""
////                            val answerName = temp["name"] ?: ""
////                            val answerUid = temp["uid"] ?: ""
////                            val answer = Answer(answerBody, answerName, answerUid, key)
////                            question.answers.add(answer)
////                        }
////                    }
////                    mAdapter.notifyDataSetChanged()
////                }
////            }
//            }
//
//            override fun onChildRemoved(p0: DataSnapshot) {
//            }
//
//            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//        }
//
//
//         val mEventListener2 = object : ChildEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//            }
//
//            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//
//                val quesionId3 = p0.key.toString()
//
//
////            Log.d("debug", "key = " + p0.key.toString())
////
////            Log.d("debug", "value = " + p0.value.toString())
//
//
////            val answerArrayList = ArrayList<Answer>()
////            val answerMap = listmap["answers"] as Map<String, String>?
////            if (answerMap != null) {
////                for (key in answerMap.keys) {
////                    val temp = answerMap[key] as Map<String, String>
////                    val answerBody = temp["body"] ?: ""
////                    val answerName = temp["name"] ?: ""
////                    val answerUid = temp["uid"] ?: ""
////                    val answer = Answer(answerBody, answerName, answerUid, key)
////
////                    answerArrayList.add(answer)
//
//                listmap = p0.value as Map<String, Map<String, String>>
//
//
//                val lst = (QuestionID2)
////            val lst2 = listOf(p0.value)
////            val lst2 = map[p0.value]
//                lst3 = ArrayList<Question>()
//
//                for (i in lst) {
////                for (g in listmap) {
//
//
//                    val favorite: Map<String, Map<String, String>>? =
//                        listmap[i] as Map<String, Map<String, String>>?
//
//
//
//                    if (favorite != null) {
//
//
//                        var title = listmap[i]!!["title"]
//
//
//                        var body = listmap[i]!!["body"]
//
//
//                        var name = listmap[i]!!["name"]
//
//
//                        var uid = listmap[i]!!["uid"]
//
//
//                        var question = listmap[i]
//
//                        var imageString = listmap[i]!!["image"]
//
//
//                        val bytes = if (imageString!!.isNotEmpty()) {
//                            Base64.decode(imageString, Base64.DEFAULT)
//
//
//                        } else {
//                            byteArrayOf()
//                        }
//
//
//                        val answerArrayList = ArrayList<Answer>()
////            val answerMap = map["answers"] as Map<String, String>?
////            if (answerMap != null) {
////                for (key in answerMap.keys) {
////                    val temp = answerMap[key] as Map<String, String>
////                    val answerBody = temp["body"] ?: ""
////                    val answerName = temp["name"] ?: ""
////                    val answerUid = temp["uid"] ?: ""
////                    val answer = Answer(answerBody, answerName, answerUid, key)
////
////                    answerArrayList.add(answer)
//
//
//                        var q = Question(
//
//                            title!!,
//
//                            body!!,
//
//                            name!!,
//
//                            uid!!,
//
//                            QuestionID2.toString(),
//
//                            Genre2,
//
//                            bytes,
//
//                            answerArrayList
//
//
//                        )
//
//
//
//                        lst3.add(q)
//
//
////}
//                    }
//
//
////
//
////
//                }
//
//
////            mAdapter.setQuestionArrayList(lst3)
//////            mAdapter.notifyDataSetChanged()
////            mAdapter.notifyDataSetInvalidated()
//
//                mQuestionArrayList.addAll(lst3)
//
//
//                mAdapter.notifyDataSetChanged()
//
//
//            }
//
//
//            override fun onChildRemoved(p0: DataSnapshot) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        }

        // Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().reference
        val userRef = FirebaseAuth.getInstance().currentUser


        // ListViewの準備
        mListView = findViewById(R.id.listView)
        mAdapter = QuestionsListAdapter(this)
        mQuestionArrayList = ArrayList<Question>()



//        mAdapter.notifyDataSetChanged()

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

//        mGenreRef2 = mDatabaseReference.child("favorites").child(userRef?.uid.toString())
////            mGenreRef2 = mDatabaseReference.child("favorites").child(userRef.toString())
//        mGenreRef2!!.addChildEventListener(mEventListener)
////            mContents = mDatabaseReference.child("contents")
//        mContents = mDatabaseReference.child("contents")
//        mContents!!.addChildEventListener (mEventListener2)
//
//        mQuestionArrayList.clear()
//        mAdapter.setQuestionArrayList(mQuestionArrayList)
//        mListView.adapter = mAdapter

    }

    override fun onResume() {
        super.onResume()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)


        // 1:趣味を既定の選択とする
        if (mGenre == 0) {
            onNavigationItemSelected(navigationView.menu.getItem(0))
        }

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            val navigationDrawer: NavigationView = findViewById(R.id.nav_view);
            val tmpm: Menu = navigationDrawer.getMenu()
            tmpm.findItem(R.id.nav_favorite)
            tmpm.findItem(R.id.nav_favorite).setVisible(false)

        } else {
            val navigationDrawer: NavigationView = findViewById(R.id.nav_view);
            val tmpm: Menu = navigationDrawer.getMenu()
            tmpm.findItem(R.id.nav_favorite)
            tmpm.findItem(R.id.nav_favorite).setVisible(true)


        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_hobby) {
            mToolbar.title = "趣味"
            mGenre = 1
        } else if (id == R.id.nav_life) {
            mToolbar.title = "生活"
            mGenre = 2
        } else if (id == R.id.nav_health) {
            mToolbar.title = "健康"
            mGenre = 3
        } else if (id == R.id.nav_compter) {
            mToolbar.title = "コンピューター"
            mGenre = 4
        } else if (id == R.id.nav_favorite) {
            mToolbar.title = "お気に入り"


//                     mGenre= FirebaseAuth.getInstance().currentUser?.uid?.toInt()!!


        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        drawer.closeDrawer(GravityCompat.START)


        // --- ここから ---

        // 質問のリストをクリアしてから再度Adapterにセットし、AdapterをListViewにセットし直す

        mQuestionArrayList.clear()


        mAdapter.setQuestionArrayList(mQuestionArrayList)

        mListView.adapter = mAdapter


        if (id == R.id.nav_hobby || id == R.id.nav_life || id == R.id.nav_health || id == R.id.nav_compter) {
            // 選択したジャンルにリスナーを登録する
            if (mGenreRef != null) {

                mGenreRef!!.removeEventListener(mEventListener0)


            }

            mGenreRef = mDatabaseReference.child(ContentsPATH).child(mGenre.toString())

            mGenreRef!!.addChildEventListener(mEventListener0)


        } else {

            val id = FirebaseAuth.getInstance().currentUser!!.uid

            val dataBaseReference = FirebaseDatabase.getInstance().reference


            mFavoriteRef =
                dataBaseReference.child("favorites").child(id)
            mFavoriteRef.addChildEventListener(mEventListener)


//        val userRef = FirebaseAuth.getInstance().currentUser
//
//
//
//        if (mGenreRef2 != null) {
//
//
//            mGenreRef2!!.removeEventListener(mEventListener)
//
//
//        }
//
//
//
//
//      QuestionID2.clear()
//
//
//        mGenreRef2 = mDatabaseReference.child("favorites").child(userRef?.uid.toString())
//
//
////            mGenreRef2 = mDatabaseReference.child("favorites").child(userRef.toString())
//
//
//        mGenreRef2!!.addChildEventListener(mEventListener)
//
//
////            mContents = mDatabaseReference.child("contents")
//
//
//        mContents = mDatabaseReference.child("contents")
//
//
//
//
//
//
//
//
//
//
//
//        mContents!!.addChildEventListener(mEventListener2)
//
//
//
//
//    }

        }
        return true
    }
}

            // --- ここまで追加する ---



//            if (id == R.id.nav_favorite) {








//        }
//
//
//
//
//
//
//
//        return true
//
//
//
//    }







