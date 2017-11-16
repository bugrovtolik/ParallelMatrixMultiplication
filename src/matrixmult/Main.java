package matrixmult;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
	int n, m, k;
	try (Scanner s = new Scanner(System.in)) {
	    n = s.nextInt();
	    m = s.nextInt();
	    k = s.nextInt();
	}
	
	Matrix a = new Matrix(n, m);
	Matrix b = new Matrix(m, k);
	
	a.fillRandomly();
	b.fillRandomly();
	
	Matrix ab;
	long start = System.nanoTime();
	ab = a.multiply(b);
	long finish = System.nanoTime();
	
	System.out.println(finish - start + " (" + (finish - start) / 1000000000.0 + " sec)");
	
	/*try {
	    Files.write(Paths.get("src\\matrixmult\\matrixA.txt"), a.toString().getBytes(StandardCharsets.UTF_8));
	    Files.write(Paths.get("src\\matrixmult\\matrixB.txt"), b.toString().getBytes(StandardCharsets.UTF_8));
	    Files.write(Paths.get("src\\matrixmult\\matrixAB.txt"), ab.toString().getBytes(StandardCharsets.UTF_8));
	} catch (IOException e) {
	}*/
	
	Matrix pa = new Matrix("src\\matrixmult\\matrixA.txt");
	Matrix pb = new Matrix("src\\matrixmult\\matrixB.txt");
	Matrix ab2 = new Matrix("src\\matrixmult\\matrixAB.txt");
	
	start = System.nanoTime();
	Matrix pab = a.parallelMultiply(b);
	finish = System.nanoTime();
	
	System.out.println(finish - start + " (" + (finish - start) / 1000000000.0 + " sec)");
	System.out.println(pab.equals(ab));
    }
}
