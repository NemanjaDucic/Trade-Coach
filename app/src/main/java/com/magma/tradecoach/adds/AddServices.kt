package com.magma.tradecoach.adds

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.magma.tradecoach.utilities.DatabaseProvider

class AddServices {
    private val database = DatabaseProvider()
    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
    private val AD_VIDEO_ID = ""
   private val Rewarded_Interstitial = 	"ca-app-pub-3940256099942544/5354046379"
    private var rewardedAd: RewardedAd? = null
     fun showRewardedVideo(activity: Activity) {
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        println("Ad was dismissed.")
                        rewardedAd = null

                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        println("Ad failed to show.")

                        rewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        println("Ad showed fullscreen content.")

                    }
                }

            rewardedAd?.show(
                activity,
                OnUserEarnedRewardListener { rewardItem ->

                    database.rewardMagmaCoins(5.00)

                    println("User earned the reward.")
                }
            )
        }
    }
     fun loadRewardedAd(activity: Activity) {
        if (rewardedAd == null) {
            val adRequest = AdRequest.Builder().build()

            RewardedAd.load(
                activity,
                AD_UNIT_ID,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        println(adError?.message)
                        rewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d(ContentValues.TAG, "Ad was loaded.")
                        rewardedAd = ad
                        showRewardedVideo(activity)
                    }
                }
            )
        }
    }
}