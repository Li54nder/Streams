package v3_osobe;
import java.util.List;
/**@author Aleksa*/
public class TableFromLists {
	
	public static String rowFormater(List<String> list, int j) {
		String line = "| ";
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).length() == 1)
				line += (tmpF(list.get(i), j) + " | ");
			else {
				try {
					Double.parseDouble(list.get(i));
					line += (tmpF(list.get(i), j) + " | ");
				} catch (Exception e) {
					line += (tmpF(list.get(i), j) + " | ");
				}
			}
		}
		return line;
	}

	/**default width for columns is 15 characters*/
	public static String rowFormater(List<String> list) {
		return rowFormater(list, 15);
	}

	/**printer with default width*/
	public static void formaterRowPrinter(List<String> list) {
		formaterRowPrinter(list, 15);
	}
	
	/**printer with costume width*/
	public static void formaterRowPrinter(List<String> list, int j) {
		System.out.println(rowFormater(list, j));
	}
	
	/**formating one item in row*/
	private static String tmpF(String text, int j) {
		if (text.length()==1 && !text.equals("0")) return String.format("%"+j+"s", text).replace(" ", ""+text);
		try {
			int tmp = Integer.parseInt(text);
			return String.format("%"+j+"d", tmp);
		} catch (Exception e) {}
		try {
			//if text is a number...
			double tmp = Double.parseDouble(text);
			return String.format("%"+j+".2f", tmp);
		} catch (Exception e) {}
		return String.format("%-"+j+"s", text);
	}

}
