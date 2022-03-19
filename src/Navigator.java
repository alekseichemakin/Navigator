import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Navigator {

	private Sight[] sights;
	private int freeTime;
	private static final int TIME_IN_TRIP = 48;
	private static final int TIME_TO_SLEEP = 16;
	private static final String FILE_PATH = "./places.txt"; // should be [name importance time]

	public Navigator(Sight[] items, int freeTime) {
		this.sights = items;
		this.freeTime = freeTime * 2;
	}

	public void display() {
		if (sights != null && sights.length > 0) {
			System.out.println("Планы на поездку");
			System.out.println("Свободное время : " + freeTime + " ч.");
			System.out.println("Достопримечательности :");

			for (Sight sight : sights) {
				System.out.println("- " + sight.toString());
			}
		}
	}

	public Route solve() {
		int NB_ITEMS = sights.length;
		int[][] matrix = new int[NB_ITEMS + 1][freeTime + 1];

		for (int i = 0; i <= freeTime; i++) {
			matrix[0][i] = 0;
		}

		for (int i = 1; i <= NB_ITEMS; i++) {
			for (int j = 0; j <= freeTime; j++) {
				if (sights[i - 1].getSpentTime() > j) {
					matrix[i][j] = matrix[i - 1][j];
				}
				else {
					matrix[i][j] = Math.max(matrix[i - 1][j],
							matrix[i - 1][j - sights[i - 1].getSpentTime()] + sights[i - 1].getImportance());
				}
			}
		}

		int res = matrix[NB_ITEMS][freeTime];
		int time = freeTime;
		List<Sight> itemsSolution = new ArrayList<>();

		for (int i = NB_ITEMS; i > 0 && res > 0; i--) {
			if (res != matrix[i - 1][time]) {
				itemsSolution.add(sights[i - 1]);
				res -= sights[i - 1].getImportance();
				time -= sights[i - 1].getSpentTime();
			}
		}

		return new Route(itemsSolution, matrix[NB_ITEMS][freeTime]);
	}

	private static Sight[] parseFile(String filePath) throws RuntimeException {
		List<Sight> sights = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))  {
			while (reader.ready()) {
				String[] params = reader.readLine().split(" ");
				String name = Arrays.stream(params).limit(params.length - 2).collect(Collectors.joining(" "));
				sights.add(new Sight(name, Integer.parseInt(params[params.length - 2]), Double.parseDouble(params[params.length - 1])));
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error: File not found");
		} catch (IOException e) {
			throw new RuntimeException("Error: File IO error");
		} catch (NumberFormatException e) {
			throw new RuntimeException("Error: File data error");
		}

		return sights.toArray(new Sight[0]);
	}

	public static void main(String[] args) {
		Sight[] sights;

		try {
			sights = parseFile(FILE_PATH);
		}
		catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}

		Navigator navigator = new Navigator(sights, TIME_IN_TRIP - TIME_TO_SLEEP);
		navigator.display();
		Route route = navigator.solve();
		route.display();
	}
}