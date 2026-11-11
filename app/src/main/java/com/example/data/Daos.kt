package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT * FROM users WHERE username = :username OR mobileNumber = :username OR email = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users WHERE username = :username")
    suspend fun deleteUser(username: String)
}

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    suspend fun getStudentById(id: Int): StudentEntity?

    @Query("SELECT * FROM students WHERE studentId = :studentId LIMIT 1")
    suspend fun getStudentByCustomId(studentId: String): StudentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<StudentEntity>)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)

    @Query("UPDATE students SET status = 'Archived' WHERE id = :id")
    suspend fun archiveStudent(id: Int)

    @Query("UPDATE students SET status = 'Active' WHERE id = :id")
    suspend fun restoreStudent(id: Int)

    @Query("UPDATE students SET balance = :newBalance WHERE id = :id")
    suspend fun updateStudentBalance(id: Int, newBalance: Double)
}

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles ORDER BY vehicleNumber ASC")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE vehicleNumber = :vehicleNumber LIMIT 1")
    suspend fun getVehicleByNumber(vehicleNumber: String): VehicleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle: VehicleEntity)

    @Query("DELETE FROM vehicles WHERE vehicleNumber = :vehicleNumber")
    suspend fun deleteVehicle(vehicleNumber: String)
}

@Dao
interface DriverDao {
    @Query("SELECT * FROM drivers ORDER BY driverName ASC")
    fun getAllDrivers(): Flow<List<DriverEntity>>

    @Query("SELECT * FROM drivers WHERE driverName = :name LIMIT 1")
    suspend fun getDriverByName(name: String): DriverEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriver(driver: DriverEntity)

    @Query("DELETE FROM drivers WHERE driverName = :name")
    suspend fun deleteDriver(name: String)
}

@Dao
interface CleanerDao {
    @Query("SELECT * FROM cleaners ORDER BY name ASC")
    fun getAllCleaners(): Flow<List<CleanerEntity>>

    @Query("SELECT * FROM cleaners WHERE name = :name LIMIT 1")
    suspend fun getCleanerByName(name: String): CleanerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCleaner(cleaner: CleanerEntity)

    @Query("DELETE FROM cleaners WHERE name = :name")
    suspend fun deleteCleaner(name: String)
}

@Dao
interface RouteDao {
    @Query("SELECT * FROM routes ORDER BY routeNumber ASC")
    fun getAllRoutes(): Flow<List<RouteEntity>>

    @Query("SELECT * FROM routes WHERE routeNumber = :routeNumber LIMIT 1")
    suspend fun getRouteByNumber(routeNumber: String): RouteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: RouteEntity)

    @Query("DELETE FROM routes WHERE routeNumber = :routeNumber")
    suspend fun deleteRoute(routeNumber: String)
}

@Dao
interface AcademicYearDao {
    @Query("SELECT * FROM academic_years ORDER BY yearName DESC")
    fun getAllAcademicYears(): Flow<List<AcademicYearEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAcademicYear(year: AcademicYearEntity)
}

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotificationById(id: Int)
}

@Dao
interface AuditLogDao {
    @Query("SELECT * FROM audit_logs ORDER BY id DESC")
    fun getAllAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLogs(logs: List<AuditLogEntity>)
}

@Dao
interface TermsAndConditionsDao {
    @Query("SELECT * FROM terms_conditions WHERE id = 1 LIMIT 1")
    fun getTermsAndConditionsFlow(): Flow<TermsAndConditionsEntity?>

    @Query("SELECT * FROM terms_conditions WHERE id = 1 LIMIT 1")
    suspend fun getTermsAndConditions(): TermsAndConditionsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTermsAndConditions(terms: TermsAndConditionsEntity)
}

@Dao
interface TermsAcceptanceDao {
    @Query("SELECT * FROM terms_acceptance WHERE username = :username ORDER BY versionAccepted DESC")
    fun getAcceptancesByUsernameFlow(username: String): Flow<List<TermsAcceptanceEntity>>

    @Query("SELECT * FROM terms_acceptance WHERE username = :username ORDER BY versionAccepted DESC")
    suspend fun getAcceptancesByUsername(username: String): List<TermsAcceptanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTermsAcceptance(acceptance: TermsAcceptanceEntity)

    @Query("SELECT * FROM terms_acceptance")
    fun getAllAcceptancesFlow(): Flow<List<TermsAcceptanceEntity>>
}

@Dao
interface CompanyDao {
    @Query("SELECT * FROM companies ORDER BY businessName ASC")
    fun getAllCompanies(): Flow<List<CompanyEntity>>

    @Query("SELECT * FROM companies WHERE companyId = :companyId LIMIT 1")
    suspend fun getCompanyById(companyId: String): CompanyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CompanyEntity)

    @Query("DELETE FROM companies WHERE companyId = :companyId")
    suspend fun deleteCompany(companyId: String)
}
