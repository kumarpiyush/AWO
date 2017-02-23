package github.kumarpiyush.awo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_display);

        Intent callingIntent = getIntent();
        String errorMessage = callingIntent.getStringExtra(Constants.errorDisplayMessageKey);

        TextView errorView = new TextView(this);
        errorView.setText(errorMessage);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(errorView);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_error_display);
        layout.addView(scrollView);
    }
}