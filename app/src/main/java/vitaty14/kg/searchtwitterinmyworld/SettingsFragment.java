package vitaty14.kg.searchtwitterinmyworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment{
    private static final String P_KEY_LANGUAGE = "setting_language";
    private static final String P_KEY_TEXT = "setting_word";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);

        EditTextPreference text = (EditTextPreference) findPreference(P_KEY_TEXT);
        text.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {
                preference.setSummary((CharSequence) value);
                return true;
            }
        });
        text.setSummary(text.getText());
    }

    public static String getText(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getString(P_KEY_TEXT, context.getString(R.string.default_word));
    }
    public static String getLanguage(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String value = preferences.getString(P_KEY_LANGUAGE,null);
        if(value == null){
            value = "japanese";
        }
        switch (value){
            case "japanese":
                return "lang:ja";
            case "english":
                return "lang:en";
            case "german":
                return "lang:de";
            case "spanish":
                return "lang:es";
            case "dutch":
                return "lang:nl";
            case "french":
                return "lang:fr";
            case "italian":
                return "lang:it";
            case "chinese":
                return "lang:zh";
            case "korean":
                return "lang:ko";
            default:
                break;
        }
        return "japanese";
    }
}
