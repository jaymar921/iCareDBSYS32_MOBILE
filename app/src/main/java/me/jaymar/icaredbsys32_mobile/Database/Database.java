package me.jaymar.icaredbsys32_mobile.Database;

import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.jaymar.icaredbsys32_mobile.data.Account;
import me.jaymar.icaredbsys32_mobile.data.Data;
import me.jaymar.icaredbsys32_mobile.data.Inquiry;
import me.jaymar.icaredbsys32_mobile.data.InquiryData;
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

            String query = "{call getAccountByUsername(?,?)}";

            if(Utility.isEmail(username)) {
                query = "{call getAccountByEmail(?,?)}";
            }
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setString(1,username);
            callableStatement.setString(2, Utility.getMD5Hash(password));

            ResultSet resultSet = callableStatement.executeQuery();
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

            query = "{call getAccountById(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, loginCredentials.getAcc_id());

            resultSet = callableStatement.executeQuery();
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

    public static Data getAccounts(String account_id){
        try{
            Connection connection = Connect();

            if(connection == null)
                throw new Exception("Could not connect to database...");

            String query = "{call getLoginCredentialById(?)}";

            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setString(1,account_id);

            ResultSet resultSet = callableStatement.executeQuery();
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

            query = "{call getAccountById(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, loginCredentials.getAcc_id());

            resultSet = callableStatement.executeQuery();
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



            String query = "select pet_id,name,age,gender,breed,specie,blood_type,weight from pet where owner_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                PetData pet = new PetData(
                        resultSet.getString("pet_id"),
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

    public static String getServiceCode(String description){
        try {
            Connection connection = Connect();

            String query = "SELECT service_code from service where description like '%?%'";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,description);

            ResultSet rs = ps.executeQuery();

            connection.close();

            while (rs.next()){
                return rs.getString("service_code");
            }

        }catch (Exception ignore){

        }
        return "";
    }

    public static double getServicePrice(String code){
        try {
            Connection connection = Connect();

            String query = "SELECT price from service where service_code = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,code);

            ResultSet rs = ps.executeQuery();

            connection.close();

            while (rs.next()){
                return rs.getDouble("price");
            }

        }catch (Exception ignore){

        }
        return 0;
    }

    public static int generateRecordId(){
        try{
            Connection connection = Connect();
            String query = "select * from service_records order by record_id desc";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            connection.close();

            while (resultSet.next()){
                return resultSet.getInt("record_id")+1;
            }

        }catch (Exception ignore){}
        return 0;
    }

    public static boolean submitInquiry(InquiryData inquiry){
        try {
            Connection connection = Connect();

            String query = "INSERT INTO service_records (`record_id`,`pet_id`,`service_code`,`date`,`venue`,`status`,`remarks`) values (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,generateRecordId());
            statement.setString(2,inquiry.getPet_id());
            statement.setString(3,inquiry.getService_code());
            statement.setString(4,inquiry.getDate());
            statement.setString(5,inquiry.getVenue());
            statement.setString(6,"pending");
            statement.setString(7,inquiry.getRemark());

            statement.executeQuery();
            connection.close();
            return true;
        }catch (Exception ignore){}
        return false;
    }

    public static boolean updateAccount(Data data){
        try{
            Connection connection = Connect();

            String query = "UPDATE account SET firstname=?, lastname=?, birthdate=?, house_no=?, street=?, barangay=?, city=?, zip=? where acc_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,data.getAccount().getFirstname());
            preparedStatement.setString(2,data.getAccount().getLastname());
            preparedStatement.setString(3,new SimpleDateFormat("yyyy-MM-dd").format(data.getAccount().getBirthdate()));
            preparedStatement.setString(4,data.getAccount().getHouse_no());
            preparedStatement.setString(5,data.getAccount().getStreet());
            preparedStatement.setString(6,data.getAccount().getBarangay());
            preparedStatement.setString(7,data.getAccount().getCity());
            preparedStatement.setString(8,data.getAccount().getZip());
            preparedStatement.setString(9,data.getAccount().getAcc_id());

            preparedStatement.executeQuery();
            return true;
        }catch (Exception ignore){}
        return false;
    }
}
