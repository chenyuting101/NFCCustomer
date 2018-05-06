package chenyuting.com.nfccustomer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Rule;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/11/17.
 */

public class RuleListBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<Rule> ruleList;
    public RuleListBaseAdapter(Context con){
        this.con = con;
    }

    public ArrayList<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(ArrayList<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    @Override
    public int getCount() {
        return ruleList.size();
    }

    @Override
    public Object getItem(int position) {
        return ruleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.rule_list_item, null);
        TextView ruleId = (TextView) convertView.findViewById(R.id.rule_list_item_rule_id_tv);
        TextView ruleType = (TextView) convertView.findViewById(R.id.rule_list_item_rule_type_tv);
        Rule rule = (Rule) this.getItem(position);
        //Bitmap bitmap = this.getLoacalBitmap(video.getImageUri());
        ruleId.setText(rule.getRuleID());
        ruleType.setText(rule.getRuleType());
        return convertView;
    }
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
