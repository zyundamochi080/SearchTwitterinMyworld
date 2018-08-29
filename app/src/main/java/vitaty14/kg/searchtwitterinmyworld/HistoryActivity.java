package vitaty14.kg.searchtwitterinmyworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryActivity extends AppCompatActivity {

    AlertDialog AlertDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView textView_disp_1 = findViewById(R.id.textView_disp_1);
        TextView textView_disp_2 = findViewById(R.id.textView_disp_2);
        TextView textView_disp_3 = findViewById(R.id.textView_disp_3);
        TextView textView_disp_4 = findViewById(R.id.textView_disp_4);
        TextView textView_disp_5 = findViewById(R.id.textView_disp_5);

        Button button_r = findViewById(R.id.button_return);
        Button button_d = findViewById(R.id.button_delete);

        SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);

        String text_1 = sp.getString("SaveString_1", "History not Found");
        textView_disp_1.setText(text_1);

        String text_2 = sp.getString("SaveString_2", "History not Found");
        textView_disp_2.setText(text_2);

        String text_3 = sp.getString("SaveString_3", "History not Found");
        textView_disp_3.setText(text_3);

        String text_4 = sp.getString("SaveString_4", "History not Found");
        textView_disp_4.setText(text_4);

        String text_5 = sp.getString("SaveString_5", "History not Found");
        textView_disp_5.setText(text_5);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dlg_title);
        builder.setMessage(R.string.dlg_msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HistoryActivity.this,"select OK",Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("sp",Context.MODE_PRIVATE);
                sp.edit().clear().commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HistoryActivity.this,"select Cancel",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDlg = builder.create();

        button_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
            }
        });

        button_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDlg.show();
            }
        });
    }
}
