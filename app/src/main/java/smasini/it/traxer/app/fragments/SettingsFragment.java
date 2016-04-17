package smasini.it.traxer.app.fragments;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;

import java.util.List;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Language;
import smasini.it.thetvdb.task.callbacks.CallbackLanguage;
import smasini.it.traxer.R;
import smasini.it.traxer.notification.NotificationHelper;


/**
 * Created by Simone Masini on 12/04/2016.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);

        TheTVDB.getInstance().getLanguages(new CallbackLanguage() {
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

        ListPreference language = (ListPreference) findPreference(getString(R.string.language_key));
        language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String language = (String)o;
                TheTVDB.getInstance().changeLanguage(language);
                return true;
            }
        });

        SwitchPreferenceCompat notificationPrefs = (SwitchPreferenceCompat) findPreference(getString(R.string.prefs_notification_key));
        notificationPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean enable = (boolean)o;
                if(enable){
                    NotificationHelper.enableNotification(getActivity());
                }else{
                    NotificationHelper.disableNotification(getActivity());
                }
                return true;
            }
        });
    }
}
