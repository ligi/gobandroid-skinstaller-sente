package org.ligi.gobandroid.skinstaller.sente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class SenteInstaller extends Activity {
    
	private HashMap<Integer,String> install_hash =null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // install definitions 
        String path="/sdcard/gobandroid/skins/sente/";
        install_hash=new HashMap<Integer,String>();
        
        install_hash.put(R.drawable.black17, "black17.png");
        install_hash.put(R.drawable.white17, "white17.png");
        
        install_hash.put(R.drawable.black32, "black32.png");
        install_hash.put(R.drawable.white32, "white32.png");
        
        install_hash.put(R.drawable.black64, "black64.png");
        install_hash.put(R.drawable.white64, "white64.png");
        
        install_hash.put(R.drawable.kaya, "board.jpg");
        // prepare the path
    	new File(path).mkdirs(); // TODO handle path creation failure 
    	        
        try {
    		int cnt=0;
    		byte[] buf=new byte[1024];
    		
        	for (Integer res:install_hash.keySet()) {
				File f = new File(path+install_hash.get(res));
				f.createNewFile();

				FileOutputStream file_writer = new FileOutputStream(f);
				InputStream ins = getResources().openRawResourceFd(
						res).createInputStream();

				while ((cnt = ins.read(buf, 0, 1024)) != -1)
					file_writer.write(buf, 0, cnt);

				
				file_writer.close();
				ins.close();
        	}

        	File f = new File(path+".nomedia");
			f.createNewFile();        	
        	
		} catch (NotFoundException e) {
			Log.e("gobandroid", e.toString());
		} catch (IOException e) {
			Log.e("gobandroid", e.toString());
		}
        
                
		new AlertDialog.Builder(this).setTitle("Skinstaller").setMessage("Done installing the skin to the SD-Card so that gobandroid can access it. You can now uninstall this app by pressing OK.")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			// uninstall this app
			Uri uri = Uri.fromParts("package", "org.ligi.gobandroid.skinstaller.sente", null);
	        Intent it = new Intent(Intent.ACTION_DELETE, uri);
	        startActivity(it); 	
		}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		finish(); // finish without uninstalling
		}
		}).show();
        
        
    }
}