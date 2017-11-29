package ren.perry.payments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import ren.perry.mvplibrary.base.BaseActivity;
import ren.perry.mvplibrary.base.BasePresenter;
import ren.perry.payments.adapter.OverTimeAdapter;
import ren.perry.payments.bean.OverTimeBean;
import ren.perry.payments.utils.AppUtils;
import ren.perry.payments.utils.DateUtils;
import ren.perry.payments.utils.MathUtils;

public class MainActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @Bind(R.id.et1)
    EditText et1;
    @Bind(R.id.et2)
    EditText et2;
    @Bind(R.id.et3)
    EditText et3;
    @Bind(R.id.et4)
    EditText et4;
    @Bind(R.id.et5)
    EditText et5;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.tvTotal)
    TextView tvTotal;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private OverTimeAdapter rvAdapter;
    private String[] dialogItem = {"删除"};
    private DatePickerDialog dateDialog;
    private double overMoney;
    private String overTime;


    private DateFormat df;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    protected void initView() {
        setTitle("薪酬计算(" + AppUtils.getVersionName() + ")");
        df = new SimpleDateFormat("HH:mm");
        overMoney = 0;
        overTime = "";

        dateDialog = new DatePickerDialog(this);
        dateDialog.getDatePicker().setMaxDate(DateUtils.getLastDay().getTime().getTime());
        dateDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            toastShow("选择下班时间");
            showTimeDialog(i1, i2);
        });

        rvAdapter = new OverTimeAdapter();
        rvAdapter.bindToRecyclerView(recyclerView);
        rvAdapter.setOnItemChildClickListener(this);
        rvAdapter.setEnableLoadMore(false);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(rvAdapter);

        et1.setText("3900");
        et2.setText("21.75");
        et5.setText("1.5");
    }

    @SuppressWarnings("unused")
    @OnTextChanged({R.id.et1, R.id.et5})
    public void onEtChanged(CharSequence s) {
        rvAdapter.setNewData(null);
        updateTotal();
    }

    private void showTimeDialog(int i1, int i2) {
        TimePickerDialog timeDialog = new TimePickerDialog(this, (timePicker, i3, i11) -> {
            try {
                addOverTime(i1 + 1, i2, i3, i11);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }, 19, 30, true);
        timeDialog.show();
    }

    @SuppressLint("SimpleDateFormat")
    private void addOverTime(int month, int day, int hour, int minute) throws ParseException {
        Date d1 = df.parse(hour + ":" + minute);
        Date d2 = df.parse("19:30");

        long min = (d1.getTime() - d2.getTime()) / (1000 * 60);
        Log.e("min", min + "");
        if (min < 0) {
            toastShow("不算加班");
            return;
        }
        //19:30后算加班，加班时间由下班时间17:30开始计算
        min += 120;
        double monthMoney = Double.parseDouble(et1.getText().toString().trim());
        double rate = Double.parseDouble(et5.getText().toString().trim());
        double rateHoursMoney = monthMoney / 174 * rate;

        OverTimeBean bean = new OverTimeBean();
        bean.setOverMinutes(min);
        bean.setCateOverHoursMoney(rateHoursMoney);
        bean.setSelectDay(day);
        bean.setSelectMonth(month);
        bean.setSelectHours(hour);
        bean.setSelectMinutes(minute);
        rvAdapter.addData(bean);
        updateTotal();
    }

    private void updateTotal() {
        List<OverTimeBean> list = rvAdapter.getData();
        long time = 0;
        double money = 0;
        for (OverTimeBean bean : list) {
            time += bean.getOverMinutes();
            money += (bean.getCateOverHoursMoney() / 60 * bean.getOverMinutes());
        }
        int hours = (int) (time / 60);
        int minutes = (int) (time - (hours * 60));
        String overTimeStr;
        if (hours < 1) {
            if (minutes < 1) {
                overTimeStr = "";
            } else {
                overTimeStr = minutes + "分钟";
            }
        } else {
            if (minutes < 1) {
                overTimeStr = hours + "小时整";
            } else {
                overTimeStr = hours + "小时" + minutes + "分钟";
            }
        }
        String totalStr = "合计：加班" + overTimeStr + "、加班费" + MathUtils.df2Double(money) + "元";
        tvTotal.setText(totalStr);
        overMoney = Double.parseDouble(MathUtils.df2Double(money));
        overTime = overTimeStr;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.cv:
                new MaterialDialog.Builder(this)
                        .items(dialogItem)
                        .itemsCallback((dialog, itemView, position1, text) -> {
                            if (position1 == 0) {
                                rvAdapter.remove(position);
                                updateTotal();
                            }
                        }).show();
                break;
        }
    }

    @OnClick({R.id.tvAddOverTime, R.id.btnCount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAddOverTime:
                if (et1.getText().length() < 1) {
                    toastShow("还没输入基本工资呢");
                } else if (et5.getText().length() < 1) {
                    toastShow("还没输入加班倍率呢");
                } else {
                    dateDialog.show();
                }
                break;
            case R.id.btnCount:
                totalCount();
                break;
        }
    }

    /**
     * 计算
     */
    @SuppressWarnings({"ConstantConditions", "SpellCheckingInspection"})
    @SuppressLint("SetTextI18n")
    private void totalCount() {
        String et1Str = et1.getText().toString().trim();
        String et2Str = et2.getText().toString().trim();
        String et3Str = et3.getText().toString().trim();
        String et4Str = et4.getText().toString().trim();

        if (et1Str.length() < 1) {
            toastShow("还没输入基本工资呢");
        } else if (et2Str.length() < 1) {
            toastShow("还没输入工作天数呢");
        } else {
            //工作天数
            double workCount = Double.parseDouble(et2Str);
            //月基本工资 = 日薪 * 工作天数
            double monthMoney = Double.parseDouble(et1Str) / 21.75 * workCount;
            //事假天数
            double vacation1 = et3Str.length() < 1 ? 0.0 : Double.parseDouble(et3Str);
            //病假天数
            double vacation2 = et4Str.length() < 1 ? 0.0 : Double.parseDouble(et4Str);
            //is试用期
            boolean isProbation = radioGroup.getCheckedRadioButtonId() == R.id.rb1;
            //事假扣除的工资
            double vaca1 = monthMoney / workCount * vacation1;
            //病假扣除的工资
            double vaca2 = monthMoney * 0.4 / workCount * vacation2;
            //实得工资合计 = (月基本 + 加班 - 事假扣除 - 病假扣除) * (试用期 ? 0.8 : 1)
            double moneyAll = (monthMoney + overMoney - vaca1 - vaca2) * (isProbation ? 0.8 : 1);

            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .customView(R.layout.view_total_count, true)
                    .show();
            View view = dialog.getCustomView();
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tv1 = view.findViewById(R.id.tv1);
            TextView tv2_1 = view.findViewById(R.id.tv2_1);
            TextView tv2_2 = view.findViewById(R.id.tv2_2);
            TextView tv3_1 = view.findViewById(R.id.tv3_1);
            TextView tv3_2 = view.findViewById(R.id.tv3_2);
            TextView tv4_1 = view.findViewById(R.id.tv4_1);
            TextView tv4_2 = view.findViewById(R.id.tv4_2);
            TextView tvMoneyAll = view.findViewById(R.id.tvMoneyAll);

            tvTitle.setText("工资明细(" + (isProbation ? "试用期" : "正式期") + ")");
            tv1.setText(MathUtils.df2Double(monthMoney) + " 元");
            tv2_1.setText(overTime);
            tv2_2.setText(MathUtils.df2Double(overMoney) + " 元");
            tv3_1.setText(MathUtils.df2Int(vacation1) + "天");
            tv3_2.setText("- " + MathUtils.df2Double(vaca1) + " 元");//事假
            tv4_1.setText(MathUtils.df2Int(vacation2) + "天");
            tv4_2.setText("- " + MathUtils.df2Double(vaca2) + " 元");//病假
            tvMoneyAll.setText(MathUtils.df2Double(moneyAll) + " 元");

        }
    }
}
