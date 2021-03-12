package com.example.room_demo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.room_demo.data.Student
import com.example.room_demo.data.StudentDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd:Button = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener() {
            val newRec : Student = Student(0, "John", "RIT")

            CoroutineScope(IO).launch {
                val studentDao = StudentDB.getDatabase(application).studentDao()
                studentDao.addStudent(newRec)
            }
        }

        val btnGet : Button = findViewById(R.id.btnGetAll)
        btnGet.setOnClickListener() {
            var name:String = ""

            CoroutineScope(Main).launch {
                val studentDao = StudentDB.getDatabase(application).studentDao()
                val studentList : Array<Student> = studentDao.getAllStudent()

                if(studentList != null) {
                    for(s : Student in studentList) {
                        name += s.name + "\n"
                    }
                }

                val tvData : TextView = findViewById(R.id.tvData)
                tvData.text = name
            }
        }
    }
}