package chenyuting.com.nfccustomer.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by chenyuting on 12/3/16.
 */

public class SharedPreferenceTool {
    private static String name = "NFCCustomer";
    private static String TAG = "SharedPreferenceTool";

    /**
     * Add data to SharedPreferences
     * @param key
     * @param value
     * @param con the context
     */
    public static void write(String key, String value, Context con)
    {
        SharedPreferences sharedPreferences = con.getSharedPreferences(name, Context.MODE_PRIVATE);
        Log.w(TAG, "write " +value);
        Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    /**
     * Read data from SharedPreferences
     * @param key
     * @param con
     * @return
     */
    public static String read(String key, Context con)
    {
        Log.w(TAG, "read");
        SharedPreferences sharedPreferences = con.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

}
