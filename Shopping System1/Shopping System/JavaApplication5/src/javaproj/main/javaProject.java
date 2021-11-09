package javaproj.main;

import javaproj.model.Product;
import javaproj.shop.ShoppingStore;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

class customer
{
    protected String uname;
    protected String password;

    customer( String uname,String password)
    {
        this.uname = uname;
        this.password = password;
    }
}

class createNewAccount extends customer
{
    protected String name;
    protected String email;
    protected String city;

    createNewAccount(String name, String uname, String password, String email, String city)
    {
        super(uname, password);
        this.name = name;
        this.email = email;
        this.city = city;
    }

    public String toString()
    {
        return ""+name+" your account was successfully created";
    }
}

class login extends customer
{

    login( String uname, String password)
    {
        super( uname, password);
    }

    public String toString()
    {
        return "Please continue shopping "+uname;
    }

}

class loginAdmin extends  customer
{
    loginAdmin( String uname, String password)
    {
        super( uname, password);
    }
    public String toString()
    {
        return "Welcome "+uname;
    }
}

abstract class payment
{
    abstract void card();
    abstract void cash();
}

class pay extends payment
{
    Scanner sc1 = new Scanner(System.in);
    @Override

    void card()
    {
        int ab = 0;
        do
        {
            System.out.println("Enter card number");
            long cardno = sc1.nextLong();
            int length1 = String.valueOf(cardno).length();

            System.out.println("Enter CVV");
            int cvv = sc1.nextInt();
            int length2 = String.valueOf(cvv).length();

            if (length1 == 16 && length2 == 3 )
            {
                System.out.println("CVV and Card number validated");
                ab++;
            }

            else
            {
                System.out.println("Please check your payment details");
            }

        }while (ab==0);

        System.out.println("Your order has been confirmed");
        System.out.println("Thank-you for shopping with us :)");
    }

    @Override
    void cash()
    {
        System.out.println("Your order has been confirmed");
        System.out.println("Thank-you for shopping with us :)");
    }
}

