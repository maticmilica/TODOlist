package mmilica.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {

    public static EditText name1;
    public static EditText desc1;
    public static Button cancel;
    public static Button edit;
    String edited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name1 = (EditText) findViewById(R.id.name1);
        desc1 = (EditText) findViewById(R.id.description1);
        edit = (Button) findViewById(R.id.editButton1);
        cancel = (Button) findViewById(R.id.cancelButton1);

        edited = getIntent().getExtras().getString("name");
        final Row row = MainActivity.db.getTask(edited);

        name1.setText(row.getTaskName());
        desc1.setText(row.getDescription());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.db.editTask(row, name1.getText().toString().trim(), desc1.getText().toString().trim(), row.isChecked());
                MainActivity.adapter.setList(MainActivity.db.read());
                if (MainActivity.isClicked)
                    MainActivity.adapterTmp.setList(MainActivity.db.read());

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
