/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chenyuting.com.nfccustomer.services;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

//import com.example.android.common.logger.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Arrays;

import chenyuting.com.nfccustomer.Util.AESUtils;
import chenyuting.com.nfccustomer.Util.RSAUtils;
import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Receipt;
import chenyuting.com.nfccustomer.models.UseCouponRecord;
import chenyuting.com.nfccustomer.tool.AccountStorage;
import chenyuting.com.nfccustomer.tool.Commands;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.NetTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import chenyuting.com.nfccustomer.tool.SharedPreferenceTool;
import chenyuting.com.nfccustomer.tool.UseNetTool;
import yalantis.com.sidemenu.sample.MainActivity;

/**
 * This is a sample APDU Service which demonstrates how to interface with the card emulation support
 * added in Android 4.4, KitKat.
 *
 * <p>This sample replies to any requests sent with the string "Hello World". In real-world
 * situations, you would need to modify this code to implement your desired communication
 * protocol.
 *
 * <p>This sample will be invoked for any terminals selecting AIDs of 0xF11111111, 0xF22222222, or
 * 0xF33333333. See src/main/res/xml/aid_list.xml for more details.
 *
 * <p class="note">Note: This is a low-level interface. Unlike the NdefMessage many developers
 * are familiar with for implementing Android Beam in apps, card emulation only provides a
 * byte-array based communication channel. It is left to developers to implement higher level
 * protocol support as needed.
 */
public class CardService extends HostApduService {
    private static final String TAG = "CardService";
    // AID for our loyalty card service.
    private static final String SAMPLE_LOYALTY_CARD_AID = "F222222222";
    // ISO-DEP command HEADER for selecting an AID.
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    private static final String SELECT_APDU_HEADER = "00A40400";
    //Send data self-defined++++++
    private static final String SEND_RECEIPT_DATA = "88A40000";
    // "OK" status word sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = BuildSelectApdu(SAMPLE_LOYALTY_CARD_AID);
    private Intent broadcastIntent = new Intent("com.example.communication.RECEIVER");
    //wether get all receipt data
    private boolean getReceiptTextData = false;
    private boolean isGetReceiptPictureData = false;

    /**
     * Called if the connection to the NFC card is lost, in order to let the application know the
     * cause for the disconnection (either a lost link, or another AID being selected by the
     * reader).
     *
     * @param reason Either DEACTIVATION_LINK_LOSS or DEACTIVATION_DESELECTED
     */
    @Override
    public void onDeactivated(int reason) { }

    /**
     * This method will be called when a command APDU has been received from a remote device. A
     * response APDU can be provided directly by returning a byte-array in this method. In general
     * response APDUs must be sent as quickly as possible, given the fact that the user is likely
     * holding his device over an NFC reader when this method is called.
     *
     * <p class="note">If there are multiple services that have registered for the same AIDs in
     * their meta-data entry, you will only get called if the user has explicitly selected your
     * service, either as a default or just for the next tap.
     *
     * <p class="note">This method is running on the main thread of your application. If you
     * cannot return a response APDU immediately, return null and use the {@link
     * #sendResponseApdu(byte[])} method later.
     *
     * @param commandApdu The APDU that received from the remote device
     * @param extras A bundle containing extra data. May be null.
     * @return a byte-array containing the response APDU, or null if no response APDU can be sent
     * at this point.
     */
    // BEGIN_INCLUDE(processCommandApdu)
    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.w(TAG, "Received APDU: " + ByteArrayToHexString(commandApdu));
        // If the APDU matches the SELECT AID command for this service,
        // send the loyalty card account number, followed by a SELECT_OK status trailer (0x9000).

