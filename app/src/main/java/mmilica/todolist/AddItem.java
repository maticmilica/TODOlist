package mmilica.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static EditText name;
    public static String whichList;
    public static EditText desc;
    public static String description;
    Button addButton;
    Button cancelButton;
    Spinner spinner;
    Row row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        whichList = "listPersonal";

        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        name = (EditText) findViewById(R.id.name);
        desc = (EditText) findViewById(R.id.description);
        spinner = (Spinner) findViewById(R.id.spinner);


        String[] items = new String[]{"Personal", "Shopping", "Wishlist", "Work"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row = new Row(name.getText().toString(), desc.getText().toString(), whichList, "false");
                MainActivity.db.insert(row);
                MainActivity.adapter.setList(MainActivity.db.read());
                description = desc.getText().toString();

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (position) {
            case 0:
                whichList = "personal";
                break;
            case 1:
                whichList = "shopping";
                break;
            case 2:
                whichList = "wishlist";
                break;
            case 3:
                whichList = "work";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Context context = getApplicationContext();
        CharSequence text = "Choose list!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}