package net.lzzy.cinemanager.frament;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.security.PublicKey;
import java.util.List;

import static net.lzzy.cinemanager.frament.OrderFragment.MNT_DISTANCE;

/**
 * @author lzzy_gxy
 * @date 2019/3/26
 * Description:
 */
public class CinemasFragment extends BaseFragment {
    public static final int MNT_DISTANCE = 100;
    private List<Cinema> cinemas;
    private CinemaFactory factory;
    private GenericAdapter<Cinema> adapter;
    private Cinema cinema;
    float touchX1;
    float touchX2;
    boolean isDelete = false;

    public CinemasFragment() {
    }

    public CinemasFragment(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    protected void populate() {

        ListView lv = findViewById(R.id.activity_cinema_content_lv);
        View empty = findViewById(R.id.item_zero);
        lv.setEmptyView(empty);
        factory = CinemaFactory.getInstance();
        cinemas = factory.get();
        adapter = new GenericAdapter<Cinema>(getActivity(), R.layout.cinema_item, cinemas) {
            @Override
            public void populate(ViewHolder holder, Cinema cinema) {
                holder.setTextView(R.id.activity_cinema_item_name, cinema.getName())
                        .setTextView(R.id.activity_cinema_item_area, cinema.getLocation());
                Button btn = holder.getView(R.id.activity_cinema_btn);
                btn.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                        .setTitle("删除确认")
                        .setMessage("要删除订单吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", (dialog, which) ->
                                adapter.remove(cinema)).show());
                holder.getConvertView().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        slideToDelete(event, cinema, btn);
                        return true;
                    }

                    private void slideToDelete(MotionEvent event, Cinema cinemas, Button btn) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                touchX1 = event.getX();
                                break;
                            case MotionEvent.ACTION_UP:
                                touchX2 = event.getX();
                                if (touchX1 - touchX2 > MNT_DISTANCE) {
                                    if (!isDelete) {
                                        btn.setVisibility(View.VISIBLE);
                                        isDelete = true;
                                    }
                                } else {
                                    if (btn.isShown()) {
                                        btn.setVisibility(View.GONE);
                                        isDelete = false;
                                    } else {
                                        clickOrder(cinemas);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }


                });
            }

            @Override
            public boolean persistInsert(Cinema cinema) {
                return factory.addCinema(cinema);
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return factory.deleteCinema(cinema);
            }
        };
        lv.setAdapter(adapter);
        if (cinema!=null){
            save(cinema);
        }

    }
    private void clickOrder(Cinema cinema) {

    }
    @Override
    public int getLayout() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void search(String kw) {
        cinemas.clear();
        if (TextUtils.isEmpty(kw)){
            cinemas.addAll(factory.get());
        }else {
            cinemas.addAll(factory.searchCinemas(kw));
        }
        adapter.notifyDataSetChanged();
    }

    public void save(Cinema cinema) {
        adapter.add(cinema);
    }
}
