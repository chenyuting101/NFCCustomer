package chenyuting.com.nfccustomer.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Receipt;
import chenyuting.com.nfccustomer.models.Shop;
import chenyuting.com.nfccustomer.models.UseCouponRecord;

/**
 * Created by chenyuting on 8/9/16.
 */

public class DataBaseTool {
    private String dataBaseName = "ReceiptBaseData.db";
    private String receiptTableName = "Receipt";
    private String couponTableName = "Coupon";
    private final String TAG = "DataBaseTool";
    private String useCouponRecordTableName = "UseCouponRecord";

    public void initiateDataBase(Context con){
        Log.w(TAG, "initiateDataBase");

        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        test(sqldb);
        sqldb.execSQL("create table if not exists Coupon (shopId text, couponId text,"
                + "couponType text,description text,number text, serverPictureURL text);");
        sqldb.execSQL("create table if not exists Receipt (receiptId text,"
                + "shopId text,receiptDateTime text,barcode_uri text,isBarcodeCache text, barcode_cache_uri text,"
                + "isUpload text, receiptDescription text, paymentDescription text, content text, username text);");

        sqldb.execSQL("create table if not exists UseCouponRecord (advertisementId text,"
                + "shopId text,dateTime text,couponId text,number text, exchangeProudctID text);");

        sqldb.close();

    }

    public void insertReceipt(Context con, Receipt receipt, String shopId, String content){
        Log.w(TAG, "insert Receipt"+receipt.getReceiptNumber()+" " +shopId + " " +AccountStorage.GetAccount(con) + " " + content
        +" is uploaded" +receipt.getIsUploaded());

        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ContentValues cv = new ContentValues();
        cv.put(Parameter.K_SHOP_ID, shopId);
        cv.put(Parameter.K_CONTENT, content);
        cv.put(Parameter.K_USERNAMEM, AccountStorage.GetAccount(con));
        cv.put(Parameter.K_RECEIPT_ID, Parameter.V_RULE_NULL_DATA);
        cv.put(Parameter.K_RECEIPT_DATETIME, Parameter.V_RULE_NULL_DATA);
        cv.put(Parameter.K_RECEIPT_BARCODE_URI, Parameter.V_RULE_NULL_DATA);
        cv.put(Parameter.K_IS_BARCODE_CACHE, Parameter.V_RULE_NULL_DATA);
        cv.put(Parameter.K_BARCODE_CACHE_URI, Parameter.V_RULE_NULL_DATA);
        cv.put(Parameter.K_IS_UPLOAD, receipt.getIsUploaded());
        cv.put(Parameter.K_RECEIPT_DESCRIPTION, Parameter.V_RULE_NULL_DATA);
        cv.put(Parameter.K_PAYMENT_DESCRIPTION, Parameter.V_RULE_NULL_DATA);
        sqldb.insert(receiptTableName, null, cv);
        sqldb.close();
    }

    public void insertCoupon(Context con, Coupon coupon){
        Log.w(TAG, "insert Coupon"+coupon.getId()+coupon.getNum()+coupon.getType()+coupon.getServerPictureURL()+coupon.getShopId());

        int couponNum = isCouponExist(con, coupon.getId());
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ContentValues cv = new ContentValues();
        if(couponNum == -1) {
            cv.put(Parameter.K_SHOP_ID, coupon.getShopId());
            cv.put(Parameter.K_COUPON_ID, coupon.getId());
            cv.put(Parameter.K_COUPON_TYPE, coupon.getType());
            cv.put(Parameter.K_DESCRIPTION, coupon.getDescription());
            cv.put(Parameter.K_RULE_NUMBER, coupon.getNum());
            cv.put(Parameter.K_SERVER_PICTURE_URL, coupon.getServerPictureURL());
            Log.w(TAG, "insert coupon");
            sqldb.insert(couponTableName, null, cv);
        }else{
            couponNum+=coupon.getNum();
            cv.put(Parameter.K_RULE_NUMBER, couponNum );//key为字段名，value为值
            String whereClause = Parameter.K_COUPON_ID+"=? ";
            //String whereClause = Parameter.K_SHOP_ID+"=?";
            //String whereArgs = username + ","+ Parameter.V_UPLOAD;
            String[] whereArgs = {coupon.getId()};
            sqldb.update(couponTableName, cv, whereClause, whereArgs);
            Log.w(TAG, "update coupon" +coupon.getId() + "num "+couponNum);
        }
        sqldb.close();
    }

