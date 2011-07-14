package matrixLib;

/**
 * A library of operations and tests for matrices that obey certain patterns
 * @author Bryan Cuccioli
 */

public class Pattern {

	/**
	 * Tells whether the matrix is upper triangular
	 * @param m the matrix to check
	 * @return whether the matrix is upper triangular or not
	 */
	public static boolean isUpperTriangular(Matrix m) {
		
		// the matrix must be square to be triangular
		if (m.rows() != m.cols()) {
			return false;
		}
		
		for (int i = 1; i < m.rows(); i++) {
			for (int j = 0; j < i; j++) {
				if (!m.getAt(i,j).isZero()) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Tells whether the matrix is lower triangular
	 * @param m the matrix to check
	 * @return whether the matrix is lower triangular or not
	 */
	public static boolean isLowerTriangular(Matrix m) {
		
		return isUpperTriangular(m.transpose());
	}
	
	/**
	 * Tells if the matrix is diagonal (upper and lower triangular) or not
	 * @param m the matrix to check
	 * @return whether the matrix is diagonal
	 */
	public static boolean isDiagonal(Matrix m) {
		
		// a matrix is diagonal if it is upper and lower triangular
		
		return isUpperTriangular(m) && isLowerTriangular(m);
	}

	/**
	 * Computes the upper Hessenberg form of the matrix via similarity forms
	 * based on Gauss-Jordan elimination
	 * @param m the matrix on which the algorithm is run
	 * @return the similar tridiagonal/Hessenberg matrix
	 * @throws NotSquareException the given matrix is not square
	 */
	public static Matrix hessenberg(Matrix m) {
		
		ComplexNumber[][] curr = m.getData(); // iterate over this matrix
		
		for (int r = 0; r < m.rows(); r++) {
			
			// find maximum magnitude in the rth col below the diagonal
			double largest = 0;
			int largest_row = 0;
			for (int i = r+1; i < m.rows(); i++) { // start below diagonal
				if (curr[i][r].abs() > largest) {
					largest = curr[i][r].abs();
					largest_row = i;
				}
			}
			
			if (largest != 0) {
				// interchange rows largest_row and r+1
				// pivoting procedure - increases numerical stability
				ComplexNumber temp;
				for (int j = 0; j < m.cols(); j++) {
					temp = curr[r+1][j];
					curr[r+1][j] = curr[largest_row][j];
					curr[largest_row][j] = temp;
				}
				// interchange cols largest_row and r+1 to make it a similarity transform
				for (int j = 0; j < m.rows(); j++) {
					temp = curr[j][r+1];
					curr[j][r+1] = curr[j][largest_row];
					curr[j][largest_row] = temp;
				}
				
				for (int i = r+2; i < m.rows(); i++) {
					ComplexNumber mult = curr[i][r].divide(curr[r+1][r]);
					// subtract mult*row r+1 from row i
					for (int j = 0; j < m.cols(); j++) {
						curr[i][j] = curr[i][j].subtract(curr[r+1][j].multiply(mult));
					}
					// add mult*col i to col r+1 to preserve similarity
					for (int j = 0; j < m.rows(); j++) {
						curr[j][r+1] = curr[j][r+1].add(curr[j][i].multiply(mult));
					}
				}
			}
		}
		
		return new Matrix(curr);
	}	


	/**
	 * Tells whether the matrix is symmetric or not
	 * @param m the matrix to check
	 * @return whether the matrix is symmetric
	 */
	public static boolean isSymmetric(Matrix m) {
		
		if (m.rows() != m.cols()) {
			return false; // only square matrices can be symmetric
		}
		
		// a symmetric matrix equals its transpose
		return (m.equals(m.transpose()));
	}
	
	/**
	 * Tells whether the matrix is anti-(skew-)symmetric or not,
	 * that is whether reflecting it over its diagonal is its negative
	 * @param m the matrix to check
	 * @return whether the mattrix is anti-symmetric
	 */
	public static boolean isAntiSymmetric(Matrix m) {
		
		if (m.rows() != m.cols()) {
			return false; // the matrix must be square to be antisymmetric
		}
		
		// an antisymmetric matrix equals the negative of its transpose
		return (m.equals(m.transpose().scale(-1)));
	}
	
	/**
	 * Tells whether the matrix is Hermetian/self-adjoint (equal to its own conjugate transpose)
	 * @param m the matrix to check
	 * @return whether the matrix is Hermetian
	 */
	public static boolean isHermetian(Matrix m) {
		
		if (m.rows() != m.cols()) {
			return false; // must be square to be Hermetian
		}
		
		return m.conjugateTranspose().equals(m);
	}

	/**
	 * Tells whether the matrix is orthogonal (A^T A = I) or not
	 * @param m the matrix to check
	 * @return whether the matrix is orthogonal 
	 */
	public static boolean isOrthogonal(Matrix m) {

		if (m.rows() != m.cols()) {
			return false; // must be square to be orthogonal
		}
		
		return isIdentity(m.multiply(m.transpose()));
	}
	
	/**
	 * Tells whether the matrix is unitary (its inverse is its conjugate transpose)
	 * @param m the matrix to check
	 * @return whether the matrix is unitary or not
	 */
	public static boolean isUnitary(Matrix m) {
		
		if (m.rows() != m.cols()) {
			return false; // must be square to be unitary
		}
		
		return isIdentity(m.multiply(m.conjugateTranspose()));
	}
	
	/**
	 * Tells whether the matrix is upper Hessenberg
	 * (whether it has zeros below the first subdiagonal)
	 * @param m the matrix to check
	 * @return whether the matrix is upper Hessenberg
	 */
	public static boolean isUpperHessenberg(Matrix m) {
		
		if (m.rows() != m.cols()) {
			return false; // must be square to be upper Hessenberg
		}
		
		for (int i = 2; i < m.rows(); i++) {
			for (int j = 0; j < i-1; j++) {
				if (!m.getAt(i,j).isZero()) {
					return false;
				}
			}
		}
		
		return true;
	}	

	/**
	 * Tells whether the matrix is the identity matrix
	 * @param m the matrix to check
	 * @return whether the matrix is the identity matrix
	 */
	public static boolean isIdentity(Matrix m) {
		
		if (m.rows() != m.cols()) {
			return false; // must be square to be the identity
		}
		
		for (int i = 0; i < m.rows(); i++) {
			for (int j = 0; j < m.cols(); j++) {
				if ((i != j && !m.getAt(i, j).equals(new ComplexNumber(0, 0)))
					|| (i == j && !m.getAt(i, j).equals(new ComplexNumber(1, 0)))) {
					return false;
				}
			}
		}
		
		return true;
	}
}