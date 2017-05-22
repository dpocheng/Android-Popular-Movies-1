package dpocheng.myappportfolio.proj0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import dpocheng.myappportfolio.proj1.MovieMainActivity;
import dpocheng.myappportfolio.R;

public class MainActivity extends AppCompatActivity {

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proj0_main_activity);

        LinearLayout buttonList = (LinearLayout) findViewById(R.id.button_list);
        for (int i = 0; i < buttonList.getChildCount(); i++) {
            final Button button = (Button) buttonList.getChildAt(i);
            if (i == 0) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MovieMainActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toast_msg = button.getContentDescription().toString();
                        onClickCreateToast(toast_msg);
                    }
                });
            }
        }
    }

    private void onClickCreateToast(String toast_msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
