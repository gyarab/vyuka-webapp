import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/init")
public class MyInitServlet extends HttpServlet {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");

    String[] initData = {
            "DROP TABLE IF EXISTS orders",
            "DROP TABLE IF EXISTS customers",
            "DROP TABLE IF EXISTS agents",
            "CREATE TABLE agents (agent_code VARCHAR PRIMARY KEY, agent_name VARCHAR NOT NULL, commission NUMERIC(10,2))",
            "CREATE TABLE customers (cust_code VARCHAR PRIMARY KEY, cust_name VARCHAR NOT NULL, grade NUMERIC(10,0) NOT NULL, agent_code VARCHAR NOT NULL REFERENCES agents(agent_code))",
            "CREATE TABLE orders (ord_num NUMERIC(6,0) PRIMARY KEY, ord_amount NUMERIC(12,2) NOT NULL, ord_date DATE NOT NULL, cust_code VARCHAR NOT NULL REFERENCES customers(cust_code), agent_code VARCHAR NOT NULL REFERENCES agents(agent_code))",
            "INSERT INTO agents VALUES ('A007', 'Ramasundar', 0.15)",
            "INSERT INTO agents VALUES ('A003', 'Alex', 0.13)",
            "INSERT INTO agents VALUES ('A008', 'Alford', 0.12)",
            "INSERT INTO agents VALUES ('A011', 'Ravi Kumar', 0.15)",
            "INSERT INTO agents VALUES ('A010', 'Santakumar', 0.14)",
            "INSERT INTO agents VALUES ('A012', 'Lucida', 0.12)",
            "INSERT INTO agents VALUES ('A005', 'Anderson', 0.13)",
            "INSERT INTO agents VALUES ('A001', 'Subbarao', 0.14)",
            "INSERT INTO agents VALUES ('A002', 'Mukesh', 0.11)",
            "INSERT INTO agents VALUES ('A006', 'McDen', 0.15)",
            "INSERT INTO agents VALUES ('A004', 'Ivan', 0.15)",
            "INSERT INTO agents VALUES ('A009', 'Benjamin', 0.11)",
            "INSERT INTO customers VALUES ('C00013', 'Holmes', 2, 'A003')",
            "INSERT INTO customers VALUES ('C00001', 'Micheal', 2, 'A008')",
            "INSERT INTO customers VALUES ('C00020', 'Albert', 3, 'A008')",
            "INSERT INTO customers VALUES ('C00025', 'Ravindran', 2, 'A011')",
            "INSERT INTO customers VALUES ('C00024', 'Cook', 2, 'A006')",
            "INSERT INTO customers VALUES ('C00015', 'Stuart', 1, 'A003')",
            "INSERT INTO customers VALUES ('C00002', 'Bolt', 3, 'A008')",
            "INSERT INTO customers VALUES ('C00018', 'Fleming', 2, 'A005')",
            "INSERT INTO customers VALUES ('C00021', 'Jacks', 1, 'A005')",
            "INSERT INTO customers VALUES ('C00019', 'Yearannaidu', 1, 'A010')",
            "INSERT INTO customers VALUES ('C00005', 'Sasikant', 1, 'A002')",
            "INSERT INTO customers VALUES ('C00007', 'Ramanathan', 1, 'A010')",
            "INSERT INTO customers VALUES ('C00022', 'Avinash', 2, 'A002')",
            "INSERT INTO customers VALUES ('C00004', 'Winston', 1, 'A005')",
            "INSERT INTO customers VALUES ('C00023', 'Karl', 0, 'A006')",
            "INSERT INTO customers VALUES ('C00006', 'Shilton', 1, 'A004')",
            "INSERT INTO customers VALUES ('C00010', 'Charles', 3, 'A009')",
            "INSERT INTO customers VALUES ('C00017', 'Srinivas', 2, 'A007')",
            "INSERT INTO customers VALUES ('C00012', 'Steven', 1, 'A012')",
            "INSERT INTO customers VALUES ('C00008', 'Karolina', 1, 'A004')",
            "INSERT INTO customers VALUES ('C00003', 'Martin', 2, 'A004')",
            "INSERT INTO customers VALUES ('C00009', 'Ramesh', 3, 'A002')",
            "INSERT INTO customers VALUES ('C00014', 'Rangarappa', 2, 'A001')",
            "INSERT INTO customers VALUES ('C00016', 'Venkatpati', 2, 'A007')",
            "INSERT INTO customers VALUES ('C00011', 'Sundariya', 3, 'A010')",
            "INSERT INTO orders VALUES (200100, 1000.00, '2008-01-08', 'C00015', 'A003')",
            "INSERT INTO orders VALUES (200110, 3000.00, '2008-04-15', 'C00019', 'A010')",
            "INSERT INTO orders VALUES (200107, 4500.00, '2008-08-30', 'C00007', 'A010')",
            "INSERT INTO orders VALUES (200112, 2000.00, '2008-05-30', 'C00016', 'A007')",
            "INSERT INTO orders VALUES (200113, 4000.00, '2008-06-10', 'C00022', 'A002')",
            "INSERT INTO orders VALUES (200102, 2000.00, '2008-05-25', 'C00012', 'A012')",
            "INSERT INTO orders VALUES (200114, 3500.00, '2008-08-15', 'C00002', 'A008')",
            "INSERT INTO orders VALUES (200122, 2500.00, '2008-09-16', 'C00003', 'A004')",
            "INSERT INTO orders VALUES (200118, 500.00, '2008-07-20', 'C00023', 'A006')",
            "INSERT INTO orders VALUES (200119, 4000.00, '2008-09-16', 'C00007', 'A010')",
            "INSERT INTO orders VALUES (200121, 1500.00, '2008-09-23', 'C00008', 'A004')",
            "INSERT INTO orders VALUES (200130, 2500.00, '2008-07-30', 'C00025', 'A011')",
            "INSERT INTO orders VALUES (200134, 4200.00, '2008-09-25', 'C00004', 'A005')",
            "INSERT INTO orders VALUES (200115, 2000.00, '2008-02-08', 'C00013', 'A010')",
            "INSERT INTO orders VALUES (200108, 4000.00, '2008-02-15', 'C00008', 'A004')",
            "INSERT INTO orders VALUES (200103, 1500.00, '2008-05-15', 'C00021', 'A005')",
            "INSERT INTO orders VALUES (200105, 2500.00, '2008-07-18', 'C00025', 'A011')",
            "INSERT INTO orders VALUES (200109, 3500.00, '2008-07-30', 'C00011', 'A010')",
            "INSERT INTO orders VALUES (200101, 3000.00, '2008-07-15', 'C00001', 'A008')",
            "INSERT INTO orders VALUES (200111, 1000.00, '2008-07-10', 'C00020', 'A008')",
            "INSERT INTO orders VALUES (200104, 1500.00, '2008-03-13', 'C00006', 'A004')",
            "INSERT INTO orders VALUES (200106, 2500.00, '2008-04-20', 'C00005', 'A002')",
            "INSERT INTO orders VALUES (200125, 2000.00, '2008-10-10', 'C00018', 'A005')",
            "INSERT INTO orders VALUES (200117, 800.00, '2008-10-20', 'C00014', 'A001')",
            "INSERT INTO orders VALUES (200123, 500.00, '2008-09-16', 'C00022', 'A002')",
            "INSERT INTO orders VALUES (200120, 500.00, '2008-07-20', 'C00009', 'A002')",
            "INSERT INTO orders VALUES (200116, 500.00, '2008-07-13', 'C00010', 'A009')",
            "INSERT INTO orders VALUES (200124, 500.00, '2008-06-20', 'C00017', 'A007')",
            "INSERT INTO orders VALUES (200126, 500.00, '2008-06-24', 'C00022', 'A002')",
            "INSERT INTO orders VALUES (200129, 2500.00, '2008-07-20', 'C00024', 'A006')",
            "INSERT INTO orders VALUES (200127, 2500.00, '2008-07-20', 'C00015', 'A003')",
            "INSERT INTO orders VALUES (200128, 3500.00, '2008-07-20', 'C00009', 'A002')",
            "INSERT INTO orders VALUES (200135, 2000.00, '2008-09-16', 'C00007', 'A010')",
            "INSERT INTO orders VALUES (200131, 900.00, '2008-08-26', 'C00012', 'A012')",
            "INSERT INTO orders VALUES (200133, 1200.00, '2008-06-29', 'C00009', 'A002')",
            "INSERT INTO orders VALUES (200132, 4000.00, '2008-08-15', 'C00013', 'A010')"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();) {
            for (String sql : initData) {
                stmt.execute(sql);
            }

            out.println("<html><body>Databaze inicializována...</body></html>");
        } catch (SQLException e) {

            out.println("<html><body>Chyba: " + e.getMessage()+ "</body></html>");
        }

    }
}