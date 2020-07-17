package com.web.contact_studentname_studentid_android

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.web.contact_studentname_studentid_android.Utiles.SQLHelperClass
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val VALID="Enter valid "
    var sqlHelperClass: SQLHelperClass? = null
    lateinit var database: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        submit.setOnClickListener({
            submitData()
        })
        record.setOnClickListener({
            startActivity(Intent(this,Records::class.java))
        })
    }

    private fun submitData() {
        if(validation()){

            //Sql
            sqlHelperClass = SQLHelperClass(this)
            database = sqlHelperClass!!.getWritableDatabase()
            try {
                var values = ContentValues()
                values.put("studentFirstName ",""+ fName.text)
                values.put("studentLastName ",""+ lName.text)
                values.put("studentEmail ",""+ email.text)
                values.put("studentPhone ",""+ phoneNum.text)
                values.put("studentAddress ",""+ addess.text)
                database.insert("DETAILS", null, values)
                Log.e("TAG", "submitClick: $values")
                Toast.makeText(this, "Record Saved!!", Toast.LENGTH_LONG).show();
                fName.setText("")
                lName.setText("")
                email.setText("")
                phoneNum.setText("")
                addess.setText("")

            }catch (e: Exception){
                e.stackTrace
            }
        }
    }

    private fun validation(): Boolean {
        if(fName.length() > 2){
            if(lName.length() > 2){
                if(email.length()>5){
                    if(phoneNum.length() > 8){
                        if(addess.length() > 10){
                            return true
                        }else{
                            addess.setError(VALID+" Address")
                            return false
                        }
                    }else{
                        phoneNum.setError(VALID+"Contact Number")
                        return false
                    }
                }else{
                    email.setError(VALID+"Email")
                    return false
                }           
            }else{
                lName.setError(VALID+"Last name")
                return false
            }
        }else{
            fName.setError(VALID+"First name")
            return false
        }
    }
}