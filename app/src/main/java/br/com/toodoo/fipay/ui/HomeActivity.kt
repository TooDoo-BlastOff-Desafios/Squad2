package br.com.toodoo.fipay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.toodoo.fipay.R
import br.com.toodoo.fipay.adapter.UpcomingBillAdapter
import br.com.toodoo.fipay.helper.FirebaseHelper
import br.com.toodoo.fipay.model.Bill
import java.math.BigInteger

class HomeActivity : AppCompatActivity() {

    private lateinit var upcomingBillAdapter: UpcomingBillAdapter
    private lateinit var rvUpcomingBill: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val user = FirebaseHelper.getFirebaseAuth().currentUser

        findViewById<TextView>(R.id.toolbar_title).text = "Goord morning, John!"

        upcomingBillAdapter = UpcomingBillAdapter(bills())
        rvUpcomingBill = findViewById(R.id.rvUpcomingBill)
        rvUpcomingBill.adapter = upcomingBillAdapter
        rvUpcomingBill.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun bills(): ArrayList<Bill> {
        return arrayListOf<Bill>(
            Bill("Market bills", "December, 28, 2021"),
            Bill("Supermarket bills", "December, 28, 2021"),
            Bill("Store bills", "December, 28, 2021"),
            Bill("Wifi bills", "December, 28, 2021"),
            Bill("Supermarket bills", "December, 28, 2021")
        )
    }
}