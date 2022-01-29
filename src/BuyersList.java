class ItemNode {

	public int item;
	public ItemNode next;

	public ItemNode(int item) {
		this.item = item;
		this.next = null;
	}
}

class ItemsList {
	private int nbNodes;
	private ItemNode first;
	private ItemNode last;

	public ItemsList() {
		this.first = null;
		this.last = null;
		this.nbNodes = 0;
	}

	public int size() {
		return nbNodes;
	}

	public boolean empty() {
		return first == null;
	}

	public boolean contains(ItemsList ilist) {
		
		int counter = 0;
		ItemNode itemnode = this.first.next;
		ItemNode ilistnode = ilist.first.next;
		
		while (ilistnode != null) {
			while (itemnode != null) {
				if (itemnode.item == ilistnode.item) {
					counter++;
				}
				itemnode = itemnode.next;
			}
			ilistnode = ilistnode.next;
			itemnode = this.first.next;
		}
		return counter == ilist.size();
	}

	public int append(int item) {
		
		ItemNode itemnode = new ItemNode(item);
		
		if (empty() == true) {
			first = new ItemNode(item);
		} 
		else {
			last = new ItemNode(item);
		}
		itemnode.next = null;
		ItemNode last = first;
		while (last.next != null) {
			last = last.next;
		}
		last.next = itemnode;
		return nbNodes++;
	}

	public void remove(ItemsList ilist) {

		ItemNode current = this.first.next;
		ItemNode previous = null;
		ItemNode key = ilist.first.next;

		while (key != null) {
			if (current != null && current.item == key.item) {
				this.first.next = current.next;
				return;
			}
			while (current != null && current.item != key.item) {
				previous = current;
				current = current.next;
			}
			if (current == null) {
				return;
			}
			previous.next = current.next;
			current = this.first.next;
			previous = null;
			key = key.next;
		}

	}
}

class BuyerNode {

	public int id;
	public int value;
	public ItemsList itemsList;
	public BuyerNode next;

	public BuyerNode(int id, int value, ItemsList ilist) {
		this.id = id;
		this.value = value;
		this.itemsList = ilist;
	}
}

class BuyersList {
	public int opt;
	private int nbNodes;
	private BuyerNode first;
	private BuyerNode last;

	public BuyersList() {
		this.first = null;
		this.last = null;
		this.nbNodes = 0;
	}

	public int readFile(String filename) {

		int m = 0;
		java.io.BufferedReader br = null;

		try {
			br = new java.io.BufferedReader(new java.io.FileReader(filename));

			// Read dimensions
			String line = br.readLine();
			String[] data = line.split(" ");
			// Number of items
			m = Integer.parseInt(data[0]);
			// Number of buyers
			int n = Integer.parseInt(data[1]);
			// Optimum revenue
			this.opt = Integer.parseInt(data[2]);

			// Read Buyers Information
			int id = 0;
			while ((line = br.readLine()) != null) {
				data = line.split(" ");
				// Read value
				int value = Integer.parseInt(data[0]);
				// Read Item List
				ItemsList itemsList = new ItemsList();
				for (int i = 1; i < data.length; i++)
					itemsList.append(Integer.parseInt(data[i]));
				// Insert new buyer
				this.append(id++, value, itemsList);
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (java.io.IOException ex) {
				ex.printStackTrace();
			}
		}
		return m;
	}

	public boolean empty() {
		return first == null;
	}

	public int size() {
		return nbNodes;
	}

	public int totalValue() {
		
	 
		int total = 0;
		BuyerNode itemnode = this.first.next;
		
		while (itemnode != null) {
			total += itemnode.value;
			itemnode = itemnode.next;
		}
		return total;
	}

	public int append(int id, int value, ItemsList ilist) {

		BuyerNode buyernode = new BuyerNode(id, value, ilist);
		
		if (empty() == true) {
			first = new BuyerNode(id, value, ilist);
		} 
		else {
			last = new BuyerNode(id, value, ilist);
		}
		buyernode.next = null;
		BuyerNode last = first;
		while (last.next != null) {
			last = last.next;
		}
		last.next = buyernode;
		return nbNodes++;
	}

	public BuyersList greedy(int m) {

		int i;
		ItemsList TotalItems = new ItemsList();
		BuyersList FinalBuyersList = new BuyersList();
		BuyerNode buyernode = this.first.next;
		
		for (i = 0; i < m; i++) {
			TotalItems.append(i);
		}
		while (buyernode != null) {
			if (TotalItems.contains(buyernode.itemsList)) {
				TotalItems.remove(buyernode.itemsList);
				FinalBuyersList.append(buyernode.id, buyernode.value, buyernode.itemsList);
			}
			buyernode = buyernode.next;
		}
		return FinalBuyersList;
	}

	public static void main(String[] args) {

		int i;
		long start, end, total = 0, avgTime;
		int files[] = new int[10];
		BuyersList buyersList = new BuyersList();

		// Διαβάζουμε κάθε αρχείο ξεχωριστά, τρέχουμε την μέθοδο greedy
		// 15 φορές και χρονομετράμε την διάρκεια εκτέλεσής του. 
		// Παράλληλα εκτυπώνουμε κατάλληλο μήνυμα.
		
 		files[0] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\m500\\m500\\p500x1000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[0]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[0] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "   greedy value = " + buyersList.greedy(files[0]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[1] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\m500\\m500\\p500x3000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[1]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[1] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "   greedy value = " + buyersList.greedy(files[1]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[2] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\m500\\m500\\p500x5000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[2]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[2] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "   greedy value = " + buyersList.greedy(files[2]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[3] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\m500\\m500\\p500x7000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[3]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[3] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "   greedy value = " + buyersList.greedy(files[3]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[4] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\m500\\m500\\p500x9000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[4]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[4] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "   greedy value = " + buyersList.greedy(files[4]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[5] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\n2000\\n2000\\p200x2000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[5]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[5] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "   greedy value = " + buyersList.greedy(files[5]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[6] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\n2000\\n2000\\p400x2000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[6]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[6] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "   greedy value = " + buyersList.greedy(files[6]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[7] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\n2000\\n2000\\p600x2000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[7]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[7] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "  greedy value = " + buyersList.greedy(files[7]).totalValue() + "   opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[8] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\n2000\\n2000\\p800x2000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[8]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[8] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "  greedy value = " + buyersList.greedy(files[8]).totalValue() + "  opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
		
		files[9] = buyersList.readFile(
				"C:\\Users\\Dell\\Desktop\\Υλικό 1ης Εργασίας (2019-2020)\\Yliko 1is Ergasias (2019-2020)\\n2000\\n2000\\p1000x2000.txt");
		for (i = 0; i < 14; i++) {
			start = System.nanoTime();
			buyersList.greedy(files[9]);
			end = System.nanoTime();
			total += end - start;
		}
		avgTime = total / 15;
		System.out.println("* m = " + files[9] + ":\n- n = " + buyersList.size() + "  avgTime = " + avgTime
				+ "  greedy value = " + buyersList.greedy(files[9]).totalValue() + "  opt value = " + buyersList.opt
				+ "\n........................................................................");
		total = buyersList.nbNodes = 0;
	}
}
