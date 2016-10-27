package mmilica.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by mmilica on 26.10.2016..
 */

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Row> tasks;

    public ListAdapter(Context context)
    {
        this.context = context;
        tasks = new ArrayList<Row>();
    }

    public void addTasks(Row task)
    {
        tasks.add(0,task);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    public String getName(int position) {return tasks.get(position).getTaskName();}
    public String getDesc(int position) {return tasks.get(position).getDescription();}

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void deleteItem(int position) { tasks.remove(position); }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Row row = (Row) getItem(position);
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, null);
            ViewHolder holder = new ViewHolder();
            holder.tName = (TextView) view.findViewById(R.id.name);
            holder.box = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(holder);

        }

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.tName.setText(row.getTaskName());
        if (row.isChecked().equals("true"))
            holder.box.setChecked(true);
        else
            holder.box.setChecked(false);

        return view;
    }

    private class ViewHolder {
        public TextView tName = null;
        public CheckBox box;
    }

    public ListAdapter find (String list)
    {
        ListAdapter adapterTmp = new ListAdapter(context);

        if (list.equals("done"))
        {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).isChecked().equals("true")) {
                    adapterTmp.addTasks(tasks.get(i));
                }
            }
        } else if (!list.equals("all")) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getBelong().equals(list)) {
                    adapterTmp.addTasks(tasks.get(i));
                }
            }
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                adapterTmp.addTasks(tasks.get(i));
            }
        }

        return adapterTmp;
    }

    public void editName (String newName, String newDesc, int position)
    {
        tasks.get(position).setTaskName(newName);
        tasks.get(position).setDescription(newDesc);
    }

    public void editChecked (int position, String checked)
    {
        tasks.get(position).setChecked(checked);
    }

    public void setList(ArrayList<Row> tasks)
    {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

}
