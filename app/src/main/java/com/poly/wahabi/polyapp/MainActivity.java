package com.poly.wahabi.polyapp;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.poly.wahabi.polyapp.model.Mydata;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    private Mydata mydata;
    private TableControllerMydata db;
    private FusedLocationProviderClient client;


    MyCustomAdapter myadpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        db=new TableControllerMydata(this);

        ArrayList<Mydata> Items=new  ArrayList<Mydata> ();
        Items= (ArrayList<Mydata>) db.read();
          myadpter= new MyCustomAdapter(Items);

        ListView ls=(ListView)findViewById(R.id.listview);
        ls.setAdapter(myadpter);





    }

    public void getLocation(View view) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    mydata=new Mydata();
                    mydata.setPosx((location.getLatitude()));
                    mydata.setPosy((location.getLongitude()));
                    mydata.setMdate(getCorruntDate());
                    TextView locationtext = findViewById(R.id.locationview);
                    TextView dateText=findViewById(R.id.mydate);
                     dateText.setText(getCorruntDate());
                    locationtext.setText("My Location : "+mydata.getPosx()+" : " + mydata.getPosy());
                }


            }
        });
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{ACCESS_FINE_LOCATION},1);
    }
    public String getCorruntDate(){

        Date dateObj = new Date();
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String newDateStr = postFormater.format(dateObj);
        return newDateStr;
    }
    public Double formatLoc(Double x){
        Toast.makeText(this, ""+x, Toast.LENGTH_SHORT).show();

        DecimalFormat numberFormat= new DecimalFormat("#0.000");
       return Double.valueOf(numberFormat.format(x));


    }
    public void saveData(View view) {
        EditText D1=findViewById(R.id.D1Edit);
        EditText D2=findViewById(R.id.D2Edit);
        EditText D3=findViewById(R.id.D3Edit);
        EditText D4=findViewById(R.id.D4Edit);
        EditText D5=findViewById(R.id.D5Edit);
        EditText D6=findViewById(R.id.D6Edit);
        if(mydata!=null){
            Toast.makeText(this, "x"+D1.getText().toString(), Toast.LENGTH_SHORT).show();
          if(D1.getText().toString()!= "")
            mydata.setD1(Double.parseDouble(""+D1.getText().toString()));
            if(D2.getText().toString()!= "")
                mydata.setD2(Double.parseDouble(""+D2.getText().toString()));
            if(D3.getText().toString()!= "")
                mydata.setD3(Double.parseDouble(""+D3.getText().toString()));
            if(D4.getText().toString()!= "")
                mydata.setD4(Double.parseDouble(""+D4.getText().toString()));
            if(D5.getText().toString()!= "")
                mydata.setD5(Double.parseDouble(D5.getText().toString()));
            if(D6.getText().toString()!= "")
                mydata.setD6(Double.parseDouble(D6.getText().toString()));
            Toast.makeText(this, ""+mydata.getPosx(), Toast.LENGTH_SHORT).show();

       if(db.create(mydata)){
           Toast.makeText(this, "added with success", Toast.LENGTH_SHORT).show();
           finish();
           startActivity(getIntent());
       }
       else{
           Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
       }





        }
        else{
            Toast.makeText(this, "Get your location First", Toast.LENGTH_SHORT).show();
        }
    }








    class MyCustomAdapter extends BaseAdapter
    {
        ArrayList<Mydata> Items=new ArrayList<Mydata>();
        MyCustomAdapter(ArrayList<Mydata> Items ) {
            this.Items=Items;

        }


        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position).getMdate();

        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater linflater =getLayoutInflater();
            View view1=linflater.inflate(R.layout.item, null);

            TextView txtmdate =(TextView) view1.findViewById(R.id.listmdate);
            TextView txtposx =(TextView) view1.findViewById(R.id.listposx);
            TextView txtposy =(TextView) view1.findViewById(R.id.listposy);
            TextView Ds =(TextView) view1.findViewById(R.id.Ds);


            txtmdate.setText("Date : "+ Items.get(i).getMdate());
            txtposx.setText("Latitude : " +Items.get(i).getPosx());
            txtposy.setText("Longitude : " +Items.get(i).getPosy());
            Ds.setText("Values of D : " +Items.get(i).getD1()+";"
                    +Items.get(i).getD2()+";"
                    +Items.get(i).getD3()+";"
                    +Items.get(i).getD4()+";"
                    +Items.get(i).getD5()+";"
                    +Items.get(i).getD6()+";"
            );

            return view1;

        }



    }
}