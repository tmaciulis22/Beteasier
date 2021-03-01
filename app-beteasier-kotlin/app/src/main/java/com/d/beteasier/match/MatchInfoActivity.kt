package com.d.beteasier.match

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.d.beteasier.R
import com.d.beteasier.api.Match
import com.d.beteasier.api.Result
import kotlinx.android.synthetic.main.activity_match_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchInfoActivity : AppCompatActivity() {

    private val viewModel: MatchesViewModel by viewModel()

    private val match: Match
        get() = viewModel.model

    private val shouldAllowBets: Boolean by lazy {
        intent.getBooleanExtra("ALLOW_BETS", true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_info)
        initViews()
    }

    private fun initViews() {
        initMatchInfo()

        if (!shouldAllowBets) {
            replaceUIElements()

            val bet = viewModel.getBetByMatchId(match.id)
            val result = bet?.result ?: Result.TBD

            bettingAmountLabel.text = result.text
            bettingAmountView.text = when (result) {
                Result.Won -> {
                    val rate = if (bet?.team == match.firstTeam)
                        match.firstRate
                    else
                        match.secondRate
                    String.format("%.2f EUR", (bet?.amount ?: 0.0) * rate)
                }
                else -> "${bet?.amount.toString()} EUR"
            }
        } else {
            betButton.setOnClickListener {
                val user = viewModel.getCurrentUser()
                val betLimit = user?.betLimit ?: 10000000.0
                val firstBetEditable = firstTeamBet.text
                val secondBetEditable = secondTeamBet.text
                val firstBet = firstBetEditable?.toString() ?: ""
                val secondBet = secondBetEditable?.toString() ?: ""

                if (firstBet.isBlank() && secondBet.isBlank())
                    Toast.makeText(
                        this,
                        "Please enter the amount you wish to bet",
                        Toast.LENGTH_SHORT
                    ).show()
                else if ((!firstBet.isBlank() && firstBet.toDouble() > betLimit)
                    || (!secondBet.isBlank() && secondBet.toDouble() > betLimit)
                )
                    Toast.makeText(this, "You cannot exceed your bet limit", Toast.LENGTH_SHORT)
                        .show()
                else {
                    if (!firstBet.isBlank())
                        viewModel.addBet(
                            userId = user?.id ?: "1",
                            matchId = match.id,
                            team = match.firstTeam,
                            amount = firstBet.toDouble()
                        )
                    if (!secondBet.isBlank())
                        viewModel.addBet(
                            userId = user?.id ?: "1",
                            matchId = match.id,
                            team = match.secondTeam,
                            amount = secondBet.toDouble()
                        )
                    finish()
                }
            }
        }
    }

    private fun initMatchInfo() {
        imageView.setImageResource(match.category.imageRes)
        dateTimeView.text = match.dateTime
        firstTeamView.text = match.firstTeam
        secondTeamView.text = match.secondTeam
        firstRateView.text = match.firstRate.toString()
        secondRateView.text = match.secondRate.toString()
    }

    private fun replaceUIElements() {
        firstTeamBet.visibility = View.GONE
        secondTeamBet.visibility = View.GONE
        betButton.visibility = View.GONE
        bettingAmountLabel.visibility = View.VISIBLE
        bettingAmountView.visibility = View.VISIBLE

        firstTeamScore.visibility = View.VISIBLE
        secondTeamScore.visibility = View.VISIBLE
        scoreDivider.visibility = View.VISIBLE
        firstTeamScore.text = match.firstTeamScore?.toString() ?: "TBD"
        secondTeamScore.text = match.secondTeamScore?.toString() ?: "TBD"
    }
}