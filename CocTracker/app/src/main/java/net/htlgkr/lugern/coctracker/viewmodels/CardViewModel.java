package net.htlgkr.lugern.coctracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.models.Card;

import java.util.ArrayList;

public class CardViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Card>> observableItems;
    private ArrayList<Card> cards;


    public CardViewModel() {
        observableItems = new MutableLiveData<>();
        cards = new ArrayList<>();
        Card card = new Card();

        card.setTvExpLvl("1000");
        card.setIvBuildTrophies(R.drawable.a1);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);
        cards.add(card);

        observableItems.postValue(cards);

    }
}
