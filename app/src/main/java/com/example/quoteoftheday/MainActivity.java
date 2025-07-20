package com.example.quoteoftheday;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    String[] quotes = {
            "Believe in yourself!",
            "Never give up.",
            "You are stronger than you think.",
            "Stay positive, work hard, make it happen.",
            "Success is no accident."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView quoteText = findViewById(R.id.quoteText);
        Button showQuoteButton = findViewById(R.id.showQuoteButton);

        // Подключаем ViewModel
        QuoteViewModel viewModel = new ViewModelProvider(this).get(QuoteViewModel.class);

        // Наблюдаем за LiveData и обновляем текст
        viewModel.getQuote().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                quoteText.setText(s);
            }
        });

        // Кнопка: показать новую цитату
        showQuoteButton.setOnClickListener(v -> {
            viewModel.generateNewQuote();
        });
    }

}


/*import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView quote = findViewById(R.id.quoteText);
        quote.setText("Believe in yourself!");
    }
}*/
