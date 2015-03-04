package com.example.sms;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	Context mContext = null;  
	/**��ȡ��Phon���ֶ�**/  
    private static final String[] PHONES_PROJECTION = new String[] {  
        Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  
     
    /**��ϵ����ʾ����**/  
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
    /**�绰����**/  
    private static final int PHONES_NUMBER_INDEX = 1;  
      
    /**��ϵ������**/  
    private ArrayList<String> mContactsName = new ArrayList<String>();  
    public int totalContact;
    /**��ϵ��ͷ��**/  
    private ArrayList<String> mContactsNumber = new ArrayList<String>();  
    
	EditText[] showListName = new EditText[1000];
	EditText[] showListNumber = new EditText[1000];
	CheckBox[] showListDelete = new CheckBox[1000];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		/*
		final TextView text = new TextView(this);
		text.setText("Loading...");
		
		
		EditText User = new EditText(this);
		User.setOnEditorActionListener(new OnEditorActionListener(){
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
				text.setText("Editing User");
				return false;
			}
		});
		*/
		setContentView(R.layout.activity_main);
		Button importContact = (Button)findViewById(R.id.act1);
		importContact.setOnClickListener(new OnClickListener(){ 
			    public void onClick(View v) {  
			        if(R.id.act1 == v.getId()){  
			        	getPhoneContact();
			        	drawContactList();
			        }  
			    }  
		});
		
		Button confirmChange = (Button)findViewById(R.id.act3);
		confirmChange.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if (R.id.act3 == v.getId()){
					EditText smsContent = (EditText)findViewById(R.id.sms);
					for(int i=0; i<totalContact;i++) if (showListDelete[i].isChecked()){
						sendSMS(showListNumber[i].getText().toString(),showListName[i].getText().toString()+","+smsContent.getText().toString());
					}
					
				}
			}
		});
		//EditText show = (EditText)findViewById(R.id.msg);
		//show.setText("1+1=2".toCharArray(),0,5);
		
	}
	protected String sendSMS(String phone_number,String sms_content){
		if (!checkNumber()) return "���벻�Ϲ淶";  
		
		SmsManager smsManager = SmsManager.getDefault();
        if(sms_content.length() > 70) {
             List<String> contents = smsManager.divideMessage(sms_content);
             for(String sms : contents) {
                 smsManager.sendTextMessage(phone_number, null, sms, null, null);
             }
        } else {
             smsManager.sendTextMessage(phone_number, null, sms_content, null, null);
        }
        Toast.makeText(MainActivity.this, "��Ϣ�������", Toast.LENGTH_SHORT).show();
        return "���ͳɹ�";
	}
	private boolean checkNumber() {
		// TODO Auto-generated method stub
		return true;
	}
	protected void drawContactList() {
		EditText show = (EditText)findViewById(R.id.contactList);
		totalContact = mContactsName.size();
		String s = new String("�ܹ����� "+totalContact+" ����ϵ��");
		show.setText(s.toCharArray(),0,s.length());
		

		
		for(int i=0; i<min(totalContact,1000); i++){
			showListName[i] = new EditText(mContext);
			showListNumber[i] = new EditText(mContext);
			showListDelete[i] = new CheckBox(mContext);
			String name = mContactsName.get(i);
			showListName[i].setText(name.toCharArray(),0,name.length());
			String number = mContactsNumber.get(i);
			showListNumber[i].setText(number.toCharArray(),0,number.length());

			TableRow tr = new TableRow(mContext);
			TableLayout table = (TableLayout)findViewById(R.id.showlist);
			table.addView(tr);
			tr.addView(showListName[i]);
			tr.addView(showListNumber[i]);
			tr.addView(showListDelete[i]);
		}
	}

	private int min(int a, int b) {
		if (a < b) return a; else return b;
	}

	protected void getPhoneContact(){
		ContentResolver resolver = mContext.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
		 
	    if (phoneCursor != null) {  
	        while (phoneCursor.moveToNext()) {  
	        	//�õ��ֻ�����  
	        	String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
	        	//���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
	        	if (TextUtils.isEmpty(phoneNumber)) continue;  
	        	//�õ���ϵ������  
	        	String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
	        mContactsName.add(contactName);  
	        mContactsNumber.add(phoneNumber);  
	        //mContactsPhonto.add(contactPhoto);  
	        }  
	 
	        phoneCursor.close();  
	    }  
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}


