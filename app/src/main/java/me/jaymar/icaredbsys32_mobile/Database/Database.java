package me.jaymar.icaredbsys32_mobile.Database;

import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.jaymar.icaredbsys32_mobile.data.Account;
import me.jaymar.icaredbsys32_mobile.data.Data;
import me.jaymar.icaredbsys32_mobile.data.Inquiry;
import me.jaymar.icaredbsys32_mobile.data.LoginCredentials;
import me.jaymar.icaredbsys32_mobile.data.PetData;
import me.jaymar.icaredbsys32_mobile.util.Utility;

public class Database {
    public static Connection Connect(){
        try {

            String url = "jdbc:mariadb://192.168.1.50:3306/icare_dbsys32";
            String user = "jaymar";
            String pass = "123456";

            Class.forName("org.mariadb.jdbc.Driver").newInstance();


            System.out.println("Connecting to database...");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            Connection connection = DriverManager.getConnection(url,user,pass);


            System.out.println("Connected to database!");

            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public static Data getAccounts(String username, String password){
        try{
            Connection connection = Connect();

            if(connection == null)
                throw new Exception("Could not connect to database...");

            String query = "SELECT * FROM login_credentials where username=? and password=?";

            if(Utility.isEmail(username)) {
                System.out.println("is email");
                query = "SELECT * FROM login_credentials where email=? and password=?";
            }
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2, Utility.getMD5Hash(password));

            ResultSet resultSet = ps.executeQuery();
            LoginCredentials loginCredentials = null;
            if(resultSet.next()){
                loginCredentials = new LoginCredentials(
                        resultSet.getString("login_id"),
                        resultSet.getString("acc_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("contact_no")
                );

            }

            if(loginCredentials == null)
                return null;

            query = "SELECT * FROM account WHERE acc_id = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, loginCredentials.getAcc_id());

            resultSet = ps.executeQuery();
            if(resultSet.next()){
                Account account = new Account(
                        resultSet.getString("acc_id"),
                        resultSet.getString("lastname"),
                        resultSet.getString("firstname"),
                        resultSet.getDate("birthdate"),
                        resultSet.getString("house_no"),
                        resultSet.getString("street"),
                        resultSet.getString("barangay"),
                        resultSet.getString("city"),
                        resultSet.getString("zip"),
                        resultSet.getDate("registry_date")
                );
                connection.close();

                return new Data(account,loginCredentials);
            }

        }catch (Exception e){

        }
        return new Data(null,null);
    }

    @NonNull
    public static List<Inquiry> getPendingSchedules(String username) {
        try {
            Connection connection = Connect();

            if (connection == null)
                return new ArrayList<>();

            String query = "select record_id, p.name as pet_id, s.description as service, sr.date, venue, sr.status, sr.remarks from service_records sr, service s,pet p, login_credentials l where sr.pet_id = p.pet_id and p.owner_id = l.acc_id and l.username = '" + username + "' and s.service_code = sr.service_code;";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("getting schedules");
            List<Inquiry> inquiries = new ArrayList<>();
            while (resultSet.next()) {
                Inquiry inquiry = new Inquiry(
                        resultSet.getString("record_id"),
                        resultSet.getString("pet_id"),
                        resultSet.getString("service"),
                        resultSet.getDate("date"),
                        resultSet.getString("venue"),
                        resultSet.getString("status"),
                        resultSet.getString("remarks")
                );
                inquiries.add(inquiry);
                System.out.println(inquiry);
            }
            connection.close();
            return inquiries;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @NonNull
    public static List<PetData> getPetInformation(String user_id){
        List<PetData> petData = new ArrayList<>();
        try {

            Connection connection = Connect();

            if(connection == null)
                return petData;



            String query = "select name,age,gender,breed,specie,blood_type,weight from pet where owner_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                PetData pet = new PetData(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getString("breed"),
                        resultSet.getString("specie"),
                        resultSet.getString("blood_type"),
                        resultSet.getDouble("weight")
                );
                petData.add(pet);
            }

            connection.close();

        }catch (Exception ignore){}
        return petData;
    }
}
