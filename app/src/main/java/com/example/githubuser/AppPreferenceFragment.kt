package com.example.githubuser

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class AppPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminder: String
    private lateinit var reminderPreference: SwitchPreference

    companion object {
        private const val DEFAULT_VALUE = "none"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        reminder = resources.getString(R.string.reminder)
        reminderPreference = findPreference<SwitchPreference>(reminder) as SwitchPreference

        val alarmReceiver = AlarmReceiver()

        val shared = preferenceManager.sharedPreferences
        reminderPreference.isChecked = shared.getBoolean(reminder, true)

        reminderPreference.setOnPreferenceChangeListener { preference, newValue ->
            val switched = (preference as SwitchPreference).isChecked
            if (!switched) {
                alarmReceiver.setRepeatingAlarm(requireContext(), AlarmReceiver.TYPE_REPEATING,
                    "05:26", "Daily Reminder")
            } else {
                alarmReceiver.cancelAlarm(requireContext(), AlarmReceiver.TYPE_REPEATING)
            }
            true
        }

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == reminder) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, true)
        }
    }
}