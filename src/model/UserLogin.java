package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UserLogin extends User {

    // Create the .txt file to write to
    public static String filename = "login_activity.txt";

    public UserLogin(int userId, String username) {
        super(userId, username);
    }

    /**
     * Writes the date, username, and login success/fail status to the .txt file.
     * @param loginUsername
     * @param loginSuccess
     * @throws IOException
     */
    public static void logUserActivity(String loginUsername, Boolean loginSuccess) throws IOException {

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);

        fwriter.write("Date: " + ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat) + " | Username: " +
                loginUsername + " | Login Success: " + loginSuccess + "\n");

        outputFile.close();
    }
}
