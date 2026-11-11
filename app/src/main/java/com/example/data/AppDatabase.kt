package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CompanyEntity::class,
        UserEntity::class,
        StudentEntity::class,
        VehicleEntity::class,
        DriverEntity::class,
        CleanerEntity::class,
        RouteEntity::class,
        AcademicYearEntity::class,
        TransactionEntity::class,
        NotificationEntity::class,
        AuditLogEntity::class,
        TermsAndConditionsEntity::class,
        TermsAcceptanceEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun companyDao(): CompanyDao
    abstract fun userDao(): UserDao
    abstract fun studentDao(): StudentDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun driverDao(): DriverDao
    abstract fun cleanerDao(): CleanerDao
    abstract fun routeDao(): RouteDao
    abstract fun academicYearDao(): AcademicYearDao
    abstract fun transactionDao(): TransactionDao
    abstract fun notificationDao(): NotificationDao
    abstract fun auditLogDao(): AuditLogDao
    abstract fun termsAndConditionsDao(): TermsAndConditionsDao
    abstract fun termsAcceptanceDao(): TermsAcceptanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kgm_travels_database"
                )
                .fallbackToDestructiveMigration(true)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
