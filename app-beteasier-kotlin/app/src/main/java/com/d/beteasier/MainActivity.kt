package com.d.beteasier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d.beteasier.bet.BetsFragment
import com.d.beteasier.match.MatchesFragment
import com.d.beteasier.profile.ProfileFragment
import com.d.beteasier.util.replaceAndCommit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val matchesFragment: MatchesFragment by lazy {
        MatchesFragment()
    }
    private val betsFragment: BetsFragment by lazy {
        BetsFragment()
    }
    private val profileFragment: ProfileFragment by lazy {
        ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() =
        bottomNavigationView.apply {
            setOnNavigationItemSelectedListener {
                show(it.itemId)
                true
            }
            bottomNavigationView.selectedItemId = R.id.matchesItem
        }

    private fun show(menuItemId: Int) {
        val fragment = when (menuItemId) {
            R.id.matchesItem -> matchesFragment
            R.id.betsItem -> betsFragment
            else -> profileFragment
        }
        replaceAndCommit(R.id.fragmentContainer, fragment) {
            setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        }
    }
}