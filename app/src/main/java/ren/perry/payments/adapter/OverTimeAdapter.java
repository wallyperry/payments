package ren.perry.payments.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import ren.perry.payments.R;
import ren.perry.payments.bean.OverTimeBean;
import ren.perry.payments.utils.MathUtils;

/**
 * @author perry
 * @date 2017/11/28
 * WeChat 917351143
 */

public class OverTimeAdapter extends BaseQuickAdapter<OverTimeBean, BaseViewHolder> {
    public OverTimeAdapter() {
        super(R.layout.item_over_time);
    }

    @Override
    protected void convert(BaseViewHolder helper, OverTimeBean item) {

        int hours = (int) (item.getOverMinutes() / 60);
        int minutes = (int) (item.getOverMinutes() - (hours * 60));

        String dateStr = item.getSelectMonth() + "-" + item.getSelectDay();
        String closedStr = item.getSelectHours() + ":" + item.getSelectMinutes() + "下班";
        String overTimeStr;
        if (hours < 1) {
            overTimeStr = "加班" + minutes + "分钟";
        } else {
            overTimeStr = "加班" + hours + (minutes < 1 ? "小时整" : "小时" + minutes + "分钟");
        }

        String moneyStr = MathUtils.df2Double(item.getCateOverHoursMoney() / 60 * item.getOverMinutes());

        helper.setText(R.id.tvDate, dateStr)
                .setText(R.id.tvClosed, closedStr)
                .setText(R.id.tvOverTime, overTimeStr)
                .setText(R.id.tvMoney, moneyStr)
                .addOnClickListener(R.id.cv);
    }
}
