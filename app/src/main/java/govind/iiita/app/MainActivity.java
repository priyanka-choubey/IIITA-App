package govind.iiita.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import govind.iiita.app.Labels.Album;
import govind.iiita.app.Labels.Gymkhana;
import govind.iiita.app.Labels.Library;
import govind.iiita.app.Labels.Geekhaven;

import govind.iiita.app.Labels.Registration_IIITA;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView textView;
    Toolbar toolbar;
    WebView webview;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=findViewById(R.id.postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* Custom Font
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "fonts/slabo.ttf");
        textView = (TextView) findViewById(R.id.postTitle);
        textView.setTypeface(myTypeFace);*/

        setUpToolbar();


        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this,"Clicked Home",Toast.LENGTH_SHORT).show();
                    case R.id.nav_TimeTable:
                        startActivity(new Intent(MainActivity.this, Gymkhana.class));
                        break;
                    case R.id.nav_library:
                        startActivity(new Intent(MainActivity.this, Library.class));
                        break;
                    case R.id.nav_cultural_societies:
                        Toast.makeText(MainActivity.this,"No article on flutter ",Toast.LENGTH_SHORT).show();
                    case R.id.nav_geekhaven:
                        startActivity(new Intent(MainActivity.this, Geekhaven.class));
                        break;
                    case R.id.nav_gymkhana:
                        startActivity(new Intent(MainActivity.this, Gymkhana.class));
                        break;
                    case R.id.nav_logOut:
                        startActivity(new Intent(MainActivity.this, LogOut.class));
                        break;
                    case R.id.Submit_article:
                        sendArticle();
                        break;
                    case R.id.registration_iiita:
                        startActivity(new Intent(MainActivity.this, Registration_IIITA.class)) ;
                    case R.id.nav_albums:
                        startActivity(new Intent(MainActivity.this, Album.class));
                        break;
                    case R.id.nav_aboutus: {
                        startActivity(new Intent(MainActivity.this, AboutPage.class));
                        break;
                    }


                }
                return false;
            }
        });


        getData();

    }

    private void sendArticle() {

        String mailto = "mailto:developer8work@gmail.com?subject=Article Submission";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }

    private void setUpToolbar()
    {
        drawerLayout =  findViewById(R.id.drawerLayout);
        toolbar =  findViewById(R.id.toolbar);

        //To give backward Compatibility
        setSupportActionBar(toolbar);

        //To sync Drawer and Toolbar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void getData()
    {
        Call<PostList> postList= BloggerAPI.getService().getPostList();
        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list=response.body();
                recyclerView.setAdapter(new PostAdapter(MainActivity.this,list.getItems()));
                //Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(MainActivity.this,"No Internet Connection Found",Toast.LENGTH_SHORT).show();

            }
        });
    }


}
