//created by KG 2018/08/20
package vitaty14.kg.searchtwitterinmyworld;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button_search);
        Button button_h = findViewById(R.id.button_history);

        final EditText editText = findViewById(R.id.editText_search);
        final EditText editText_gn = findViewById(R.id.editText_getname);

        final CheckBox checkBox_jp = findViewById(R.id.checkBox_jp);
        final CheckBox checkBox_gn = findViewById(R.id.checkBox_getname);
        final CheckBox checkBox_bt = findViewById(R.id.checkBox_bot);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchtext = editText.getText().toString();
                String searchname = null;
                StringBuilder sb = new StringBuilder("https://twitter.com/search?q=");

                //入力値確認
                Log.d("debug","Debug_input:"+searchtext);

                if(searchtext.length() == 0) {
                    if (checkBox_gn.isChecked() == true) {
                        searchname = editText_gn.getText().toString();
                        sb.append("%20from:@" + searchname);
                        Log.d("debug", "Debug(notText && name):" + sb);
                    } else {
                        Toast toast = Toast.makeText(MainActivity.this, "word null", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }

                if(searchtext.startsWith("#")){
                    String str_tag = searchtext.replaceFirst("#","%23");
                    sb.append(str_tag);
                    Log.d("debug","Debug(#):"+sb);
                }

                if(checkBox_jp.isChecked() == true){
                    if(searchtext.startsWith("#")){
                        sb.append("%20lang:ja");
                        Log.d("debug","Debug(lang:ja && #):"+sb);
                    }else {
                        String str_jp = searchtext + "%20lang:ja";
                        sb.append(str_jp);
                        Log.d("debug", "Debug(lang:ja):" + sb);
                    }
                }

                if(checkBox_gn.isChecked() == true && searchname == null){
                    searchname = editText_gn.getText().toString();
                    if(searchname.length() == 0){
                        Toast toast = Toast.makeText(MainActivity.this, "user ID not Found", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(searchtext.startsWith("#") || checkBox_jp.isChecked() == true) {
                        sb.append("%20from:@" + searchname);
                        Log.d("debug", "Debug(searchname && (# || jp)):" + sb);
                    }else {
                        sb.append(searchtext + "%20from:@" + searchname);
                        Log.d("debug", "Debug(searchname):" + sb);
                    }
                }

                else {
                    if(!searchtext.startsWith("#") && checkBox_jp.isChecked() == false && checkBox_gn.isChecked() == false){
                        sb.append(searchtext);
                    }
                }
                if(checkBox_bt.isChecked() == true){
                    sb.append("%20-%22bot%22");
                }
                Log.d("debug","DebugMessage_last:"+sb);

                SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
                sp.edit().putString("SaveString",sb.toString()).commit();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
                startActivity(intent);
            }
        });

        button_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
