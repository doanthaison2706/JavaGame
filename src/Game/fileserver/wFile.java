package Game.fileserver;

import java.io.*;
import java.util.ArrayList;

/**
 * Lớp hỗ trợ đọc và ghi dữ liệu từ/to file văn bản trong game N-Puzzle.
 * Dùng để nạp dữ liệu puzzle và lưu kết quả sau mỗi lần chơi.
 *
 * @author Bạn
 */
public class wFile {

	/** Đường dẫn file đầu vào chứa dữ liệu puzzle */
	public static final String FILE_INPUT_PATH = "/Users/doanthaison/Code Everything/N-Puzzle-main/DuLieu.txt";

	/** Đường dẫn file đầu ra để lưu kết quả chơi */
	public static final String FILE_OUTPUT_PATH = "/Users/doanthaison/Code Everything/N-Puzzle-main/KetQua.txt";

	/**
	 * Đọc nội dung từ file đầu vào và tách các số thành danh sách chuỗi.
	 *
	 * @return Danh sách các số đọc được từ file dưới dạng chuỗi.
	 */
	public static ArrayList<String> readFile() {
		ArrayList<String> list = new ArrayList<>();
		File fileIn = new File(FILE_INPUT_PATH);

		try (BufferedReader br = new BufferedReader(new FileReader(fileIn))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] strs = line.trim().split("\\s+");
				for (String s : strs) {
					if (!s.isEmpty()) {
						list.add(s);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Không tìm thấy file: " + fileIn.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Lỗi khi đọc file: " + fileIn.getAbsolutePath());
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Ghi kết quả chơi vào file đầu ra với định dạng: "time(s) - Count Clicks".
	 * @param time  Thời gian hoàn thành (tính bằng giây).
	 * @param count Số lượt nhấn (clicks).
	 */
	public static void writeFile(String time, String count) {
		File fileOut = new File(FILE_OUTPUT_PATH);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true))) {
			bw.write(time + "(s) - " + count + " Clicks\n");
		} catch (IOException e) {
			System.err.println("Lỗi khi ghi vào file: " + fileOut.getAbsolutePath());
			e.printStackTrace();
		}
	}
	/*
	 * Phần main chỉ để test tạm, có thể bỏ khi build chính thức
	 *
	 * public static void main(String[] args) {
	 *     ArrayList<String> a = readFile();
	 *     for (String s : a) {
	 *         System.out.println(s);
	 *     }
	 * }
	 */
}