    public void insertUseCouponRecord(Context con, UseCouponRecord useCouponRecord){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);

        ContentValues cv = new ContentValues();
        cv.put(Parameter.K_ADVERTISEMENT_ID, useCouponRecord.getAdvertisementId());
        cv.put(Parameter.K_SHOP_ID, useCouponRecord.getShopName());
        cv.put(Parameter.K_DATE_TIME, useCouponRecord.getDateTime());
        cv.put(Parameter.K_COUPON_ID,useCouponRecord.getCouponId());
        cv.put(Parameter.K_RULE_NUMBER,useCouponRecord.getUseNum());
        cv.put(Parameter.K_RULE_EXCHANGE_PRODUCT_ID,useCouponRecord.getExchangeProductId());

        sqldb.insert(useCouponRecordTableName, null, cv);

    }

    public ArrayList<UseCouponRecord> getAllUseCouponRecord(Context con){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<UseCouponRecord> products = new ArrayList<UseCouponRecord>();
        String[] colums = {Parameter.K_ADVERTISEMENT_ID, Parameter.K_SHOP_ID, Parameter.K_DATE_TIME,
                Parameter.K_COUPON_ID, Parameter.K_RULE_NUMBER,Parameter.K_RULE_EXCHANGE_PRODUCT_ID};
        Cursor cur = sqldb.query(useCouponRecordTableName,colums,null,null,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        while(cur.moveToNext()){
            UseCouponRecord useCouponRecord = new UseCouponRecord();
            useCouponRecord.setAdvertisementId(cur.getString(0));
            useCouponRecord.setShopName(cur.getString(1));
            useCouponRecord.setDateTime(cur.getString(2));
            useCouponRecord.setCouponId(cur.getString(3));
            useCouponRecord.setUseNum(cur.getString(4));
            useCouponRecord.setExchangeProductId(cur.getString(5));
            System.out.println("\ngetUseCouponRecord:"+useCouponRecord.getAdvertisementId()+"name:"+useCouponRecord.getCouponId()
                    +"datetime:"+useCouponRecord.getDateTime());
            products.add(useCouponRecord);
        }
        sqldb.close();
        cur.close();
        return products;
    }

    public void updateCouponNum(Context con, String couponId,String couponNum){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ContentValues cv = new ContentValues();
        Log.w(TAG, "update coupon"+couponId+"to num" +couponNum);
        cv.put(Parameter.K_RULE_NUMBER, couponNum );//key为字段名，value为值
        String whereClause = Parameter.K_COUPON_ID+"=? ";
        //String whereClause = Parameter.K_SHOP_ID+"=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = {couponId};
        sqldb.update(couponTableName, cv, whereClause, whereArgs);
        sqldb.close();
    }

    public ArrayList<Coupon> getAllCoupon(Context con){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
        String[] colums = {Parameter.K_COUPON_ID, Parameter.K_COUPON_TYPE, Parameter.K_DESCRIPTION,
                Parameter.K_RULE_NUMBER, Parameter.K_SERVER_PICTURE_URL};
        //String whereClause = Parameter.K_USERNAMEM+"=? AND " + Parameter.K_IS_UPLOAD + "=?";
        //String whereClause = Parameter.K_SHOP_ID+"=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        //String[] whereArgs = {shopId};
        Cursor cur = sqldb.query(couponTableName,colums,null,null,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        while(cur.moveToNext()){
            Log.w(TAG, "getallcoupon");
            Coupon coupon = new Coupon();
            coupon.setId(cur.getString(0));
            coupon.setType(cur.getString(1));
            coupon.setDescription(cur.getString(2));
            coupon.setNum(Integer.parseInt(cur.getString(3)));
            coupon.setServerPictureURL(cur.getString(4));
            System.out.println("\ngetCouponByShopId:"+coupon.getId()+"type:"+coupon.getType()+"serverrul:"+coupon.getServerPictureURL());
            coupons.add(coupon);
        }
        sqldb.close();
        cur.close();
        return coupons;
    }

    public int isCouponExist(Context con, String couponId){
        int couponNum = -1;
        Log.w(TAG, "getcouopnbyId"+couponId);
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);

        String[] colums = {Parameter.K_COUPON_ID, Parameter.K_COUPON_TYPE, Parameter.K_DESCRIPTION,
                Parameter.K_RULE_NUMBER, Parameter.K_SERVER_PICTURE_URL};
        String whereClause = Parameter.K_COUPON_ID+"=? ";
        //String whereClause = Parameter.K_SHOP_ID+"=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = {couponId};
        Cursor cur = sqldb.query(couponTableName,colums,whereClause,whereArgs,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        if(cur.moveToNext()){
            couponNum = Integer.parseInt(cur.getString(3));
            Log.w(TAG, "coupon num"+couponNum);
        }
        sqldb.close();
        cur.close();
        return  couponNum;

    }

    public ArrayList<Coupon> getCouponByShopId(Context con, String shopId){
        Log.w(TAG, "getcouopnbyshopId"+shopId);
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
        String[] colums = {Parameter.K_COUPON_ID, Parameter.K_COUPON_TYPE, Parameter.K_DESCRIPTION,
                Parameter.K_RULE_NUMBER, Parameter.K_SERVER_PICTURE_URL};
        String whereClause = Parameter.K_SHOP_ID+"=? ";
        //String whereClause = Parameter.K_SHOP_ID+"=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = {shopId};
        Cursor cur = sqldb.query(couponTableName,colums,whereClause,whereArgs,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        while(cur.moveToNext()){
            Log.w(TAG, "getcouponsbyshopid"+shopId);
            Coupon coupon = new Coupon();
            coupon.setId(cur.getString(0));
            coupon.setType(cur.getString(1));
            coupon.setDescription(cur.getString(2));
            coupon.setNum(Integer.parseInt(cur.getString(3)));
            coupon.setServerPictureURL(cur.getString(4));
            System.out.println("\ngetCouponByShopId:"+coupon.getId()+"type:"+coupon.getType()+"serverrul:"+coupon.getServerPictureURL());
            coupons.add(coupon);
        }
        sqldb.close();
        cur.close();
        return coupons;
    }

    public void deleteCoupon(Context con){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        int result = sqldb.delete(couponTableName, null, null);
        sqldb.close();
    }
    public void insertReceipt(Context con, String receiptId, String shopId, String receiptDateTime, String barcode_uri, String isBarcodeCache,
                              String barcode_cache_uri, String isUploaded, String receiptDescription,
                              String paymentDescription){

        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ContentValues cv = new ContentValues();
        cv.put(Parameter.K_RECEIPT_ID, receiptId);
        cv.put(Parameter.K_SHOP_ID, shopId);
        cv.put(Parameter.K_RECEIPT_DATETIME, receiptDateTime);
        cv.put(Parameter.K_RECEIPT_BARCODE_URI, barcode_uri);
        cv.put(Parameter.K_IS_BARCODE_CACHE, isBarcodeCache);
        cv.put(Parameter.K_BARCODE_CACHE_URI, barcode_cache_uri);
        cv.put(Parameter.K_IS_UPLOAD, isUploaded);
        cv.put(Parameter.K_RECEIPT_DESCRIPTION, receiptDescription);
        cv.put(Parameter.K_PAYMENT_DESCRIPTION, paymentDescription);
        sqldb.insert(receiptTableName, null, cv);
        sqldb.close();
    }

    public ArrayList<Receipt> getReceiptByUsername(Context con, String username){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<Receipt> videos = new ArrayList<Receipt>();
        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
        String[] colums = {Parameter.K_ID, Parameter.K_IMAGE_URI, Parameter.K_VIDEO_URI};
        String whereClause = Parameter.K_USERNAMEM+"=? AND " + Parameter.K_IS_UPLOAD + "=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = {username, Parameter.V_UPLOAD};
        Cursor cur = sqldb.query(receiptTableName,colums,whereClause,whereArgs,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
//        while(cur.moveToNext()){
//            Video video = new Video();
//            video.setId(cur.getString(0));
//            video.setImageUri(cur.getString(1));
//            video.setVideoUri(cur.getString(2));
//            System.out.println("\nGetUploadVideoByUsernameid:"+video.getId()+"image_uri:"+video.getImageUri()+"video_uri:"+video.getVideoUri());
//            videos.add(video);
//        }
//        sqldb.close();
//        cur.close();
        return videos;
    }

    public void test(SQLiteDatabase sqLiteDatabase){
        //sqLiteDatabase.execSQL("DROP TABLE "+ruleTableName);
        sqLiteDatabase.execSQL("DROP TABLE "+couponTableName);
        sqLiteDatabase.execSQL("DROP TABLE "+receiptTableName);
        sqLiteDatabase.execSQL("DROP TABLE "+useCouponRecordTableName);
        //sqLiteDatabase.execSQL("DROP TABLE "+productTableName);
    }

    public Shop getShopByShopId(Context con, String shopId){
        Shop shop = new Shop();
        shop.setId("SHOP0001");
        shop.setAddress("Shop CEN E3, Central MTR Station");
        shop.setPhoneNumber("3622 0869");
        shop.setName("FANCL");
        return  shop;
    }

    public ArrayList<Shop> getShopsByUserAccount(Context con, String userAccount ){
        ArrayList<Shop> shops = new ArrayList<Shop>();
        Shop shop = new Shop();
        shop.setId("SHOP0001");
        shop.setAddress("Shop CEN E3, Central MTR Station");
        shop.setPhoneNumber("3622 0869");
        shop.setName("FANCL");
        shops.add(shop);
        return  shops;
    }

    public ArrayList<JSONObject> getReceiptByShopId(Context con, String shopId){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<JSONObject> receiptList = new ArrayList<JSONObject>();
        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
        String[] colums = {Parameter.K_CONTENT};
        String whereClause = Parameter.K_USERNAMEM+"=? AND " + Parameter.K_SHOP_ID + "=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = {AccountStorage.GetAccount(con), shopId};
        Log.w(TAG, "getReceiptByShopId"+AccountStorage.GetAccount(con) + "shop" +shopId);
        Cursor cur = sqldb.query(receiptTableName,colums,whereClause,whereArgs,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        while(cur.moveToNext()){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(cur.getString(0));
                Log.w(TAG, "getReceiptByShopId content" +cur.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            receiptList.add(jsonObject);
        }
        sqldb.close();
        cur.close();
        return receiptList;
    }

//    public ArrayList<Video> getUnUploadVideosByUsername(Context con, String username){
//        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
//        ArrayList<Video> videos = new ArrayList<Video>();
//        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
//        String[] colums = {Parameter.K_ID, Parameter.K_IMAGE_URI, Parameter.K_VIDEO_URI};
//        String whereClause = Parameter.K_USERNAMEM+"=? AND " + Parameter.K_IS_UPLOAD + "=?";
//        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
//        String[] whereArgs = {username, Parameter.V_NOT_UPLOAD};
//        Cursor cur = sqldb.query(receiptTableName,colums,whereClause,whereArgs,null,null,null);
//        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
//        while(cur.moveToNext()){
//            Video video = new Video();
//            video.setId(cur.getString(0));
//            video.setImageUri(cur.getString(1));
//            video.setVideoUri(cur.getString(2));
//            System.out.println("\nGetUnUploadVideoByUsernameid:"+video.getId()+"image_uri:"+video.getImageUri()+"video_uri:"+video.getVideoUri());
//            videos.add(video);
//        }
//        sqldb.close();
//        cur.close();
//        return videos;
//    }
//
//    public ArrayList<Video> getAllUploadVideos(Context con){
//        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
//        ArrayList<Video> videos = new ArrayList<Video>();
//        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
//        String[] colums = {Parameter.K_ID, Parameter.K_USERNAMEM, Parameter.K_IMAGE_URI, Parameter.K_VIDEO_URI};
//        String whereClause = Parameter.K_IS_UPLOAD + "=?";
//        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
//        String[] whereArgs = { Parameter.V_UPLOAD};
//        Cursor cur = sqldb.query(receiptTableName,colums,whereClause,whereArgs,null,null,null);
//        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
//        while(cur.moveToNext()){
//            Video video = new Video();
//            video.setId(cur.getString(0));
//            video.setUsername(cur.getString(1));
//            video.setImageUri(cur.getString(2));
//            video.setVideoUri(cur.getString(3));
//            System.out.println("\nGetUploadVideoByUsernameid:"+video.getId()+"username:"+video.getUsername()+"image_uri:"+video.getImageUri()+"video_uri:"+video.getVideoUri());
//            videos.add(video);
//        }
//        sqldb.close();
//        cur.close();
//        return videos;
//    }
//
//    public void deleteVideoById(Context con, String id){
//        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
//        int result = sqldb.delete(receiptTableName, "id=?", new String[]{id});
//        sqldb.close();
//    }
//    public void updateVideoToUploadById(Context con, String id){
//        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
//        ContentValues cv =new ContentValues();
//        cv.put(Parameter.K_IS_UPLOAD, Parameter.V_UPLOAD);
//        int result = sqldb.update(receiptTableName, cv, "id=?", new String[]{id});
//        sqldb.close();
//    }

}
