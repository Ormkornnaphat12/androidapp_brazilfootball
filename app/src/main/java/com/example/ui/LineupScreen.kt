package com.example.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.BrasilRepository
import com.example.data.Player
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LineupScreen(
    lineup: Map<Int, Player?>,
    onSetPlayer: (Int, Player?) -> Unit,
    onSetCustomPlayer: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingSlot by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Banner Header with simple hint
        Surface(
            tonalElevation = 0.dp,
            color = ImmersiveHeaderBg,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SELECT SQUAD FORMATION (4-4-2)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = ImmersiveGold,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⚠️ Tap any position jersey on the pitch to customize players",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ImmersiveTextSec,
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Adaptive Dynamic Scale Canvas Pitch Layout
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            val maxW = maxWidth
            val maxH = maxHeight

            // Deduce aspect-ratio calculation to perfectly fit high screens as well as wide tablet layouts
            val fieldHeight = maxH
            val fieldWidth = fieldHeight * 0.72f

            val finalWidth = if (fieldWidth > maxW) maxW else fieldWidth
            val finalHeight = finalWidth / 0.72f

            Box(
                modifier = Modifier
                    .size(finalWidth, finalHeight)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(24.dp))
                    .border(2.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(24.dp))
            ) {
                FootballPitch(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(vertical = 12.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // 1. FORWARDS Row (Centered, spaced from edges)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val fw1 = lineup[10]
                            val fw2 = lineup[11]

                            TacticalPlayerSlot(
                                slotNumber = 10,
                                player = fw1,
                                onClick = { editingSlot = 10 },
                                modifier = Modifier.weight(1f)
                            )
                            TacticalPlayerSlot(
                                slotNumber = 11,
                                player = fw2,
                                onClick = { editingSlot = 11 },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // 2. MIDFIELDERS Row (Full length horizontal, 4 players)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val lm = lineup[6]
                            val cm1 = lineup[7]
                            val cm2 = lineup[8]
                            val rm = lineup[9]

                            TacticalPlayerSlot(
                                slotNumber = 6,
                                player = lm,
                                onClick = { editingSlot = 6 },
                                modifier = Modifier.weight(1f)
                            )
                            TacticalPlayerSlot(
                                slotNumber = 7,
                                player = cm1,
                                onClick = { editingSlot = 7 },
                                modifier = Modifier.weight(1f)
                            )
                            TacticalPlayerSlot(
                                slotNumber = 8,
                                player = cm2,
                                onClick = { editingSlot = 8 },
                                modifier = Modifier.weight(1f)
                            )
                            TacticalPlayerSlot(
                                slotNumber = 9,
                                player = rm,
                                onClick = { editingSlot = 9 },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // 3. DEFENDERS Row (Full length horizontal, 4 players)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val lb = lineup[2]
                            val cb1 = lineup[3]
                            val cb2 = lineup[4]
                            val rb = lineup[5]

                            TacticalPlayerSlot(
                                slotNumber = 2,
                                player = lb,
                                onClick = { editingSlot = 2 },
                                modifier = Modifier.weight(1f)
                            )
                            TacticalPlayerSlot(
                                slotNumber = 3,
                                player = cb1,
                                onClick = { editingSlot = 3 },
                                modifier = Modifier.weight(1f)
                            )
                            TacticalPlayerSlot(
                                slotNumber = 4,
                                player = cb2,
                                onClick = { editingSlot = 4 },
                                modifier = Modifier.weight(1f)
                            )
                            TacticalPlayerSlot(
                                slotNumber = 5,
                                player = rb,
                                onClick = { editingSlot = 5 },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // 4. GOALKEEPER Row (One single player, centered)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val gk = lineup[1]
                            TacticalPlayerSlot(
                                slotNumber = 1,
                                player = gk,
                                onClick = { editingSlot = 1 },
                                modifier = Modifier.wrapContentSize()
                            )
                        }
                    }

                    // Watermark signature matching buildlineup.com in image
                    Text(
                        text = "buildlineup.com",
                        color = Color.White.copy(alpha = 0.35f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 12.dp, end = 16.dp)
                    )
                }
            }
        }
    }

    // Modal Sheet Editor Dialog for slot selection
    if (editingSlot != null) {
        val slot = editingSlot!!
        LineupSlotEditDialog(
            slotNumber = slot,
            currentPlayer = lineup[slot],
            onDismiss = { editingSlot = null },
            onSelectPlayer = { player ->
                onSetPlayer(slot, player)
                editingSlot = null
            },
            onSaveCustomPlayer = { customName ->
                onSetCustomPlayer(slot, customName)
                editingSlot = null
            }
        )
    }
}

