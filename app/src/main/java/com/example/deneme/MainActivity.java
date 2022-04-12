package com.example.deneme;

import android.content.Intent;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deneme.model.Category;
import com.example.deneme.model.DailyCalendar;
import com.example.deneme.model.User;
import com.example.deneme.ui.calendar.CalendarActivity;
import com.example.deneme.ui.drawingTool.DrawingToolActivity;
import com.example.deneme.ui.exams.ExamsActivity;
import com.example.deneme.ui.home.HomeActivity;
import com.example.deneme.ui.schedule.ScheduleActivity;
import com.example.deneme.ui.syllabus.SyllabusActivity;
import com.example.deneme.ui.toDoList.ToDoListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deneme.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        //Check the user account already logged in
        if (user == null)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // initializing the user object to retrieve data
        newUser = new User("john", "doe", "johndoe@gmail.com", "13.02");

        /*try {
            newUser.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        /*try {
            newUser = new User();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        for( int index = 0; index < 6; index++ )
            System.out.println( newUser.getPreferencesIndices().get(index) + "BBBBBBBBBBBB");

        setSupportActionBar(binding.appBarMain.myToolbar);
        /*
        binding.appBarMain.myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                launchAdd( view );
            }
        });*/

        DrawerLayout drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        setUserInfo();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calendar, R.id.nav_syllabus, R.id.nav_toDoList, R.id.nav_schedule, R.id.nav_exams, R.id.nav_drawingTool)
                .setDrawerLayout(drawerLayout).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // getting the selection from the navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Menu menu = navigationView.getMenu();

                if( item.getItemId() == R.id.nav_home )
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                if( item.getItemId() == R.id.nav_calendar )
                    launchCalendar( findViewById( R.id.nav_calendar ) );
                if( item.getItemId() == R.id.nav_toDoList )
                    launchToDoList( findViewById( R.id.nav_toDoList ) );
                if( item.getItemId() == R.id.nav_exams )
                    startActivity(new Intent(MainActivity.this, ExamsActivity.class));
                if( item.getItemId() == R.id.nav_schedule )
                    startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
                if( item.getItemId() == R.id.nav_syllabus )
                    startActivity(new Intent(MainActivity.this, SyllabusActivity.class));
                if( item.getItemId() == R.id.nav_drawingTool )
                    startActivity(new Intent(MainActivity.this, DrawingToolActivity.class));

                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        // Handle item selection
        if( item.getItemId() == R.id.action_settings )
            launchSettings( findViewById( R.id.action_settings ) );

        if( item.getItemId() == R.id.action_logout ) {
            auth.signOut();
            startActivity( new Intent(this, LoginActivity.class) );
            finish();
        }

        if( item.getItemId() == R.id.action_about ) {
            startActivity( new Intent(this, AboutActivity.class) );
        }

        return true;
    }


    public void goUserPage( View v ){ startActivity( new Intent( MainActivity.this, UserActivity.class ) );}

    public void setUserInfo() {
        DrawerLayout drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        TextView userName = (TextView) headerView.findViewById(R.id.navUserNameText);
        TextView userEmail = (TextView) headerView.findViewById(R.id.navUserEmailText);
        ImageView userPicture = (ImageView) headerView.findViewById(R.id.navUserImage);


        documentReference = firestore.collection("Users").document(Objects.requireNonNull(user.getEmail()));

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {

                        String name = (String) document.get("name");
                        String surname = (String) document.get("surname");
                        String image = (String) document.get("image");
                        String email = (String) document.get("email");

                        userName.setText(name + " " + surname);
                        userEmail.setText(user.getEmail());
                        if (image.equals("default"))
                            userPicture.setImageResource(R.mipmap.ic_launcher_round);
                        else {
                            Picasso.get().load(image).resize(80,80).centerCrop().into(userPicture);
                        }
                    }
                }
            }
        });
    }

    public void launchDailyCalendar(View view)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void launchCalendar( View v ) {
        Intent i = new Intent( this, CalendarActivity.class );
        //i.putExtra("user", newUser);
        startActivity(i);
    }

    public void launchToDoList( View v ) {
        Intent i = new Intent( this, ToDoListActivity.class );
        //i.putExtra("user", newUser);
        startActivity(i);
    }

    public void launchAdd( View v ) {
        Intent i = new Intent(this, AddActivity.class);
        //i.putExtra("user", newUser);
        startActivity(i);
    }

    public void launchSettings( View v ) {
        Intent i = new Intent(this, SettingsActivity.class);
        //i.putExtra("user", newUser);
        startActivity(i);
    }
}
