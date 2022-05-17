package me.jaymar.icaredbsys32_mobile.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static String getMD5Hash(String data){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(data.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32){
                hashtext = "0" + hashtext;
            }

            return hashtext.substring(0,30);
        }catch (Exception ignore){}
        return "";
    }

    public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                ")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
