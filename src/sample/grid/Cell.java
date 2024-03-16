package sample.grid;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Collections;

public class Cell extends StackPane {

    int column;
    int row;
    CellType type;
    QueenType queenType;
    CellMark mark;
    Label labelMark;
    Label labelG;
    Label labelH;
    public Cell(String text, int column, int row, CellType type) {

        this.column = column;
        this.row = row;
        this.type = type;
        this.mark=CellMark.SAFE;
        this.queenType=QueenType.NOT_QUEEN;



        updateTypeStyle();

        labelMark = new Label();
        labelMark.getStyleClass().add("label-f");

        Label labelGoals = new Label(text);
        AnchorPane anchorPane = new AnchorPane();

        labelG = new Label("");
        AnchorPane.setTopAnchor(labelG, 3.);
        AnchorPane.setLeftAnchor(labelG, 3.);

        labelH = new Label("");
        AnchorPane.setTopAnchor(labelH, 3.);
        AnchorPane.setRightAnchor(labelH, 3.);

        anchorPane.getChildren().addAll(labelG, labelH);

        getChildren().addAll(anchorPane, labelMark,labelGoals);
    }



    public void setType(CellType type) {
        this.type = type;
        this.setLabelMark("");
        removeMark();
        updateTypeStyle();
    }

    public void setQueenType(QueenType queenType){
        this.queenType=queenType;
        updateQueenType();
    }

    public void removeMark() {
        this.mark = null;
        updateMarkStyle();
    }

    public void setMark(CellMark mark) {
        this.mark = mark;
        updateMarkStyle();
    }

    public String toString() {
        return this.column + "/" + this.row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isSafe() {
        return mark == CellMark.SAFE;
    }

    public void setLabelMark(String text) {
        labelMark.setText(text);
    }


    private void updateTypeStyle() {

        getStyleClass().remove("dark_chess");
        getStyleClass().remove("bright_chess");
        getStyleClass().add("boarder");
        if (labelMark!=null){
            setLabelMark("");
        }
        switch (type) {
            case DARK_CHESS:
                getStyleClass().add("dark_chess");
                break;
            case BRIGHT_CHESS:
                getStyleClass().add("bright_chess");
                break;
        }

    }

    private void updateMarkStyle() {
        setLabelMark("");
        if (mark == null)
            return;
        if (mark == CellMark.SAFE) {
            updateTypeStyle();
        }

    }
    private void updateQueenType() {
        setLabelMark("");
        getStyleClass().remove("dark_chess");
        getStyleClass().remove("bright_chess");
        if (queenType==null)
            return;
        switch (queenType){
            case QUEEN:
                setBackground(new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(0),
                                new Insets(0))),
                        Collections.singletonList(new BackgroundImage(
                                new Image(getClass().getResource("/sample/images/queen3.png").toExternalForm(), 50, 50, true, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false)
                        ))));
                break;
            case NOT_QUEEN:
                updateTypeStyle();
                updateMarkStyle();
        }
    }
}