@Composable
fun TacticalPlayerSlot(
    slotNumber: Int,
    player: Player?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .testTag("lineup_slot_$slotNumber")
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Red with white sleeve jersey
        Box(
            modifier = Modifier
                .size(54.dp)
                .padding(bottom = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            JerseyIcon(
                number = slotNumber,
                shirtColor = Color(0xFFC62828), // Premium Football Red
                sleeveColor = Color.White,
                modifier = Modifier.fillMaxSize()
            )

            // Number centering on shirt body
            Text(
                text = slotNumber.toString(),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.offset(y = 5.dp) // align visually with T-Shirt body center
            )
        }

        // Capsule text displaying name or placeholder
        val textName = player?.name ?: "Click to edit"
        Text(
            text = textName,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(
                    color = Color.Black.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun JerseyIcon(
    number: Int,
    shirtColor: Color,
    sleeveColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Define premium contour Path for football Jersey T-Shirt
        val path = Path().apply {
            moveTo(w * 0.33f, h * 0.16f) // Collar left
            quadraticBezierTo(w * 0.5f, h * 0.28f, w * 0.67f, h * 0.16f) // Collar curved cut
            lineTo(w * 0.78f, h * 0.16f) // Shoulder right
            lineTo(w * 0.95f, h * 0.35f) // Right Sleeve top edge
            lineTo(w * 0.82f, h * 0.46f) // Right Sleeve lower hem
            lineTo(w * 0.75f, h * 0.36f) // Right underarm joint
            lineTo(w * 0.75f, h * 0.88f) // Right side hem down
            lineTo(w * 0.25f, h * 0.88f) // Bottom jersey hem
            lineTo(w * 0.25f, h * 0.36f) // Left side hem up
            lineTo(w * 0.18f, h * 0.46f) // Left underarm joint
            lineTo(w * 0.05f, h * 0.35f) // Left Sleeve lower hem
            lineTo(w * 0.22f, h * 0.16f) // Left Shoulder edge
            close()
        }

        // Draw primary T-shirt body fill
        drawPath(path = path, color = shirtColor)

        // Draw collar neck white highlight trim
        val neckTrimPath = Path().apply {
            moveTo(w * 0.33f, h * 0.16f)
            quadraticBezierTo(w * 0.5f, h * 0.28f, w * 0.67f, h * 0.16f)
            lineTo(w * 0.67f, h * 0.12f)
            quadraticBezierTo(w * 0.5f, h * 0.24f, w * 0.33f, h * 0.12f)
            close()
        }
        drawPath(path = neckTrimPath, color = sleeveColor)

        // Draw left white sleeve highlight
        val leftSleeveHighlight = Path().apply {
            moveTo(w * 0.22f, h * 0.16f)
            lineTo(w * 0.05f, h * 0.35f)
            lineTo(w * 0.18f, h * 0.46f)
            lineTo(w * 0.25f, h * 0.36f)
            close()
        }
        drawPath(path = leftSleeveHighlight, color = sleeveColor)

        // Draw right white sleeve highlight
        val rightSleeveHighlight = Path().apply {
            moveTo(w * 0.78f, h * 0.16f)
            lineTo(w * 0.95f, h * 0.35f)
            lineTo(w * 0.82f, h * 0.46f)
            lineTo(w * 0.75f, h * 0.36f)
            close()
        }
        drawPath(path = rightSleeveHighlight, color = sleeveColor)
    }
}

@Composable
fun FootballPitch(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2E7D32)) // Base turf green
            .drawBehind {
                val w = size.width
                val h = size.height

                // 1. Draw alternating Horizontal Cut Grass Stripes
                val stripeCount = 13
                val stripeHeight = h / stripeCount
                for (i in 0 until stripeCount) {
                    val color = if (i % 2 == 0) Color(0xFF1B5E20) else Color(0xFF2E7D32)
                    drawRect(
                        color = color,
                        topLeft = Offset(0f, i * stripeHeight),
                        size = androidx.compose.ui.geometry.Size(w, stripeHeight)
                    )
                }

                // Pitch Line Styling configuration
                val linePaintColor = Color.White.copy(alpha = 0.55f)
                val strokeThickness = 1.5.dp.toPx()
                val lineStyle = Stroke(width = strokeThickness)

                // Margin buffers inside the bounding box
                val padding = 12.dp.toPx()
                val topY = padding
                val bottomY = h - padding
                val leftX = padding
                val rightX = w - padding
                val pitchW = rightX - leftX
                val pitchH = bottomY - topY

                // 2. Playable Pitch Boundary Outline
                drawRect(
                    color = linePaintColor,
                    topLeft = Offset(leftX, topY),
                    size = androidx.compose.ui.geometry.Size(pitchW, pitchH),
                    style = lineStyle
                )

                // 3. Center Kickoff line
                val midY = h / 2f
                drawLine(
                    color = linePaintColor,
                    start = Offset(leftX, midY),
                    end = Offset(rightX, midY),
                    strokeWidth = strokeThickness
                )

                // 4. Center Field Circle
                val cirRadius = pitchW * 0.18f
                drawCircle(
                    color = linePaintColor,
                    radius = cirRadius,
                    center = Offset(w / 2f, midY),
                    style = lineStyle
                )
                // Center Spot dot
                drawCircle(
                    color = linePaintColor,
                    radius = 3.dp.toPx(),
                    center = Offset(w / 2f, midY)
                )

                // 5. PENALTY BOX TOP (Attack Area)
                val penBoxW = pitchW * 0.58f
                val penBoxH = pitchH * 0.16f
                val penBoxLeft = leftX + (pitchW - penBoxW) / 2f

                drawRect(
                    color = linePaintColor,
                    topLeft = Offset(penBoxLeft, topY),
                    size = androidx.compose.ui.geometry.Size(penBoxW, penBoxH),
                    style = lineStyle
                )

                // Goal area goalkeeper box top
                val goalAreaW = pitchW * 0.28f
                val goalAreaH = pitchH * 0.05f
                val goalAreaLeft = leftX + (pitchW - goalAreaW) / 2f
                drawRect(
                    color = linePaintColor,
                    topLeft = Offset(goalAreaLeft, topY),
                    size = androidx.compose.ui.geometry.Size(goalAreaW, goalAreaH),
                    style = lineStyle
                )

                // Goal Net/Post structure outline top
                val goalW = pitchW * 0.18f
                val goalH = 6.dp.toPx()
                drawRect(
                    color = linePaintColor,
                    topLeft = Offset(w / 2f - goalW / 2f, topY - goalH),
                    size = androidx.compose.ui.geometry.Size(goalW, goalH),
                    style = lineStyle
                )

                // Penalty Arc curve Top box
                val penSpotTop = topY + penBoxH * 0.72f
                drawCircle(
                    color = linePaintColor,
                    radius = 2.dp.toPx(),
                    center = Offset(w / 2f, penSpotTop)
                )

                // 6. PENALTY BOX BOTTOM (Defence Area)
                val penBoxBottomTop = bottomY - penBoxH
                drawRect(
                    color = linePaintColor,
                    topLeft = Offset(penBoxLeft, penBoxBottomTop),
                    size = androidx.compose.ui.geometry.Size(penBoxW, penBoxH),
                    style = lineStyle
                )

                // Goal area goalkeeper box bottom
                val goalAreaBottomTop = bottomY - goalAreaH
                drawRect(
                    color = linePaintColor,
                    topLeft = Offset(goalAreaLeft, goalAreaBottomTop),
                    size = androidx.compose.ui.geometry.Size(goalAreaW, goalAreaH),
                    style = lineStyle
                )

                // Goal Net structure outline bottom
                drawRect(
                    color = linePaintColor,
                    topLeft = Offset(w / 2f - goalW / 2f, bottomY),
                    size = androidx.compose.ui.geometry.Size(goalW, goalH),
                    style = lineStyle
                )

                // Penalty Arc spot bottom
                val penSpotBottom = bottomY - penBoxH * 0.72f
                drawCircle(
                    color = linePaintColor,
                    radius = 2.dp.toPx(),
                    center = Offset(w / 2f, penSpotBottom)
                )

                // Corner Kick arcs
                val cornRad = 6.dp.toPx()
                // Top Left
                drawArc(
                    color = linePaintColor,
                    startAngle = 0f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = Offset(leftX - cornRad, topY - cornRad),
                    size = androidx.compose.ui.geometry.Size(cornRad * 2, cornRad * 2),
                    style = lineStyle
                )
                // Top Right
                drawArc(
                    color = linePaintColor,
                    startAngle = 90f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = Offset(rightX - cornRad, topY - cornRad),
                    size = androidx.compose.ui.geometry.Size(cornRad * 2, cornRad * 2),
                    style = lineStyle
                )
                // Bottom Left
                drawArc(
                    color = linePaintColor,
                    startAngle = 270f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = Offset(leftX - cornRad, bottomY - cornRad),
                    size = androidx.compose.ui.geometry.Size(cornRad * 2, cornRad * 2),
                    style = lineStyle
                )
                // Bottom Right
                drawArc(
                    color = linePaintColor,
                    startAngle = 180f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = Offset(rightX - cornRad, bottomY - cornRad),
                    size = androidx.compose.ui.geometry.Size(cornRad * 2, cornRad * 2),
                    style = lineStyle
                )
            }
    ) {
        content()
    }
}

