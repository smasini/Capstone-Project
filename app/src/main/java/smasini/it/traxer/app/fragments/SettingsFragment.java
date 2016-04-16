package smasini.it.traxer.app.fragments;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.util.List;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Language;
import smasini.it.thetvdb.task.callbacks.CallbackLanguage;
import smasini.it.traxer.R;


/**
 * Created by Simone Masini on 12/04/2016.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);

        TheTVDB theTVDB = new TheTVDB(getActivity(), getString(R.string.the_tvdb_api_key));
        theTVDB.getLanguages(new CallbackLanguage() {
            @Override
            public void doAfterTask(List<Language> languages) {
                ListPreference language = (ListPreference) findPreference(getString(R.string.language_key));
                String[] entryL = new String[languages.size()];
                String[] valuesL = new String[languages.size()];
                for(int i = 0;i<languages.size();i++){
                    entryL[i] = languages.get(i).getName();
                    valuesL[i]= languages.get(i).getPrefix();
                }
                language.setEntries(entryL);
                language.setEntryValues(valuesL);
            }
        });

    }
/*


       <ListPreference
        android:key="language"
        android:title="@string/language"
        android:defaultValue=""
        android:negativeButtonText="@null"
        android:positiveButtonText="@null" />

*/
}
