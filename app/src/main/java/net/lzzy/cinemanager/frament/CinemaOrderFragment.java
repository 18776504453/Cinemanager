package net.lzzy.cinemanager.frament;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * @author lzzy_gxy
 * @date 2019/4/3
 * Description:
 */
public class CinemaOrderFragment extends BaseFragment {
    public static final String ARG_CINEMA_ID = "argCinemaId";
    private String cinemaId;

    public static CinemaOrderFragment newinstance(String cinemaId) {
        CinemaOrderFragment fragment = new CinemaOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CINEMA_ID, cinemaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cinemaId = getArguments().getString(ARG_CINEMA_ID);
        }
    }

    @Override
    protected void populate() {
        ListView lv = findViewById(R.id.fragment_cinema_content_lv);
        View empty = findViewById(R.id.item_zero);
        lv.setEmptyView(empty);
        List<Order> orders = OrderFactory.getInstance().getOrdersByCinema(cinemaId);
        GenericAdapter<Order> adapter = new GenericAdapter<Order>(getActivity(), R.layout.cinema_item, orders) {
            @Override
            public void populate(ViewHolder holder, Order order) {
                holder.setTextView(R.id.activity_cinema_item_name, order.getMovie())
                        .setTextView(R.id.activity_cinema_item_area, order.getMovieTime());
            }

            @Override
            public boolean persistInsert(Order order) {
                return false;
            }

            @Override
            public boolean persistDelete(Order order) {
                return false;
            }

        };
        lv.setAdapter(adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_cinema_order;
    }

    @Override
    public void search(String kw) {

    }
}
