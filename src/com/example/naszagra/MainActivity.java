package com.example.naszagra;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	// sta³e do menu
	private static final int DIALOG_EXIT = 0;
	private final int Pierszy = 0;
	private final int Drugi = 1;
	private final int Trzeci = 2;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnMainItemClickListener());
	}
	
	
																	// Lista dotykania
private class OnMainItemClickListener implements OnItemClickListener
{
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		TextView t1 = (TextView) findViewById(R.id.textView1);
		switch (position)
		{
			case Pierszy:
			{
				startActivity(new Intent(MainActivity.this, Touching.class));
				return;
			}
			case Drugi:
			{
				t1.setText("");
				t1.setText(R.string.Instructions);
				return;
			}
			case Trzeci:
			{
				t1.setText("");
				t1.setText(R.string.Authors);
				return;
			}
			
		}
	}
}
@Override                                                                               ////////////////////DIALOG WYJSCIOWY
protected Dialog onCreateDialog(int id)
{
	final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setCancelable(true);
	switch(id)
	{
	case DIALOG_EXIT:
		builder.setTitle(R.string.dlexit);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setMessage(R.string.dlask);
		builder.setPositiveButton(R.string.dlpos, new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int arg1)
			{
				finish();
			}
		});
		builder.setNegativeButton(R.string.dlneg, new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) 
			{
				dialog.cancel();
				
			}
		});
		break;
	}
	return builder.create();
}
@Override
@SuppressWarnings("deprecation")
public void onBackPressed()		// Wywo³anie dialogu na wyjscie z apki
{
	showDialog(DIALOG_EXIT);
}

}
