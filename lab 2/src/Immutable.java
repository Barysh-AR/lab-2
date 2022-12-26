public class Immutable implements Matrix{

    private int row;
    private int column;
    private float[][] matrix;

    public Immutable(){matrix = new float[row][column];}
    public Immutable(int dimension) {
        if (dimension <= 0){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}

        row = dimension;
        column = dimension;
        matrix = new float[row][column];
    }
    public Immutable(int r, int c) {
        if ((r <= 0) || (c <= 0)){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = r;
        column = c;
        matrix = new float[row][column];
    }
    public Immutable(int dimension, String s) {
        if (dimension <= 0) {throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = dimension;
        column = dimension;
        matrix = new float[row][column];
        this.input_matrix(s);
    }
    public Immutable(int r, int c, String s) {
        if ((r <= 0) || (c <= 0)){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = r;
        column = c;
        matrix = new float[row][column];
        this.input_matrix(s);
    }
    public Immutable(Matrix matrix){
        this.row = matrix.size_row();
        this.column = matrix.size_column();
        this.matrix = new float[row][column];

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                this.matrix[i][j] = matrix.get(i,j);
            }
        }
    }
    private void input_matrix(String str){
        String[] elements = str.split(" ");
        int leght = elements.length;
        int n = 0;
        for(int i = 0; i < row; i++){

            for(int j = 0; j < column; j++){
                if (n < leght){
                    String el = elements[n];
                    n++;
                    try{
                        this.matrix[i][j] = Float.parseFloat(el);
                    }
                    catch (NumberFormatException e) {}
                }
                else{
                    this.matrix[i][j] = 0;
                }

            }
        }
    }
    public float get(int i, int j){
        if ((i >= row) || (j >= column) || (j < 0) || (i < 0)){
            throw new IndexOutOfBoundsException("Немає елемента з індексом ["+i+"x"+j+"]");
        }
        return this.matrix[i][j];
    }
    public float[] get_row( int n) {
        if ((n >= row) || (n < 0)){throw new IndexOutOfBoundsException("Немає рядка з індексом ["+n+"]");}
        float[] mat;
        mat = new float[this.column];
        for (int i = 0; i < this.column; i++) {
            mat[i] = this.matrix[n][i];
        }
        return mat;
    }
    public float[] get_column( int n){
        if ((n >= column) || (n < 0)){throw new IndexOutOfBoundsException("Немає стовбця з індексом ["+n+"]");}
        float[] mat;
        mat = new float[this.row];
        for(int i = 0; i < this.row; i++){
            mat[i] = this.matrix[i][n];
        }
        return mat;
    }
    public int size_row(){return row;}
    public int size_column(){return column;}

    public int hashCode() {return  31 * this.size_row() + this.size_column() ;}
    public boolean equals(Immutable mat) {
        if ((this.row == mat.row)||(this.column == mat.column)){
            for(int i = 0; i < row; i++){
                for(int j = 0; j < column; j++){
                    if (this.matrix[i][j] != mat.matrix[i][j]){
                        return false;
                    }
                }
            }
            return true;
        }
        else{return false;}
    }

    public Immutable multiply(Matrix mat){
        if ((this.column != mat.size_row()) || (this.equals(new Immutable()))){
            throw new IndexOutOfBoundsException("Некоректні розмірності матриць");
        }
        Immutable result = new Immutable(this.row, mat.size_column());
        for(int i = 0; i < result.row; i++){
            float [] row_m = this.get_row(i);
            for(int j = 0; j < result.column; j++){
                float [] column_m = mat.get_column(j);
                float sum = 0;
                for(int e = 0; e < this.column ; e++){
                    sum = sum + row_m[e] * column_m[e];
                }
                result.matrix[i][j] = sum;
            }
        }
        return result;
    }

    public Immutable identity (){
        if (this.row != this.column){throw new IndexOutOfBoundsException("Матриця повинна бути квадратною");}
        Mutable m = new Mutable(this);
        Immutable result = new Immutable(m.identity());
        return result;
    }

    public void print_inf(){
        System.out.println("-----");

        System.out.println(row + "x" + column);

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }
}