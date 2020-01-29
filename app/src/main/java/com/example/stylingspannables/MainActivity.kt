package com.example.stylingspannables

import android.graphics.Color
import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spannableTextStyling = SpannableString(getString(R.string.text_styling_txt))
        spannableTextStyling.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableTextStyling.setSpan(StyleSpan(BOLD), 5 ,spannableTextStyling.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableTextStyling.setSpan(ForegroundColorSpan(Color.RED), spannableTextStyling.length - 1, spannableTextStyling.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textStylingTextView.text = spannableTextStyling

        val spannableSpantastic = SpannableStringBuilder(getString(R.string.text_is_spantastic_txt))
        spannableSpantastic.setSpan(StyleSpan(BOLD), 8, 12, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableSpantastic.setSpan(ForegroundColorSpan(Color.RED), 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableSpantastic.insert(12, "(& fon)")

        spantasticTextView.text = spannableSpantastic
    }
}
