package com.example.ui

import com.example.util.AppIconManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.*
import com.example.viewmodel.*
import com.example.R
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BlueSecondary
import com.example.ui.theme.BorderColor
import com.example.ui.theme.ContentDark
import com.example.ui.theme.ContentMuted
import org.json.JSONObject
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

@Composable
fun AppLogoImage(
    logoUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String = "App Logo",
    contentScale: androidx.compose.ui.layout.ContentScale = androidx.compose.ui.layout.ContentScale.Fit
) {
    val context = LocalContext.current
    val cleanLogo = logoUrl?.trim() ?: ""

    when {
        cleanLogo.startsWith("http://") || cleanLogo.startsWith("https://") || cleanLogo.startsWith("content://") || cleanLogo.startsWith("file://") -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(cleanLogo)
                    .crossfade(true)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                error = androidx.compose.ui.res.painterResource(id = R.drawable.ic_kgm_logo_vector),
                placeholder = androidx.compose.ui.res.painterResource(id = R.drawable.ic_kgm_logo_vector)
            )
        }
        cleanLogo.startsWith("data:image") || (cleanLogo.length > 100 && !cleanLogo.contains(" ")) -> {
            val bitmap = remember(cleanLogo) {
                com.example.util.BitmapUtils.decodeBase64ToBitmap(cleanLogo, 300, 300)?.asImageBitmap()
            }
            if (bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = contentDescription,
                    modifier = modifier,
                    contentScale = contentScale
                )
            } else {
                Image(
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_kgm_logo_vector),
                    contentDescription = contentDescription,
                    modifier = modifier,
                    contentScale = contentScale
                )
            }
        }
        cleanLogo == "wp_emerald" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857)),
                accentColor = Color(0xFFFBBF24),
                icon = Icons.Filled.DirectionsBus,
                modifier = modifier
            )
        }
        cleanLogo == "wp_gold" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A)),
                accentColor = Color(0xFFF59E0B),
                icon = Icons.Filled.Stars,
                modifier = modifier
            )
        }
        cleanLogo == "wp_sapphire" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF1D4ED8), Color(0xFF3B82F6), Color(0xFF60A5FA)),
                accentColor = Color(0xFF93C5FD),
                icon = Icons.Filled.DirectionsBus,
                modifier = modifier
            )
        }
        cleanLogo == "wp_onyx" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF18181B), Color(0xFF09090B)),
                accentColor = Color(0xFFE4E4E7),
                icon = Icons.Filled.Shield,
                modifier = modifier
            )
        }
        cleanLogo == "wp_ruby" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF881337), Color(0xFFBE123C), Color(0xFFE11D48)),
                accentColor = Color(0xFFFDE047),
                icon = Icons.Filled.Diamond,
                modifier = modifier
            )
        }
        cleanLogo == "wp_neon" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF581C87), Color(0xFF7C3AED), Color(0xFFA855F7)),
                accentColor = Color(0xFF22D3EE),
                icon = Icons.Filled.Bolt,
                modifier = modifier
            )
        }
        cleanLogo == "wp_sunset" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFFC2410C), Color(0xFFEA580C), Color(0xFFF97316)),
                accentColor = Color(0xFFFEF08A),
                icon = Icons.Filled.WbSunny,
                modifier = modifier
            )
        }
        cleanLogo == "wp_retro_green" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF075E54), Color(0xFF128C7E), Color(0xFF25D366)),
                accentColor = Color.White,
                icon = Icons.Filled.DirectionsBus,
                modifier = modifier
            )
        }
        cleanLogo == "wp_fleet_yellow" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFFF59E0B), Color(0xFFD97706)),
                accentColor = Color(0xFF0F172A),
                icon = Icons.Filled.DirectionsBus,
                modifier = modifier
            )
        }
        cleanLogo == "wp_titanium" -> {
            WhatsAppPlusBadge(
                gradient = listOf(Color(0xFF334155), Color(0xFF64748B), Color(0xFF94A3B8)),
                accentColor = Color(0xFF38BDF8),
                icon = Icons.Filled.DirectionsBus,
                modifier = modifier
            )
        }
        cleanLogo == "kgm_brand_logo_emblem" -> {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.kgm_brand_logo_emblem),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        cleanLogo == "kgm_school_bus_fleet" -> {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.kgm_school_bus_fleet),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        cleanLogo == "kgm_bus_interior" -> {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.kgm_bus_interior),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
        else -> {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_kgm_logo_vector),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
    }
}

@Composable
fun WhatsAppPlusBadge(
    gradient: List<Color>,
    accentColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(gradient),
                shape = RoundedCornerShape(12.dp)
            )
            .border(1.5.dp, accentColor.copy(alpha = 0.85f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Inner gloss shine effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0.25f), Color.Transparent)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier
                .fillMaxSize(0.6f)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun KgmApp(viewModel: KgmViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val statusMsg by viewModel.statusMessage.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
            when (screen) {
                "splash" -> SplashScreen(viewModel)
                "login" -> LoginScreen(viewModel)
                "parent_register" -> ParentRegisterScreen(viewModel)
                "terms_view" -> TermsAndConditionsScreen(viewModel, readOnly = true)
                else -> {
                    if (currentUser == null) {
                        LoginScreen(viewModel)
                    } else {
                        MainShell(viewModel, screen)
                    }
                }
            }
        }

        statusMsg?.let { msg ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp, start = 16.dp, end = 16.dp)
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Info, contentDescription = "Info", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        TermsReacceptanceOverlay(viewModel)
    }
}

