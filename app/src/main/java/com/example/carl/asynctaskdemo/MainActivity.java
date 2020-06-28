package com.example.carl.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private Button btn ;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        pb=findViewById(R.id.pb);
    }

    public void buttonClick(View view) {
        new TestTask().execute();

    }

    /**
     *AsyncTask
     * 参数：
     * param1:执行AsyncTask时传入的参数
     * param2:如果要显示进度，进度的类型。
     * param3:执行完毕后返回值的类型
     */

    class TestTask extends AsyncTask<Void,Integer,Boolean>{
        private int mProgress=0;
        // 当前还在主线程当中，做些准备工作，还没开始执行异步任务
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            log("准备下载");
            pb.setVisibility(View.VISIBLE);
        }

        //在异步线程里面执行
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                while (true){

                    Thread.sleep(1000);
                    mProgress+=10;
                    publishProgress(mProgress);
                    if (mProgress>=100){
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            log("正在下载："+values[0]);
            pb.setProgress(values[0]);
        }

        //切换到主线程执行

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                log("下载成功");
                pb.setVisibility(View.GONE);
            }else {
                log("下载失败");
            }
        }
    }

    private void log(String str){
        Log.i("chen",str);
    }
}
