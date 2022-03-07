package com.example.githubusersearch.common.extensions

import android.annotation.SuppressLint
import com.example.githubusersearch.R
import com.example.githubusersearch.business.domain.model.DevLanguage
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.toSince(): String {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(this)

        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val mon = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return "Since $year/$mon/$day"
    } catch (e: ParseException) {
        return this
    }
}

fun String?.toDevLanguage(): DevLanguage {
    return when (this) {
        "C" -> DevLanguage(R.drawable.ic_c, this)
        "C++" -> DevLanguage(R.drawable.ic_c_plus, this)
        "C#" -> DevLanguage(R.drawable.ic_c_sharp, this)
        "CSS" -> DevLanguage(R.drawable.ic_css, this)
        "Go" -> DevLanguage(R.drawable.ic_css, this)
        "HTML" -> DevLanguage(R.drawable.ic_html, this)
        "Java" -> DevLanguage(R.drawable.ic_java, this)
        "JavaScript" -> DevLanguage(R.drawable.ic_javascript, this)
        "Kotlin" -> DevLanguage(R.drawable.ic_kotlin, this)
        "PHP" -> DevLanguage(R.drawable.ic_php, this)
        "Python" -> DevLanguage(R.drawable.ic_python, this)
        "Ruby" -> DevLanguage(R.drawable.ic_ruby, this)
        "Scala" -> DevLanguage(R.drawable.ic_scala, this)
        "Swift" -> DevLanguage(R.drawable.ic_swift, this)
        "TypeScript" -> DevLanguage(R.drawable.ic_typescript, this)
        "Perl" -> DevLanguage(R.drawable.ic_perl, this)
        else -> DevLanguage(R.drawable.ic_default_lang_img, this?: "N/A")
    }
}