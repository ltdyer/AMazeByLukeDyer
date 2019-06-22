package edu.wm.cs.cs301.amazebylukedyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AMazeActivity extends AppCompatActivity {

    private SeekBar selectSkillBar;
    private Button revisitButton;
    private Button exploreButton;
    private TextView revisitChosen;
    private Spinner exploreChosen;
    private TextView exploreChosenText;
    private Button startButton;

    private View.OnClickListener visibilityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            makeExplorerSpinnerVisible();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.AMazeActivity_main);

        selectSkillBar = (SeekBar) findViewById(R.id.select_skill_bar);
        selectSkillBar.setMax(9);
        revisitButton = (Button) findViewById(R.id.Revisit_button);
        exploreButton = (Button) findViewById(R.id.Explore_button);

        revisitChosen = (TextView) findViewById(R.id.Revisit_chosen);
        exploreChosen = (Spinner) findViewById(R.id.Explore_chosen);

        startButton = (Button) findViewById(R.id.start_button);

        exploreButton = new Button(this);
        exploreButton.setOnClickListener(visibilityClickListener);

    }

    public void addEntriesToSpinner() {
        List<String> spinnerOptions = new ArrayList<String>();
        spinnerOptions.add("Prim");
        spinnerOptions.add("Kruskal");
        spinnerOptions.add("Default");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exploreChosen.setAdapter(arrayAdapter);
    }
    public void selectSkillLevelListener() {
        selectSkillBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                exploreChosenText.setText(String.valueOf(i));
                Log.v("skill is: ", ""+ String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void makeExplorerSpinnerVisible() {
        exploreChosen.setVisibility(View.VISIBLE);
    }



    public void changeToGeneratingState(View view) {
        String keyIdentifier = null;
        String skill = "Skill";
        String alg = "Algorithm";
        Intent intent = new Intent(this, GeneratingActivity.class);
        keyIdentifier = ((Spinner) findViewById(R.id.Explore_chosen)).getSelectedItem().toString();
        intent.putExtra(skill, selectSkillBar.getProgress());
        intent.putExtra(alg, keyIdentifier);
        startActivity(intent);
    }
}
