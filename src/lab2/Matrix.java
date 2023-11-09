package lab2;

import java.util.Random;

public class Matrix {
    double[]data;
    int rows;
    int cols;
    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows*cols];
    }

    Matrix(double [][] d){
        int max = 0;
        for(double[] el: d){
            if(el.length > max){
                max = el.length;
            }
        }

        rows = d.length;
        cols = max;
        data = new double[rows * cols];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<d[i].length; j++){
                data[i*cols + j] = d[i][j];
            }
            for(int k = d[i].length; k < cols; k++){
                data[i*cols + k] = 0;
            }
        }
    }

    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
        for(int i=1; i<=n;i++){
            for(int j=1; j<=n; j++){
                if(i==j){
                    m.set(i,j,1);
                }
                else{
                    m.set(i,j,0);
                }
            }
        }
        return m;
    }

    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();
        for(int i=1; i<=rows; i++){
            for(int j=1; j<=rows; j++){
                m.set(i,j,r.nextDouble()*100);
            }
        }
        return m;
    }

    double get(int r, int c){
        if(r > this.rows | r < 0 | c > this.cols | c < 0){
            throw new RuntimeException("Invalid indexes");
        }
        return data[(r-1)*cols + c - 1];
    }

    void set(int r, int c, double value){
        if(r > this.rows | r < 0 | c > this.cols | c < 0){
            throw new RuntimeException("Invalid indexes");
        }
        data[(r-1)*cols + c - 1] = value;
    }

    double [][] asArray(){
        double[][] array = new double[rows][cols];
        for(int i=0; i<data.length; i++){
            array[i/cols][i % cols] = data[i];
        }
        return array;
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for(int i=1; i<=rows; i++){
            buf.append("[");
            for(int j=1; j<=cols; j++){
                buf.append(this.get(i, j));
                if(j == cols){break;}
                buf.append(" ");}
            buf.append("]");
            if(i == rows){break;}
            buf.append("\n");}
        buf.append("]");
        return buf.toString();
    }

    void reshape(int newRows,int newCols){
        if(rows*cols != newRows*newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        this.rows = newRows;
        this.cols = newCols;
    }

    int[] shape(){
        return new int[]{this.rows, this.cols};
    }

    Matrix add(Matrix m){
        if(m.rows != this.rows | m.cols != this.cols)
            throw new RuntimeException("Matrix shapes must match!");
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] + m.data[i];
        }
        return result;
    }

    Matrix sub(Matrix m){
        if(m.rows != this.rows | m.cols != this.cols)
            throw new RuntimeException("Matrix shapes must match!");
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] - m.data[i];
        }
        return result;
    }

    Matrix mul(Matrix m){
        if(m.rows != this.rows | m.cols != this.cols)
            throw new RuntimeException("Matrix shapes must match!");
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] * m.data[i];
        }
        return result;
    }

    Matrix div(Matrix m){
        if(m.rows != this.rows | m.cols != this.cols)
            throw new RuntimeException("Matrix shapes must match!");
        for(double el: m.data){
            if(el == 0){
                throw new RuntimeException("Dividing by zero is impossible!");
            }
        }

        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] / m.data[i];
        }
        return result;
    }

    Matrix add(double w){
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] + w;
        }
        return result;
    }

    Matrix sub(double w){
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] - w;
        }
        return result;
    }

    Matrix mul(double w){
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] * w;
        }
        return result;
    }

    Matrix div(double w){
        if(w == 0)
            throw new RuntimeException("Dividing by zero is impossible!");
        Matrix result = new Matrix(this.rows, this.cols);
        for(int i=0; i<data.length; i++){
            result.data[i] = this.data[i] / w;
        }
        return result;
    }

    Matrix dot(Matrix m){
        if(this.cols != m.rows)
            throw new RuntimeException("Number of rows of the first matrix must match number of columns of the second matrix!");
        Matrix result = new Matrix(this.rows, m.cols);
        double value;
        for(int i=1; i<=result.rows; i++){
            for(int j=1; j<=result.cols; j++){
                value = 0;
                for(int z=1; z<=this.cols; z++){
                   value += (this.get(i, z) * m.get(z, j));
            }
                result.set(i,j,value);
            }
    }
        return result;
    }

    double frobienius(){
        double sum = 0;
        for(double el: this.data){
            sum += Math.pow(el, 2);
        }
        return Math.sqrt(sum);
    }

    void swapRows(int r1, int r2){
        double temp;
        for(int i=1; i<=this.cols; i++){
            temp = this.get(r1,i);
            this.set(r1,i,this.get(r2,i));
            this.set(r2,i,temp);
        }
    }


    double determinant(){
        if(this.rows != this.cols){
            throw new RuntimeException("Determinant can be calculated only from square matrix!");
        }
        Matrix result = new Matrix(this.asArray());
        double coef = 1;
        double div;
        double val;
        for(int i=1; i<=result.cols; i++){
            if(Math.abs(result.get(i,i)) < Math.pow(10, -10)){
                for(int j=i+1; j<=result.rows; j++){
                    if(Math.abs(result.get(j,i)) > Math.pow(10,-10)){
                        result.swapRows(j, i);
                        coef *= -1;
                        break;}
                }}
            if(Math.abs(result.get(i,i)) < Math.pow(10, -10)){
                continue;
            }

            div = result.get(i, i);
            coef *= div;
            for (int j = i; j <= result.cols; j++) {
                val = result.get(i, j);
                result.set(i, j, val / div);}

            for(int z=i+1; z<=result.rows; z++){
                div = result.get(z,i)/result.get(i,i);
                for(int j=i; j<=result.cols; j++){
                    val = result.get(z,j) - div*result.get(i,j);
                    result.set(z,j,val);
            }
        }
        }
        double det = 1;
        for(int i = 1; i<=result.rows; i++){
            det *= result.get(i,i);
        }
        return det*coef;
    }
















}




