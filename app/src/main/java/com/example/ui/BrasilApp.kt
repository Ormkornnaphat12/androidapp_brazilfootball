package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.focus.FocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.BrasilRepository
import com.example.data.Championship
import com.example.data.Match
import com.example.data.Player
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrasilApp(
    viewModel: BrasilViewModel,
    modifier: Modifier = Modifier
) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val positionFilter by viewModel.positionFilter.collectAsStateWithLifecycle()
    val favoritePlayers by viewModel.favoritePlayers.collectAsStateWithLifecycle()
    val selectedPlayer by viewModel.selectedPlayer.collectAsStateWithLifecycle()
    val selectedMatchYear by viewModel.selectedMatchYear.collectAsStateWithLifecycle()
    val playersList by viewModel.filteredPlayers.collectAsStateWithLifecycle(initialValue = emptyList())
    val lineup by viewModel.lineup.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.safeDrawing // Prevent camera notch clipping on Android 15/16
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. Immersive UI Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ImmersiveHeaderBg)
                    .drawBehind {
                        // Draw subtle bottom border matching border-white/5
                        drawLine(
                            color = ImmersiveBorder,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Brand Side
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Left soccer/emblem decorative circle
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.08f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsSoccer,
                                contentDescription = "Soccer icon",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Seleção Brasileira",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = (-0.5).sp
                                )
                            )
                            Text(
                                text = "Confederação Brasileira",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ImmersiveGreen,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.5.sp
                                )
                            )
                        }
                    }

                    // Right Brand Star Badge - gold background with blue star
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(ImmersiveGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star badge",
                            tint = ImmersiveBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 2. Custom Modern M3 Navigation Tabs Bar with explicit ripple feedbacks
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                containerColor = ImmersiveHeaderBg,
                contentColor = ImmersiveGold,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                        color = ImmersiveGold,
                        height = 3.dp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        // Subtle bottom divider border
                        drawLine(
                            color = ImmersiveBorder,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
            ) {
                BrasilTab.values().forEach { tab ->
                    val tabSelected = selectedTab == tab
                    val tabIcon = when (tab) {
                        BrasilTab.PLAYERS -> Icons.Default.Groups
                        BrasilTab.LINEUP -> Icons.Default.SportsSoccer
                        BrasilTab.MATCHES -> Icons.Default.CalendarToday
                        BrasilTab.CHAMPIONSHIPS -> Icons.Default.EmojiEvents
                    }
                    val tabTitle = when (tab) {
                        BrasilTab.PLAYERS -> "Roster"
                        BrasilTab.LINEUP -> "Lineup"
                        BrasilTab.MATCHES -> "Calendar"
                        BrasilTab.CHAMPIONSHIPS -> "Titles"
                    }

                    Tab(
                        selected = tabSelected,
                        onClick = { viewModel.selectTab(tab) },
                        text = {
                            Text(
                                tabTitle,
                                fontWeight = if (tabSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp,
                                color = if (tabSelected) ImmersiveGold else ImmersiveTextSec
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tabIcon,
                                contentDescription = tabTitle,
                                tint = if (tabSelected) ImmersiveGold else ImmersiveTextSec.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .testTag("tab_${tab.name.lowercase()}")
                            .height(64.dp)
                    )
                }
            }

            // 3. Interactive Container for active view states
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Crossfade container transition between tab items
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        val duration = 220
                        fadeIn(animationSpec = tween(duration)) togetherWith
                                fadeOut(animationSpec = tween(duration))
                    },
                    label = "TabContentTransition"
                ) { targetTab ->
                    when (targetTab) {
                        BrasilTab.PLAYERS -> {
                            PlayersScreen(
                                players = playersList,
                                searchQuery = searchQuery,
                                selectedPositionFilter = positionFilter,
                                favoritePlayers = favoritePlayers,
                                onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                                onPositionFilterSelect = { viewModel.setPositionFilter(it) },
                                onFavoriteToggle = { viewModel.toggleFavorite(it) },
                                onPlayerClick = { viewModel.selectPlayer(it) },
                                focusManager = focusManager
                            )
                        }

                        BrasilTab.LINEUP -> {
                            LineupScreen(
                                lineup = lineup,
                                onSetPlayer = { slot, player -> viewModel.setLineupPlayer(slot, player) },
                                onSetCustomPlayer = { slot, name -> viewModel.setCustomLineupPlayer(slot, name) }
                            )
                        }

                        BrasilTab.MATCHES -> {
                            MatchesScreen(
                                matches = BrasilRepository.matches,
                                selectedYear = selectedMatchYear,
                                onYearSelect = { viewModel.setMatchYear(it) }
                            )
                        }

                        BrasilTab.CHAMPIONSHIPS -> {
                            ChampionshipsScreen(
                                championships = BrasilRepository.championships
                            )
                        }
                    }
                }
            }
        }

        // 4. Modal/Dialog sheet displaying detail stats, age, birth year when a player is selected
        if (selectedPlayer != null) {
            PlayerDetailDialog(
                player = selectedPlayer!!,
                isFavorite = favoritePlayers.contains(selectedPlayer!!.name),
                onDismiss = { viewModel.selectPlayer(null) },
                onFavoriteToggle = { viewModel.toggleFavorite(selectedPlayer!!.name) }
            )
        }
    }
}

