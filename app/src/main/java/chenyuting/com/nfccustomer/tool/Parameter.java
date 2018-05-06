package chenyuting.com.nfccustomer.tool;

/**
 * Created by chenyuting on 12/2/16.
 */

public class Parameter {

    //Reader_ReceiptTitle
    public static final String NULL_RECEIPT="";
    public static final String K_TOTAL_COST = "totalCost";
    public static final String K_RECEIPT_DATA = "receiptData";
    public static final String K_SHOPE_NAME = "shopName";
    public static final String K_SHOPE_ADDRESS = "shopAddress";
    public static final String K_SHOPE_PHONE_NUMBER = "shopPhoneNumber";
    public static final String K_RECEIPT_ID = "receiptId";
    public static final String K_SHOP_ID = "shopId";
    public static final String K_CONTENT = "content";
    public static final String K_RECEIPT_DATE = "receiptDate";
    public static final String K_RECEIPT_TIME = "receiptTime";
    public static final String K_RECEIPT_DATETIME = "receiptDateTime";
    public static final String K_RECEIPT_BARCODE_URI = "barcode_uri";
    public static final String K_IS_BARCODE_CACHE = "isBarcodeCache";
    public static final String V_IS_BARCODE_CACHE_YES = "1";
    public static final String V_IS_BARCODE_CACHE_NO = "0";
    public static final String K_BARCODE_CACHE_URI = "barcode_cache_uri";
    public static final String K_RECEIPT_QRCODE = "receiptQRcode";
    public static final String K_RECEIPT_DESCRIPTION = "receiptDescription";
    public static final String K_PAYMENT_DESCRIPTION = "paymentDescription";

    public static final String K_USER_ACCOUNT = "userAccount";
    public static final String K_USE_ADVERTISEMENT_DATA = "useAdvertisementData";

    public static final String K_DATE_TIME = "dateTime";

    //ADVERTISEMENT
    public static final String K_ADVERTISEMENT_ID = "advertisementId";
    //public static final String K_SERVER_PICTURE_URL = "serverPictureURL";


    //Reader_SalesMemo
    public static final String K_SALES_ITEM = "ITEM";
    public static final String K_SALES_QUANTITY = "QTY";
    public static final String K_SALES_AMOUNT = "AMOUNT";
    public static final String K_SALES_TOTAL_NUMBER = "TOTAL NUMBER";
    public static final String K_SALES_TOTAL_PAY = "NET PAY AMOUNT";
    public static final String K_SALSE_PAID = "PAID";
    public static final String K_SALSE_CHANGE = "CHANGE";
    public static final String K_ITEM_NAME = "Name";
    public static final String K_ITEM_ID = "ItemID";
    public static final String K_ITEM_QUANTITY = "Quantity";
    public static final String K_ITEM_ORIGINAL_PRICE = "OrginalPrice";
    public static final String K_ITEM_DISCOUNT = "Discount";
    public static final String K_ITEM_PRICE = "Price";

    //Reader_Coupon
    public static final String K_IS_COUPON_IMAGE = "isCouponImage";
    public static final String V_IS_COUPON_IMAGE_YES = "y";
    public static final String V_IS_COUPON_IMAGE_NO = "n";
    //Coupon
    //tree coupon types
    public static final int COUPON_STAMP = 0;
    public static final int COUPON_DISCOUNT = 1;
    public static final int COUPON_CASH = 2;
    public static final String K_COUPON = "coupon";
    public static final String K_COUPON_ID = "couponId";
    public static final String K_COUPON_TYPE= "couponType";
    public static final String K_DESCRIPTION = "description";//数据库中使用
    public static final String K_LOCAL_PICTURE_URL = "localPictureURL";
    public static final String K_SERVER_PICTURE_URL = "serverPictureURL";
    public static final String V_COUPON_TYPE_STAMP = "Stamp";
    public static final String V_COUPON_TYPE_DISCOUNT = "Discount";
    public static final String V_COUPON_TYPE_CASH = "Cash";
    public static final String K_RULE_FOR_TYPE ="ruleForType";
    public static final String V_RULE_FOR_TYPE_ADVERTISEMENT ="Advertisement";
    public static final String V_RULE_FOR_TYPE_COUPON ="Coupon";
    public static final String K_RULE_TYPE = "ruleType";
    public static final String V_RULE_TYPE_GIVE ="Give";
    public static final String V_RULE_TYPE_USE ="Use";
    public static final String K_RULE_TOTAL_COST = "ruleTotalCost";
    public static final String V_RULE_TOTAL_COST_PER ="Per";
    public static final String V_RULE_TOTAL_COST_OVER ="Over";
    public static final String V_RULE_NULL_DATA ="null";
    public static final String COUPON_DATA_SEPERATOR = ";";
    public static final String COUPON_STAMP_ID_PREFIX = "STAMP";
    public static final String COUPON_STAMP_ID = "stampId";
    public static final String COUPON_DISCOUNT_ID_PREFIX = "DISCOUNT";
    public static final String COUPON_DISCOUNT_ID = "discountId";
    public static final String COUPON_CASH_ID_PREFIX = "CASH";
    public static final String COUPON_CASH_ID = "cashId";
    public static final String COUPON_RULE_ID_PREFIX = "RULE";
    public static final String COUPON_RULE_ID = "ruleId";
    public static final String K_RULE_VALID_DATE_FROM = "validDateFrom";
    public static final String K_RULE_VALID_DATE_TO = "validDateFrom";
    public static final String K_RULE_NUMBER = "number";
    public static final String K_RULE_EXCHANGE_PRODUCT_ID = "exchangeProudctID";


