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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AMazeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final public static String GENERATIONALGO = "ALGORITHM";
    final public static String SKILLLEVEL = "LEVEL";
    final public static String OPERATIONMODE = "OPERATION";
    final public static String FILENAME = "example.txt";
    private SeekBar selectSkillBar;
    private Button revisitButton;
    private Button exploreButton;
    private TextView revisitChosen;
    private Button levelZeroButton;
    private Button levelOneButton;
    private Button levelTwoButton;
    private Spinner exploreChosen;
    private TextView exploreChosenText;
    private Button startButton;
    private Spinner operationMode;

    /**
     * Interface definition for overriding the onClick built in method. Not entirely necessary but makes the code cleaner by not putting everything in the constructor
     * @author Luke Dyer
     */
    private View.OnClickListener visibilityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //when we come back, maye try and make explore invisible if revisit is clicked but maybe just move on
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
        levelZeroButton = (Button) findViewById(R.id.levelZero_button);
        levelOneButton = (Button) findViewById(R.id.levelOne_button);
        levelTwoButton = (Button) findViewById(R.id.levelTwo_button);


        exploreChosen = (Spinner) findViewById(R.id.Explore_chosen);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.explore_choices_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exploreChosen.setAdapter(arrayAdapter);
        exploreChosen.setOnItemSelectedListener(this);

        operationMode = (Spinner) findViewById(R.id.Operation_mode);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.operation_choices_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationMode.setAdapter(arrayAdapter2);
        operationMode.setOnItemSelectedListener(this);

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
            revisitButton.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.v("You chose: ", ""+id);
        Toast.makeText(parent.getContext(), "You Have Selected: " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Makes the buttons for previous maze generation visible. Makes explorer invisible
     * @param view
     */
    public void makeRevisitChoicesVisible(View view) {
        exploreButton.setVisibility(View.INVISIBLE);
        selectSkillBar.setVisibility(View.INVISIBLE);
        levelZeroButton.setVisibility(View.VISIBLE);
        levelOneButton.setVisibility(View.VISIBLE);
        levelTwoButton.setVisibility(View.VISIBLE);
    }

    /**
     * By pressing them after revisit, checks for a saved maze of the corresponding size. If there is one, uses it, if not, makes one of this size
     * @param view
     */
    public void levelZeroMaze(View view) {
        selectSkillBar.setProgress(0);
        Toast.makeText(getBaseContext(), "Level is 0", Toast.LENGTH_SHORT).show();
    }

    public void levelOneMaze(View view) {
        selectSkillBar.setProgress(1);
        Toast.makeText(getBaseContext(), "Level is 1", Toast.LENGTH_SHORT).show();
    }

    public void levelTwoMaze(View view) {
        selectSkillBar.setProgress(2);
        Toast.makeText(getBaseContext(), "Level is 2", Toast.LENGTH_SHORT).show();
    }


    /**
     * Method that moves the app from the main menu to the maze generation screen when the start button is pressed
     * @param view
     */
    public void changeToGeneratingState(View view) {
        String keyIdentifierSeekBarSkillLevel = null;
        String keyIdentifierSpinnerAlgo = null;
        String keyIdentifierSpinnerOperation = null;


        Intent intent1 = new Intent(this, GeneratingActivity.class);
        keyIdentifierSeekBarSkillLevel = ""+selectSkillBar.getProgress();
        keyIdentifierSpinnerAlgo = ((Spinner) findViewById(R.id.Explore_chosen)).getSelectedItem().toString();
        keyIdentifierSpinnerOperation = ((Spinner) findViewById(R.id.Operation_mode)).getSelectedItem().toString();

        intent1.putExtra(SKILLLEVEL, keyIdentifierSeekBarSkillLevel);
        intent1.putExtra(GENERATIONALGO, keyIdentifierSpinnerAlgo);
        intent1.putExtra(OPERATIONMODE, keyIdentifierSpinnerOperation);

        Log.v("skill level: ", ""+keyIdentifierSeekBarSkillLevel);
        Log.v("generation algo is: ", ""+keyIdentifierSpinnerAlgo);
        Log.v("operation mode is: ", ""+keyIdentifierSpinnerOperation);

        startActivity(intent1);

    }

    public void save(View v) {
        String text = ""+selectSkillBar.getProgress();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILENAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILENAME, Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            //selectSkillBar.setProgress();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gives the user the chance to make things visible aain that were turned invisible
     * @param view
     */
    public void refreshOptions(View view) {
        exploreButton.setVisibility(View.VISIBLE);
        revisitButton.setVisibility(View.VISIBLE);
        selectSkillBar.setVisibility(View.VISIBLE);
        selectSkillBar.setProgress(0);
        levelZeroButton.setVisibility(View.INVISIBLE);
        levelOneButton.setVisibility(View.INVISIBLE);
        levelTwoButton.setVisibility(View.INVISIBLE);
        exploreChosen.setVisibility(View.INVISIBLE);
        operationMode.setSelection(0);
    }
}