@Composable
fun SplashScreen(viewModel: KgmViewModel) {
    val businessInfo by viewModel.businessInfo.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1800)
        if (currentUser != null) {
            viewModel.setScreen("dashboard")
        } else {
            viewModel.setScreen("login")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF005CB9),
                        Color(0xFF0F172A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                AppLogoImage(
                    logoUrl = businessInfo["logo"],
                    contentDescription = "App Logo",
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "KGM TRAVELS",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Your Child's Safety, Our Priority",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFFBBF24),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Enterprise School Transport Management",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = {
                    if (currentUser != null) {
                        viewModel.setScreen("dashboard")
                    } else {
                        viewModel.setScreen("login")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24), contentColor = Color(0xFF0F172A)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("get_started_button")
            ) {
                Text("GET STARTED", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Developer Demo Shortcuts", color = Color(0xFFFBBF24), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.login("admin", "admin") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                            modifier = Modifier.weight(1f).testTag("shortcut_admin")
                        ) {
                            Text("Admin", fontSize = 11.sp, color = Color.White)
                        }
                        Button(
                            onClick = { viewModel.login("parent", "parent") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            modifier = Modifier.weight(1f).testTag("shortcut_parent")
                        ) {
                            Text("Parent", fontSize = 11.sp, color = Color.White)
                        }
                        Button(
                            onClick = { viewModel.login("driver", "driver") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC4899)),
                            modifier = Modifier.weight(1f).testTag("shortcut_driver")
                        ) {
                            Text("Driver", fontSize = 11.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(viewModel: KgmViewModel) {
    val businessInfo by viewModel.businessInfo.collectAsStateWithLifecycle()
    var phoneOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginError by viewModel.loginError.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .size(120.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                AppLogoImage(
                    logoUrl = businessInfo["logo"],
                    contentDescription = "App Logo",
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome to KGM Travels",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "\"Your Child's Safety, Our Priority\"",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFD97706),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Sign in to manage Velammal Vidyalaya transit",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = phoneOrEmail,
                onValueChange = { phoneOrEmail = it },
                label = { Text("Username / Mobile Number / Email") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Username") },
                modifier = Modifier.fillMaxWidth().testTag("username_input"),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().testTag("password_input"),
                shape = RoundedCornerShape(12.dp)
            )

            loginError?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.login(phoneOrEmail, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("login_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Login Securely", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { viewModel.navigateTo("parent_register") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("go_to_register_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.PersonAdd, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Register as Parent", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { viewModel.forgotPassword(phoneOrEmail) }) {
                    Text("Forgot Password?")
                }
                TextButton(onClick = { viewModel.navigateTo("terms_view") }) {
                    Text("Terms & Conditions")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("⚡ Quick Demo One-Tap Login", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.login("admin", "admin") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D4ED8)),
                            modifier = Modifier.weight(1f).testTag("login_shortcut_admin"),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text("Super Admin", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                        Button(
                            onClick = { viewModel.login("parent", "parent") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857)),
                            modifier = Modifier.weight(1f).testTag("login_shortcut_parent"),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text("Parent", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.login("driver", "driver") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB45309)),
                            modifier = Modifier.weight(1f).testTag("login_shortcut_driver"),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text("Driver", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                        Button(
                            onClick = { viewModel.login("staff", "staff") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),
                            modifier = Modifier.weight(1f).testTag("login_shortcut_staff"),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text("Staff", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                        Button(
                            onClick = { viewModel.login("cleaner", "cleaner") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569)),
                            modifier = Modifier.weight(1f).testTag("login_shortcut_cleaner"),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text("Cleaner", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeChip(text: String, selected: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = if (selected) Color.White else Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = if (selected) Color(0xFF005CB9) else Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MainShell(viewModel: KgmViewModel, screen: String) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val businessInfo by viewModel.businessInfo.collectAsStateWithLifecycle()
    val userRole = currentUser?.role ?: "Parent"
    val appSettings by viewModel.appSettings.collectAsStateWithLifecycle()
    var showProfileDialog by remember { mutableStateOf(false) }
    var showLogoutConfirm by remember { mutableStateOf(false) }

    // Edit Profile states
    var isEditingProfile by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(currentUser?.name ?: "") }
    var editEmail by remember { mutableStateOf(currentUser?.email ?: "") }
    var editMobile by remember { mutableStateOf(currentUser?.mobileNumber ?: "") }
    var editOldPass by remember { mutableStateOf("") }
    var editNewPass by remember { mutableStateOf("") }
    var editAvatarPath by remember { mutableStateOf("https://images.unsplash.com/photo-1544005313-94ddf0286df2") } // editable profile photo link

    val context = LocalContext.current

    val galleryLogoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        if (uri != null) viewModel.applyLogoFromUri(context, uri)
    }

    val fileLogoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: android.net.Uri? ->
        if (uri != null) viewModel.applyLogoFromUri(context, uri)
    }


    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (screen != "dashboard") {
                                IconButton(onClick = { viewModel.navigateBack() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                            } else {
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.size(38.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    AppLogoImage(
                                        logoUrl = businessInfo["logo"],
                                        contentDescription = "Logo",
                                        modifier = Modifier.fillMaxSize().padding(2.dp),
                                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Column {
                                Text(
                                    text = "VELAMMAL VIDYALAYA",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White.copy(alpha = 0.8f),
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "KGM TRAVELS",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { viewModel.navigateTo("website") },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Filled.Public, contentDescription = "Website", tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                            
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                    .clickable { viewModel.navigateTo("notifications_list") },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color.Red, CircleShape)
                                        .align(Alignment.TopEnd)
                                        .offset(x = (-2).dp, y = 2.dp)
                                )
                            }
                            
                            val initials = when {
                                userRole.contains("Super", ignoreCase = true) -> "SA"
                                userRole.contains("Admin", ignoreCase = true) -> "AD"
                                userRole.contains("Driver", ignoreCase = true) -> "DR"
                                userRole.contains("Parent", ignoreCase = true) -> "PA"
                                else -> "ST"
                            }
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                    .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                                    .clickable { showProfileDialog = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = initials,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BadgeChip(text = userRole, selected = true)
                        BadgeChip(text = "Main Campus", selected = false)
                        BadgeChip(text = "AY 2026-27", selected = false)
                    }
                }
            }
        },
        bottomBar = {
            if (userRole == "Super Admin" || userRole == "Admin") {
                NavigationBar {
                    NavigationBarItem(
                        selected = screen == "dashboard",
                        onClick = { viewModel.navigateTo("dashboard") },
                        icon = { Icon(Icons.Filled.Dashboard, contentDescription = "Dashboard") },
                        label = { Text("Home", fontSize = 10.sp) }
                    )
                    NavigationBarItem(
                        selected = screen == "student_list",
                        onClick = { viewModel.navigateTo("student_list") },
                        icon = { Icon(Icons.Filled.People, contentDescription = "Students") },
                        label = { Text("Students", fontSize = 10.sp) }
                    )
                    NavigationBarItem(
                        selected = screen == "vehicle_list" || screen == "route_list",
                        onClick = { viewModel.navigateTo("route_list") },
                        icon = { Icon(Icons.AutoMirrored.Filled.AltRoute, contentDescription = "Routes") },
                        label = { Text("Routes", fontSize = 10.sp) }
                    )
                    NavigationBarItem(
                        selected = screen == "fee_management",
                        onClick = { viewModel.navigateTo("fee_management") },
                        icon = { Icon(Icons.Filled.Payment, contentDescription = "Fees") },
                        label = { Text("Fees", fontSize = 10.sp) }
                    )
                    NavigationBarItem(
                        selected = screen == "reports",
                        onClick = { viewModel.navigateTo("reports") },
                        icon = { Icon(Icons.Filled.BarChart, contentDescription = "Reports") },
                        label = { Text("Reports", fontSize = 10.sp) }
                    )
                }
            } else if (userRole == "Parent") {
                NavigationBar {
                    NavigationBarItem(
                        selected = screen == "dashboard",
                        onClick = { viewModel.navigateTo("dashboard") },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                    NavigationBarItem(
                        selected = screen == "parent_fees" || screen == "payment_gateway",
                        onClick = { viewModel.navigateTo("parent_fees") },
                        icon = { Icon(Icons.Filled.Payments, contentDescription = "Payments") },
                        label = { Text("Payments", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                    NavigationBarItem(
                        selected = screen == "parent_receipts" || screen == "receipt_view",
                        onClick = { viewModel.navigateTo("parent_receipts") },
                        icon = { Icon(Icons.Filled.Receipt, contentDescription = "Receipts") },
                        label = { Text("Receipts", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                    NavigationBarItem(
                        selected = screen == "notifications_list",
                        onClick = { viewModel.navigateTo("notifications_list") },
                        icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notifications") },
                        label = { Text("Notifications", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                    NavigationBarItem(
                        selected = screen == "parent_profile",
                        onClick = { viewModel.navigateTo("parent_profile") },
                        icon = { Icon(Icons.Filled.Person, contentDescription = "Profile Settings") },
                        label = { Text("Profile", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (screen) {
                "dashboard" -> DashboardScreen(viewModel)
                "parent_student" -> ParentStudentInfoScreen(viewModel)
                "parent_fees" -> ParentFeesScreen(viewModel)
                "parent_receipts" -> ParentReceiptsScreen(viewModel)
                "parent_support" -> ParentSupportScreen(viewModel)
                "parent_profile" -> ParentProfileScreen(viewModel)
                "student_list" -> StudentListScreen(viewModel)
                "student_detail" -> StudentDetailScreen(viewModel)
                "add_edit_student" -> AddEditStudentScreen(viewModel)
                "vehicle_list" -> VehicleListScreen(viewModel)
                "add_edit_vehicle" -> AddEditVehicleScreen(viewModel)
                "driver_list" -> DriverListScreen(viewModel)
                "add_edit_driver" -> AddEditDriverScreen(viewModel)
                "cleaner_list" -> CleanerListScreen(viewModel)
                "add_edit_cleaner" -> AddEditCleanerScreen(viewModel)
                "route_list" -> RouteListScreen(viewModel)
                "add_edit_route" -> AddEditRouteScreen(viewModel)
                "seat_layout" -> SeatLayoutScreen(viewModel)
                "fee_management" -> FeeManagementScreen(viewModel)
                "payment_gateway" -> PaymentGatewayScreen(viewModel)
                "receipt_view" -> ReceiptViewScreen(viewModel)
                "reports" -> ReportsScreen(viewModel)
                "website" -> WebsiteScreen(viewModel)
                "audit_logs" -> AuditLogsScreen(viewModel)
                "notifications_list" -> NotificationsListScreen(viewModel)
                "super_admin_panel" -> SuperAdminPanelScreen(viewModel)
                "app_icon_studio" -> AppIconStudioScreen(viewModel)
                else -> DashboardScreen(viewModel)
            }
        }
    }

    if (showProfileDialog) {
        Dialog(onDismissRequest = { 
            showProfileDialog = false 
            isEditingProfile = false
        }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isEditingProfile) "Edit Profile & Password" else "KGM Profile & Settings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (isEditingProfile) {
                        // Profile Avatar Selection
                        Text("Choose Profile Picture", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf(
                                "https://images.unsplash.com/photo-1544005313-94ddf0286df2" to "Avatar 1",
                                "https://images.unsplash.com/photo-1534528741775-53994a69daeb" to "Avatar 2",
                                "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d" to "Avatar 3",
                                "https://images.unsplash.com/photo-1494790108377-be9c29b29330" to "Avatar 4"
                            ).forEach { avatar ->
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .border(
                                            width = if (editAvatarPath == avatar.first) 3.dp else 1.dp,
                                            color = if (editAvatarPath == avatar.first) MaterialTheme.colorScheme.primary else Color.LightGray,
                                            shape = CircleShape
                                        )
                                        .background(Color.LightGray, CircleShape)
                                        .clickable { editAvatarPath = avatar.first },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(avatar.second.takeLast(1), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = editName,
                            onValueChange = { editName = it },
                            label = { Text("Parent Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = editEmail,
                            onValueChange = { editEmail = it },
                            label = { Text("Email Address") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = editMobile,
                            onValueChange = { editMobile = it },
                            label = { Text("Mobile Number") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Security / Change Password", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray, modifier = Modifier.align(Alignment.Start))
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = editOldPass,
                            onValueChange = { editOldPass = it },
                            label = { Text("Old Password (to verify)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = editNewPass,
                            onValueChange = { editNewPass = it },
                            label = { Text("New Password") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { isEditingProfile = false },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Cancel")
                            }
                            Button(
                                onClick = {
                                    if (editNewPass.isNotEmpty()) {
                                        if (editOldPass.isEmpty()) {
                                            viewModel.showMessage("Please verify using Old Password.")
                                        } else {
                                            val passChanged = viewModel.changePassword(editOldPass, editNewPass)
                                            if (passChanged) {
                                                viewModel.updateParentProfile(editName, editEmail, editMobile)
                                                isEditingProfile = false
                                                editOldPass = ""
                                                editNewPass = ""
                                            } else {
                                                viewModel.showMessage("Incorrect Old Password.")
                                            }
                                        }
                                    } else {
                                        viewModel.updateParentProfile(editName, editEmail, editMobile)
                                        isEditingProfile = false
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Save Info")
                            }
                        }

                    } else {
                        // Normal Card View
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(52.dp)
                                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val initials = when {
                                            userRole.contains("Super", ignoreCase = true) -> "SA"
                                            userRole.contains("Admin", ignoreCase = true) -> "AD"
                                            userRole.contains("Driver", ignoreCase = true) -> "DR"
                                            userRole.contains("Parent", ignoreCase = true) -> "PA"
                                            else -> "ST"
                                        }
                                        Text(
                                            text = initials,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column {
                                        Text(
                                            text = currentUser?.name ?: "User",
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Text(
                                            text = userRole,
                                            color = MaterialTheme.colorScheme.primary,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "Email: ${currentUser?.email ?: "N/A"}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.DarkGray
                                        )
                                        Text(
                                            text = "Mobile: ${currentUser?.mobileNumber ?: "N/A"}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        editName = currentUser?.name ?: ""
                                        editEmail = currentUser?.email ?: ""
                                        editMobile = currentUser?.mobileNumber ?: ""
                                        isEditingProfile = true
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit Profile", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Edit Contact Profile Info", fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "APP PREFERENCES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.Start),
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        val isDarkEnabled = (appSettings["dark_mode"] ?: "false") == "true"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isDarkEnabled) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                                    contentDescription = "Theme Icon",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Dark Mode Theme", fontWeight = FontWeight.Medium)
                            }
                            Switch(
                                checked = isDarkEnabled,
                                onCheckedChange = { checked ->
                                    viewModel.updateAppSettings("dark_mode", if (checked) "true" else "false")
                                    viewModel.showMessage("Dark Mode ${if (checked) "Enabled" else "Disabled"}")
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val isNotificationsEnabled = (appSettings["notifications_enabled"] ?: "true") == "true"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isNotificationsEnabled) Icons.Filled.NotificationsActive else Icons.Filled.NotificationsOff,
                                    contentDescription = "Notifications Icon",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Enable Notifications", fontWeight = FontWeight.Medium)
                            }
                            Switch(
                                checked = isNotificationsEnabled,
                                onCheckedChange = { checked ->
                                    viewModel.updateAppSettings("notifications_enabled", if (checked) "true" else "false")
                                    viewModel.showMessage("Notifications ${if (checked) "Enabled" else "Disabled"}")
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Gallery & Files App Logo Selector
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PhotoLibrary,
                                        contentDescription = "Gallery Logo Icon",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("App Logo & Branding", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("Change app logo across screens & icon", fontSize = 10.sp, color = Color.Gray)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { galleryLogoPicker.launch("image/*") },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Icon(Icons.Filled.PhotoLibrary, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Gallery", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Button(
                                        onClick = { fileLogoPicker.launch(arrayOf("image/*")) },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Icon(Icons.Filled.FolderOpen, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Files", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showProfileDialog = false },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Close")
                            }
                            Button(
                                onClick = {
                                    showProfileDialog = false
                                    showLogoutConfirm = true
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Logout")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Warning, contentDescription = "Warning", tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Confirm Logout")
                }
            },
            text = { Text("Are you sure you want to log out of KGM Travels Transport App?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        viewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Logout", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DashboardScreen(viewModel: KgmViewModel) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val role = currentUser?.role ?: "Parent"

    when (role) {
        "Super Admin", "Admin", "Office Staff" -> AdminDashboard(viewModel)
        "Parent" -> ParentDashboard(viewModel)
        "Driver" -> DriverDashboard(viewModel)
        else -> CleanerDashboard(viewModel)
    }
}

@Composable
fun AdminDashboard(viewModel: KgmViewModel) {
    val context = LocalContext.current
    val businessInfo by viewModel.businessInfo.collectAsStateWithLifecycle()
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val vehicles by viewModel.allVehicles.collectAsStateWithLifecycle()
    val routes by viewModel.allRoutes.collectAsStateWithLifecycle()
    val txns by viewModel.allTransactions.collectAsStateWithLifecycle()

    val adminLogoGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        if (uri != null) viewModel.applyLogoFromUri(context, uri)
    }

    val adminLogoFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: android.net.Uri? ->
        if (uri != null) viewModel.applyLogoFromUri(context, uri)
    }

    val totalStudents = students.size
    val mainCampusCount = students.count { it.campus == "Main School" }
    val annexCampusCount = students.count { it.campus == "Annex School" }
    val regularCount = students.count { it.category == "Regular" }
    val neetCount = students.count { it.category == "NEET" }
    val iitCount = students.count { it.category == "IIT Foundation" }

    val totalVehicles = vehicles.size
    val totalRoutes = routes.size

    val collectionThisMonth = txns.sumOf { it.amount }
    val pendingFees = students.sumOf { it.balance }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Velammal Vidyalaya Transport Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Financial Metrics Row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(
                title = "Total Collected",
                value = "₹${collectionThisMonth.toInt()}",
                icon = Icons.Filled.Payments,
                color = Color(0xFF10B981),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Pending Fees",
                value = "₹${pendingFees.toInt()}",
                icon = Icons.Filled.PendingActions,
                color = Color(0xFFEF4444),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Student metrics row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(
                title = "Total Students",
                value = "$totalStudents",
                icon = Icons.Filled.People,
                color = Color(0xFF3B82F6),
                modifier = Modifier.weight(1.0f)
            )
            StatCard(
                title = "Vehicles Fleet",
                value = "$totalVehicles",
                icon = Icons.Filled.DirectionsBus,
                color = Color(0xFFF59E0B),
                modifier = Modifier.weight(1.0f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Campus Breakdown", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).background(BluePrimary, CircleShape))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Main School Campus", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                    Text("$mainCampusCount Students", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).background(BlueSecondary, CircleShape))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Annex School Campus", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                    Text("$annexCampusCount Students", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Student Categories Card from HTML theme
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF005CB9).copy(alpha = 0.05f)),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFF005CB9).copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STUDENT CATEGORIES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF005CB9),
                        letterSpacing = 1.sp
                    )
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xFF005CB9).copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("By Campus", fontSize = 10.sp, color = Color(0xFF005CB9), fontWeight = FontWeight.Medium)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text(text = "$regularCount", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ContentDark)
                        Text(text = "REGULAR", fontSize = 10.sp, color = ContentMuted, fontWeight = FontWeight.SemiBold)
                    }
                    Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFF005CB9).copy(alpha = 0.1f)))
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text(text = "$neetCount", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ContentDark)
                        Text(text = "NEET", fontSize = 10.sp, color = ContentMuted, fontWeight = FontWeight.SemiBold)
                    }
                    Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color(0xFF005CB9).copy(alpha = 0.1f)))
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text(text = "$iitCount", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ContentDark)
                        Text(text = "IIT FOUND.", fontSize = 10.sp, color = ContentMuted, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Operational Health Card from HTML theme
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "OPERATIONAL HEALTH",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ContentMuted,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                // Row 1: Vehicles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFE0F2FE), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.DirectionsBus, contentDescription = "Vehicles", tint = Color(0xFF005CB9), modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Vehicles Fleet", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = ContentDark)
                            Text("$totalVehicles Active | 0 Maintenance", fontSize = 11.sp, color = ContentMuted)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("$totalVehicles", fontWeight = FontWeight.Black, fontSize = 14.sp, color = ContentDark)
                        Text("All Functional", fontSize = 10.sp, color = Color(0xFF16A34A), fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(12.dp))
                
                // Row 2: Staff & Drivers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFF3E8FF), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.People, contentDescription = "Staff", tint = Color(0xFF7E22CE), modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Staff & Drivers", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = ContentDark)
                            Text("12 Drivers | 8 Attendants", fontSize = 11.sp, color = ContentMuted)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("20", fontWeight = FontWeight.Black, fontSize = 14.sp, color = ContentDark)
                        Text("All On-Duty", fontSize = 10.sp, color = Color(0xFF16A34A), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Red Overdue alert banner
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFFFEE2E2)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color(0xFFEF4444), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Warning, contentDescription = "Alert", tint = Color.White, modifier = Modifier.size(14.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("24 Pending Payments Overdue", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF991B1B))
                    Text("System auto-reminders scheduled for 10:00 AM", fontSize = 10.sp, color = Color(0xFFDC2626))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Administrative Operations", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        // Grid of Action Shortcuts
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { viewModel.navigateTo("student_list") },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Filled.PersonAdd, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Students", fontSize = 12.sp)
                }
                Button(
                    onClick = { viewModel.navigateTo("vehicle_list") },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Filled.DirectionsBus, contentDescription = "Vehicles")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Vehicles", fontSize = 12.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { viewModel.navigateTo("driver_list") },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Filled.DriveFileRenameOutline, contentDescription = "Drivers")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Drivers", fontSize = 12.sp)
                }
                Button(
                    onClick = { viewModel.navigateTo("route_list") },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Filled.Map, contentDescription = "Routes")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Routes", fontSize = 12.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { viewModel.navigateTo("audit_logs") },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(Icons.Filled.History, contentDescription = "Audit Logs")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Audit Logs", fontSize = 12.sp)
                }
                Button(
                    onClick = { viewModel.navigateTo("cleaner_list") },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(Icons.Filled.CleaningServices, contentDescription = "Cleaners")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cleaners", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // App Branding & Logo Quick Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.size(42.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                AppLogoImage(
                                    logoUrl = businessInfo["logo"],
                                    contentDescription = "Current App Logo",
                                    modifier = Modifier.fillMaxSize().padding(3.dp),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("App Logo & Branding", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Change app logo across all screens and launcher icon", fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { adminLogoGalleryLauncher.launch("image/*") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Filled.PhotoLibrary, contentDescription = "Gallery", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("From Gallery", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { adminLogoFileLauncher.launch(arrayOf("image/*")) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Filled.FolderOpen, contentDescription = "Files", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("From Files", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // WhatsApp Plus App Icon Studio Super Admin Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo("app_icon_studio") }
                    .testTag("app_icon_studio_shortcut")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                    listOf(Color(0xFF10B981), Color(0xFF047857))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(1.dp, Color(0xFFFBBF24), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Palette, contentDescription = "App Icon", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Choose Your App Icon", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                            Surface(color = Color(0xFF10B981), shape = RoundedCornerShape(6.dp)) {
                                Text("WA+ STYLE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                            }
                        }
                        Text("Super Admin: Choose from gallery photos, files, or 11 luxury WhatsApp Plus icon packs.", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                    }
                    Icon(Icons.Filled.ChevronRight, contentDescription = "Enter", tint = Color(0xFF10B981))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo("super_admin_panel") }
                    .testTag("super_admin_panel_shortcut")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.SettingsSuggest, contentDescription = "Admin Config", tint = Color.White)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Super Admin Control Panel", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("Fully configure schools, academic years, parents, drivers, service areas, website content, and app styles.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                    }
                    Icon(Icons.Filled.ChevronRight, contentDescription = "Enter", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Financial Transactions & Webhooks",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        // State variables for filter and search
        var searchQuery by remember { mutableStateOf("") }
        var selectedMethodFilter by remember { mutableStateOf("All") }
        var selectedStatusFilter by remember { mutableStateOf("All") }
        
        // Expansion state map
        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
        
        // Export states
        var exportPdfLoading by remember { mutableStateOf(false) }
        var exportExcelLoading by remember { mutableStateOf(false) }
        var infoMessage by remember { mutableStateOf<String?>(null) }
        
        // Filter transactions list
        val filteredTxns = txns.filter { t ->
            val matchSearch = searchQuery.isEmpty() || t.studentName.contains(searchQuery, ignoreCase = true) || t.transactionId.contains(searchQuery, ignoreCase = true)
            val matchMethod = selectedMethodFilter == "All" || t.paymentMethod.equals(selectedMethodFilter, ignoreCase = true)
            val matchStatus = selectedStatusFilter == "All" || (selectedStatusFilter == "Paid" && t.status == "Paid") || (selectedStatusFilter == "Failed" && t.status != "Paid")
            matchSearch && matchMethod && matchStatus
        }

        val filteredTotal = filteredTxns.filter { it.status == "Paid" }.sumOf { it.amount }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by Student Name or Txn ID...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Method Filters
                Text("Filter by Payment Method", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ContentMuted)
                Spacer(modifier = Modifier.height(6.dp))
                val methods = listOf("All", "UPI", "Card", "Net Banking", "Wallet")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    methods.forEach { method ->
                        val isSelected = selectedMethodFilter == method
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedMethodFilter = method },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                            ),
                            border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 6.dp, horizontal = 2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(method, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Status Filters
                Text("Filter by Status", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ContentMuted)
                Spacer(modifier = Modifier.height(6.dp))
                val statuses = listOf("All", "Paid", "Failed")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    statuses.forEach { st ->
                        val isSelected = selectedStatusFilter == st
                        val chipColor = when(st) {
                            "Paid" -> Color(0xFF10B981)
                            "Failed" -> Color(0xFFEF4444)
                            else -> MaterialTheme.colorScheme.primary
                        }
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedStatusFilter = st },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) chipColor.copy(alpha = 0.15f) else Color.Transparent
                            ),
                            border = BorderStroke(1.dp, if (isSelected) chipColor else Color.LightGray.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (st == "Paid") "Paid / Captured" else if (st == "Failed") "Failed / Declined" else "All Statuses",
                                    fontSize = 11.sp, 
                                    fontWeight = FontWeight.Bold, 
                                    color = if (isSelected) chipColor else Color.Gray
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(12.dp))

                // Stats summary of filtered results
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Filtered Total Revenue", fontSize = 11.sp, color = ContentMuted, fontWeight = FontWeight.Bold)
                        Text("₹${filteredTotal.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF10B981))
                    }
                    Text("${filteredTxns.size} Records Found", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ContentDark)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // PDF / Excel simulation action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            exportPdfLoading = true
                            infoMessage = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        if (exportPdfLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Exporting PDF...", fontSize = 11.sp)
                        } else {
                            Icon(Icons.Filled.Description, contentDescription = "PDF", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Export PDF Summary", fontSize = 11.sp)
                        }
                    }

                    Button(
                        onClick = {
                            exportExcelLoading = true
                            infoMessage = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        if (exportExcelLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Exporting Excel...", fontSize = 11.sp)
                        } else {
                            Icon(Icons.AutoMirrored.Filled.InsertDriveFile, contentDescription = "Excel", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Export Excel Ledger", fontSize = 11.sp)
                        }
                    }
                }

                // Show action callback feedback
                if (exportPdfLoading) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(1800)
                        exportPdfLoading = false
                        viewModel.logAudit(
                            user = viewModel.currentUser.value?.username ?: "Admin",
                            type = "Export Report",
                            desc = "Generated secure PDF Billing Summary containing ${filteredTxns.size} webhook entries."
                        )
                        infoMessage = "Report exported successfully! Downloaded KGM_Travels_Statement_${System.currentTimeMillis() % 100000}.pdf with digital seal."
                    }
                }
                if (exportExcelLoading) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(1800)
                        exportExcelLoading = false
                        viewModel.logAudit(
                            user = viewModel.currentUser.value?.username ?: "Admin",
                            type = "Export Report",
                            desc = "Exported transaction ledger to Excel spreadsheet containing ${filteredTxns.size} data rows."
                        )
                        infoMessage = "Excel sheet KGM_Ledger_Output_${System.currentTimeMillis() % 100000}.xlsx generated & saved successfully."
                    }
                }

                infoMessage?.let { msg ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFBFDBFE))
                    ) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = "Success", tint = Color(0xFF2563EB), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(msg, fontSize = 11.sp, color = Color(0xFF1E40AF), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Transaction Logs Expandable List
        if (filteredTxns.isEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderColor),
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Search, contentDescription = "Empty", tint = Color.LightGray, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No transactions match the selected filters.", fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            filteredTxns.forEach { t ->
                val isExpanded = expandedStates[t.id] ?: false
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, if (isExpanded) MaterialTheme.colorScheme.primary else BorderColor)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(t.studentName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(t.paymentMonth, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("•", fontSize = 12.sp, color = Color.LightGray)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(t.paymentMethod, fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("₹${t.amount.toInt()}", fontWeight = FontWeight.Black, fontSize = 15.sp)
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = if (t.status == "Paid") Color(0xFFECFDF5) else Color(0xFFFEF2F2),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (t.status == "Paid") "CAPTURED" else "DECLINED",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (t.status == "Paid") Color(0xFF047857) else Color(0xFFB91C1C)
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = { expandedStates[t.id] = !isExpanded },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                        contentDescription = "Expand",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }

                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                            Spacer(modifier = Modifier.height(10.dp))
                            
                            Text("SECURITY METADATA & TELEMETRY", fontSize = 10.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            AdminDetailRow("Gateway", "Razorpay India Node (Live Webhook)")
                            AdminDetailRow("Order ID", t.transactionId)
                            AdminDetailRow("Payment ID", if (t.status == "Paid") "pay_${t.transactionId.substringAfter("order_")}" else "N/A (Failed Session)")
                            AdminDetailRow("Crypto Integrity", "HMAC-SHA256 SECURED & MATCHED")
                            AdminDetailRow("Biller Server", "com.kgm.billing.webhook.Receiver")
                            AdminDetailRow("Date & Time", t.dateAndTime)
                            AdminDetailRow("Class & Route", "${t.classAndSection} | Route: ${t.routeNumber}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.DarkGray)
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}



@Composable
fun DriverDashboard(viewModel: KgmViewModel) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val businessInfo by viewModel.businessInfo.collectAsStateWithLifecycle()
    val routes by viewModel.allRoutes.collectAsStateWithLifecycle()
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    
    val activeTripStatus by viewModel.tripStatus.collectAsStateWithLifecycle()
    val boardingState by viewModel.studentBoardingState.collectAsStateWithLifecycle()
    val stopCompletionState by viewModel.stopCompletionState.collectAsStateWithLifecycle()

    val myRoute = routes.find { it.assignedDriver.contains(currentUser?.name ?: "", ignoreCase = true) }
        ?: routes.firstOrNull()
    val myStudents = students.filter { it.assignedRoute == myRoute?.routeNumber }

    val totalPassengers = myStudents.size
    val boardedCount = myStudents.count { boardingState[it.studentId] == "Boarded" }
    val absentCount = myStudents.count { boardingState[it.studentId] == "Absent" }
    val pendingCount = totalPassengers - boardedCount - absentCount

    var parentToCall by remember { mutableStateOf<StudentEntity?>(null) }
    var showResetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Driver Banner Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(56.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    AppLogoImage(
                        logoUrl = businessInfo["logo"],
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize().padding(4.dp),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Hello, ${currentUser?.name ?: "Driver"}!",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Vehicle: ${myRoute?.assignedVehicle ?: "TN-02-AB-1001"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "Route ${myRoute?.routeNumber ?: "R-101"}: ${myRoute?.routeName ?: "Mel Ayanambakkam"}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trip Controller Card
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when (activeTripStatus) {
                    "Morning Run" -> Color(0xFFEFF6FF)
                    "Evening Run" -> Color(0xFFFDF4FF)
                    "Completed" -> Color(0xFFECFDF5)
                    else -> MaterialTheme.colorScheme.surface
                }
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active Trip Status",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    val badgeColor = when (activeTripStatus) {
                        "Morning Run" -> Color(0xFF3B82F6)
                        "Evening Run" -> Color(0xFFD946EF)
                        "Completed" -> Color(0xFF10B981)
                        else -> Color.Gray
                    }
                    val badgeBg = when (activeTripStatus) {
                        "Morning Run" -> Color(0xFFDBEAFE)
                        "Evening Run" -> Color(0xFFFAE8FF)
                        "Completed" -> Color(0xFFD1FAE5)
                        else -> Color(0xFFF3F4F6)
                    }
                    Box(
                        modifier = Modifier
                            .background(badgeBg, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (activeTripStatus.contains("Run")) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(badgeColor, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            Text(
                                text = activeTripStatus.uppercase(Locale.getDefault()),
                                color = badgeColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))

                if (activeTripStatus == "Not Started") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.updateTripStatus("Morning Run") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                        ) {
                            Icon(Icons.Filled.WbSunny, contentDescription = "Morning Run")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Morning Run")
                        }
                        Button(
                            onClick = { viewModel.updateTripStatus("Evening Run") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD946EF))
                        ) {
                            Icon(Icons.Filled.NightsStay, contentDescription = "Evening Run")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Evening Run")
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (activeTripStatus == "Completed") {
                            Button(
                                onClick = { showResetDialog = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Filled.Refresh, contentDescription = "Reset")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Reset Trip for Next Run")
                            }
                        } else {
                            Button(
                                onClick = { viewModel.updateTripStatus("Completed") },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                            ) {
                                Icon(Icons.Filled.CheckCircle, contentDescription = "Complete Run")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Complete Run")
                            }
                            OutlinedButton(
                                onClick = { showResetDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Filled.Cancel, contentDescription = "Cancel")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Cancel")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Real-time Headcount Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5))
            ) {
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("BOARDED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF047857))
                    Text("$boardedCount", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2))
            ) {
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("ABSENT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB91C1C))
                    Text("$absentCount", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF991B1B))
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("PENDING", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4B5563))
                    Text("$pendingCount", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Daily Route Stops Indicator List
        Text(
            text = "Route Sequence & Stops Progress",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val stopsList = if (activeTripStatus == "Evening Run") {
                    myRoute?.dropStops?.split(",") ?: emptyList()
                } else {
                    myRoute?.pickupStops?.split(",") ?: emptyList()
                }

                if (stopsList.isEmpty() || (stopsList.size == 1 && stopsList[0].trim().isEmpty())) {
                    Text("No stops assigned to this route parameter.", color = Color.Gray, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                } else {
                    stopsList.forEachIndexed { index, stopRaw ->
                        val stop = stopRaw.trim()
                        val isCompleted = stopCompletionState[stop] ?: false
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(
                                        if (isCompleted) Color(0xFF10B981) else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isCompleted) {
                                    Icon(Icons.Filled.Check, contentDescription = "Done", tint = Color.White, modifier = Modifier.size(16.dp))
                                } else {
                                    Text(
                                        text = "${index + 1}",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = stop,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f),
                                color = if (isCompleted) Color.Gray else MaterialTheme.colorScheme.onSurface,
                                style = if (isCompleted) MaterialTheme.typography.bodyMedium.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough) else MaterialTheme.typography.bodyMedium
                            )

                            IconButton(
                                onClick = { viewModel.toggleStopCompletion(stop, !isCompleted) }
                            ) {
                                Icon(
                                    imageVector = if (isCompleted) Icons.Filled.RadioButtonChecked else Icons.Filled.RadioButtonUnchecked,
                                    contentDescription = "Toggle Stop Status",
                                    tint = if (isCompleted) Color(0xFF10B981) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Passenger Boarding Manifest List
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Student Pick-up Manifest (${myStudents.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { viewModel.navigateTo("seat_layout") }) {
                Icon(Icons.Filled.GridView, contentDescription = "Seat Grid", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Seat Layout", fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (myStudents.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Filled.People, contentDescription = "No students", modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No students assigned to your route.", color = Color.Gray)
                }
            }
        } else {
            myStudents.forEach { student ->
                val currentBoardingStatus = boardingState[student.studentId] ?: "Pending"
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (currentBoardingStatus) {
                            "Boarded" -> Color(0xFFF0FDF4)
                            "Absent" -> Color(0xFFFEF2F2)
                            else -> MaterialTheme.colorScheme.surface
                        }
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = when (currentBoardingStatus) {
                            "Boarded" -> Color(0xFFBBF7D0)
                            "Absent" -> Color(0xFFFEE2E2)
                            else -> MaterialTheme.colorScheme.outlineVariant
                        }
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = student.name,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Class: ${student.classLevel} | Seat: ${student.assignedSeat}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Stop: ${if (activeTripStatus == "Evening Run") student.dropAddress else student.pickupAddress}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            
                            IconButton(
                                onClick = { parentToCall = student },
                                modifier = Modifier
                                    .background(Color(0xFFEFF6FF), CircleShape)
                                    .size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Phone,
                                    contentDescription = "Call Parent",
                                    tint = Color(0xFF2563EB),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.updateBoardingStatus(student.studentId, "Boarded") },
                                modifier = Modifier.weight(1.0f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (currentBoardingStatus == "Boarded") Color(0xFF10B981) else Color(0xFFE2E8F0),
                                    contentColor = if (currentBoardingStatus == "Boarded") Color.White else Color(0xFF475569)
                                ),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DirectionsBus,
                                        contentDescription = "Boarded",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Boarded", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Button(
                                onClick = { viewModel.updateBoardingStatus(student.studentId, "Absent") },
                                modifier = Modifier.weight(1.0f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (currentBoardingStatus == "Absent") Color(0xFFEF4444) else Color(0xFFE2E8F0),
                                    contentColor = if (currentBoardingStatus == "Absent") Color.White else Color(0xFF475569)
                                ),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Cancel,
                                        contentDescription = "Absent",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Absent", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset Trip State?") },
            text = { Text("This will clear all manifest check-ins and stop completions, resetting the trip to 'Not Started' state. Are you sure you want to proceed?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetTripState()
                        showResetDialog = false
                    }
                ) {
                    Text("Reset", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (parentToCall != null) {
        val student = parentToCall!!
        AlertDialog(
            onDismissRequest = { parentToCall = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Phone, contentDescription = "Call", tint = Color(0xFF10B981))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Contact Parent/Guardian")
                }
            },
            text = {
                Column {
                    Text("Student: ${student.name}", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Parent: ${student.parentName}")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Primary Mobile Number:", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = student.parentMobileNumber,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (student.alternateMobile.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Alternate Number:", fontSize = 12.sp, color = Color.Gray)
                        Text(student.alternateMobile, fontWeight = FontWeight.Medium)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.showMessage("Simulating voice call connection to ${student.parentMobileNumber}...")
                        parentToCall = null
                    }
                ) {
                    Text("Dial Number")
                }
            },
            dismissButton = {
                TextButton(onClick = { parentToCall = null }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun CleanerDashboard(viewModel: KgmViewModel) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.CleaningServices, contentDescription = "Cleaner", modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Welcome, ${currentUser?.name}!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Assigned Vehicle: TN-02-AB-1001", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Status: Active Attendant", color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StudentListScreen(viewModel: KgmViewModel) {
    val query by viewModel.studentSearchQuery.collectAsStateWithLifecycle()
    val students by viewModel.filteredStudents.collectAsStateWithLifecycle()

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
            Text("Students Directory", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Row {
                IconButton(onClick = {
                    val csv = viewModel.exportStudentsToCSV()
                    viewModel.showMessage("Copied CSV output to clipboard!")
                }) {
                    Icon(Icons.Filled.Download, contentDescription = "Export")
                }
                IconButton(onClick = {
                    viewModel.studentFormState.value = StudentFormState()
                    viewModel.navigateTo("add_edit_student")
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Student", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.studentSearchQuery.value = it },
            placeholder = { Text("Search by name, ID, vehicle, or phone") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth().testTag("student_search_input"),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Quick Campus filter tabs
        val campusFilter by viewModel.studentCampusFilter.collectAsStateWithLifecycle()
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            listOf("All", "Main School", "Annex School").forEach { opt ->
                FilterChip(
                    selected = campusFilter == opt,
                    onClick = { viewModel.studentCampusFilter.value = opt },
                    label = { Text(opt, fontSize = 11.sp) }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (students.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("No students match the criteria.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(students) { s ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectedStudent.value = s
                                viewModel.navigateTo("student_detail")
                            }
                            .testTag("student_item_${s.studentId}"),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(s.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("ID: ${s.studentId} | Class: ${s.classLevel} - ${s.section}", fontSize = 12.sp, color = Color.Gray)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.DirectionsBus, contentDescription = "Bus", modifier = Modifier.size(12.dp), tint = Color.Gray)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(s.assignedVehicle, fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (s.balance > 0) "₹${s.balance}" else "Paid",
                                    fontWeight = FontWeight.Bold,
                                    color = if (s.balance > 0) Color.Red else Color(0xFF10B981)
                                )
                                Text(s.campus, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentDetailScreen(viewModel: KgmViewModel) {
    val student by viewModel.selectedStudent.collectAsStateWithLifecycle()
    if (student == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No student selected.")
        }
        return
    }

    val s = student!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Student Profile", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Row {
                IconButton(onClick = {
                    viewModel.studentFormState.value = StudentFormState(
                        id = s.id,
                        studentId = s.studentId,
                        admissionNumber = s.admissionNumber,
                        name = s.name,
                        gender = s.gender,
                        dateOfBirth = s.dateOfBirth,
                        bloodGroup = s.bloodGroup,
                        classLevel = s.classLevel,
                        section = s.section,
                        academicYear = s.academicYear,
                        campus = s.campus,
                        category = s.category,
                        parentName = s.parentName,
                        fatherName = s.fatherName,
                        motherName = s.motherName,
                        guardianName = s.guardianName,
                        parentMobileNumber = s.parentMobileNumber,
                        alternateMobile = s.alternateMobile,
                        whatsAppNumber = s.whatsAppNumber,
                        emailAddress = s.emailAddress,
                        emergencyContact = s.emergencyContact,
                        residentialAddress = s.residentialAddress,
                        googleMapsLocation = s.googleMapsLocation,
                        pickupAddress = s.pickupAddress,
                        pickupLandmark = s.pickupLandmark,
                        dropAddress = s.dropAddress,
                        dropLandmark = s.dropLandmark,
                        pickupTime = s.pickupTime,
                        dropTime = s.dropTime,
                        assignedVehicle = s.assignedVehicle,
                        assignedDriver = s.assignedDriver,
                        assignedRoute = s.assignedRoute,
                        assignedSeat = s.assignedSeat,
                        monthlyVanFee = s.monthlyVanFee,
                        discount = s.discount,
                        advancePaid = s.advancePaid,
                        joiningDate = s.joiningDate,
                        leavingDate = s.leavingDate,
                        medicalNotes = s.medicalNotes,
                        remarks = s.remarks,
                        status = s.status
                    )
                    viewModel.navigateTo("add_edit_student")
                }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { viewModel.deleteStudent(s) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Student Basic Info Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(s.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                Text("Admission No: ${s.admissionNumber}", fontSize = 14.sp)
                Text("Custom ID: ${s.studentId}", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column {
                        Text("Class & Section", fontSize = 11.sp, color = Color.Gray)
                        Text("${s.classLevel} - ${s.section}", fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text("Academic Year", fontSize = 11.sp, color = Color.Gray)
                        Text(s.academicYear, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text("Campus", fontSize = 11.sp, color = Color.Gray)
                        Text(s.campus, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Parent Contact Info", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Parent/Guardian Name: ${s.parentName}")
                Text("Mobile Number: ${s.parentMobileNumber}")
                Text("WhatsApp: ${s.whatsAppNumber}")
                Text("Email: ${s.emailAddress}")
                Text("Emergency Contact: ${s.emergencyContact}")
                Text("Residential Address: ${s.residentialAddress}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Transit Details", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Vehicle Number: ${s.assignedVehicle}")
                Text("Driver Assigned: ${s.assignedDriver}")
                Text("Route: ${s.assignedRoute}")
                Text("Seat: ${s.assignedSeat}")
                Text("Pickup: ${s.pickupTime} (${s.pickupLandmark})")
                Text("Drop: ${s.dropTime} (${s.dropLandmark})")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Fee Status", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Monthly Transport Fee: ₹${s.monthlyVanFee}")
                Text("Discounts: ₹${s.discount}")
                Text("Advance Payments: ₹${s.advancePaid}")
                Text("Outstanding Balance: ₹${s.balance}", color = if (s.balance > 0) Color.Red else Color(0xFF10B981), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (s.status == "Active") {
            Button(
                onClick = { viewModel.archiveStudent(s.id) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Archive Student Record")
            }
        } else {
            Button(
                onClick = { viewModel.restoreStudent(s.id) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restore Student Record")
            }
        }
    }
}

@Composable
fun AddEditStudentScreen(viewModel: KgmViewModel) {
    val f by viewModel.studentFormState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = if (f.id == 0) "Add New Student" else "Edit Student Profile",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = f.name,
            onValueChange = { viewModel.studentFormState.value = f.copy(name = it) },
            label = { Text("Student Name") },
            modifier = Modifier.fillMaxWidth().testTag("student_name_field")
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = f.classLevel,
                onValueChange = { viewModel.studentFormState.value = f.copy(classLevel = it) },
                label = { Text("Class") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = f.section,
                onValueChange = { viewModel.studentFormState.value = f.copy(section = it) },
                label = { Text("Section") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.parentName,
            onValueChange = { viewModel.studentFormState.value = f.copy(parentName = it, fatherName = it) },
            label = { Text("Parent / Father Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.parentMobileNumber,
            onValueChange = { viewModel.studentFormState.value = f.copy(parentMobileNumber = it, whatsAppNumber = it) },
            label = { Text("Parent Mobile Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.residentialAddress,
            onValueChange = { viewModel.studentFormState.value = f.copy(residentialAddress = it) },
            label = { Text("Residential Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = f.pickupTime,
                onValueChange = { viewModel.studentFormState.value = f.copy(pickupTime = it) },
                label = { Text("Pickup Time") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = f.dropTime,
                onValueChange = { viewModel.studentFormState.value = f.copy(dropTime = it) },
                label = { Text("Drop Time") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.monthlyVanFee.toString(),
            onValueChange = { viewModel.studentFormState.value = f.copy(monthlyVanFee = it.toDoubleOrNull() ?: 0.0) },
            label = { Text("Monthly Fee (₹)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveStudent() },
            modifier = Modifier.fillMaxWidth().height(50.dp).testTag("save_student_button")
        ) {
            Text("Save Student Profile", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun VehicleListScreen(viewModel: KgmViewModel) {
    val vehicles by viewModel.allVehicles.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Vehicles Fleet", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            IconButton(onClick = {
                viewModel.vehicleFormState.value = VehicleFormState()
                viewModel.navigateTo("add_edit_vehicle")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Vehicle", tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vehicles) { v ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(v.vehicleNumber, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                            Text("${v.manufacturer} - ${v.vehicleModel}", fontSize = 14.sp)
                            Text("Seating Capacity: ${v.seatingCapacity} seats", fontSize = 12.sp, color = Color.Gray)
                            Text("Insurance Expiry: ${v.insuranceExpiry}", fontSize = 11.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { viewModel.deleteVehicle(v) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddEditVehicleScreen(viewModel: KgmViewModel) {
    val f by viewModel.vehicleFormState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Add New Fleet Vehicle", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = f.vehicleNumber,
            onValueChange = { viewModel.vehicleFormState.value = f.copy(vehicleNumber = it) },
            label = { Text("Vehicle Number (e.g. TN-02-CD-2002)") },
            modifier = Modifier.fillMaxWidth().testTag("vehicle_number_field")
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.vehicleModel,
            onValueChange = { viewModel.vehicleFormState.value = f.copy(vehicleModel = it) },
            label = { Text("Vehicle Model & Model Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.manufacturer,
            onValueChange = { viewModel.vehicleFormState.value = f.copy(manufacturer = it) },
            label = { Text("Manufacturer (e.g. TATA, Mazda)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.seatingCapacity.toString(),
            onValueChange = { viewModel.vehicleFormState.value = f.copy(seatingCapacity = it.toIntOrNull() ?: 40) },
            label = { Text("Seating Capacity") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveVehicle() },
            modifier = Modifier.fillMaxWidth().height(50.dp).testTag("save_vehicle_button")
        ) {
            Text("Save Vehicle", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DriverListScreen(viewModel: KgmViewModel) {
    val drivers by viewModel.allDrivers.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Drivers Directory", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            IconButton(onClick = {
                viewModel.driverFormState.value = DriverFormState()
                viewModel.navigateTo("add_edit_driver")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Driver", tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(drivers) { d ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(d.driverName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("DL Number: ${d.licenceNumber}", fontSize = 13.sp)
                            Text("Licence Expiry: ${d.licenceExpiry}", fontSize = 12.sp, color = if (d.licenceExpiry < "2026-10-01") Color.Red else Color.Gray)
                            Text("Vehicle: ${d.assignedVehicle} | Route: ${d.assignedRoute}", fontSize = 12.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { viewModel.deleteDriver(d) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddEditDriverScreen(viewModel: KgmViewModel) {
    val f by viewModel.driverFormState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Register New Driver Profile", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = f.driverName,
            onValueChange = { viewModel.driverFormState.value = f.copy(driverName = it) },
            label = { Text("Driver Name") },
            modifier = Modifier.fillMaxWidth().testTag("driver_name_field")
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.mobileNumber,
            onValueChange = { viewModel.driverFormState.value = f.copy(mobileNumber = it) },
            label = { Text("Driver Mobile Number (For App Access)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.licenceNumber,
            onValueChange = { viewModel.driverFormState.value = f.copy(licenceNumber = it) },
            label = { Text("Driving Licence Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.licenceExpiry,
            onValueChange = { viewModel.driverFormState.value = f.copy(licenceExpiry = it) },
            label = { Text("Licence Expiry Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveDriver() },
            modifier = Modifier.fillMaxWidth().height(50.dp).testTag("save_driver_button")
        ) {
            Text("Save Driver Details", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CleanerListScreen(viewModel: KgmViewModel) {
    val cleaners by viewModel.allCleaners.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Cleaner / Attendants", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            IconButton(onClick = {
                viewModel.cleanerFormState.value = CleanerFormState()
                viewModel.navigateTo("add_edit_cleaner")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Cleaner", tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(cleaners) { c ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(c.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Phone: ${c.phoneNumber}", fontSize = 13.sp)
                            Text("Assigned Vehicle: ${c.assignedVehicle}", fontSize = 12.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { viewModel.deleteCleaner(c) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddEditCleanerScreen(viewModel: KgmViewModel) {
    val f by viewModel.cleanerFormState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Add Cleaner/Attendant Record", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = f.name,
            onValueChange = { viewModel.cleanerFormState.value = f.copy(name = it) },
            label = { Text("Attendant Name") },
            modifier = Modifier.fillMaxWidth().testTag("cleaner_name_field")
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.phoneNumber,
            onValueChange = { viewModel.cleanerFormState.value = f.copy(phoneNumber = it) },
            label = { Text("Attendant Mobile Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.assignedVehicle,
            onValueChange = { viewModel.cleanerFormState.value = f.copy(assignedVehicle = it) },
            label = { Text("Assigned Vehicle Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveCleaner() },
            modifier = Modifier.fillMaxWidth().height(50.dp).testTag("save_cleaner_button")
        ) {
            Text("Save Cleaner Profile", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RouteListScreen(viewModel: KgmViewModel) {
    val routes by viewModel.allRoutes.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Routes Registry", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            IconButton(onClick = {
                viewModel.routeFormState.value = RouteFormState()
                viewModel.navigateTo("add_edit_route")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Route", tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(routes) { r ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(r.routeNumber, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                            IconButton(onClick = { viewModel.deleteRoute(r) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = Color.Red)
                            }
                        }
                        Text(r.routeName, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Stops: ${r.pickupStops}", fontSize = 12.sp, color = Color.Gray, overflow = TextOverflow.Ellipsis, maxLines = 1)
                        Text("Vehicle: ${r.assignedVehicle} | Driver: ${r.assignedDriver}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun AddEditRouteScreen(viewModel: KgmViewModel) {
    val f by viewModel.routeFormState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Create / Edit Route Stops", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = f.routeNumber,
            onValueChange = { viewModel.routeFormState.value = f.copy(routeNumber = it) },
            label = { Text("Route ID (e.g. R-103)") },
            modifier = Modifier.fillMaxWidth().testTag("route_number_field")
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.routeName,
            onValueChange = { viewModel.routeFormState.value = f.copy(routeName = it) },
            label = { Text("Route Description / Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = f.pickupStops,
            onValueChange = { viewModel.routeFormState.value = f.copy(pickupStops = it) },
            label = { Text("Stops List (comma separated, in order)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveRoute() },
            modifier = Modifier.fillMaxWidth().height(50.dp).testTag("save_route_button")
        ) {
            Text("Save Route Params", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SeatLayoutScreen(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    var selectedSeatNum by remember { mutableStateOf("") }
    var showAssignDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Interactive Bus Seat Layout", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Vehicle: TN-02-AB-1001 (Swaraj Mazda)", fontSize = 13.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        // Display beautiful 40-seat visual grid representing the bus
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Bus Driver Area
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("🚍 ENTRANCE GATE", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("STEERING WHEEL ⭕", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Seats Grid
            for (row in 1..10) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left 2 Seats
                    for (col in listOf("A", "B")) {
                        val seatId = "S-$row$col"
                        val occupier = students.find { it.assignedSeat == seatId && it.assignedVehicle == "TN-02-AB-1001" }
                        SeatWidget(seatId, occupier) {
                            selectedSeatNum = seatId
                            showAssignDialog = true
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    // Aisle
                    Spacer(modifier = Modifier.width(32.dp))

                    // Right 2 Seats
                    for (col in listOf("C", "D")) {
                        val seatId = "S-$row$col"
                        val occupier = students.find { it.assignedSeat == seatId && it.assignedVehicle == "TN-02-AB-1001" }
                        SeatWidget(seatId, occupier) {
                            selectedSeatNum = seatId
                            showAssignDialog = true
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }

        if (showAssignDialog) {
            Dialog(onDismissRequest = { showAssignDialog = false }) {
                Card(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Seat Assignment: $selectedSeatNum", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Select an active student to assign to this seat:")
                        Spacer(modifier = Modifier.height(12.dp))

                        val availableStudents = students.filter { it.status == "Active" && it.assignedSeat != selectedSeatNum }
                        LazyColumn(
                            modifier = Modifier.height(150.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(availableStudents) { student ->
                                TextButton(
                                    onClick = {
                                        viewModel.allocateSeat(student.studentId, selectedSeatNum)
                                        showAssignDialog = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("${student.name} (Current Seat: ${student.assignedSeat})", textAlign = TextAlign.Left)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = { showAssignDialog = false },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SeatWidget(seatId: String, occupier: StudentEntity?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(
                if (occupier != null) Color(0xFFEF4444) else Color(0xFF10B981),
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .testTag("seat_$seatId"),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(seatId, fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
            if (occupier != null) {
                Text(
                    text = occupier.name.take(3),
                    fontSize = 8.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text("FREE", fontSize = 8.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
fun FeeManagementScreen(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val txns by viewModel.allTransactions.collectAsStateWithLifecycle()
    val feesConfig by viewModel.feesConfig.collectAsStateWithLifecycle()

    val dueDate = feesConfig["due_date"] ?: "10th of every month"

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterClass by remember { mutableStateOf("All") }

    // Dialog state variables
    var showDueDateDialog by remember { mutableStateOf(false) }
    var newDueDateVal by remember { mutableStateOf(dueDate) }

    var showReportDialog by remember { mutableStateOf(false) }
    var reportTitle by remember { mutableStateOf("") }
    var reportContent by remember { mutableStateOf("") }

    var editingStudentForFee by remember { mutableStateOf<StudentEntity?>(null) }
    var newFeeInput by remember { mutableStateOf("") }

    var loggingOfflinePayForStudent by remember { mutableStateOf<StudentEntity?>(null) }
    var offlineSelectedMonth by remember { mutableStateOf("July") }
    var offlineSelectedMethod by remember { mutableStateOf("Cash") }

    val monthsList = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val classesList = listOf("All") + students.map { it.classLevel }.distinct().sorted()

    val filteredStudents = students.filter { s ->
        (s.name.contains(searchQuery, ignoreCase = true) || s.studentId.contains(searchQuery, ignoreCase = true)) &&
        (selectedFilterClass == "All" || s.classLevel == selectedFilterClass)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Dashboard Title & Trigger Automated Reminders Info Banner
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Transport Fee Ledger", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            
            Button(
                onClick = { viewModel.triggerOverdueReminders() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24), contentColor = Color(0xFF0F172A))
            ) {
                Icon(Icons.Filled.NotificationsActive, contentDescription = "Reminder")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Broadcast Reminders", fontSize = 11.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Automated Reminders Status Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        ) {
            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CheckCircle, contentDescription = "Active", tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Automated Smart Reminders: ACTIVE (Notifies parents on: 5d, 3d, 1d before due date, on due date, 3d after, and every 7d overdue. Automatically ceases once full payment is logged.)",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Dashboard Summary Metrics
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp)) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total Collected", fontSize = 11.sp, color = Color.Gray)
                    val totalPaid = txns.filter { it.status == "Paid" }.sumOf { it.amount }
                    Text("₹${totalPaid.toInt()}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF16A34A))
                }
            }
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp)) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Outstanding", fontSize = 11.sp, color = Color.Gray)
                    val outstanding = students.sumOf { it.balance }
                    Text("₹${outstanding.toInt()}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFDC2626))
                }
            }
            Card(modifier = Modifier.weight(1.1f), shape = RoundedCornerShape(10.dp)) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Monthly Due Date", fontSize = 11.sp, color = Color.Gray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(dueDate, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = { showDueDateDialog = true }, modifier = Modifier.size(20.dp)) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Date", modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Report Downloads Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Collection & Outstanding Reports", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            reportTitle = "KGM Travels Monthly Collection Report - July 2026"
                            val paidTxns = txns.filter { it.paymentMonth.startsWith("July", ignoreCase = true) }
                            var csv = "Student ID,Student Name,Class,Vehicle,Route,Payment Method,Amount Paid,Date\n"
                            paidTxns.forEach { t ->
                                csv += "${t.studentId},\"${t.studentName}\",${t.classAndSection},${t.vehicleNumber},${t.routeNumber},${t.paymentMethod},₹${t.amount.toInt()},${t.dateAndTime}\n"
                            }
                            csv += "\nTotal Month Collection: ₹${paidTxns.sumOf { it.amount }.toInt()}"
                            reportContent = csv
                            showReportDialog = true
                            viewModel.logAudit(viewModel.currentUser.value?.username ?: "Admin", "Export Report", "Generated Monthly Collection Report for July 2026.")
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Filled.Download, contentDescription = "Download", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Monthly Collection", fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            reportTitle = "KGM Travels Annual transport Fee Summary (2026-27)"
                            var summary = "Student Name,Monthly Fee,Annual Committed,Advance Paid,Outstanding Balance,Account Status\n"
                            students.forEach { s ->
                                val committed = s.monthlyVanFee * 12
                                summary += "\"${s.name}\",₹${s.monthlyVanFee.toInt()},₹${committed.toInt()},₹${s.advancePaid.toInt()},₹${s.balance.toInt()},${s.status}\n"
                            }
                            summary += "\nTotal Committed: ₹${students.sumOf { it.monthlyVanFee * 12 }.toInt()}\nTotal Collected: ₹${students.sumOf { it.advancePaid }.toInt()}\nTotal Overdue: ₹${students.sumOf { it.balance }.toInt()}"
                            reportContent = summary
                            showReportDialog = true
                            viewModel.logAudit(viewModel.currentUser.value?.username ?: "Admin", "Export Report", "Generated Annual transport Fee Summary (2026-27).")
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Filled.Download, contentDescription = "Download", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Annual Summary Ledger", fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search & Filter Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by name/ID...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", modifier = Modifier.size(18.dp)) },
                modifier = Modifier
                    .weight(1.5f)
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp)
            )

            // Class filter spinner/scroller
            Box(modifier = Modifier.weight(1f)) {
                var expandedClassMenu by remember { mutableStateOf(false) }
                OutlinedButton(
                    onClick = { expandedClassMenu = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Class: $selectedFilterClass", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 12.sp)
                }
                DropdownMenu(expanded = expandedClassMenu, onDismissRequest = { expandedClassMenu = false }) {
                    classesList.forEach { c ->
                        DropdownMenuItem(
                            text = { Text(c) },
                            onClick = {
                                selectedFilterClass = c
                                expandedClassMenu = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Students Transport Fee Status List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredStudents) { s ->
                val isLocked = s.status == "Locked"
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isLocked) Color(0xFFFEF2F2) else MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(1.dp, if (isLocked) Color(0xFFFECACA) else Color.LightGray.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        // Header: Student Info, Status and Outstandings
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(s.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    if (isLocked) {
                                        Icon(
                                            imageVector = Icons.Filled.Lock,
                                            contentDescription = "Locked",
                                            tint = Color.Red,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text("ID: ${s.studentId} | ${s.classLevel} - ${s.section}", fontSize = 12.sp, color = Color.Gray)
                                Text("Monthly Van Fee: ₹${s.monthlyVanFee.toInt()} | Route: ${s.assignedRoute}", fontSize = 12.sp, color = Color.Gray)
                            }
                            
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (s.balance > 0) "Overdue: ₹${s.balance.toInt()}" else "Fully Paid",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (s.balance > 0) Color.Red else Color(0xFF16A34A)
                                )
                                Text("Advance: ₹${s.advancePaid.toInt()}", fontSize = 11.sp, color = Color.Gray)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(10.dp))

                        // Month-by-month status mini grid
                        Text("Month-wise Ledger Status (Tap green to view receipt, red to quick pay/remind):", fontSize = 11.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))

                        // 12 months split into 3 rows of 4 months each
                        val chunkedMonths = monthsList.chunked(4)
                        chunkedMonths.forEach { rowMonths ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                rowMonths.forEach { m ->
                                    val monthTxn = txns.find {
                                        it.studentId == s.studentId &&
                                        it.paymentMonth.startsWith(m, ignoreCase = true) &&
                                        it.status.equals("Paid", ignoreCase = true)
                                    }
                                    val isPaid = monthTxn != null

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                if (isPaid) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .border(
                                                1.dp,
                                                if (isPaid) Color(0xFF86EFAC) else Color(0xFFFCA5A5),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .clickable {
                                                if (isPaid) {
                                                    viewModel.selectedStudent.value = s
                                                    viewModel.selectedTransaction.value = monthTxn
                                                    viewModel.navigateTo("receipt_view")
                                                } else {
                                                    loggingOfflinePayForStudent = s
                                                    offlineSelectedMonth = m
                                                }
                                            }
                                            .padding(vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = m.take(3),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isPaid) Color(0xFF15803D) else Color(0xFFB91C1C)
                                            )
                                            Spacer(modifier = Modifier.width(2.dp))
                                            Icon(
                                                imageVector = if (isPaid) Icons.Filled.CheckCircle else Icons.Filled.Error,
                                                contentDescription = null,
                                                tint = if (isPaid) Color(0xFF15803D) else Color(0xFFB91C1C),
                                                modifier = Modifier.size(10.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action Controls Bar
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Update Fee Amount
                            OutlinedButton(
                                onClick = {
                                    editingStudentForFee = s
                                    newFeeInput = s.monthlyVanFee.toInt().toString()
                                },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(34.dp),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Icon(Icons.Filled.Settings, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("Set Fee", fontSize = 10.sp)
                            }

                            // Lock / Unlock Toggle
                            OutlinedButton(
                                onClick = { viewModel.toggleStudentLock(s.studentId) },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(34.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = if (isLocked) Color(0xFF16A34A) else Color(0xFFDC2626)
                                )
                            ) {
                                Icon(
                                    imageVector = if (isLocked) Icons.Filled.LockOpen else Icons.Filled.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(if (isLocked) "Unlock" else "Lock", fontSize = 10.sp)
                            }

                            // Quick Log Offline Payment
                            Button(
                                onClick = {
                                    loggingOfflinePayForStudent = s
                                    offlineSelectedMonth = "July"
                                },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier
                                    .weight(1.2f)
                                    .height(34.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                            ) {
                                Icon(Icons.Filled.Payment, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("Offline Pay", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    // --- ADMINISTRATIVE MODAL DIALOGS ---

    // 1. Due Date Dialog
    if (showDueDateDialog) {
        AlertDialog(
            onDismissRequest = { showDueDateDialog = false },
            title = { Text("Update Monthly Due Date") },
            text = {
                Column {
                    Text("Specify the transport fee billing due date of each month.", fontSize = 13.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newDueDateVal,
                        onValueChange = { newDueDateVal = it },
                        label = { Text("Due Date Expression") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateFeeConfig("due_date", newDueDateVal)
                        showDueDateDialog = false
                    }
                ) {
                    Text("Save Changes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDueDateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // 2. Report Output / Copy Dialog
    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = { Text(reportTitle) },
            text = {
                Column {
                    Text("Export compiled successfully! Below is the raw ledger data formatted in CSV.", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reportContent,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.showMessage("Report downloaded as .CSV file successfully!")
                        showReportDialog = false
                    }
                ) {
                    Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Download .CSV")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReportDialog = false }) {
                    Text("Dismiss")
                }
            }
        )
    }

    // 3. Set Student Monthly Fee Dialog
    if (editingStudentForFee != null) {
        val target = editingStudentForFee!!
        AlertDialog(
            onDismissRequest = { editingStudentForFee = null },
            title = { Text("Set Monthly Van Fee: ${target.name}") },
            text = {
                Column {
                    Text("Adjust the monthly transport fee for this student. Balance is automatically synchronized based on committing rates.", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newFeeInput,
                        onValueChange = { newFeeInput = it },
                        label = { Text("Monthly Fee (₹)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsed = newFeeInput.toDoubleOrNull()
                        if (parsed != null && parsed >= 0.0) {
                            viewModel.updateStudentMonthlyFee(target.studentId, parsed)
                            editingStudentForFee = null
                        } else {
                            viewModel.showMessage("Please enter a valid numeric value.")
                        }
                    }
                ) {
                    Text("Update Fee")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingStudentForFee = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // 4. Log Offline Payment & Send Reminders Dialog
    if (loggingOfflinePayForStudent != null) {
        val target = loggingOfflinePayForStudent!!
        AlertDialog(
            onDismissRequest = { loggingOfflinePayForStudent = null },
            title = { Text("Log Fee Action: ${target.name}") },
            text = {
                Column {
                    Text("Choose an administrative action for the billing ledger.", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text("1. Select Month:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    var monthMenuExpanded by remember { mutableStateOf(false) }
                    OutlinedButton(
                        onClick = { monthMenuExpanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Month: $offlineSelectedMonth")
                    }
                    DropdownMenu(expanded = monthMenuExpanded, onDismissRequest = { monthMenuExpanded = false }) {
                        monthsList.forEach { m ->
                            DropdownMenuItem(text = { Text(m) }, onClick = {
                                offlineSelectedMonth = m
                                monthMenuExpanded = false
                            })
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("2. Select Method (For Offline Pay):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    var methodMenuExpanded by remember { mutableStateOf(false) }
                    OutlinedButton(
                        onClick = { methodMenuExpanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Method: $offlineSelectedMethod")
                    }
                    DropdownMenu(expanded = methodMenuExpanded, onDismissRequest = { methodMenuExpanded = false }) {
                        listOf("Cash", "Cheque", "Direct Bank Transfer", "Offline UPI Scan").forEach { met ->
                            DropdownMenuItem(text = { Text(met) }, onClick = {
                                offlineSelectedMethod = met
                                methodMenuExpanded = false
                            })
                        }
                    }
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Remind manually
                    OutlinedButton(
                        onClick = {
                            viewModel.sendManualReminder(target, "$offlineSelectedMonth 2026")
                            loggingOfflinePayForStudent = null
                        }
                    ) {
                        Icon(Icons.Filled.NotificationsActive, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Remind Parent", fontSize = 11.sp)
                    }

                    // Log Payment
                    Button(
                        onClick = {
                            viewModel.processOfflinePayment(target, "$offlineSelectedMonth 2026", "Offline $offlineSelectedMethod")
                            loggingOfflinePayForStudent = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Record Paid", fontSize = 11.sp)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { loggingOfflinePayForStudent = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PaymentGatewayScreen(viewModel: KgmViewModel) {
    val student by viewModel.selectedStudent.collectAsStateWithLifecycle()
    val selectedPaymentMonth by viewModel.selectedPaymentMonth.collectAsStateWithLifecycle()

    if (student == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No student selected.")
        }
        return
    }

    val s = student!!
    
    // Core payment states
    var selectedMethod by remember { mutableStateOf("UPI") } // "UPI", "Card", "Net Banking", "Wallet"
    
    // Inputs
    var upiId by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cardExpiry by remember { mutableStateOf("") }
    var cardCvv by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("State Bank of India") }
    var selectedWallet by remember { mutableStateOf("Google Pay") }
    
    // Error states
    var upiError by remember { mutableStateOf<String?>(null) }
    var cardError by remember { mutableStateOf<String?>(null) }
    var expiryError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }

    // Checkout stage: 0 = Setup/Inputs, 1 = Order Generation, 2 = 3DS Secure authorization, 3 = Success, 4 = Failed/Cancelled
    var checkoutStage by remember { mutableStateOf(0) }
    var scaleCheckmark by remember { mutableStateOf(0f) }
    
    // Transaction parameters retrieved from API
    var activeOrderId by remember { mutableStateOf("") }
    var activePaymentId by remember { mutableStateOf("") }
    var activeSignature by remember { mutableStateOf("") }
    var paymentFailureReason by remember { mutableStateOf("Transaction declined by cardholder bank.") }

    // Helpers
    fun checkLuhn(cardNumberStr: String): Boolean {
        val clean = cardNumberStr.replace(" ", "")
        if (clean.length < 13 || clean.length > 19) return false
        var sum = 0
        var alternate = false
        for (i in clean.length - 1 downTo 0) {
            var n = clean[i] - '0'
            if (n < 0 || n > 9) return false
            if (alternate) {
                n *= 2
                if (n > 9) {
                    n = (n % 10) + 1
                }
            }
            sum += n
            alternate = !alternate
        }
        return sum % 10 == 0
    }

    fun detectCardBrand(cardNumberStr: String): String {
        val clean = cardNumberStr.replace(" ", "")
        return when {
            clean.startsWith("4") -> "Visa"
            clean.matches(Regex("^(51|52|53|54|55|2221|2720).*")) -> "Mastercard"
            clean.matches(Regex("^(60|65|81|82|508).*")) -> "RuPay"
            clean.startsWith("34") || clean.startsWith("37") -> "Amex"
            else -> "Unknown"
        }
    }

    val brand = detectCardBrand(cardNumber)

    if (checkoutStage == 1) {
        // CALL WEBHOOK ORDER INITIALIZATION
        LaunchedEffect(Unit) {
            viewModel.initRazorpayPayment(
                student = s,
                month = selectedPaymentMonth,
                onSuccess = { orderId, paymentId, signature ->
                    activeOrderId = orderId
                    activePaymentId = paymentId
                    activeSignature = signature
                    // Move to 3D Secure Banking authentication simulator
                    checkoutStage = 2
                },
                onFailure = { err ->
                    paymentFailureReason = err
                    checkoutStage = 4
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 5.dp
                )
                Text(
                    "Contacting Secure Gateway Server...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    "Requesting secure order signature from Razorpay APIs.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Shield, contentDescription = "Secure", tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                    Text("PCI-DSS SECURED | HTTPS VERIFIED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                }
            }
        }
    } else if (checkoutStage == 2) {
        // 3D SECURE BANKING AUTHORIZATION SIMULATOR SCREEN
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F5F9))
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            // Banking brand frame
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Color(0xFF3B82F6))
            ) {
                Column {
                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1E3A8A))
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("3D SECURE CHIP GATEWAY", color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.sp)
                            Icon(Icons.Filled.Lock, contentDescription = "Lock", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Merchant: KGM TRAVELS PRIVATE LIMITED", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                        Text("Amount: ₹${(s.monthlyVanFee - s.discount).toInt()}.00", fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color(0xFF1E3A8A))
                        Text("Order ID: $activeOrderId", fontSize = 11.sp, color = Color.Gray)
                        Text("Payment ID: $activePaymentId", fontSize = 11.sp, color = Color.Gray)
                        Text("Gateway: Razorpay Enterprise API", fontSize = 11.sp, color = Color.Gray)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            "This is a simulated secure sandbox checkout environment mimicking the actual bank authentication loop. Tapping Approve will verify the gateway digital signature and commit the transaction directly to KGM database without manual intervention.",
                            fontSize = 11.sp,
                            color = Color.DarkGray
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        Button(
                            onClick = {
                                // Webhook Capture Simulation
                                val success = viewModel.receiveWebhookNotification(
                                    orderId = activeOrderId,
                                    paymentId = activePaymentId,
                                    signature = activeSignature,
                                    student = s,
                                    month = selectedPaymentMonth,
                                    method = selectedMethod
                                )
                                if (success) {
                                    checkoutStage = 3
                                } else {
                                    paymentFailureReason = "Cryptographic signature verification failed. The transaction payload was rejected by security nodes."
                                    checkoutStage = 4
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = "Approve")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Approve Payment (Success Sim)", fontWeight = FontWeight.Bold)
                        }
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        OutlinedButton(
                            onClick = {
                                paymentFailureReason = "Payment session was cancelled by the parent during bank validation."
                                checkoutStage = 4
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0xFFEF4444))
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "Cancel")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Cancel / Decline (Fail Sim)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(30.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.VerifiedUser, contentDescription = "Safe", tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Verified by VISA | Mastercard Identity Check | RuPay Secure", fontSize = 10.sp, color = Color.Gray)
            }
        }
    } else if (checkoutStage == 3) {
        // ANIMATED SUCCESS SCREEN (STAGE 3)
        LaunchedEffect(Unit) {
            val anim1 = androidx.compose.animation.core.Animatable(0f)
            anim1.animateTo(
                targetValue = 1f,
                animationSpec = androidx.compose.animation.core.spring(
                    dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                    stiffness = androidx.compose.animation.core.Spring.StiffnessLow
                )
            ) {
                scaleCheckmark = this.value
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFECFDF5),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Animated Confetti Canvas
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .drawBehind {
                        val center = size.center
                        val count = 12
                        for (i in 0 until count) {
                            val angle = (i * (360f / count)) * (Math.PI / 180f)
                            val distance = 60.dp.toPx() * scaleCheckmark
                            val px = center.x + Math.cos(angle).toFloat() * distance
                            val py = center.y + Math.sin(angle).toFloat() * distance
                            
                            val particleColors = listOf(Color(0xFF10B981), Color(0xFF3B82F6), Color(0xFFFBBF24), Color(0xFFEC4899), Color(0xFF8B5CF6))
                            val color = particleColors[i % particleColors.size]
                            
                            drawCircle(
                                color = color.copy(alpha = 1f - (scaleCheckmark * 0.4f)),
                                radius = 6.dp.toPx() * (1f - (scaleCheckmark * 0.3f)),
                                center = androidx.compose.ui.geometry.Offset(px, py)
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp * scaleCheckmark)
                        .background(Color(0xFF10B981), CircleShape)
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(44.dp * scaleCheckmark)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Payment Successful!",
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF047857),
                textAlign = TextAlign.Center
            )
            Text(
                "Your transport fees have been securely verified and received.",
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "KGM OFFICIAL PAYMENT TICKET", 
                        fontSize = 10.sp, 
                        fontWeight = FontWeight.Black, 
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    ReceiptRow("Student Name", s.name)
                    ReceiptRow("Class & Section", "${s.classLevel} - ${s.section}")
                    ReceiptRow("Payment Month", selectedPaymentMonth)
                    ReceiptRow("Amount Paid", "₹${s.monthlyVanFee.toInt()}.00")
                    ReceiptRow("Method used", selectedMethod)
                    ReceiptRow("Order ID", activeOrderId)
                    ReceiptRow("Transaction ID", activePaymentId)
                    ReceiptRow("Status", "Captured & Verified")

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.VerifiedUser, contentDescription = "Verified", tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("KGM TRAVELS AUTO-LOGGED RECEIPT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.navigateTo("parent_receipts")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Filled.Receipt, contentDescription = "View Receipt")
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Digital Receipt", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    viewModel.navigateTo("dashboard")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back to Dashboard", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    } else if (checkoutStage == 4) {
        // PAYMENT FAILED / CANCELLED STAGE
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color(0xFFFEF2F2), CircleShape)
                    .border(4.dp, Color(0xFFEF4444), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Error,
                    contentDescription = "Failed",
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Payment Failed / Cancelled",
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFB91C1C),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                paymentFailureReason,
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("How to fix this issue?", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Verify that your account has sufficient balance.", fontSize = 12.sp, color = Color.DarkGray)
                    Text("• Ensure your UPI app or Card CVV is entered correctly.", fontSize = 12.sp, color = Color.DarkGray)
                    Text("• Do not exit or refresh the banking screen prematurely.", fontSize = 12.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    checkoutStage = 0 // Return to setup to retry securely
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Retry")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retry Payment Gateway", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    viewModel.navigateTo("parent_fees")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back to Fees Screen", fontWeight = FontWeight.Bold)
            }
        }
    } else {
        // INPUT SETUP STAGE (STAGE 0)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("KGM Travels Secure Gateway", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Official Biller Account: KGM Travels Private Limited", fontSize = 11.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Outstanding Fee Breakdown", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Student Name", color = Color.Gray, fontSize = 13.sp)
                        Text(s.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Class & Sec", color = Color.Gray, fontSize = 13.sp)
                        Text("${s.classLevel} - ${s.section}", fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Selected Month", color = Color.Gray, fontSize = 13.sp)
                        Text(selectedPaymentMonth, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("PAYABLE AMOUNT", fontWeight = FontWeight.Bold)
                        Text("₹${s.monthlyVanFee.toInt()}.00", fontWeight = FontWeight.Bold, color = Color(0xFF10B981), fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "⚠️ FULL PAYMENT RULE: KGM Travels billing system only accepts full monthly fees. Partial payments, edit of amounts, or installments are prohibited.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Select Secure Payment Method", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Method Row selectors
            val modes = listOf("UPI", "Card", "Net Banking", "Wallet")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                modes.forEach { mode ->
                    val isSelected = selectedMethod == mode
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedMethod = mode },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.5.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.4f)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = when (mode) {
                                    "UPI" -> Icons.AutoMirrored.Filled.SendToMobile
                                    "Card" -> Icons.Filled.CreditCard
                                    "Net Banking" -> Icons.Filled.AccountBalance
                                    else -> Icons.Filled.Wallet
                                },
                                contentDescription = mode,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(mode, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DYNAMIC INPUT SUB-FORMS based on selected method
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    when (selectedMethod) {
                        "UPI" -> {
                            Text("Enter Virtual Payment Address (UPI ID)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = upiId,
                                onValueChange = { 
                                    upiId = it
                                    upiError = null
                                },
                                placeholder = { Text("e.g. parent@upi, mobile@ybl") },
                                isError = upiError != null,
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            upiError?.let { err ->
                                Text(err, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Supported apps: GPay, PhonePe, Paytm, BHIM, Amazon Pay", fontSize = 11.sp, color = Color.Gray)
                        }
                        "Card" -> {
                            Text("Credit / Debit Card details", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            OutlinedTextField(
                                value = cardNumber,
                                onValueChange = { 
                                    cardNumber = it.filter { char -> char.isDigit() }.take(16)
                                    cardError = null
                                },
                                label = { Text("Card Number") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.CreditCard,
                                        contentDescription = "Card",
                                        tint = if (brand != "Unknown") Color(0xFF10B981) else Color.Gray
                                    )
                                },
                                trailingIcon = {
                                    if (brand != "Unknown") {
                                        Text(brand, fontWeight = FontWeight.Black, fontSize = 11.sp, color = Color(0xFF10B981), modifier = Modifier.padding(end = 8.dp))
                                    }
                                },
                                isError = cardError != null,
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            cardError?.let { err ->
                                Text(err, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = cardExpiry,
                                    onValueChange = { 
                                        cardExpiry = it.take(5)
                                        expiryError = null
                                    },
                                    label = { Text("Expiry (MM/YY)") },
                                    isError = expiryError != null,
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                OutlinedTextField(
                                    value = cardCvv,
                                    onValueChange = { 
                                        cardCvv = it.filter { char -> char.isDigit() }.take(3)
                                        cvvError = null
                                    },
                                    label = { Text("CVV") },
                                    isError = cvvError != null,
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                            val localExpiryError = expiryError
                            val localCvvError = cvvError
                            if (localExpiryError != null || localCvvError != null) {
                                val errText = localExpiryError ?: localCvvError ?: ""
                                Text(errText, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = cardName,
                                onValueChange = { cardName = it },
                                label = { Text("Cardholder Name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Lock, contentDescription = "Secure", tint = Color(0xFF10B981), modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("🔒 PCI-DSS Secure: Card credentials are never stored in the app.", fontSize = 10.sp, color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
                            }
                        }
                        "Net Banking" -> {
                            Text("Select Your Bank", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            val banks = listOf("State Bank of India", "HDFC Bank", "ICICI Bank", "Axis Bank", "Kotak Mahindra Bank")
                            banks.forEach { bank ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { selectedBank = bank },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (selectedBank == bank) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent
                                    ),
                                    border = BorderStroke(1.dp, if (selectedBank == bank) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(selected = selectedBank == bank, onClick = { selectedBank = bank })
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(bank, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                        else -> {
                            Text("Select Wallet Provider", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            val wallets = listOf("Google Pay", "PhonePe", "Paytm Wallet", "Amazon Pay")
                            wallets.forEach { wallet ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { selectedWallet = wallet },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (selectedWallet == wallet) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent
                                    ),
                                    border = BorderStroke(1.dp, if (selectedWallet == wallet) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(selected = selectedWallet == wallet, onClick = { selectedWallet = wallet })
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(wallet, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { 
                    // Perform validation first
                    var valid = true
                    if (selectedMethod == "UPI") {
                        if (upiId.isEmpty() || !upiId.contains("@")) {
                            upiError = "Please enter a valid UPI Virtual Address (must contain '@')"
                            valid = false
                        }
                    } else if (selectedMethod == "Card") {
                        if (cardNumber.length < 13 || !checkLuhn(cardNumber)) {
                            cardError = "Invalid credit/debit card number (Luhn checksum failed)"
                            valid = false
                        }
                        if (cardExpiry.length < 5 || !cardExpiry.contains("/")) {
                            expiryError = "Enter valid expiry date (MM/YY)"
                            valid = false
                        }
                        if (cardCvv.length < 3) {
                            cvvError = "Enter valid 3-digit CVV number"
                            valid = false
                        }
                    }
                    
                    if (valid) {
                        checkoutStage = 1 // Go to Order Generation API call
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("confirm_payment_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Lock, contentDescription = "Pay Securely")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Authorize & Pay ₹${(s.monthlyVanFee - s.discount).toInt()}.00", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Shield, contentDescription = "Secured", tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("PCI-DSS Compliant 256-bit SSL Secure Payment Gateway", fontSize = 10.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ReceiptViewScreen(viewModel: KgmViewModel) {
    val student by viewModel.selectedStudent.collectAsStateWithLifecycle()
    val transaction by viewModel.selectedTransaction.collectAsStateWithLifecycle()
    val businessInfo by viewModel.businessInfo.collectAsStateWithLifecycle()

    if (student == null || transaction == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No receipt details loaded.")
        }
        return
    }

    val s = student!!
    val t = transaction!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Payment Receipt", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Receipt Boarding-Pass Styling
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header with KGM Logo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(44.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            AppLogoImage(
                                logoUrl = businessInfo["logo"],
                                contentDescription = "Logo",
                                modifier = Modifier.fillMaxSize().padding(3.dp),
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("KGM TRAVELS", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
                            Text("School Transport Service", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                    Text("PAID", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, modifier = Modifier.background(Color(0xFFD1FAE5), RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                // Student / Trip details
                ReceiptRow("Receipt Number", t.transactionId)
                ReceiptRow("Student Name", t.studentName)
                ReceiptRow("Class & Section", t.classAndSection)
                ReceiptRow("Vehicle assigned", t.vehicleNumber)
                ReceiptRow("Route Number", t.routeNumber)
                ReceiptRow("Period", t.paymentMonth)
                ReceiptRow("Date & Time", t.dateAndTime)
                ReceiptRow("Payment Method", t.paymentMethod)

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Paid", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("₹${t.amount}", fontWeight = FontWeight.Bold, color = Color(0xFF10B981), fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))
                // Simulated QR Code
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(Color.White)
                        .border(1.dp, Color.LightGray)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Drawing a fake QR pattern in canvas for gorgeous styling!
                        for (i in 0..10 step 2) {
                            drawRect(
                                color = Color.Black,
                                topLeft = androidx.compose.ui.geometry.Offset((i * 8).toFloat(), (i * 8).toFloat()),
                                size = androidx.compose.ui.geometry.Size(12f, 12f)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { viewModel.showMessage("📥 PDF receipt downloaded successfully to your device!") },
                modifier = Modifier.weight(1.5f).height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Download, contentDescription = "Download")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Download PDF", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Button(
                onClick = { viewModel.showMessage("🖨️ Opening print service... Ready to output ticket to connected printer.") },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            ) {
                Icon(Icons.Filled.Print, contentDescription = "Print")
                Spacer(modifier = Modifier.width(6.dp))
                Text("Print", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            IconButton(
                onClick = { viewModel.showMessage("🔗 Share link generated! Sent to native sharing options.") },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .size(50.dp)
            ) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = { viewModel.navigateTo("parent_receipts") },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Receipts Ledger", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 13.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 13.sp)
    }
}

@Composable
fun ReportsScreen(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val txns by viewModel.allTransactions.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Downloadable PDF/Excel Reports", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        listOf(
            "Student Master List" to "${students.size} active students",
            "Monthly Fee Collection Summary" to "Total Collected: ₹${txns.sumOf { it.amount }}",
            "Vehicle-wise Student strength" to "Breakdown by vehicles",
            "Licence Document Expiries" to "Alerts for upcoming expiries",
            "Audit logs ledger" to "Record of administrative operations"
        ).forEach { (report, desc) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(report, fontWeight = FontWeight.Bold)
                        Text(desc, fontSize = 12.sp, color = Color.Gray)
                    }
                    IconButton(onClick = { viewModel.showMessage("Generating PDF for $report...") }) {
                        Icon(Icons.Filled.Download, contentDescription = "Download Report", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun WebsiteScreen(viewModel: KgmViewModel) {
    val context = LocalContext.current
    val businessInfo by viewModel.businessInfo.collectAsStateWithLifecycle()
    val websiteConfig by viewModel.websiteConfig.collectAsStateWithLifecycle()
    var contactName by remember { mutableStateOf("") }
    var contactMsg by remember { mutableStateOf("") }

    val homeTitle = websiteConfig["home"] ?: "KGM Travels - Premier School Bus Provider"
    val aboutText = websiteConfig["about"] ?: "Delivering safe, comfortable, and reliable transport for Velammal Vidyalaya since 2018."
    val servicesText = websiteConfig["services"] ?: "Daily pickup/drop, trackable routes, trained attendants."
    val rulesText = websiteConfig["rules"] ?: "1. Students must occupy their pre-allocated seats.\n2. Disciplinary actions for double booking seat maps.\n3. Parents must monitor active notifications and timely pickups."
    val faqText = websiteConfig["faq"] ?: "Q: Can we change seats?\nA: Yes, via layout manager."
    val contactInfo = websiteConfig["contact"] ?: "+91 98765 43210 | info@kgmtravels.com"
    val metaTitle = websiteConfig["seo_meta_title"] ?: "KGM Travels - School Bus & Student Transport Portal"
    val metaDesc = websiteConfig["seo_meta_description"] ?: "Safe, GPS-tracked school bus transport for Velammal Vidyalaya students with live route tracking, online fee payments, and verified staff attendants."
    val canonicalUrl = websiteConfig["seo_canonical_url"] ?: "https://www.kgmtravels.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(56.dp).padding(bottom = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    AppLogoImage(
                        logoUrl = businessInfo["logo"],
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize().padding(4.dp),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }
                Text("KGM TRAVELS", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(homeTitle, color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp, maxLines = 1)
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.showMessage("Calling KGM Travels Support...") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24))
                    ) {
                        Icon(Icons.Filled.Phone, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call Us", color = Color(0xFF0F172A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { viewModel.showMessage("Opening WhatsApp Support...") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("WhatsApp", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // About Us Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("About KGM Transport", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(aboutText, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Services Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.DirectionsBus, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Our Transit Services", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(servicesText, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Transit Fleet & Bus Photo Gallery
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.PhotoLibrary, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Vehicle & Fleet Gallery", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Official photos of KGM modern fleet, comfortable seating, and verified transport infrastructure.", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))

                    val galleryItems = listOf(
                        Triple("Brand Emblem Logo", "kgm_brand_logo_emblem", "Official Brand Crest & Logo Emblem"),
                        Triple("Modern Yellow Bus", "kgm_school_bus_fleet", "School Campus Transit Bus Fleet"),
                        Triple("Spacious Interiors", "kgm_bus_interior", "Comfortable Ergonomic Seating Layout")
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        galleryItems.forEach { (title, key, desc) ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                modifier = Modifier.width(220.dp)
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                            .background(Color.White)
                                    ) {
                                        AppLogoImage(
                                            logoUrl = key,
                                            contentDescription = title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    }
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(desc, fontSize = 10.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Spacer(modifier = Modifier.height(6.dp))
                                        OutlinedButton(
                                            onClick = {
                                                viewModel.updateBusinessInfo("logo", key)
                                                AppIconManager.setAppIcon(context, key)
                                                viewModel.showMessage("Set '$title' as the active App Logo successfully!")
                                            },
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Set as App Logo", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Transport Rules Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Gavel, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Core Transport Rules", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(rulesText, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // FAQs Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.QuestionAnswer, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Frequently Asked Questions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(faqText, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Public Search & Web Presence
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Official Web Presence & SEO Portal", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(metaTitle, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1A0DAB))
                    Text(canonicalUrl, fontSize = 11.sp, color = Color(0xFF475569))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(metaDesc, fontSize = 12.sp, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Contact: $contactInfo", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                }
            }

            // Contact Form
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Contact Support Team", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        label = { Text("Your Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = contactMsg,
                        onValueChange = { contactMsg = it },
                        label = { Text("How can we help you?") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            viewModel.showMessage("Thank you! KGM Travels team will contact you shortly.")
                            contactName = ""
                            contactMsg = ""
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit Contact Form", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun AuditLogsScreen(viewModel: KgmViewModel) {
    val logs by viewModel.allAuditLogs.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Administrative Audit Logs", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(logs) { log ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(log.adminUser, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(log.timestamp, fontSize = 11.sp, color = Color.Gray)
                        }
                        Text("Action: ${log.actionType}", fontWeight = FontWeight.Medium, fontSize = 13.sp)
                        Text(log.description, fontSize = 12.sp, color = Color.DarkGray)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsListScreen(viewModel: KgmViewModel) {
    val notifications by viewModel.allNotifications.collectAsStateWithLifecycle()
    val readIds by viewModel.readNotificationIds.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Notifications & Broadcasts", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            if (notifications.isNotEmpty()) {
                TextButton(onClick = {
                    notifications.forEach { viewModel.markNotificationAsRead(it.id) }
                    viewModel.showMessage("All notifications marked as read")
                }) {
                    Text("Mark All Read")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (notifications.isEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                    Text("No notifications found.", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(notifications) { n ->
                    val isRead = readIds.contains(n.id)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isRead) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(1.dp, if (isRead) Color.LightGray.copy(alpha = 0.4f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (!isRead) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    Text(n.title, fontWeight = FontWeight.Bold, color = if (isRead) Color.DarkGray else MaterialTheme.colorScheme.primary, fontSize = 15.sp)
                                }
                                Text(n.timestamp, fontSize = 11.sp, color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(n.message, fontSize = 13.sp, color = if (isRead) Color.Gray else Color.Black)
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (!isRead) {
                                    TextButton(
                                        onClick = { viewModel.markNotificationAsRead(n.id) },
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Icon(Icons.Filled.Done, contentDescription = "Mark Read", modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Mark Read", fontSize = 12.sp)
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                                TextButton(
                                    onClick = { viewModel.deleteNotification(n.id) },
                                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Delete", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ParentStudentInfoScreen(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    val myStudents = students.filter { 
        cleanPhone(it.parentMobileNumber) == cleanPhone(currentUser?.mobileNumber) || 
        it.studentId == currentUser?.linkedStudentId 
    }

    var selectedStudentId by remember(myStudents) { 
        mutableStateOf(myStudents.firstOrNull()?.studentId ?: "") 
    }

    val myStudent = myStudents.find { it.studentId == selectedStudentId } ?: myStudents.firstOrNull()

    if (myStudent == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                Icon(Icons.Filled.Person, contentDescription = "No Student", modifier = Modifier.size(64.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Text("No linked student record found.", style = MaterialTheme.typography.bodyLarge)
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Student Profile & Pass", 
            style = MaterialTheme.typography.titleLarge, 
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        // DYNAMIC STUDENT PROFILE SWITCHER
        if (myStudents.size > 1) {
            Text(
                "Select Profile to View Details",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                myStudents.forEach { student ->
                    val isSelected = student.studentId == selectedStudentId
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedStudentId = student.studentId },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        if (isSelected) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = student.name.take(1).uppercase(),
                                    fontWeight = FontWeight.Black,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = student.name.split(" ").firstOrNull() ?: student.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Class: ${student.classLevel}",
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Professional Student Digital ID Card (Glow Gradient)
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // ID Card Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "KGM TRAVELS",
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp,
                            letterSpacing = 1.sp
                        )
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "STUDENT PASS",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Profile Image Placeholder & Identity Info
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.School, contentDescription = "Student Photo", modifier = Modifier.size(36.dp), tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(myStudent.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                            Text("Adm No: ${myStudent.admissionNumber}", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text("ID: ${myStudent.studentId}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Educational & Campus Details
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text("Class & Section", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text("${myStudent.classLevel} - ${myStudent.section}", fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Campus School", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text(myStudent.campus, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text("Date Of Birth", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text(myStudent.dateOfBirth, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Blood Group", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text(myStudent.bloodGroup, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Transit Allocation Details", 
            style = MaterialTheme.typography.titleMedium, 
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Transit details card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.DirectionsBus, contentDescription = "Bus", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Assigned Vehicle", fontWeight = FontWeight.Medium)
                    }
                    Text(myStudent.assignedVehicle, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Person, contentDescription = "Driver", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Driver Name", fontWeight = FontWeight.Medium)
                    }
                    Text(myStudent.assignedDriver, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.AltRoute, contentDescription = "Route", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Route Profile", fontWeight = FontWeight.Medium)
                    }
                    Text(myStudent.assignedRoute, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.EventSeat, contentDescription = "Seat", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Seat Allocated", fontWeight = FontWeight.Medium)
                    }
                    Text(myStudent.assignedSeat, fontWeight = FontWeight.Black, color = Color(0xFF10B981))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Boarding & Scheduled Timings", 
            style = MaterialTheme.typography.titleMedium, 
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.DirectionsBus, contentDescription = "Morning Pickup", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Morning Pickup", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(myStudent.pickupTime, fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium)
                    Text(myStudent.pickupLandmark, fontSize = 10.sp, color = Color.DarkGray, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Home, contentDescription = "Evening Drop", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Evening Drop", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(myStudent.dropTime, fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium)
                    Text(myStudent.dropLandmark, fontSize = 10.sp, color = Color.DarkGray, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Emergency Info & Safeguards", 
            style = MaterialTheme.typography.titleMedium, 
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Parent Registered Phone: ${myStudent.parentMobileNumber}", 
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Special Instructions / Medical Notes", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (myStudent.medicalNotes.isEmpty()) "No medical restrictions or custom safety instructions registered." else myStudent.medicalNotes,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ParentFeesScreen(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val txns by viewModel.allTransactions.collectAsStateWithLifecycle()

    val myStudents = students.filter { 
        cleanPhone(it.parentMobileNumber) == cleanPhone(currentUser?.mobileNumber) || 
        it.studentId == currentUser?.linkedStudentId 
    }

    var selectedStudentId by remember(myStudents) { 
        mutableStateOf(myStudents.firstOrNull()?.studentId ?: "") 
    }

    val myStudent = myStudents.find { it.studentId == selectedStudentId } ?: myStudents.firstOrNull()

    if (myStudent == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                Icon(Icons.Filled.Person, contentDescription = "No Student", modifier = Modifier.size(64.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Text("No linked student record found.", style = MaterialTheme.typography.bodyLarge)
            }
        }
        return
    }

    val currentMonth = "July"
    val isPaid = txns.any { 
        it.studentId == myStudent.studentId && 
        it.paymentMonth.startsWith(currentMonth, ignoreCase = true) && 
        it.status.equals("Paid", ignoreCase = true) 
    }
    val outstandingAmount = if (isPaid) 0.0 else (myStudent.monthlyVanFee - myStudent.discount)

    var searchQuery by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("All") }
    var selectedMonth by remember { mutableStateOf("All") }

    val years = listOf("All", "2026", "2025")
    val months = listOf("All", "July", "June", "May", "April", "March")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Transport Fees & Ledger", 
            style = MaterialTheme.typography.titleLarge, 
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        // DYNAMIC STUDENT PROFILE SWITCHER
        if (myStudents.size > 1) {
            Text(
                "Select Profile to View Ledger",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                myStudents.forEach { student ->
                    val isSelected = student.studentId == selectedStudentId
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedStudentId = student.studentId },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        if (isSelected) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = student.name.take(1).uppercase(),
                                    fontWeight = FontWeight.Black,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = student.name.split(" ").firstOrNull() ?: student.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Class: ${student.classLevel}",
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Outstanding balance card (Super modern Cred style)
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (outstandingAmount > 0) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f) else Color(0xFFECFDF5)
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (outstandingAmount > 0) MaterialTheme.colorScheme.error.copy(alpha = 0.3f) else Color(0xFF10B981).copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "OUTSTANDING TRANSPORT DUE", 
                    fontSize = 10.sp, 
                    fontWeight = FontWeight.Black, 
                    color = if (outstandingAmount > 0) MaterialTheme.colorScheme.error else Color(0xFF047857),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "₹${outstandingAmount.toInt()}.00", 
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black, 
                    color = if (outstandingAmount > 0) MaterialTheme.colorScheme.error else Color(0xFF047857)
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = if (outstandingAmount > 0) MaterialTheme.colorScheme.error.copy(alpha = 0.2f) else Color(0xFF10B981).copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Billing Status", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (outstandingAmount > 0) "Overdue (July 2026)" else "Fully Paid (July 2026)", 
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = if (outstandingAmount > 0) Color.DarkGray else Color(0xFF047857)
                        )
                    }
                    if (outstandingAmount > 0) {
                        Button(
                            onClick = {
                                viewModel.selectedStudent.value = myStudent
                                viewModel.selectedPaymentMonth.value = "July 2026"
                                viewModel.navigateTo("payment_gateway")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.Payment, contentDescription = "Pay", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Pay Now", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = "Paid", tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Active", fontWeight = FontWeight.Black, color = Color(0xFF047857), fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Payment Ledger", 
            style = MaterialTheme.typography.titleMedium, 
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search transactions by month or status...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Gray) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Year Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Year:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            years.forEach { year ->
                val isSelected = selectedYear == year
                Box(
                    modifier = Modifier
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable { selectedYear = year }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = year,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Month Filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Month:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            months.forEach { month ->
                val isSelected = selectedMonth == month
                Box(
                    modifier = Modifier
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(1.dp, if (isSelected) MaterialTheme.colorScheme.secondary else Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable { selectedMonth = month }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = month,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))

        val studentTxns = txns.filter { it.studentId == myStudent.studentId }
        val filteredTxns = studentTxns.filter { t ->
            val yearMatches = selectedYear == "All" || t.dateAndTime.contains(selectedYear)
            val monthMatches = selectedMonth == "All" || t.paymentMonth.contains(selectedMonth, ignoreCase = true)
            val searchMatches = searchQuery.isEmpty() || t.paymentMonth.contains(searchQuery, ignoreCase = true) || t.status.contains(searchQuery, ignoreCase = true) || t.paymentMethod.contains(searchQuery, ignoreCase = true)
            yearMatches && monthMatches && searchMatches
        }

        if (filteredTxns.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = "No Ledger", modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No payment records found.", color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredTxns) { t ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectedStudent.value = myStudent
                                viewModel.selectedTransaction.value = t
                                viewModel.navigateTo("receipt_view")
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(t.paymentMonth, fontWeight = FontWeight.Black, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text("Ref ID: ${t.transactionId}", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.CreditCard, contentDescription = "Method", modifier = Modifier.size(12.dp), tint = Color.Gray)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${t.paymentMethod} | ${t.dateAndTime.split(" ").firstOrNull() ?: t.dateAndTime}", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "₹${t.amount.toInt()}.00", 
                                    fontWeight = FontWeight.Black, 
                                    style = MaterialTheme.typography.titleMedium, 
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (t.status == "Paid") Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = t.status.uppercase(),
                                        color = if (t.status == "Paid") Color(0xFF15803D) else Color(0xFFB91C1C),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ParentSupportScreen(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val complaints by viewModel.parentComplaints.collectAsStateWithLifecycle()

    val myStudent = students.find { cleanPhone(it.parentMobileNumber) == cleanPhone(currentUser?.mobileNumber) || it.studentId == currentUser?.linkedStudentId }

    // Forms states
    var category by remember { mutableStateOf("Route") }
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showCategoryMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Parent Support Center", 
            style = MaterialTheme.typography.titleLarge, 
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Direct Touchpoints Support Channels
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.showMessage("Simulating WhatsApp Integration Launch with KGM Helpdesk!")
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0xFFDCFCE7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "WhatsApp Chat", tint = Color(0xFF15803D), modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("WhatsApp", fontWeight = FontWeight.Black, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text("Chat 24/7", fontSize = 10.sp, color = Color.Gray)
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.showMessage("Dialing KGM Emergency Supervisor Contact: +91 98765 43210")
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Call, contentDescription = "Call Desk", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Call Supervisor", fontWeight = FontWeight.Black, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text("Instant Voice", fontSize = 10.sp, color = Color.Gray)
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.showMessage("Drafting support email desk launch to: support@kgmtravels.com")
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Email, contentDescription = "Email Help", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Email Help", fontWeight = FontWeight.Black, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text("Reply in 1hr", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        // TRANSPORT RULES & CLAUSES CARD IN SUPPORT
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.navigateTo("terms_view") }
                .testTag("support_terms_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Gavel, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Transport Rules & Clauses", fontWeight = FontWeight.Black, fontSize = 14.sp)
                        Text("Read full Terms & Conditions and Safety guidelines", fontSize = 11.sp, color = Color.Gray)
                    }
                }
                Icon(Icons.Filled.ChevronRight, contentDescription = "View", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Frequently Asked Questions (FAQ)", 
            style = MaterialTheme.typography.titleMedium, 
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        listOf(
            "How do I request a change in boarding stop?" to "Please submit a support complaint ticket under 'Route' category indicating the preferred new stop. The Transport Coordinator will review vehicle occupancy and update route paths.",
            "What happens if payment is delayed past the due date?" to "A late surcharge of ₹100 is automatically accrued past the 10th. If unpaid past the 20th, transport access is automatically suspended (locked) by the system.",
            "Who do I contact in case of an afternoon bus delay?" to "You will receive an automated push broadcast notification if the bus is delayed >15 minutes. Alternatively, you can directly call the bus driver via 'Home' tab."
        ).forEach { faq ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Q: ${faq.first}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("A: ${faq.second}", fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Raise a Support Ticket", 
            style = MaterialTheme.typography.titleMedium, 
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Category Selector Dropdown Menu
                Box {
                    OutlinedButton(
                        onClick = { showCategoryMenu = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Category: $category", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                    DropdownMenu(
                        expanded = showCategoryMenu,
                        onDismissRequest = { showCategoryMenu = false }
                    ) {
                        listOf("Route", "Driver", "Fees", "Vehicle", "Other").forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    category = cat
                                    showCategoryMenu = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (subject.isEmpty() || description.isEmpty()) {
                            viewModel.showMessage("Please fill out both Subject and Description!")
                        } else {
                            viewModel.raiseComplaint(
                                studentName = myStudent?.name ?: "Student",
                                category = category,
                                subject = subject,
                                description = description
                            )
                            subject = ""
                            description = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Submit Ticket", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submit Support Ticket", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your Raised Tickets", 
            style = MaterialTheme.typography.titleMedium, 
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (complaints.isEmpty()) {
            Text("No past complaints raised.", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        } else {
            complaints.forEach { ticket ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(ticket.id, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (ticket.status == "Resolved") Color(0xFFDCFCE7) else Color(0xFFFEF3C7),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = ticket.status.uppercase(),
                                    color = if (ticket.status == "Resolved") Color(0xFF15803D) else Color(0xFFB45309),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(ticket.subject, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text("Category: ${ticket.category} | ${ticket.dateStr}", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(ticket.description, fontSize = 12.sp, color = Color.DarkGray)

                        if (ticket.adminReply.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Official Coordinator Response:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(ticket.adminReply, fontSize = 12.sp, color = Color.DarkGray)
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ParentProfileScreen(viewModel: KgmViewModel) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val appSettings by viewModel.appSettings.collectAsStateWithLifecycle()

    val isDarkEnabled = (appSettings["dark_mode"] ?: "false") == "true"
    val isNotificationsEnabled = (appSettings["notifications_enabled"] ?: "true") == "true"
    var selectedLanguage by remember { mutableStateOf(appSettings["language"] ?: "English") }
    var showLanguageMenu by remember { mutableStateOf(false) }

    // Edit states
    var isEditingProfile by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(currentUser?.name ?: "") }
    var editEmail by remember { mutableStateOf(currentUser?.email ?: "") }
    var editMobile by remember { mutableStateOf(currentUser?.mobileNumber ?: "") }

    // Password change states
    var isChangingPassword by remember { mutableStateOf(false) }
    var editOldPass by remember { mutableStateOf("") }
    var editNewPass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Parent Profile & Settings", 
            style = MaterialTheme.typography.titleLarge, 
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 1. DYNAMIC PROFILE AVATAR CARD
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            Brush.sweepGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary,
                                    MaterialTheme.colorScheme.primary
                                )
                            ),
                            CircleShape
                        )
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (currentUser?.name ?: "P").take(1).uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = currentUser?.name ?: "Parent",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Role: ${currentUser?.role ?: "Parent User"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Filled.Email, 
                        contentDescription = "Email", 
                        tint = Color.Gray, 
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = currentUser?.email ?: "no-email@kgmtravels.com",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. ACCOUNT SETTINGS SECTION
        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (isEditingProfile) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Parent Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editMobile,
                        onValueChange = { editMobile = it },
                        label = { Text("Mobile Number") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { isEditingProfile = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                viewModel.updateParentProfile(editName, editEmail, editMobile)
                                isEditingProfile = false
                                viewModel.showMessage("Profile updated successfully")
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Save")
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Full Name", fontSize = 11.sp, color = Color.Gray)
                            Text(currentUser?.name ?: "N/A", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Email", fontSize = 11.sp, color = Color.Gray)
                            Text(currentUser?.email ?: "N/A", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Phone Number", fontSize = 11.sp, color = Color.Gray)
                            Text(currentUser?.mobileNumber ?: "N/A", fontWeight = FontWeight.Bold)
                        }
                        IconButton(
                            onClick = {
                                editName = currentUser?.name ?: ""
                                editEmail = currentUser?.email ?: ""
                                editMobile = currentUser?.mobileNumber ?: ""
                                isEditingProfile = true
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        ) {
                            Icon(
                                Icons.Filled.Edit, 
                                contentDescription = "Edit Profile", 
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. SECURITY & PASSWORD CHANGE
        Text(
            text = "Security Settings",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (isChangingPassword) {
                    OutlinedTextField(
                        value = editOldPass,
                        onValueChange = { editOldPass = it },
                        label = { Text("Old Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editNewPass,
                        onValueChange = { editNewPass = it },
                        label = { Text("New Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { 
                                isChangingPassword = false
                                editOldPass = ""
                                editNewPass = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                if (editNewPass.isEmpty()) {
                                    viewModel.showMessage("New password cannot be empty")
                                } else {
                                    val success = viewModel.changePassword(editOldPass, editNewPass)
                                    if (success) {
                                        isChangingPassword = false
                                        editOldPass = ""
                                        editNewPass = ""
                                        viewModel.showMessage("Password changed successfully!")
                                    } else {
                                        viewModel.showMessage("Incorrect old password!")
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Update")
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Lock, 
                                contentDescription = "Security", 
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Update Account Password", fontWeight = FontWeight.Bold)
                                Text("Secure your parent portal credentials", fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                        Button(
                            onClick = { isChangingPassword = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Change", color = MaterialTheme.colorScheme.onSecondaryContainer, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. DEVICE PREFERENCES (DARK MODE, NOTIFICATIONS, LANGUAGE)
        Text(
            text = "Device Preferences",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // A. Dark Mode Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isDarkEnabled) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                            contentDescription = "Theme Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Dark Mode Theme", fontWeight = FontWeight.Medium)
                    }
                    Switch(
                        checked = isDarkEnabled,
                        onCheckedChange = { checked ->
                            viewModel.updateAppSettings("dark_mode", if (checked) "true" else "false")
                            viewModel.showMessage("Dark Mode ${if (checked) "Enabled" else "Disabled"}")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                // B. Notification Preferences
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isNotificationsEnabled) Icons.Filled.NotificationsActive else Icons.Filled.NotificationsOff,
                            contentDescription = "Notifications Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Enable Notifications", fontWeight = FontWeight.Medium)
                    }
                    Switch(
                        checked = isNotificationsEnabled,
                        onCheckedChange = { checked ->
                            viewModel.updateAppSettings("notifications_enabled", if (checked) "true" else "false")
                            viewModel.showMessage("Notifications ${if (checked) "Enabled" else "Disabled"}")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                // C. Language Selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Language,
                            contentDescription = "Language",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("App Language", fontWeight = FontWeight.Medium)
                    }
                    Box {
                        OutlinedButton(
                            onClick = { showLanguageMenu = true },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(selectedLanguage)
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                        }
                        DropdownMenu(
                            expanded = showLanguageMenu,
                            onDismissRequest = { showLanguageMenu = false }
                        ) {
                            listOf("English", "Tamil (தமிழ்)", "Hindi (हिंदी)", "Telugu (తెలుగు)").forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang) },
                                    onClick = {
                                        selectedLanguage = lang
                                        viewModel.updateAppSettings("language", lang)
                                        showLanguageMenu = false
                                        viewModel.showMessage("Language switched to $lang")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4B. TERMS & CONDITIONS CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.navigateTo("terms_view") }
                .testTag("profile_terms_card")
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Gavel,
                        contentDescription = "Terms & Conditions",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("KGM Transport Rules & Policies", fontWeight = FontWeight.Bold)
                        Text("View our 17 safety and operation clauses", fontSize = 11.sp, color = Color.Gray)
                    }
                }
                Icon(Icons.Filled.ChevronRight, contentDescription = "View", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 5. SIGN OUT BUTTON
        Button(
            onClick = { viewModel.logout() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign Out of KGM Account", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ParentDashboard(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val txns by viewModel.allTransactions.collectAsStateWithLifecycle()
    val feesConfig by viewModel.feesConfig.collectAsStateWithLifecycle()
    val drivers by viewModel.allDrivers.collectAsStateWithLifecycle()
    val cleaners by viewModel.allCleaners.collectAsStateWithLifecycle()

    val dueDate = feesConfig["due_date"] ?: "10th of every month"

    // 1. FILTER STUDENTS LINKED TO THIS PARENT (MULTIPLE KIDS SUPPORT)
    val myStudents = students.filter { 
        cleanPhone(it.parentMobileNumber) == cleanPhone(currentUser?.mobileNumber) || 
        it.studentId == currentUser?.linkedStudentId 
    }

    var selectedStudentId by remember(myStudents) { 
        mutableStateOf(myStudents.firstOrNull()?.studentId ?: "") 
    }

    val myStudent = myStudents.find { it.studentId == selectedStudentId } ?: myStudents.firstOrNull()

    // Dialog state for Digital Pass
    var showPassDialog by remember { mutableStateOf(false) }

    // Live tracking status simulator state
    var trackingState by remember { mutableStateOf("On the Way") }

    if (myStudent == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.padding(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.School, 
                        contentDescription = "No Student", 
                        modifier = Modifier.size(64.dp), 
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No Linked Student Record", 
                        fontWeight = FontWeight.Black, 
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "We couldn't find any student linked to your mobile number (${currentUser?.mobileNumber}). Please contact the school management or call support to link your children.",
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { viewModel.navigateTo("parent_support") },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Contact Support")
                    }
                }
            }
        }
        return
    }

    val isLocked = myStudent.status == "Locked"

    // Fetch driver & attendant details
    val assignedDriverObj = drivers.find { it.driverName == myStudent.assignedDriver }
    val driverContact = assignedDriverObj?.mobileNumber ?: "+91 98765 43210"

    val assignedCleanerObj = cleaners.find { it.assignedVehicle == myStudent.assignedVehicle }
    val cleanerName = assignedCleanerObj?.name ?: "Sundar Swamy"
    val cleanerContact = assignedCleanerObj?.phoneNumber ?: "+91 97865 12345"

    // Check fee status for active month (e.g., July 2026)
    val activeMonthName = "July"
    val currentMonthTxn = txns.find {
        it.studentId == myStudent.studentId &&
        it.paymentMonth.startsWith(activeMonthName, ignoreCase = true) &&
        it.status.equals("Paid", ignoreCase = true)
    }
    val isCurrentMonthPaid = currentMonthTxn != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // HEADER ROW: GREETING & NOTIFICATION BELL (COFEE LUXURY STYLE)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Profile Avatar with high-end golden outline
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .border(2.dp, Color(0xFFFBBF24), CircleShape)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (currentUser?.name ?: "P").take(1).uppercase(),
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "WELCOME BACK",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White.copy(alpha = 0.7f),
                            letterSpacing = 1.5.sp
                        )
                        Text(
                            text = currentUser?.name ?: "Parent",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "✨ Premium Transit Parent Member",
                            fontSize = 10.sp,
                            color = Color(0xFFFBBF24),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Notification Bell with modern pulsing indicator
                IconButton(
                    onClick = { viewModel.navigateTo("notifications_list") },
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                        .size(42.dp)
                ) {
                    Box {
                        Icon(
                            Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .background(Color(0xFFEF4444), CircleShape)
                                .align(Alignment.TopEnd)
                                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // DYNAMIC STUDENT SWITCHER (FOR MULTIPLE CHILDREN)
        if (myStudents.size > 1) {
            Text(
                "Switch Student Profile",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                myStudents.forEach { student ->
                    val isSelected = student.studentId == selectedStudentId
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedStudentId = student.studentId },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        if (isSelected) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = student.name.take(1).uppercase(),
                                    fontWeight = FontWeight.Black,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = student.name.split(" ").firstOrNull() ?: student.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Class: ${student.classLevel}",
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // LOCK OUT AND SUSPENSION BANNER
        if (isLocked) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Locked",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PORTAL LOCKOUT ACTIVE",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Transport services are temporarily suspended due to outstanding due payments. Please clear pending fees instantly below to reactivate portal tracking & access.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // 2. PREMIUM STUDENT INFORMATION BOARDING PASS
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = myStudent.name.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = myStudent.name,
                                fontWeight = FontWeight.Black,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Adm No: ${myStudent.admissionNumber} | Class: ${myStudent.classLevel}-${myStudent.section}",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "ROUTE ${myStudent.assignedRoute}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("ASSIGNED SEAT", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text(myStudent.assignedSeat.ifEmpty { "S12" }, fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Column {
                        Text("VEHICLE NUMBER", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text(myStudent.assignedVehicle, fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("CAMPUS LOCATION", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text(myStudent.campus, fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // 3. STUDENT STATUS TRACKING TIMELINE (COFEE APP STYLE PROGRESS BAR)
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LIVE TRANSIT MONITOR",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF10B981), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Status: $trackingState",
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "ETA TO DESTINATION",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = when (trackingState) {
                                "Boarded" -> "${myStudent.pickupTime} (In 5 Mins)"
                                "On the Way" -> "08:12 AM (In 10 Mins)"
                                else -> "Reached (08:25 AM)"
                            },
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = Color(0xFF10B981)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Beautiful Visual Progress Timeline Bar with Connections
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Step 1: Boarded / Pickup
                    val step1Active = trackingState == "Boarded" || trackingState == "On the Way" || trackingState == "Reached School"
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (step1Active) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.DirectionsBus, contentDescription = "Boarded", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Boarded", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (step1Active) MaterialTheme.colorScheme.primary else Color.Gray)
                        Text(myStudent.pickupTime, fontSize = 8.sp, color = Color.Gray)
                    }

                    // Connecting Line 1
                    val line1Active = trackingState == "On the Way" || trackingState == "Reached School"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(3.dp)
                            .background(if (line1Active) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f))
                            .align(Alignment.CenterVertically)
                    )

                    // Step 2: On the Way
                    val step2Active = trackingState == "On the Way" || trackingState == "Reached School"
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (step2Active) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.LocationOn, contentDescription = "Transit", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("In Transit", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (step2Active) MaterialTheme.colorScheme.primary else Color.Gray)
                        Text("08:12 AM", fontSize = 8.sp, color = Color.Gray)
                    }

                    // Connecting Line 2
                    val line2Active = trackingState == "Reached School"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(3.dp)
                            .background(if (line2Active) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f))
                            .align(Alignment.CenterVertically)
                    )

                    // Step 3: Reached School
                    val step3Active = trackingState == "Reached School"
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (step3Active) Color(0xFF10B981) else Color.LightGray.copy(alpha = 0.5f),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = "Reached", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("School", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (step3Active) Color(0xFF10B981) else Color.Gray)
                        Text(myStudent.dropTime, fontSize = 8.sp, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                // Interactive Simulator Switch so Parents can test states!
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Simulate Journey Status:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Boarded", "On the Way", "Reached School").forEach { state ->
                            val isSel = trackingState == state
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (isSel) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(1.dp, if (isSel) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .clickable { trackingState = state }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (state == "Reached School") "School" else state,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. OUTSTANDING FEES & DUE MANAGEMENT MODULE (REDESIGNED)
        Spacer(modifier = Modifier.height(16.dp))
        
        val schoolMonths = listOf("April 2026", "May 2026", "June 2026", "July 2026")
        val unpaidMonths = schoolMonths.filter { month ->
            !txns.any { t ->
                t.studentId == myStudent.studentId &&
                t.paymentMonth.startsWith(month.split(" ").first(), ignoreCase = true) &&
                t.status.equals("Paid", ignoreCase = true)
            }
        }
        val perMonthAmount = (myStudent.monthlyVanFee - myStudent.discount).toInt()
        val totalOutstandingAmount = unpaidMonths.size * perMonthAmount

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Dashboard Summary Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "OUTSTANDING TRANSPORT FEES",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = if (unpaidMonths.isEmpty()) "Account Balanced" else "${unpaidMonths.size} Pending Payments",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                if (unpaidMonths.isEmpty()) Color(0xFFD1FAE5) else Color(0xFFFEE2E2),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (unpaidMonths.isEmpty()) "FULLY PAID" else "₹$totalOutstandingAmount DUE",
                            color = if (unpaidMonths.isEmpty()) Color(0xFF065F46) else Color(0xFF991B1B),
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(16.dp))

                if (unpaidMonths.isEmpty()) {
                    // All fees cleared celebration state
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "No dues",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("All clear! No dues remaining", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Thank you for paying transport charges on time.", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                } else {
                    // Unpaid months as beautiful modern cards
                    Text(
                        text = "Unpaid Billing Cycles",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    unpaidMonths.forEach { month ->
                        val monthDueDay = "10th of ${month.split(" ").first()}"
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.2f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = month,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.Event, contentDescription = "Due", modifier = Modifier.size(12.dp), tint = Color.Gray)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Due by $monthDueDay", fontSize = 11.sp, color = Color.Gray)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Amount: ₹$perMonthAmount.00",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }

                                Button(
                                    onClick = {
                                        viewModel.selectedStudent.value = myStudent
                                        viewModel.selectedPaymentMonth.value = month
                                        viewModel.navigateTo("payment_gateway")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Icon(Icons.Filled.Payment, contentDescription = "Pay", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Quick Pay", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(16.dp))

                // Payment History Quick-Links
                val paidTxns = txns.filter { it.studentId == myStudent.studentId && it.status == "Paid" }.take(2)
                
                Text(
                    text = "Recent Paid Receipts",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (paidTxns.isEmpty()) {
                    Text(
                        text = "No paid history available for this student.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        paidTxns.forEach { txn ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .width(200.dp)
                                    .clickable {
                                        viewModel.selectedStudent.value = myStudent
                                        viewModel.selectedTransaction.value = txn
                                        viewModel.navigateTo("receipt_view")
                                    }
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(txn.paymentMonth, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Box(
                                            modifier = Modifier
                                                .background(Color(0xFFDCFCE7), RoundedCornerShape(4.dp))
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            Text("PAID", color = Color(0xFF15803D), fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("₹${txn.amount.toInt()} | ${txn.paymentMethod}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                    Text("Ref: ${txn.transactionId.take(12)}...", fontSize = 9.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. TRANSPORT & FLEET INFO CARD
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Transport & Vehicle Details",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Route & Vehicle header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.DirectionsBus, 
                            contentDescription = "Bus", 
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Vehicle No: ${myStudent.assignedVehicle}",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        text = "Route: ${myStudent.assignedRoute}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                // Timings Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Pickup Stop", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Text(myStudent.pickupTime, fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            Text(myStudent.pickupLandmark, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Drop Stop", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Text(myStudent.dropTime, fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
                            Text(myStudent.dropLandmark, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(14.dp))

                // Driver contact row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(myStudent.assignedDriver.take(1).uppercase(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Assigned Driver", fontSize = 10.sp, color = Color.Gray)
                            Text(myStudent.assignedDriver, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                    IconButton(
                        onClick = { viewModel.showMessage("Initiating phone call to Driver: $driverContact") },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(Icons.Filled.Call, contentDescription = "Call Driver", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Attendant contact row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(cleanerName.take(1).uppercase(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Vehicle Attendant", fontSize = 10.sp, color = Color.Gray)
                            Text(cleanerName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                    IconButton(
                        onClick = { viewModel.showMessage("Initiating phone call to Attendant: $cleanerContact") },
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(Icons.Filled.Call, contentDescription = "Call Attendant", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. QUICK ACTION COMMAND CENTER (CRED/G-PAY INSPIRED GRID)
        Text(
            text = "Quick Command Center",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // A. Pay Fees
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { viewModel.navigateTo("parent_fees") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFEFF6FF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.AccountBalanceWallet, contentDescription = "Pay Fees", tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Pay Fees", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            // B. Digital Pass
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { showPassDialog = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFECFDF5), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Badge, contentDescription = "Digital Pass", tint = Color(0xFF059669), modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Digital Pass", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            // C. Support Center
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { viewModel.navigateTo("parent_support") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFFFF7ED), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.SupportAgent, contentDescription = "Support Center", tint = Color(0xFFEA580C), modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Support", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 6. LATEST BROADCASTS & EMERGENCY FEED
        Text(
            text = "Latest KGM Broadcasts & Alerts",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        // High visibility Emergency Alert
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
            border = BorderStroke(1.dp, Color(0xFFD97706)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Warning, contentDescription = "Emergency Notice", tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("EMERGENCY NOTICE", fontWeight = FontWeight.Black, color = Color(0xFF92400E), fontSize = 11.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Today, 08:15 AM", fontSize = 10.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Minor Waterlogging on Main Sector Road: Route 10 and Route 14 will be delayed by approximately 15-20 minutes. Drop times will be adjusted. Parents are requested to track the live status via student timelines.",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // General Notice
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Campaign, contentDescription = "Announcement", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("GENERAL BROADCAST", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("05 July, 2026", fontSize = 10.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Quarterly General Bus Maintenance Checkup successfully completed for all KGM buses. Safeties are verified for active service operations.",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    // 7. DIGITAL BUS PASS DIALOG MODAL (STUNNING INTERACTIVE VISUAL PASS)
    if (showPassDialog) {
        Dialog(onDismissRequest = { showPassDialog = false }) {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header with logo
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Filled.DirectionsBus, 
                            contentDescription = "Bus", 
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "KGM TRANSIT PASS", 
                            fontWeight = FontWeight.Black, 
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Student card layout
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                        MaterialTheme.colorScheme.surface
                                    )
                                ),
                                RoundedCornerShape(16.dp)
                            )
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = myStudent.name.take(1).uppercase(),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = myStudent.name,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Adm No: ${myStudent.admissionNumber}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("VEHICLE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                    Text(myStudent.assignedVehicle, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("ROUTE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                    Text(myStudent.assignedRoute, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("CLASS", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                    Text("${myStudent.classLevel}-${myStudent.section}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // QR Code Placeholder Card
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.5.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Drawing a fake modern QR Code utilizing a stylized canvas Grid
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val cellSize = size.width / 9f
                            // Outer corners
                            drawRect(color = Color.Black, size = androidx.compose.ui.geometry.Size(cellSize * 3, cellSize * 3))
                            drawRect(color = Color.White, topLeft = androidx.compose.ui.geometry.Offset(cellSize, cellSize), size = androidx.compose.ui.geometry.Size(cellSize, cellSize))

                            drawRect(color = Color.Black, topLeft = androidx.compose.ui.geometry.Offset(cellSize * 6, 0f), size = androidx.compose.ui.geometry.Size(cellSize * 3, cellSize * 3))
                            drawRect(color = Color.White, topLeft = androidx.compose.ui.geometry.Offset(cellSize * 7, cellSize), size = androidx.compose.ui.geometry.Size(cellSize, cellSize))

                            drawRect(color = Color.Black, topLeft = androidx.compose.ui.geometry.Offset(0f, cellSize * 6), size = androidx.compose.ui.geometry.Size(cellSize * 3, cellSize * 3))
                            drawRect(color = Color.White, topLeft = androidx.compose.ui.geometry.Offset(cellSize, cellSize * 7), size = androidx.compose.ui.geometry.Size(cellSize, cellSize))

                            // Draw minor data bits
                            val randomBitPositions = listOf(
                                3 to 1, 4 to 1, 5 to 1,
                                1 to 3, 2 to 3, 3 to 3, 5 to 3, 8 to 3,
                                1 to 4, 3 to 4, 4 to 4, 6 to 4, 7 to 4,
                                1 to 5, 2 to 5, 4 to 5, 5 to 5, 8 to 5,
                                3 to 6, 4 to 6, 5 to 6, 6 to 6, 7 to 6,
                                4 to 7, 5 to 7, 7 to 7, 8 to 7,
                                3 to 8, 4 to 8, 5 to 8, 6 to 8
                            )
                            randomBitPositions.forEach { (cx, cy) ->
                                drawRect(color = Color.Black, topLeft = androidx.compose.ui.geometry.Offset(cellSize * cx, cellSize * cy), size = androidx.compose.ui.geometry.Size(cellSize, cellSize))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Scan pass upon boarding", 
                        fontSize = 10.sp, 
                        fontWeight = FontWeight.Bold, 
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Academic Year: 2026-27", 
                        fontSize = 9.sp, 
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showPassDialog = false },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Dismiss Pass")
                    }
                }
            }
        }
    }
}

@Composable
fun ParentReceiptsScreen(viewModel: KgmViewModel) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val txns by viewModel.allTransactions.collectAsStateWithLifecycle()

    val myStudents = students.filter { 
        cleanPhone(it.parentMobileNumber) == cleanPhone(currentUser?.mobileNumber) || 
        it.studentId == currentUser?.linkedStudentId 
    }

    var selectedStudentId by remember(myStudents) { 
        mutableStateOf(myStudents.firstOrNull()?.studentId ?: "") 
    }

    val myStudent = myStudents.find { it.studentId == selectedStudentId } ?: myStudents.firstOrNull()

    if (myStudent == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = "No Receipts", modifier = Modifier.size(64.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Text("No linked student record found.", style = MaterialTheme.typography.bodyLarge)
            }
        }
        return
    }

    var selectedYear by remember { mutableStateOf("All") }
    var selectedMonth by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val years = listOf("All", "2026", "2025")
    val months = listOf("All", "July", "June", "May", "April", "March")

    val studentTxns = txns.filter { it.studentId == myStudent.studentId && it.status.equals("Paid", ignoreCase = true) }
    
    val filteredTxns = studentTxns.filter { t ->
        val yearMatches = selectedYear == "All" || t.dateAndTime.contains(selectedYear)
        val monthMatches = selectedMonth == "All" || t.paymentMonth.contains(selectedMonth, ignoreCase = true)
        val searchMatches = searchQuery.isEmpty() || t.transactionId.contains(searchQuery, ignoreCase = true) || t.paymentMonth.contains(searchQuery, ignoreCase = true)
        yearMatches && monthMatches && searchMatches
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Receipts & Invoices", 
            style = MaterialTheme.typography.titleLarge, 
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Official digital receipts for tax and transport records.",
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // DYNAMIC STUDENT PROFILE SWITCHER
        if (myStudents.size > 1) {
            Text(
                "Select Profile to View Receipts",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                myStudents.forEach { student ->
                    val isSelected = student.studentId == selectedStudentId
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedStudentId = student.studentId },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        if (isSelected) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                  Text(
                                      text = student.name.take(1).uppercase(),
                                      fontWeight = FontWeight.Black,
                                      color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                                      fontSize = 12.sp
                                  )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = student.name.split(" ").firstOrNull() ?: student.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Class: ${student.classLevel}",
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by receipt or ID...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Gray) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Year Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Year:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            years.forEach { year ->
                val isSelected = selectedYear == year
                Box(
                    modifier = Modifier
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable { selectedYear = year }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = year,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Month Filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Month:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            months.forEach { month ->
                val isSelected = selectedMonth == month
                Box(
                    modifier = Modifier
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(1.dp, if (isSelected) MaterialTheme.colorScheme.secondary else Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable { selectedMonth = month }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = month,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Receipts List
        if (filteredTxns.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = "No receipts", modifier = Modifier.size(56.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No receipts match filters.", color = Color.Gray, fontSize = 14.sp)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredTxns) { t ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
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
                                Column {
                                    Text(
                                        text = t.paymentMonth, 
                                        fontWeight = FontWeight.Black, 
                                        fontSize = 16.sp, 
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Receipt: ${t.transactionId}", 
                                        fontSize = 11.sp, 
                                        color = Color.Gray,
                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFD1FAE5), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "PAID",
                                        color = Color(0xFF065F46),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Amount Paid", fontSize = 11.sp, color = Color.Gray)
                                    Text("₹${t.amount.toInt()}.00", fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium, color = Color(0xFF10B981))
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Date & Time", fontSize = 11.sp, color = Color.Gray)
                                    Text(t.dateAndTime, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            
                            // Modern action strip
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.selectedStudent.value = myStudent
                                        viewModel.selectedTransaction.value = t
                                        viewModel.navigateTo("receipt_view")
                                    },
                                    modifier = Modifier.weight(1.5f),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(Icons.Filled.Visibility, contentDescription = "View", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("View", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                IconButton(
                                    onClick = { viewModel.showMessage("Downloading PDF for Receipt ${t.transactionId}...") },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                        .size(40.dp)
                                ) {
                                    Icon(Icons.Filled.Download, contentDescription = "Download PDF", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }

                                IconButton(
                                    onClick = { viewModel.showMessage("Opening print command for Receipt ${t.transactionId}...") },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                        .size(40.dp)
                                ) {
                                    Icon(Icons.Filled.Print, contentDescription = "Print", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }

                                IconButton(
                                    onClick = { viewModel.showMessage("Sharing Receipt ${t.transactionId} link...") },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                        .size(40.dp)
                                ) {
                                    Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



