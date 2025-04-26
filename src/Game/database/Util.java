package Game.database;

import java.sql.*;
import java.util.ArrayList;

public class Util {

	/** Đường dẫn tới driver SQL Server */
	public static String DRIVER_PATH = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/** Địa chỉ URL kết nối đến cơ sở dữ liệu SQL Server */
	public static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=BTLJAVA";
	public static final String DB_USER = "SA";
	public static final String DB_PASSWORD = "123456aA@$";
	/**
	 * Kết nối đến cơ sở dữ liệu SQL Server.
	 * @return đối tượng {@link Connection} nếu kết nối thành công, {@code null} nếu không thể kết nối.
	 */
	public static Connection dbConn() {
		try {
			// Tải driver SQL Server
			Class.forName(DRIVER_PATH);
			// Kết nối đến cơ sở dữ liệu
			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Chèn dữ liệu vào bảng `result` trong cơ sở dữ liệu SQL Server.
	 * @param time thời gian chơi (dưới dạng String)
	 * @param Count số lần nhấn chuột (dưới dạng String, được chuyển thành số nguyên)
	 */
	public static void insert(String time, String Count) {
		// Kiểm tra tham số đầu vào
		if (time == null || Count == null) {
			System.err.println("Invalid parameters!");
			return;
		}
		try (Connection c = dbConn();
			 PreparedStatement pt = c.prepareStatement("INSERT INTO result(Tg, Clicks) VALUES(?,?)")) {

			// Chuyển Count thành số nguyên
			int C = Integer.parseInt(Count);
			// Cài đặt tham số cho câu lệnh SQL
			pt.setString(1, time);
			pt.setInt(2, C);
			// Thực thi câu lệnh
			pt.executeUpdate();
			System.out.println("Data inserted successfully");

		} catch (SQLException e) {
			// Xử lý lỗi SQL
			System.err.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Lấy danh sách các ID từ bảng `Combobox` trong cơ sở dữ liệu và lưu vào {@link ArrayList}.
	 * @return danh sách các ID dưới dạng {@link ArrayList<String>}
	 */
	public static ArrayList<String> loadDataToCombobox() {
		ArrayList<String> list = new ArrayList<>();
		// Câu lệnh SQL để lấy dữ liệu từ bảng Combobox
		String sql = "SELECT ID FROM Combobox";

		try (Connection c = dbConn();
			 Statement st = c.createStatement();
			 ResultSet rs = st.executeQuery(sql)) {

			// Duyệt qua kết quả và thêm các ID vào danh sách
			while (rs.next()) {
				list.add(rs.getString("ID"));
				System.out.println(rs.getString("ID"));
			}

		} catch (SQLException e) {
			// Xử lý lỗi SQL
			System.err.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}

		return list;
	}
}