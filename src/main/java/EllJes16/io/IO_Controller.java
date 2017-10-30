package EllJes16.io;

import EllJes16.io.db.DBConnection;
import EllJes16.io.db.DB_IO;
import EllJes16.io.extern_input.FileHandler;
import EllJes16.io.extern_input.TextSplitter;
import EllJes16.io.extern_output.Printer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class IO_Controller
{
    private DB_IO db_io;
    private FileHandler fh;

    public IO_Controller(String propFile)
    {
        new DBConnection(propFile);
        db_io = new DB_IO();
        fh = new FileHandler();
    }

    public void createTables()
    {
        try
        {
            System.out.println("Creating tables...");
            db_io.createTables();
            System.out.println("Success!\n");
        } catch (SQLException e) {
            System.out.println("Failed...");
            e.printStackTrace();
        }
    }

    public void deleteAllTables()
    {
        try {
            System.out.println("Deleting tables...");
            db_io.deleteAllTables();
            System.out.println("Success!\n");
        } catch (SQLException e) {
            System.out.println("Failed...");
            e.printStackTrace();
        }
    }

    public int insertIntoTable(List<Object> inputs)
    {
        try
        {
            int value;

            System.out.println("Inserting into table...");
            value = db_io.insertIntoTable(inputs);
            System.out.println("Success!\n");

            return value;
        }
        catch (IllegalArgumentException | SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    // Todo: Make both print functions into get, returning a list of strings with the correct formatting
    public List<String> getTable(String table)
    {
        try
        {
            List<String> listString;
            System.out.println(String.format("Fetching table \"%s\"...\n", table));
            listString = db_io.getTable(table);
            return listString;
        }
        catch (IllegalArgumentException | SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getSpecifiedSubject(String subjectCode)
    {
        try
        {
            System.out.println(String.format("Retrieving information about subject with code: %s\n", subjectCode));
            return db_io.getSpecifiedSubject(subjectCode);
        }
        catch (SQLException e)
        {
            System.out.println(String.format("Failed: %s is not a valid subjectcode\n", subjectCode));
            e.printStackTrace();
            return null;
        }
    }

    public List<String> readInput(String txtInput)
    {
        try
        {
            List<String> listString;
            System.out.println("Reading file...");
            listString = fh.readInput(txtInput);
            System.out.println("Sucess!\n");

            return listString;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> split(List<String> listString)
    {
        List<Object> listObject;
        System.out.println("Splitting text into objects...");
        TextSplitter ts = new TextSplitter();
        listObject = ts.split(listString);
        System.out.println("Success!\n");

        return listObject;
    }

    public void print(String msg)
    {
        System.out.println("Printing out subject...");
        Printer.print(msg);
        System.out.print("\n");
    }

    public void print(List<String> msgs)
    {
        System.out.println("Printing out table...");
        Printer.print(msgs);
        System.out.print("\n");
    }
}