// ==========================================
// SCREEN 1: PLAYERS ROSTER SCREEN
// ==========================================
@Composable
fun PlayersScreen(
    players: List<Player>,
    searchQuery: String,
    selectedPositionFilter: String,
    favoritePlayers: Set<String>,
    onSearchQueryChange: (String) -> Unit,
    onPositionFilterSelect: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onPlayerClick: (Player) -> Unit,
    focusManager: FocusManager
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Search & Filter Section
        Surface(
            tonalElevation = 0.dp, // Flat surface for modern flat black aesthetic
            color = ImmersiveHeaderBg,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Search Input TextField
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("player_search_input"),
                    placeholder = { Text("Search player name or club...", color = ImmersiveTextMuted) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = ImmersiveGold
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search",
                                    tint = ImmersiveTextSec
                                )
                            }
                        }
                    },
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
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable pill filter selector for positions
                val positions = listOf("All", "Goalkeepers", "Defenders", "Midfielders", "Forwards")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(positions) { position ->
                        val isSelected = selectedPositionFilter == position
                        FilterChip(
                            selected = isSelected,
                            onClick = { onPositionFilterSelect(position) },
                            label = { Text(position) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ImmersiveGreen,
                                selectedLabelColor = Color.White,
                                containerColor = ImmersiveCardMedium,
                                labelColor = ImmersiveTextSec
                            ),
                            shape = RoundedCornerShape(20.dp),
                            border = FilterChipDefaults.filterChipBorder(
                                selected = isSelected,
                                enabled = true,
                                borderColor = ImmersiveBorder,
                                selectedBorderColor = ImmersiveGreen,
                                borderWidth = 1.dp
                            ),
                            modifier = Modifier
                                .testTag("filter_$position")
                                .minimumInteractiveComponentSize()
                        )
                    }
                }
            }
        }

        // Players List Area
        if (players.isEmpty()) {
            // Elegant empty state display
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.SearchOff,
                    contentDescription = "No players found",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No players found",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Try refining your query or setting the position filter back to 'All'.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(players, key = { it.name }) { player ->
                    PlayerCard(
                        player = player,
                        isFavorite = favoritePlayers.contains(player.name),
                        onFavoriteToggle = { onFavoriteToggle(player.name) },
                        onClick = { onPlayerClick(player) }
                    )
                }
            }
        }
    }
}

@Composable
fun PlayerCard(
    player: Player,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("player_card_${player.name.replace(" ", "_").lowercase()}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = ImmersiveCardMedium
        ),
        border = BorderStroke(1.dp, ImmersiveBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Flat premium dark appeal
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Jersey Number Custom Graphic (glowing yellow circle representing the classic shirt)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(ImmersiveGold),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = player.number.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ImmersiveBlue,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Player Info (Name, Position, Club)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = player.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Metadata row displaying position and club
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Position Badge (Gold Green tag style)
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ImmersiveGreen.copy(alpha = 0.15f),
                        border = BorderStroke(0.5.dp, ImmersiveGreen.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = player.position.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ImmersiveGold,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = player.club,
                        style = MaterialTheme.typography.bodySmall,
                        color = ImmersiveTextSec,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Bookmarked rating star
            IconButton(
                onClick = onFavoriteToggle,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("favorite_button_${player.name.replace(" ", "_").lowercase()}")
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Add to favorites",
                    tint = if (isFavorite) ImmersiveGold else ImmersiveTextSec.copy(alpha = 0.4f)
                )
            }
        }
    }
}

