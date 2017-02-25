package github.kumarpiyush.awo.Sync;

public interface ISyncComponent {
    // returns true iff operation successful
    public boolean updateState();

    // returns true iff operation successful
    public boolean notifyChanges();
}
