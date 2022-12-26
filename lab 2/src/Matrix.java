public interface Matrix {
    public float get(int i, int j);
    public float[] get_row(int n);
    public float[] get_column(int n);
    public int size_row();
    public int size_column();
}