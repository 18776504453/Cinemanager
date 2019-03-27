package net.lzzy.cinemanager.frament;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.simpledatepicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author lzzy_gxy
 * @date 2019/3/27
 * Description:
 */
public class AddOrderFragment extends BaseFragment {

    private EditText edtMovie;
    private EditText edtPrice;
    private TextView tvTime;
    private Spinner spCinema;
    private ImageView imgQRcode;
    private Button btn;
    private List<Cinema>cinemas;
    public CustomDatePicker datePicker;

    @Override
    protected void populate() {
        initView();
    }

    private void initView() {
        edtMovie = findViewById(R.id.order_dialog_edt_movie);
        edtPrice = findViewById(R.id.order_dialog_edt_price);
        findViewById(R.id.order_dialog_layoutTime).setOnClickListener(v -> {
            datePicker.show(tvTime.getText().toString());
        });
        tvTime = findViewById(R.id.order_dialog_movieTime);
        spCinema = findViewById(R.id.order_dialog_btn_spinnerArea);
        imgQRcode = findViewById(R.id.order_dialog_Iv);
        btn = findViewById(R.id.order_dialog_btn_ok);
        initDatePicker();
        Dialog();

    }
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvTime.setText(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        String end = sdf.format(calendar.getTime());
        datePicker = new CustomDatePicker(getActivity(), s -> tvTime.setText(s), now, end);
        datePicker.showSpecificTime(true);
        datePicker.setIsLoop(true);
    }
    private void Dialog() {
        cinemas = CinemaFactory.getInstance().get();
        spCinema.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, cinemas));

        findViewById(R.id.order_dialog_btn_3).setOnClickListener(v -> {

        });
        findViewById(R.id.order_dialog_btn_ok).setOnClickListener(v -> {
            Order order = new Order();
            String movie = edtMovie.getText().toString();
            String time = tvTime.getText().toString();
            if (TextUtils.isEmpty(movie) || TextUtils.isEmpty(time)) {
                Toast.makeText(getActivity(), "信息不全", Toast.LENGTH_SHORT).show();
                return;
            }
            Float price;
            try {
                price = Float.parseFloat(edtPrice.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "数字格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
            Cinema cinema = cinemas.get(spCinema.getSelectedItemPosition());


            try {

            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "请添加影院信息", Toast.LENGTH_SHORT).show();
                return;
            }

            order.setMovie(movie);
            order.setMovieTime(time);
            order.setPrice(price);

            order.setCinemaId(cinema.getId());
            //adapter.add(order);
            edtMovie.setText("");
            edtPrice.setText("");
        });
        findViewById(R.id.order_dialog_btn_er).setOnClickListener(v -> {
            String name = edtMovie.getText().toString();
            String price = edtPrice.getText().toString();
            String location = spCinema.getSelectedItem().toString();
            String time = tvTime.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
                Toast.makeText(getActivity(), "信息不全", Toast.LENGTH_SHORT).show();
                return;
            }
            String content = "[" + name + "]" + time + "\n" + location + "票价" + price + "元";
            imgQRcode.setImageBitmap(AppUtils.createQRCodeBitmap(content, 200, 200));
        });
        imgQRcode.setOnLongClickListener(v -> {
            Bitmap bitmap = ((BitmapDrawable) imgQRcode.getDrawable()).getBitmap();
            Toast.makeText(getActivity(), AppUtils.readQRCode(bitmap), Toast.LENGTH_SHORT).show();
            return true;
        });


    }
    @Override
    public int getLayout() {
        return R.layout.add_fragment_orders;
    }
}
