import java.sql.*;
import java.util.Scanner;

public class Studentdatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "";

        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Database!");

            while (true) {
                System.out.println("\n------ MENU ------");
                System.out.println("1. Insert Student");
                System.out.println("2. Display Students");
                System.out.println("3. Modify Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Search Student");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // INSERT
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Roll No: ");
                        int rollNo = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Department: ");
                        String department = sc.nextLine();
                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Address: ");
                        String address = sc.nextLine();
                        System.out.print("Enter Phone No: ");
                        String phoneNo = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Tuition Fees: ");
                        double tuitionFees = sc.nextDouble();

                        String insertQuery = "INSERT INTO college (name, Rollno, Department, Gender, Age, Address, Phone_no, Email, tuition_fees) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                        insertStmt.setString(1, name);
                        insertStmt.setInt(2, rollNo);
                        insertStmt.setString(3, department);
                        insertStmt.setString(4, gender);
                        insertStmt.setInt(5, age);
                        insertStmt.setString(6, address);
                        insertStmt.setString(7, phoneNo);
                        insertStmt.setString(8, email);
                        insertStmt.setDouble(9, tuitionFees);
                        insertStmt.executeUpdate();
                        System.out.println("✅ Student inserted successfully!");
                        break;

                    case 2: // DISPLAY
                        Statement displayStmt = conn.createStatement();
                        ResultSet rs = displayStmt.executeQuery("SELECT * FROM college");
                        System.out.println("\n------ Student Records ------");
                        while (rs.next()) {
                            System.out.println("Roll No: " + rs.getInt("Rollno") +
                                               "\nName: " + rs.getString("name") +
                                               "\nDept: " + rs.getString("Department") +
                                               "\nGender: " + rs.getString("Gender") +
                                               "\nAge: " + rs.getInt("Age") +
                                               "\nAddress: " + rs.getString("Address") +
                                               "\nPhone: " + rs.getString("Phone_no") +
                                               "\nEmail: " + rs.getString("Email") +
                                               "\nFees: " + rs.getDouble("tuition_fees"));
                        }
                        break;

                    case 3: // MODIFY
                        System.out.print("Enter Roll No to update: ");
                        int modifyRoll = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Address: ");
                        String newAddress = sc.nextLine();
                        System.out.print("Enter New Phone No: ");
                        String newPhone = sc.nextLine();

                        String updateQuery = "UPDATE college SET Address = ?, Phone_no = ? WHERE Rollno = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                        updateStmt.setString(1, newAddress);
                        updateStmt.setString(2, newPhone);
                        updateStmt.setInt(3, modifyRoll);
                        int rowsUpdated = updateStmt.executeUpdate();
                        System.out.println(rowsUpdated > 0 ? "✅ Student updated!" : "❌ Student not found.");
                        break;

                    case 4: // DELETE
                        System.out.print("Enter Roll No to delete: ");
                        int deleteRoll = sc.nextInt();
                        String deleteQuery = "DELETE FROM college WHERE Rollno = ?";
                        PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                        deleteStmt.setInt(1, deleteRoll);
                        int rowsDeleted = deleteStmt.executeUpdate();
                        System.out.println(rowsDeleted > 0 ? "✅ Student deleted!" : "❌ Student not found.");
                        break;

                    case 5: // SEARCH
                        System.out.print("Enter Roll No to search: ");
                        int searchRoll = sc.nextInt();
                        String searchQuery = "SELECT * FROM college WHERE Rollno = ?";
                        PreparedStatement searchStmt = conn.prepareStatement(searchQuery);
                        searchStmt.setInt(1, searchRoll);
                        ResultSet searchRs = searchStmt.executeQuery();
                        if (searchRs.next()) {
                            System.out.println("✅ Student Found: " +
                                "\nName: " + searchRs.getString("name") +
                                "\nDepartment: " + searchRs.getString("Department") +
                                "\nGender: " + searchRs.getString("Gender") +
                                "\nAge: " + searchRs.getInt("Age") +
                                "\nAddress: " + searchRs.getString("Address") +
                                "\nPhone: " + searchRs.getString("Phone_no") +
                                "\nEmail: " + searchRs.getString("Email") +
                                "\nTuition Fees: " + searchRs.getDouble("tuition_fees"));
                        } else {
                            System.out.println("❌ Student not found.");
                        }
                        break;

                    case 6: // EXIT
                        System.out.println("👋 Exiting...");
                        conn.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("❌ Invalid choice. Try again.");
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Error!");
            e.printStackTrace();
        }
    }
}

