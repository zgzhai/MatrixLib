package matrixLib.test;

import matrixLib.ComplexNumber;
import matrixLib.Matrix;
import matrixLib.SquareMatrixOps;
import matrixLib.Pattern;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Library of test routines for the SquareMatrixOps class
 * @author Bryan Cuccioli
 */

public class SquareMatrixOpsTest {

	@Test public void det() {
		// set up matrices for a testing a variety of determinants
		double[][] f = {{1,2,3},{4,5,6},{7,8,7}};
		double[][] g = {{1,2},{3,4}};
		double[][] h = {{3,0,6,-3},{0,2,3,0},{-4,-7,2,0},{2,0,1,10}};
		ComplexNumber[][] i = {{new ComplexNumber(2,1),new ComplexNumber(3,-1),new ComplexNumber(4,-3)},
				{new ComplexNumber(4,0),new ComplexNumber(6,-1),new ComplexNumber(2,5)},
				{new ComplexNumber(0,3),new ComplexNumber(2,-1),new ComplexNumber(1,3)}};
		Matrix m = new Matrix(f);
		Matrix t = new Matrix(g);
		Matrix u = new Matrix(h);
		Matrix z = new Matrix(i);
		
		assertTrue(SquareMatrixOps.determinant(m).equals(new ComplexNumber(6, 0)));
		assertTrue(SquareMatrixOps.determinant(t).equals(new ComplexNumber(-2, 0)));
		assertTrue(SquareMatrixOps.determinant(new Matrix(9)).equals(new ComplexNumber(1, 0)));
		assertTrue(SquareMatrixOps.determinant(u).equals(new ComplexNumber(1404, 0)));
		assertTrue(SquareMatrixOps.determinant(z).equals(new ComplexNumber(-118,-84)));
	}
	
	@Test public void inverse() {
		double[][] mat = {{5,19},{1,4}};
		double[][] mat2 = {{1,2,3},{4,5,6},{7,8,9}};
		double[][] inv = {{4,-19},{-1,5}};
		
		//System.out.println(SquareMatrixOps.inverse(new Matrix(mat)));
		//ComplexNumber.setEpsilon(1e-13);
		//assertTrue(SquareMatrixOps.inverse(new Matrix(mat)).equals(new Matrix(inv)));
		//assertTrue(SquareMatrixOps.inverse(new Matrix(mat2)) == null);
	}
	
	@Test public void eigenvalues() {
		
		//double[][] mat = {{0,-1},{1,0}};
		double[][] mat = {{3,0,0},{1,3,1},{2,-1,1}};
		System.out.println(Pattern.hessenberg(new Matrix(mat)));
		//double[][] mat1 = {{2,1,-2},{-3,1,0},{4,3,1}};
		ComplexNumber[] evs = SquareMatrixOps.eigenvalues(new Matrix(mat));
		for (ComplexNumber ev : evs) {
			System.out.println(ev);
		}
	}
	
	@Test public void pow() {
		double[][] mat_arr = {{1,2},{3,4}}, matcube = {{37,54},{81,118}};
		Matrix mat = new Matrix(mat_arr);
		
		assertTrue(SquareMatrixOps.pow(mat,1).equals(mat));
		assertTrue(SquareMatrixOps.pow(mat,3).equals(new Matrix(matcube)));
		assertTrue(Pattern.isIdentity(SquareMatrixOps.pow(mat,0)));
	}
}
