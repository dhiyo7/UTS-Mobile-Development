package plugin.id.utsfix;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Command> contacts;
    private Adapter adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    TextView search;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchContact("users", "");
    }

    public void fetchContact(String type, String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Command>> call = apiInterface.getContact(type, key);
        call.enqueue(new Callback<List<Command>>() {
            @Override
            public void onResponse(Call<List<Command>> call, Response<List<Command>> response) {
                progressBar.setVisibility(View.GONE);
                contacts = response.body();
                adapter = new Adapter(contacts, MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Command>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void fetchData(){
        Call<List<Command>> call = apiInterface.getContacts();
        call.enqueue(new Callback<List<Command>>() {
            @Override
            public void onResponse(Call<List<Command>> call, Response<List<Command>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                contacts = response.body();
                adapter = new Adapter(contacts, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Command>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchContact("users", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact("users", newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(this, EditActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

}
