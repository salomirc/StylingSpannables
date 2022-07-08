package com.example.stylingspannables

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SpannableString()
        val spannable = SpannableString(getString(R.string.text_styling))
        spannable.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(BOLD), 5 ,spannable.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.RED), spannable.length - 1, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannedTextView.text = spannable

        //SpannableStringBuilder()
        val spannableBuilder = SpannableStringBuilder(getString(R.string.text_is_spantastic))
        spannableBuilder.setSpan(StyleSpan(BOLD), 8, 12, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableBuilder.setSpan(ForegroundColorSpan(Color.RED), 8, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableBuilder.insert(12, "(& fon)")
        spantasticTextView.text = spannableBuilder

        //SpannedString()
        val spanned = SpannedString(getString(R.string.text_is_spantastic))
        spannedTextView.text = spanned
        spannedTextView.postDelayed(
            {
                spannedTextView.text = SpannedString(spannableBuilder.apply {
                    this.insert(this.length, "\nbut read-only now!")
                })
            },
            2000
        )

        //Link span
        val spanForUrlLink = SpannableString(getString(R.string.home_page_message)).apply {
            setSpan(URLSpan("https://m.youtube.com"), 6, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textWithUrlTextView.apply {
            text = spanForUrlLink
            movementMethod = LinkMovementMethod.getInstance()
        }

        //Text with hyperlink and other HTML markup used directly from XML layout:
        //Notice that we are using the XML attribute: android:text="@string/text_with_hyperlink"
        hyperlinkTextView.apply {
            movementMethod = LinkMovementMethod.getInstance()
        }

        // Same result as applying the string resource directly from XML layout
        hyperlinkFromCodeTextView.apply {
            val txt: CharSequence = getText(R.string.text_with_hyperlink)
            text = txt
            movementMethod = LinkMovementMethod.getInstance()

            Log.d(TAG, "txt returned by getText() method = $txt")
        }

        //String format with HTML tags and HTML sensitive characters in values
        //Notice that the opening bracket is HTML-escaped, using the &lt; notation.
        //This approach is useful when you have to use string format with arguments
        stylingWithHtmlTextView.apply {
            val username = "<Ciprian>"
            val mailCount = 10
            val escapedUsername: String = TextUtils.htmlEncode(username)
            val url = getString(R.string.home_page_url)

            //Compact version
//            text = Html.fromHtml(
//                getString(R.string.welcome_messages, escapedUsername, mailCount, url),
//                FROM_HTML_MODE_LEGACY
//            )

            // <![CDATA[ ... ]]> version of the string resources
            // val txt: String = getString(R.string.welcome_messages_cdata, escapedUsername, mailCount, url)
            val txt: String = getString(R.string.welcome_messages, escapedUsername, mailCount, url)
            val styledText: Spanned = Html.fromHtml(txt, FROM_HTML_MODE_LEGACY)
            text = styledText
            movementMethod = LinkMovementMethod.getInstance()

            Log.d(TAG, "username = $username")
            Log.d(TAG, "escapedUsername = $escapedUsername")
            Log.d(TAG, "txt = $txt")
            Log.d(TAG, "styledText = $styledText")
        }

        //Styling with spans alternative to above example
        stylingWithHtmlSpannableAlternativeTextView.apply {
            val username = "<Ciprian>"
            val mailCount = 10
            val builder = SpannableStringBuilder(
                getString(R.string.welcome_messages_simple_string)
            ).apply {
                var positionIndex = 7
                insert(7, username)
                positionIndex += username.length + 11
                insert(positionIndex, "$mailCount ")
                var styleIndexStart = positionIndex
                var styleIndexEnd = styleIndexStart + "$mailCount ".length + 12
                setSpan(StyleSpan(BOLD), styleIndexStart, styleIndexEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                styleIndexStart = styleIndexEnd + 1
                styleIndexEnd = styleIndexStart + 3
                setSpan(
                    URLSpan(getString(R.string.home_page_url)),
                    styleIndexStart,
                    styleIndexEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            text = builder
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
