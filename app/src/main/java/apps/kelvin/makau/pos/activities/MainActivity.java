package apps.kelvin.makau.pos.activities;


import android.animation.Animator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import apps.kelvin.makau.pos.R;
import apps.kelvin.makau.pos.adapters.NavMenuAdapter;
import apps.kelvin.makau.pos.fragments.FoodCartFragment;
import apps.kelvin.makau.pos.fragments.FoodListFragment;
import apps.kelvin.makau.pos.interfaces.FoodListListerner;
import apps.kelvin.makau.pos.models.Food;
import apps.kelvin.makau.pos.models.MyMenus;
import apps.kelvin.makau.pos.util.CircleAnimationUtil;
import apps.kelvin.makau.pos.util.MyCircleImageView;


public class MainActivity extends AppCompatActivity implements FoodListListerner {

    ArrayList<Food> cart;
    ImageView dest;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawer);

        FoodListFragment foodlistf = new FoodListFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();


        transaction.add(R.id.parent_left, foodlistf);
     /*   if(findViewById(R.id.parent_right)!=null){
            FoodCartFragment foodCartFragment = new FoodCartFragment();
            transaction.add(R.id.parent_right,foodCartFragment);
        }*/
        transaction.commit();



        dest=findViewById(R.id.cartRL);
        dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cart.size() > 0) {
                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    int containerViewId = R.id.parent_left;

                    if(findViewById(R.id.parent_right)!=null)
                        containerViewId = R.id.parent_right;


                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("carts",cart);


                    FoodCartFragment foodCartFragment = new FoodCartFragment();
                    foodCartFragment.setArguments(bundle);
                    fragmentTransaction.replace(containerViewId,foodCartFragment);

                    if(findViewById(R.id.parent_right)==null)
                        fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                  /*  for (Food f : cart) {

                        Toast.makeText(MainActivity.this, String.valueOf(f.getPrice()), Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        });

        ActionBar actionBar= getSupportActionBar();


        cart= new ArrayList<>();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        }



        if(drawerToggle==null){
            drawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
            /*drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });*/
        }
        drawerToggle.syncState();
        setupNavMenu();


        if(savedInstanceState!=null){
            return;

        }








    }

    private void setupNavMenu() {
        ListView menu = (ListView) findViewById(R.id.nav_menu);
        ArrayList<MyMenus> menus = setMenus();
        menu.setAdapter(new NavMenuAdapter(menus,MainActivity.this));

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                drawerLayout.closeDrawer(Gravity.START);

            }
        });

    }

    private ArrayList<MyMenus> setMenus() {
        ArrayList<MyMenus> menus = new ArrayList<>();
        MyMenus menus1 = new MyMenus(R.drawable.ic_sales,"Sales");
        menus.add(menus1);
        MyMenus menus2 = new MyMenus(R.drawable.ic_receipts,"Receipts");
        menus.add(menus2);

        MyMenus menus3 = new MyMenus(R.drawable.ic_items,"Items");
        menus.add(menus3);

        MyMenus menus4 = new MyMenus(R.drawable.ic_reports,"Reports");
        menus.add(menus4);

        return menus;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.cart:

                break;
            default:
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onListClick(Food f, View v) {
         makeAnimation(f,v);


    }

    private void makeAnimation(final Food f, View v) {


        new CircleAnimationUtil().attachActivity(this).setTargetView(v).setMoveDuration(1000).setDestView(dest).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                cart.add(f);

                TextView cart_count= findViewById(R.id.cart_count);
                cart_count.setText(String.valueOf(cart.size()));
                Toast.makeText(MainActivity.this,"Added to cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).startAnimation();


    }
}
