package chenyuting.com.nfccustomer.tool;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Receipt;

/**
 * Created by chenyuting on 11/12/17.
 */

public class UseNetTool {
    private String TAG = "UseNetTool";
    public String getAdvertisementDataFromServer(){
        NetTool netTool = new NetTool();
        String result="";
        String requsetBody = Parameter.K_ADVERTISEMENT_FUNCTION+"="+ Parameter.V_COUPON_FUNCTION_GET_ADVERTISEMENT_DATA;
        try {
            result = netTool.postRequest(Parameter.ADVERTISEMENT_URL, requsetBody).trim();
            Log.w(TAG, "data from server"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean customerUploadReceipt(Receipt receipt, String shopId, String content , Context con) {
        NetTool netTool = new NetTool();
        try {

            AESUtil aesUtil = new AESUtil("chenyuting1234");
            String username = aesUtil.encrypt(AccountStorage.GetAccount(con)); //Rsa.encryptByPublic(AccountStorage.GetAccount(con));
            Log.w(TAG, "encrypted message"+username);

            String requsetBody = Parameter.K_RECEIPT_FUNCTION+ "=" + Parameter.V_COUPON_FUNCTION_CUSTOMER_INSERT_RECEIPT + "&"
                    + Parameter.K_RECEIPT_ID + "=" + receipt.getReceiptNumber()
                    + "&" + Parameter.K_SHOP_ID + "=" + shopId
                    + "&" + Parameter.K_CONTENT + "=" + content
                    //+ "&" + Parameter.K_USERNAMEM + "=" + username;
                    + "&" + Parameter.K_USERNAMEM + "=" + AccountStorage.GetAccount(con);

            String result = netTool.postRequest(Parameter.RECEIPT_URL, requsetBody).trim();
            Log.w(TAG, result+receipt.getReceiptNumber()+content);
            if (result.equals(Parameter.UPLOAD_SUCCESS)) {
                //dataBaseTool.deleteVideoById(con, video.getId());
                Log.w(TAG, "customer insert receipt success");
            } else Log.w(TAG, "customer insert rule failed");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//    public boolean uploadCoupon(Coupon coupon, Context con){
//        NetTool netTool = new NetTool();
//        DataBaseTool dataBaseTool = new DataBaseTool();
//        try {
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            String picPath="";
//            Cursor cursor = null;
//
//                cursor = con.getContentResolver()
//                        .query(coupon.getLocalPictureURL(), filePathColumn, null, null, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                int columnIndex = cursor
//                        .getColumnIndex(filePathColumn[0]);
//                picPath = cursor.getString(columnIndex);
//                cursor.close();
//                Log.w(TAG, ""+picPath);
//            }
//            String filename = picPath.substring(picPath.lastIndexOf("/")+1);
//            String result = NetTool.uploadFile(Parameter.UPLOAD_IMAGE_URL, picPath,filename);//coupon.getId());
//            Log.w(TAG, result.trim()+Parameter.UPLOAD_SUCCESS);
//
//            if(result.trim().equals(Parameter.UPLOAD_SUCCESS)){
////                String imageFilename = coupon.getImageUri().substring(video.getImageUri().lastIndexOf("/") + 1);
////                String videoFilename = video.getVideoUri().substring(video.getVideoUri().lastIndexOf("/") + 1);
////                Log.d(TAG, "imageFilename"+imageFilename);
////                Log.d(TAG, "videoFilename"+videoFilename);
//                String requsetBody = Parameter.K_COUPON_FUNCTION+"="+ Parameter.V_COUPON_FUNCTION_INSERT_COUPON+"&"+Parameter.K_COUPON_ID+"="+coupon.getId()
//                        +"&"+Parameter.K_SHOP_ID+"="+SharedPreferenceTool.read(Parameter.K_SHOP_ID, con)
//                        +"&"+Parameter.K_COUPON_TYPE+"="+coupon.getType()
//                        +"&"+Parameter.K_DESCRIPTION+"="+coupon.getDescription()
//                        +"&"+Parameter.K_LOCAL_PICTURE_URL+"="+coupon.getLocalPictureURL()
//                        +"&"+Parameter.K_SERVER_PICTURE_URL+"="+coupon.getServerPictureURL();
//                result = netTool.postRequest(Parameter.COUPON_URL, requsetBody).trim();
//                Log.w(TAG, result);
//                if(result.equals(Parameter.UPLOAD_SUCCESS)){
//                    //dataBaseTool.deleteVideoById(con, video.getId());
//                    uploadRules(coupon.getRulesList(),con);
//                    Log.w(TAG, "insert coupon success");
//                }else Log.w(TAG, "insert coupon failed");
//
//
//            }else Log.d(TAG, "upload coupon failed");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//
//    public boolean uploadAdvertisement(Advertisement advertisement, Context con){
//        NetTool netTool = new NetTool();
//        DataBaseTool dataBaseTool = new DataBaseTool();
//        try {
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            String picPath="";
//            Cursor cursor = null;
//
//            cursor = con.getContentResolver()
//                    .query(advertisement.getLocalPictureURL(), filePathColumn, null, null, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                int columnIndex = cursor
//                        .getColumnIndex(filePathColumn[0]);
//                picPath = cursor.getString(columnIndex);
//                cursor.close();
//                Log.w(TAG, ""+picPath);
//            }
//            String filename = picPath.substring(picPath.lastIndexOf("/")+1);
//            String result = NetTool.uploadFile(Parameter.UPLOAD_IMAGE_URL, picPath,filename);//coupon.getId());
//            Log.w(TAG, result.trim()+Parameter.UPLOAD_SUCCESS);
//
//            if(result.trim().equals(Parameter.UPLOAD_SUCCESS)){
////                String imageFilename = coupon.getImageUri().substring(video.getImageUri().lastIndexOf("/") + 1);
////                String videoFilename = video.getVideoUri().substring(video.getVideoUri().lastIndexOf("/") + 1);
////                Log.d(TAG, "imageFilename"+imageFilename);
////                Log.d(TAG, "videoFilename"+videoFilename);
//                String requsetBody = Parameter.K_ADVERTISEMENT_FUNCTION+"="+ Parameter.V_COUPON_FUNCTION_INSERT_ADVERTISEMENT+"&"+Parameter.K_ADVERTISEMENT_ID+"="+advertisement.getId()
//                        +"&"+Parameter.K_SHOP_ID+"="+SharedPreferenceTool.read(Parameter.K_SHOP_ID, con)
//                        +"&"+Parameter.K_COUPON_ID+"="+advertisement.getCouponId()
//                        +"&"+Parameter.K_DESCRIPTION+"="+advertisement.getDescription()
//                        +"&"+Parameter.K_LOCAL_PICTURE_URL+"="+advertisement.getLocalPictureURL()
//                        +"&"+Parameter.K_SERVER_PICTURE_URL+"="+advertisement.getServerPictureURL();
//                result = netTool.postRequest(Parameter.ADVERTISEMENT_URL, requsetBody).trim();
//                Log.w(TAG, result);
//                if(result.equals(Parameter.UPLOAD_SUCCESS)){
//                    //dataBaseTool.deleteVideoById(con, video.getId());
//                    uploadRules(advertisement.getRulesList(),con);
//                    Log.w(TAG, "insert advertisement success");
//                }else Log.w(TAG, "insert advertisement failed");
//
//
//            }else Log.d(TAG, "upload advertisement failed");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//
//    public boolean uploadRules(ArrayList<Rule> rules , Context con) {
//        NetTool netTool = new NetTool();
//        try {
//            for (int i = 0; i < rules.size(); i++) {
//                String requsetBody = Parameter.K_COUPON_FUNCTION + "=" + Parameter.V_COUPON_FUNCTION_INSERT_RULE + "&"
//                        + Parameter.K_RULE_ID + "=" + rules.get(i).getRuleID()
//                        + "&" + Parameter.K_SHOP_ID + "=" + SharedPreferenceTool.read(Parameter.K_SHOP_ID, con)
//                        + "&" + Parameter.K_RULE_FOR_TYPE + "=" + rules.get(i).getForType()
//                        + "&" + Parameter.K_RULE_TYPE + "=" + rules.get(i).getRuleType()
//                        + "&" + Parameter.K_RULE_VALID_DATE_FROM + "=" + rules.get(i).getValidDateFrom()
//                        + "&" + Parameter.K_RULE_VALID_DATE_TO + "=" + rules.get(i).getValidDateTo()
//                        + "&" + Parameter.K_RULE_NUMBER + "=" + rules.get(i).getNumber()
//                        + "&" + Parameter.K_RULE_TOTAL_COST + "=" + rules.get(i).getTotalCost()
//                        + "&" + Parameter.K_RULE_EXCHANGE_PRODUCT_ID + "=" + rules.get(i).getExchangeProudctID()
//                        + "&" + Parameter.K_COUPON_ID + "=" + rules.get(i).getCouponID();
//
//                String result = netTool.postRequest(Parameter.COUPON_URL, requsetBody).trim();
//                Log.w(TAG, result+rules.get(i).getValidDateFrom()+rules.get(i).getValidDateTo());
//                if (result.equals(Parameter.UPLOAD_SUCCESS)) {
//                    //dataBaseTool.deleteVideoById(con, video.getId());
//                    Log.w(TAG, "insert rule success");
//                } else Log.w(TAG, "insert rule failed");
//
//            }
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
