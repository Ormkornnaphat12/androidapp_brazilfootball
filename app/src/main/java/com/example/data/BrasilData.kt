package com.example.data

data class Player(
    val name: String,
    val position: String,
    val age: Int,
    val birthYear: Int,
    val number: Int,
    val club: String,
    val caps: Int,
    val goals: Int,
    val description: String
)

data class Match(
    val year: Int,
    val opponent: String,
    val opponentFlag: String, // Emoji flag
    val date: String,          // e.g. "Jun 24, 2024"
    val time: String,          // e.g. "18:00 UTC"
    val competition: String,   // E.g. "Copa América Group Stage", "World Cup 2026 Q"
    val location: String,      // Stadium, City
    val score: String?,        // Null if upcoming, e.g. "2 - 1" or "0 - 0"
    val result: String?        // "W", "D", "L", or null
)

data class Championship(
    val year: Int,
    val hostCountry: String,
    val finalScore: String,    // e.g. "Brazil 5 - 2 Sweden"
    val keyPlayers: List<String>,
    val starPlayer: String,
    val description: String,
    val tournamentStats: String // e.g. "6 Matches, 6 Wins, 16 Goals"
)

object BrasilRepository {
    val players = listOf(
        Player(
            name = "Vinícius Júnior",
            position = "Forward",
            age = 25,
            birthYear = 2000,
            number = 7,
            club = "Real Madrid",
            caps = 30,
            goals = 5,
            description = "The explosive wing wizard, celebrated worldwide for his electric pace, lethal dribbling, and incredible clutch performances on the highest stages."
        ),
        Player(
            name = "Neymar Jr",
            position = "Forward",
            age = 34,
            birthYear = 1992,
            number = 10,
            club = "Al Hilal",
            caps = 128,
            goals = 79,
            description = "Brazil's all-time record goalscorer. A magical samba playmaker possessing unmatched technical creativity, flair, and precision playmaking skills."
        ),
        Player(
            name = "Rodrygo Goes",
            position = "Forward",
            age = 25,
            birthYear = 2001,
            number = 11,
            club = "Real Madrid",
            caps = 27,
            goals = 6,
            description = "A tactically brilliant, versatile attacker who plays with clinical intelligence, exquisite close-control, and a phenomenal instinct for crucial goals."
        ),
        Player(
            name = "Raphinha",
            position = "Forward",
            age = 29,
            birthYear = 1996,
            number = 22,
            club = "FC Barcelona",
            caps = 26,
            goals = 7,
            description = "An energetic winger possessing a spectacular left foot, exceptional defensive work-rate, and a brilliant threat from direct free kicks."
        ),
        Player(
            name = "Endrick",
            position = "Forward",
            age = 19,
            birthYear = 2006,
            number = 9,
            club = "Real Madrid",
            caps = 10,
            goals = 3,
            description = "The generational teenage striker wonderkid. Possesses explosive physical power, incredible speed, and an extremely lethal instinct inside the box."
        ),
        Player(
            name = "Gabriel Martinelli",
            position = "Forward",
            age = 24,
            birthYear = 2001,
            number = 17,
            club = "Arsenal",
            caps = 15,
            goals = 2,
            description = "An athletic, tireless winger who panics defenses with direct running, constant high pressing, and swift baseline cuts."
        ),
        Player(
            name = "Bruno Guimarães",
            position = "Midfielder",
            age = 28,
            birthYear = 1997,
            number = 5,
            club = "Newcastle United",
            caps = 22,
            goals = 1,
            description = "The engine room of the midfield. Combines aggressive, tactical defensive ball-winning with delightful progressive passing and tempo control."
        ),
        Player(
            name = "Lucas Paquetá",
            position = "Midfielder",
            age = 28,
            birthYear = 1997,
            number = 8,
            club = "West Ham United",
            caps = 44,
            goals = 10,
            description = "A fluid modern playmaker known for intricate samba combinations, clever flick-ons, and a rugged defensive work rate."
        ),
        Player(
            name = "Douglas Luiz",
            position = "Midfielder",
            age = 28,
            birthYear = 1998,
            number = 18,
            club = "Juventus",
            caps = 18,
            goals = 0,
            description = "A well-rounded central midfielder with exceptional passing vision, hard-tackling qualities, and outstanding corner and penalty delivery."
        ),
        Player(
            name = "João Gomes",
            position = "Midfielder",
            age = 25,
            birthYear = 2001,
            number = 15,
            club = "Wolverhampton Wanderers",
            caps = 9,
            goals = 0,
            description = "A high-intensity ball-winner who dominates midfield physical battles. Nicknamed 'The Pitbull' for his relentless hustle."
        ),
        Player(
            name = "Andreas Pereira",
            position = "Midfielder",
            age = 30,
            birthYear = 1996,
            number = 19,
            club = "Fulham",
            caps = 8,
            goals = 1,
            description = "An elegant midfielder who excels in unlocking tight defensive lines and represents a massive threat on direct set-pieces."
        ),
        Player(
            name = "Marquinhos",
            position = "Defender",
            age = 32,
            birthYear = 1994,
            number = 4,
            club = "Paris Saint-Germain",
            caps = 84,
            goals = 7,
            description = "The rock-solid captain and defensive general. Celebrated for elite tactical coverage, speed off the blocks, and immense aerial authority."
        ),
        Player(
            name = "Gabriel Magalhães",
            position = "Defender",
            age = 28,
            birthYear = 1997,
            number = 14,
            club = "Arsenal",
            caps = 16,
            goals = 1,
            description = "A physically imposing left-sided central defender who excels in 1v1 duels and poses an elite offensive threat on attacking corner kicks."
        ),
        Player(
            name = "Éder Militão",
            position = "Defender",
            age = 28,
            birthYear = 1998,
            number = 3,
            club = "Real Madrid",
            caps = 32,
            goals = 2,
            description = "An explosive defender possessing incredible recovery pace, enabling high defensive lines and spectacular slide tackles."
        ),
        Player(
            name = "Danilo",
            position = "Defender",
            age = 34,
            birthYear = 1991,
            number = 2,
            club = "Juventus",
            caps = 56,
            goals = 1,
            description = "The experienced, versatile fullback who brings tactical discipline, positional intelligence, and mature calm to the squad."
        ),
        Player(
            name = "Guilherme Arana",
            position = "Defender",
            age = 29,
            birthYear = 1997,
            number = 6,
            club = "Atlético Mineiro",
            caps = 12,
            goals = 0,
            description = "An explosive, attacking left-back representing the home-grown Brazilian league talent, famous for rich overlapping runs."
        ),
        Player(
            name = "Alisson Becker",
            position = "Goalkeeper",
            age = 33,
            birthYear = 1992,
            number = 1,
            club = "Liverpool",
            caps = 65,
            goals = 0,
            description = "Arguably the world's most complete goalkeeper. Renowned for hypnotic 1v1 blocking, elite positional reads, and lightning-fast counter starts."
        ),
        Player(
            name = "Ederson Moraes",
            position = "Goalkeeper",
            age = 32,
            birthYear = 1993,
            number = 23,
            club = "Manchester City",
            caps = 25,
            goals = 0,
            description = "A pioneer of modern goalkeeping, possessing unmatched passing range, pinpoint playmaking side-volleys, and fearless sweeping."
        )
    )

