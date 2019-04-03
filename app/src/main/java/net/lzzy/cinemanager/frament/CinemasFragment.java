package net.lzzy.cinemanager.frament;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

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
    public static final int MNT_DISTANCE = 100;
    public static final String CINEMA = "cinema";
    private List<Cinema> cinemas;
    private CinemaFactory factory;
    private GenericAdapter<Cinema> adapter;
    private OnCinemaSelectedListener listener;
    private Cinema cinema;
    float touchX1;
    float touchX2;
    boolean isDelete = false;

  public static CinemasFragment newinstance(Cinema cinema){
      CinemasFragment fragment=new CinemasFragment();
      Bundle args=new Bundle();
      args.putParcelable(CINEMA,cinema);
      //putParcelable 序列化,所有的完成都是在内存进行
      fragment.setArguments(args);
      return fragment;
  }

    @Override
    protected void populate() {

        ListView lv = findViewById(R.id.fragment_cinema_content_lv);
        View empty = findViewById(R.id.item_zero);
        lv.setEmptyView(empty);
        factory = CinemaFactory.getInstance();
        cinemas = factory.get();
        adapter = new GenericAdapter<Cinema>(getActivity(), R.layout.cinema_item, cinemas) {
            @Override
            public void populate(ViewHolder holder, Cinema cinema) {
                holder.setTextView(R.id.activity_cinema_item_name, cinema.getName())
                        .setTextView(R.id.activity_cinema_item_area, cinema.getLocation());
               /* Button btn = holder.getView(R.id.activity_cinema_btn);
                btn.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                        .setTitle("删除确认")
                        .setMessage("要删除订单吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", (dialog, which) ->{
                            adapter.remove(cinema);
                            isDelete=false;
                        }).show());
                int visible=isDelete?View.VISIBLE:View.GONE;
                btn.setVisibility(visible);
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

                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }


                });*/
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
        lv.setOnItemClickListener((parent, view, position, id) -> {
            listener.onCinemaSelected(adapter.getItem(position).getId().toString());
        });

    }
    @Override
    public int getLayout() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCinemaSelectedListener){
            listener= (OnCinemaSelectedListener) context;
        }else {
            throw new ClassCastException(context.toString() + "必需实现OnCinemaSelectedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
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
    public interface OnCinemaSelectedListener {
        void onCinemaSelected(String cinemaId);
    }
}
