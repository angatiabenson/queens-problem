package sample.model;

public class CellModel<T>{

    int col;
    int row;
    boolean isSafe;
    T obj;

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public CellModel(int col, int row, boolean isSafe, T obj) {
        this.col = col;
        this.row = row;
        this.isSafe = isSafe;
        this.obj = obj;
    }


    public T getObject() {
        return obj;
    }

}
