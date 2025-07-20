package com.example.quoteoftheday;

import android.app.Application;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class QuoteViewModel extends AndroidViewModel {

    private final MutableLiveData<String> quote = new MutableLiveData<>();
    private String[] quotes = {};

    public QuoteViewModel(@NonNull Application application) {
        super(application);
        loadQuotesFromAssets();
        generateNewQuote();
    }

    public LiveData<String> getQuote() {
        return quote;
    }

    public void generateNewQuote() {
        if (quotes.length == 0) return;

        String current = quote.getValue();
        String newQuote = current;

        if (quotes.length == 1) {
            // Если только одна цитата — показываем её
            newQuote = quotes[0];
        } else {
            while (newQuote == null || newQuote.equals(current)) {
                int randomIndex = new Random().nextInt(quotes.length);
                newQuote = quotes[randomIndex];
            }
        }

        quote.setValue(newQuote);
    }


    private void loadQuotesFromAssets() {
        try {
            AssetManager assetManager = getApplication().getAssets();
            InputStream is = assetManager.open("quotes.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            JSONArray jsonArray = new JSONArray(builder.toString());
            quotes = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                quotes[i] = jsonArray.getString(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            quotes = new String[]{"Error loading quotes."};
        }
    }
}
