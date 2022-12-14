package DB2;

import java.sql.*;

public class DB2Test {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    public void openConnection(){

// Step 1: Load IBM DB2 JDBC driver

        try {

            DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());

        }

        catch(Exception cnfex) {

            System.out.println("Problem in loading or registering IBM DB2 JDBC driver");

            cnfex.printStackTrace();
        }

// Step 2: Opening database connection


        try {

            connection = DriverManager.getConnection("jdbc:db2://62.44.108.24:50000/SAMPLE", "db2admin", "db2admin");

            statement = connection.createStatement();

        }

        catch(SQLException s){

            s.printStackTrace();

        }

    }

    public void closeConnection(){

        try {

            if(null != connection) {

                // cleanup resources, once after processing

                resultSet.close();

                statement.close();


                // and then finally close connection

                connection.close();

            }

        }

        catch (SQLException s) {

            s.printStackTrace();

        }

    }

    public void select(String stmnt, int column) {

        try{

            resultSet = statement.executeQuery(stmnt);

            String result = "";

            while(resultSet.next()) {

                for (int i = 1; i <= column; i++) {

                    result += resultSet.getString(i);

                    if (i == column) result += " \n";
                    else             result += ", ";
                }
            }

            System.out.println("Executing query: " + stmnt + "\n");
            System.out.println("Result output \n");
            System.out.println("---------------------------------- \n");
            System.out.println(result);

        }
        catch (SQLException s)
        {
            s.printStackTrace();
        }

    }

    public void insert(String stmnt) {

        try{

            statement.executeUpdate(stmnt);

        }

        catch (SQLException s){

            s.printStackTrace();

        }

        System.out.println("Successfully inserted!");

    }


    public void delete(String stmnt) {

        try{

            statement.executeUpdate(stmnt);

        }

        catch (SQLException s){

            s.printStackTrace();

        }

        System.out.println("Successfully deleted!");

    }

    public static void main(String[] args) {

        DB2Test db2Obj = new DB2Test();
        String stmnt = "", stmnt2 = "", stmnt3 = "", stmnt4 = "";
        String name = "???????????? ????????????????";
        String phonenum = "0888976654";
        String email = "ralitsav26@abv.bg";
        String egn = "9712034389";
        String birthdate = "1997-12-03";

        db2Obj.openConnection();

        stmnt = "SELECT NAME, PHONENUM, BIRTHDATE, EMAIL FROM FN71943.PATIENTS";

        db2Obj.select(stmnt, 4);

        stmnt4 = "SELECT TYPE, PRICE FROM FN71943.TREATMENTS";

        db2Obj.select(stmnt4, 2);


        stmnt2 = " INSERT INTO FN71943.PATIENTS(EGN, PHONENUM, NAME, BIRTHDATE, EMAIL)"
                + " VALUES ('" + egn + "','" + phonenum + "','" + name + "','" + birthdate + "','" + email + "')";

     //   db2Obj.insert(stmnt2);


        stmnt3 = "DELETE FROM FN71943.PATIENTS WHERE NAME = " + "'" + name + "' ";

      //  db2Obj.delete(stmnt3);

        db2Obj.closeConnection();

    }

}
