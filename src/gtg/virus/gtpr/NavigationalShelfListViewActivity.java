package gtg.virus.gtpr;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NavigationalShelfListViewActivity extends ActionBarActivity {

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
        int numRow = 20;
        int numCol = 3;

        TableLayout tblLayout = (TableLayout) findViewById(R.id.tblLayout);

        for(int i = 0; i < numRow; i++) {
/*            HorizontalScrollView HSV = new HorizontalScrollView(this);
            HSV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
*/
            TableRow tblRow = new TableRow(this);
            tblRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tblRow.setBackgroundResource(R.drawable.shelf);
            tblRow.setWeightSum(3);

            
            for(int j = 0; j < numCol; j++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.ic_launcher);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT , 1f));

                TextView textView = new TextView(this);
                textView.setText("Java Tester");
                textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                textView.setBackgroundResource(R.drawable.ic_launcher);
                
                            
            	
                tblRow.addView(imageView);
                //tblRow.addView(textView);
                
            }


          /*  HSV.addView(tblRow);*/
            
            tblLayout.addView(tblRow, i);
        }
	}

	
}
