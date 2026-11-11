package com.example.ui

import com.example.util.AppIconManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.*
import com.example.viewmodel.*
import com.example.ui.theme.BorderColor
import org.json.JSONObject
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperAdminPanelScreen(viewModel: KgmViewModel) {
    // Role Security Guard: Strictly restrict access to Super Admin only
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val isSuperAdmin = viewModel.isSuperAdmin()

    if (!isSuperAdmin) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = "Access Denied",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        "Super Admin Access Required",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        "This section is strictly restricted. Only users with the Super Admin role have permission to customize the App Icon, system configurations, and branding.",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Button(
                        onClick = { viewModel.navigateBack() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Return to Dashboard")
                    }
                }
            }
        }
        return
    }

    // Flows from viewModel
    val business by viewModel.businessInfo.collectAsStateWithLifecycle()
    val schools by viewModel.schoolsList.collectAsStateWithLifecycle()
    val areas by viewModel.serviceAreas.collectAsStateWithLifecycle()
    val apartments by viewModel.apartments.collectAsStateWithLifecycle()
    val pickupJson by viewModel.pickupPointsJson.collectAsStateWithLifecycle()
    val dropJson by viewModel.dropPointsJson.collectAsStateWithLifecycle()
    val classes by viewModel.classesList.collectAsStateWithLifecycle()
    val sections by viewModel.sectionsList.collectAsStateWithLifecycle()
    val categories by viewModel.categoriesList.collectAsStateWithLifecycle()
    val fees by viewModel.feesConfig.collectAsStateWithLifecycle()
    val website by viewModel.websiteConfig.collectAsStateWithLifecycle()
    val appContent by viewModel.appContent.collectAsStateWithLifecycle()
    val settings by viewModel.appSettings.collectAsStateWithLifecycle()
    val parents by viewModel.allUsers.collectAsStateWithLifecycle()
    val drivers by viewModel.allDrivers.collectAsStateWithLifecycle()
    val cleaners by viewModel.allCleaners.collectAsStateWithLifecycle()
    val currentTermsState by viewModel.currentTerms.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val logoGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        if (uri != null) viewModel.applyLogoFromUri(context, uri)
    }

    val logoFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: android.net.Uri? ->
        if (uri != null) viewModel.applyLogoFromUri(context, uri)
    }

    var showTermsEditDialog by remember { mutableStateOf(false) }
    var termsEditText by remember { mutableStateOf("") }
    var termsEditMajor by remember { mutableStateOf(false) }

    var activeTab by remember { mutableStateOf(0) }
    val tabs = listOf(
        "Business" to Icons.Filled.Business,
        "Choose App Icon" to Icons.Filled.Palette,
        "Schools & AY" to Icons.Filled.School,
        "Locations" to Icons.Filled.Place,
        "Academics" to Icons.Filled.Book,
        "Parents" to Icons.Filled.People,
        "Staff Ops" to Icons.Filled.Build,
        "Site & Settings" to Icons.Filled.Settings,
        "SaaS Tenants" to Icons.Filled.Cloud
    )

    // Pre-parse complex JSONs to comply with Compose try-catch-composable rules
    val parsedPickupPoints = remember(pickupJson) {
        val list = mutableListOf<Map<String, Any>>()
        try {
            val arr = JSONArray(pickupJson)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val map = mutableMapOf<String, Any>()
                map["id"] = obj.optString("id", "")
                map["name"] = obj.optString("name", "")
                map["route"] = obj.optString("route", "")
                map["time"] = obj.optString("time", "")
                map["order"] = obj.optInt("order", 1)
                map["students"] = obj.optString("students", "")
                list.add(map)
            }
        } catch (e: Exception) {}
        list
    }

    val parsedDropPoints = remember(dropJson) {
        val list = mutableListOf<Map<String, String>>()
        try {
            val arr = JSONArray(dropJson)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(mapOf(
                    "id" to obj.optString("id", ""),
                    "name" to obj.optString("name", ""),
                    "route" to obj.optString("route", ""),
                    "time" to obj.optString("time", "")
                ))
            }
        } catch (e: Exception) {}
        list
    }

    // Editing Dialog states
    var showEditDialog by remember { mutableStateOf(false) }
    var editDialogTitle by remember { mutableStateOf("") }
    var editDialogKey by remember { mutableStateOf("") }
    var editDialogValue by remember { mutableStateOf("") }
    var editDialogType by remember { mutableStateOf("") } // "business", "fees", "website", "app_content", "settings"

    // Parent dialog states
    var showParentDialog by remember { mutableStateOf(false) }
    var parentUsername by remember { mutableStateOf("") }
    var parentName by remember { mutableStateOf("") }
    var parentEmail by remember { mutableStateOf("") }
    var parentMobile by remember { mutableStateOf("") }
    var parentPassword by remember { mutableStateOf("") }
    var parentStudentId by remember { mutableStateOf("") }
    var parentEditMode by remember { mutableStateOf(false) }

    // PickupPoint dialog states
    var showPickupDialog by remember { mutableStateOf(false) }
    var ppId by remember { mutableStateOf("") }
    var ppName by remember { mutableStateOf("") }
    var ppRoute by remember { mutableStateOf("") }
    var ppTime by remember { mutableStateOf("") }
    var ppOrder by remember { mutableStateOf("") }
    var ppStudents by remember { mutableStateOf("") }
    var ppIsEdit by remember { mutableStateOf(false) }

    // DropPoint dialog states
    var showDropDialog by remember { mutableStateOf(false) }
    var dpId by remember { mutableStateOf("") }
    var dpName by remember { mutableStateOf("") }
    var dpRoute by remember { mutableStateOf("") }
    var dpTime by remember { mutableStateOf("") }
    var dpIsEdit by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Super Admin Control Panel",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Total system administration with immediate, zero-code updates.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Horizontal scrollable tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEachIndexed { index, (label, icon) ->
                val isSelected = activeTab == index
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .clickable { activeTab = index }
                        .testTag("admin_tab_$index")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(icon, contentDescription = label, modifier = Modifier.size(16.dp))
                        Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (activeTab) {
                0 -> {
                    // Business Section
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        item {
                            Text("Business Configuration", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Featured App Logo & Branding Management Card
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)),
                                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Filled.Image,
                                                contentDescription = "Logo Settings",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(24.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column {
                                                Text(
                                                    "App Branding & Logo",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    "Live updates across all user screens",
                                                    fontSize = 11.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }

                                        // Live preview of current logo
                                        Card(
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.size(52.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color.White),
                                            border = BorderStroke(1.dp, Color.LightGray)
                                        ) {
                                            AppLogoImage(
                                                logoUrl = business["logo"],
                                                contentDescription = "Live Logo Preview",
                                                modifier = Modifier.fillMaxSize().padding(4.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Gallery & Files Photo upload buttons
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = { logoGalleryLauncher.launch("image/*") },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                        ) {
                                            Icon(Icons.Filled.PhotoLibrary, contentDescription = "Gallery", modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Gallery / Photos", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        }

                                        Button(
                                            onClick = { logoFileLauncher.launch(arrayOf("image/*")) },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                        ) {
                                            Icon(Icons.Filled.FolderOpen, contentDescription = "Files", modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Browse Files", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Presets row
                                    Text("Select Preset Logo / Theme:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    val presets = listOf(
                                        "Default KGM Logo" to "ic_kgm",
                                        "Brand Emblem Crest" to "kgm_brand_logo_emblem",
                                        "School Bus Fleet" to "kgm_school_bus_fleet",
                                        "Bus Interior" to "kgm_bus_interior",
                                        "Transit Crest" to "https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=400&q=80",
                                        "Yellow Fleet" to "https://images.unsplash.com/photo-1557223562-6c77ef16210f?w=400&q=80",
                                        "Safety Shield" to "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=400&q=80"
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        presets.forEach { (label, urlOrCode) ->
                                            val isSelected = (business["logo"] ?: "ic_kgm") == urlOrCode
                                            FilterChip(
                                                selected = isSelected,
                                                onClick = {
                                                    viewModel.updateBusinessInfo("logo", urlOrCode)
                                                    AppIconManager.setAppIcon(context, urlOrCode)
                                                    viewModel.showMessage("App logo updated to $label!")
                                                },
                                                label = { Text(label, fontSize = 11.sp) },
                                                leadingIcon = if (isSelected) {
                                                    { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                                } else null
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Custom URL / Base64 input
                                    var customLogoInput by remember(business["logo"]) { mutableStateOf(business["logo"] ?: "") }
                                    OutlinedTextField(
                                        value = customLogoInput,
                                        onValueChange = { customLogoInput = it },
                                        label = { Text("Custom Logo Image URL or Base64 String") },
                                        placeholder = { Text("https://example.com/logo.png") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        trailingIcon = {
                                            if (customLogoInput.isNotBlank()) {
                                                IconButton(onClick = { customLogoInput = "" }) {
                                                    Icon(Icons.Filled.Clear, contentDescription = "Clear")
                                                }
                                            }
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                viewModel.updateBusinessInfo("logo", "ic_kgm")
                                                AppIconManager.setAppIcon(context, "ic_kgm")
                                                viewModel.showMessage("App Logo reset to default!")
                                            },
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Reset to Default", fontSize = 11.sp)
                                        }

                                        Button(
                                            onClick = {
                                                val finalUrl = customLogoInput.trim().ifEmpty { "ic_kgm" }
                                                viewModel.updateBusinessInfo("logo", finalUrl)
                                                AppIconManager.setAppIcon(context, finalUrl)
                                                viewModel.showMessage("App Logo updated successfully across all screens!")
                                            },
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Save New App Logo", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Button(
                                        onClick = { activeTab = 1 },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
                                    ) {
                                        Icon(Icons.Filled.Palette, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Open WhatsApp+ App Icon Studio 🎨", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                        business.forEach { (k, v) ->
                            item {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    border = BorderStroke(1.dp, BorderColor)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(k.uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                            Text(v, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                        }
                                        IconButton(onClick = {
                                            editDialogTitle = "Edit Business Detail"
                                            editDialogKey = k
                                            editDialogValue = v
                                            editDialogType = "business"
                                            showEditDialog = true
                                        }, modifier = Modifier.testTag("edit_business_$k")) {
                                            Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // WhatsApp Plus App Icon Studio (Super Admin Exclusive)
                    AppIconStudioScreen(viewModel)
                }
                2 -> {
                    // Schools & Academic Years
                    var newSchoolName by remember { mutableStateOf("") }
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        item {
                            Text("Manage Schools / Campuses", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                        items(schools) { sch ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                border = BorderStroke(1.dp, BorderColor)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.School, contentDescription = "School", tint = MaterialTheme.colorScheme.primary)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(sch, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    }
                                    IconButton(onClick = { viewModel.deleteSchool(sch) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = newSchoolName,
                                    onValueChange = { newSchoolName = it },
                                    label = { Text("New School Name") },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                Button(
                                    onClick = {
                                        if (newSchoolName.isNotBlank()) {
                                            viewModel.addSchool(newSchoolName)
                                            newSchoolName = ""
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Filled.Add, contentDescription = "Add")
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Academic Years & Promotions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Active Academic Year: AY 2026-27", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("Status: Running | Locked: No", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                viewModel.showMessage("Successfully promoted all active students to next Academic Year!")
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Promote Students", fontSize = 11.sp)
                                        }
                                        Button(
                                            onClick = {
                                                viewModel.showMessage("AY 2026-27 has been safely archived.")
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Archive AY", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                3 -> {
                    // Locations & Points
                    var newAreaName by remember { mutableStateOf("") }
                    var newApartmentName by remember { mutableStateOf("") }
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        item {
                            Text("Service Areas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        items(areas) { area ->
                            Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(area, fontWeight = FontWeight.Medium)
                                    IconButton(onClick = { viewModel.deleteServiceArea(area) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = newAreaName,
                                    onValueChange = { newAreaName = it },
                                    label = { Text("Add Area") },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                Button(
                                    onClick = {
                                        if (newAreaName.isNotBlank()) {
                                            viewModel.addServiceArea(newAreaName)
                                            newAreaName = ""
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Filled.Add, contentDescription = "Add")
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Apartments", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        items(apartments) { apt ->
                            Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(apt, fontWeight = FontWeight.Medium)
                                    IconButton(onClick = { viewModel.deleteApartment(apt) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = newApartmentName,
                                    onValueChange = { newApartmentName = it },
                                    label = { Text("Add Apartment") },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                Button(
                                    onClick = {
                                        if (newApartmentName.isNotBlank()) {
                                            viewModel.addApartment(newApartmentName)
                                            newApartmentName = ""
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Filled.Add, contentDescription = "Add")
                                }
                            }
                        }

                        // Pickup and Drop Points Tables
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Pickup Points Manager", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Button(onClick = {
                                    ppId = "PP" + (100..999).random()
                                    ppName = ""
                                    ppRoute = "R-101"
                                    ppTime = "08:00 AM"
                                    ppOrder = "1"
                                    ppStudents = ""
                                    ppIsEdit = false
                                    showPickupDialog = true
                                }) {
                                    Icon(Icons.Filled.AddLocation, contentDescription = "Add")
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Point", fontSize = 11.sp)
                                }
                            }
                        }
                        items(parsedPickupPoints) { map ->
                            val id = map["id"]?.toString() ?: ""
                            val name = map["name"]?.toString() ?: ""
                            val route = map["route"]?.toString() ?: ""
                            val time = map["time"]?.toString() ?: ""
                            val order = (map["order"] as? Number)?.toInt() ?: 1
                            val studs = map["students"]?.toString() ?: ""
                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), border = BorderStroke(1.dp, BorderColor)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("ID: $id | Route: $route", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                                        Row {
                                            IconButton(onClick = {
                                                ppId = id
                                                ppName = name
                                                ppRoute = route
                                                ppTime = time
                                                ppOrder = order.toString()
                                                ppStudents = studs
                                                ppIsEdit = true
                                                showPickupDialog = true
                                            }) {
                                                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                            }
                                            IconButton(onClick = { viewModel.deletePickupPoint(id) }) {
                                                Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                            }
                                        }
                                    }
                                    Text("Name: $name", fontWeight = FontWeight.SemiBold)
                                    Text("Schedule: $time (Order: $order)", fontSize = 12.sp)
                                    if (studs.isNotBlank()) {
                                        Text("Students: $studs", fontSize = 11.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }

                        // Drop point header
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Drop Points Manager", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Button(onClick = {
                                    dpId = "DP" + (100..999).random()
                                    dpName = ""
                                    dpRoute = "R-101"
                                    dpTime = "04:00 PM"
                                    dpIsEdit = false
                                    showDropDialog = true
                                }) {
                                    Icon(Icons.Filled.AddLocation, contentDescription = "Add")
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Point", fontSize = 11.sp)
                                }
                            }
                        }
                        items(parsedDropPoints) { map ->
                            val id = map["id"] ?: ""
                            val name = map["name"] ?: ""
                            val route = map["route"] ?: ""
                            val time = map["time"] ?: ""
                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), border = BorderStroke(1.dp, BorderColor)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("ID: $id | Route: $route", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                                        Row {
                                            IconButton(onClick = {
                                                dpId = id
                                                dpName = name
                                                dpRoute = route
                                                dpTime = time
                                                dpIsEdit = true
                                                showDropDialog = true
                                            }) {
                                                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                            }
                                            IconButton(onClick = { viewModel.deleteDropPoint(id) }) {
                                                Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                            }
                                        }
                                    }
                                    Text("Name: $name", fontWeight = FontWeight.SemiBold)
                                    Text("Schedule: $time", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
                4 -> {
                    // Academics & Fees
                    var newClassName by remember { mutableStateOf("") }
                    var newSectionName by remember { mutableStateOf("") }
                    var newCategoryName by remember { mutableStateOf("") }
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        item {
                            Text("Academics Configurations", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        // Classes
                        item { Text("Classes", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp) }
                        items(classes) { cl ->
                            Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(cl, modifier = Modifier.weight(1f).padding(start = 8.dp))
                                    IconButton(onClick = { viewModel.deleteClass(cl) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = newClassName, onValueChange = { newClassName = it }, label = { Text("New Class") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp))
                                Button(onClick = { if(newClassName.isNotBlank()){viewModel.addClass(newClassName); newClassName=""} }, shape = RoundedCornerShape(8.dp)) { Icon(Icons.Filled.Add, "") }
                            }
                        }

                        // Sections
                        item { Text("Sections", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp) }
                        items(sections) { sec ->
                            Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(sec, modifier = Modifier.weight(1f).padding(start = 8.dp))
                                    IconButton(onClick = { viewModel.deleteSection(sec) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = newSectionName, onValueChange = { newSectionName = it }, label = { Text("New Section") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp))
                                Button(onClick = { if(newSectionName.isNotBlank()){viewModel.addSection(newSectionName); newSectionName=""} }, shape = RoundedCornerShape(8.dp)) { Icon(Icons.Filled.Add, "") }
                            }
                        }

                        // Categories
                        item { Text("Student Categories", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp) }
                        items(categories) { cat ->
                            Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(cat, modifier = Modifier.weight(1f).padding(start = 8.dp))
                                    IconButton(onClick = { viewModel.deleteCategory(cat) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = newCategoryName, onValueChange = { newCategoryName = it }, label = { Text("New Category") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp))
                                Button(onClick = { if(newCategoryName.isNotBlank()){viewModel.addCategory(newCategoryName); newCategoryName=""} }, shape = RoundedCornerShape(8.dp)) { Icon(Icons.Filled.Add, "") }
                            }
                        }

                        // Fees
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Fees & Sibling Discount Structures", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        fees.forEach { (k, v) ->
                            item {
                                Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(k.replace("_", " ").uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                            Text(v, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                        }
                                        IconButton(onClick = {
                                            editDialogTitle = "Edit Fee Configuration"
                                            editDialogKey = k
                                            editDialogValue = v
                                            editDialogType = "fees"
                                            showEditDialog = true
                                        }) {
                                            Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                5 -> {
                    // Parents Section
                    val parentList = parents.filter { it.role.equals("Parent", ignoreCase = true) }
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text("Parents Accounts Manager", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Button(onClick = {
                                    parentUsername = ""
                                    parentName = ""
                                    parentEmail = ""
                                    parentMobile = ""
                                    parentPassword = ""
                                    parentStudentId = ""
                                    parentEditMode = false
                                    showParentDialog = true
                                }) {
                                    Icon(Icons.Filled.PersonAdd, "")
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Parent", fontSize = 11.sp)
                                }
                            }
                        }
                        items(parentList) { p ->
                            Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Text(p.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Row {
                                            IconButton(onClick = {
                                                parentUsername = p.username
                                                parentName = p.name
                                                parentEmail = p.email
                                                parentMobile = p.mobileNumber
                                                parentPassword = p.passwordHash
                                                parentStudentId = p.linkedStudentId ?: ""
                                                parentEditMode = true
                                                showParentDialog = true
                                            }) { Icon(Icons.Filled.Edit, "", tint = MaterialTheme.colorScheme.primary) }
                                            IconButton(onClick = { viewModel.deleteParent(p.username) }) { Icon(Icons.Filled.Delete, "", tint = Color.Red) }
                                        }
                                    }
                                    Text("Username: ${p.username}", fontSize = 12.sp, color = Color.Gray)
                                    Text("Mobile: ${p.mobileNumber} | Email: ${p.email}", fontSize = 12.sp)
                                    p.linkedStudentId?.let {
                                        Text("Linked Student ID: $it", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                    }
                }
                6 -> {
                    // Staff Ops Section
                    var selectedStaffType by remember { mutableStateOf("Driver") }
                    var selectedStaffId by remember { mutableStateOf("") }
                    var staffSectionTab by remember { mutableStateOf(0) }

                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { selectedStaffType = "Driver"; selectedStaffId = "" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = if (selectedStaffType == "Driver") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                            ) { Text("Drivers", color = if (selectedStaffType == "Driver") Color.White else MaterialTheme.colorScheme.onSurfaceVariant) }
                            Button(
                                onClick = { selectedStaffType = "Cleaner"; selectedStaffId = "" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = if (selectedStaffType == "Cleaner") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                            ) { Text("Cleaners", color = if (selectedStaffType == "Cleaner") Color.White else MaterialTheme.colorScheme.onSurfaceVariant) }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        val staffList = if (selectedStaffType == "Driver") {
                            drivers.map { it.driverName to it.driverName }
                        } else {
                            cleaners.map { it.name to it.name }
                        }

                        if (selectedStaffId.isEmpty() && staffList.isNotEmpty()) {
                            selectedStaffId = staffList.firstOrNull()?.first ?: ""
                        }

                        if (staffList.isEmpty()) {
                            Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                                Text("No staff members available.", color = Color.Gray)
                            }
                        } else {
                            // Staff drop-down selector
                            var expanded by remember { mutableStateOf(false) }
                            val currentStaffName = staffList.find { it.first == selectedStaffId }?.second ?: ""
                            Box(modifier = Modifier.fillMaxWidth()) {
                                OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                        Text("Selected Staff: $currentStaffName", fontWeight = FontWeight.Bold)
                                        Icon(Icons.Filled.ArrowDropDown, "")
                                    }
                                }
                                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    staffList.forEach { (usr, name) ->
                                        DropdownMenuItem(text = { Text(name) }, onClick = { selectedStaffId = usr; expanded = false })
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            // Sub tabs for operations
                            TabRow(selectedTabIndex = staffSectionTab) {
                                Tab(selected = staffSectionTab == 0, onClick = { staffSectionTab = 0 }, text = { Text("Documents", fontSize = 11.sp) })
                                Tab(selected = staffSectionTab == 1, onClick = { staffSectionTab = 1 }, text = { Text("Attendance", fontSize = 11.sp) })
                                Tab(selected = staffSectionTab == 2, onClick = { staffSectionTab = 2 }, text = { Text("Salary logs", fontSize = 11.sp) })
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            when (staffSectionTab) {
                                0 -> {
                                    // Documents Upload and Storage
                                    val docsJson = viewModel.documentStore.collectAsStateWithLifecycle().value[selectedStaffId] ?: "[]"
                                    var docTypeInput by remember { mutableStateOf("Licence Copy") }
                                    var docNameInput by remember { mutableStateOf("TN_License_Ramesh.pdf") }

                                    val documentList = remember(docsJson) {
                                        val list = mutableListOf<Triple<String, String, String>>()
                                        try {
                                            val arr = JSONArray(docsJson)
                                            for (i in 0 until arr.length()) {
                                                val entry = arr.getJSONObject(i)
                                                list.add(Triple(
                                                    entry.optString("type", ""),
                                                    entry.optString("name", ""),
                                                    entry.optString("timestamp", "")
                                                ))
                                            }
                                        } catch (e: Exception) {}
                                        list
                                    }

                                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                        Text("Upload & Maintain Staff Documents", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Spacer(modifier = Modifier.height(12.dp))

                                        OutlinedTextField(value = docTypeInput, onValueChange = { docTypeInput = it }, label = { Text("Document Type (e.g. Licence, Aadhaar, Insurance)") }, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedTextField(value = docNameInput, onValueChange = { docNameInput = it }, label = { Text("File Name") }, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(12.dp))

                                        Button(onClick = {
                                            if (docTypeInput.isNotBlank() && docNameInput.isNotBlank()) {
                                                viewModel.uploadDocument(selectedStaffId, docTypeInput, docNameInput)
                                                viewModel.showMessage("Document uploaded successfully!")
                                            }
                                        }, modifier = Modifier.fillMaxWidth()) {
                                            Icon(Icons.Filled.CloudUpload, "")
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Upload Document File")
                                        }

                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text("Uploaded Documents List", fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(8.dp))

                                        if (documentList.isEmpty()) {
                                            Text("No documents uploaded yet.", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                                        } else {
                                            documentList.forEach { (type, name, timestamp) ->
                                                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), border = BorderStroke(1.dp, BorderColor)) {
                                                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                                        Column {
                                                            Text(type, fontWeight = FontWeight.Bold)
                                                            Text(name, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                                            Text("Uploaded: $timestamp", fontSize = 10.sp, color = Color.Gray)
                                                        }
                                                        Icon(Icons.Filled.AttachFile, "", tint = Color.Gray)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                1 -> {
                                    // Attendance marking
                                    val attStore = viewModel.attendanceStore.collectAsStateWithLifecycle().value
                                    val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                    var pickedDate by remember { mutableStateOf(dateStr) }

                                    val currentStatus = remember(attStore, pickedDate, selectedStaffId) {
                                        try {
                                            val todayAttendanceObj = JSONObject(attStore[pickedDate] ?: "{}")
                                            todayAttendanceObj.optString(selectedStaffId, "Not Marked")
                                        } catch (e: Exception) {
                                            "Not Marked"
                                        }
                                    }

                                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                        Text("Duty Attendance System", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Spacer(modifier = Modifier.height(12.dp))

                                        OutlinedTextField(value = pickedDate, onValueChange = { pickedDate = it }, label = { Text("Date (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(16.dp))

                                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                                            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text("Current Status on $pickedDate", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                                Text(currentStatus.uppercase(), fontSize = 24.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text("Mark Duty Status:", fontWeight = FontWeight.SemiBold)
                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                            Button(
                                                onClick = { viewModel.markAttendance(selectedStaffId, pickedDate, "Present") },
                                                modifier = Modifier.weight(1f),
                                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                                            ) { Text("Present", color = Color.White) }
                                            Button(
                                                onClick = { viewModel.markAttendance(selectedStaffId, pickedDate, "Absent") },
                                                modifier = Modifier.weight(1f),
                                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                                            ) { Text("Absent", color = Color.White) }
                                            Button(
                                                onClick = { viewModel.markAttendance(selectedStaffId, pickedDate, "On Leave") },
                                                modifier = Modifier.weight(1f),
                                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                                            ) { Text("Leave", color = Color.White) }
                                        }
                                    }
                                }
                                2 -> {
                                    // Salaries Logging
                                    val salariesJson = viewModel.salaryStore.collectAsStateWithLifecycle().value[selectedStaffId] ?: "[]"
                                    var monthInput by remember { mutableStateOf("July 2026") }
                                    var amountInput by remember { mutableStateOf("15000.00") }
                                    var methodInput by remember { mutableStateOf("Bank Transfer") }
                                    var remarksInput by remember { mutableStateOf("Monthly base salary paid.") }

                                    val salaryList = remember(salariesJson) {
                                        val list = mutableListOf<Map<String, Any>>()
                                        try {
                                            val arr = JSONArray(salariesJson)
                                            for (i in 0 until arr.length()) {
                                                val entry = arr.getJSONObject(i)
                                                list.add(mapOf(
                                                    "month" to entry.optString("month", ""),
                                                    "amount" to entry.optDouble("amount", 0.0),
                                                    "method" to entry.optString("method", ""),
                                                    "remarks" to entry.optString("remarks", ""),
                                                    "timestamp" to entry.optString("timestamp", "")
                                                ))
                                            }
                                        } catch (e: Exception) {}
                                        list
                                    }

                                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                        Text("Salary & Payroll Logging", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Spacer(modifier = Modifier.height(12.dp))

                                        OutlinedTextField(value = monthInput, onValueChange = { monthInput = it }, label = { Text("Payroll Month") }, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedTextField(value = amountInput, onValueChange = { amountInput = it }, label = { Text("Salary Amount Paid (INR)") }, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedTextField(value = methodInput, onValueChange = { methodInput = it }, label = { Text("Payment Method (e.g. Bank Transfer, UPI)") }, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedTextField(value = remarksInput, onValueChange = { remarksInput = it }, label = { Text("Payment Notes / Remarks") }, modifier = Modifier.fillMaxWidth())
                                        Spacer(modifier = Modifier.height(12.dp))

                                        Button(onClick = {
                                            val amt = amountInput.toDoubleOrNull() ?: 0.0
                                            if (monthInput.isNotBlank() && amt > 0.0) {
                                                viewModel.logSalary(selectedStaffId, monthInput, amt, methodInput, remarksInput)
                                                viewModel.showMessage("Salary payment successfully logged!")
                                            }
                                        }, modifier = Modifier.fillMaxWidth()) {
                                            Icon(Icons.Filled.Payments, "")
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Log Salary Payment")
                                        }

                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text("Salary Disbursement Logs", fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(8.dp))

                                        if (salaryList.isEmpty()) {
                                            Text("No payments logged yet.", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                                        } else {
                                            salaryList.forEach { entry ->
                                                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), border = BorderStroke(1.dp, BorderColor)) {
                                                    Column(modifier = Modifier.padding(12.dp)) {
                                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                            Text(entry["month"]?.toString() ?: "", fontWeight = FontWeight.Bold)
                                                            Text("₹${(entry["amount"] as? Number)?.toDouble() ?: 0.0}", fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                                                        }
                                                        Text("Method: ${entry["method"]?.toString() ?: ""}", fontSize = 12.sp)
                                                        Text("Notes: ${entry["remarks"]?.toString() ?: ""}", fontSize = 11.sp, color = Color.Gray)
                                                        Text("Logged: ${entry["timestamp"]?.toString() ?: ""}", fontSize = 10.sp, color = Color.Gray)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                7 -> {
                    // Site, SEO & System Settings Configuration
                    val seoItems = website.filterKeys { it.startsWith("seo_") }
                    val contentItems = website.filterKeys { !it.startsWith("seo_") }

                    val metaTitle = website["seo_meta_title"] ?: "KGM Travels - School Bus & Student Transport Portal"
                    val metaDesc = website["seo_meta_description"] ?: "Safe, GPS-tracked school bus transport for Velammal Vidyalaya students with live route tracking, online fee payments, and verified staff attendants."
                    val canonicalUrl = website["seo_canonical_url"] ?: "https://www.kgmtravels.com"
                    val keywords = website["seo_keywords"] ?: "school bus, student transport, Velammal Vidyalaya"
                    val ogImage = website["seo_og_image"] ?: "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957"

                    val isTitleOptimal = metaTitle.length in 30..65
                    val isDescOptimal = metaDesc.length in 80..170
                    val hasKeywords = keywords.isNotBlank()
                    val hasCanonical = canonicalUrl.startsWith("http")

                    val score = listOf(isTitleOptimal, isDescOptimal, hasKeywords, hasCanonical).count { it } * 25

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // SEO & Search Engine Optimization Engine Card
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)),
                                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(38.dp)
                                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(Icons.Filled.TravelExplore, contentDescription = "SEO", tint = Color.White, modifier = Modifier.size(22.dp))
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text("Search Engine Optimization (SEO)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                                Text("Meta tags, Google snippet, Open Graph & sitemap", fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }

                                        // SEO Health Score Badge
                                        Surface(
                                            color = if (score >= 75) Color(0xFF10B981) else Color(0xFFF59E0B),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(
                                                text = "SEO Score: $score%",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Google Search Live Preview Box
                                    Text("Google Search Result Preview", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                        .background(Color(0xFFE2E8F0), CircleShape),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(Icons.Filled.Public, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                                                }
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "$canonicalUrl › transport › velammal",
                                                    fontSize = 11.sp,
                                                    color = Color(0xFF475569)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = metaTitle,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1A0DAB)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = metaDesc,
                                                fontSize = 12.sp,
                                                color = Color(0xFF4D5156),
                                                lineHeight = 16.sp,
                                                maxLines = 3
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Action buttons for SEO
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                viewModel.updateBulkWebsiteConfig(
                                                    mapOf(
                                                        "seo_meta_title" to "KGM Travels - School Bus & Student Transport Portal",
                                                        "seo_meta_description" to "Safe, GPS-tracked school bus transport for Velammal Vidyalaya students with live route tracking, online fee payments, and verified staff attendants.",
                                                        "seo_keywords" to "KGM Travels, Velammal Vidyalaya school bus, student transport, GPS bus tracking Chennai, Mel Ayanambakkam school transit, school fee payment",
                                                        "seo_canonical_url" to "https://www.kgmtravels.com",
                                                        "seo_og_image" to "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957",
                                                        "seo_robots_index" to "index, follow",
                                                        "seo_author" to "KGM Travels Transport Division",
                                                        "seo_sitemap" to "https://www.kgmtravels.com/sitemap.xml"
                                                    )
                                                )
                                                viewModel.showMessage("Optimized SEO best-practice presets applied successfully!")
                                            },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                        ) {
                                            Icon(Icons.Filled.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Auto-Optimize SEO", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Text("SEO Parameters & Meta Directives", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        }

                        // SEO Tags List
                        seoItems.forEach { (k, v) ->
                            item {
                                Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = k.removePrefix("seo_").replace("_", " ").uppercase(),
                                                fontSize = 10.sp,
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(v, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                        }
                                        IconButton(onClick = {
                                            editDialogTitle = "Edit SEO Tag (${k.removePrefix("seo_")})"
                                            editDialogKey = k
                                            editDialogValue = v
                                            editDialogType = "website"
                                            showEditDialog = true
                                        }) { Icon(Icons.Filled.Edit, "Edit SEO Tag", tint = MaterialTheme.colorScheme.primary) }
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Website Content Configuration", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }

                        contentItems.forEach { (k, v) ->
                            item {
                                Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(k.uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                            Text(v, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                        }
                                        IconButton(onClick = {
                                            editDialogTitle = "Edit Website Content"
                                            editDialogKey = k
                                            editDialogValue = v
                                            editDialogType = "website"
                                            showEditDialog = true
                                        }) { Icon(Icons.Filled.Edit, "", tint = MaterialTheme.colorScheme.primary) }
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("App Content Notifications", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        appContent.forEach { (k, v) ->
                            item {
                                Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(k.replace("_", " ").uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                            Text(v, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                        }
                                        IconButton(onClick = {
                                            editDialogTitle = "Edit Dashboard Announcement"
                                            editDialogKey = k
                                            editDialogValue = v
                                            editDialogType = "app_content"
                                            showEditDialog = true
                                        }) { Icon(Icons.Filled.Edit, "", tint = MaterialTheme.colorScheme.primary) }
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("System Core Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        settings.forEach { (k, v) ->
                            item {
                                Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, BorderColor)) {
                                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(k.replace("_", " ").uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                            Text(v, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                        }
                                        IconButton(onClick = {
                                            editDialogTitle = "Configure System Setting"
                                            editDialogKey = k
                                            editDialogValue = v
                                            editDialogType = "settings"
                                            showEditDialog = true
                                        }) { Icon(Icons.Filled.Edit, "", tint = MaterialTheme.colorScheme.primary) }
                                    }
                                }
                            }
                        }

                        // Terms & Conditions / Transport Rules Admin Card
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Terms & Conditions & Transport Rules", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                border = BorderStroke(1.dp, BorderColor)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("Current Policy Version: v${currentTermsState?.version ?: 1}", fontWeight = FontWeight.Bold)
                                            Text("Effective Date: ${currentTermsState?.effectiveDate ?: "N/A"}", fontSize = 12.sp, color = Color.Gray)
                                            Text("Last Updated: ${currentTermsState?.lastUpdated ?: "N/A"}", fontSize = 11.sp, color = Color.Gray)
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(
                                            onClick = {
                                                termsEditText = currentTermsState?.content ?: ""
                                                termsEditMajor = false
                                                showTermsEditDialog = true
                                            },
                                            modifier = Modifier.testTag("admin_edit_terms_btn")
                                        ) {
                                            Icon(Icons.Filled.Edit, "")
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Edit Rules")
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Modify KGM transport clauses, policies, security parameters, and require parents to accept updated version upon their next login dynamically.",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        // Backup & Restore Block
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Database Administration Tools", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                                    Text("Backup all local parameters, schemas, parent info, and driver/cleaner structures into persistent offline storage.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f))
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                viewModel.saveConfigs()
                                                viewModel.showMessage("Offline backup saved securely into system directory.")
                                            },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Filled.Backup, "")
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Backup DB", fontSize = 11.sp)
                                        }
                                        Button(
                                            onClick = {
                                                viewModel.loadConfigs()
                                                viewModel.showMessage("Configs loaded and restored successfully!")
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Filled.Restore, "")
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Restore Configs", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                8 -> {
                    SaaSCompaniesTab(viewModel)
                }
            }
        }
    }

    // Edit Key-Value Dialog
    if (showEditDialog) {
        Dialog(onDismissRequest = { showEditDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(editDialogTitle, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("Field: ${editDialogKey.uppercase()}", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)

                    OutlinedTextField(
                        value = editDialogValue,
                        onValueChange = { editDialogValue = it },
                        label = { Text("Value") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showEditDialog = false }) { Text("Cancel") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                when (editDialogType) {
                                    "business" -> viewModel.updateBusinessInfo(editDialogKey, editDialogValue)
                                    "fees" -> viewModel.updateFeeConfig(editDialogKey, editDialogValue)
                                    "website" -> viewModel.updateWebsiteConfig(editDialogKey, editDialogValue)
                                    "app_content" -> viewModel.updateAppContent(editDialogKey, editDialogValue)
                                    "settings" -> viewModel.updateAppSettings(editDialogKey, editDialogValue)
                                }
                                showEditDialog = false
                                viewModel.showMessage("Setting updated successfully!")
                             },
                             modifier = Modifier.testTag("dialog_save_btn")
                         ) { Text("Save Changes") }
                     }
                 }
             }
         }
     }

    // Terms Edit Dialog
    if (showTermsEditDialog) {
        Dialog(
            onDismissRequest = { showTermsEditDialog = false },
            properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.85f)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Edit Terms & Conditions & Transport Rules", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    
                    Text("These regulations are parsed dynamically into collapsible M3 cards for parents.", fontSize = 11.sp, color = Color.Gray)
                    
                    OutlinedTextField(
                        value = termsEditText,
                        onValueChange = { termsEditText = it },
                        label = { Text("Terms & Conditions Text") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        maxLines = 100
                    )
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { termsEditMajor = !termsEditMajor }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = termsEditMajor,
                            onCheckedChange = { termsEditMajor = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Major Revision (Increment Version & Alert Parents)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                            Text("Forces all parents to re-accept these rules upon their next login.", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showTermsEditDialog = false }) { Text("Cancel") }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(
                            onClick = {
                                viewModel.updateTermsAndConditions(termsEditText, termsEditMajor)
                                showTermsEditDialog = false
                            },
                            modifier = Modifier.testTag("terms_save_button")
                        ) {
                            Text("Publish Updates")
                        }
                    }
                }
            }
        }
    }

    // Parent CRUD Dialog
    if (showParentDialog) {
        Dialog(onDismissRequest = { showParentDialog = false }) {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(if (parentEditMode) "Edit Parent Account" else "Add Parent Account", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(value = parentUsername, onValueChange = { if(!parentEditMode) parentUsername = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth(), enabled = !parentEditMode)
                    OutlinedTextField(value = parentName, onValueChange = { parentName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = parentEmail, onValueChange = { parentEmail = it }, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = parentMobile, onValueChange = { parentMobile = it }, label = { Text("Mobile Number") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = parentPassword, onValueChange = { parentPassword = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = parentStudentId, onValueChange = { parentStudentId = it }, label = { Text("Linked Student ID") }, modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showParentDialog = false }) { Text("Cancel") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (parentUsername.isNotBlank() && parentName.isNotBlank() && parentMobile.isNotBlank()) {
                                viewModel.saveParent(parentUsername, parentName, parentEmail, parentMobile, parentPassword, parentStudentId.ifBlank { null })
                                showParentDialog = false
                                viewModel.showMessage("Parent account saved successfully!")
                            }
                        }) { Text("Save") }
                    }
                }
            }
        }
    }

    // PickupPoint CRUD Dialog
    if (showPickupDialog) {
        Dialog(onDismissRequest = { showPickupDialog = false }) {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(if (ppIsEdit) "Edit Pickup Point" else "Add Pickup Point", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(value = ppId, onValueChange = { if(!ppIsEdit) ppId = it }, label = { Text("Point ID") }, modifier = Modifier.fillMaxWidth(), enabled = !ppIsEdit)
                    OutlinedTextField(value = ppName, onValueChange = { ppName = it }, label = { Text("Location Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = ppRoute, onValueChange = { ppRoute = it }, label = { Text("Assigned Route") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = ppTime, onValueChange = { ppTime = it }, label = { Text("Schedule Time") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = ppOrder, onValueChange = { ppOrder = it }, label = { Text("Route Stop Order") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = ppStudents, onValueChange = { ppStudents = it }, label = { Text("Assigned Students (Comma-separated)") }, modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showPickupDialog = false }) { Text("Cancel") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            val ord = ppOrder.toIntOrNull() ?: 1
                            if (ppId.isNotBlank() && ppName.isNotBlank()) {
                                viewModel.addOrUpdatePickupPoint(ppId, ppName, ppRoute, ppTime, ord, ppStudents)
                                showPickupDialog = false
                                viewModel.showMessage("Pickup point saved!")
                            }
                        }) { Text("Save") }
                    }
                }
            }
        }
    }

    // DropPoint CRUD Dialog
    if (showDropDialog) {
        Dialog(onDismissRequest = { showDropDialog = false }) {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(if (dpIsEdit) "Edit Drop Point" else "Add Drop Point", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(value = dpId, onValueChange = { if(!dpIsEdit) dpId = it }, label = { Text("Point ID") }, modifier = Modifier.fillMaxWidth(), enabled = !dpIsEdit)
                    OutlinedTextField(value = dpName, onValueChange = { dpName = it }, label = { Text("Location Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = dpRoute, onValueChange = { dpRoute = it }, label = { Text("Assigned Route") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = dpTime, onValueChange = { dpTime = it }, label = { Text("Schedule Time") }, modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showDropDialog = false }) { Text("Cancel") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (dpId.isNotBlank() && dpName.isNotBlank()) {
                                viewModel.addOrUpdateDropPoint(dpId, dpName, dpRoute, dpTime)
                                showDropDialog = false
                                viewModel.showMessage("Drop point saved!")
                            }
                        }) { Text("Save") }
                    }
                }
            }
        }
    }
}

@Composable
fun SaaSCompaniesTab(viewModel: KgmViewModel) {
    val companies by viewModel.allCompanies.collectAsStateWithLifecycle()
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val vehicles by viewModel.allVehicles.collectAsStateWithLifecycle()
    val transactions by viewModel.allTransactions.collectAsStateWithLifecycle()

    var showRegisterDialog by remember { mutableStateOf(false) }

    // Dialog state for new SaaS company
    var companyId by remember { mutableStateOf("") }
    var businessName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var businessAddress by remember { mutableStateOf("") }
    var gstNumber by remember { mutableStateOf("") }
    var schoolsServed by remember { mutableStateOf("") }
    var numVehicles by remember { mutableStateOf("0") }
    var numDrivers by remember { mutableStateOf("0") }
    var primaryColor by remember { mutableStateOf("#005CB9") }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Platform Analytics Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "KGM Travels SaaS Platform Analytics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("TOTAL COMPANIES", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text("${companies.size}", fontSize = 20.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                        }
                        Column {
                            Text("ACTIVE STUDENTS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text("${students.size}", fontSize = 20.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                        }
                        Column {
                            Text("ACTIVE VEHICLES", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text("${vehicles.size}", fontSize = 20.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                        }
                        Column {
                            Text("REVENUE COLLECTION", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text("₹${transactions.filter { it.status == "Paid" }.sumOf { it.amount }.toInt()}", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color(0xFF10B981))
                        }
                    }
                }
            }
        }

        // Platform Health & Server performance
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Platform Health & Infrastructure",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Storage, contentDescription = "Storage", tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Storage Capacity", fontSize = 10.sp, color = Color.Gray)
                                Text("4.2 GB / 50 GB", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                LinearProgressIndicator(progress = { 0.084f }, modifier = Modifier.padding(top = 4.dp).height(4.dp).fillMaxWidth())
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Memory, contentDescription = "CPU", tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Server CPU Load", fontSize = 10.sp, color = Color.Gray)
                                Text("12.4% Usage", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                LinearProgressIndicator(progress = { 0.124f }, modifier = Modifier.padding(top = 4.dp).height(4.dp).fillMaxWidth())
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.VerifiedUser, contentDescription = "Security", tint = Color(0xFF10B981))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("SSL & Encryption", fontSize = 10.sp, color = Color.Gray)
                                Text("256-Bit Active", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF10B981))
                            }
                        }
                    }
                }
            }
        }

        // Header + Register Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Registered SaaS Companies",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { showRegisterDialog = true },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Register Company")
                }
            }
        }

        // List of Companies
        if (companies.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No SaaS companies registered yet.", color = Color.Gray)
                }
            }
        } else {
            items(companies) { company ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color(try { company.primaryColor.toColorInt() } catch (e: Exception) { 0xFF005CB9.toInt() }), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = company.businessName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(
                                        when (company.status) {
                                            "Approved" -> Color(0xFFD1FAE5)
                                            "Pending" -> Color(0xFFFEF3C7)
                                            "Suspended" -> Color(0xFFFEE2E2)
                                            else -> Color.LightGray
                                        },
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = company.status,
                                    color = when (company.status) {
                                        "Approved" -> Color(0xFF065F46)
                                        "Pending" -> Color(0xFF92400E)
                                        "Suspended" -> Color(0xFF991B1B)
                                        else -> Color.DarkGray
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Company ID: ${company.companyId}", fontSize = 11.sp, color = Color.Gray)
                        Text("Owner: ${company.ownerName} | Contact: ${company.mobileNumber}", fontSize = 12.sp)
                        Text("Address: ${company.businessAddress}", fontSize = 12.sp)
                        if (company.gstNumber.isNotBlank()) {
                            Text("GSTIN: ${company.gstNumber}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Schools: ${company.schoolsServed.ifEmpty { "None" }}",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                modifier = Modifier.weight(1f)
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                if (company.status == "Pending" || company.status == "Suspended") {
                                    Button(
                                        onClick = { viewModel.updateCompanyStatus(company.companyId, "Approved") },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Approve / Activate", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }

                                if (company.status == "Approved") {
                                    OutlinedButton(
                                        onClick = { viewModel.updateCompanyStatus(company.companyId, "Suspended") },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                                        border = BorderStroke(1.dp, Color(0xFFEF4444)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Suspend", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Register Company Dialog
    if (showRegisterDialog) {
        Dialog(onDismissRequest = { showRegisterDialog = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Register SaaS Company",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(value = companyId, onValueChange = { companyId = it }, label = { Text("Company Unique ID (e.g., sriram)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = businessName, onValueChange = { businessName = it }, label = { Text("Business / Company Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = ownerName, onValueChange = { ownerName = it }, label = { Text("Owner / CEO Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = mobileNumber, onValueChange = { mobileNumber = it }, label = { Text("Mobile Number (Will be Username)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = emailAddress, onValueChange = { emailAddress = it }, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = businessAddress, onValueChange = { businessAddress = it }, label = { Text("Business Address") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = gstNumber, onValueChange = { gstNumber = it }, label = { Text("GSTIN Number") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = schoolsServed, onValueChange = { schoolsServed = it }, label = { Text("Schools Served") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = primaryColor, onValueChange = { primaryColor = it }, label = { Text("Brand Hex Code (e.g., #005CB9)") }, modifier = Modifier.fillMaxWidth())

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showRegisterDialog = false }) { Text("Cancel") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (companyId.isNotBlank() && businessName.isNotBlank() && mobileNumber.isNotBlank()) {
                                    viewModel.registerCompany(
                                        companyId = companyId.trim().lowercase(),
                                        businessName = businessName.trim(),
                                        ownerName = ownerName.trim(),
                                        mobileNumber = mobileNumber.trim(),
                                        emailAddress = emailAddress.trim(),
                                        businessAddress = businessAddress.trim(),
                                        gstNumber = gstNumber.trim(),
                                        schoolsServed = schoolsServed.trim(),
                                        primaryColor = primaryColor.trim()
                                    )
                                    showRegisterDialog = false
                                }
                            }
                        ) {
                            Text("Register Company")
                        }
                    }
                }
            }
        }
    }
}
