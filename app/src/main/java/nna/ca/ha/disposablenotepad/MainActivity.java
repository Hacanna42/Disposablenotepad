package nna.ca.ha.disposablenotepad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText editText;
    LinearLayout layout;
    String buofbu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainfragment);
        editText = findViewById(R.id.notepad);
        layout = findViewById(R.id.Layout);

        editText.requestFocus();

        SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);

        //첫 시작 도움말
        if (pref.getBoolean("tutorial", true)) {
            SharedPreferences.Editor editor = pref.edit();
            editText.setHint("옆으로 스와이프 : 작성 내용 삭제\n밑으로 스와이프 : 삭제한 작성 내용 복원\n다시 밑으로 스와이프 : 복원 전 내용 복원\n위로 스와이프 : 테마 변경\n개발자 : dtmdgus901@gmail.com");
            editor.putBoolean("tutorial", false);
            editor.apply();
        }

        //테마 설정
        if (pref.getString("theme", "white").equals("black")) {
            editText.setTextColor(Color.parseColor("#FFFFFF"));
            editText.setHintTextColor(Color.parseColor("#ABABAB"));
            layout.setBackgroundColor(Color.parseColor("#000000"));
        }

        //이전 내용 복구
        if (pref.getString("save", "").length() > 0) {
            editText.setText(pref.getString("save", ""));
        }

        //레이아웃에 할당
        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (pref.getString("theme", "white").equals("white")) {
                    editText.setTextColor(Color.parseColor("#FFFFFF"));
                    editText.setHintTextColor(Color.parseColor("#ABABAB"));
                    layout.setBackgroundColor(Color.parseColor("#000000"));
                    editor.putString("theme", "black");
                    editor.apply();
                } else {
                    editText.setTextColor(Color.parseColor("#000000"));
                    editText.setHintTextColor(Color.parseColor("#ABABAB"));
                    layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    editor.putString("theme", "white");
                    editor.apply();
                }
            }

            public void onSwipeRight() {
                //지울 내용을 백업
                if (editText.getText().length() > 0) {
                    SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("backup", editText.getText().toString());
                    editor.remove("save");
                    editor.apply();
                    editText.setText("");
                    editText.setHint("되돌릴려면 아래로 스와이프.");
                }
            }

            public void onSwipeLeft() {
                //지울 내용을 백업
                if (editText.getText().length() > 0) {
                    SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("backup", editText.getText().toString());
                    editor.remove("save");
                    editor.apply();
                    editText.setText("");
                    editText.setHint("되돌릴려면 아래로 스와이프.");
                }
            }

            public void onSwipeBottom() {
                SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (pref.getString("backup", "").length() > 0) {
                    buofbu = editText.getText().toString();
                    editText.setText(pref.getString("backup", ""));
                    editor.remove("backup");
                    editor.apply();
                    editText.setHint("화면을 탭해 작성을 시작하세요.");
                } else if (buofbu.length() > 0) {
                    editor.putString("backup", editText.getText().toString());
                    editor.apply();
                    editText.setText(buofbu);
                    buofbu = "";
                } else {
                    Toast.makeText(MainActivity.this, "되돌릴 노트가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //EditText에 할당
        editText.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (pref.getString("theme", "white").equals("white")) {
                    editText.setTextColor(Color.parseColor("#FFFFFF"));
                    editText.setHintTextColor(Color.parseColor("#ABABAB"));
                    layout.setBackgroundColor(Color.parseColor("#000000"));
                    editor.putString("theme", "black");
                    editor.apply();
                } else {
                    editText.setTextColor(Color.parseColor("#000000"));
                    editText.setHintTextColor(Color.parseColor("#ABABAB"));
                    layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    editor.putString("theme", "white");
                    editor.apply();
                }
            }
            public void onSwipeRight() {
                //지울 내용을 백업
                if (editText.getText().length() > 0) {
                    SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("backup", editText.getText().toString());
                    editor.remove("save");
                    editor.apply();
                    editText.setText("");
                    editText.setHint("되돌릴려면 아래로 스와이프.");
                }
            }

            public void onSwipeLeft() {
                //지울 내용을 백업
                if (editText.getText().length() > 0) {
                    SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("backup", editText.getText().toString());
                    editor.remove("save");
                    editor.apply();
                    editText.setText("");
                    editText.setHint("되돌릴려면 아래로 스와이프.");
                }
            }

            public void onSwipeBottom() {
                SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (pref.getString("backup", "").length() > 0) {
                    buofbu = editText.getText().toString();
                    editText.setText(pref.getString("backup", ""));
                    editor.remove("backup");
                    editor.apply();
                    editText.setHint("화면을 탭해 작성을 시작하세요.");
                } else if (buofbu.length() > 0) {
                    editor.putString("backup", editText.getText().toString());
                    editor.apply();
                    editText.setText(buofbu);
                    buofbu = "";
                } else {
                    Toast.makeText(MainActivity.this, "되돌릴 노트가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //자동저장
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences pref = getSharedPreferences("notepad", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("save", editText.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
