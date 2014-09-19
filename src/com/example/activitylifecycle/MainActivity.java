package com.example.activitylifecycle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView threadCounter;
	int counterOfThread = 0;
	Thread pauseThread;

	public class ThreadClass implements Runnable {
		@Override
		public void run() {
			for (;;) {
				try {
					Thread.sleep(1000);
					counterOfThread++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		System.out.println("In Create");

		threadCounter = (TextView) findViewById(R.id.threadCountTextView);
		Button dialog = (Button) findViewById(R.id.dialogButton);
		Button close = (Button) findViewById(R.id.closeButton);
		Button startB = (Button) findViewById(R.id.startActivityBButton);

		final Intent intent = new Intent(this, ActivityB.class);

		ThreadClass pauseCount = new ThreadClass();
		pauseThread = new Thread(pauseCount);

		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.this.finish();
			}
		});

		startB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(intent);

			}
		});

		dialog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MainActivity.this);
				alertDialog.setMessage("Simple Dialog");
				alertDialog.setNeutralButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// MainActivity.this.finish();
							}
						});
				alertDialog.show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onStart() {
		super.onStart();
		System.out.println("On start");
	}

	@SuppressWarnings("deprecation")
	public void onResume() {
		super.onResume();
		System.out.println("On Resume");
		threadCounter.setText(String.valueOf(counterOfThread));
	}

	public void onPause() {
		super.onPause();
		System.out.println("Pause");
		if (!pauseThread.isAlive())
			pauseThread.start();
	}

	public void onStop() {
		super.onStop();
		System.out.println("Stop");
	}

	public void onRestart() {
		super.onRestart();
		System.out.println("Restart");
	}

	public void onDestroy() {
		super.onDestroy();
		System.out.println("Destroy");
	}
}
