import java.util.List;

public class Route {

	private List<Sight> sights;
	private int importance;

	public Route(List<Sight> sights, int importance) {
		this.sights = sights;
		this.importance = importance;
	}

	public List<Sight> getSights() {
		return sights;
	}

	public void setSights(List<Sight> sights) {
		this.sights = sights;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public void display() {
		if (sights != null && !sights.isEmpty()) {
			System.out.println("\nОптимальный маршрут");
			System.out.println("Выбранные достопримечательности :");

			double t = 0;
			for (Sight sight : sights) {
				System.out.println("- " + sight.toString());
				t += sight.getSpentTime() / 2.0;
			}

			System.out.println("Осталось времени: " + (48 - 16 - t) + " ч");
		}
	}
}