    val championships = listOf(
        Championship(
            year = 2002,
            hostCountry = "South Korea & Japan",
            finalScore = "Brazil 2 - 0 Germany",
            keyPlayers = listOf("Ronaldo Nazário", "Rivaldo", "Ronaldinho", "Cafu", "Roberto Carlos"),
            starPlayer = "Ronaldo",
            description = "The 'Pentacampeonato'! Ronaldo (The Phenomenon) staged one of the greatest comeback stories in history, scoring 8 goals to capture the championship, including twice in the final against Oliver Kahn.",
            tournamentStats = "7 Games, 7 Wins, 18 Goals scored"
        ),
        Championship(
            year = 1994,
            hostCountry = "United States",
            finalScore = "Brazil 0 - 0 Italy (3 - 2 Pen)",
            keyPlayers = listOf("Romário", "Bebeto", "Dunga", "Cláudio Taffarel", "Branco"),
            starPlayer = "Romário",
            description = "After a nailbiting penalty shootout climax, Roberto Baggio's missed penalty sealed Brazil's 4th title. The Romário-Bebeto striking duo defined this tactical masterclass tournament.",
            tournamentStats = "7 Games, 5 Wins, 2 Draws, 11 Goals scored"
        ),
        Championship(
            year = 1970,
            hostCountry = "Mexico",
            finalScore = "Brazil 4 - 1 Italy",
            keyPlayers = listOf("Pelé", "Jairzinho", "Tostão", "Rivelino", "Carlos Alberto"),
            starPlayer = "Pelé",
            description = "Widely considered the greatest football team ever assembled. Brazil played beautiful, fluent 'Joga Bonito' football, capping the tournament with Jairzinho scoring in every single game, and Carlos Alberto's iconic final goal.",
            tournamentStats = "6 Games, 6 Wins, 19 Goals scored"
        ),
        Championship(
            year = 1962,
            hostCountry = "Chile",
            finalScore = "Brazil 3 - 1 Czechoslovakia",
            keyPlayers = listOf("Garrincha", "Amarildo", "Zito", "Vavá", "Didi"),
            starPlayer = "Garrincha",
            description = "With Pelé sidelined by injury early on, the legendary dribbler Garrincha delivered a sensational solo tournament to carry the Seleção to consecutive championships.",
            tournamentStats = "6 Games, 5 Wins, 1 Draw, 14 Goals scored"
        ),
        Championship(
            year = 1958,
            hostCountry = "Sweden",
            finalScore = "Brazil 5 - 2 Sweden",
            keyPlayers = listOf("Pelé (17 yrs)", "Garrincha", "Vavá", "Mário Zagallo", "Didi"),
            starPlayer = "Pelé",
            description = "The world met the 17-year-old prodigy Pelé. He scored a sensational hat-trick in the semi-final and a historic brace in the final, establishing Brazil's first-ever global crown.",
            tournamentStats = "6 Games, 5 Wins, 1 Draw, 16 Goals scored"
        )
    )

