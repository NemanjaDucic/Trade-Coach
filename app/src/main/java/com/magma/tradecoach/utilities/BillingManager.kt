package com.magma.tradecoach.utilities

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.android.billingclient.api.Purchase.PurchaseState.PURCHASED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class BillingManager(
    private val context: Context,
    private val activity: Activity,
    private val listener: BillingListener?
) {

    private val purchaseUpdateListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            // Process the purchases and acknowledge them
            for (purchase in purchases) {
                println("$purchase")
                if (purchase.purchaseState == PURCHASED && !purchase.isAcknowledged) {
                    handlePurchase(purchase)
                }
            }
        }
    }

    private val billingClient: BillingClient =
        BillingClient.newBuilder(context).enablePendingPurchases()
            .setListener(purchaseUpdateListener).build()


    fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // BillingClient is ready, you can query and make purchases.
                } else {
                    // Handle connection failure
                }
            }

            override fun onBillingServiceDisconnected() {
                // Handle the disconnection
            }
        })
    }

    fun endConnection() {
        billingClient.endConnection()
    }

    private fun handlePurchase(purchase: Purchase) {
        val purchaseAcknowledgeResponseListener =
            AcknowledgePurchaseResponseListener { billingResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Purchase acknowledged successfully
                    consumePurchase(purchase)
                } else {
                    // Handle acknowledgment failure
                    listener?.onPurchaseFailed()
                }
            }
        billingClient.acknowledgePurchase(
            AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build(),
            purchaseAcknowledgeResponseListener
        )
    }

    suspend fun purchaseProduct(sku: String, userId: String) {
        val productList = ArrayList<QueryProductDetailsParams.Product>()
        productList.add(
            QueryProductDetailsParams.Product.newBuilder().setProductId(getAmountForSku(sku)).setProductType(BillingClient.ProductType.INAPP)
                .build()
        )
        val params = QueryProductDetailsParams.newBuilder()
        params.setProductList(productList)


        val productDetailsResult = withContext(Dispatchers.IO) {
            billingClient.queryProductDetails(params.build())
        }

        if (productDetailsResult.productDetailsList == null || productDetailsResult.productDetailsList.isNullOrEmpty()) {

            listener?.onPurchaseFailed()
            return
        }

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetailsResult.productDetailsList!!.first()).build()
        )

        val billingFlowParams =
            BillingFlowParams.newBuilder().setProductDetailsParamsList(productDetailsParamsList)
                .setObfuscatedAccountId(userId).setObfuscatedProfileId(userId).build()

        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    private fun consumePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        val listener =
            ConsumeResponseListener { billingResult, purchaseToken ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    println("consumeResult.purchaseToken $purchaseToken")
                    listener?.onPurchaseAcknowledged(purchase)
                } else {
                    listener?.onPurchaseFailed()
                }
            }

        billingClient.consumeAsync(consumeParams, listener)
    }

    fun handlePendingPayments() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { billingResult, purchases ->
            println("purchase $purchases")
            println("billingResult ${billingResult.responseCode}")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in purchases) {
                    if (purchase.purchaseState == PURCHASED && !purchase.isAcknowledged) {
                        handlePurchase(purchase)
                    }
                }
            }
        }
    }
    private fun getAmountForSku(sku: String): String {
        return when (sku) {
            Constants.MAGMA_PACK_ID_250 -> "250"
            Constants.MAGMA_PACK_ID_500 -> "500"
            Constants.MAGMA_PACK_ID_750 -> "750"
            Constants.MAGMA_PACK_ID_1000 -> "1000"
            Constants.MAGMA_PACK_ID_1250 -> "1250"
            Constants.MAGMA_PACK_ID_1500 -> "1500"
            else -> "0"
        }
    }

}

interface BillingListener {
    fun onPurchaseAcknowledged(purchase: Purchase)
    fun onPurchaseFailed()
}




