package tech.fastj.stackattack.ui;

import tech.fastj.gameloop.event.GameEvent;

public class ContentChangedEvent<T> extends GameEvent {

    private final T oldContent;
    private final T newContent;
    private final ContentBox source;

    public ContentChangedEvent(T oldContent, T newContent, ContentBox source) {
        this.oldContent = oldContent;
        this.newContent = newContent;
        this.source = source;
    }

    public T getOldContent() {
        return oldContent;
    }

    public T getNewContent() {
        return newContent;
    }

    public ContentBox getSource() {
        return source;
    }
}
