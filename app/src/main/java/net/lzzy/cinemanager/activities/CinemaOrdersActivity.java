package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.frament.CinemaOrderFragment;

import static net.lzzy.cinemanager.activities.MainActivity.CINEMA_ID;

/**
 * @author Administrator
 */
public class CinemaOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);
        String cinemaId = getIntent().getStringExtra(CINEMA_ID);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_order_container);
        if (fragment == null) {
            fragment = CinemaOrderFragment.newinstance(cinemaId);
            manager.beginTransaction().add(R.id.fragment_order_cinema_container, fragment).commit();
        }
    }
}
