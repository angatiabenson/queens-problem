package sample.algorithm;

import animatefx.animation.BounceIn;
import javafx.application.Platform;
import javafx.scene.control.Label;
import sample.grid.*;
import sample.model.CellModel;
import sample.model.GridModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class QueensProblemAlgorithm<T extends Cell> {
    private GridModel<T> gridModel = null;
    private Grid mGrid;
    private Stack<List<CellModel>> routes;
    private Stack<CellModel> queens;
    private boolean isRunning;
    private int currentColumn,currentRow;
    private CellModel currentQueen;
    private List<CellModel> currentQueenRoute;
    private CellModel backtrackedQueen;
    private List<CellModel> backtrackedQueenRoute;
    private boolean safeLocationFound,backtracked,isShowRoute;

    public void setUp(Grid mGrid){
        this.mGrid=mGrid;

        gridModel = new GridModel<>(mGrid.getColumns(), mGrid.getRows());

        for (int row = 0; row < mGrid.getRows(); row++) {
            for (int col = 0; col < mGrid.getColumns(); col++) {
                T cell = (T) mGrid.getCell(col, row);
                gridModel.setCell(cell, col, row, cell.isSafe());
            }
        }

        routes=new Stack<>();
        queens=new Stack<>();
        isRunning=false;
        safeLocationFound=true;
        backtracked=false;
        currentColumn=0;
        currentRow=0;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void solveQueensProblem(boolean isShowRoute, Label descriptionLabel){
        this.isShowRoute=isShowRoute;
        if (queens.size()!=mGrid.getColumns()){
            if (currentRow!=mGrid.getRows()){
                {
                    System.out.println("currentColumn:"+currentColumn+" :: currentRow:"+currentRow);
                    currentQueen =gridModel.getCell(currentColumn,currentRow);
                    safeLocationFound=isLocationSafe(currentQueen);
                    System.out.println("safeLocationFound: "+safeLocationFound);
                    if (safeLocationFound){
                        queens.push(currentQueen);
                        currentQueenRoute=gridModel.getRoute(currentQueen);
//                        System.out.println("routeSize: "+currentQueenRoute.size());
                        routes.push(currentQueenRoute);
                        ++currentColumn;
                        currentRow=0;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                descriptionLabel.setText("Queen "+queens.size()+" is safe");
                            }
                        });
                        System.out.println("Queen"+queens.size()+" safe");
                        System.out.println("********************************************************************");
                    }else{
                        ++currentRow;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int currentUnsafeQueen=queens.size()+1;
                                descriptionLabel.setText("Queen "+ currentUnsafeQueen +" is not safe");
                            }
                        });
//                        System.out.println("currentRow: "+currentRow+"== Total grid Rows: "+mGrid.getRows());
                        if (currentRow == mGrid.getRows()){
                            currentQueen=queens.peek();
                            backtrackedQueen=queens.peek();
                            backtrackedQueenRoute=routes.peek();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    descriptionLabel.setText("Backtracked to Queen "+queens.size());
                                        }
                            });
//                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@BacktrackedRoute: "+backtrackedQueenRoute.size());
                            routes.pop();
                            queens.pop();
                            currentColumn=currentQueen.getCol();
                            currentRow=currentQueen.getRow();
                            ++currentRow;
                            backtracked=true;
                        }
                    }

                }
            }else {
                currentQueen=queens.peek();
                backtrackedQueen=queens.peek();
                backtrackedQueenRoute=routes.peek();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        descriptionLabel.setText("Backtracked to Queen "+queens.size());
                    }
                });
                routes.pop();
                queens.pop();
                currentColumn=currentQueen.getCol();
                currentRow=currentQueen.getRow();
                ++currentRow;
                backtracked=true;
            }
        }else{
           Platform.runLater(() -> new NotificationWorker().notify("All "+queens.size()+" Queens are safe",mGrid)
                   .showInformation());
            isRunning=true;
            return;
        }
        Platform.runLater(this::showAnimation);
    }

    private void showAnimation() {
        if (backtracked){
            T cellObject= (T) backtrackedQueen.getObject();
                for (Cell cell : getBacktrackedQueenRoutSnapshot()) {
                    if (!isPathToAnotherQueen(cell)){
                        cell.setMark(CellMark.SAFE);
                        cell.setLabelMark("");
                    }
                }
                cellObject.setQueenType(QueenType.NOT_QUEEN);
                backtracked=false;

        }else{
            T cellObject= (T) currentQueen.getObject();
            if (safeLocationFound) {
                for (Cell cell : getQueenRoutSnapshot()) {
                    cell.setMark(CellMark.UNSAFE);
                    if (isShowRoute)
                        cell.setLabelMark(".");
                }
                cellObject.setQueenType(QueenType.QUEEN);
            }
        }
    }

    private boolean isLocationSafe(CellModel currentCell){
        if (!routes.isEmpty()){
            for (List<CellModel> route:routes) {
                if (route.contains(currentCell)){
                    return false;
                }
            }
        }
        return true;
    }

    private List<T> unmarshal(List<CellModel> marshaledRoute) {
        List<T> list = new ArrayList<>();
            for (CellModel cellModel : marshaledRoute) {
                T cellObject = (T) cellModel.getObject();
                list.add(cellObject);
            }
        return list;
    }

    private List<T> getQueenRoutSnapshot() {
        return unmarshal(currentQueenRoute);
    }
    private List<T> getBacktrackedQueenRoutSnapshot() {
        return unmarshal(backtrackedQueenRoute);
    }
    private boolean isPathToAnotherQueen(Cell cell){
        CellModel pathCell=gridModel.getCell(cell.getColumn(), cell.getRow());
        if (!routes.isEmpty()){
            for (List<CellModel> route:routes) {
                if (route.contains(pathCell)){
                    return true;
                }
            }
        }
        return false;
    }
}
