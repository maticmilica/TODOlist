package mmilica.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public static ListAdapter adapter;
    public static ListAdapter adapterTmp;
    public static ListAdapter adapterDone;
    public static NavigationView navigationView;
    public static ListView list;
    public static boolean isClicked = false;
    public static String taskName;
    public static String taskDesc;
    public static int position;
    public static DataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DataBase(this, DataBase.TABLE_NAME, null, 1);
        adapter = new ListAdapter(this);
        adapterTmp = new ListAdapter(this);
        adapterDone = new ListAdapter(this);

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        adapter.setList(db.read());

        registerForContextMenu(list);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddItem.class));
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.all:
                        fab.setVisibility(View.VISIBLE);
                        list.setAdapter(adapter);
                        isClicked = false;
                        break;
                    case R.id.personal:
                        fab.setVisibility(View.VISIBLE);
                        adapterTmp = adapter.find("personal");
                        list.setAdapter(adapterTmp);
                        adapterTmp.notifyDataSetChanged();
                        isClicked = true;
                        break;
                    case R.id.shopping:
                        fab.setVisibility(View.VISIBLE);
                        adapterTmp = adapter.find("shopping");
                        list.setAdapter(adapterTmp);
                        adapterTmp.notifyDataSetChanged();
                        isClicked = true;
                        break;
                    case R.id.wishlist:
                        fab.setVisibility(View.VISIBLE);
                        adapterTmp = adapter.find("wishlist");
                        list.setAdapter(adapterTmp);
                        adapterTmp.notifyDataSetChanged();
                        isClicked = true;
                        break;
                    case R.id.work:
                        fab.setVisibility(View.VISIBLE);
                        adapterTmp = adapter.find("work");
                        list.setAdapter(adapterTmp);
                        adapterTmp.notifyDataSetChanged();
                        isClicked = true;
                        break;
                    case R.id.finished:
                        fab.setVisibility(View.INVISIBLE);
                        adapterTmp = adapter.find("done");
                        list.setAdapter(adapterTmp);
                        adapterTmp.notifyDataSetChanged();
                        isClicked = true;
                        break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                taskName = adapter.getName(info.position);
                taskDesc = adapter.getDesc(info.position);
                position = info.position;
                startActivity(new Intent(MainActivity.this, EditItem.class));
                return true;
            case R.id.delete:
                if (!isClicked) {
                    adapter.deleteItem(info.position);
                    adapter.notifyDataSetChanged();
                } else
                {
                    adapterTmp.deleteItem(info.position);
                    adapter.deleteItem(info.position);
                    adapterTmp.notifyDataSetChanged();
                }
                return true;
            case R.id.done:
                    taskName = adapter.getName(info.position);
                    taskDesc = adapter.getDesc(info.position);
                    MainActivity.adapter.editChecked(info.position, true);
                    MainActivity.adapter.notifyDataSetChanged();
                if(isClicked)
                    MainActivity.adapterTmp.notifyDataSetChanged();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
