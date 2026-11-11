package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LoadTest1000Users {

    private lateinit var db: AppDatabase
    private lateinit var repository: KgmRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = KgmRepository(db)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test1000ConcurrentUsersAuthenticationAndRegistration() {
        runBlocking {
            val userCount = 1000
            val createdUsers = Collections.synchronizedList(mutableListOf<UserEntity>())
            val completedCount = AtomicInteger(0)

            val deferredList = (1..userCount).map { index ->
                async(Dispatchers.Default) {
                    val role = when {
                        index <= 800 -> "Parent"
                        index <= 900 -> "Driver"
                        index <= 950 -> "Cleaner"
                        else -> "Admin"
                    }
                    val username = "user_%04d".format(index)
                    val user = UserEntity(
                        username = username,
                        passwordHash = "password$index",
                        role = role,
                        name = "Test User $index",
                        mobileNumber = "98765%05d".format(index),
                        email = "user$index@kgmtravels.com",
                        linkedStudentId = if (role == "Parent") "STU_%04d".format(index) else null,
                        companyId = "kgm-travels"
                    )
                    repository.insertUser(user)
                    createdUsers.add(user)
                    completedCount.incrementAndGet()
                }
            }

            deferredList.awaitAll()
            assertEquals(userCount, completedCount.get())

            // Verify all 1,000 users are stored and retrievable concurrently
            val queryDeferredList = (1..userCount).map { index ->
                async(Dispatchers.Default) {
                    val username = "user_%04d".format(index)
                    val retrieved = repository.getUserByUsername(username)
                    assertNotNull("User $username should exist in database", retrieved)
                    assertEquals(username, retrieved?.username)
                }
            }
            queryDeferredList.awaitAll()
        }
    }

    @Test
    fun test1000ConcurrentStudentRegistrationsAndFeeSettlements() {
        runBlocking {
            val count = 1000
            val txnMap = ConcurrentHashMap<String, Boolean>()
            val totalAmountProcessed = AtomicInteger(0)

            // 1. Concurrently insert 1,000 students
            val studentInsertDeferred = (1..count).map { index ->
                async(Dispatchers.Default) {
                    val student = StudentEntity(
                        id = index,
                        studentId = "STU_%04d".format(index),
                        admissionNumber = "ADM_%04d".format(index),
                        name = "Student Number $index",
                        gender = if (index % 2 == 0) "Male" else "Female",
                        dateOfBirth = "2015-05-15",
                        bloodGroup = "O+",
                        classLevel = "${(index % 12) + 1}",
                        section = listOf("A", "B", "C", "D")[index % 4],
                        academicYear = "2026-2027",
                        campus = "Main School",
                        category = "Regular",
                        parentName = "Parent of Student $index",
                        fatherName = "Father $index",
                        motherName = "Mother $index",
                        guardianName = "",
                        parentMobileNumber = "98765%05d".format(index),
                        alternateMobile = "",
                        whatsAppNumber = "98765%05d".format(index),
                        emailAddress = "parent$index@gmail.com",
                        emergencyContact = "98765%05d".format(index),
                        residentialAddress = "No $index, Anna Nagar, Chennai",
                        googleMapsLocation = "13.0827,80.2707",
                        pickupAddress = "Stop ${(index % 30) + 1}",
                        pickupLandmark = "Near Junction",
                        dropAddress = "Stop ${(index % 30) + 1}",
                        dropLandmark = "Near Junction",
                        pickupTime = "07:30 AM",
                        dropTime = "03:45 PM",
                        assignedVehicle = "TN-02-AB-${1000 + (index % 25)}",
                        assignedDriver = "Driver ${(index % 20) + 1}",
                        assignedRoute = "R-${101 + (index % 15)}",
                        assignedSeat = "S${(index % 40) + 1}",
                        monthlyVanFee = 1500.0,
                        discount = 0.0,
                        advancePaid = 0.0,
                        balance = 1500.0,
                        joiningDate = "2026-06-01",
                        leavingDate = "",
                        medicalNotes = "None",
                        remarks = "All good",
                        status = "Active",
                        companyId = "kgm-travels"
                    )
                    repository.insertStudent(student)
                }
            }
            studentInsertDeferred.awaitAll()

            // 2. Concurrently simulate 1,000 parent fee payments
            val paymentDeferred = (1..count).map { index ->
                async(Dispatchers.Default) {
                    val studentCustomId = "STU_%04d".format(index)
                    val student = repository.getStudentByCustomId(studentCustomId)
                    assertNotNull("Student $studentCustomId must exist", student)

                    val amountPaid = 1500.0
                    val ts = System.currentTimeMillis()
                    val randomSuffix = UUID.randomUUID().toString().take(6).uppercase()
                    val txnId = "TXN-$ts-$randomSuffix-$index"

                    // Check collision freedom
                    val previous = txnMap.putIfAbsent(txnId, true)
                    assertNull("Transaction ID $txnId must be strictly unique", previous)

                    val updatedStudent = student!!.copy(
                        advancePaid = student.advancePaid + amountPaid,
                        balance = 0.0
                    )
                    repository.insertStudent(updatedStudent)

                    val transaction = TransactionEntity(
                        transactionId = txnId,
                        studentId = student.studentId,
                        studentName = student.name,
                        classAndSection = "${student.classLevel} - ${student.section}",
                        vehicleNumber = student.assignedVehicle,
                        routeNumber = student.assignedRoute,
                        paymentMonth = "August 2026",
                        amount = amountPaid,
                        paymentMethod = listOf("UPI", "Google Pay", "PhonePe", "Net Banking")[index % 4],
                        dateAndTime = "2026-08-21 12:00 PM",
                        status = "Paid",
                        companyId = "kgm-travels"
                    )
                    repository.insertTransaction(transaction)

                    val notification = NotificationEntity(
                        title = "Fee Payment Receipt - August 2026",
                        message = "Payment of Rs.$amountPaid confirmed for ${student.name}. Txn: $txnId",
                        timestamp = "2026-08-21 12:00 PM",
                        targetRole = "Parent",
                        targetUser = student.parentMobileNumber,
                        companyId = "kgm-travels"
                    )
                    repository.insertNotification(notification)

                    totalAmountProcessed.addAndGet(amountPaid.toInt())
                }
            }
            paymentDeferred.awaitAll()

            assertEquals(1000 * 1500, totalAmountProcessed.get())
            assertEquals(1000, txnMap.size)

            // Verify student balances updated correctly
            val allStudents = repository.allStudents.first()
            assertEquals(1000, allStudents.size)
            val fullyPaidStudents = allStudents.count { it.balance == 0.0 && it.advancePaid == 1500.0 }
            assertEquals(1000, fullyPaidStudents)

            val allTransactions = repository.allTransactions.first()
            assertEquals(1000, allTransactions.size)

            val allNotifications = repository.allNotifications.first()
            assertEquals(1000, allNotifications.size)
        }
    }

    @Test
    fun test1000ConcurrentAuditLogsAndTermsAcceptances() {
        runBlocking {
            val count = 1000

            val logAndTermsDeferred = (1..count).map { index ->
                async(Dispatchers.Default) {
                    val username = "user_%04d".format(index)
                    
                    // Concurrent audit logging
                    repository.insertAuditLog(
                        AuditLogEntity(
                            adminUser = username,
                            actionType = "Login",
                            description = "User $username logged in successfully on Android client.",
                            timestamp = "2026-08-21 12:05 PM",
                            companyId = "kgm-travels"
                        )
                    )

                    // Concurrent terms acceptance
                    repository.insertTermsAcceptance(
                        TermsAcceptanceEntity(
                            username = username,
                            versionAccepted = 1,
                            acceptedAt = "2026-08-21 12:05 PM",
                            companyId = "kgm-travels"
                        )
                    )
                }
            }
            logAndTermsDeferred.awaitAll()

            val allLogs = repository.allAuditLogs.first()
            assertEquals(1000, allLogs.size)

            val allAcceptances = repository.allTermsAcceptances.first()
            assertEquals(1000, allAcceptances.size)
        }
    }

    @Test
    fun test1000BatchOperationsThroughput() {
        runBlocking {
            val count = 1000

            val bulkStudents = (1..count).map { index ->
                StudentEntity(
                    id = index + 5000,
                    studentId = "BULK_%04d".format(index),
                    admissionNumber = "BULK_ADM_%04d".format(index),
                    name = "Bulk Student $index",
                    gender = "Male",
                    dateOfBirth = "2016-01-01",
                    bloodGroup = "B+",
                    classLevel = "10",
                    section = "A",
                    academicYear = "2026-2027",
                    campus = "Main School",
                    category = "Regular",
                    parentName = "Bulk Parent $index",
                    fatherName = "Father $index",
                    motherName = "Mother $index",
                    guardianName = "",
                    parentMobileNumber = "99999%05d".format(index),
                    alternateMobile = "",
                    whatsAppNumber = "99999%05d".format(index),
                    emailAddress = "bulk$index@gmail.com",
                    emergencyContact = "99999%05d".format(index),
                    residentialAddress = "Chennai",
                    googleMapsLocation = "13.0827,80.2707",
                    pickupAddress = "Main Gate",
                    pickupLandmark = "Gate",
                    dropAddress = "Main Gate",
                    dropLandmark = "Gate",
                    pickupTime = "07:30 AM",
                    dropTime = "04:00 PM",
                    assignedVehicle = "TN-02-AB-1001",
                    assignedDriver = "Driver 1",
                    assignedRoute = "R-101",
                    assignedSeat = "S$index",
                    monthlyVanFee = 2000.0,
                    discount = 0.0,
                    advancePaid = 2000.0,
                    balance = 0.0,
                    joiningDate = "2026-06-01",
                    leavingDate = "",
                    medicalNotes = "",
                    remarks = "",
                    status = "Active",
                    companyId = "kgm-travels"
                )
            }

            // Batch insert
            repository.insertStudents(bulkStudents)

            val bulkTransactions = (1..count).map { index ->
                TransactionEntity(
                    transactionId = "BULK-TXN-$index-${UUID.randomUUID()}",
                    studentId = "BULK_%04d".format(index),
                    studentName = "Bulk Student $index",
                    classAndSection = "10 - A",
                    vehicleNumber = "TN-02-AB-1001",
                    routeNumber = "R-101",
                    paymentMonth = "Term 1",
                    amount = 2000.0,
                    paymentMethod = "Razorpay Online",
                    dateAndTime = "2026-08-21 12:10 PM",
                    status = "Paid",
                    companyId = "kgm-travels"
                )
            }

            repository.insertTransactions(bulkTransactions)

            val totalStudents = repository.allStudents.first().count { it.studentId.startsWith("BULK_") }
            assertEquals(1000, totalStudents)

            val totalTxns = repository.allTransactions.first().count { it.transactionId.startsWith("BULK-TXN-") }
            assertEquals(1000, totalTxns)
        }
    }
}
