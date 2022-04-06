package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.fragments.DescriptionExerciseFragment;
import com.example.fitapp.fragments.ExerciseFragment;
import com.example.fitapp.model.Exercise;
import com.example.fitapp.model.User;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener {
    private int positionTraining;
    private int positionExercise = 0;
    private User user;
    private TextView nameTraining,timer;
    private ImageView previousEx;
    private ImageView nextEx;
    private ArrayList<ExerciseFragment> exercises = new ArrayList<>();
    private ArrayList<DescriptionExerciseFragment> descriptionExerciseFragments = new ArrayList<>();
    private MyThread myThread;
    public static Bundle myBundle = new Bundle();
    private ImageView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        previousEx = findViewById(R.id.EA_previousExercise);
        nextEx = findViewById(R.id.EA_nextExercise);
        timer = findViewById(R.id.EA_timer);
        info = findViewById(R.id.EA_info);
        previousEx.setOnClickListener(this);
        nextEx.setOnClickListener(this);
        info.setOnClickListener(this);
        this.positionTraining = getIntent().getExtras().getInt("positionTraining");
        user = User.getInstance();
        nameTraining = findViewById(R.id.EA_nameTraining);
        nameTraining.setText(user.getTrainings().get(positionTraining).getName());
        for(int i =0; i< user.getTrainings().get(positionTraining).getExercises().size();i++ ){
            exercises.add(ExerciseFragment.newInstance(i,positionTraining));
            descriptionExerciseFragments.add(DescriptionExerciseFragment.newInstance(i,positionTraining));
        }
        startThread();
        startThread2();
        user.setActuallTraining(user.getTrainings().get(positionTraining).getHistoryTraining());

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("positionTraining", positionTraining);
            bundle.putInt("positionExercise", positionExercise);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.EA_fragmentExercise, exercises.get(positionExercise), String.valueOf(positionExercise))
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.EA_fragmentDescription, descriptionExerciseFragments.get(positionExercise), String.valueOf(positionExercise))
                    .commit();
        }

    }

    @SuppressLint("HandlerLeak")
    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                String tmp = String.valueOf(msg.arg1);
                timer.setText(tmp);
            }
            else if(msg.what == 2){
                timer.setText("90");
            }
            else if(msg.what == 3){
                timer.setText("90");
                exercises.get(0).nextSerie.performClick();
                Toast.makeText(ExerciseActivity.this,"Odpoczynek skończony !!",Toast.LENGTH_LONG).show();
            }
        }
    };




    class MyThread implements Runnable{
        private boolean exit;

        public MyThread() {
            this.exit = false;
        }
        @Override
        public void run() {
            int i = 90;
            while (!exit && i!=0){
                Message message = mHandler.obtainMessage();
                message.what = 1;
                message.arg1 = i;
                mHandler.sendMessage(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i -= 1;
                if(i== 0){
                    Message message1 = mHandler.obtainMessage();
                    message1.what = 3;
                    mHandler.sendMessage(message1);
                }
            }

            myBundle.putString("ready","1");
        }

        public void stop()
        {
            exit = true;
        }
    }




    public void startThread(){
        new Thread  (new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                while (true){
                    SystemClock.sleep(1000);
                    if(myBundle.get("clicked")!=null &&myBundle.get("clicked").equals("1")){
                        myThread = new MyThread();
                        myThread.run();
                        myBundle.clear();
                    }
                }
            }
        }).start();
    }

    public void startThread2(){
        new Thread  (new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(myBundle.get("changed")!=null &&myBundle.get("changed").equals("1")){
                        myBundle.clear();
                        if (myThread !=null){
                            myThread.stop();
                        }
                        Message message = mHandler.obtainMessage();
                        message.what = 2;
                        mHandler.sendMessage(message);
                    }
                }
            }
        }).start();
    }




    @Override
    public void onClick(View v) {
        FragmentTransaction ft;

        switch (v.getId()){
            case R.id.EA_previousExercise:
                ft = getSupportFragmentManager().beginTransaction();
                positionExercise--;
                for (ExerciseFragment f : exercises){
                    if(f.isAdded()){
                        ft.hide(f);
                        if (myThread !=null){
                            myThread.stop();
                        }
                    }
                }
                for (DescriptionExerciseFragment df : descriptionExerciseFragments){
                    if(df.isAdded()){
                        ft.hide(df);
                        if (myThread !=null){
                            myThread.stop();
                        }

                    }
                }
                if (exercises.get(positionExercise).isAdded()){
                    ft.show(exercises.get(positionExercise));
                    if (myThread !=null){
                        myThread.stop();
                    }

                } else {
                    ft.add(R.id.EA_fragmentExercise, exercises.get(positionExercise), String.valueOf(positionExercise));
                    if (myThread !=null){
                        myThread.stop();
                    }

                }
                if (descriptionExerciseFragments.get(positionExercise).isAdded()){
                    ft.show(descriptionExerciseFragments.get(positionExercise));
                    if (myThread !=null){
                        myThread.stop();
                    }

                } else {
                    ft.add(R.id.EA_fragmentDescription, descriptionExerciseFragments.get(positionExercise), String.valueOf(positionExercise));
                    if (myThread !=null){
                        myThread.stop();
                    }

                }

                if (positionExercise ==0){
                    previousEx.setVisibility(View.INVISIBLE);
                    if (myThread !=null){
                        myThread.stop();
                    }

                }
                Message message = mHandler.obtainMessage();
                message.what = 2;
                mHandler.sendMessage(message);
                ft.commit();
                break;
            case R.id.EA_nextExercise:
               ft = getSupportFragmentManager().beginTransaction();
                if (exercises.size()-1 > positionExercise ){
                    previousEx.setVisibility(View.VISIBLE);
                    positionExercise++;
                    for (ExerciseFragment f : exercises){
                        if(f.isAdded()){
                            ft.hide(f);
                            if (myThread !=null){
                                myThread.stop();
                            }
                        }
                    }
                    for (DescriptionExerciseFragment df : descriptionExerciseFragments){
                        if(df.isAdded()){
                            ft.hide(df);
                            if (myThread !=null){
                                myThread.stop();
                            }

                        }
                    }
                    if (exercises.get(positionExercise).isAdded()){
                        ft.show(exercises.get(positionExercise));
                        if (myThread !=null){
                            myThread.stop();
                        }

                    } else {
                        ft.add(R.id.EA_fragmentExercise, exercises.get(positionExercise), String.valueOf(positionExercise));
                        if (myThread !=null){
                            myThread.stop();
                        }


                    }

                    if (descriptionExerciseFragments.get(positionExercise).isAdded()){
                        ft.show(descriptionExerciseFragments.get(positionExercise));
                        if (myThread !=null){
                            myThread.stop();
                        }

                    } else {
                        ft.add(R.id.EA_fragmentDescription, descriptionExerciseFragments.get(positionExercise), String.valueOf(positionExercise));
                        if (myThread !=null){
                            myThread.stop();
                        }

                    }
                } else {
                    user.getActuallTraining().checkChecking();
                    startActivity(new Intent(ExerciseActivity.this, SummaryActivity.class));
                    if (myThread !=null){
                        myThread.stop();
                    }

                    this.finish();
                }
                Message message1 = mHandler.obtainMessage();
                message1.what = 2;
                mHandler.sendMessage(message1);
                ft.commit();
                break;
            case R.id.EA_info:
                Intent intent = new Intent(ExerciseActivity.this, InfoExerciseActivity.class);
                intent.putExtra("position", positionExercise);
                intent.putExtra("x", "zzz");
                intent.putExtra("positionTraining", positionTraining);
                startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExerciseActivity.this, MainScreenActivity.class));
        User.getInstance().setActuallTraining(null);
        this.finish();
    }
}