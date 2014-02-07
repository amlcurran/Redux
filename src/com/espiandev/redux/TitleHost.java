package com.espiandev.redux;

/**
 * Represents an item which can show titles and subtitles
 */
public interface TitleHost {
    void setTitle(CharSequence title);
    void setSubtitle(CharSequence subtitle);

    public static final TitleHost NONE = new TitleHost() {
        @Override
        public void setTitle(CharSequence title) {

        }

        @Override
        public void setSubtitle(CharSequence subtitle) {

        }
    };

}
