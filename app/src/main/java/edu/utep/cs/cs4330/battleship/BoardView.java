package edu.utep.cs.cs4330.battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A special view class to display a battleship board as a2D grid.
 *
 * @see Board
 */
public class BoardView extends View {

    /** Callback interface to listen for board touches. */
    public interface BoardTouchListener {

        /**
         * Called when a place of the board is touched.
         * The coordinate of the touched place is provided.
         *
         * @param x 0-based column index of the touched place
         * @param y 0-based row index of the touched place
         */
        void onTouch(int x, int y);
    }

    /** Listeners to be notified upon board touches. */
    private final List<BoardTouchListener> listeners = new ArrayList<>();

    /** Board background color. */
    private final int boardColor = Color.rgb(102, 163, 255);

    /** Board background paint. */
    private final Paint boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        boardPaint.setColor(boardColor);
    }

    /** Board grid line color. */
    private final int boardLineColor = Color.WHITE;

    /** Board grid line paint. */
    private final Paint boardLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        boardLinePaint.setColor(boardLineColor);
        boardLinePaint.setStrokeWidth(2);
    }

    /** Board to be displayed by this view. */
    private Board board;

    /** Size of the board. */
    private int boardSize;

    /** Create a new board view to be run in the given context. */
    public BoardView(Context context) {
        super(context);
    }

    /** Create a new board view with the given attribute set. */
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** Create a new board view with the given attribute set and style. */
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** Set the board to to be displayed by this view. */
    public void setBoard(Board board) {
        this.board = board;
        this.boardSize = board.getSize();
        canShowShips = false; //@created Mahdokht Afravi on 03.05 U
        invalidate(); //@modified Mahdokht Afravi on 03.05 U
    }

    /**
     * Overridden here to detect a board touch. When the board is
     * touched, the corresponding place is identified,
     * and registered listeners are notified.
     *
     * @see BoardTouchListener
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int xy = locatePlace(event.getX(), event.getY());
                if (xy >= 0) {
                    notifyBoardTouch(xy / 100, xy % 100);
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /** Overridden here to draw a 2-D representation of the board. */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawPlaces(canvas);
    }

    //@created Mahdokht Afravi on 03.18 S
    /* Paint for hit on a Place with a Ship */
    private final Paint hitPaint = new Paint(Paint.ANTI_ALIAS_FLAG); {
        hitPaint.setColor(Color.RED);
    }
    /* Paint for hit on a Place without a Ship */
    private final Paint missPaint = new Paint(Paint.ANTI_ALIAS_FLAG); {
        missPaint.setColor(Color.WHITE);
    }
    /* Paint for Ship on a Place */
    private final Paint shipPaint = new Paint(Paint.ANTI_ALIAS_FLAG); {
        shipPaint.setColor(Color.GREEN);
    }
    public void setUserBoard(Board b) {
        board = b;
        boardSize = board.getSize();
        canShowShips = true;
        invalidate();
    }
    private boolean canShowShips;
    private int RADIUS;

    //@modified Mahdokht Afravi on 03.18 S
    /** Draw all the places of the board. */
    private void drawPlaces(Canvas canvas) {
        final float HALFLINE = lineGap()/2;
        for( int i=0 ; i<boardSize ; i++) {
            for( int j=0 ; j<boardSize ; j++) {
                float left = j*lineGap() + RADIUS;
                float top = i*lineGap() + RADIUS;
                if ( board.isHit(i,j) )
                    if ( board.hasShip(i,j) )
                        canvas.drawCircle(left, top, HALFLINE, hitPaint);
                    else
                        canvas.drawCircle(left, top, HALFLINE, missPaint);
                if ( canShowShips ) {
                    if ( board.hasShip(i,j) )
                        canvas.drawCircle(left, top, HALFLINE, shipPaint);
                }
            }
        }
    }

    //@author Mahdokht Afravi on 03.18 S
    /* Sets the radius to fit to screen */
    public void setRadius(int r) {
        RADIUS = r;
    }

    /** Draw horizontal and vertical lines. */
    private void drawGrid(Canvas canvas) {
        final float maxCoord = maxCoord();
        final float placeSize = lineGap();
        canvas.drawRect(0, 0, maxCoord, maxCoord, boardPaint);
        for (int i = 0; i < numOfLines(); i++) {
            float xy = i * placeSize;
            canvas.drawLine(0, xy, maxCoord, xy, boardLinePaint); // horizontal line
            canvas.drawLine(xy, 0, xy, maxCoord, boardLinePaint); // vertical line
        }
    }

    /** Calculate the gap between two horizontal/vertical lines. */
    protected float lineGap() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight()) / (float) boardSize;
    }

    /** Calculate the number of horizontal/vertical lines. */
    private int numOfLines() {
        return boardSize + 1;
    }

    /** Calculate the maximum screen coordinate. */
    protected float maxCoord() {
        return lineGap() * (numOfLines() - 1);
    }

    /**
     * Given screen coordinates, locate the corresponding place in the board
     * and return its coordinates; return -1 if the screen coordinates
     * don't correspond to any place in the board.
     * The returned coordinates are encoded as <code>x*100 + y</code>.
     */
    private int locatePlace(float x, float y) {
        if (x <= maxCoord() && y <= maxCoord()) {
            final float placeSize = lineGap();
            int ix = (int) (x / placeSize);
            int iy = (int) (y / placeSize);
            return ix * 100 + iy;
        }
        return -1;
    }

    /** Register the given listener. */
    public void addBoardTouchListener(BoardTouchListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /** Unregister the given listener. */
    public void removeBoardTouchListener(BoardTouchListener listener) {
        listeners.remove(listener);
    }

    /** Notify all registered listeners. */
    private void notifyBoardTouch(int x, int y) {
        for (BoardTouchListener listener: listeners) {
            listener.onTouch(x, y);
        }
    }
}
