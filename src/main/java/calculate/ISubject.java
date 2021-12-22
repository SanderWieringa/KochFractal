package calculate;

import edges.IObserver;

public interface ISubject {
    void subscribe(IObserver sub);

    void unsubscribe(IObserver sub);

    void notifySubs();
}
