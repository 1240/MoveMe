package ru.moleculus.moveme.presenters;

import ru.moleculus.moveme.interactors.RequestInteractor;
import ru.moleculus.moveme.interactors.impl.RequestInteractorImpl;

/**
 * Created by Oleg on 02.03.2016.
 */
public interface BasePresenter{
     RequestInteractor requestInteractor = new RequestInteractorImpl();
}
