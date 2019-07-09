package pro.siberian.test.weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.androidannotations.annotations.EApplication
import pro.siberian.test.weather.di.ContextModule
import pro.siberian.test.weather.di.DaggerRepositoryComponent
import pro.siberian.test.weather.di.LocalDbModule
import pro.siberian.test.weather.di.RepositoryComponent
import pro.siberian.test.weather.engine.LocalDb

@SuppressLint("Registered")
@EApplication
open class WeatherApp : Application() {
    lateinit var repositories: RepositoryComponent
    override fun onCreate() {
        super.onCreate()

        lateinit var localDb: LocalDb
        localDb = Room.databaseBuilder(this, LocalDb::class.java, "local_db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    prepopulateDb(db)
                }

            }).build()

        repositories = DaggerRepositoryComponent.builder()
            .localDbModule(LocalDbModule(localDb))
            .contextModule(ContextModule(this))
            .build()
    }

    private fun prepopulateDb(db: SupportSQLiteDatabase) {
        var cv = ContentValues()
        cv.put("name", getString(R.string.moscow))
        cv.put("id", resources.getInteger(R.integer.moscow_code))
        db.insert("city", SQLiteDatabase.CONFLICT_REPLACE, cv)

        cv = ContentValues()
        cv.put("name", getString(R.string.petersburg))
        cv.put("id", resources.getInteger(R.integer.petersburg_code))
        db.insert("city", SQLiteDatabase.CONFLICT_REPLACE, cv)
    }
}