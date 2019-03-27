package net.lzzy.cinemanager.frament;


import android.view.View;
import android.widget.ListView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 *
 * @author lzzy_gxy
 * @date 2019/3/26
 * Description:
 */
public class OrderFragment extends BaseFragment {
    private List<Order> orders;
    private OrderFactory factory;
    private GenericAdapter<Order> adapter;
    @Override
    protected void populate() {
        ListView lv=findViewById(R.id.activity_cinema_content_lv);
        View empty=findViewById(R.id.item_zero);
        lv.setEmptyView(empty);
        factory=OrderFactory.getInstance();
        orders=factory.get();
       adapter=new GenericAdapter<Order>(getActivity(),R.layout.cinema_item,orders) {
           @Override
           public void populate(ViewHolder holder, Order order) {
               String location = CinemaFactory.getInstance()
                       .getById(order.getCinemaId().toString()).toString();
               holder.setTextView(R.id.activity_cinema_item_name, order.getMovie())
                       .setTextView(R.id.activity_cinema_item_area, location);
           }

           @Override
           public boolean persistInsert(Order order) {
               return factory.addOrder(order);
           }

           @Override
           public boolean persistDelete(Order order) {
               return factory.delete(order);
           }
       };
        lv.setAdapter(adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_orders;
    }
}