    public static final String GET_VIDEO_URL = "http://i.cs.hku.hk/~ytchen/videophp/getVideo.php";
    public static final String UPLOAD_VIDEO_URL = "http://i.cs.hku.hk/~ytchen/videophp/upload.php";
    public static final String UPLOAD_IMAGE_URL = "http://i.cs.hku.hk/~ytchen/videophp/uploadImage.php";

    public static final String K_FUNCTION = "function";
    public static final String V_FUNCTION_GET_ALL_VIDEO = "0";
    public static final String V_FUNCTION_GET_VIDEO_BY_ID = "1";
    public static final String V_FUNCTION_DELETE_VIDEO_BY_ID = "2";
    public static final String V_FUNCTION_INSERT_VIDEO = "3";
    public static final String K_USERNAMEM = "username";
    public static final String K_ID = "id";
    public static final String K_IMAGE_URI = "image_uri";
    public static final String K_VIDEO_URI = "video_uri";
    public static final String K_IS_UPLOAD = "isUpload";
    public static final String V_UPLOAD = "1";
    public static final String V_NOT_UPLOAD = "0";
    public static final String K_IS_AUTO_UPLOAD = "isAutoUpload";
    public static final String V_AUTO_UPLOAD = "1";
    public static final String V_NOT_AUTO_UPLOAD = "0";
    public static final String UPLOAD_SUCCESS = "successful";
    public static final String K_IMAGE_FILENAME = "imageFilename";
    public static final String K_VIDEO_FILENAME = "videoFilename";
    public static final String K_ADVERTISEMENT_FUNCTION = "function";
    public static final String V_COUPON_FUNCTION_INSERT_ADVERTISEMENT = "3";
    public static final String V_COUPON_FUNCTION_ASK_FOR_ADVERTISEMENT = "4";
    public static final String V_COUPON_FUNCTION_GET_ADVERTISEMENT_DATA = "5";

    public static final String RECEIPT_URL = "http://i.cs.hku.hk/~ytchen/videophp/receipt.php";
    public static final String K_RECEIPT_FUNCTION = "function";
    public static final String V_RECEIPT_FUNCTION_MERCHANT_INSERT_RECEIPT = "3";
    public static final String V_COUPON_FUNCTION_CUSTOMER_INSERT_RECEIPT = "4";
    //setting
    public static final String K_SETTING_UPLOAD_DATA = "uploadData";
    public static final String K_SETTING_RECEIVE_ADVERTISEMENT = "receiveAdvertisement";
    public static final String K_SETTING_FILTER_ADVERTISEMENT = "filterAdvertisement";
    public static final String V_TOGGLE_BUTTON_ON = "on";
    public static final String V_TOGGLE_BUTTON_OFF = "off";









    public static final int SET_ICON = 1;
    public static final int SET_NAME = 2;
    public static final int SET_GENDER = 3;
    public static final int SET_SLOGN = 4;
    public static final String SET = "SET";
    public static final String PRE = "PRE";


    public static final String USERNAME = "NAME";
    public static final String USERGENDER = "GENDER";
    public static final String USERSLOGN = "SLOGN";
    public static final String USERSICON = "ICON";
    public static final String USERSID = "userid";

    public static final int SUCCESSFUL = 1;
    public static final int FAIL = 0;

    public static final String V_SERVER_IMAGES_PATH = "http://i.cs.hku.hk/~ytchen/videophp/images/";
    public static final String ADVERTISEMENT_URL = "http://i.cs.hku.hk/~ytchen/videophp/advertisement.php";






}
