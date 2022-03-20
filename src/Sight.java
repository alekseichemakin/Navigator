public class Sight {

	private String name;
	private int spentTime;
	private int importance;

	public Sight(String name, int importance, double spentTime) {
		this.name = name;
		this.spentTime = (Double.valueOf(spentTime * 2).intValue()); // spent time can be 0.5, for getting integer multiply on 2
		this.importance = importance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpentTime() {
		return spentTime;
	}

	public void setSpentTime(int spentTime) {
		this.spentTime = spentTime;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	@Override
	public String toString() {
		return "Затрачиваемое время = " + (double)spentTime / 2 + " ч" +
				", Важность = " + importance + " " +  name;
	}
}
