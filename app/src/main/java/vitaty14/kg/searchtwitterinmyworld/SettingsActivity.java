package vitaty14.kg.searchtwitterinmyworld;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity{

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new SettingsFragment())
                .commit();
    }
}
