package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class KgmRepository(private val db: AppDatabase) {

    val allCompanies: Flow<List<CompanyEntity>> = db.companyDao().getAllCompanies()

    suspend fun getCompanyById(companyId: String): CompanyEntity? {
        return db.companyDao().getCompanyById(companyId)
    }

    suspend fun insertCompany(company: CompanyEntity) {
        db.companyDao().insertCompany(company)
    }

    suspend fun deleteCompany(companyId: String) {
        db.companyDao().deleteCompany(companyId)
    }

    val allUsers: Flow<List<UserEntity>> = db.userDao().getAllUsers()
    val allStudents: Flow<List<StudentEntity>> = db.studentDao().getAllStudents()
    val allVehicles: Flow<List<VehicleEntity>> = db.vehicleDao().getAllVehicles()
    val allDrivers: Flow<List<DriverEntity>> = db.driverDao().getAllDrivers()
    val allCleaners: Flow<List<CleanerEntity>> = db.cleanerDao().getAllCleaners()
    val allRoutes: Flow<List<RouteEntity>> = db.routeDao().getAllRoutes()
    val allAcademicYears: Flow<List<AcademicYearEntity>> = db.academicYearDao().getAllAcademicYears()
    val allTransactions: Flow<List<TransactionEntity>> = db.transactionDao().getAllTransactions()
    val allNotifications: Flow<List<NotificationEntity>> = db.notificationDao().getAllNotifications()
    val allAuditLogs: Flow<List<AuditLogEntity>> = db.auditLogDao().getAllAuditLogs()
    val currentTerms: Flow<TermsAndConditionsEntity?> = db.termsAndConditionsDao().getTermsAndConditionsFlow()
    val allTermsAcceptances: Flow<List<TermsAcceptanceEntity>> = db.termsAcceptanceDao().getAllAcceptancesFlow()

    // Terms & Conditions functions
    suspend fun getTermsAndConditions(): TermsAndConditionsEntity? {
        return db.termsAndConditionsDao().getTermsAndConditions()
    }

    suspend fun insertTermsAndConditions(terms: TermsAndConditionsEntity) {
        db.termsAndConditionsDao().insertTermsAndConditions(terms)
    }

    fun getAcceptancesByUsernameFlow(username: String): Flow<List<TermsAcceptanceEntity>> {
        return db.termsAcceptanceDao().getAcceptancesByUsernameFlow(username)
    }

    suspend fun getAcceptancesByUsername(username: String): List<TermsAcceptanceEntity> {
        return db.termsAcceptanceDao().getAcceptancesByUsername(username)
    }

    suspend fun insertTermsAcceptance(acceptance: TermsAcceptanceEntity) {
        db.termsAcceptanceDao().insertTermsAcceptance(acceptance)
    }

    // Auth functions
    suspend fun getUserByUsername(username: String): UserEntity? {
        return db.userDao().getUserByUsername(username)
    }

    suspend fun insertUser(user: UserEntity) {
        db.userDao().insertUser(user)
    }

    suspend fun insertUsers(users: List<UserEntity>) {
        db.userDao().insertUsers(users)
    }

    suspend fun deleteUser(username: String) {
        db.userDao().deleteUser(username)
    }

    // Student functions
    suspend fun getStudentById(id: Int): StudentEntity? {
        return db.studentDao().getStudentById(id)
    }

    suspend fun getStudentByCustomId(studentId: String): StudentEntity? {
        return db.studentDao().getStudentByCustomId(studentId)
    }

    suspend fun insertStudent(student: StudentEntity) {
        db.studentDao().insertStudent(student)
    }

    suspend fun insertStudents(students: List<StudentEntity>) {
        db.studentDao().insertStudents(students)
    }

    suspend fun deleteStudent(student: StudentEntity) {
        db.studentDao().deleteStudent(student)
    }

    suspend fun archiveStudent(id: Int) {
        db.studentDao().archiveStudent(id)
    }

    suspend fun restoreStudent(id: Int) {
        db.studentDao().restoreStudent(id)
    }

    suspend fun updateStudentBalance(id: Int, newBalance: Double) {
        db.studentDao().updateStudentBalance(id, newBalance)
    }

    // Vehicle functions
    suspend fun getVehicleByNumber(vehicleNumber: String): VehicleEntity? {
        return db.vehicleDao().getVehicleByNumber(vehicleNumber)
    }

    suspend fun insertVehicle(vehicle: VehicleEntity) {
        db.vehicleDao().insertVehicle(vehicle)
    }

    suspend fun deleteVehicle(vehicleNumber: String) {
        db.vehicleDao().deleteVehicle(vehicleNumber)
    }

    // Driver functions
    suspend fun getDriverByName(name: String): DriverEntity? {
        return db.driverDao().getDriverByName(name)
    }

    suspend fun insertDriver(driver: DriverEntity) {
        db.driverDao().insertDriver(driver)
    }

    suspend fun deleteDriver(name: String) {
        db.driverDao().deleteDriver(name)
    }

    // Cleaner functions
    suspend fun getCleanerByName(name: String): CleanerEntity? {
        return db.cleanerDao().getCleanerByName(name)
    }

    suspend fun insertCleaner(cleaner: CleanerEntity) {
        db.cleanerDao().insertCleaner(cleaner)
    }

    suspend fun deleteCleaner(name: String) {
        db.cleanerDao().deleteCleaner(name)
    }

    // Route functions
    suspend fun getRouteByNumber(routeNumber: String): RouteEntity? {
        return db.routeDao().getRouteByNumber(routeNumber)
    }

    suspend fun insertRoute(route: RouteEntity) {
        db.routeDao().insertRoute(route)
    }

    suspend fun deleteRoute(routeNumber: String) {
        db.routeDao().deleteRoute(routeNumber)
    }

    // Academic Year
    suspend fun insertAcademicYear(year: AcademicYearEntity) {
        db.academicYearDao().insertAcademicYear(year)
    }

    // Transaction
    suspend fun insertTransaction(transaction: TransactionEntity) {
        db.transactionDao().insertTransaction(transaction)
    }

    suspend fun insertTransactions(transactions: List<TransactionEntity>) {
        db.transactionDao().insertTransactions(transactions)
    }

    // Notification
    suspend fun insertNotification(notification: NotificationEntity) {
        db.notificationDao().insertNotification(notification)
    }

    suspend fun insertNotifications(notifications: List<NotificationEntity>) {
        db.notificationDao().insertNotifications(notifications)
    }

    suspend fun deleteNotificationById(id: Int) {
        db.notificationDao().deleteNotificationById(id)
    }

    // Audit Log
    suspend fun insertAuditLog(log: AuditLogEntity) {
        db.auditLogDao().insertAuditLog(log)
    }

    suspend fun insertAuditLogs(logs: List<AuditLogEntity>) {
        db.auditLogDao().insertAuditLogs(logs)
    }

    // Database Seeder
    suspend fun seedDatabaseIfNeeded() {
        val terms = db.termsAndConditionsDao().getTermsAndConditions()
        if (terms == null) {
            val defaultTermsContent = """
1. Registration
- Parents must provide accurate student and parent information.
- Parents must keep their contact details updated.
- False information may result in suspension of transport services.

2. Transport Fees
- Transport fees must be paid within the first week of every month.
- Fees are determined based on distance, pickup/drop timings, traffic conditions, route, road conditions, fuel costs, and vehicle maintenance.
- KGM Travels reserves the right to revise transport fees after notifying parents.

3. Payment Policy
- Payments must be made only through the official KGM Travels application using approved payment methods.
- Cash payments are not accepted unless specifically authorized by KGM Travels.
- Parents should include the student's name, class, and section where applicable.
- Partial monthly payments are not allowed.
- Digital receipts will be generated automatically after successful payment.

4. Advance Deposit
- Any advance deposit will be adjusted according to the KGM Travels transport policy.
- Advance deposits are generally non-refundable unless approved by KGM Travels.

5. Pickup & Drop Rules
- Students must be ready at the pickup point at least 5 minutes before the scheduled pickup time.
- The vehicle will not wait for late arrivals.
- Pickup or drop location changes require prior approval.
- Route changes may result in revised transport fees.

6. Student Conduct
Students must:
- Follow the driver's and attendant's instructions.
- Remain seated while the vehicle is moving.
- Behave respectfully toward other students.
- Keep the vehicle clean.
- Avoid damaging the vehicle or disturbing other passengers.
Parents are responsible for intentional damage caused by their child.

7. Parent Responsibilities
Parents must:
- Ensure students are ready on time.
- Inform KGM Travels if transport is not required on a particular day.
- Keep mobile numbers updated.
- Cooperate with transport staff.
- Follow all transport policies.

8. Safety
- Student safety is the highest priority.
- Parents should ensure safe pickup and drop-off where required.
- Students must not distract the driver while the vehicle is in motion.

9. Delays
Transport schedules may occasionally be affected due to:
- Traffic congestion
- Weather conditions
- Road closures
- Vehicle breakdowns
- Government restrictions
- Other unforeseen operational circumstances
KGM Travels will make reasonable efforts to minimize delays.

10. Vehicle & Route Changes
KGM Travels reserves the right to:
- Change vehicles
- Change drivers
- Modify routes
- Adjust pickup/drop timings
- Reassign students when operationally necessary
Parents will be informed whenever reasonably possible.

11. Holidays & Non-Service Days
- Transport services generally follow the school's academic calendar unless otherwise announced.

12. Communication
- Official transport-related communication will be provided through the KGM Travels app notifications and phone calls when necessary.

13. Privacy
- Student and parent information will be used only for providing transport services and administrative purposes. Personal information will be protected and handled responsibly.

14. Suspension of Service
Transport services may be suspended due to:
- Non-payment of fees
- Submission of false information
- Repeated violations of transport rules
- Behavior that compromises safety

15. Limitation of Liability
- KGM Travels is not responsible for delays or interruptions caused by circumstances beyond its reasonable control, including severe weather, traffic disruptions, road closures, government restrictions, or other emergencies.

16. Changes to Terms
- KGM Travels may revise these Terms & Conditions from time to time. Updated versions will be published in the app, and continued use of the service after acceptance will indicate agreement with the revised terms.

17. Contact Us
- For any transport-related queries, assistance, emergencies, or support, parents/guardians may contact KGM Travels only through the official contact phone number provided by KGM Travels.
- Support is provided via phone calls only. WhatsApp, email, and social media are not official customer support channels unless announced by KGM Travels.
- Parents must keep their registered mobile number updated to receive important transport-related calls when necessary.
            """.trimIndent()
            db.termsAndConditionsDao().insertTermsAndConditions(
                TermsAndConditionsEntity(
                    id = 1,
                    version = 1,
                    content = defaultTermsContent,
                    effectiveDate = "July 10, 2026",
                    lastUpdated = "2026-07-10T00:00:00-07:00"
                )
            )
        }

        val userCount = db.userDao().getUserCount()
        if (userCount > 0) {
            return // Database already seeded
        }

        // 0. Seed Default Company (KGM Travels)
        val defaultCompany = CompanyEntity(
            companyId = "kgm-travels",
            businessName = "KGM Travels",
            ownerName = "KGM Owner",
            mobileNumber = "9876543210",
            emailAddress = "owner@kgmtravels.com",
            businessAddress = "Velammal Campus, Chennai",
            status = "Approved",
            primaryColor = "#005CB9"
        )
        db.companyDao().insertCompany(defaultCompany)

        // 1. Seed Users
        val defaultUsers = listOf(
            UserEntity("admin", "KGM Owner", "Super Admin", "admin", "9876543210", "owner@kgmtravels.com"),
            UserEntity("staff", "Office Staff", "Office Staff", "staff", "6876543210", "office@kgmtravels.com"),
            UserEntity("parent", "Ramesh Kumar Parent", "Parent", "parent", "8876543210", "ramesh.p@gmail.com", "STU-26-001"),
            UserEntity("driver", "Ramesh Kumar Driver", "Driver", "driver", "7876543210", "ramesh.driver@gmail.com"),
            UserEntity("cleaner", "Sundar", "Cleaner", "cleaner", "5876543210", "sundar@gmail.com")
        )
        for (u in defaultUsers) db.userDao().insertUser(u)

        // 2. Seed Academic Years
        db.academicYearDao().insertAcademicYear(AcademicYearEntity("2026-2027", "Active"))
        db.academicYearDao().insertAcademicYear(AcademicYearEntity("2025-2026", "Archived"))

        // 3. Seed Vehicles
        val defaultVehicles = listOf(
            VehicleEntity(
                vehicleNumber = "TN-02-AB-1001",
                registrationNumber = "REG-TN02AB1001",
                vehicleModel = "School Bus 40 Seater",
                manufacturer = "Swaraj Mazda",
                fuelType = "Diesel",
                seatingCapacity = 40,
                currentStudentStrength = 2,
                insuranceNumber = "INS-SM-99812",
                insuranceExpiry = "2027-05-10",
                permitNumber = "PRM-TN-02-4521",
                permitExpiry = "2027-08-15",
                fitnessCertificate = "FIT-2026-90",
                fitnessExpiry = "2026-11-20",
                pollutionCertificate = "POL-2026-77",
                pollutionExpiry = "2026-09-12",
                roadTaxExpiry = "2026-12-05",
                maintenanceHistory = "Regular oil change & filter replacement done on 2026-05-15.",
                tyreReplacementHistory = "All 6 tyres replaced with MRF heavy duty on 2025-10-10.",
                batteryReplacementHistory = "Amaron heavy vehicle battery installed on 2025-12-20.",
                status = "Active"
            ),
            VehicleEntity(
                vehicleNumber = "TN-02-CD-2002",
                registrationNumber = "REG-TN02CD2002",
                vehicleModel = "Winger School Van 18 Seater",
                manufacturer = "Tata Motors",
                fuelType = "CNG",
                seatingCapacity = 18,
                currentStudentStrength = 1,
                insuranceNumber = "INS-TATA-33211",
                insuranceExpiry = "2026-10-15",
                permitNumber = "PRM-TN-02-1200",
                permitExpiry = "2026-12-30",
                fitnessCertificate = "FIT-2026-34",
                fitnessExpiry = "2027-02-18",
                pollutionCertificate = "POL-2026-15",
                pollutionExpiry = "2026-08-30",
                roadTaxExpiry = "2026-11-28",
                maintenanceHistory = "CNG leak check and spark plug replacement on 2026-04-10.",
                tyreReplacementHistory = "Front 2 tyres replaced on 2026-01-20.",
                batteryReplacementHistory = "Exide tubular battery installed on 2025-06-18.",
                status = "Active"
            )
        )
        for (v in defaultVehicles) db.vehicleDao().insertVehicle(v)

        // 4. Seed Drivers
        val defaultDrivers = listOf(
            DriverEntity(
                driverName = "Ramesh Kumar Driver",
                mobileNumber = "7876543210",
                alternateNumber = "9988776655",
                email = "ramesh.driver@gmail.com",
                address = "12, Pillaiyar Kovil St, Mel Ayanambakkam, Chennai",
                licenceNumber = "DL-04TN20150912",
                licenceExpiry = "2026-09-15", // Expiry soon
                aadhaarNumber = "4321-5678-9012",
                joiningDate = "2022-06-10",
                emergencyContact = "9876500112 (Wife)",
                medicalNotes = "Wears distance glasses. No chronic ailments.",
                salary = 22000.0,
                assignedVehicle = "TN-02-AB-1001",
                assignedRoute = "R-101",
                status = "Active"
            ),
            DriverEntity(
                driverName = "Vijay Singh",
                mobileNumber = "7564738291",
                alternateNumber = "9845321098",
                email = "vijay.singh@gmail.com",
                address = "45, Reddy Street, Mogappair West, Chennai",
                licenceNumber = "DL-02TN20180211",
                licenceExpiry = "2028-02-10",
                aadhaarNumber = "8901-2345-6789",
                joiningDate = "2023-09-15",
                emergencyContact = "9845322110 (Brother)",
                medicalNotes = "Perfect health.",
                salary = 20000.0,
                assignedVehicle = "TN-02-CD-2002",
                assignedRoute = "R-102",
                status = "Active"
            )
        )
        for (d in defaultDrivers) db.driverDao().insertDriver(d)

        // 5. Seed Cleaners
        val defaultCleaners = listOf(
            CleanerEntity(
                name = "Sundar",
                phoneNumber = "5876543210",
                address = "No. 3, Slum Clearance Board, Ambattur, Chennai",
                emergencyContact = "9043210987",
                assignedVehicle = "TN-02-AB-1001",
                joiningDate = "2024-01-15",
                salary = 12000.0,
                status = "Active"
            )
        )
        for (c in defaultCleaners) db.cleanerDao().insertCleaner(c)

        // 6. Seed Routes
        val defaultRoutes = listOf(
            RouteEntity(
                routeNumber = "R-101",
                routeName = "Mel Ayanambakkam - Main School Loop",
                startingPoint = "Mel Ayanambakkam Junction",
                endingPoint = "Velammal Main School Campus",
                pickupStops = "Mel Ayanambakkam, Mogappair West Bus Stand, Golden Flats, Annex Campus Gate, Main School Campus",
                dropStops = "Main School Campus, Annex Campus Gate, Golden Flats, Mogappair West, Mel Ayanambakkam",
                estimatedDistance = "8.5 km",
                estimatedTravelTime = "35 mins",
                morningSchedule = "Pickup starts at 07:45 AM, drop at school at 08:20 AM",
                eveningSchedule = "Departure at 03:45 PM, drops complete by 04:30 PM",
                assignedVehicle = "TN-02-AB-1001",
                assignedDriver = "Ramesh Kumar Driver"
            ),
            RouteEntity(
                routeNumber = "R-102",
                routeName = "Anna Nagar - Annex Campus Express",
                startingPoint = "Anna Nagar Roundtana",
                endingPoint = "Velammal Annex School Campus",
                pickupStops = "Anna Nagar Roundtana, Thirumangalam Flyover, Koyambedu Metro Stn, Annex Campus Gate",
                dropStops = "Annex Campus Gate, Koyambedu Metro Stn, Thirumangalam, Anna Nagar Roundtana",
                estimatedDistance = "12.0 km",
                estimatedTravelTime = "50 mins",
                morningSchedule = "Pickup starts at 07:20 AM, drop at school at 08:10 AM",
                eveningSchedule = "Departure at 03:50 PM, drops complete by 04:45 PM",
                assignedVehicle = "TN-02-CD-2002",
                assignedDriver = "Vijay Singh"
            )
        )
        for (r in defaultRoutes) db.routeDao().insertRoute(r)

        // 7. Seed Students
        val defaultStudents = listOf(
            StudentEntity(
                studentId = "STU-26-001",
                admissionNumber = "ADM-26-001",
                name = "Aditya Ramesh",
                gender = "Male",
                dateOfBirth = "2011-04-12",
                bloodGroup = "O+ Positive",
                classLevel = "Class 10",
                section = "A",
                academicYear = "2026-2027",
                campus = "Main School",
                category = "NEET",
                parentName = "Ramesh Kumar Parent",
                fatherName = "Ramesh Kumar Parent",
                motherName = "Gowri Ramesh",
                guardianName = "",
                parentMobileNumber = "8876543210", // links parent user account
                alternateMobile = "9845300012",
                whatsAppNumber = "8876543210",
                emailAddress = "ramesh.p@gmail.com",
                emergencyContact = "8876543210 (Father)",
                residentialAddress = "12, Pillaiyar Kovil St, Mel Ayanambakkam, Chennai",
                googleMapsLocation = "https://maps.google.com/?q=13.0906,80.1517",
                pickupAddress = "12, Pillaiyar Kovil St, Mel Ayanambakkam",
                pickupLandmark = "Near Ganesha Temple",
                dropAddress = "12, Pillaiyar Kovil St, Mel Ayanambakkam",
                dropLandmark = "Near Ganesha Temple",
                pickupTime = "07:50 AM",
                dropTime = "04:20 PM",
                assignedVehicle = "TN-02-AB-1001",
                assignedDriver = "Ramesh Kumar Driver",
                assignedRoute = "R-101",
                assignedSeat = "Seat 12",
                monthlyVanFee = 3500.0,
                discount = 500.0,
                advancePaid = 3000.0,
                balance = 0.0, // Fully paid for current month
                joiningDate = "2024-06-01",
                leavingDate = "",
                medicalNotes = "None",
                remarks = "Regular and punctual.",
                status = "Active"
            ),
            StudentEntity(
                studentId = "STU-26-002",
                admissionNumber = "ADM-26-002",
                name = "Kavya Vignesh",
                gender = "Female",
                dateOfBirth = "2013-11-20",
                bloodGroup = "A+ Positive",
                classLevel = "Class 8",
                section = "B",
                academicYear = "2026-2027",
                campus = "Annex School",
                category = "Regular",
                parentName = "Vignesh Raj",
                fatherName = "Vignesh Raj",
                motherName = "Lakshmi Vignesh",
                guardianName = "",
                parentMobileNumber = "9840123456",
                alternateMobile = "9840554433",
                whatsAppNumber = "9840123456",
                emailAddress = "vignesh.r@gmail.com",
                emergencyContact = "9840123456 (Father)",
                residentialAddress = "7A, Golden Flats Main Road, Mogappair West, Chennai",
                googleMapsLocation = "https://maps.google.com/?q=13.0850,80.1650",
                pickupAddress = "7A, Golden Flats Main Road, Mogappair West",
                pickupLandmark = "Opposite ICICI Bank ATM",
                dropAddress = "7A, Golden Flats Main Road, Mogappair West",
                dropLandmark = "Opposite ICICI Bank ATM",
                pickupTime = "08:00 AM",
                dropTime = "04:10 PM",
                assignedVehicle = "TN-02-AB-1001",
                assignedDriver = "Ramesh Kumar Driver",
                assignedRoute = "R-101",
                assignedSeat = "Seat 25",
                monthlyVanFee = 3000.0,
                discount = 0.0,
                advancePaid = 0.0,
                balance = 3000.0, // Pending fee
                joiningDate = "2025-06-15",
                leavingDate = "",
                medicalNotes = "Mild dust allergy, keep Windows clean.",
                remarks = "Polite student.",
                status = "Active"
            ),
            StudentEntity(
                studentId = "STU-26-003",
                admissionNumber = "ADM-26-003",
                name = "Siddharth Vijay",
                gender = "Male",
                dateOfBirth = "2009-08-05",
                bloodGroup = "B+ Positive",
                classLevel = "Class 12",
                section = "C",
                academicYear = "2026-2027",
                campus = "Main School",
                category = "IIT Foundation",
                parentName = "Vijay Sundar",
                fatherName = "Vijay Sundar",
                motherName = "Sujatha Vijay",
                guardianName = "",
                parentMobileNumber = "9840987654",
                alternateMobile = "9840112233",
                whatsAppNumber = "9840987654",
                emailAddress = "vijay.sundar@gmail.com",
                emergencyContact = "9840987654 (Father)",
                residentialAddress = "No. 110, Y-Block, Anna Nagar Roundtana, Chennai",
                googleMapsLocation = "https://maps.google.com/?q=13.0851,80.2101",
                pickupAddress = "No. 110, Y-Block, Anna Nagar Roundtana",
                pickupLandmark = "Near Saravana Bhavan Hotel",
                dropAddress = "No. 110, Y-Block, Anna Nagar Roundtana",
                dropLandmark = "Near Saravana Bhavan Hotel",
                pickupTime = "07:25 AM",
                dropTime = "04:40 PM",
                assignedVehicle = "TN-02-CD-2002",
                assignedDriver = "Vijay Singh",
                assignedRoute = "R-102",
                assignedSeat = "Seat 5",
                monthlyVanFee = 4000.0,
                discount = 200.0,
                advancePaid = 2000.0,
                balance = 1800.0, // Overdue/Pending fee
                joiningDate = "2023-06-05",
                leavingDate = "",
                medicalNotes = "None",
                remarks = "Very energetic, participates in NEET preparation.",
                status = "Active"
            )
        )
        for (s in defaultStudents) db.studentDao().insertStudent(s)

        // 8. Seed Transactions
        val defaultTransactions = listOf(
            TransactionEntity(
                transactionId = "TXN-20260701-0812",
                studentId = "STU-26-001",
                studentName = "Aditya Ramesh",
                classAndSection = "Class 10 - A",
                vehicleNumber = "TN-02-AB-1001",
                routeNumber = "R-101",
                paymentMonth = "July 2026",
                amount = 3000.0,
                paymentMethod = "PhonePe",
                dateAndTime = "2026-07-01 10:30 AM",
                status = "Paid"
            ),
            TransactionEntity(
                transactionId = "TXN-20260702-1452",
                studentId = "STU-26-003",
                studentName = "Siddharth Vijay",
                classAndSection = "Class 12 - C",
                vehicleNumber = "TN-02-CD-2002",
                routeNumber = "R-102",
                paymentMonth = "July 2026",
                amount = 2000.0,
                paymentMethod = "Google Pay",
                dateAndTime = "2026-07-02 03:45 PM",
                status = "Paid"
            )
        )
        for (t in defaultTransactions) db.transactionDao().insertTransaction(t)

        // 9. Seed Notifications
        val defaultNotifications = listOf(
            NotificationEntity(
                title = "July Transport Fee Invoice",
                message = "Transport fees invoices for the academic month of July 2026 are generated. Please make your payment by the 10th to avoid late fees.",
                timestamp = "2026-07-01 09:00 AM",
                targetRole = "Parent"
            ),
            NotificationEntity(
                title = "Golden Flats Route detours today",
                message = "Notice for Route R-101: A sewer line work near Golden Flats main junction requires a detour. Drop times may be delayed by 10 minutes.",
                timestamp = "2026-07-07 03:00 PM",
                targetRole = "All"
            )
        )
        for (n in defaultNotifications) db.notificationDao().insertNotification(n)

        // 10. Seed Audit Logs
        db.auditLogDao().insertAuditLog(
            AuditLogEntity(
                timestamp = "2026-07-08 02:48 AM",
                adminUser = "admin",
                actionType = "Create",
                description = "Initialized system database with standard school campuses and default student data for Velammal Vidyalaya Mel Ayanambakkam."
            )
        )
    }
}
