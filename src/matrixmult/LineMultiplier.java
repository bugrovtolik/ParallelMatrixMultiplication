package matrixmult;

import java.util.concurrent.Callable;

public class LineMultiplier implements Callable<Matrix> {
    private final Matrix f1, f2, res;
    private final int start, end, m, n;

    LineMultiplier(Matrix f1, Matrix f2, int s, int e) {
	this.f1 = f1;
	this.f2 = f2;
	this.m = f2.getM();
	this.n = f2.getN();
	this.res = new Matrix(f1.getM(), n);
	this.start = s;
	this.end = e;
    }

    @Override
    public Matrix call() {
	int[][] m1 = f1.getMatrix(), m2 = f2.getMatrix(), resm = res.getMatrix();
	try {
	for (int i = start; i < end; i++) {
	    for (int k = 0; k < m; k++) {
		for (int j = 0; j < n; j++) {
		    res.setElement(i, j, resm[i][j] + m1[i][k] * m2[k][j]);
		}
	    }
	}
	} catch(ArrayIndexOutOfBoundsException e) {
	}
	return res;
    }
}