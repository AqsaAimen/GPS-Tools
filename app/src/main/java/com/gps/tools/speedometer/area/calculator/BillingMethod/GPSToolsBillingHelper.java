package com.gps.tools.speedometer.area.calculator.BillingMethod;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.gps.tools.speedometer.area.calculator.AddsManager.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class GPSToolsBillingHelper implements PurchasesUpdatedListener {

    private BillingClient googleBillingGPSToolsClient;
    private static final String TAG = "BillingLoggerInfo";
    private Context mConnect;
    private final ArrayList<SkuDetails> listAvailGPSToolsPurchases = new ArrayList<>();

    private final TinyDB db;

    public GPSToolsBillingHelper(Context activityContext) {
        mConnect=activityContext;
        this.db = new TinyDB(activityContext);
        initMyBillingClientGPSTools(activityContext);
    }

    private void initMyBillingClientGPSTools(Context activityContext) {
        googleBillingGPSToolsClient = BillingClient.newBuilder(activityContext)
                .enablePendingPurchases()
                .setListener(this)
                .build();
        googleBillingGPSToolsClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Google Billing is Connected");
                    fetchGPSToolsAllInAppsFromConsole();
                    fetchGpsToolsPurchasedSubscription();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.d(TAG, "Google Billing is Disconnected");
            }
        });
    }

    private void fetchGPSToolsAllInAppsFromConsole() {
        List<String> skuListToQuery = new ArrayList<>();
        skuListToQuery.add("remove_ads");
        SkuDetailsParams params = SkuDetailsParams.newBuilder()
                .setSkusList(skuListToQuery)
                .setType(BillingClient.SkuType.INAPP)
                .build();
        googleBillingGPSToolsClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetails) {
                if (skuDetails != null) {
                    for (SkuDetails skuDetail : skuDetails) {
                        listAvailGPSToolsPurchases.add(skuDetail);
                        Log.d(TAG, "SKU Details: " + skuDetail.toString());
                        Log.d(TAG, "List Size: " + listAvailGPSToolsPurchases.size());
                    }
                } else {
                    Log.i(TAG, "No SKUs for this application");
                }
            }
        });
    }

    public void fetchGpsToolsPurchasedSubscription() {
        googleBillingGPSToolsClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder()
                        .setProductType(BillingClient.SkuType.INAPP)
                        .build(),
                (billingResult, purchaseList) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        if (purchaseList != null && !purchaseList.isEmpty()) {
                            for (Purchase singlePurchase : purchaseList) {
                                if (singlePurchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                                    Log.d(TAG, "Product Purchased: " + singlePurchase.getSkus().get(0));
                                    Log.d(TAG, "Purchased Subscription: " + singlePurchase);
                                    db.putInAppPurchases(true);
                                }
                            }
                        } else {
                            Log.d(TAG, "No purchases found");
                            db.putInAppPurchases(false);
                        }
                    } else {
                        Log.d(TAG, "Billing Checker Failed: " + billingResult.getResponseCode());
                    }
                });
    }

    public void purchaseProduct() {
        Log.d(TAG, "Going to purchase ads_purchase");
        if (!listAvailGPSToolsPurchases.isEmpty()) {
            try {
                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(listAvailGPSToolsPurchases.get(0))
                        .build();
                BillingResult responseCode = googleBillingGPSToolsClient.launchBillingFlow((Activity) mConnect, flowParams);
                Log.d(TAG, "Google Billing Response: " + responseCode.getResponseCode());
            } catch (Exception e) {
                Log.e(TAG, "Error during purchase: ", e);
            }
        } else {
            Log.d(TAG, "Nothing to purchase for Google Billing"+listAvailGPSToolsPurchases);
        }
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                db.putInAppPurchases(true);
                Log.d(TAG, "Successfully Purchased: " + purchase.getSkus().get(0));
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "Google Billing Cancelled");
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            Log.d(TAG, "Google Billing Purchased Already");
            db.putInAppPurchases(true);

            Toast.makeText(mConnect, "You have already purchased this item", Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "Google Billing Error: " + billingResult.getResponseCode());
        }
    }

    private final AcknowledgePurchaseResponseListener purchaseAcknowledgedListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
            Log.d(TAG, "Successfully Acknowledged: " + billingResult.getResponseCode() + ": " + billingResult.getDebugMessage());
            fetchGpsToolsPurchasedSubscription();
        }
    };

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
            Log.d(TAG, "Process acknowledging: " + purchase.getSkus().get(0));
            AcknowledgePurchaseParams acknowledgeParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.getPurchaseToken())
                    .build();
            googleBillingGPSToolsClient.acknowledgePurchase(acknowledgeParams, purchaseAcknowledgedListener);
            db.putInAppPurchases(true);
        }
    }
}

