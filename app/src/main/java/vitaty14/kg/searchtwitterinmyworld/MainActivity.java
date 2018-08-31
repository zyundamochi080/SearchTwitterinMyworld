//created by KG 2018/08/20
package vitaty14.kg.searchtwitterinmyworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button button = findViewById(R.id.button_search);
        Button button_h = findViewById(R.id.button_history);

        final EditText editText = findViewById(R.id.editText_search);
        final EditText editText_gn = findViewById(R.id.editText_getname);

        final CheckBox checkBox_sl = findViewById(R.id.checkBox_selectlanguage);
        final CheckBox checkBox_gn = findViewById(R.id.checkBox_getname);
        final CheckBox checkBox_bt = findViewById(R.id.checkBox_bot);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchtext = editText.getText().toString();
                String searchname = null;
                StringBuilder sb = new StringBuilder("https://twitter.com/search?q=");

                String exceptword = SettingsFragment.getText(MainActivity.this);
                String selectword = SettingsFragment.getLanguage(MainActivity.this);

                //入力値確認
                Log.d("debug","Debug_input:"+searchtext);

                if(searchtext.length() == 0) {
                    if (checkBox_gn.isChecked()) {
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

                if(checkBox_sl.isChecked()){
                    if(searchtext.startsWith("#")){
                        sb.append("%20" + selectword);
                        Log.d("debug","Debug(lang:ja && #):" + sb);
                    }else {
                        String str_sl = searchtext + "%20" + selectword;
                        sb.append(str_sl);
                        Log.d("debug", "Debug(lang:ja):" + sb);
                    }
                }

                if(checkBox_gn.isChecked() && searchname == null){
                    searchname = editText_gn.getText().toString();
                    if(searchname.length() == 0){
                        Toast toast = Toast.makeText(MainActivity.this, "user ID not Found", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(searchtext.startsWith("#") || checkBox_sl.isChecked()) {
                        sb.append("%20from:@" + searchname);
                        Log.d("debug", "Debug(searchname && (# || jp)):" + sb);
                    }else {
                        sb.append(searchtext + "%20from:@" + searchname);
                        Log.d("debug", "Debug(searchname):" + sb);
                    }
                }

                else {
                    if(!searchtext.startsWith("#") && !checkBox_sl.isChecked() && !checkBox_gn.isChecked()){
                        sb.append(searchtext);
                    }
                }
                if(checkBox_bt.isChecked()){
                    sb.append("%20-%22" + exceptword + "%22");
                }

                //最終出力文字列
                Log.d("debug","DebugMessage_last:"+sb);

                SharedPreferences sp = getSharedPreferences("HistoryDate",MODE_PRIVATE);

                for(int count=1; count<6; count++) {
                    Log.d("debug", "Debug(string):" + count);
                    if (!sp.contains("SaveString_" + count)) {
                        sp.edit().putString("SaveString_" + count, sb.toString()).apply();
                        Log.d("debug", "Debug(writing)");
                        count = 10;
                    }
                    if(sp.contains("SaveString_5") && count<=5){
                        Toast.makeText(MainActivity.this,"HistoryCapacity is full",Toast.LENGTH_SHORT).show();
                    }
                }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_open_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