class javaProject
{
    public static void help()
    {
        System.out.println("Type \"Q\" to Quit :( ");
        System.out.println("Type \"H\" for help to display commands ;) ");
        System.out.println("Type \"add <ProductId>\" to add to cart");
        System.out.println("Type \"remove <ProductId>\" to remove from cart");
        System.out.println("Type \"list\" to show the list of products");
        System.out.println("Type \"cart\" to show the total price of the cart");
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int inte;

        do
        {
            System.out.print("\nEnter Login Type:");
            System.out.print("\n\t1. For Admin:");
            System.out.print("\n\t2. For User:");
            System.out.print("\nEnter your choice: ");

            inte = sc.nextInt();
            switch (inte)
            {
                case 1:
                    try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/first_database";
                        String uname = "root";
                        String pass = "Enter your password";
                        Connection con = DriverManager.getConnection(url, uname, pass);
                        Statement s = con.createStatement();

                        String un1, password;
                        int abc = 1;
                        do
                        {
                            System.out.println("\nEnter user name: ");
                            un1 = sc.next();

                            System.out.println("\nEnter password: ");
                            password = sc.next();

                            loginAdmin admin = new loginAdmin(un1, password);
                            String sql = "SELECT * FROM admin WHERE user_name='" + un1 + "' && passkey='" + password + "'";
                            ResultSet rs = s.executeQuery(sql);
                            if (rs.next())
                            {
                                System.out.println("Login successful");
                                System.out.println(admin);
                                abc++;
                            }
                            else
                            {
                                System.out.println("Please check your login credentials");
                            }
                        } while (abc == 1);

                        System.out.println("Displaying customer details....");
                        Thread.sleep(500);

                        String query = "SELECT Costomer_id, Name, User_Name, Email_id, Address FROM first_database.costomer_info ";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);

                        while (rs.next())
                        {
                            String userData = rs.getInt(1) + " : " + rs.getString(2) + " : " + rs.getString(3) + " : " + rs.getString(4) + " : " + rs.getString(5);
                            System.out.println(userData);
                        }

                        st.close();
                        con.close();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    System.out.println("Enter-----------\n'1' to go back to the menu\n'2' to log out");
                    inte = sc.nextInt();
                    switch (inte)
                    {
                        case 2:
                            System.exit(0);
                    }
                    break;

                    case 2:
                        System.out.print("\nSignUp and Login:");
                        System.out.print("\n\t1. Creating a New Account:");
                        System.out.print("\n\t2. Login:");
                        System.out.print("\nEnter your choice: ");

                        int num = sc.nextInt();
                        switch (num)
                        {
                            case 1:
                            try
                            {
                                Class.forName("com.mysql.jdbc.Driver");
                                String url = "jdbc:mysql://localhost:3306/first_database";
                                String uname = "root";
                                String pass = "Enter your password";
                                Connection con = DriverManager.getConnection(url, uname, pass);
                                Statement s = con.createStatement();

                                String q1 = "INSERT into costomer_info(Name,User_Name,Email_id,Address,Password) values (?,?,?,?,?)";
                                String q2 = "INSERT into login(user_name,passkey) values (?,?)";
                                PreparedStatement pstmt = con.prepareStatement(q1);
                                PreparedStatement pstmt2 = con.prepareStatement(q2);

                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                System.out.println("\nEnter name: ");
                                String n = br.readLine();

                                String un;
                                int abc = 1;
                                do
                                {
                                    System.out.println("\nEnter user name: ");
                                    un = br.readLine();

                                    String sql = "SELECT * FROM costomer_info WHERE User_Name='" + un + "'";
                                    ResultSet rs = s.executeQuery(sql);
                                    if (rs.next())
                                    {
                                        System.out.println("User name already taken");
                                    }
                                    else
                                    {
                                        System.out.println("Username created");
                                        abc++;
                                    }
                                } while (abc == 1);

                                System.out.println("\nEnter email id: ");
                                String eid = br.readLine();

                                System.out.println("\nEnter address: ");
                                String addr = br.readLine();

                                System.out.println("\nSet password: ");
                                String password = br.readLine();

                                createNewAccount cna = new createNewAccount(n, un, password, eid, addr);

                                pstmt.setString(1, n);
                                pstmt.setString(2, un);
                                pstmt.setString(3, eid);
                                pstmt.setString(4, addr);
                                pstmt.setString(5, password);
                                pstmt.executeUpdate();

                                pstmt2.setString(1, un);
                                pstmt2.setString(2, password);
                                pstmt2.executeUpdate();

                                System.out.println(cna);
                                con.close();

                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            break;

                            case 2:
                                try
                                {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    String url = "jdbc:mysql://localhost:3306/first_database";
                                    String uname = "root";
                                    String pass = "Enter your password";
                                    Connection con = DriverManager.getConnection(url, uname, pass);
                                    Statement s = con.createStatement();

                                    String un1, password;
                                    int abc = 1;
                                    do
                                    {
                                        System.out.println("\nEnter user name: ");
                                        un1 = sc.next();

                                        System.out.println("\nEnter password: ");
                                        password = sc.next();

                                        login l = new login(un1, password);
                                        String sql = "SELECT * FROM login WHERE user_name='" + un1 + "' && passkey='" + password + "'";
                                        ResultSet rs = s.executeQuery(sql);

                                        if (rs.next())
                                        {
                                            System.out.println("Login successful");
                                            System.out.println(l);
                                            abc++;
                                        }
                                        else
                                        {
                                            System.out.println("Please check your login credentials");
                                        }
                                    } while (abc == 1);
                                    break;
                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        break;
            }
        }while (inte==1);

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("**********************************************");
        System.out.println("* Welcome to the World's best shopping store *");
        System.out.println("**********************************************");
        System.out.println();
        help();

        ShoppingStore shop = new ShoppingStore();
        shop.loadProducts();

        while (true)
        {
            String inputValue = scanner.next();

            if (inputValue.startsWith("add"))
            {
                int productId = -1;
                if (scanner.hasNextInt())
                {
                    productId = scanner.nextInt();

                    try
                    {
                        shop.addProductToBasket(productId);
                    } catch (IllegalArgumentException ex)
                    {
                        System.err.println(ex);
                    }
                }
                else
                {
                    System.out.println("Add command expects an int argument");
                }
            }
            else if (inputValue.startsWith("remove"))
            {
                int productId = -1;
                if (scanner.hasNext())
                {
                    productId = scanner.nextInt();
                    try
                    {
                        shop.removeProductFromBasket(productId);
                    } catch (IllegalArgumentException ex)
                    {
                        System.err.println(ex);
                    }
                }
                else
                {
                    System.out.println("Remove command expects an int argument");
                }
            }
            else if (inputValue.startsWith("list"))
            {
                shop.listProducts();
            }
            else if (inputValue.startsWith("cart"))
            {
                shop.getTotal();
                System.out.println("Would you like to proceed to checkout ?");
                System.out.print("\nEnter '1' to checkout\nEnter '2' to continue shopping\nEnter '3' to exit\nEnter your choice: ");
                int b = scanner.nextInt();
                if(b==1)
                {
                    System.out.print("\n\nChoose a payment method\nPress '1' for Card\nPress '2' for Cash on delivery\nEnter your choice: ");
                    int paychoice = scanner.nextInt();

                    switch (paychoice)
                    {
                        case 1:
                            payment p = new pay();
                            p.card();
                            System.exit(1);

                        case 2:
                            payment p1 = new pay();
                            p1.cash();
                            System.exit(1);
                    }
                }
                else if(b==3)
                {
                    System.out.println("You successfully logged out");
                    System.exit(1);
                }
            }
            else if ("H".equalsIgnoreCase(inputValue))
            {
                help();
            }
            else if ("Q".equalsIgnoreCase(inputValue))
            {
                break;
            }

        }
    }
}