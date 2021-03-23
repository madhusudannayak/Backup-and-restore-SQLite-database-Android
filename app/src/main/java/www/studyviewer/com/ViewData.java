package www.studyviewer.com;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    ListView list;
    dbmanager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        ListView list = findViewById(R.id.list);
        db =new dbmanager(this);

        ArrayList<Model> number = new ArrayList<Model>();
        Cursor data = db.getData();

        if(data.getCount() ==0){
            Toast.makeText(ViewData.this,"No data found",Toast.LENGTH_SHORT).show();

        }else {
            while (data.moveToNext()) {

                int id = data.getInt(0);
                String name = data.getString(1);
                String phone = data.getString(2);
                String email = data.getString(3);


                number.add(new Model(id,name,phone,email));
                CustomAdapter numberAdapter = new CustomAdapter(this,number);
                list.setAdapter(numberAdapter);


            }
        }


    }
}