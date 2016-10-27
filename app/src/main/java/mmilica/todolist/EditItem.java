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

        name1.setText(MainActivity.taskName);
        desc1.setText(MainActivity.taskDesc);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.isClicked) {
                    MainActivity.adapter.editName(name1.getText().toString(), desc1.getText().toString(), MainActivity.position);
                    MainActivity.adapter.notifyDataSetChanged();
                } else
                {
                    MainActivity.adapter.editName(name1.getText().toString(), desc1.getText().toString(), MainActivity.position);
                    MainActivity.adapterTmp.editName(name1.getText().toString(), desc1.getText().toString(), MainActivity.position);
                    MainActivity.adapter.notifyDataSetChanged();
                    MainActivity.adapterTmp.notifyDataSetChanged();
                }
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