// ==========================================
// SCREEN 2: MATCH SCHEDULE SCREEN
// ==========================================
@Composable
fun MatchesScreen(
    matches: List<Match>,
    selectedYear: Int,
    onYearSelect: (Int) -> Unit
) {
    val filteredMatches = remember(matches, selectedYear) {
        matches.filter { it.year == selectedYear }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Year filter bar
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
                    text = "SELECT MATCH SEASON",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ImmersiveGold,
                        letterSpacing = 1.5.sp
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val years = listOf(2024, 2025, 2026)
                    years.forEach { year ->
                        val isSelected = selectedYear == year
                        Button(
                            onClick = { onYearSelect(year) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) ImmersiveGreen else ImmersiveCardMedium,
                                contentColor = if (isSelected) Color.White else ImmersiveTextSec
                            ),
                            shape = RoundedCornerShape(12.dp),
                            border = if (isSelected) null else BorderStroke(1.dp, ImmersiveBorder),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("year_button_$year")
                        ) {
                            Text(
                                year.toString(),
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // Matches list
        if (filteredMatches.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.EventBusy,
                    contentDescription = "No schedule found",
                    tint = ImmersiveTextMuted,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No matches scheduled",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(filteredMatches) { match ->
                    MatchCard(match = match)
                }
            }
        }
    }
}

