package com.web.contact_studentname_studentid_android

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.web.contact_studentname_studentid_android.Utiles.SQLHelperClass
import kotlinx.android.synthetic.main.activity_main.*

class EditRecord : AppCompatActivity() {
      var id:String?="";
      val VALID="Enter valid "
    var sqlHelperClass: SQLHelperClass? = null
    lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleHeader.setText("Update Information")
        record.visibility= View.GONE
        val extras = intent.extras
        fName.setText(extras?.getString("fName"))
        lName.setText(extras?.getString("lName"))
        email.setText(extras?.getString("mail"))
        phoneNum.setText(extras?.getString("phone"))
        addess.setText(extras?.getString("address"))
        id=extras?.getString("id")
        Log.e("TAG",id)

        submit.setText("Update")

        submit.setOnClickListener({
            submitData()

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
                database.update("DETAILS", values, "_id="+id, null);
                Toast.makeText(this, "Record Saved!!", Toast.LENGTH_LONG).show();
                finish()

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