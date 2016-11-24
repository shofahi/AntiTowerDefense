import java.awt.event.ActionEvent;

/**
 * Created by MasoudMac on 24/11/16.
 */
public interface Observable {

    void registerObserver(Observer observer);
    void notifyObservers();
    void removeObserver(Observer observer);
}
