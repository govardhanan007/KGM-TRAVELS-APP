package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class CompanyEntity(
    @PrimaryKey val companyId: String,
    val businessName: String,
    val ownerName: String,
    val mobileNumber: String,
    val emailAddress: String,
    val businessAddress: String,
    val gstNumber: String = "",
    val logoUrl: String = "",
    val businessLicense: String = "",
    val bankAccountDetails: String = "",
    val paymentGatewayDetails: String = "",
    val numVehicles: Int = 0,
    val numDrivers: Int = 0,
    val schoolsServed: String = "",
    val status: String = "Pending", // "Pending", "Approved", "Rejected", "Suspended"
    val primaryColor: String = "#005CB9", // Brand primary color Hex code
    val contactNumber: String = "9876543210",
    val splashLogoUrl: String = "",
    val invoiceFooter: String = "Thank you for using our premium transport services.",
    val receiptHeader: String = "Official Transport Payment Receipt"
)

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val username: String, // mobile number or email
    val name: String,
    val role: String, // "Super Admin", "Admin", "Office Staff", "Parent", "Driver", "Cleaner"
    val passwordHash: String,
    val mobileNumber: String,
    val email: String,
    val linkedStudentId: String? = null, // for parents to view their child
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: String,
    val admissionNumber: String,
    val name: String,
    val gender: String,
    val dateOfBirth: String,
    val bloodGroup: String,
    val classLevel: String,
    val section: String,
    val academicYear: String,
    val campus: String, // "Main School", "Annex School"
    val category: String, // "Regular", "NEET", "IIT Foundation"
    val parentName: String,
    val fatherName: String,
    val motherName: String,
    val guardianName: String,
    val parentMobileNumber: String,
    val alternateMobile: String,
    val whatsAppNumber: String,
    val emailAddress: String,
    val emergencyContact: String,
    val residentialAddress: String,
    val googleMapsLocation: String,
    val pickupAddress: String,
    val pickupLandmark: String,
    val dropAddress: String,
    val dropLandmark: String,
    val pickupTime: String,
    val dropTime: String,
    val assignedVehicle: String, // vehicle registration number
    val assignedDriver: String,  // driver name
    val assignedRoute: String,   // route code / number
    val assignedSeat: String,    // seat ID/number e.g. "S5"
    val monthlyVanFee: Double,
    val discount: Double,
    val advancePaid: Double,
    val balance: Double,
    val joiningDate: String,
    val leavingDate: String,
    val medicalNotes: String,
    val remarks: String,
    val status: String, // "Active", "Inactive", "Archived"
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey val vehicleNumber: String, // e.g. TN-02-AB-1234
    val registrationNumber: String,
    val vehicleModel: String,
    val manufacturer: String,
    val fuelType: String, // "Diesel", "Petrol", "CNG", "Electric"
    val seatingCapacity: Int,
    val currentStudentStrength: Int,
    val insuranceNumber: String,
    val insuranceExpiry: String,
    val permitNumber: String,
    val permitExpiry: String,
    val fitnessCertificate: String,
    val fitnessExpiry: String,
    val pollutionCertificate: String,
    val pollutionExpiry: String,
    val roadTaxExpiry: String,
    val maintenanceHistory: String, // Brief summaries
    val tyreReplacementHistory: String,
    val batteryReplacementHistory: String,
    val status: String, // "Active", "In Service", "Under Repair"
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "drivers")
data class DriverEntity(
    @PrimaryKey val driverName: String,
    val mobileNumber: String,
    val alternateNumber: String,
    val email: String,
    val address: String,
    val licenceNumber: String,
    val licenceExpiry: String,
    val aadhaarNumber: String,
    val joiningDate: String,
    val emergencyContact: String,
    val medicalNotes: String,
    val salary: Double,
    val assignedVehicle: String,
    val assignedRoute: String,
    val status: String, // "Active", "On Leave", "Suspended"
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "cleaners")
data class CleanerEntity(
    @PrimaryKey val name: String,
    val phoneNumber: String,
    val address: String,
    val emergencyContact: String,
    val assignedVehicle: String,
    val joiningDate: String,
    val salary: Double,
    val status: String, // "Active", "On Leave"
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "routes")
data class RouteEntity(
    @PrimaryKey val routeNumber: String, // e.g. R-101
    val routeName: String,
    val startingPoint: String,
    val endingPoint: String,
    val pickupStops: String, // Comma separated list of stops
    val dropStops: String,   // Comma separated list of stops
    val estimatedDistance: String,
    val estimatedTravelTime: String,
    val morningSchedule: String,
    val eveningSchedule: String,
    val assignedVehicle: String,
    val assignedDriver: String,
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "academic_years")
data class AcademicYearEntity(
    @PrimaryKey val yearName: String, // e.g. "2026-2027"
    val status: String, // "Active", "Archived"
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionId: String,
    val studentId: String,
    val studentName: String,
    val classAndSection: String,
    val vehicleNumber: String,
    val routeNumber: String,
    val paymentMonth: String,
    val amount: Double,
    val paymentMethod: String, // "UPI", "Google Pay", "PhonePe", "Paytm", "Card", "Net Banking"
    val dateAndTime: String,
    val status: String, // "Paid", "Pending", "Failed"
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val timestamp: String,
    val targetRole: String, // "All", "Parent", "Driver", "Cleaner"
    val targetUser: String? = null, // if specific user
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: String,
    val adminUser: String,
    val actionType: String, // "Create", "Update", "Delete", "Promote"
    val description: String,
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "terms_conditions")
data class TermsAndConditionsEntity(
    @PrimaryKey val id: Int = 1,
    val version: Int,
    val content: String,
    val effectiveDate: String,
    val lastUpdated: String,
    val companyId: String = "kgm-travels"
)

@Entity(tableName = "terms_acceptance")
data class TermsAcceptanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val versionAccepted: Int,
    val acceptedAt: String,
    val companyId: String = "kgm-travels"
)
