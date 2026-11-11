package com.example.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.util.AppIconManager
import com.example.viewmodel.KgmViewModel

data class WhatsAppPlusTheme(
    val id: String,
    val name: String,
    val editionTag: String,
    val gradientColors: List<Color>,
    val accentColor: Color,
    val icon: ImageVector,
    val description: String
)

val WHATSAPP_PLUS_THEMES = listOf(
    WhatsAppPlusTheme(
        id = "wp_emerald",
        name = "WhatsApp Plus Emerald",
        editionTag = "WhatsApp Plus Edition",
        gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669), Color(0xFF047857)),
        accentColor = Color(0xFFFBBF24),
        icon = Icons.Filled.DirectionsBus,
        description = "Vibrant WhatsApp Emerald gradient with golden school bus badge & neon glow."
    ),
    WhatsAppPlusTheme(
        id = "wp_gold",
        name = "Royal 24K Gold VIP",
        editionTag = "Luxury Signature",
        gradientColors = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A)),
        accentColor = Color(0xFFF59E0B),
        icon = Icons.Filled.Stars,
        description = "Executive midnight navy background crowned by 24K liquid gold stars."
    ),
    WhatsAppPlusTheme(
        id = "wp_sapphire",
        name = "Cyber Sapphire Blue",
        editionTag = "Electric Pro",
        gradientColors = listOf(Color(0xFF1D4ED8), Color(0xFF3B82F6), Color(0xFF60A5FA)),
        accentColor = Color(0xFF93C5FD),
        icon = Icons.Filled.DirectionsBus,
        description = "Ultra high-tech electric cobalt transit fleet with sapphire ice accents."
    ),
    WhatsAppPlusTheme(
        id = "wp_onyx",
        name = "Midnight Stealth Onyx",
        editionTag = "Dark Mode Elite",
        gradientColors = listOf(Color(0xFF18181B), Color(0xFF09090B)),
        accentColor = Color(0xFFE4E4E7),
        icon = Icons.Filled.Shield,
        description = "Matte stealth black carbon canvas with brushed titanium shield."
    ),
    WhatsAppPlusTheme(
        id = "wp_ruby",
        name = "Imperial Ruby Luxe",
        editionTag = "Executive Edition",
        gradientColors = listOf(Color(0xFF881337), Color(0xFFBE123C), Color(0xFFE11D48)),
        accentColor = Color(0xFFFDE047),
        icon = Icons.Filled.Diamond,
        description = "Royal Bordeaux wine & velvet red diamond emblem with gold trim."
    ),
    WhatsAppPlusTheme(
        id = "wp_neon",
        name = "Cyberpunk Neon Violet",
        editionTag = "Futuristic Plus",
        gradientColors = listOf(Color(0xFF581C87), Color(0xFF7C3AED), Color(0xFFA855F7)),
        accentColor = Color(0xFF22D3EE),
        icon = Icons.Filled.Bolt,
        description = "Electric neon purple with cyber cyan lightning bolt insignia."
    ),
    WhatsAppPlusTheme(
        id = "wp_sunset",
        name = "Sunset Flame Tangerine",
        editionTag = "Vibrant Dawn",
        gradientColors = listOf(Color(0xFFC2410C), Color(0xFFEA580C), Color(0xFFF97316)),
        accentColor = Color(0xFFFEF08A),
        icon = Icons.Filled.WbSunny,
        description = "Radiant sunrise orange & solar amber with golden morning crest."
    ),
    WhatsAppPlusTheme(
        id = "wp_retro_green",
        name = "WhatsApp Classic Green",
        editionTag = "Retro Plus",
        gradientColors = listOf(Color(0xFF075E54), Color(0xFF128C7E), Color(0xFF25D366)),
        accentColor = Color.White,
        icon = Icons.Filled.DirectionsBus,
        description = "Authentic WhatsApp messaging green with crisp white transport emblem."
    ),
    WhatsAppPlusTheme(
        id = "wp_fleet_yellow",
        name = "Official School Fleet",
        editionTag = "Fleet Pro",
        gradientColors = listOf(Color(0xFFF59E0B), Color(0xFFD97706)),
        accentColor = Color(0xFF0F172A),
        icon = Icons.Filled.DirectionsBus,
        description = "Iconic high-visibility school bus yellow with jet slate contrast."
    ),
    WhatsAppPlusTheme(
        id = "wp_titanium",
        name = "Titanium Platinum Shield",
        editionTag = "Enterprise",
        gradientColors = listOf(Color(0xFF334155), Color(0xFF64748B), Color(0xFF94A3B8)),
        accentColor = Color(0xFF38BDF8),
        icon = Icons.Filled.DirectionsBus,
        description = "Brushed steel metallic chrome with cobalt blue highlights."
    ),
    WhatsAppPlusTheme(
        id = "ic_kgm",
        name = "Heritage KGM Crest",
        editionTag = "Original Master",
        gradientColors = listOf(Color(0xFF1E3A8A), Color(0xFF1E293B)),
        accentColor = Color(0xFFF59E0B),
        icon = Icons.Filled.DirectionsBus,
        description = "The authentic signature KGM Travels royal shield & gold bus crest."
    )
)

