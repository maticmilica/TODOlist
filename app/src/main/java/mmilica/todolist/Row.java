package mmilica.todolist;

/**
 * Created by mmilica on 26.10.2016..
 */

public class Row {
    public String taskName;
    public String description;
    public String belong;
    public String isChecked;

    public Row(String taskName, String description, String belong, String isChecked) {
        this.taskName = taskName;
        this.belong = belong;
        this.description = description;
        this.isChecked = isChecked;
    }

    public void setChecked(String checked) {
        isChecked = checked;
    }

    public String isChecked() {
        return isChecked;
    }

    public String getBelong() {
        return belong;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}