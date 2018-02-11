package com.ramki.myexercise.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.ramki.myexercise.data.model.Fact;
import com.ramki.myexercise.data.model.Information;
import com.ramki.myexercise.data.model.Response;
import com.ramki.myexercise.data.repository.ApplicationRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ramki on 2/11/2018.
 */
public class MainActivityViewModelTest {

    private MainActivityViewModule mViewModel;

    @Before
    public void setupTasksViewModel() {
        mViewModel = new MainActivityViewModule();
        ArrayList<Information> informations = new ArrayList<>();
        informations.add(new Information("Title1", "Desc1", null));
        informations.add(new Information("Title2", "Desc2", "https://www.google.com"));
    }


    @Test
    public void allDataLoaded() {
        mViewModel.refreshFact().observeForever(new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                if (response.status == Response.SUCCESS) {
                    assertFalse(((Fact) response.data) == null);
                    assertTrue(((Fact) response.data).getInformations().size() >= 0);
                }

            }
        });

    }

    @Test
    public void dataNotLoaded() {
        mViewModel.refreshFact().observeForever(new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                if (response.status != Response.SUCCESS) {
                    assertTrue(((Throwable) response.error) != null);
                }
            }
        });

    }

    @Test
    public void SceenRotate() {
        assertTrue(mViewModel.loadFact().getValue() == mViewModel.refreshFact().getValue());
    }


}
