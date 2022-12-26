

public class Gmatrix {
    private int row;
    private int column;
    private Elem[][] matrix;
    public Gmatrix(){matrix = new Elem[row][column];}
    public Gmatrix(int dimension) {
        if (dimension <= 0){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}

        row = dimension;
        column = dimension;
        matrix = new Elem[row][column];
    }
    public Gmatrix(int r, int c) {
        if ((r <= 0) || (c <= 0)){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = r;
        column = c;
        matrix = new Elem[row][column];
    }
    public Gmatrix(int dimension, String s) {
        if (dimension <= 0) {throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = dimension;
        column = dimension;
        matrix = new Elem[row][column];
        this.input_matrix(s);
    }
    public Gmatrix(int r, int c, String s) {
        if ((r <= 0) || (c <= 0)){throw new IndexOutOfBoundsException("Розмірність повинна бути більша 0");}
        row = r;
        column = c;
        matrix = new Elem[row][column];
        this.input_matrix(s);
    }
    public Gmatrix(Matrix matrix){
        this.row = matrix.size_row();
        this.column = matrix.size_column();
        this.matrix = new Elem[row][column];

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                this.matrix[i][j] = new Elem(matrix.get(i,j));
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
                        this.matrix[i][j] = new Elem(Float.parseFloat(el));
                    }
                    catch (NumberFormatException e) {
                        this.matrix[i][j] = new Elem(el);
                    }
                }
                else{
                    this.matrix[i][j] = new Elem(0);
                }
            }
        }
    }


    public void set_el(int i, int j, float el){
        if ((i >= row) || (j >= column) || (j < 0) || (i < 0)) {throw new IndexOutOfBoundsException("Некоректний індекс");}
        this.matrix[i][j] = new Elem(el);}
    public void set_el(int i, int j, char el){
        if ((i >= row) || (j >= column) || (j < 0) || (i < 0)) {throw new IndexOutOfBoundsException("Некоректний індекс");}
        this.matrix[i][j] = new Elem(el);}
    public void set_el(int i, int j, String el){
        if ((i >= row) || (j >= column) || (j < 0) || (i < 0)) {throw new IndexOutOfBoundsException("Некоректний індекс");}
        this.matrix[i][j] = new Elem(el);}

    public Elem get(int i, int j){
        if ((i >= row) || (j >= column) || (j < 0) || (i < 0)){
            throw new IndexOutOfBoundsException("Немає елемента з індексом ["+i+"x"+j+"]");
        }
        return this.matrix[i][j];
    }


    public Elem[] get_row( int n) {
        if ((n >= row) || (n < 0)){throw new IndexOutOfBoundsException("Немає рядка з індексом ["+n+"]");}
        Elem[] mat;
        mat = new Elem[this.column];
        for (int i = 0; i < this.column; i++) {
            mat[i] = this.matrix[n][i];
        }
        return mat;
    }
    public Elem[] get_column( int n){
        if ((n >= column) || (n < 0)){throw new IndexOutOfBoundsException("Немає рядка з індексом ["+n+"]");}
        Elem[] mat;
        mat = new Elem[this.row];
        for(int i = 0; i < this.row; i++){
            mat[i] = this.matrix[i][n];
        }
        return mat;
    }
    public int size_row(){return row;}
    public int size_column(){return column;}

    public int hashCode() {return  31 * this.size_row() + this.size_column() ;}
    public boolean equals(Gmatrix mat) {
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

    public Gmatrix multiply(Gmatrix mat){
        if ((this.column != mat.size_row()) || (this.equals(new Gmatrix()))){
            throw new IndexOutOfBoundsException("Некоректні розмірності матриць");
        }
        Gmatrix result = new Gmatrix(this.row, mat.size_column());
        for(int i = 0; i < result.row; i++){
            Elem [] row_m = this.get_row(i);
            for(int j = 0; j < result.column; j++){
                Elem [] column_m = mat.get_column(j);
                Elem sum = new Elem();
                for(int e = 0; e < this.column ; e++){
                    sum = sum.add(row_m[e].multiply(column_m[e]));
                }
                result.matrix[i][j] = sum;
            }
        }
        return result;
    }

    public Gmatrix identity (){
        if (this.row != this.column){throw new IndexOutOfBoundsException("Матриця повинна бути квадратною");}
        Gmatrix result = new Gmatrix(this.row);
        for(int i = 0; i < result.row; i++){
            for(int j = 0; j < result.column; j++){
                if (i == j){
                    result.matrix[i][j] = new Elem(1);
                }
                else {
                    result.matrix[i][j] = new Elem(0);
                }
            }
        }
        return result;
    }

    public void print_inf(){
        System.out.println("-----");

        System.out.println(row + "x" + column);

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                matrix[i][j].show();
                System.out.print( " ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }
}

class Elem {
    private String str;
    private float nomber;
    Elem (){
        str = null;
        nomber = 0;
    }
    Elem (String str){
        this.str = str;
        nomber = 0;
    }
    Elem (char c){
        this.str = String.valueOf(c);
        nomber = 0;
    }
    Elem (float f){
        this.nomber = f;
        str = null;
    }

    public void set_value(String str){
        this.str = str;
        nomber = 0;
    }
    public void set_value(char c){
        this.str = String.valueOf(c);
        nomber = 0;
    }
    public void set_value(float f){
        this.nomber = f;
        str = null;
    }


    private String get_str(){
        return this.str;
    }
    private float get_nomber(){
        return this.nomber;
    }

    public Elem add(Elem elem){
        Elem result = new Elem();

        if(this.str == null){
            if ((elem.str == null)) {result.set_value(this.nomber + elem.nomber);}
            else {result.set_value(Float.toString(this.nomber) + elem.str);}
        }
        else {
            if (elem.str == null) {result.set_value(this.str + Float.toString(elem.nomber));}
            else {result.set_value(this.str + elem.str);}
        }
        return result;
    }
    public Elem multiply(Elem elem){
        Elem result = new Elem();

        if(this.str == null){
            if ((elem.str == null)) {result.set_value(this.nomber * elem.nomber);}
            else {result.set_value(Float.toString(this.nomber) +"*"+ elem.str);}

        }
        else {
            if (elem.str == null) {result.set_value(this.str +"*"+ Float.toString(elem.nomber));}
            else {result.set_value(this.str +"*"+ elem.str);}
        }
        return result;
    }
    public void show(){
        if(this.str == null){
            System.out.print(this.nomber);
        }
        else {
            System.out.print(this.str);
        }
    }
    public Elem copy(){
        Elem result = new Elem();
        result.str = this.str;
        result.nomber = this.nomber;
        return result;
    }
}