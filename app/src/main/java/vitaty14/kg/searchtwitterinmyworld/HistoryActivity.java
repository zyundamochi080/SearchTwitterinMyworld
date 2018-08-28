package vitaty14.kg.searchtwitterinmyworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView textView_disp = (TextView)findViewById(R.id.textView_disp);

        Button button_d = findViewById(R.id.button_delete);

        SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
        String text = sp.getString("SaveString",null);

        textView_disp.setText(text);

        button_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("sp",Context.MODE_PRIVATE);
                sp.edit().clear().commit();
            }
        });
    }
}