@Composable
fun MatchCard(match: Match) {
    val isUpcoming = match.score == null

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = ImmersiveCardMedium
        ),
        border = BorderStroke(1.dp, ImmersiveBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Competition name + Icon badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = match.competition.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ImmersiveGold,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                )

                // Match Status indicator
                if (isUpcoming) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Scheduled icon",
                            tint = ImmersiveGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "UPCOMING",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ImmersiveGold,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                } else {
                    val outcomeColor = when (match.result) {
                        "W" -> ImmersiveGreen
                        "D" -> ImmersiveTextSec
                        "L" -> Color(0xFFEF4444)
                        else -> ImmersiveTextSec
                    }
                    val outcomeLabel = when (match.result) {
                        "W" -> "WIN"
                        "D" -> "DRAW"
                        "L" -> "LOSS"
                        else -> "COMPLETED"
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = outcomeColor.copy(alpha = 0.15f),
                        border = BorderStroke(0.5.dp, outcomeColor.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "$outcomeLabel  -  ${match.score}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = outcomeColor,
                                fontWeight = FontWeight.Black
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Scoreboard UI Layer: Brazil vs Opponent
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Team Yellow-Green represent Brazil
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1.2f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(ImmersiveGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🇧🇷", fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Brazil",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }

                // Center score layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (isUpcoming) {
                        Text(
                            text = "VS",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = ImmersiveGold
                        )
                    } else {
                        val scores = match.score!!.split(" - ")
                        Text(
                            text = "${scores[0]} - ${scores[1]}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }
                }

                // Opponent Team Column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1.2f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(ImmersiveCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(match.opponentFlag, fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        match.opponent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            HorizontalDivider(color = ImmersiveBorder, thickness = 1.dp)

            Spacer(modifier = Modifier.height(10.dp))

            // Logistics Layer: Stadium Details & Time stamps
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Location Place icon",
                        tint = ImmersiveTextSec.copy(alpha = 0.6f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = match.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = ImmersiveTextSec,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Time icon",
                        tint = ImmersiveTextSec.copy(alpha = 0.6f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${match.date} @ ${match.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = ImmersiveTextSec,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ==========================================
// SCREEN 3: CHAMPIONSHIPS HISTORY SCREEN (Titles)
// ==========================================
@Composable
fun ChampionshipsScreen(
    championships: List<Championship>
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            // Hero Championship Card matching HTML design gradient and layout
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(ImmersiveGreen, ImmersiveBlue)
                            )
                        )
                        .padding(20.dp)
                ) {
                    // Background Faded Trophy Icon
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.12f),
                        modifier = Modifier
                            .size(110.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 10.dp, y = (-15).dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "WORLD CHAMPIONS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ImmersiveGold,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val years = listOf("1958", "1962", "1970", "1994", "2002")
                            years.forEach { year ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White.copy(alpha = 0.15f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = year,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        items(championships) { cup ->
            ChampionshipCard(cup = cup)
        }
    }
}

@Composable
fun ChampionshipCard(cup: Championship) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = ImmersiveCardMedium
        ),
        border = BorderStroke(1.dp, ImmersiveBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("championship_card_${cup.year}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Gold Title Year Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Golden Cup trophy icon",
                        tint = ImmersiveGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "WORLD CUP ${cup.year}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }

                // Stats Tag
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ImmersiveGold.copy(alpha = 0.15f),
                    border = BorderStroke(0.5.dp, ImmersiveGold.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = cup.tournamentStats,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ImmersiveGold,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Host Country Information
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "📍 Host: ",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ImmersiveTextSec
                    )
                )
                Text(
                    text = cup.hostCountry,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }

            // Final Match Score Information
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "⚽ Final Score: ",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ImmersiveTextSec
                    )
                )
                Text(
                    text = cup.finalScore,
                    style = MaterialTheme.typography.bodySmall,
                    color = ImmersiveGold,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Detailed narrative history about the cup
            Text(
                text = cup.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            HorizontalDivider(color = ImmersiveBorder, thickness = 1.dp)

            Spacer(modifier = Modifier.height(10.dp))

            // Star key players layout banner
            Column {
                Text(
                    text = "CAMPAIGN HEROES",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ImmersiveTextSec,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                // Row listing players with gold dot spacers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = cup.keyPlayers.joinToString(separator = "  •  "),
                        style = MaterialTheme.typography.bodySmall,
                        color = ImmersiveGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ==========================================
// ==========================================
// MODAL POPUP DIALOG SHEET DETAIL VIEW FOR PLAYERS
// ==========================================
@Composable
fun PlayerDetailDialog(
    player: Player,
    isFavorite: Boolean,
    onDismiss: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false // Adapt dynamically across screens
        )
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = ImmersiveCardMedium
            ),
            border = BorderStroke(1.dp, ImmersiveBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Premium flat visual depth
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .widthIn(max = 480.dp) // Maintain tablet canonical compact guidelines
                .wrapContentHeight()
                .padding(vertical = 16.dp)
                .testTag("player_detail_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Top Header Row with Close & Toggle Favorite
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("close_detail_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close dialog",
                            tint = ImmersiveTextSec
                        )
                    }

                    Text(
                        text = "PLAYER PROFILE",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = ImmersiveGold,
                            letterSpacing = 2.sp
                        )
                    )

                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Favorite toggle in details",
                            tint = if (isFavorite) ImmersiveGold else ImmersiveTextSec.copy(alpha = 0.5f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Highlighted Display Sector: Big Yellow Shirt, Name and Club
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(ImmersiveGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = player.number.toString(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = ImmersiveBlue,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace
                              )
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Column {
                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = player.club.uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = ImmersiveGreen
                        )
                        Text(
                            text = player.position.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = ImmersiveTextSec
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(color = ImmersiveBorder, thickness = 1.dp)

                Spacer(modifier = Modifier.height(16.dp))

                // Mandatory parameters requested: Age and Birth Year
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "AGE",
                            style = MaterialTheme.typography.labelSmall,
                            color = ImmersiveTextMuted,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${player.age} Years",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "BIRTH YEAR",
                            style = MaterialTheme.typography.labelSmall,
                            color = ImmersiveTextMuted,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = player.birthYear.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = ImmersiveBorder, thickness = 1.dp)

                Spacer(modifier = Modifier.height(16.dp))

                // National Team statistics: Caps & Goals
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "SELEÇÃO CAPS",
                            style = MaterialTheme.typography.labelSmall,
                            color = ImmersiveTextMuted,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = player.caps.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "GOALS SCORED",
                            style = MaterialTheme.typography.labelSmall,
                            color = ImmersiveTextMuted,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = player.goals.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ImmersiveGold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = ImmersiveBorder, thickness = 1.dp)

                Spacer(modifier = Modifier.height(16.dp))

                // Narrative Profile Section
                Text(
                    text = "SCOUT BIO",
                    style = MaterialTheme.typography.labelSmall,
                    color = ImmersiveTextMuted,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Text(
                    text = player.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Custom Dismiss Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ImmersiveGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "DISMISS PROFILE",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
