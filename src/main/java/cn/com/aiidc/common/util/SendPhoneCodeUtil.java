package cn.com.aiidc.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SendPhoneCodeUtil {
	private static String mainHost = "http://120.27.46.232:888/SDK/Service.asmx/";
	private static String sendURL = mainHost + "sendMessage";
	
    public static String doPost ( String url, NameValuePair[] params, String charset)   
    {   
          StringBuffer response = new StringBuffer();   
          //HttpClient client = new HttpClient();   
          HttpClient client=new HttpClient();
          client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);//ָ�������ַ�ΪGBK��ʽ

          client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);//�������ӳ�ʱʱ��Ϊ10�루���ӳ�ʼ��ʱ�䣩

          PostMethod method = new PostMethod(url);
          if(params!=null)
          {
              method.setRequestBody(params);
          }
          try  
          {   
                client.executeMethod(method);   
                if ( method.getStatusCode() == HttpStatus.SC_OK )   
                {   
                      BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));   
                      String line;   
                      while ( ( line = reader.readLine() ) != null )   
                      {   
                                  response.append(line);   
                      }   
                      reader.close();   
                }
          }   
          catch ( IOException e )   
          { 
        	  System.out.println("1");  
          }   
          finally  
          {   
                method.releaseConnection();   
          }   
          return response.toString();   
    }   

	public static int sendMessage(String phones, String contents, String scode,String setTime)
	{
   	 	NameValuePair par1 = new NameValuePair("username","cf-cdyskj");    
   	 	NameValuePair par2 = new NameValuePair("pwd","6FH0UB");
   	    NameValuePair par3 = new NameValuePair("phones",phones);
	   	NameValuePair par4 = new NameValuePair("contents",contents);
	   	NameValuePair par5 = new NameValuePair("scode",scode);
	   	NameValuePair par6 = new NameValuePair("setTime",setTime);
   	 	NameValuePair[] pa = new NameValuePair[]{par1,par2,par3,par4,par5,par6} ;
        String result = doPost(sendURL,pa, "utf-8");   
 
	   	if(result!="")
	   	{
	   		 Document doc = null;
		try {
				 doc = DocumentHelper.parseText(result);
				 Element rootElt = doc.getRootElement(); 
		   		 return Integer.parseInt(rootElt.getText());
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	}
	   	return -1;
	}
}
