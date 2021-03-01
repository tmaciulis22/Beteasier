package com.d.beteasier.util

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.d.beteasier.base.BaseFragment

fun <T : BaseFragment> FragmentActivity.replaceAndCommit(
    @IdRes containerId: Int,
    fragment: T,
    extraCalls: FragmentTransaction.() -> Unit = {}
) =
    supportFragmentManager.beginTransaction().apply {
        if (fragment.isAdded) return@apply
        extraCalls()
        replace(containerId, fragment, fragment::class.java.name)
        safeCommit(lifecycle.currentState)
    }

fun FragmentTransaction.safeCommit(lifecycleState: Lifecycle.State) =
    if (lifecycleState.isAtLeast(Lifecycle.State.RESUMED))
        commit()
    else
        commitAllowingStateLoss()

inline fun <T> List<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? {
    val index = indexOfFirst(predicate)
    if (index == -1) {
        return null
    }
    return index
}