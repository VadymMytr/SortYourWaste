package vadym.my.wastesorting.data.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsImpl @Inject constructor(@ApplicationContext context: Context) : SharedPrefs {

    override var isIntroductionSeen: Boolean
        get() = sharedPrefs.getBoolean(PREF_INTRODUCTION_SEEN, false)
        set(value) {
            sharedPrefs.edit { putBoolean(PREF_INTRODUCTION_SEEN, value) }
        }

    private val sharedPrefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        edit().apply {
            this.action()
            this.apply()
        }
    }

    private companion object {
        const val PREFS_NAME = "shared_preferences"
        const val PREF_INTRODUCTION_SEEN = "introduction_seen"
    }
}
