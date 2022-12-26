

public class Mutable implements Matrix{

    private int row;
    private int column;
    private float[][] matrix;

    public Mutable(){matrix = new float[row][column];}
    public Mutable(int dimension) {
        if (dimension <= 0){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}

        row = dimension;
        column = dimension;
        matrix = new float[row][column];
    }
    public Mutable(int r, int c) {
        if ((r <= 0) || (c <= 0)){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = r;
        column = c;
        matrix = new float[row][column];
    }
    public Mutable(int dimension, String s) {
        if (dimension <= 0) {throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = dimension;
        column = dimension;
        matrix = new float[row][column];
        this.input_matrix(s);
    }
    public Mutable(int r, int c, String s) {
        if ((r <= 0) || (c <= 0)){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = r;
        column = c;
        matrix = new float[row][column];
        this.input_matrix(s);
    }
    public Mutable(Matrix matrix){
        this.row = matrix.size_row();
        this.column = matrix.size_column();
        this.matrix = new float[row][column];

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                this.matrix[i][j] = matrix.get(i,j);
            }
        }
    }
    public void input_matrix(String str){
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
    public void set_el(int i, int j, float el){
        if ((i >= row) || (j >= column) || (j < 0) || (i < 0)){
            throw new IndexOutOfBoundsException("Не може бути елемента з індексом ["+i+"x"+j+"]");
        }
        this.matrix[i][j] = el;
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
    public boolean equals(Mutable mat) {
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

    public Mutable multiply(Matrix mat){
        if ((this.column != mat.size_row()) || (this.equals(new Mutable()))){
            throw new IndexOutOfBoundsException("Некоректні розмірності матриць");
        }
        Mutable result = new Mutable(this.row, mat.size_column());
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

    private float deter(){
        if (this.row != this.column){throw new IndexOutOfBoundsException("Матриця повинна бути квадратною");}
        int dim = row;
        if (dim == 1) {
            return matrix[0][0];
        }
        float sum = 0;
        for(int i = 0; i < dim; i++){
            if ((i+1) % 2  != 0){
                sum = sum + matrix[0][i] * A(this, 0, i);
            }
            else {
                sum = sum - matrix[0][i] * A(this, 0, i);
            }
        }
        return sum;
    }
    private float A(Matrix mat, int row , int column){
        if (this.row != this.column){throw new IndexOutOfBoundsException("Матриця повинна бути квадратною");}
        Mutable m = get_A(mat, row, column);
        return m.deter();
    }
    private Mutable get_A(Matrix mat, int row , int column){
        int size = mat.size_row();

        Mutable m = new Mutable(size - 1);
        int r = 0, c = 0;
        for(int i = 0; i < size; i++){
            c = 0;
            if (i != row){
                for(int j = 0; j < size; j++){
                    if ( (j != column)){
                        m.matrix[r][c] =  mat.get(i,j);
                        c++;
                    }
                }
                r++;
            }
        }
        return m;
    }

    private Mutable U (){
        if (this.row != this.column){throw new IndexOutOfBoundsException("Матриця повинна бути квадратною");}
        int dim = row;
        float deter = this.deter();
        if (deter == 0){throw new IndexOutOfBoundsException("Детермінант = 0");}
        Mutable result = new Mutable(dim);

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if ((i+j) % 2  != 0) {result.matrix[i][j] = -1 * A(this, j ,i) / deter;}
                else {result.matrix[i][j] = A(this, j ,i) / deter;}
            }
        }
        return result;
    }

    public Mutable identity (){
        if (this.row != this.column){throw new IndexOutOfBoundsException("Матриця повинна бути квадратною");}
        return this.multiply(this.U());
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