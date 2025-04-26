package Game.point;

/**
 * Lớp đại diện cho một điểm 2 chiều với tọa độ nguyên x và y.
 * Thường dùng trong các thuật toán di chuyển hoặc xử lý tọa độ.
 *
 * @author Bạn
 */
public class Point2D {

	private int x;
	private int y;

	/**
	 * Khởi tạo một điểm với tọa độ x và y.
	 *
	 * @param x hoành độ
	 * @param y tung độ
	 */
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** @return hoành độ x */
	public int getX() {
		return x;
	}

	/** @param x đặt lại hoành độ */
	public void setX(int x) {
		this.x = x;
	}

	/** @return tung độ y */
	public int getY() {
		return y;
	}

	/** @param y đặt lại tung độ */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * So sánh hai điểm xem có bằng nhau không.
	 *
	 * @param obj đối tượng so sánh
	 * @return true nếu x và y giống nhau
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Point2D point = (Point2D) obj;
		return x == point.x && y == point.y;
	}

	/**
	 * Sinh hashcode cho sử dụng trong Set hoặc HashMap.
	 */
	@Override
	public int hashCode() {
		return 31 * x + y;
	}
	/**
	 * Trả về chuỗi biểu diễn đối tượng Point2D.
	 */
	@Override
	public String toString() {
		return "Point2D[x=" + x + ", y=" + y + "]";
	}

	/**
	 * Tạo bản sao của điểm hiện tại
	 * @return một đối tượng Point2D mới có cùng tọa độ
	 */
	public Point2D copy() {
		return new Point2D(x, y);
	}

	/**
	 * Tính khoảng cách Manhattan giữa 2 điểm (nếu dùng trong game grid hoặc n-puzzle).
	 *
	 * @param other điểm cần so sánh
	 * @return khoảng cách Manhattan
	 */
	public int manhattanDistance(Point2D other) {
		return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
	}
}