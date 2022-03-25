public class hu1 {

	public static void main(String[] args) {

		int a[] = new int[999];
		int s = 0;
		for (int i = 1; i <= 1000; i++) {
			for (int x = i; x <= 1000; x++) {
				s = s + x;
				a[x - i] = x;
				if (s >= 1000) {
					if (s == 1000) {
						for (int j = 0; j <= x - i; j++)
							System.out.print(" " + a[j]);
						System.out.println("");
					}
					a = new int[1000];
					s = 0;
					continue;
				}

			}
		}
	}
}