package concaro.hackernews.app.presentation.util.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Concaro on 2/17/2017.
 */

public class EndOffsetItemEventProfileDecoration extends RecyclerView.ItemDecoration {

    private int mOffsetPx;

    /**
     * Sole constructor. Takes in the size of the offset to be added to the end
     * of the RecyclerView.
     *
     * @param offsetPx The size of the offset to be added to the end of the
     *                 RecyclerView in pixels
     */
    public EndOffsetItemEventProfileDecoration(int offsetPx) {
        mOffsetPx = offsetPx;
    }

    /**
     * Determines the size and location of the offset to be added to the end
     * of the RecyclerView.
     *
     * @param outRect The {@link Rect} of offsets to be added around the child view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = state.getItemCount();
        if (parent.getChildAdapterPosition(view) == itemCount) {
            outRect.bottom = mOffsetPx;
        }
    }
}
