package www.studyviewer.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Backup extends AppCompatActivity {

    private dbmanager databaseHelper;

    private static  final  int STORAGE_REQUEST_CODE_EXPORT = 1;
    private static  final  int STORAGE_REQUEST_CODE_IMPORT = 2;

    private String[] storagePermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        databaseHelper = new dbmanager(this);

        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ImageButton backup = findViewById(R.id.backup);
        ImageButton restore = findViewById(R.id.restore);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkStoragePermission()){
                    exportCSV();

                }else {
                    requestStoragePermissionExport();

                }

            }
        });

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkStoragePermission()){
                    databaseHelper.delete();
                    importCSV();
                    onResume();

                }else {
                    requestStoragePermissionImport();

                }
            }
        });
    }
    private boolean checkStoragePermission() {
        //check if storage permission is enabled or not and return tre. false
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermissionImport() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE_IMPORT);
    }
    private void requestStoragePermissionExport() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE_EXPORT);
    }

    private void exportCSV() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + "SQLIteBackup");

        boolean isFolderCreated = false;
        if (!folder.exists()) {
            isFolderCreated = folder.mkdir();
        }
        Log.d("CSC_TAG", "exportCSV: " + isFolderCreated);

        String csvFileNAme = "SQLite_Backup.csv";

        String filePathAndName = folder.toString() + "/" + csvFileNAme;

        //get records
        ArrayList<Model> arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList = databaseHelper.getAlllistData();

        try {
            FileWriter fw = new FileWriter(filePathAndName);
            for (int i = 0; i < arrayList.size(); i++) {
                fw.append("" + arrayList.get(i).getId());
                fw.append(",");
                fw.append("" + arrayList.get(i).getName());
                fw.append(",");
                fw.append("" + arrayList.get(i).getPhone());
                fw.append(",");
                fw.append("" + arrayList.get(i).getEmail());
                fw.append("\n");
            }
            fw.flush();
            ;
            fw.close();

            Toast.makeText(this, "Backup Export to: " + filePathAndName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    private void importCSV() {
        String filePathAndName = Environment.getExternalStorageDirectory()+"/SQLIteBackup/" + "SQLite_Backup.csv";
        File csvFile = new File(filePathAndName);

        if(csvFile.exists()){

            try{
                CSVReader csvReader =new CSVReader(new FileReader(csvFile.getAbsolutePath()));
                String [] nextLine;
                while ((nextLine = csvReader.readNext()) != null){
                    //use same order for import as used export e.g id is saved on o index
                    String ids = nextLine[0];
                    String name = nextLine[1];
                    String date = nextLine[2];
                    String phone = nextLine[3];


                    long timestamp =System.currentTimeMillis();
                    boolean id =databaseHelper.addData(
                            ""+name,
                            ""+date,
                            ""+phone


                    );
                }
                Toast.makeText(this," backup Restore",Toast.LENGTH_SHORT).show();

            }
            catch (Exception e){
                Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }

        }else {
            Toast.makeText(this,"No backup Found",Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        //handle permission result

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_REQUEST_CODE_EXPORT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    exportCSV();

                } else {
                    Toast.makeText(this, "Storage permission required", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE_IMPORT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  importCSV();

                } else {
                    Toast.makeText(this, "Storage permission required", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }


}

