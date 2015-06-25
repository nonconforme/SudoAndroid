package com.thinkmobiles.sudo.billing;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import static com.thinkmobiles.sudo.billing.BillingConstants.*;

/**
 * Created by omar on 25.06.15.
 */
public class BillingHelper {
    private Activity activity;

    private IabHelper mHelper;
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;

    private BillingHelperCallback billingHelperCallback;

    public static final String TAG = "BillingHelper";

    private static BillingHelper billingHelper;


    private BillingHelper(Activity activity, BillingHelperCallback billingHelperCallback) {
        this.activity = activity;
        this.billingHelperCallback = billingHelperCallback;
        setupInventoryListener();
        setupPurchaseFinishedListener();
        setupConsumeFinishedListener();
        run();

    }

    public static BillingHelper getInstance(Activity activity, BillingHelperCallback billingHelperCallback) {
        if (billingHelper != null) billingHelper.onDestroyCheck();
        billingHelper = new BillingHelper(activity, billingHelperCallback);
        return billingHelper;


    }

    public void onDestroyCheck() {
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.flagEndAsync();
            mHelper.dispose();
            mHelper = null;
        }
    }

    private void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    private void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(activity);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    private void setupInventoryListener() {
        mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                Log.d(TAG, "Query inventory finished.");

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // Is it a failure?
                if (result.isFailure()) {
                    complain("Failed to query inventory: " + result);
                    return;
                }

                /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */


                // Check for gas delivery -- if we own gas, we should fill up the tank immediately
                Purchase credit10Purchase = inventory.getPurchase(SKU_CREDITS10);
                Purchase credit20Purchase = inventory.getPurchase(SKU_CREDITS20);
                Purchase credit30Purchase = inventory.getPurchase(SKU_CREDITS30);
                Purchase credit40Purchase = inventory.getPurchase(SKU_CREDITS40);


                if (credit10Purchase != null && verifyDeveloperPayload(credit10Purchase)) {
                    Log.d(TAG, "We have gas. Consuming it.");
                    mHelper.consumeAsync(inventory.getPurchase(SKU_CREDITS10), mConsumeFinishedListener);
                    return;
                }
                if (credit10Purchase != null && verifyDeveloperPayload(credit20Purchase)) {
                    Log.d(TAG, "We have gas. Consuming it.");
                    mHelper.consumeAsync(inventory.getPurchase(SKU_CREDITS20), mConsumeFinishedListener);
                    return;
                }
                if (credit10Purchase != null && verifyDeveloperPayload(credit30Purchase)) {
                    Log.d(TAG, "We have gas. Consuming it.");
                    mHelper.consumeAsync(inventory.getPurchase(SKU_CREDITS30), mConsumeFinishedListener);
                    return;
                }
                if (credit10Purchase != null && verifyDeveloperPayload(credit40Purchase)) {
                    Log.d(TAG, "We have gas. Consuming it.");
                    mHelper.consumeAsync(inventory.getPurchase(SKU_CREDITS40), mConsumeFinishedListener);
                    return;
                }

                Log.d(TAG, "Initial inventory query finished; enabling main UI.");


            }
        };


    }

    private void setupPurchaseFinishedListener() {
        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

                // if we were disposed of in the meantime, quit.
                if (mHelper == null) return;

                if (result.isFailure()) {
                    complain("Error purchasing: " + result);

                    return;
                }
                if (!verifyDeveloperPayload(purchase)) {
                    complain("Error purchasing. Authenticity verification failed.");

                    return;
                }

                Log.d(TAG, "Purchase successful.");

                if (purchase.getSku().equals(BillingConstants.SKU_CREDITS10)) {
                    Log.d(TAG, "Purchase is credits. Starting credits consumption.");
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } else if (purchase.getSku().equals(BillingConstants.SKU_CREDITS30)) {
                    Log.d(TAG, "Purchase is credits. Starting credits consumption.");
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                }
                if (purchase.getSku().equals(BillingConstants.SKU_CREDITS20)) {
                    Log.d(TAG, "Purchase is credits. Starting credits consumption.");
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                }
                if (purchase.getSku().equals(BillingConstants.SKU_CREDITS40)) {
                    Log.d(TAG, "Purchase is credits. Starting credits consumption.");
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                }
            }
        };
    }

    private void setupConsumeFinishedListener() {
        mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

                // if we were disposed of in the meantime, quit.
                if (mHelper == null) return;

                if (result.isSuccess()) {
                    switch (purchase.getSku()) {
                        case SKU_CREDITS10:
                            break;
                        case SKU_CREDITS20:
                            break;
                        case SKU_CREDITS30:
                            break;
                        case SKU_CREDITS40:
                            break;
                    }


                } else {
                    complain("Error while consuming: " + result);
                }

                Log.d(TAG, "End consumption flow.");
            }
        };
    }


    private void initIabHelper() {
        mHelper = new IabHelper(activity, BillingConstants.base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
        // Listener that's called when we finish querying the items and subscriptions we own


        Log.d(TAG, "Query inventory was successful.");
    }


    public void run() {


        initIabHelper();

    }

    public void doPurchase10() {
        String payload = "";
        if (mHelper != null) mHelper.flagEndAsync();
        mHelper.launchPurchaseFlow(activity, SKU_CREDITS10, RC_REQUEST, mPurchaseFinishedListener, payload);
    }

    public void doPurchase20() {
        String payload = "";
        if (mHelper != null) mHelper.flagEndAsync();
        mHelper.launchPurchaseFlow(activity, SKU_CREDITS20, RC_REQUEST, mPurchaseFinishedListener, payload);
    }

    public void doPurchase30() {
        String payload = "";
        if (mHelper != null) mHelper.flagEndAsync();
        mHelper.launchPurchaseFlow(activity, SKU_CREDITS30, RC_REQUEST, mPurchaseFinishedListener, payload);
    }

    public void doPurchase40() {
        String payload = "";
        if (mHelper != null) mHelper.flagEndAsync();
        mHelper.launchPurchaseFlow(activity, SKU_CREDITS40, RC_REQUEST, mPurchaseFinishedListener, payload);
    }
}

//