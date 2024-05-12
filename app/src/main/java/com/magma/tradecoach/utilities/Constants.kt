package com.magma.tradecoach.utilities

import com.magma.tradecoach.R
import com.magma.tradecoach.model.PurchaseModel

object Constants {
      const val LOGGED_KEY = "STAY_LOGGED"
      const val PURCHASE_ITEM_COUNT = 6
      const val PURCHASE_GRID_COUNT = 3
      const val CHAT_LIMIT = 50
      const val BLOG_POST_LIMIT = 100
      const val MAIN = "MAIN"
      const val COMPARE = "CompareToMGM"
      const val GROWTH = "Growth"
      const val HOME_ITEM_LIMIT = 3
      const val LAST_LOGIN_DAY = "last_login_day"
      const val CONSECUTIVE_DAYS = "num_consecutive_days"
      const val ONE = 1

      const val MAGMA_PACK_ID_250 = "product_id_250"
      const val MAGMA_PACK_ID_500 = "product_id_500"
      const val MAGMA_PACK_ID_750 = "product_id_750"
      const val MAGMA_PACK_ID_1000 = "product_id_1000"
      const val MAGMA_PACK_ID_1250 = "product_id_1250"
      const val MAGMA_PACK_ID_1500 = "product_id_1500"
      const val SUBSCRIPTION_ID = "sub_id_some"
      val PURCHASE_OPTIONS_IDS = arrayListOf(MAGMA_PACK_ID_250, MAGMA_PACK_ID_500, MAGMA_PACK_ID_750,
            MAGMA_PACK_ID_1000, MAGMA_PACK_ID_1250, MAGMA_PACK_ID_1500)
      val PURCHASE_OPTIONS = arrayListOf(PurchaseModel(R.drawable.mcoin,"250 Coin Pack"),PurchaseModel(R.drawable.mcoin,"500 Coin Pack"),PurchaseModel(R.drawable.mcoin,"750 Coin Pack"),
            PurchaseModel(R.drawable.mcoin,"1000 Coin Pack"),PurchaseModel(R.drawable.mcoin,"1250 Coin Pack"),PurchaseModel(R.drawable.mcoin,"1500 Coin Pack"))
}