package me.jaymar.icaredbsys32_mobile.util;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    @NonNull
    public static String getMD5Hash(String data){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(data.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32){
                hashtext.insert(0, "0");
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

    @NonNull
    @Contract("_ -> new")
    public static String[] getFirstLastname(String fullName){
        String[] name = fullName.split(" ");
        StringBuilder firstname = new StringBuilder(name[0]);
        String lastname = "";
        for(int i = 1; i < name.length; i++){
            if(i == name.length-1)
                lastname = name[i];
            else
                firstname.append(" ").append(name[i]);
        }
        return new String[]{firstname.toString(), lastname};
    }
}