@Composable
fun LineupSlotEditDialog(
    slotNumber: Int,
    currentPlayer: Player?,
    onDismiss: () -> Unit,
    onSelectPlayer: (Player?) -> Unit,
    onSaveCustomPlayer: (String) -> Unit
) {
    var customNameInput by remember { mutableStateOf("") }
    var listFilterKeyword by remember { mutableStateOf("") }
    var showOnlyPositionFit by remember { mutableStateOf(true) }

    // Deduce position group name
    val positionGroup = when (slotNumber) {
        1 -> "Goalkeeper"
        in 2..5 -> "Defender"
        in 6..9 -> "Midfielder"
        else -> "Forward"
    }

    // Filter roster candidates
    val candidatePlayers = remember(listFilterKeyword, showOnlyPositionFit) {
        BrasilRepository.players.filter { player ->
            val matchQuery = player.name.contains(listFilterKeyword, ignoreCase = true) ||
                    player.club.contains(listFilterKeyword, ignoreCase = true)

            val matchPos = if (showOnlyPositionFit) {
                player.position.equals(positionGroup, ignoreCase = true)
            } else {
                true
            }
            matchQuery && matchPos
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = ImmersiveCardMedium
            ),
            border = BorderStroke(1.dp, ImmersiveBorder),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .widthIn(max = 480.dp)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header Position Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CUSTOMIZE POSITION #$slotNumber",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ImmersiveGold,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                        )
                        Text(
                            text = "Role: $positionGroup",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Black
                            )
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.06f), CircleShape)
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancel edit",
                            tint = ImmersiveTextSec,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Custom Input Field Section
                Text(
                    text = "A. ASSIGN CUSTOM TEAM MATE NAME",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ImmersiveTextMuted,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                OutlinedTextField(
                    value = customNameInput,
                    onValueChange = { customNameInput = it },
                    placeholder = { Text("E.g. Pele, Ronaldinho, Yourself...", color = ImmersiveTextMuted, fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_lineup_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ImmersiveGold,
                        unfocusedBorderColor = ImmersiveBorder,
                        cursorColor = ImmersiveGold,
                        focusedContainerColor = ImmersiveCardLight,
                        unfocusedContainerColor = ImmersiveCardLight,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (customNameInput.isNotBlank()) {
                            onSaveCustomPlayer(customNameInput)
                        }
                    })
                )

                if (customNameInput.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onSaveCustomPlayer(customNameInput) },
                        colors = ButtonDefaults.buttonColors(containerColor = ImmersiveGold, contentColor = ImmersiveBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Text("Add custom player to slot", fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                HorizontalDivider(color = ImmersiveBorder, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                // Official Roster Search Section
                Text(
                    text = "B. CHOOSE FROM OFFICIAL SELEÇÃO SQUAD",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ImmersiveTextMuted,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                // Search Box inside dialog
                OutlinedTextField(
                    value = listFilterKeyword,
                    onValueChange = { listFilterKeyword = it },
                    placeholder = { Text("Search Seleção stars...", color = ImmersiveTextMuted, fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = ImmersiveTextSec, modifier = Modifier.size(16.dp))
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ImmersiveGold,
                        unfocusedBorderColor = ImmersiveBorder,
                        focusedContainerColor = ImmersiveCardLight,
                        unfocusedContainerColor = ImmersiveCardLight,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Suggestion/All Filter Switch Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (showOnlyPositionFit) "Showing only standard $positionGroup" else "Showing entire 18-man squad",
                        style = MaterialTheme.typography.labelSmall.copy(color = ImmersiveTextSec),
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        onClick = { showOnlyPositionFit = !showOnlyPositionFit },
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (showOnlyPositionFit) "Show All Roster" else "Suggest Roles",
                            color = ImmersiveGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }

                // Scrollable List of Candidates
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .background(ImmersiveCardLight, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (candidatePlayers.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No squad match found", color = ImmersiveTextMuted, fontSize = 13.sp)
                            }
                        }
                    } else {
                        items(candidatePlayers) { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onSelectPlayer(player) }
                                    .padding(vertical = 10.dp, horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = player.name,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${player.club}  •  ${player.position}",
                                        color = ImmersiveTextSec,
                                        fontSize = 12.sp
                                    )
                                }

                                Surface(
                                    shape = CircleShape,
                                    color = ImmersiveGreen.copy(alpha = 0.15f),
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = player.number.toString(),
                                            color = ImmersiveGold,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 11.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom actions: Clear slot or Reset
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (currentPlayer != null) {
                        OutlinedButton(
                            onClick = { onSelectPlayer(null) },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFEF5350)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFEF5350).copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                        ) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Clear Position", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ImmersiveCardLight,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Text("Close Editor", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
