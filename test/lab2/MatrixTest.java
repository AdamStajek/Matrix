package lab2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Random;

class MatrixTest {



    @Test
    void Matrix() {
        Matrix m = new Matrix(3,4);
        assertEquals(3, m.rows,.1);
        assertEquals(4, m.cols,.1);
        double [][] array = {{1,2},{4,5,6},{7}};
        Matrix x = new Matrix(array);
        double [][] xar = x.asArray();
        assertEquals(xar[0][2], 0,.1);
        assertEquals(xar[2][2], 0,.1);
        for(double[] row: xar){
            assertEquals(row.length, x.cols,.1);
        }

    }

    @Test
    void eye() {
        Matrix a = Matrix.eye(3);
        assertEquals(a.frobienius(), Math.sqrt(3),.1);
    }

    @Test
    void get() {
        double[][] array = {{1,2,3},{4,5,6},{7,8,9}};
        Matrix m = new Matrix(array);
        assertEquals(m.get(1,1), 1,.1);
        assertEquals(m.get(m.rows, m.cols), 9,.1);
        assertEquals(m.get(2,3), 6,.1);
    }

    @Test
    void set() {
        double[][] array = {{1,2,3},{4,5,6},{7,8,9}};
        Matrix m = new Matrix(array);
        Random r = new Random();
        double a = r.nextInt(), b = r.nextInt(), c = r.nextInt();
        m.set(1,1,a);
        m.set(m.rows,m.cols, b);
        m.set(2,3, c);
        assertEquals(m.get(1,1), a,.1);
        assertEquals(m.get(m.rows, m.cols), b,.1);
        assertEquals(m.get(2,3), c,.1);

    }

    @Test
    void asArray() {
        double [][] array = {{1,2},{3,4},{5,6},{7,8}};
        Matrix m = new Matrix(array);
        for(int i=0; i<m.rows; i++){
            assertArrayEquals(array[i], m.asArray()[i]);
        }
    }

    @Test
    void testToString() {
        int left=0, right=0, blank=0;
        char c;
        Matrix m = Matrix.random(5,5);
        String matrix = m.toString();
        for(int i=0; i<matrix.length(); i++){
            c = matrix.charAt(i);
            switch (c) {
                case '[' -> left++;
                case ']' -> right++;
                case ' ' -> blank++;
            }
        }
        assertEquals(left, m.rows + 1,.1);
        assertEquals(right, m.rows + 1,.1);
        assertEquals(blank, m.rows * (m.cols - 1),.1);
        }