@Composable
fun AppIconStudioScreen(viewModel: KgmViewModel) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val business by viewModel.businessInfo.collectAsStateWithLifecycle()
    val currentLogo = business["logo"] ?: "ic_kgm"

    val isSuperAdmin = viewModel.isSuperAdmin()

    // Preview state (local selection before committing)
    var selectedIconCode by remember(currentLogo) { mutableStateOf(currentLogo) }
    var selectedShapeIndex by remember { mutableStateOf(0) } // 0: Squircle, 1: Circle, 2: Teardrop, 3: Rounded Square
    var customUrlInput by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val logoGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.applyLogoFromUri(context, uri)
        }
    }

    val logoFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.applyLogoFromUri(context, uri)
        }
    }

    // Role-based Access Control
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
                        "Super Admin Access Restricted",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        "Only authorized Super Admins have permission to customize the application launcher icon, gallery photos, and branding themes.",
                        textAlign = TextAlign.Center,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // WhatsApp Plus Inspired Header Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF0F172A), Color(0xFF064E3B), Color(0xFF047857))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(1.dp, Color(0xFF10B981).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                color = Color(0xFF10B981),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "WHATSAPP+ EXCLUSIVE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Surface(
                                color = Color(0xFFF59E0B),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "🔒 SUPER ADMIN ONLY",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "Choose Your App Icon",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = "Inspired by WhatsApp Plus custom icon packs. Upload any photo from Gallery/Files or select from high-definition luxury icon presets to update the app branding instantly across all screens.",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Live Android Home Screen Simulator Mockup
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Live Launcher Preview",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Android Home Screen",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Realistic Android Home Screen Wallpaper Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF020617))
                            )
                        )
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Status Bar at Top
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("09:41", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Wifi, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(13.dp))
                            Icon(Icons.Filled.BatteryFull, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(13.dp))
                        }
                    }

                    // Centered App Icon with dynamic shape
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        val iconShape: Shape = when (selectedShapeIndex) {
                            1 -> CircleShape
                            2 -> RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 8.dp, bottomEnd = 32.dp) // Teardrop
                            3 -> RoundedCornerShape(8.dp) // Box
                            else -> RoundedCornerShape(20.dp) // Squircle
                        }

                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .shadow(12.dp, shape = iconShape, spotColor = Color(0xFF10B981))
                                .clip(iconShape)
                                .background(Color.White)
                        ) {
                            AppLogoImage(
                                logoUrl = selectedIconCode,
                                contentDescription = "Active Icon Preview",
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "KGM Travels",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Surface(
                            color = if (selectedIconCode == currentLogo) Color(0xFF10B981) else Color(0xFFF59E0B),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = if (selectedIconCode == currentLogo) "● CURRENT ACTIVE ICON" else "PREVIEW (NOT SAVED)",
                                color = Color.Black,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Launcher Shape Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Launcher Icon Shape:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Squircle", "Circle", "Teardrop", "Box").forEachIndexed { idx, shapeName ->
                            FilterChip(
                                selected = selectedShapeIndex == idx,
                                onClick = { selectedShapeIndex = idx },
                                label = { Text(shapeName, fontSize = 10.sp) },
                                modifier = Modifier.height(28.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section: Custom Photo Upload from Gallery or Files
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Filled.AddPhotoAlternate, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Column {
                        Text(
                            "Upload Custom Photo or File",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Use any photo from your phone gallery or document files",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { logoGalleryLauncher.launch("image/*") },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("btn_choose_gallery_icon"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
                    ) {
                        Icon(Icons.Filled.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Gallery / Photos", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { logoFileLauncher.launch(arrayOf("image/*")) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("btn_choose_files_icon"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
                    ) {
                        Icon(Icons.Filled.FolderOpen, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Browse Files", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Custom Web URL Input
                OutlinedTextField(
                    value = customUrlInput,
                    onValueChange = {
                        customUrlInput = it
                        if (it.isNotBlank()) selectedIconCode = it
                    },
                    label = { Text("Or enter Image Web URL / Base64") },
                    placeholder = { Text("https://example.com/brand_icon.png") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        if (customUrlInput.isNotBlank()) {
                            IconButton(onClick = { customUrlInput = "" }) {
                                Icon(Icons.Filled.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
            }
        }

        // Section: WhatsApp Plus Icon Pack Collection
        Text(
            text = "WhatsApp Plus Icon Pack (Choose Your Theme)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 850.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = false
        ) {
            items(WHATSAPP_PLUS_THEMES) { theme ->
                val isSelected = selectedIconCode == theme.id
                val isActiveInSystem = currentLogo == theme.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedIconCode = theme.id
                        }
                        .testTag("theme_card_${theme.id}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        else MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Badge Icon Thumbnail
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .shadow(6.dp, RoundedCornerShape(14.dp))
                        ) {
                            AppLogoImage(
                                logoUrl = theme.id,
                                contentDescription = theme.name,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Theme Name
                        Text(
                            text = theme.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )

                        // Edition Tag
                        Surface(
                            color = theme.accentColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = theme.editionTag,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = theme.description,
                            fontSize = 10.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            lineHeight = 13.sp,
                            maxLines = 2
                        )

                        // Select / Active Action Indicator
                        if (isActiveInSystem) {
                            Surface(
                                color = Color(0xFF10B981),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color.Black, modifier = Modifier.size(12.dp))
                                    Text("ACTIVE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Black)
                                }
                            }
                        } else if (isSelected) {
                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "SELECTED",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Commit & Reset Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    selectedIconCode = "ic_kgm"
                    viewModel.updateAppIcon(context, "ic_kgm", "Heritage KGM Crest")
                },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset Default", fontSize = 12.sp)
            }

            Button(
                onClick = {
                    val matchingTheme = WHATSAPP_PLUS_THEMES.find { it.id == selectedIconCode }
                    val themeName = matchingTheme?.name ?: "Custom Theme"
                    viewModel.updateAppIcon(context, selectedIconCode, themeName)
                    showSuccessDialog = true
                },
                modifier = Modifier
                    .weight(1.5f)
                    .height(48.dp)
                    .testTag("btn_apply_app_icon"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Apply App Icon", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(40.dp)
                )
            },
            title = {
                Text(
                    "App Icon Changed Successfully!",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    "The new application icon and branding have been saved to the database, updated in memory, and applied across all user portals and headers.",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Awesome")
                }
            }
        )
    }
}
