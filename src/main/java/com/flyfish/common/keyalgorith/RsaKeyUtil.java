package com.flyfish.common.keyalgorith;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.Base64;


/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年5月16日 下午2:26:34 
 *</p>
  * @version 1.0  
*/
public class RsaKeyUtil {
  public static final String RSA_KEY="RSA";
  private static final int KEY_SIZE=1024;
  private static final String PUB_KEY="RSAPublicKey";
  private static final String PRIV_KEY="RSAPrivateKey";
  public static Map<String,Object> initKey() throws NoSuchAlgorithmException{
      Map<String,Object> keyMap=new HashMap<String,Object>();
      KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(RSA_KEY);
      keyPairGenerator.initialize(KEY_SIZE);
      KeyPair keyPair=keyPairGenerator.generateKeyPair();
      RSAPublicKey publicKey=(RSAPublicKey)keyPair.getPublic();
      RSAPrivateKey privateKey=(RSAPrivateKey)keyPair.getPrivate();
      System.out.println("publicKey:"+publicKey);
      System.out.println("privateKey:"+privateKey);
      keyMap.put(PUB_KEY, publicKey);
      keyMap.put(PRIV_KEY, privateKey);
      return keyMap;
  }
  
  public static byte[] getPrivateKey(Map<String,Object> keyMap){
     Key key=(Key) keyMap.get(PRIV_KEY);
     return key.getEncoded();
  }
  public static byte[] getPublicKey(Map<String,Object> keyMap){
      Key key=(Key)keyMap.get(PUB_KEY);
      return key.getEncoded();
  }
  
  public static String getRsaKey() throws NoSuchAlgorithmException{
      Map<String,Object> keyMap=initKey();
    return "";
  }
  public static void main(String[] args) {
      try {
        initKey();
    }
    catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}
