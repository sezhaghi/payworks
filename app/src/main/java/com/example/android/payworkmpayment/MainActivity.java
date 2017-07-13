package com.example.android.payworkmpayment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

public void Pay(View v){
    void paymentButtonClicked() {
        MposUi ui = MposUi.initialize(this, ProviderMode.MOCK,
                "merchantIdentifier", "merchantSecretKey");

        ui.getConfiguration().setSummaryFeatures(EnumSet.of(
                // Add this line, if you do want to offer printed receipts
                // MposUiConfiguration.SummaryFeature.PRINT_RECEIPT,
                MposUiConfiguration.SummaryFeature.SEND_RECEIPT_VIA_EMAIL)
        );

        // Start with a mocked card reader:
        AccessoryParameters accessoryParameters = new AccessoryParameters.Builder(AccessoryFamily.MOCK)
                .mocked()
                .build();
        ui.getConfiguration().setTerminalParameters(accessoryParameters);

        // Add this line if you would like to collect the customer signature on the receipt (as opposed to the digital signature)
        // ui.getConfiguration().setSignatureCapture(MposUiConfiguration.SignatureCapture.ON_RECEIPT);

    /* When using the Bluetooth Miura, use the following parameters:
    AccessoryParameters accessoryParameters = new AccessoryParameters.Builder(AccessoryFamily.MIURA_MPI)
                                                                     .bluetooth()
                                                                     .build();
    ui.getConfiguration().setTerminalParameters(accessoryParameters);
    */

    /* When using the WiFi Miura M010, use the following parameters:
    AccessoryParameters accessoryParameters = new AccessoryParameters.Builder(AccessoryFamily.MIURA_MPI)
                                                                     .tcp("192.168.254.123", 38521)
                                                                     .build();
    ui.getConfiguration().setTerminalParameters(accessoryParameters);
    */

        TransactionParameters transactionParameters = new TransactionParameters.Builder()
                .charge(new BigDecimal("5.00"), io.mpos.transactions.Currency.EUR)
                .subject("Bouquet of Flowers")
                .customIdentifier("yourReferenceForTheTransaction")
                .build();

        Intent intent = ui.createTransactionIntent(transactionParameters);
        startActivityForResult(intent, MposUi.REQUEST_CODE_PAYMENT);
    }
}

public void Refund(View v){
    TransactionParameters parameters = new TransactionParameters.Builder()
            .refund("<transactionIdentifer>")
            // For partial refunds, specify the amount to be refunded
            // and the currency from the original transaction
            //.amountAndCurrency(new BigDecimal("1.00"), io.mpos.transactions.Currency.EUR)
            .build();
    Intent intent = ui.createTransactionIntent(transactionParameters);
    startActivityForResult(intent, MposUi.REQUEST_CODE_PAYMENT);

}
