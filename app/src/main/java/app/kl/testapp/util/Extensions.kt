package app.kl.testapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlin.properties.Delegates

fun <T> invokeOnChangeDelegate(initialValue: T, callback: (old: T, new: T) -> Unit) =
    Delegates.observable(initialValue) { _, old, new -> if (old != new) callback(old, new) }

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

inline fun <reified T> Fragment.getArg(key: String): T {
    val args = requireArguments()
    return when (T::class) {
        String::class -> args.getString(key) as T
        Int::class -> args.getInt(key, Int.MIN_VALUE) as T
        Boolean::class -> args.getBoolean(key) as T
        else -> null
    } ?: throw IllegalArgumentException("Expected argument of type ${T::class.java.simpleName} with key: $key")
}