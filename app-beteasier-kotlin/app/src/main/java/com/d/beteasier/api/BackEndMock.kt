package com.d.beteasier.api

object BackEndMock {

    private val users: MutableList<User> = mutableListOf(
        User(
            id = "1",
            email = "juris@gmail.com",
            password = "Password1",
            name = "Juris",
            surname = "Jurgaitis",
            country = "Zimbabwe",
            balance = 5000.0,
            monthlyLimit = 500.0,
            betLimit = 25.0,
            bets = mutableListOf(
                Bet(
                    userId = "1",
                    matchId = "7",
                    team = "PSG",
                    amount = 20.0,
                    result = Result.Lost
                ),
                Bet(
                    userId = "1",
                    matchId = "8",
                    team = "Real Madrid",
                    amount = 24.0,
                    result = Result.Won
                )
            )
        )
    )
    private var currentUser: User? = null

    private val matches: MutableList<Match> = mutableListOf(
        Match(
            "1",
            "Bayern Munich",
            "Schalke",
            "2020-06-02 19:00",
            Category.Football,
            1.1,
            3.1,
            false
        ),
        Match(
            "2",
            "Borussia",
            "Mainz",
            "2020-06-03 19:00",
            Category.Football,
            1.3,
            2.1,
            false
        ),
        Match(
            "3",
            "Real Madrid",
            "Barcelona",
            "2020-08-02 21:00",
            Category.Football,
            2.1,
            2.3,
            false
        ),
        Match(
            "4",
            "Zalgiris",
            "CSKA Moscow",
            "2020-09-10 20:00",
            Category.Basketball,
            2.0,
            1.6,
            false
        ),
        Match(
            "5",
            "Vilniaus Rytas",
            "Neptunas",
            "2020-09-12 21:00",
            Category.Basketball,
            1.1,
            2.1,
            false
        ),
        Match(
            "6",
            "NY Yankees",
            "Boston Red Sox",
            "2020-10-02 18:00",
            Category.Baseball,
            2.1,
            2.1,
            false
            ),
        Match(
            "7",
            "PSG",
            "Juventus",
            "2020-02-02 19:00",
            Category.Football,
            1.4,
            1.3,
            true,
            1,
            2
        ),
        Match(
            "8",
            "Chelsea",
            "Real Madrid",
            "2020-02-02 19:00",
            Category.Football,
            1.1,
            2.1,
            true,
            1,
            3
        )
    )

    fun login(email: String, password: String): Boolean {
        currentUser = users.find { it.email == email && it.password == password }
        return currentUser != null
    }

    fun register(email: String, password: String) =
        if (users.none { it.email == email }) {
            users.add(User((users.last().id.toInt() + 1).toString(), email, password))
            currentUser = users.last()
            true
        }
        else
            false

    fun getCurrentUser() = currentUser

    fun updateUser(
        name: String,
        surname: String,
        country: String,
        monthlyLimit: String,
        betLimit: String
    ) = currentUser?.apply {
        if (name.isNotBlank()) this.name = name
        if (surname.isNotBlank()) this.surname = surname
        if (country.isNotBlank()) this.country = country
        if (monthlyLimit.isNotBlank()) this.monthlyLimit = monthlyLimit.toDouble()
        if (betLimit.isNotBlank()) this.betLimit = betLimit.toDouble()
    }


    fun addFunds(amount: Double) {
        val currentBalance = currentUser?.balance ?: 0.0
        currentUser?.balance = currentBalance + amount
    }

    fun addBet(bet: Bet) {
        currentUser?.bets?.add(bet)
    }

    fun deleteCurrentUser() {
        users.remove(currentUser)
        currentUser = null
    }

    fun getMatches() = matches.toList()

    fun getMatchById(id: String) = matches.first { it.id == id }

    fun getBets() = currentUser?.bets ?: listOf<Bet>()

    fun getBetByMatchId(id: String) = currentUser?.bets?.first { it.matchId == id }
}