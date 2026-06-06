package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.data.BrasilRepository
import com.example.data.Championship
import com.example.data.Match
import com.example.data.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

enum class BrasilTab {
    PLAYERS, LINEUP, MATCHES, CHAMPIONSHIPS
}

class BrasilViewModel : ViewModel() {

    // Tab State
    private val _selectedTab = MutableStateFlow(BrasilTab.PLAYERS)
    val selectedTab: StateFlow<BrasilTab> = _selectedTab.asStateFlow()

    // Lineup State (maps positions 1 to 11 to Player?)
    private val _lineup = MutableStateFlow<Map<Int, Player?>>(
        mapOf(
            1 to BrasilRepository.players.find { it.number == 1 }, // Alisson
            2 to BrasilRepository.players.find { it.number == 2 }, // Danilo
            3 to BrasilRepository.players.find { it.number == 3 }, // Eder Militao
            4 to BrasilRepository.players.find { it.number == 4 }, // Marquinhos
            5 to BrasilRepository.players.find { it.number == 5 }, // Bruno Guimaraes
            6 to BrasilRepository.players.find { it.number == 6 }, // Guilherme Arana
            7 to BrasilRepository.players.find { it.number == 7 }, // Vinicius Jr
            8 to BrasilRepository.players.find { it.number == 8 }, // Lucas Paquetá
            9 to BrasilRepository.players.find { it.number == 9 }, // Endrick
            10 to BrasilRepository.players.find { it.number == 10 }, // Neymar Jr
            11 to BrasilRepository.players.find { it.number == 11 } // Rodrygo Goes
        )
    )
    val lineup: StateFlow<Map<Int, Player?>> = _lineup.asStateFlow()

    // Player Search & Filter States
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _positionFilter = MutableStateFlow("All")
    val positionFilter: StateFlow<String> = _positionFilter.asStateFlow()

    private val _favoritePlayers = MutableStateFlow(setOf<String>())
    val favoritePlayers: StateFlow<Set<String>> = _favoritePlayers.asStateFlow()

    // Selected Player for Details
    private val _selectedPlayer = MutableStateFlow<Player?>(null)
    val selectedPlayer: StateFlow<Player?> = _selectedPlayer.asStateFlow()

    // Match Filter States
    private val _selectedMatchYear = MutableStateFlow(2026) // Default to current local time's year
    val selectedMatchYear: StateFlow<Int> = _selectedMatchYear.asStateFlow()

    // Combined stream for filtered players
    val filteredPlayers = combine(
        _searchQuery,
        _positionFilter,
        _favoritePlayers
    ) { query, posFilter, favs ->
        BrasilRepository.players.filter { player ->
            val matchesSearch = player.name.contains(query, ignoreCase = true) ||
                    player.club.contains(query, ignoreCase = true)
            val matchesPosition = if (posFilter == "All") {
                true
            } else {
                player.position.equals(posFilter, ignoreCase = true) ||
                        (posFilter == "Goalkeepers" && player.position == "Goalkeeper") ||
                        (posFilter == "Defenders" && player.position == "Defender") ||
                        (posFilter == "Midfielders" && player.position == "Midfielder") ||
                        (posFilter == "Forwards" && player.position == "Forward")
            }
            matchesSearch && matchesPosition
        }
    }.combine(_searchQuery) { list, _ -> list }

    fun selectTab(tab: BrasilTab) {
        _selectedTab.value = tab
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setPositionFilter(filter: String) {
        _positionFilter.value = filter
    }

    fun selectPlayer(player: Player?) {
        _selectedPlayer.value = player
    }

    fun setMatchYear(year: Int) {
        _selectedMatchYear.value = year
    }

    fun toggleFavorite(playerName: String) {
        val current = _favoritePlayers.value
        _favoritePlayers.value = if (current.contains(playerName)) {
            current - playerName
        } else {
            current + playerName
        }
    }

    fun setLineupPlayer(slot: Int, player: Player?) {
        val current = _lineup.value.toMutableMap()
        current[slot] = player
        _lineup.value = current
    }

    fun setCustomLineupPlayer(slot: Int, name: String) {
        val current = _lineup.value.toMutableMap()
        if (name.isBlank()) {
            current[slot] = null
        } else {
            current[slot] = Player(
                name = name,
                position = when (slot) {
                    1 -> "Goalkeeper"
                    in 2..5 -> "Defender"
                    in 6..9 -> "Midfielder"
                    else -> "Forward"
                },
                age = 25,
                birthYear = 2000,
                number = slot,
                club = "Custom Squad",
                caps = 0,
                goals = 0,
                description = "Custom customized player on the squad."
            )
        }
        _lineup.value = current
    }
}
