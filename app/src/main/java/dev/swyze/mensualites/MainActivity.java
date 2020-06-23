package dev.swyze.mensualites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.text.DecimalFormat;

import dev.swyze.mensualites.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    Double capital;
    Double rate;
    Double nbYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.buttonName.setOnClickListener(getOnClickListener());

        binding.editTextTextCapital.addTextChangedListener(watcher);
        binding.editTextNbYears.addTextChangedListener(watcher);
        binding.editTextRate.addTextChangedListener(watcher);
    }

    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnButton(view);
            }
        };
    }

    private void clickOnButton(View view) {

        if (view instanceof Button) {
            if (binding.buttonName.equals(view)) {
                hideKeybaord(view);
                try {
                    capital = Double.parseDouble(binding.editTextTextCapital.getText().toString());
                    rate = Double.parseDouble(binding.editTextRate.getText().toString());
                    nbYears = Double.parseDouble(binding.editTextNbYears.getText().toString());
                    DecimalFormat df = new DecimalFormat("###.##");
                    binding.textViewResultat.setText(df.format(calcul(capital, rate, nbYears)) + "€/mois");
                } catch (Exception e) {
                    Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private TextWatcher watcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                capital = Double.parseDouble(binding.editTextTextCapital.getText().toString());
                rate = Double.parseDouble(binding.editTextRate.getText().toString());
                nbYears = Double.parseDouble(binding.editTextNbYears.getText().toString());
                DecimalFormat df = new DecimalFormat("###.##");
                binding.textViewResultat.setText(df.format(calcul(capital, rate, nbYears)) + "€/mois");
            } catch (Exception e) {
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };


    public double calcul(double capital, double rate, double nbYears) {
        return (capital * ((rate / 100) / 12)) / (1 - Math.pow(1 + ((rate / 100) / 12), -(nbYears * 12)));
    }

    private void hideKeybaord(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