    @Test
    void reshape() {
        Exception exception = assertThrows(RuntimeException.class, () ->{
            Matrix m = new Matrix(3,3);
            m.reshape(4,4);
        });
        String expectedMessage = "3 x 3 matrix can't be reshaped to 4 x 4";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void add() {
        Exception exception = assertThrows(RuntimeException.class, () ->{
            Matrix a = Matrix.random(3,3);
            Matrix b = Matrix.random(4,4);
            a.add(b);
        });
        String expectedMessage = "Matrix shapes must match!";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        Matrix a = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
        Matrix b = new Matrix(new double[][] {{4,7,9},{3,2,1},{3,4,1}});
        Matrix expected_matrix = new Matrix(new double [][] {{5,9,12},{7,7,7},{10,12,10}});
        Matrix result_matrix = a.add(b);
        assertTrue(result_matrix.sub(expected_matrix).frobienius() < Math.pow(10,-6));

        double x = 5;
        Matrix expected_double =  new Matrix(new double [][] {{6,7,8},{9,10,11},{12,13,14}});
        Matrix result_double = a.add(x);
        assertTrue(result_double.sub(expected_double).frobienius() < Math.pow(10,-6));



    }

    @Test
    void sub() {
        Exception exception = assertThrows(RuntimeException.class, () ->{
            Matrix a = Matrix.random(3,3);
            Matrix b = Matrix.random(4,4);
            a.sub(b);
        });
        String expectedMessage = "Matrix shapes must match!";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        Matrix a = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
        Matrix b = new Matrix(new double[][] {{4,7,9},{3,2,1},{3,4,1}});
        Matrix expected_matrix = new Matrix(new double [][] {{-3,-5,-6},{1,3,5},{4,4,8}});
        Matrix result_matrix = a.sub(b);
        assertTrue(result_matrix.sub(expected_matrix).frobienius() < Math.pow(10,-6));

        double x = 5;
        Matrix expected_double =  new Matrix(new double [][] {{-4,-3,-2},{-1,0,1},{2,3,4}});
        Matrix result_double = a.sub(x);
        assertTrue(result_double.sub(expected_double).frobienius() < Math.pow(10,-6));
    }

    @Test
    void mul() {
        Exception exception = assertThrows(RuntimeException.class, () ->{
            Matrix a = Matrix.random(3,3);
            Matrix b = Matrix.random(4,4);
            a.mul(b);
        });
        String expectedMessage = "Matrix shapes must match!";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        Matrix a = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
        Matrix b = new Matrix(new double[][] {{4,7,9},{3,2,1},{3,4,1}});
        Matrix expected_matrix = new Matrix(new double [][] {{4,14,27},{12,10,6},{21,32,9}});
        Matrix result_matrix = a.mul(b);
        assertTrue(result_matrix.sub(expected_matrix).frobienius() < Math.pow(10,-6));

        double x = 5;
        Matrix expected_double =  new Matrix(new double [][] {{5,10,15},{20,25,30},{35,40,45}});
        Matrix result_double = a.mul(x);
        assertTrue(result_double.sub(expected_double).frobienius() < Math.pow(10,-6));
    }

    @Test
    void div() {
        Exception exception = assertThrows(RuntimeException.class, () ->{
            Matrix a = Matrix.random(3,3);
            Matrix b = Matrix.random(4,4);
            a.div(b);
        });
        String expectedMessage = "Matrix shapes must match!";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        Exception exception2 = assertThrows(RuntimeException.class, () ->{
            Matrix a = Matrix.random(3,3);
            Matrix b = Matrix.random(3,3);
            b.set(1,1,0);
            a.div(b);
        });
        String expectedMessage2 = "Dividing by zero is impossible!";
        String actualMessage2 = exception2.getMessage();
        assertEquals(expectedMessage2, actualMessage2);


        Matrix a = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
        Matrix b = new Matrix(new double[][] {{4,7,9},{3,2,1},{3,4,1}});
        Matrix expected_matrix = new Matrix(new double [][] {
                {1.0/4, 2.0/7,3.0/9},{ 4.0/3, 5.0/2, 6.0},{ 7.0/3,  8.0/4, 9.0}});
        Matrix result_matrix = a.div(b);
        assertTrue(result_matrix.sub(expected_matrix).frobienius() < Math.pow(10,-6));

        double x = 5;
        Matrix expected_double = new Matrix(new double [][] {
                {1.0/5, 2.0/5,3.0/5},{ 4.0/5, 5.0/5, 6.0/5},{ 7.0/5,  8.0/5, 9.0/5}});
        Matrix result_double = a.div(x);
        assertTrue(result_double.sub(expected_double).frobienius() < Math.pow(10,-6));
    }

    @Test
    void dot() {
        Exception exception = assertThrows(RuntimeException.class, () ->{
            Matrix a = Matrix.random(3,3);
            Matrix b = Matrix.random(4,4);
            a.dot(b);
        });
        String expectedMessage = "Number of rows of the first matrix must match number of columns of the second matrix!";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        Matrix a = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
        Matrix b = new Matrix(new double[][] {{4,7,9},{3,2,1},{3,4,1}});
        Matrix expected_matrix = new Matrix(new double [][] {{19,23,14},{49,62,47},{79,101,80}});
        Matrix result_matrix = a.dot(b);
        assertTrue(result_matrix.sub(expected_matrix).frobienius() < Math.pow(10,-6));

    }

    @Test
    void frobienius() {
        Matrix a = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
        assertEquals(a.frobienius(), Math.sqrt(285));
    }

    @Test
    void determinant(){
        Exception exception = assertThrows(RuntimeException.class, () ->{
            Matrix a = Matrix.random(3,6);
            a.determinant();
        });
        String expectedMessage = "Determinant can be calculated only from square matrix!";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        Matrix a = new Matrix(new double [][] {{4,5,6},{3,2,1},{77,6,4}});
        assertEquals(a.determinant(), -483,.1);
    }
}