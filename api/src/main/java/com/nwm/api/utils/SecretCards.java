package com.nwm.api.utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
@Component
public class SecretCards implements Serializable{

    

    /**
	 * 
	 */
	private static final long serialVersionUID = 3950028107262856084L;

	public String encrypt(String message)  {
    	try{
        final MessageDigest md = MessageDigest.getInstance("md5");
        //"1kkqpqzjx14959j"
        final byte[] digestOfPassword = md.digest(Constants.AES_KEY.getBytes("utf-8"));
        
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
        }

        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        final byte[] plainTextBytes = message.getBytes("utf-8");
        final byte[] cipherText = cipher.doFinal(plainTextBytes);
        // final String encodedCipherText = new sun.misc.BASE64Encoder()
        // .encode(cipherText);
        String hex=byteArrayToHexString(cipherText);
        return hex;
    	}catch(Exception ex){
    		return null;
    	}
    }

    public String decrypt(String message){
    	try{
    	byte[] str=hexStringToByteArray(message);	
        final MessageDigest md = MessageDigest.getInstance("md5");
        final byte[] digestOfPassword = md.digest(Constants.AES_KEY.getBytes("utf-8"));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
        }

        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, key, iv);

        final byte[] plainText = decipher.doFinal(str);

        return new String(plainText, "UTF-8");
    	}catch(Exception ex){
    		
				return null;
			
    	}
    }
  
      private  String byteArrayToHexString(byte[] b){
          StringBuffer sb = new StringBuffer(b.length * 2);
          for (int i = 0; i < b.length; i++){
            int v = b[i] & 0xff;
            if (v < 16) {
              sb.append('0');
            }
            sb.append(Integer.toHexString(v));
          }
          return sb.toString().toUpperCase();
      }

        private  byte[] hexStringToByteArray(String s) {
          byte[] b = new byte[s.length() / 2];
          for (int i = 0; i < b.length; i++){
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte)v;
          }
          return b;
      }

     
}
