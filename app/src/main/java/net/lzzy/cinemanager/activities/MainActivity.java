package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.frament.AddCinemasFragment;
import net.lzzy.cinemanager.frament.AddOrderFragment;
import net.lzzy.cinemanager.frament.CinemasFragment;
import net.lzzy.cinemanager.frament.OrderFragment;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager manager = getSupportFragmentManager();
    private LinearLayout layoutMenu;
    private TextView tvTitle;
    private SearchView search;
    private SparseArray<String> titleArray =new SparseArray<>();
    private SparseArray<Fragment> fragmentArray =new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setTitleMenu();
    }

    private void setTitleMenu() {
        titleArray.put(R.id.bar_see_cinema,"影院列表");
        titleArray.put(R.id.bar_add_cinema,"添加影院");
        titleArray.put(R.id.bar_add_order,"添加订单");
        titleArray.put(R.id.bar_order,"订单列表");
        layoutMenu = findViewById(R.id.bar_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_img_menu).setOnClickListener(v -> {
            int visible = layoutMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            layoutMenu.setVisibility(visible);
        });
        tvTitle = findViewById(R.id.bar_title_tv_title);
        tvTitle.setText("我的订单");
        search = findViewById(R.id.bar_searchView);
        findViewById(R.id.bar_order).setOnClickListener(this);
        findViewById(R.id.bar_add_cinema).setOnClickListener(this);
        findViewById(R.id.bar_add_order).setOnClickListener(this);
        findViewById(R.id.bar_see_cinema).setOnClickListener(this);
        findViewById(R.id.bar_exit).setOnClickListener(v -> System.exit(0));
    }

    @Override
    public void onClick(View v) {
        layoutMenu.setVisibility(View.GONE);
        tvTitle.setText(titleArray.get(v.getId()));
        FragmentTransaction transaction=manager.beginTransaction();
        Fragment fragment= fragmentArray.get(v.getId());
        if (fragment==null){
            fragment=createFragment(v.getId());
            fragmentArray.put(v.getId(),fragment);
            transaction.add(R.id.fragment_container,fragment);
        }
        for (Fragment f:manager.getFragments()){
            transaction.hide(f);
        }
        transaction.show(fragment).commit();
        //region
/*        switch (v.getId()) {
            case R.id.bar_order:
                manager.beginTransaction().replace(R.id.fragment_container,
                        new OrderFragment())
                        .commit();
                break;
            case R.id.bar_add_order:
                break;
            case R.id.bar_add_cinema:
                break;
            case R.id.bar_see_cinema:
                manager.beginTransaction().replace(R.id.fragment_container,
                        new CinemasFragment())
                        .commit();
                break;
            default:
                break;
        }*/
       //endregion
    }

    private Fragment createFragment(int id) {
        switch (id) {
            case R.id.bar_order:
                return new OrderFragment();
            case R.id.bar_add_order:
               return  new AddOrderFragment();
            case R.id.bar_add_cinema:
                return new AddCinemasFragment();
            case R.id.bar_see_cinema:
                return new CinemasFragment();
            default:
                break;
        }
        return null;
    }
}
