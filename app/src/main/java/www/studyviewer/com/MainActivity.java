package www.studyviewer.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name,phone,emailaddress;
    Button save,view,backup;
    dbmanager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new dbmanager(this);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        emailaddress = findViewById(R.id.emailaddress);

        save = findViewById(R.id.save);
        view = findViewById(R.id.view);
        backup = findViewById(R.id.backup);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametxt = name.getText().toString();
                String phonetxt = phone.getText().toString();
                String emailtext = emailaddress.getText().toString();

                if(nametxt.equals("") | phonetxt.equals("") | emailtext.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Please enter details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean save = db.addData(nametxt,phonetxt,emailtext);

                    name.setText("");
                    phone.setText("");
                    emailaddress.setText("");

                    if(save){
                        Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this,"Sorry",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewData.class));
            }
        });

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Backup.class));
            }
        });

    }
}