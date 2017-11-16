package matrixmult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Matrix {
    private int m, n;
    private int[][] matrix;

    public Matrix(int m, int n) {
	if(m<=0 || n<=0) {
	    matrix = null;
	    return;
	}
	this.m = m;
	this.n = n;
	matrix = new int[m][n];
    }
    
    public Matrix(Matrix other) {
	this.m = other.getM();
	this.n = other.getN();
	this.matrix = other.getMatrix();
    }
    
    public Matrix(int[][] arr) {
	this.m = arr.length;
	this.n = arr[0].length;
	this.matrix = arr;
    }
    
    public Matrix(String path) {
	List<String> lines;
	try {
	    lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
	} catch (IOException ex) {
	    lines = new ArrayList<>();
	}
	StringTokenizer st;
	String str = new String();
	st = new StringTokenizer(lines.toString(), " \n", false);

	m = Integer.parseInt(st.nextToken().substring(1));
	str = st.nextToken();
	n = Integer.parseInt(str.substring(0, str.length()-1));
	matrix = new int[m][n];
	
	for (int i = 0; i < m; i++){
	    for (int j = 0; j < n; j++) {
		str = st.nextToken();
		try {
		    matrix[i][j] = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		    matrix[i][j] = Integer.parseInt(str.substring(1));
		}
	    }
	    st.nextToken();
	}
    }
    
    void fillRandomly() {
	for (int i = 0; i < m; i++) {
	    for (int j = 0; j < n; j++) {
		matrix[i][j] = (int) (Math.random() * 100);
	    }
	}
    }
    
    Matrix multiply(Matrix factor) {
	Matrix res = new Matrix(getM(), factor.getN());
	for (int i = 0, rm = res.getM(); i < rm; i++) {
            for (int j = 0, rn = res.getN(); j < rn; j++) {
                for (int k = 0; k < n; k++) {
                    res.getMatrix()[i][j] += this.matrix[i][k] * factor.getMatrix()[k][j]; 
                }
            }
        }
	return res;
    }
    
    Matrix parallelMultiply(Matrix factor) {
	Matrix res = new Matrix(m, factor.getN());
	int cores = Runtime.getRuntime().availableProcessors();
	ExecutorService executor = Executors.newFixedThreadPool(cores);
	List<Future<Matrix>> list = new ArrayList<>();

	int part = m / cores;
	if (part < 1) {
	    part = 1;
	}
	for (int i = 0; i < m; i += part) {
	    Callable<Matrix> worker = new LineMultiplier(this, factor, i, i+part);
	    Future<Matrix> submit = executor.submit(worker);
	    list.add(submit);
	}

	int start = 0;
	Matrix futureMatrix;
	for (Future<Matrix> future : list) {
	    try {
		futureMatrix = future.get();
		for (int i=start; i < start+part; i++) {
		    res.setRow(i, futureMatrix.getMatrix()[i]);
		}
	    } catch (InterruptedException | ExecutionException e) {
	    }
	    start+=part;
	}
	executor.shutdown();

	return res;
    }

    @Override
    public String toString() {
	String res = new String();
	res += m + " " + n + "\n";
	for (int i = 0; i < m; i++) {
	    for (int j = 0; j < n; j++) {
		res += matrix[i][j] + " ";
	    }
	    res += "\n";
	}
	return res;
    } 
    
    public boolean equals(Matrix other) {
	boolean b = true;
	if(m == other.getM() && n == other.getN()) {
	    for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
		    if(matrix[i][j] != other.getMatrix()[i][j]) {
			b = false;
		    }
		}
	    }
	}
	else {
	    b = false;
	}
	return b;
    }
    
    /**
     * @return the m
     */
    public int getM() {
	return m;
    }

    /**
     * @return the n
     */
    public int getN() {
	return n;
    }

    /**
     * @return the matrix
     */
    public int[][] getMatrix() {
	return matrix;
    }

    public void setRow(int where, int[] row) {
	this.matrix[where] = row;
    }
    
    public void setElement(int i, int j, int elem) {
	this.matrix[i][j] = elem;
    }
}
