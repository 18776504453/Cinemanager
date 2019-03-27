package net.lzzy.cinemanager.frament;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * @author lzzy_gxy
 * @date 2019/3/26
 * Description:
 */
public class CinemasFragment extends BaseFragment {
    private List<Cinema> cinemas;
    private CinemaFactory factory;
    private GenericAdapter<Cinema> adapter;

    @Override
    protected void populate() {
        ListView lv=findViewById(R.id.activity_cinema_content_lv);
        View empty=findViewById(R.id.item_zero);
        lv.setEmptyView(empty);
        factory=CinemaFactory.getInstance();
        cinemas=factory.get();
        adapter=new GenericAdapter<Cinema>(getActivity(),R.layout.cinema_item,cinemas) {
            @Override
            public void populate(ViewHolder holder, Cinema cinema) {
                holder.setTextView(R.id.activity_cinema_item_name, cinema.getName())
                        .setTextView(R.id.activity_cinema_item_area, cinema.getLocation());
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

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_cinemas;
    }
}
