package chenyuting.com.nfccustomer.activity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.widget.Toast;

import chenyuting.com.nfccustomer.base.BaseNfcActivity;
import yalantis.com.sidemenu.sample.R;

import java.nio.charset.Charset;

/**
 * Author:Created by Ricky on 2017/8/25.
 * Email:584182977@qq.com
 * Description:
 */
public class WriteMUActivity extends BaseNfcActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mu);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = tag.getTechList();
        boolean haveMifareUltralight = false;
        for (String tech : techList) {
            if (tech.indexOf("MifareUltralight") >= 0) {
                haveMifareUltralight = true;
                break;
            }
        }
        if (!haveMifareUltralight) {
            Toast.makeText(this, "不支持MifareUltralight数据格式", Toast.LENGTH_SHORT).show();
            return;
        }
        writeTag(tag);
    }

    public void writeTag(Tag tag) {
        MifareUltralight ultralight = MifareUltralight.get(tag);
        try {
            ultralight.connect();
            //写入八个汉字，从第五页开始写，中文需要转换成GB2312格式
            ultralight.writePage(4, "北京".getBytes(Charset.forName("GB2312")));
            ultralight.writePage(5, "上海".getBytes(Charset.forName("GB2312")));
            ultralight.writePage(6, "广州".getBytes(Charset.forName("GB2312")));
            ultralight.writePage(7, "天津".getBytes(Charset.forName("GB2312")));
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        } finally {
            try {
                ultralight.close();
            } catch (Exception e) {
            }
        }
    }
}
