package com.murtuza4u.androiddevtask

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.murtuza4u.androiddevtask.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.razorPay.setOnClickListener {
            if (binding.edPayment.text.isNullOrBlank()) {
                showToast("Please fill payment")
            } else {
                startPayment()
            }
        }

    }

    private fun startPayment() {
        val checkout = Checkout()
        try {
            val options = JSONObject().apply {
                put("name", "RazorPay")
                put("description", "Service Charge")
                put(
                    "image",
                    "https://png.pngtree.com/png-vector/20210525/ourmid/pngtree-letter-m-logo-png-vector-png-image_3320105.jpg"
                )
                put("theme.color", "#3399cc")
                put("currency", "INR")
                put("amount", (binding.edPayment.text.toString().toDouble() * 100).toInt())

                val preFill = JSONObject().apply {
                    put("email", " ")
                    put("contact", " ")
                }
                put("prefill", preFill)
            }
            checkout.open(this, options)
        } catch (e: Exception) {
            showToast("Error in payment: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        showToast("Payment Successfully Done")
        binding.edPayment.setText("")
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        showToast("Error in payment")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
