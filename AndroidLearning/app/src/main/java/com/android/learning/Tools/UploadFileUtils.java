package com.android.learning.Tools;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liming.zhang on 2017/1/5.
 */

public class UploadFileUtils {

    String BOUNDARY = java.util.UUID.randomUUID().toString();
    String PREFIX = "--";
    String POSTFIX = "\r\n";

    private Context mContext;

    public UploadFileUtils(Context context){
        mContext = context;
    }

    public void uploadFile(String uploadUrl,String filePath){
        try{
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection","Keep-Alive");
            httpURLConnection.setRequestProperty("Charset","UTF-8");
            httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDARY);
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            File file = new File(filePath);
            Log.d("XXXXXXXXXXXXXX","file = "+file+",file.exists = "+file.exists());
            if(file != null && file.exists()){
                /*dos.writeBytes(PREFIX+BOUNDARY+POSTFIX);
                dos.writeBytes("Content-Disposition:form-data;name=\"Filedata\";filename="+"\""+file.getName()+"\""+POSTFIX);
                dos.writeBytes("Content-Type:application/octet-stream;charset=utf-8"+POSTFIX);
                dos.writeBytes(POSTFIX);*/

                StringBuilder sb = new StringBuilder();
                sb.append(PREFIX+BOUNDARY+POSTFIX);
                sb.append("Content-Disposition: form-data; name=\"Filedata\"; filename=\"" + file.getName() + "\"" +POSTFIX);
                sb.append("Content-Type:application/octet-stream;charset=utf-8"+POSTFIX);
                sb.append(POSTFIX);

                dos.write(sb.toString().getBytes());
                FileInputStream fis = new FileInputStream(file);
                byte []buffer = new byte[8192];
                int count = 0;
                while((count = fis.read(buffer)) != -1){
                    dos.write(buffer,0,count);
                }
                fis.close();
            }
            dos.write(POSTFIX.getBytes());
            dos.write((PREFIX+BOUNDARY+PREFIX+POSTFIX).getBytes());

            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            //Toast.makeText(mContext,result,Toast.LENGTH_SHORT).show();
            Log.d("XXXXXXXXXXXXXXXXXXX","httpURLConnection.getResponseCode() = "+httpURLConnection.getResponseCode()
                    +"\r\n upload result ......"+result);
            dos.close();
            is.close();
        }catch(Exception e){
            Log.d("XXXXXXXXXXXXXXXXXXX","upload excepiton ......");
            e.printStackTrace();
        }

    }

}
