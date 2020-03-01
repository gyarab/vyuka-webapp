import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/run")
public class MyServlet extends HttpServlet {
    protected String dbUrl = System.getenv("JDBC_DATABASE_URL");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        String sql = "SELECT agent_name FROM agents";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            out.println("<table>");
            out.println("<tr><th>agent</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString(1) + "</td></tr>");
            }
            out.println("</table>");
        } catch (SQLException e) {
            out.println("getConnection(" + dbUrl + ") failed: " + e.getMessage());
        }

        out.println("</body></html>");
    }
}
