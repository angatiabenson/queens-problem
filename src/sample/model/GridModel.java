package sample.model;


import java.util.ArrayList;
import java.util.List;

public class GridModel<T> {

    CellModel<T>[][] gridCells;
    int cols;
    int rows;

    public GridModel(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        gridCells = new CellModel[rows][cols];
    }

    public void setCell(T cell, int col, int row, boolean safe) {
        gridCells[row][col] = new CellModel<T>(col, row, safe, cell);
    }

    public CellModel<T> getCell(int col, int row) {
        return gridCells[row][col];
    }

    public List<CellModel<T>> getRoute(CellModel<T> cell) {
        List<CellModel<T>> route=new ArrayList<>();
        //getting horizontal-right route
        int constantRow= cell.getRow();
        int varyingColumn= cell.getCol();
        for (int col = varyingColumn; col <cols ; col++) {
            route.add(gridCells[constantRow][col]);
//            System.out.println("HR-r: row="+constantRow+" col="+col);
        }

        //getting horizontal-left route
        --varyingColumn;
        for (int col = varyingColumn; col >=0 ; col--) {
            route.add(gridCells[constantRow][col]);
//            System.out.println("HR-l: row="+constantRow+" col="+col);
        }

        //getting vertical-down route
        int constantColumn= cell.getCol();
        int varyingRow= cell.getRow();
        ++varyingRow;
        for (int row = varyingRow; row <rows ; row++) {
            route.add(gridCells[row][constantColumn]);
//            System.out.println("VR-d: row="+row+" col="+constantColumn);
        }
        //getting vertical-up route
        varyingRow= cell.getRow();
        --varyingRow;
        for (int row = varyingRow; row >=0 ; row--) {
            route.add(gridCells[row][constantColumn]);
//            System.out.println("VR-u: row="+row+" col="+constantColumn);
        }

        //getting diagonal-right-down route
        int currentRow= cell.getRow();
        int currentColumn=cell.getCol();
        ++currentColumn;
        ++currentRow;
        for (int row = currentRow; row <rows ; row++) {
               if (currentColumn!=cols){
                   route.add(gridCells[row][currentColumn]);
//                   System.out.println("DI-r-d: row="+row+" col="+currentColumn);
               }else{
                   break;
               }
            ++currentColumn;
        }

        //getting diagonal-left-down route
        currentRow= cell.getRow();
         currentColumn=cell.getCol();
        --currentColumn;
        ++currentRow;
           for (int row = currentRow; row <rows ; row++) {
               if (currentColumn>=0){
                   route.add(gridCells[row][currentColumn]);
//                   System.out.println("DI-l-d: row="+row+" col="+currentColumn);
               }else{
                   break;
               }
               --currentColumn;
       }

        //getting diagonal-left-up route
        currentRow= cell.getRow();
        currentColumn=cell.getCol();
        --currentColumn;
        --currentRow;
            for (int row = currentRow; row >=0 ; row--) {
                if (currentColumn>=0){
                    route.add(gridCells[row][currentColumn]);
//                    System.out.println("DI-l-u: row="+row+" col="+currentColumn);
                }else{
                    break;
                }
                    --currentColumn;
            }



        //getting diagonal-right-up route
        currentRow= cell.getRow();
        currentColumn=cell.getCol();
        ++currentColumn;
        --currentRow;
        if (currentRow>=0){
            for (int row = currentRow; row >=0 ; row--) {
                if (currentColumn!=cols){
                    route.add(gridCells[row][currentColumn]);
//                    System.out.println("DI-r-u: row="+row+" col="+currentColumn);
                }else{
                    break;
                }
                ++currentColumn;


            }
        }

        return route;
    }

}