    val matches = listOf(
        // 2024
        Match(
            year = 2024,
            opponent = "Uruguay",
            opponentFlag = "🇺🇾",
            date = "Nov 19, 2024",
            time = "21:45",
            competition = "World Cup 2026 Qualifiers",
            location = "Arena Fonte Nova, Salvador",
            score = "1 - 1",
            result = "D"
        ),
        Match(
            year = 2024,
            opponent = "Venezuela",
            opponentFlag = "🇻🇪",
            date = "Nov 14, 2024",
            time = "17:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Estadio Monumental, Maturín",
            score = "1 - 1",
            result = "D"
        ),
        Match(
            year = 2024,
            opponent = "Peru",
            opponentFlag = "🇵🇪",
            date = "Oct 15, 2024",
            time = "21:45",
            competition = "World Cup 2026 Qualifiers",
            location = "Arena Mané Garrincha, Brasília",
            score = "4 - 0",
            result = "W"
        ),
        Match(
            year = 2024,
            opponent = "Chile",
            opponentFlag = "🇨🇱",
            date = "Oct 10, 2024",
            time = "21:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Estadio Nacional, Santiago",
            score = "2 - 1",
            result = "W"
        ),
        Match(
            year = 2024,
            opponent = "Paraguay",
            opponentFlag = "🇵🇾",
            date = "Sep 10, 2024",
            time = "20:30",
            competition = "World Cup 2026 Qualifiers",
            location = "Defensores del Chaco, Asunción",
            score = "0 - 1",
            result = "L"
        ),
        Match(
            year = 2024,
            opponent = "Ecuador",
            opponentFlag = "🇪🇨",
            date = "Sep 06, 2024",
            time = "22:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Couto Pereira, Curitiba",
            score = "1 - 0",
            result = "W"
        ),
        Match(
            year = 2024,
            opponent = "Colombia (Copa América)",
            opponentFlag = "🇨🇴",
            date = "Jul 02, 2024",
            time = "18:00",
            competition = "Copa América Group D",
            location = "Levi's Stadium, Santa Clara",
            score = "1 - 1",
            result = "D"
        ),
        Match(
            year = 2024,
            opponent = "Paraguay (Copa América)",
            opponentFlag = "🇵🇾",
            date = "Jun 28, 2024",
            time = "18:00",
            competition = "Copa América Group D",
            location = "Allegiant Stadium, Las Vegas",
            score = "4 - 1",
            result = "W"
        ),
        Match(
            year = 2024,
            opponent = "Costa Rica (Copa América)",
            opponentFlag = "🇨🇷",
            date = "Jun 24, 2024",
            time = "18:00",
            competition = "Copa América Group D",
            location = "SoFi Stadium, Inglewood",
            score = "0 - 0",
            result = "D"
        ),

        // 2025
        Match(
            year = 2025,
            opponent = "Colombia",
            opponentFlag = "🇨🇴",
            date = "Mar 20, 2025",
            time = "21:30",
            competition = "World Cup 2026 Qualifiers",
            location = "Maracanã, Rio de Janeiro",
            score = "2 - 1",
            result = "W"
        ),
        Match(
            year = 2025,
            opponent = "Argentina",
            opponentFlag = "🇦🇷",
            date = "Mar 25, 2025",
            time = "22:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Estadio Monumental, Buenos Aires",
            score = "0 - 1",
            result = "L"
        ),
        Match(
            year = 2025,
            opponent = "Ecuador",
            opponentFlag = "🇪🇨",
            date = "Jun 04, 2025",
            time = "18:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Estadio Olímpico Atahualpa, Quito",
            score = "2 - 2",
            result = "D"
        ),
        Match(
            year = 2025,
            opponent = "Paraguay",
            opponentFlag = "🇵🇾",
            date = "Jun 09, 2025",
            time = "21:30",
            competition = "World Cup 2026 Qualifiers",
            location = "Beira-Rio, Porto Alegre",
            score = "3 - 0",
            result = "W"
        ),
        Match(
            year = 2025,
            opponent = "Chile",
            opponentFlag = "🇨🇱",
            date = "Sep 04, 2025",
            time = "21:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Allianz Parque, São Paulo",
            score = "2 - 0",
            result = "W"
        ),
        Match(
            year = 2025,
            opponent = "Peru",
            opponentFlag = "🇵🇪",
            date = "Sep 09, 2025",
            time = "20:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Estadio Nacional, Lima",
            score = "1 - 1",
            result = "D"
        ),
        Match(
            year = 2025,
            opponent = "Venezuela",
            opponentFlag = "🇻🇪",
            date = "Oct 09, 2025",
            time = "20:30",
            competition = "World Cup 2026 Qualifiers",
            location = "Mineirão, Belo Horizonte",
            score = "3 - 1",
            result = "W"
        ),
        Match(
            year = 2025,
            opponent = "Uruguay",
            opponentFlag = "🇺🇾",
            date = "Oct 14, 2025",
            time = "21:00",
            competition = "World Cup 2026 Qualifiers",
            location = "Estadio Centenario, Montevideo",
            score = "1 - 2",
            result = "L"
        ),

        // 2026
        Match(
            year = 2026,
            opponent = "Spain",
            opponentFlag = "🇪🇸",
            date = "Mar 12, 2026",
            time = "20:45",
            competition = "International Friendly",
            location = "Santiago Bernabéu, Madrid",
            score = "3 - 3",
            result = "D"
        ),
        Match(
            year = 2026,
            opponent = "England",
            opponentFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            date = "Mar 17, 2026",
            time = "20:00",
            competition = "International Friendly",
            location = "Wembley Stadium, London",
            score = "1 - 0",
            result = "W"
        ),
        Match(
            year = 2026,
            opponent = "South Korea",
            opponentFlag = "🇰🇷",
            date = "Jun 14, 2026",
            time = "15:00",
            competition = "World Cup 2026 Group Stage",
            location = "MetLife Stadium, New Jersey",
            score = null,
            result = null
        ),
        Match(
            year = 2026,
            opponent = "Poland",
            opponentFlag = "🇵🇱",
            date = "Jun 20, 2026",
            time = "18:00",
            competition = "World Cup 2026 Group Stage",
            location = "SoFi Stadium, California",
            score = null,
            result = null
        ),
        Match(
            year = 2026,
            opponent = "Morocco",
            opponentFlag = "🇲🇦",
            date = "Jun 26, 2026",
            time = "21:00",
            competition = "World Cup 2026 Group Stage",
            location = "Hard Rock Stadium, Florida",
            score = null,
            result = null
        )
    )
}
