/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixmult;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author PC
 */
public class MatrixTest {
    /**
     * Test of multiply method, of class Matrix.
     */
    @Test
    public void testMultiply() {
	System.out.println("multiply");
	int[][] mA = 
            {{33,34,12},
             {33,19,10},
             {12,14,17},
             {84,24,51},
             {43,71,21}};
        int[][] mB = 
            {{10,11,34,55},
             {33,45,17,81},
             {45,63,12,16}};
	int[][] exp = 
	    {{1992,2649,1844,4761},
	    {1407,1848,1565,3514},
	    {1347,1833,850,2066},
	    {3927,5217,3876,7380},
	    {3718,4991,2921,8452}};
	Matrix instance = new Matrix(mA);
	Matrix factor = new Matrix(mB);
	Matrix expResult = new Matrix(exp);
	Matrix result = instance.multiply(factor);
	Assert.assertArrayEquals(expResult.getMatrix(), result.getMatrix());
    }
    
    /**
     * Test of parallel multiply method, of class Matrix.
     */
    @Test
    public void testParallelMultiply() {
	System.out.println("parallel multiply");
	int[][] mA = 
            {{33,34,12},
             {33,19,10},
             {12,14,17},
             {84,24,51},
             {43,71,21}};
        int[][] mB = 
            {{10,11,34,55},
             {33,45,17,81},
             {45,63,12,16}};
	int[][] exp = 
	    {{1992,2649,1844,4761},
	    {1407,1848,1565,3514},
	    {1347,1833,850,2066},
	    {3927,5217,3876,7380},
	    {3718,4991,2921,8452}};
	Matrix instance = new Matrix(mA);
	Matrix factor = new Matrix(mB);
	Matrix expResult = new Matrix(exp);
	Matrix result = instance.parallelMultiply(factor);
	Assert.assertArrayEquals(expResult.getMatrix(), result.getMatrix());
    }
}
