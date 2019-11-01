package applications.listadapter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);


        final DatabaseHelper mydb = new DatabaseHelper(this);


        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(ViewListContents.this,"The Database is empty  :(.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                user = new User(data.getString(1),data.getString(2),data.getString(3),data.getString(4));
                userList.add(i,user);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+"  "+data.getString(4));
                System.out.println(userList.get(i).getFirstName());
                i++;
            }

            ThreeColumn_ListAdapter adapter =  new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, userList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String getName = ((TextView) view).getText().toString();
                    if( mydb.deleteContactByName(getName) == 1){
                        Toast.makeText(getApplicationContext(),
                                "Record DELETED", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}