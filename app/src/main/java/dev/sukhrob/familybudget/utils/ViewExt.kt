import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dev.sukhrob.familybudget.data.model.Transaction
import dev.sukhrob.familybudget.view.adapter.DataItem
import java.text.SimpleDateFormat
import java.util.*

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun View.snack(
    @StringRes string: Int,
    length: Int = Snackbar.LENGTH_LONG,
    action: Snackbar.() -> Unit = {}
) {
    val snack = Snackbar.make(this, resources.getString(string), length)
    action.invoke(snack)
    snack.show()
}

fun Snackbar.action(
    @StringRes text: Int,
    color: Int? = null,
    listener: (View) -> Unit
) {
    setAction(text, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}

fun TextInputEditText.transformIntoDatePicker(
    context: Context,
    format: String,
    maxDate: Date? = null
) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.UK)
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,
            datePickerOnDataSetListener,
            myCalendar
                .get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}

// uzbek sum converter
fun uzbekSum(amount: Double): String {
//    val format: NumberFormat = NumberFormat.getCurrencyInstance()
//    format.maximumFractionDigits = 0
//    format.currency = Currency.getInstance("SUM")
//    return format.format(amount)
    return amount.toString().plus(" so\'m")
}

val String.cleanTextContent: String
    get() {
        // strips off all non-ASCII characters
        var text = this
        text = text.replace("[^\\x00-\\x7F]".toRegex(), "")

        // erases all the ASCII control characters
        text = text.replace("[\\p{Cntrl}&&[^\r\n\t]]".toRegex(), "")

        // removes non-printable characters from Unicode
        text = text.replace("\\p{C}".toRegex(), "")
        text = text.replace(",".toRegex(), "")
        return text.trim()
    }

// parse string to double
fun parseDouble(value: String?): Double {
    return if (value == null || value.isEmpty()) Double.NaN else value.toDouble()
}

fun List<Transaction>.toListOfDataItem(): List<DataItem> {
    val grouping = this.groupBy { transaction ->
        transaction.createdAt.toDate()
    }

    val listDataItem = mutableListOf<DataItem>()
    grouping.forEach { mapEntry ->
        listDataItem.add(DataItem.HeaderData(mapEntry.key))
        listDataItem.addAll(
            mapEntry.value.map { transaction ->
                DataItem.TransactionData(transaction)
            }
        )
    }

    return listDataItem
}

fun Long.toDate(): String {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat("dd/MM/yyyy")

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

