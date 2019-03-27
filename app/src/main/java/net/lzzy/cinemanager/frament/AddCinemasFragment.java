package net.lzzy.cinemanager.frament;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.sqllib.GenericAdapter;

/**
 * @author lzzy_gxy
 * @date 2019/3/27
 * Description:
 */
public class AddCinemasFragment extends BaseFragment {

    private TextView tvArea;
    private EditText editText;
    private GenericAdapter<Cinema> adapter;
    private String city = "柳州市";
    private String province = "广西壮族自治区";
    private String area = "鱼峰区";

    @Override
    protected void populate() {
        initView();
        showDialog();
    }

    private void initView() {
        tvArea = findViewById(R.id.activity_dialog_location);
        editText = findViewById(R.id.activity_dialog_edt_name);
    }
    public void showDialog() {

        findViewById(R.id.activity_cinema_content_layoutArea).setOnClickListener(v -> {
            JDCityPicker cityPicker = new JDCityPicker();
            cityPicker.init(getActivity());
            cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                @Override
                public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                    AddCinemasFragment.this.province = province.getName();
                    AddCinemasFragment.this.city = city.getName();
                    AddCinemasFragment.this.area = district.getName();
                    String loc = province.getName() + city.getName() + district.getName();
                    tvArea.setText(loc);
                }
                @Override
                public void onCancel() {
                }
            });
            cityPicker.showCityPicker();
        });
        findViewById(R.id.activity_dialog_save).setOnClickListener(v -> {
            Cinema cinema = new Cinema();
            String name = editText.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "请输入影院名称", Toast.LENGTH_SHORT).show();
                return;
            }
            cinema.setCity(city);
            cinema.setName(name);
            cinema.setArea(area);
            cinema.setProvince(province);
            cinema.setLocation(tvArea.getText().toString());

        });
        findViewById(R.id.activity_dialog_cancel).setOnClickListener(v -> {

        });
    }

    @Override
    public int getLayout() {
        return R.layout.add_fragment_cinemas;
    }
}
