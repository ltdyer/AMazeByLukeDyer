package edu.wm.cs.cs301.amazebylukedyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AMazeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SeekBar selectSkillBar;
    private Button revisitButton;
    private Button exploreButton;
    private TextView revisitChosen;
    private Spinner exploreChosen;
    private TextView exploreChosenText;
    private Button startButton;

    /**
     * Interface definition for overriding the onClick built in method. Not entirely necessary but makes the code cleaner by not putting everything in the constructor
     * @author Luke Dyer
     */
    private View.OnClickListener visibilityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            makeExplorerSpinnerVisible(v);
        }
    };

    /**
     * Initialization method called when app is started
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amazeactivity_main);

        selectSkillBar = (SeekBar) findViewById(R.id.select_skill_bar);
        selectSkillBar.setMax(9);
        revisitButton = (Button) findViewById(R.id.Revisit_button);
        exploreButton = (Button) findViewById(R.id.Explore_button);

        revisitChosen = (TextView) findViewById(R.id.Revisit_chosen);
        exploreChosen = (Spinner) findViewById(R.id.Explore_chosen);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.explore_choices_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exploreChosen.setAdapter(arrayAdapter);
        exploreChosen.setOnItemSelectedListener(this);


        startButton = (Button) findViewById(R.id.start_button);

        exploreButton.setOnClickListener(visibilityClickListener);
        selectSkillLevelListener();

    }

    /**
     * SkillBar method
     * method that watches for change on the SkillBar and updates the user on what level was chosen
     */
    public void selectSkillLevelListener() {
        selectSkillBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.v("skill is: ", ""+i);
                Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Spinner method
     * Method called by onClick when the explore button is pressed to make the spinner for choosing the generation algorithm visible
     * @param view
     */
    public void makeExplorerSpinnerVisible(View view) {
        if (exploreChosen.getVisibility() == View.GONE || exploreChosen.getVisibility() == View.INVISIBLE) {
            exploreChosen.setVisibility(View.VISIBLE);
        }
        else {
            exploreChosen.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.v("Maze generations is: ", ""+id);
        Toast.makeText(parent.getContext(), "OnItemSelectedListener: " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /**
     * Method that moves the app from the main menu to the maze generation screen when the start button is pressed
     * @param view
     */
    public void changeToGeneratingState(View view) {
        String keyIdentifierSpinner = null;
        String keyIdentifierSkillBar = null;
        String skill = "Skill";
        String alg = "Algorithm";

        Intent intent1 = new Intent(this, GeneratingActivity.class);
        keyIdentifierSpinner = ((Spinner) findViewById(R.id.Explore_chosen)).getSelectedItem().toString();
        intent1.putExtra(skill, selectSkillBar.getProgress());
        intent1.putExtra(alg, keyIdentifierSpinner);

        startActivity(intent1);
    }
}
