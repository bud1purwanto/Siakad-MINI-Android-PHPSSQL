package com.bud1purwanto.siakad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Beranda extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener{

    private TextView nama, nim, tj, alamat, iok, kwu, qms, iht, sik, bi, st, mn, ipk;
    private TextView Oiok, Okwu, Oqms, Oiht, Osik, Obi, Ost, Omn, Oipk;
    private SwipeRefreshLayout swipe;
    private String getNIM;
    private TextView NAMA, EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Beranda");

        if (Session.Login == false){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
            Session.NIM = null;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        NAMA = header.findViewById(R.id.nama);
        EMAIL = header.findViewById(R.id.email);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        Intent i = getIntent();
        getNIM = i.getStringExtra("nim");


        toolbar.setSubtitle("Selamat Datang "+Session.NIM);

        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);
        tj = findViewById(R.id.tj);
        alamat = findViewById(R.id.alamat);
        iok = findViewById(R.id.iok);
        kwu = findViewById(R.id.kwu);
        qms = findViewById(R.id.qms);
        iht = findViewById(R.id.iht);
        sik = findViewById(R.id.sik);
        bi = findViewById(R.id.bi);
        st = findViewById(R.id.st);
        mn = findViewById(R.id.mn);

        Oiok = findViewById(R.id.Oiok);
        Okwu = findViewById(R.id.Okwu);
        Oqms = findViewById(R.id.Oqms);
        Oiht = findViewById(R.id.Oiht);
        Osik = findViewById(R.id.Osik);
        Obi = findViewById(R.id.Obi);
        Ost = findViewById(R.id.Ost);
        Omn = findViewById(R.id.Omn);

        ipk = findViewById(R.id.ipk);

        swipe.setOnRefreshListener(this);

        NAMA.setText(Session.NIM);
        EMAIL.setText(Session.NIM+"@polinema.ac.id");

        getData();
    }

    private void getData(){
        class getData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Beranda.this,"Mengambil Data","Harap Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showData(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_Detail, Session.NIM);
                return s;
            }
        }
        getData ge = new getData();
        ge.execute();
    }

    private void showData(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");

            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Konfigurasi.KEY_NAMA);
            String number = c.getString(Konfigurasi.KEY_NIM);
            String year = c.getString(Konfigurasi.KEY_TAHUN);
            String way = c.getString(Konfigurasi.KEY_NJALUR);
            String classs = c.getString(Konfigurasi.KEY_NKElAS);
            String tempat = c.getString(Konfigurasi.KEY_TEMPAT);
            String tanggal = c.getString(Konfigurasi.KEY_TANGGAL);

            String address = c.getString(Konfigurasi.KEY_ALAMAT);

            String budi = c.getString(Konfigurasi.KEY_IOK);
            String hendra = c.getString(Konfigurasi.KEY_KWU);
            String romlah = c.getString(Konfigurasi.KEY_QMS);
            String shohib = c.getString(Konfigurasi.KEY_IHT);
            String deddy = c.getString(Konfigurasi.KEY_SIK);
            String dika = c.getString(Konfigurasi.KEY_BI);
            String vipkas = c.getString(Konfigurasi.KEY_ST);
            String deasy = c.getString(Konfigurasi.KEY_MN);

            float pbudi = Float.parseFloat(budi);
            float phendra = Float.parseFloat(hendra);
            float bromlah = Float.parseFloat(romlah);
            float pshohib = Float.parseFloat(shohib);
            float pdeddy = Float.parseFloat(deddy);
            float pdika = Float.parseFloat(dika);
            float pvipkas = Float.parseFloat(vipkas);
            float bdeasy = Float.parseFloat(deasy);

            float IPK = (pbudi+phendra+bromlah+pshohib+pdeddy+pdika+pvipkas+bdeasy)/8;

            nama.setText(name);
            nim.setText(number+" / "+way+" / "+year+" / "+classs);
            tj.setText(tempat+", "+tanggal);
            alamat.setText(address);

            iok.setText(budi);
            kwu.setText(hendra);
            qms.setText(romlah);
            iht.setText(shohib);
            sik.setText(deddy);
            bi.setText(dika);
            st.setText(vipkas);
            mn.setText(deasy);

            if (pbudi >= 3.5){
                Oiok.setText("Sempurna");
            } else if(pbudi >= 3.0 && pbudi < 3.49){
                Oiok.setText("Baik");
            } else if(pbudi >= 2.7 && pbudi < 2.99){
                Oiok.setText("Cukup");
            } else { Oiok.setText("Kurang"); }

            if (phendra >= 3.5){
                Okwu.setText("Sempurna");
            } else if(phendra >= 3.0 && phendra < 3.49){
                Okwu.setText("Baik");
            } else if(phendra >= 2.7 && phendra < 2.99){
                Okwu.setText("Cukup");
            } else { Okwu.setText("Kurang"); }

            if (bromlah >= 3.5){
                Oqms.setText("Sempurna");
            } else if(bromlah >= 3.0 && bromlah < 3.49){
                Oqms.setText("Baik");
            } else if(bromlah >= 2.7 && bromlah < 2.99){
                Oqms.setText("Cukup");
            } else { Oqms.setText("Kurang"); }

            if (pshohib >= 3.5){
                Oiht.setText("Sempurna");
            } else if(pshohib >= 3.0 && pshohib < 3.49){
                Oiht.setText("Baik");
            } else if(pshohib >= 2.7 && pshohib < 2.99){
                Oiht.setText("Cukup");
            } else { Oiht.setText("Kurang"); }

            if (pdeddy >= 3.5){
                Osik.setText("Sempurna");
            } else if(pdeddy >= 3.0 && pdeddy < 3.49){
                Osik.setText("Baik");
            } else if(pdeddy >= 2.7 && pdeddy < 2.99){
                Osik.setText("Cukup");
            } else { Osik.setText("Kurang"); }

            if (pdika >= 3.5){
                Obi.setText("Sempurna");
            } else if(pdika >= 3.0 && pdika < 3.49){
                Obi.setText("Baik");
            } else if(pdika >= 2.7 && pdika < 2.99){
                Obi.setText("Cukup");
            } else { Obi.setText("Kurang"); }

            if (pvipkas >= 3.5){
                Ost.setText("Sempurna");
            } else if(pvipkas >= 3.0 && pvipkas < 3.49){
                Ost.setText("Baik");
            } else if(pvipkas >= 2.7 && pvipkas < 2.99){
                Ost.setText("Cukup");
            } else { Ost.setText("Kurang"); }

            if (bdeasy >= 3.5){
                Omn.setText("Sempurna");
            } else if(bdeasy >= 3.0 && bdeasy < 3.49){
                Omn.setText("Baik");
            } else if(bdeasy >= 2.7 && bdeasy < 2.99){
                Omn.setText("Cukup");
            } else { Omn.setText("Kurang"); }

            ipk.setText("IPK : "+Float.toString(IPK));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        getData();
        swipe.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.beranda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
            Session.Login = false;
            Session.NIM = null;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
            Session.Login = false;
            Session.NIM = null;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
