package com.example.user.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button buttonThread;
    private Button buttonAsyncTask;
    private Button buttonExecutorService;
    ExampleAsyncTask mAsyncTask;
    private Handler mHandler;
    private ExecutorService mExecutorService= Executors.newSingleThreadExecutor();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.data_textview);
        buttonAsyncTask=(Button)findViewById(R.id.button_AsyncTask);
        buttonAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAsyncTask = new ExampleAsyncTask();
                mAsyncTask.execute();

            }
        });

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0){
                    mTextView.setText("Start Thread");}
                else if (msg.what==1){
                    mTextView.setText("End Thread");
                } else if (msg.what==2){
                    mTextView.setText("Start ExecutorService");
                } else{
                    mTextView.setText("End ExecutorService");
                }
            }
        };

        buttonThread=(Button) findViewById(R.id.button_Thread);
        buttonThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread mThread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(0);
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                });
                mThread.start();
            }
        });

        buttonExecutorService=(Button)findViewById(R.id.button_ExecutorService);
        buttonExecutorService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(2);
                        try{
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(3);
                    }
                };
                mExecutorService.execute(runnable);

            }
        });



    }

    public class ExampleAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTextView.setText("Start AsyncTask");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mTextView.setText("End AsyncTask");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                java.util.concurrent.TimeUnit.SECONDS.sleep(2);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
