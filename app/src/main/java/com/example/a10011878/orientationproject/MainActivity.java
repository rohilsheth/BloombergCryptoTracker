package com.example.a10011878.orientationproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String json;
    String apiReq;
    JSONObject root;
    ArrayList<Cryptocurrency> arrayList;
    TextView t1, t2, t3, t4;
    String currentCap = "";
    int currentYear;
    String currentDetails = "";
    String currentCoin = "";
    Boolean req = true;
    Handler handler;
    Runnable handlerTask;
    int change = 0;
    private final static int Interval = 1000*8;
    public static final String YEAR_KEY = "savedyear";
    public static final String CAP_KEY = "savedcap";
    public static final String DETAILS_KEY = "saveddetails";
    public static final String ARRAY_KEY = "savedarray";
    public static final String NAME_KEY = "savedname";
    public static final String INTENT_KEY = "EE";
    Cryptocurrency bitcoin;
    Cryptocurrency ethereum;
    Cryptocurrency ripple;
    Cryptocurrency litecoin;
    Cryptocurrency dash;
    Cryptocurrency monero;
    Cryptocurrency omisego;
    Button news, converter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TAG",""+currentYear);
        Log.d("TAG",currentCap);
        Log.d("TAG",currentDetails);
        outState.putInt(YEAR_KEY,currentYear);
        outState.putString(CAP_KEY,currentCap);
        outState.putString(DETAILS_KEY,currentDetails);
        outState.putSerializable(ARRAY_KEY,arrayList);
        outState.putString(NAME_KEY,currentCoin);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int orientation = this.getResources().getConfiguration().orientation;
        listView = (ListView) findViewById(R.id.ID_ListView);
        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView6);
        t3 = (TextView) findViewById(R.id.textView10);
        t4 = (TextView) findViewById(R.id.textView5);
        news = (Button) findViewById(R.id.button3);
        converter = (Button) findViewById(R.id.button4);
        arrayList = new ArrayList<>();

        bitcoin = new Cryptocurrency("Bitcoin",2009,"$167.5 Billion","Bitcoin is the first cryptocurrency to use the blockchain technology, which was invented by its inventor Satoshi Nakamoto. Recently, it has become very popular, although transaction fees are very high.",R.drawable.bitcoin,"0","BTC","");
        ethereum = new Cryptocurrency("Ethereum",2015,"$45.6 Billion","Ethereum is the second most popular cryptocurrency, with more features such as smart contracts. It has had incredible growth over the last year.",R.drawable.ethereum,"0","ETH","");
        ripple = new Cryptocurrency("Ripple",2012,"$10.5 Billion", "Ripple is a coin that also runs on a public ledger format, but is more adopted by banks and payment networks for payment technology, as it offers more security.",R.drawable.ripple,"0","XRP","");
        litecoin = new Cryptocurrency("Litecoin",2011,"$5.0 Billion", "Litecoin is a light version of bitcoin, with faster transaction rates and less coins. It has become a strong cryptocurrency in its own. ",R.drawable.litecoin,"0","LTC","");
        dash = new Cryptocurrency("Dash",2014,"$4.8 Billion","Dash is a coin that allows instant and private transactions, and allows for self-governance and self-funding. It aims to be the most user-friendly. ",R.drawable.dash,"0","DASH","");
        monero = new Cryptocurrency("Monero",2014,"$2.87 Billion","Monero aims to improve on existing cryptocurrency design by obscuring sender, recipient and amount of every transaction made as well as making the mining process more equal.",R.drawable.monero,"0","XMR","");
        omisego = new Cryptocurrency("OmiseGo", 2017, "$1.0 Billion","OmiseGo is a new cryptocurrecny created on the Ethereum platform, with the intent to make financial transactions more efficient and cheaper. It has been highly praised and lauded for the technology that supports it.",R.drawable.omisego,"0","OMG","");
        arrayList.add(bitcoin);
        arrayList.add(ethereum);
        arrayList.add(ripple);
        arrayList.add(litecoin);
        arrayList.add(dash);
        arrayList.add(monero);
        arrayList.add(omisego);
        if(savedInstanceState!=null) {
            currentYear = savedInstanceState.getInt(YEAR_KEY);
            currentCap = savedInstanceState.getString(CAP_KEY);
            currentDetails = savedInstanceState.getString(DETAILS_KEY);
            currentCoin = savedInstanceState.getString(NAME_KEY);
            arrayList = (ArrayList) savedInstanceState.getSerializable(ARRAY_KEY);
            t1.setText(currentCap);
            t2.setText(currentYear+"");
            t4.setText(currentCoin);
            if(orientation == ORIENTATION_LANDSCAPE) {
                t3.setText(currentDetails);
            }
        }
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                intent.putExtra("List",arrayList);
                startActivity(intent);
            }
        });

        final CustomAdapter CryptoAdapter = new CustomAdapter(this,R.layout.list_layout,arrayList);

        listView.setAdapter(CryptoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentCap = arrayList.get(i).getMarketCap();
                currentYear = arrayList.get(i).getYear();
                currentDetails = arrayList.get(i).getDetails();
                currentCoin = arrayList.get(i).getName();
                t1.setText(currentCap);
                t2.setText(currentYear+"");
                t4.setText(currentCoin);
                if(orientation == ORIENTATION_LANDSCAPE) {
                    Log.d("TAG", "Land");
                    t3.setText(currentDetails);
                }

            }
        });
        listView.setAdapter(CryptoAdapter);
        final AsyncThread thread = new AsyncThread();
        thread.execute();
        handler = new Handler();
        handlerTask = new Runnable() {
            @Override
            public void run() {
                AsyncThread thread1 = new AsyncThread();
                thread1.execute();
                handler.postDelayed(handlerTask,Interval);
            }
        };
        handlerTask.run();

        //CryptoAdapter.notifyDataSetChanged();






    }
    public class CustomAdapter extends ArrayAdapter<Cryptocurrency> {
        Context context;
        List list;

        public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Cryptocurrency> objects) {
            super(context, resource, objects);
            this.context = context;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View layoutView = layoutInflater.inflate(R.layout.list_layout,null);
            TextView name = (TextView) layoutView.findViewById(R.id.textView2);
            TextView price = layoutView.findViewById(R.id.textView8);
            ImageView image = (ImageView) layoutView.findViewById(R.id.imageView);
            name.setText(arrayList.get(position).getName());
            price.setText("$"+arrayList.get(position).getPrice());
            image.setImageResource(arrayList.get(position).getID());
            if (change>0){
                if(arrayList.get(position).getColorvis().equals("red")){
                    price.setTextColor(Color.RED);
                }
                if(arrayList.get(position).getColorvis().equals("green")){
                    price.setTextColor(Color.GREEN);
                }

            }
            return layoutView;
        }
    }
    public class AsyncThread extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                apiReq = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,XRP,LTC,DASH,XMR,OMG&tsyms=USD";
                Log.d("TAG",apiReq);
                URL url = new URL(apiReq);
                URLConnection urlConnection = url.openConnection();
                InputStream stream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                json = "";
                while ((line = reader.readLine()) != null) {
                    json +=line;
                }
                Log.d("TAG",json+"front");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                root = new JSONObject(json);
                for(int i = 0;i<arrayList.size();i++){
                    String newPrice = root.getJSONObject(arrayList.get(i).getTicker()).get("USD")+"";
                    if (change>0) {
                        String oldPrice = arrayList.get(i).getPrice();
                        if (Double.parseDouble(oldPrice)>Double.parseDouble(newPrice)){
                            arrayList.get(i).setColorvis("red");
                        }
                        if (Double.parseDouble(oldPrice)<Double.parseDouble(newPrice)){
                            arrayList.get(i).setColorvis("green");
                        }

                    }
                    arrayList.get(i).setPrice(newPrice);
                }
                Log.d("TAG",bitcoin.getPrice()+"getting price");
                listView.setAdapter(new CustomAdapter(MainActivity.this,R.layout.list_layout,arrayList));
                change++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }

    }