        if (Arrays.equals(SELECT_APDU, commandApdu)) {
            String account = AccountStorage.GetAccount(this);
            byte[] accountBytes = account.getBytes();
            Log.i(TAG, "Sending account number: " + account);
            return ConcatArrays(accountBytes, SELECT_OK_SW);
        }
        else if(Arrays.equals(HexStringToByteArray(Commands.SEND_NEW_RECEIPT_DATA), Arrays.copyOf(commandApdu,4))){
           // String data = ByteArrayToHexString(Arrays.copyOfRange(commandApdu, 5,(commandApdu.length-1)));
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setClass(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            String data = ByteArrayToHexString(Arrays.copyOfRange(commandApdu, 7,(commandApdu.length-1)));
            String receiptData = hexStr2Str(data)+"}";
            Log.w(TAG, "Received send data command"+ receiptData);

//            try {
//                JSONObject jsonData = new JSONObject(data);
//                String rsaAesKey = jsonData.getString("key");
//                String aesToContext = jsonData.getString("content");
//                RSAPrivateKey privateKey= RSAUtils.loadPrivateKey(Commands.PRIVATE_KEY_STR);
//                //RSA私钥解密加密过后的AES生成的密钥匙
//                String aesRKey=RSAUtils.decryptByPrivateKey(rsaAesKey, privateKey);
//                String txt= AESUtils.decrypt(aesToContext, aesRKey);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            SharedPreferenceTool sharedPreferenceTool = new SharedPreferenceTool();
            sharedPreferenceTool.write(Parameter.K_RECEIPT_DATA,receiptData, this.getBaseContext());
            //Log.w(TAG, "read"+sharedPreferenceTool.read(Parameter.RECEIPT, this.getBaseContext()));
            this.getReceiptTextData = true;
            Log.w(TAG, "Received text data"+ this.getReceiptTextData);
            //发送Action为com.example.communication.RECEIVER的广播
            //if(this.getReceiptTextData&&this.isGetReceiptPictureData) {
//                Intent intent = new Intent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setClass(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
                broadcastIntent.putExtra(Commands.COMMAND, Commands.COMMAND_GET_NEW_RECEIPT);
                sendBroadcast(broadcastIntent);
                this.getReceiptTextData = this.isGetReceiptPictureData = false;
            //insert coupon and receipt here
            Log.w(TAG, "command send new receipt insert coupon and receipt here");
            DataBaseTool dataBaseTool = new DataBaseTool();
            //dataBaseTool.initiateDataBase(this.getBaseContext());

            JSONObject object = null;
            try {
                object = new JSONObject(receiptData);
                String isUploadedBtnState = SharedPreferenceTool.read(Parameter.K_SETTING_UPLOAD_DATA,getBaseContext());
                final Receipt receipt  = new Receipt();
                final String shopId = object.getString(Parameter.K_SHOP_ID);
                receipt.setReceiptNumber(object.getString(Parameter.K_RECEIPT_ID));
                if(isUploadedBtnState.equals(Parameter.V_TOGGLE_BUTTON_ON)){
                    //上传到网络

                    Thread myThread = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            DataBaseTool dataBaseTool = new DataBaseTool();
                            NetTool netTool = new NetTool();
                            UseNetTool useNetTool = new UseNetTool();
                            useNetTool.customerUploadReceipt(receipt, shopId,
                                    SharedPreferenceTool.read(Parameter.K_RECEIPT_DATA,getBaseContext()),getBaseContext());

                        }
                    };
                    myThread.start();
                    Log.w(TAG, "receipt is uploaded to the server");
                    receipt.setIsUploaded(Parameter.V_UPLOAD);
                }
                else{
                    Log.w(TAG, "receipt is not uploaded to the server");
                    receipt.setIsUploaded(Parameter.V_NOT_UPLOAD);
                }

                dataBaseTool.insertReceipt(this.getBaseContext(), receipt, object.getString(Parameter.K_SHOP_ID), receiptData);
                //insert coupons
                ArrayList<Coupon> couponList = new ArrayList<Coupon>();
                JSONArray coupons = object.getJSONArray(Parameter.K_COUPON);
                String text = "";
                for(int i=0;i<coupons.length();i++){
                    Coupon coupon = new Coupon();
                    coupon.setShopId(object.getString(Parameter.K_SHOP_ID));
                    JSONObject couponJson = coupons.getJSONObject(i);
                    coupon.setNum(Integer.parseInt(couponJson.getString(Parameter.K_RULE_NUMBER)));
                    coupon.setType(couponJson.getString(Parameter.K_COUPON_TYPE));
                    coupon.setId(couponJson.getString(Parameter.K_COUPON_ID));
                    coupon.setDescription(couponJson.getString(Parameter.K_DESCRIPTION));
                    coupon.setServerPictureURL(couponJson.getString(Parameter.K_SERVER_PICTURE_URL));
                    text += "You get "+coupon.getNum()+" "+coupon.getId()+"\n";
                    Log.w(TAG, text);
                    couponList.add(coupon);
                    dataBaseTool.insertCoupon(this.getBaseContext(),coupon);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //}
            return ConcatArrays("33333".getBytes(), SELECT_OK_SW);
        }else if(Arrays.equals(HexStringToByteArray(Commands.USE_ADVERTISEMENT), Arrays.copyOf(commandApdu,4))){

            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            String data = ByteArrayToHexString(Arrays.copyOfRange(commandApdu, 7,(commandApdu.length-1)));
            String receiptData = hexStr2Str(data)+"}";
            Log.w(TAG, "Received send data command"+ receiptData);
            SharedPreferenceTool sharedPreferenceTool = new SharedPreferenceTool();
            sharedPreferenceTool.write(Parameter.K_USE_ADVERTISEMENT_DATA,receiptData, this.getBaseContext());
            //Log.w(TAG, "read"+sharedPreferenceTool.read(Parameter.RECEIPT, this.getBaseContext()));
//            this.getReceiptTextData = true;
//            Log.w(TAG, "Received text data"+ this.getReceiptTextData);
            //发送Action为com.example.communication.RECEIVER的广播
            //if(this.getReceiptTextData&&this.isGetReceiptPictureData) {
//                Intent intent = new Intent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setClass(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
            broadcastIntent.putExtra(Commands.COMMAND, Commands.COMMAND_USE_ADVERTISEMENT);
            sendBroadcast(broadcastIntent);
            //this.getReceiptTextData = this.isGetReceiptPictureData = false;
            //insert coupon and receipt here
            Log.w(TAG, "command USE ADVERTISEMTN");
            JSONObject useRecordData = null;
            try {
                useRecordData = new JSONObject(SharedPreferenceTool.read(Parameter.K_USE_ADVERTISEMENT_DATA, this.getBaseContext()));
                UseCouponRecord useCouponRecord = new UseCouponRecord();
                useCouponRecord.setAdvertisementId(useRecordData.getString(Parameter.K_ADVERTISEMENT_ID));
                useCouponRecord.setShopName(useRecordData.getString(Parameter.K_SHOP_ID));
                useCouponRecord.setCouponId(useRecordData.getString(Parameter.K_COUPON_ID));
                useCouponRecord.setUseNum(useRecordData.getString(Parameter.K_RULE_NUMBER));
                useCouponRecord.setExchangeProductId(useRecordData.getString(Parameter.K_RULE_EXCHANGE_PRODUCT_ID));
                useCouponRecord.setDateTime(useRecordData.getString(Parameter.K_DATE_TIME));
                DataBaseTool dataBaseTool = new DataBaseTool();
                dataBaseTool.insertUseCouponRecord(this.getBaseContext(),useCouponRecord);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return ConcatArrays("33333".getBytes(), SELECT_OK_SW);
        }
        else {
            return UNKNOWN_CMD_SW;
        }
    }
    // END_INCLUDE(processCommandApdu)

    /**
     * Build APDU for SELECT AID command. This command indicates which service a reader is
     * interested in communicating with. See ISO 7816-4.
     *
     * @param aid Application ID (AID) to select
     * @return APDU for SELECT AID command
     */
    public static byte[] BuildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X",
                aid.length() / 2) + aid);
    }

    /**
     * Utility method to convert a byte array to a hexadecimal string.
     *
     * @param bytes Bytes to convert
     * @return String, containing hexadecimal representation.
     */
    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }

    /**
     * 字符串转换成十六进制字符串
     * @param str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */

    public static String str2HexStr(String str)
    {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            //sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     * @param hexStr Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr)
    {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * Utility method to convert a hexadecimal string to a byte string.
     *
     * <p>Behavior with input strings containing non-hexadecimal characters is undefined.
     *
     * @param s String containing hexadecimal characters to convert
     * @return Byte array generated from input
     * @throws java.lang.IllegalArgumentException if input length is incorrect
     */
    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Utility method to concatenate two byte arrays.
     * @param first First array
     * @param rest Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
