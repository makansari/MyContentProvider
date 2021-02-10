package com.example.mycontentprovider

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED",Toast.LENGTH_SHORT).show()
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_CONTACTS),123)
        }

        buttongetData.setOnClickListener {

            var phonelist = ArrayList<String>()
            var cresolver = contentResolver
            var sb = StringBuilder()

            var cursor = cresolver.query(ContactsContract.Contacts.CONTENT_URI,null, null,null,null)

            if(cursor!!.count > 0) {

                while (cursor.moveToNext()){

                    var id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    var name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    sb.append("Name is : $name \n")

                    var phoneCursor = cresolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                            if(phoneCursor!!.count > 0){

                                while (phoneCursor.moveToNext()){
                                    var phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    sb.append("phone number is : $phoneNumber \n \n")
                                }

                                var result = sb.toString()
                                textViewData.setText(result)
                            }

                }

            }


        }
    }
}








