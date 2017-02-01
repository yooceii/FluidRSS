package com.myapp.yooceii.fluidrss.Utils;

import android.content.Context;
import android.os.Environment;

import com.myapp.yooceii.fluidrss.model.Rssres;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by yooceii on 2016/11/14.
 */

public class JSONSerializer {
    private Context mContext;
    private String mFilename;
    private File mFile;
    public JSONSerializer(Context context,String filename)
    {
        this.mContext=context;
        this.mFilename=filename;
        mFile =new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),mFilename);
    }
    public ArrayList<Rssres> loadRss() throws IOException,JSONException
    {
        ArrayList<Rssres> rssresList=new ArrayList<Rssres>();
        BufferedReader reader=null;
        try {
            InputStream in=mContext.openFileInput(mFilename);
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString=new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null)
            {
                jsonString.append(line);
            }
            JSONArray array=(JSONArray)new JSONTokener(jsonString.toString()).nextValue();
            for(int i=0;i<array.length();i++)
            {
                rssresList.add(new Rssres(array.getJSONObject(i)));
            }
        }catch (FileNotFoundException e)
        {

        }finally {
            if(reader!=null)
                reader.close();
        }
        return rssresList;
    }
    public void saveRssres(ArrayList<Rssres> rssresArrayList) throws JSONException,IOException
    {
        JSONArray array=new JSONArray();
        for(Rssres r:rssresArrayList)
        {
            array.put(r);
            Writer writer=null;
            try
            {
                OutputStream out=mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
                writer=new OutputStreamWriter(out);
                writer.write(array.toString());
            }finally {
                if(writer!=null)
                    writer.close();
            }
        }
    }
}
